package main.scala
import main.scala.Typing._
import main.scala.Interpreter._

object Main extends App {
  
  val varmap = VarMap.default
  /*println(LuneParser("let a = 2 in 5"))
  println(LuneParser("a b c d"))
  println(LuneParser("let add = fun a b -> + a b in add 1 2"))
  println(LuneParser("let fact a = if == a 0 then 1 else * a (fact (- a 1)) in fact 12"))
  */
  
  println(LuneParser("let fact a = if == a 0 then 1 else * a (fact (- a 1)) in fact 12").typecheck(varmap, new TypeMap()))
  
  println(LuneParser("+ 5 5").typecheck(varmap, new TypeMap)._1.typ)
  println(LuneParser("+ 5 5").typecheck(varmap, new TypeMap)._1.eval(Interpreter.prims))
  println(LuneParser("let a = 2 in if == a 2 then 12 else 14").typecheck(varmap, new TypeMap)._1.eval(Interpreter.prims))
  
  def readExpr() : String = {
    val input = readLine()
    if (input.endsWith(";;")) input.replace(";;", "")
    else input + "\n" + readExpr()
  }
  
  var repl_varmap = varmap
  var repl_typemap = new TypeMap
  var env = Interpreter.prims
  
  while (true) {
    print(" > ")
    System.out.flush()
    val input = readExpr()
    try {
      val (texpr, ntmap, nvmap) = LuneParser(input).typecheck(repl_varmap, repl_typemap)
      println(texpr)
      repl_varmap = nvmap
      repl_typemap = ntmap
      try {
        val v = texpr.eval(env)
        println("= " + v.toString)
      } catch {
        case e => println("Runtime error : " + e.getMessage())
      }
    } catch {
      case e => println("Type error : " + e.getMessage())
    }
  }
  
/*  
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
  
                               
  println(test_ast_6.typecheck(varmap, new TypeMap()))

  println(varmap.getType("="))
  val test_ast_7 = FunCall(VarRef("="), List(ValInt(5), ValInt(15)))
  println(test_ast_7.typecheck(varmap, new TypeMap()))

  val test_ast_8 = IfExpr(test_ast_7, 
                          FunCall(VarRef("="), List(ValString("lol"), ValString("lolz"))),
                          FunCall(VarRef("="), List(ValBool(true), ValBool(false))))

  val test_ast_9 = LetBind("fact",
                           FunDef(List("a"), 
                                  IfExpr(FunCall(VarRef("="), List(VarRef("a"), ValInt(0))),
                                         ValInt(1),
                                         FunCall(VarRef("fact"), 
                                                 List(FunCall(VarRef("-"), List(VarRef("a"), ValInt(1))))))),
                          FunCall(VarRef("fact"), List(ValInt(5))))

  println(test_ast_9.typecheck(varmap, new TypeMap()))
  */
}
