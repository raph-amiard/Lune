Lune Mini ML Language
=====================

### Introduction

Lune is a mini language of the ML family. 
It's syntax is a bit different from the usual ML languages, both for simplicity and for experimentation reasons.

A quick list of the differences are :

- Top level definitions are introduced with the "def" keyword
- There are no operators, only regular functions

Its features include :

- Hindley-Milner style type inference

~~~
 > def pow2 a = * a a ;;
 = fun(int -> int)
~~~

- Tuples

~~~
 > def mytuple = (1, 2)
 = (1, 2)
~~~

- Sum types, including recursive sum types

~~~
 > type intlist = | Node of int * intlist | Tail of int ;;
~~~

- Pattern matching 

~~~
def sumlist a = match a with
  | Node(i, tail) -> + i (sumlist tail)
  | Tail(i) -> i ;;

def il = Node(1, Node(2, Node(3, Node(4, Tail(5))))) ;;

sumlist il ;;
= 15
~~~

- First class functions

~~~
def maplist il fn = match il with
  | Node(i, tail) -> Node((fn i), (maplist tail fn))
  | Tail(i) -> Tail((fn i)) ;;

maplist il (fun a -> * a 2);;
= Node((2, Node((4, Node((6, Node((8, Tail(10)))))))))
~~~

- Haskell style, but very basic, typeclasses

~~~
class Foo a 
  with bar : fun(a -> int)
  with bar2 : fun(a -> (int * int)) ;;

instance Foo int
  with bar a = + a 2
  with bar2 a = (a, a) ;;
  
instance Foo (int * int)
  with bar (a, b) = + a b
  with bar2 (a, b) = (a, b) ;;
~~~

### How to run

- Via eclipse, just run Main
- If you have sbt, just run "sbt run"

### Limitations

- REPL only for the moment
- Kind of has generic types, but not really implemented yet
- Very *Very* basic interpreter
