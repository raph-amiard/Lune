package main.scala
import scala.util.parsing.combinator.RegexParsers
import scala.collection.immutable.HashMap

object LuneParser extends RegexParsers {
  
  def matchExc = {throw new Exception("Match exception that shouldn't happen")}
  
  override val whiteSpace = """[ \t]+""".r

  // **************************************************
  // *            EXPRESSIONS DEFINITIONS             *
  // **************************************************
  
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

  def valst : Parser[Expr] = ("val" ~> T_ID <~ "=") ~! expr ^^ {
    case id ~ e1 => Val(id, e1)
  }
  
  def val_function_st : Parser[Expr] = ("function" ~> (T_ID ~ fundeflist) <~ "->") ~! expr ^^ {
    case id ~ args ~ e1 => Val(id, FunDef(args, e1))
  }
  
  def lambda : Parser[Expr] = (("fn" ~> fundeflist) <~ "->") ~ expr ^^ {
    case args ~ e1 => FunDef(args, e1)
  }

  def ifxp = (("if" ~> expr <~ "then") ~ expr <~ "else") ~ expr ^^ {
    case cond ~ body ~ alt => IfExpr(cond, body, alt)
  }
  
  // **************************************************
  // *       MATCH CLAUSE PARSERS DEFINITIONS         *
  // **************************************************
  
  def match_clause_cons = T_ID ~ opt("(" ~> rep1sep(match_clause_expr, ",") <~ ")") ^^ {
    case id ~ Some(matches) => ConsMatchExpr(id, matches)
    case id ~ None => SimpleMatchExpr(id)
  }
  def match_clause_tuple = "(" ~> rep1sep(match_clause_expr, ",") <~ ")" ^^ (TupleMatchExpr(_))
  def match_clause_expr : Parser[MatchBranchExpr] = (match_clause_tuple | match_clause_cons)
  def match_expr_branch = ((match_clause_expr) <~ "->") ~! expr ^^ {
    case mce ~ expr => MatchClause(mce, expr)
  }
  def match_expr = (("match" ~> p_expr <~ "with") <~ (T_SEP?)) ~! rep1sep(match_expr_branch, T_SEP) <~ ((T_SEP?) ~ "end") ^^ {
    case e ~ branches => MatchExpr(e, branches)
  }
  
  def expr : Parser[Expr] = let | lambda | ifxp | match_expr | app | do_block
  def toplevel = valst | val_function_st | typedef | typeclassdecl | instancedecl | expr 
  def toplevel_all = "\n*".r ~> rep1sep(toplevel, T_SEP) <~ "\n*".r ^^ (expr_list => {
    var ctypedefs : List[TypeDef] = List()
    val new_exprs = expr_list flatMap {
      case td : TypeDef => {
        ctypedefs = ctypedefs :+ td
        List()
      }
      case expr => if (ctypedefs.isEmpty) List(expr) else {
        val typedefs = TypeDefs(ctypedefs)
        ctypedefs = List()
        List(typedefs, expr)
      }
    }
    
    if (ctypedefs.isEmpty) new_exprs else (new_exprs :+ TypeDefs(ctypedefs))
  })
  
  def do_block_insts = valst | val_function_st | expr
  def do_block = ("do" ~! (T_SEP?)) ~> rep1sep(do_block_insts, T_SEP) <~ ((T_SEP?) ~! "end") ^^ {
    case list_exprs => DoBlock(list_exprs)
  }

  // **************************************************
  // *           TOKENS PARSERS DEFINITIONS           *
  // **************************************************
  
  def KWS = """(instance|class|with|let|in|=|fun|->|if|then|else|def|\||union|of|end|do)"""

  def T_NUMBER = """^[-+]?[0-9]+(\.[0-9]+)?""".r  
  def T_ID : Parser[String] = """[a-zA-Z\_\+\-\/\|\>\<\=\*][a-zA-Z\_\+\-\/\|\>\<\=\*0-9]*""".r ^? {
    case x if !(x.matches(KWS)) => x
  }
  
  def TKWS = """(instance|class|with|let|in|=|fun|->|if|then|else|def|\*|\||\-\>|union|of|end|do)"""
  def TT_ID : Parser[String] = """[a-zA-Z\_\+\-\/\|\>\<\=\*][a-zA-Z\_\+\-\/\|\>\<\=\*0-9]*""".r ^? {
    case x if !(x.matches(TKWS)) => x
  }
  def T_SEP = "(\n*|;)".r
  
  // **************************************************
  // *           TYPES PARSERS DEFINITIONS            *
  // **************************************************
  
  def basic_named_type = TT_ID ^^ (NamedTypeExpr(_))
  def named_type_param = basic_named_type | ("(" ~> type_expr <~ ")")
  def named_type = (TT_ID ~! (named_type_param*)) ^^ {
    case id ~ texprs => if (texprs.isEmpty) NamedTypeExpr(id)
    					else ParametricTypeInst(id, texprs)
  }
  def paren_type_expr : Parser[Expr] = named_type | ("(" ~> type_expr <~ ")")
  def tuple_type_expr : Parser [Expr] = rep1sep(paren_type_expr, "*") ^^ (x => if (x.length == 1) x(0) else ProductTypeExpr(x))
  def type_expr = tuple_type_expr | fun_type_expr
  
  def sum_type_branch = (TT_ID <~ "->") ~ type_expr ^^ { case id ~ texpr => (id, texpr) }
  def sum_type_body = rep1sep(sum_type_branch, T_SEP) ^^ (SumTypeExpr(_))
  
  def sum_type_def = ("union" ~> (TT_ID+) <~ "of" <~ (T_SEP?)) ~ sum_type_body <~ ((T_SEP?) ~ "end") ^^ {
    case (id :: ptypes) ~ sum_type => TypeDef(id, ptypes, sum_type)
  }
  def alias_type_def = ("alias" ~> (TT_ID+) <~ "of") ~ type_expr ^^ {
    case (id :: ptypes) ~ t => TypeDef(id, ptypes, t)
  }
  def typedef = sum_type_def | alias_type_def
  
  def fun_type_expr = "fun" ~> ("(" ~> rep1sep(paren_type_expr, "->") <~ ")") ^^ (FunctionTypeExpr(_))
  
  
  // **************************************************
  // *        TYPECLASSES PARSERS DEFINITIONS         *
  // **************************************************
  
  def typeclassdecl = ("class" ~> (TT_ID ~ TT_ID) <~ "with") ~ rep1sep(typeclassfundecl, T_SEP) <~ "end" ^^ {
    case id ~ ptype ~ fundecls => TypeClassDef(id, ptype, fundecls)
  }
  def typeclassfundecl = (T_ID <~ ":") ~ fun_type_expr ^^ {
    case id ~ ftexpr => TypeClassFunExpr(id, ftexpr)
  }
  
  def instancedecl = ("instance" ~> (TT_ID ~ type_expr) <~ "with") ~ rep1sep(instancefundecl, T_SEP) <~ "end" ^^ {
    case tclass ~ typ ~ fundecls => InstanceDef(tclass, typ, fundecls)
  }
  
  def instancefundecl = val_function_st ^^ {
    case Val(name, fundef : FunDef) => InstanceFunExpr(name, fundef)
  }

  def apply(input: String): List[Expr] = parseAll(toplevel_all, input) match {
    case Success(result, _) => result
    case failure : NoSuccess => scala.sys.error(failure.msg)
  }
}
