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
  
  def match_clause_cons = T_ID ~ opt("(" ~> rep1sep(match_clause_expr, ",") <~ ")") ^^ {
    case id ~ Some(matches) => ConsMatchExpr(id, matches)
    case id ~ None => SimpleMatchExpr(id)
  }
  def match_clause_tuple = "(" ~> rep1sep(match_clause_expr, ",") <~ ")" ^^ (TupleMatchExpr(_))
  def match_clause_expr : Parser[MatchBranchExpr] = (match_clause_tuple | match_clause_cons)
  def match_expr_branch = (("|" ~> match_clause_expr) <~ "->") ~! expr ^^ {
    case mce ~ expr => MatchClause(mce, expr)
  }
  def match_expr = ("match" ~> p_expr <~ "with") ~! rep1(match_expr_branch) ^^ {
    case e ~ branches => MatchExpr(e, branches)
  }

  def lambda : Parser[Expr] = (("fun" ~> fundeflist) <~ "->") ~ expr ^^ {
    case args ~ e1 => FunDef(args, e1)
  }

  def ifxp = (("if" ~> expr <~ "then") ~ expr <~ "else") ~ expr ^^ {
    case cond ~ body ~ alt => IfExpr(cond, body, alt)
  }
  
  def expr : Parser[Expr] = let | lambda | ifxp | match_expr | app
  def toplevel = defst | typedef | typeclassdecl | instancedecl | expr 

  def KWS = """(instance|class|with|let|in|=|fun|->|if|then|else|def|\|)"""

  def T_NUMBER = """^[-+]?[0-9]+(\.[0-9]+)?""".r  
  def T_ID : Parser[String] = """[a-zA-Z\_\+\-\/\|\>\<\=\*][a-zA-Z\_\+\-\/\|\>\<\=\*0-9]*""".r ^? {
    case x if !(x.matches(KWS)) => x
  }
  
  def TKWS = """(instance|class|with|let|in|=|fun|->|if|then|else|def|\*|\||\-\>)"""
  def TT_ID : Parser[String] = """[a-zA-Z\_\+\-\/\|\>\<\=\*][a-zA-Z\_\+\-\/\|\>\<\=\*0-9]*""".r ^? {
    case x if !(x.matches(TKWS)) => x
  }
  
  def basic_named_type = TT_ID ^^ (NamedTypeExpr(_))
  def named_type_param = basic_named_type | ("(" ~> type_expr <~ ")")
  def named_type = (TT_ID ~! (named_type_param*)) ^^ {
    case id ~ texprs => if (texprs.isEmpty) NamedTypeExpr(id)
    					else ParametricTypeInst(id, texprs)
  }
  def paren_type_expr : Parser[Expr] = named_type | ("(" ~> type_expr <~ ")")
  def tuple_type_expr : Parser [Expr] = rep1sep(paren_type_expr, "*") ^^ (x => if (x.length == 1) x(0) else ProductTypeExpr(x))
  def type_expr = tuple_type_expr | fun_type_expr
  def sum_type_branch = ("|" ~> TT_ID <~ "of") ~ type_expr ^^ { case id ~ texpr => (id, texpr) }
  def sum_type = rep1(sum_type_branch) ^^ (SumTypeExpr(_))
  def fun_type_expr = "fun" ~> ("(" ~> rep1sep(paren_type_expr, "->") <~ ")") ^^ (FunctionTypeExpr(_))
  def typedef_elem = ((TT_ID+) <~ "=") ~! (sum_type | type_expr) ^^ {
    case (id :: ptypes) ~ texpr => TypeDef(id, ptypes, texpr)
  }
  def typedef = "type" ~> rep1sep(typedef_elem, "and") ^^ {
    case defs => TypeDefs(defs)
  }
  
  def typeclassdecl = ("class" ~> (TT_ID ~ TT_ID)) ~ rep("with" ~> typeclassfundecl) ^^ {
    case id ~ ptype ~ fundecls => TypeClassDef(id, ptype, fundecls)
  }
  def typeclassfundecl = (T_ID <~ ":") ~ fun_type_expr ^^ {
    case id ~ ftexpr => TypeClassFunExpr(id, ftexpr)
  }
  
  def instancedecl = ("instance" ~> (TT_ID ~ type_expr)) ~ rep1("with" ~> instancefundecl) ^^ {
    case tclass ~ typ ~ fundecls => InstanceDef(tclass, typ, fundecls)
  }
  
  def instancefundecl = ((T_ID ~ fundeflist) <~ "=") ~ expr ^^ {
    case name ~ args ~ exp => InstanceFunExpr(name, FunDef(args, exp))
  }

  def apply(input: String): Expr = parseAll(toplevel, input) match {
    case Success(result, _) => result
    case failure : NoSuccess => scala.sys.error(failure.msg)
  }
}
