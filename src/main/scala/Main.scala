package main.scala
import main.java.lexparse.lunegrammarLexer
import main.java.lexparse.lunegrammarParser
import org.antlr.runtime.ANTLRStringStream
import org.antlr.runtime.tree.CommonTree
import org.antlr.runtime.CommonTokenStream

object Main extends App {
  val lex = new lunegrammarLexer(new ANTLRStringStream("def a b c = + b c"))
  val tkstream = new CommonTokenStream(lex)
  val parser : lunegrammarParser = new lunegrammarParser(tkstream)
  val tree : CommonTree = parser.top().getTree() match {
    case t : CommonTree => t
    case _ => throw new ClassCastException()
  }
  println(tree.getChildCount())
  println(tree.getText())
  println(tree.getChildren())
  for (i <- 0 until tree.getChildCount()) {
    val c = tree.getChild(i).asInstanceOf[CommonTree]
    println("TEXT", c.getText())
    println("TYPE", c.getType())
    println("TOKEN", c.getToken())
    println("PARENT", c.getParent())
    println("CHILDREN", c.getChildren())
  }
  println(tree.getChildren().get(1).asInstanceOf[CommonTree].getChildren())
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
  
                               
  val varmap = VarMap.default
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
