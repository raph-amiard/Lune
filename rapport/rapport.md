% Un mini langage ML avec TypeClasses
% Novembre 2012 - Raphaël Amiard

Motivation
----------

Le but de ce projet est de réaliser un petit langage de la famille des langages ML, qui implante une fonctionnalité de type "TypeClasses" telle qu'elle est implantée dans le langage Haskell.

Il n'est pas nouveau que, tant dans les langages fonctionnels que dans les langages orientés objets, les réalisateurs de langages de programmation cherchent a trouver de nouveaux moyens de programmer d'une manière qui soit a la fois sure et modulaire, permettant la réutilisation et l'extension du code extérieur tout en garantissant la sureté du typage.

Les methodes d'extensions en C# sont un exemple d'une telle fonctionnalité, permettant d'étendre les fonctionnalités de types réalisés a l'extérieur d'un projet.

Les conversions implicites de Scala représentent une autre manière d'étendre de manière transparente du code.

Les typeclasses telles qu'implantées en Haskell représentent une solution élégante pour les langages fonctionnels de rajouter a la fois :

- Une variété de comportements selon le type, sans avoir recours a des fonctions d'ordre supérieur
- Une facilité a étendre le code existant, tout en restant dans un paradigme purement fonctionnels.

D'autres langages implantent des systèmes similaires, prouvant la popularité de cette abstraction. (interfaces en Rust, protocoles en Clojure).

### Polymorphisme ad-hoc

Un des principaux avantages de cette abstraction est de permettre un polymorphisme ad-hoc au sein d'un langage purement fonctionnel. Le polymorphisme ad-hoc représente l'idée qu'une procédure implante des comportements hétérogènes, dépendants du type de ou des valeurs qui lui sont passée. La forme la plus connue de polymorphisme ad-hoc aujourd'hui est celle que l'on retrouve en Java/C# et autres langes implantant le même sous ensemble du paradigme objet, dont on voit un exemple en Scala ci dessous :

~~~scala
abstract class Expr {
    def eval() : Int
}
class Addition(v1 : Int, v2 : Int) {
    def eval() : Int = v1 + v2
}
class Substraction(v1 : Int, v2 : Int) {
    def eval() : Int = v1 - v2
}
~~~

On voit ici que la procédure eval, a un comportement hétérogène selon le type, qui dépend directement du type de son argument implicite "this".

OCaml, langage hybride, au coeur fonctionnel et implantant une surcouche objet, fournit un moyen d'implanter du polymorphisme ad-hoc au travers de sa couche objet. Cependant, OCaml n'étant pas un langage objet, les objets sont utilisés plus ou moins souvent dans du code OCaml idiomatique, et la connexion entre les deux niveaux d'abstraction n'est pas forcément évidente.

C'est pourquoi il m'a semblé intéressant d'implanter un système de typeclasses sur un langage similaire au coeur fonctionnel de OCaml, Caml Light.

### Modularité et extensibilité, ou "The expression problem"

Comme cela a été esquissé en introduction, les typeclasses permettent de résoudre un problème d'extensibilité et de modularité très courant dans les langages statiquement typés, connu sous le nom d'"Expression problem"

Dans un langage fonctionnel pur du type Caml Light, le moyen le plus simple et le plus répandu pour fournir un comportement variable en fonction du type des arguments est l'utilisation des types sommes, selon l'idiome suivant :

~~~ocaml
type Expr = 
  | Val of int
  | Addition of Expr * Expr
  | Substraction of Expr * Expr

let eval e = function 
  | Val(v) -> v
  | Addition(v1, v2) -> (eval v1) + (eval v2)
  | Substraction(v1, v2) -> (eval v1) - (eval v2)
~~~

Ce qu'on remarque dans ce cas, c'est que dans du code structuré de cette manière, il est extrêmement simple pour une personne qui utilise le code sans le posséder (c'est a dire sans pouvoir le modifier) de rajouter des nouveaux comportements concernant le type défini :

~~~ocaml
let as_string e = function
  | Val(v) -> string_of_int v
  | Addition(v1, v2) -> (as_string v1) ^ " + " ^ (as_string v2)
  | Substraction(v1, v2) -> (as_string v1) ^ " - " ^ (as_string v2)
~~~

Il est en revanche impossible d'étendre l'ensemble de types représenté par le type Expr si l'on n'est pas le propriétaire direct du code existant.

Dans le paradigme objet dominant, le problème est inversé. Rappelons le code idiomatique correspondant au code fonctionnel vu ci-dessus :

~~~scala
abstract class Expr {
    def eval() : Int
}
class Addition(v1 : Int, v2 : Int) extends Expr {
    def eval() : Int = v1 + v2
}
class Substraction(v1 : Int, v2 : Int) extends Expr {
    def eval() : Int = v1 - v2
}
~~~

Si l'on veut étendre l'ensemble des types auxquels eval peut s'appliquer, il nous suffit de définir un nouveau type.

~~~scala
class Division(v1 : Int, v2 : Int) extends Expr {
    def eval() : Int = v1 / v2
}
~~~

En revanche, il n'existe pas de moyen simple de rajouter le comportement "as_string" si l'on n'est pas le propriétaire du code des classes Expr, Addition, et Substraction.

Les typeclasses permettent de pallier a ce problème. En effet, il est possible avec les typeclasses :

- De rajouter des nouveaux comportements sur des types existants, en précisant a posteriori l'implantation correspondante
- D'étendre les comportements a des nouveaux types, de la même manière.

Pour donner un exemple, voici le cas étudié, cette fois ci dans notre mini langage, utilisant les typeclasses :

~~~ocaml

class Evalable a
  with eval : fun(a -> int) ;;

type Addition = Evalable * Evalable ;;
type Substraction = Evalable * Evalable ;;

instance Evalable Addition 
  with eval (ev1, ev2) = + (eval ev1) (eval ev2) ;;

instance Evalable Substraction 
  with eval (ev1, ev2) = - (eval ev1) (eval ev2) ;;

(* Dans un autre fichier *)
type Division = Evalable * Evalable ;;

instance Evalable Division 
  with eval (ev1, ev2) = / (eval ev1) (eval ev2) ;;

(* On imagine que cette classe existe déja, et fait partie du langage *)
class Stringable a
  with to_string : fun(a -> string)

instance Stringable Addition
  with to_string (ev1, ev2) =
    concat (concat (to_string ev1) " + ") (to_string ev2)

instance Stringable Substraction
  with to_string (ev1, ev2) = 
    concat (concat (to_string ev1) " - ") (to_string ev2)

instance Stringable Division
  with to_string (ev1, ev2) = 
    concat (concat (to_string ev1) " / ") (to_string ev2)

~~~

On voit ici qu'on peut étendre la classe "Evalable" avec un nouveau type, "Division", et ceci en dehors de la définition originelle des types de données. Il est également possible d'étendre des types existants avec de nouveaux comportements. Le problème que nous nous posions a l'origine est résolu !

### Extension des comportements structurels au langage

Un effet de bord intéressant de cette abstraction est qu'il est possible d'implanter un grand nombre de comportements structurels du langage, et essentiel a son fonctionnement, en terme de typeclasses, comme par exemple, l'égalité, la conversion en chaine de caractères, etc.

Ceci est courant dans les langages objets, mais assez peu répandu dans les langages fonctionnels.

Présentation du langage
-----------------------

Dans cette section nous présenterons brièvement les caractéristiques de Lune, le mini langage ML implanté dans le cadre de ce projet.

### Inférence de types

Lune, étant un langage de la classe des ML, possède un typeur appliquant un algorithme d'inférence de type Hindley-Milner, ce qui rend la plupart des déclarations de types inutiles. Cet algorithme suit l'algorithme original dans le contexte du lambda-calcul typé. Il est cependant nécessaire de l'adapter a certaines des fonctionnalités du langage, ce qui sera décrit dans les sections suivantes.

### Types de données

Lune possède les types de données primitifs suivants : Entier, Flottant, Booléen, Chaine de caractères, Unit. Lune offre également la possibilité de créer des types composites.

#### Tuples

La première classe de type composite que l'on peut créer sont les tuples :

~~~ocaml
def tuplint = (1, 2, 3, 4)
(* = (1, 2, 3, 4) *)
~~~

Les tuples sont typés structurellement, comme il est de rigueur. L'extension du typeur au types sommes se fait de manière relativement évidente, en parcourant récursivement les sous composantes des types tuples, et en les unifiant une a une.

#### Types somme

La deuxième classe de type composite que l'on peut créer sont les types somme

~~~ocaml
type Expr = 
  | Val of int
  | Addition of Expr * Expr
  | Substraction of Expr * Expr ;;
~~~

Les types sommes sont typés nominalement. On ne peut avoir de type somme anonyme (il doit forcément faire partie d'une déclaration de type). En cela, Lune suit le comportement des langages ML.

L'extension du typeur aux types sommes est relativement simple, étant donné que ceux ci sont nominalement typés. Cependant, un but dans la conception de Lune est de gérer les types génériques, même ci ceux ci ne sont pas complètement implantés dans la version actuelle du langage, par manque de temps.

Cette contrainte implique qu'il faut parcourir récursivement les branches des types sommes pour unifier les éventuels types paramétriques. Si l'on combine cette contrainte avec la nécessité de gérer les types sommes récursifs, on comprends que l'implantation du typage des types sommes demande le plus grand soin.

### Filtrage par motif

Lune implante également le filtrage par motif, a la fois pour les tuples et pour les types sommes. Le typage du filtrage est relativement simple, et permet par la même occasion au typeur de récupérer des informations de type.

~~~ocaml
type IntList = 
  | Node of int * IntList
  | Tail of int ;;

def sumlist a = match a with
  | Node(i, tail) -> + i (sumlist tail)
  | Tail(i) -> i ;;

def il = Node(1, Node(2, Node(3, Node(4, Tail(5))))) ;;

sumlist il ;;

= 15
~~~

### Fonctions d'ordre superieur

Lune implante bien évidemment les fonctions comme valeurs de première classe, ce qui permet de programmer dans le style fonctionnel idiomatique. Les fonctions gèrent la sous, et la sur application. dans la lignée du lambda calcul, toute fonction a plusieurs arguments peut être considéré comme la composition de fonctions unaires.

~~~ocaml
def maplist il fn = match il with
  | Node(i, tail) -> Node((fn i), (maplist tail fn))
  | Tail(i) -> Tail((fn i)) ;;

maplist il (fun a -> * a 2);;
= Node(2, Node(4, Node(6, Node(8, Tail(10)))))
~~~

### TypeClasses

Enfin, Lune implante une version complète, mais grandement simplifiée, des typeclasses a la Haskell, ce qui nous permet d'avoir du polymorphisme ad-hoc dans un style de programmation purement fonctionnel.

~~~ocaml
type IntList = | Node of int * IntList | Tail of int ;;
type IntTree = | TNode of int * IntTree * IntTree | Leaf of int ;;

class Countable a
  with count : fun(a -> int) ;;
  
instance Countable IntList
  with count il = match il with
    | Node(i, tail) -> + 1 (count tail)
    | Tail(i) -> 1

instance Countable IntTree
  with count it = match it with
    | TNode(v, n1, n2) -> + 1 (+ (count n1) (count n2))
    | Leaf(v) -> 1
~~~

Cet exemple illustre comment les typeclasses permettent de définir des protocoles génériques sur les types, ce qui est particulièrement utile pour les opérations primitives d'un langage.

Pour donner un exemple, la fonction map d'ocaml n'est pas polymorphique, et ne fonctionne que sur les listes. Si l'on veut l'utiliser sur un tableau, il est nécessaire d'utiliser la fonction spécifique aux tableaux.

Avec les typeclasses, on peut imaginer un système ressemblant a celui de haskell :

~~~haskell
class  Functor f  where
    fmap :: (a -> b) -> f a -> f b
~~~

ou chaque fonction map n'est qu'une facette de la fonction fmap, généralisée a tout les types mappables.

Point qui nous amène aux limitations de l'implantation des typeclasses dans Lune :

1. Les typeclasses ne peuvent dispatcher que sur le premier argument des fonctions définies. Ceci veut dire que la classe suivante ne pourra pas mener a du code fonctionnel dans l'état actuel.

~~~ocaml
class Foo a 
  with bar : fun(int -> a -> int)
~~~

La limitation n'est pas dans le typeur mais dans l'interpreteur, qui dispatche sur le type du premier arguement, dans l'implantation actuelle.

2. Les typeclasses ne fonctionnent pas avec les types paramétriques. Ceci est une limitation qui découle juste du manque de temps pour procéder a l'implantation. En effet la grammaire ne gère pas l'introduction de polytypes supplémentaires dans la déclaration d'une classe de type.

### Limitations plus générales

En raison du temps limité nécessaire a l'implantation du langage, toutes les fonctionnalités n'ont pas été implantées.

- Tout les types primitifs n'ont pas été implantés, notamment les chaines de caractères et le type Unit sont manquants. Ils existent dans l'implantation du langage, mais ne sont pas gérés par l'analyseur syntaxique.
- Les annotations de type ne sont gérées que dans les déclarations de typeclasses, et nulle part aileurs.

Ces manques sont facilement remédiables, ne relevant pas d'un problème structurel dans l'implantation du système, mais plus aux choix qui ont été faits face aux contraintes de temps.

Implantation
------------

### Tour d'horizon

Le langage a été implanté a l'aide du langage Scala, qui a été choisi pour sa concision, son support du paradigme fonctionnel, et ses excellentes librairies.

La totalité du code représente un ensemble d'environ 1400 lignes. Sur ces 1400 lignes, environ 1000 appartiennent au typeur, et 250 a l'interpreteur. On voit ainsi que le typeur est vraiment la pièce centrale de ce projet.

Le langage ne possède pour l'instant qu'une boucle d'évaluation interactive, et ne permet ainsi pas d'évaluer un fichier entier.


### Analyseur syntaxique

L'analyseur syntaxique a été construit a l'aide de la librairie de combinateur d'analyseurs syntaxiques intégrée a la librairie standard de Scala.

Cette solution permet de générer un analyseur très compact (100 lignes pour la totalité de l'analyseur).

L'analyseur résultant est un analyseur LL(1) descendant, qui produit directement un Arbre de syntaxe abstraite utilisant les classes de données propres au langage.

Certaines des fonctionnalités du langage découlent d'une simplicité voulue d'implantation. Ainsi, le langage ne possède pas d'opérateurs infixes. De la même manière, la syntaxe des types fonctionnels diffère des langages OCaml ou Haskell.


### Typeur

Le typeur est implanté par une fonction recursive en arbre, parcourant l'arbre de syntaxe abstraite non typé, et produisant un arbre typé.

L'algorithme implanté par le typeur est une extension de l'algorithme de Hindley-Milner pour l'inférence de types. Ses étapes sont, de la même manière :

1. Récolte des contraintes et unification
2. Substitution des types

Le typeur, comme précédemment décrit, a dû être étendu pour gérer les types composites gérés par le langage. Ceci comprend donc la gestion des tuples, qui est triviale, mais également le typage des types sommes.

Le typage des typeclasses, étant le sujet principal de ce rendu, mérite qu'on s'y attarde quelque peu.

#### Typage des typeclasses

Le typage des typeclasses correspond a une extension triviale de l'algorithme de typage Hindley-Milner.

- Les polytypes ont un ensemble (qui peut être vide) de classes qu'ils doivent implanter.
- Lors de l'unification des polytypes, les classes sont propagées entre polytypes
- Lors de l'unification d'un polytype possédant des contraintes de classes a un monotype, on teste que le monotype satisfait bel et bien les contraintes du polytype, ce qui est la condition du typage.

### Interpréteur

L'interpréteur est implantée de manière extrêmement simple et naïve, par une fonction récursive descendante, parcourant l'arbre de syntaxe abstraite.

L'interpréteur utilise quelques classes auxilliaires, notamment pour gérer la représentation des données. Cependant, même dans ce domaine, la représentation la plus naïve a été choisie, pour optimiser le temps de réalisation, face au temps d'exécution.

L'interpreteur est la source de la limitation sur les typeclasses, stipulant que le premier argument d'une fonction d'une typeclass doit être du type implanté par la typeclass. En effet, le dispatch de ces fonction est fait a l'aide d'une imbrication de table de hachages suivant le schema suivant :

~~~
classes[classe][fonction][type]
~~~

Cette limitation est facile a résoudre cependant, dans le cas ou l'on en ressentirait le besoin.

Conclusion
----------

Bien que le langage ne soit pas terminé, l'objectif était de démontrer l'utilité d'un mécanisme de typeclasses dans un langage fonctionnel de type ML. On voit en effet qu'il est possible d'implanter des fonctionnalités génériques utiles qui sont impossible a implanter dans les langages dérivés de ML existants, si l'on omet Haskell.

De plus, cette extension s'intègre plus naturellement avec les idiomes ML que l'extension objet d'OCaml, permettant de programmer dans un style fonctionnel sans renoncer a la modularité et a l'extensibilité.
