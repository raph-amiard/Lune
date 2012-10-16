package main.scala

object Main extends App {
  val test_ast =
	  FunDef(List("a","b"),
			 FunCall(FunCall(VarRef("+"),
				             List(VarRef("b"))), List(VarRef("a"))))
  val test_ast_2 = FunDef(List("a"), FunCall(VarRef("+"), List(VarRef("a"))))
  val test_ast_3 = FunDef(List("a", "b", "c"), FunCall(VarRef("a"), List(VarRef("b"), VarRef("c"))))
  val test_ast_4 = LetBind("a", FunDef(List("b", "c"), FunCall(VarRef("+"), List(VarRef("b"), VarRef("c")))),
		  						FunCall(VarRef("a"), List(ValInt(12), ValInt(15))))
  val test_ast_5 = FunCall(VarRef("+"), List(ValInt(12), ValInt(15)))
  val test_ast_6 = LetBind("a", test_ast_2,
		  						         FunCall(VarRef("a"), List(ValInt(12), ValInt(15))))
  
                                            
  val varmap = VarMap.default
  println(test_ast_6.typecheck(varmap, new TypeMap()))

  println(varmap.getType("="))
  val test_ast_7 = FunCall(VarRef("="), List(ValInt(5), ValInt(15)))
  println(test_ast_7.typecheck(varmap, new TypeMap()))

  val test_ast_8 = IfExpr(test_ast_7, 
                          FunCall(VarRef("="), List(ValString("lol"), ValString("lolz"))),
                          FunCall(VarRef("="), List(ValBool(true), ValBool(false))))
  println(test_ast_8.typecheck(varmap, new TypeMap()))
}
