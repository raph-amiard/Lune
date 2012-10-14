package main.scala

import scala.collection.immutable.HashMap


object test {
	val test_ast =
	  FunDef(List("a","b"),
			     FunCall(VarRef("+"), List(VarRef("a"), VarRef("b"))))
                                                  //> test_ast  : main.scala.FunDef = FunDef(List(a, b),FunCall(VarRef(+),List(Var
                                                  //| Ref(a), VarRef(b))))
	val varmap = List(("+", TypeFunction(List(TypeInt, TypeInt, TypeInt)))).toMap
                                                  //> varmap  : scala.collection.immutable.Map[java.lang.String,main.scala.TypeFun
                                                  //| ction] = Map(+ -> int -> int -> int)
  test_ast.typecheck(varmap, new TypeMap())       //> (IN FUNCALL,VarRef(+),List(VarRef(a), VarRef(b)))
                                                  //| (IN MATCH,int -> int -> int)
                                                  //| (IN UNIFY ,int -> int -> int,P1 -> P2 -> P3)
                                                  //| (IN UNIFY ,int,P1)
                                                  //| (IN UNIFY ,int -> int,P2 -> P3)
                                                  //| (IN UNIFY ,int,P2)
                                                  //| (IN UNIFY ,int,P3)
                                                  //| res0: main.scala.TypedExpr = TFunDef(P1 -> P2 -> P3,List(a, b),TFunCall(P3,T
                                                  //| VarRef(int -> int -> int,+),List(TVarRef(P1,a), TVarRef(P2,b))))
}