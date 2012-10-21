package main.scala
import scala.util.parsing.combinator.RegexParsers

object LuneParser extends RegexParsers {
  
  def varref = T_ID ^^ (s => {println("VAR_REF", s);VarRef(s)})
  def number = T_NUMBER ^^ ( s =>
    if (s.find {_ == '.'} isEmpty) ValInt(s.toInt) 
    else ValDouble(s.toDouble)
  )
  
  def basic_expr = number | varref
  def paren_expr = "(" ~> expr <~ ")"
  def p_expr = basic_expr | paren_expr
  def app = (p_expr+) ^^ {
    case h :: Nil => h
    case h :: t => FunCall(h, t)
  }
  
  def let : Parser[Expr] = (("let" ~> (T_ID+) <~ "=") ~! expr <~ "in") ~! expr ^^ {
    case ids ~ e1 ~ e2 => if (ids.length == 1) LetBind(ids(0), e1, e2)
    					  else LetBind(ids(0), FunDef(ids.tail, e1), e2)
  }
  
  def defst : Parser[Expr] = ("def" ~> (T_ID+) <~ "=") ~! expr ^^ {
    case ids ~ e1 => if (ids.length == 1) Def(ids(0), e1)
    				 else Def(ids(0), FunDef(ids.tail, e1))
  }
  
  def lambda : Parser[Expr] = (("fun" ~> (T_ID+)) <~ "->") ~ expr ^^ {
    case ids ~ e1 => FunDef(ids, e1)
  }
  
  def ifxp = (("if" ~> expr <~ "then") ~ expr <~ "else") ~ expr ^^ {
    case cond ~ body ~ alt => IfExpr(cond, body, alt)
  }
  
  def expr : Parser[Expr] = let | lambda | ifxp | app
  def toplevel = defst | expr
  
  def KWS = """(let|in|=|fun|->|if|then|else|def)"""
  
  def T_NUMBER = """^[-+]?[0-9]+(\.[0-9]+)?""".r  
  def T_ID : Parser[String] = """[a-zA-Z\_\+\-\/\|\>\<\=\*]+""".r ^? {
    case x if !(x.matches(KWS)) => x
  }
  
  def apply(input: String): Expr = parseAll(toplevel, input) match {
    case Success(result, _) => result
    case failure : NoSuccess => scala.sys.error(failure.msg)
  }
}