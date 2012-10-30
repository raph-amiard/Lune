package main.scala
import scala.util.parsing.combinator.RegexParsers
import scala.collection.immutable.HashMap

object LuneParser extends RegexParsers {

  def varref = T_ID ^^ (s => {println("VAR_REF", s);VarRef(s)})
  def number = T_NUMBER ^^ ( s =>
    if (s.find {_ == '.'} isEmpty) ValInt(s.toInt) 
    else ValDouble(s.toDouble)
  )

  def basic_expr = number | varref
  def tuple_body = repsep(expr, ",") ^^ ( x => { if (x.length == 1) x(0) else Tuple(x)})

  def paren_expr = "(" ~> tuple_body <~ ")"
  def p_expr = basic_expr | paren_expr

  def app = (p_expr+) ^^ {
    case h :: Nil => h
    case h :: t => FunCall(h, t)
  }

  def simple_arg = T_ID ^^ { SimpleArg(_) }
  def tuple_arg = "(" ~> repsep(arg_pattern, ",") <~ ")" ^^ { TupleMatch(_) }
  def arg_pattern : Parser[Arg] = simple_arg | tuple_arg 
  def fundeflist = arg_pattern+

  def let : Parser[Expr] = (("let" ~> (T_ID ~ (fundeflist?)) <~ "=") ~! expr <~ "in") ~! expr ^^ {
    case id ~ opt_args ~ e1 ~ e2 => opt_args match {
      case Some(args) => LetBind(id, FunDef(args, e1), e2)
      case None => LetBind(id, e1, e2)
    }
  }

  def defst : Parser[Expr] = ("def" ~> (T_ID ~ (fundeflist?)) <~ "=") ~! expr ^^ {
    case id ~ opt_args ~ e1 => opt_args match {
      case Some(args) => Def(id, FunDef(args, e1))
      case None => Def(id, e1)
    }
  }

  def lambda : Parser[Expr] = (("fun" ~> fundeflist) <~ "->") ~ expr ^^ {
    case args ~ e1 => FunDef(args, e1)
  }

  def ifxp = (("if" ~> expr <~ "then") ~ expr <~ "else") ~ expr ^^ {
    case cond ~ body ~ alt => IfExpr(cond, body, alt)
  }
  
  def expr : Parser[Expr] = let | lambda | ifxp | app
  def toplevel = defst | typedef | expr

  def KWS = """(let|in|=|fun|->|if|then|else|def)"""

  def T_NUMBER = """^[-+]?[0-9]+(\.[0-9]+)?""".r  
  def T_ID : Parser[String] = """[a-zA-Z\_\+\-\/\|\>\<\=\*][a-zA-Z\_\+\-\/\|\>\<\=\*0-9]*""".r ^? {
    case x if !(x.matches(KWS)) => x
  }
  
  def TKWS = """(let|in|=|fun|->|if|then|else|def|\*)"""
  def TT_ID : Parser[String] = """[a-zA-Z\_\+\-\/\|\>\<\=\*][a-zA-Z\_\+\-\/\|\>\<\=\*0-9]*""".r ^? {
    case x if !(x.matches(TKWS)) => x
  }
  
  def named_type = (TT_ID ~! (type_expr*)) ^^ {
    case id ~ texprs => if (texprs.isEmpty) NamedTypeExpr(id)
    					else ParametricTypeInst(id, texprs)
  }
    
  def paren_type_expr : Parser[Expr] = named_type | ("(" ~> type_expr <~ ")")
  def type_expr : Parser [Expr] = rep1sep(paren_type_expr, "*") ^^ (x => if (x.length == 1) x(0) else ProductTypeExpr(x))
    
  def sum_type_branch = ("|" ~> TT_ID <~ "of") ~ type_expr
  
  def typedef = ("type" ~> (TT_ID+) <~ "=") ~! type_expr ^^ {
    case (id :: ptypes) ~ texpr => TypeDef(id, ptypes, texpr)
  }
  

  def apply(input: String): Expr = parseAll(toplevel, input) match {
    case Success(result, _) => result
    case failure : NoSuccess => scala.sys.error(failure.msg)
  }
}
