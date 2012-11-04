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

Un langage hybride, tel que OCaml, fournit 

### The expression problem
### Extension des comportements structurels au langage

Presentation du langage
-----------------------

### Inference de types
### Types de données
#### Tuples
#### Types somme
### Filtrage par motif
### Fonctions d'ordre superieur
### TypeClasses

Implantation
------------

### Typeur
### Extension du typeur pour les types sommes récursifs
### Extension du typeur pour les TypeClasses

