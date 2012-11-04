Lune : Un mini langage ML avec TypeClasses
==========================================

Motivation
----------

Le but de ce projet est de réaliser un petit langage de la famille des langages ML, qui implante une fonctionnalité de type "TypeClasses" telle qu'elle est implantée dans le langage Haskell.

Il n'est pas nouveau que, tant dans les langages fonctionnels que dans les langages orientés objets, les réalisateurs de langages de programmation cherchent a trouver de nouveaux moyens de programmer d'une manière qui soit a la fois sure et modulaire, permettant la réutilisation et l'extension du code exterieur tout en garantissant la sureté du typage.

Les methodes d'extensions en C# sont un exemple d'une telle fonctionnalité, permettant d'étendre les fonctionnalités de types réalisés a l'exterieur d'un projet.

Les conversions implicites de Scala représentent une autre manière d'étendre de manière transparente du code.

Les typeclasses telles qu'implantées en Haskell représentent une solution élégante pour les langages fonctionnels de rajouter a la fois :

- Une variété de comportements selon le type, sans avoir recours a des fonctions d'ordre supérieur
- Une facilité a étendre le code existant, tout en restant dans un paradigme purement fonctionnels.

D'autres langages implantent des systèmes similaires, prouvant la popularité de cette abstraction. (interfaces en Rust, protocoles en Clojure).

### Polymorphisme ad-hoc

Un des principaux avantages de cette abstraction est de permettre un polymorphisme ad-hoc au sein d'un langage purement fonctionnel. Le polymorphisme ad-hoc représente l'idée qu'une procédure implante des comportements hétérogènes, dépendants du type de ou des valeurs qui lui sont passée. La forme la plus connue de polymorphisme ad hoc aujourd'hui est celle que l'on retrouve en Java/C# et autres langes implantant le même sous ensemble du paradigme objet, dont on voit un exemple en Scala ci dessous :

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

OCaml, langage hybride, au coeur fonctionnel et implantant une surcouche objet, fournit un moyen d'implanter du polymorphisme ad-hoc au travers de sa couche objet. Cependant, OCaml n'étant pas un langage objet, les objets sont utilisés plus ou moins souvent dans du code OCaml idiomatique, et la connection entre les deux niveaux d'abstraction n'est pas forcément évidente.

C'est pourquoi il m'a semblé interressant d'implanter un système de typeclasses sur un langage similaire au coeur fonctionnel de OCaml, Caml Light.

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

Ce qu'on remarque dans ce cas, c'est que dans du code structuré de cette manière, il est extremement simple pour une personne qui utilise le code sans le posséder (c'est a dire sans pouvoir le modifier) de rajouter des nouveaux comportements concernant le type défini :

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
  with to_string (ev1, ev2) -> concat (concat (to_string ev1) " + ") (to_string ev2)

instance Stringable Substraction
  with to_string (ev1, ev2) -> concat (concat (to_string ev1) " - ") (to_string ev2)

instance Stringable Division
  with to_string (ev1, ev2) -> concat (concat (to_string ev1) " / ") (to_string ev2)

~~~

On voit ici qu'on peut étendre la classe "Evalable" avec un nouveau type, "Division", et ceci en dehors de la définition originelle des types de données. Il est également possible d'étendre des types existants avec de nouveaux comportements. Le problème que nous nous posions a l'origine est résolu !

### Extension des comportements structurels au langage

Un effet de bord interressant de cette abstraction est qu'il est possible d'implanter un grand nombre de comportements structurels du langage, et essentiel a son fonctionnement, en terme de typeclasses, comme par exemple, l'égalité, la conversion en chaine de caractères, etc.

Ceci est courant dans les langages objets, mais assez peu répandu dans les langages fonctionnels.

Presentation du langage
-----------------------

Dans cette section nous présenterons brièvement les caractéristiques de Lune, le mini langage ML implanté dans le cadre de ce projet.

### Inference de types

Lune, étant un langage de la classe des ML, possède un typeur appliquant un algorithme d'inférence de type Hindley-Milner, ce qui rend la plupart des déclarations de types inutiles. Cet algorithme suit l'algorithme original dans le contexte du lambda-calcul typé. Il est cependant nécessaire de l'adapter a certaines des fonctionnalités du langage, ce qui sera décrit dans les sections suivantes.

### Types de données
#### Tuples
#### Types somme
### Filtrage par motif
### Fonctions d'ordre superieur
### TypeClasses

