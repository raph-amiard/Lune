package main.scala

import scala.collection.immutable.Map

class Arg
case class SimpleArg(str : String) extends Arg
case class TupleMatch(args: List[Arg]) extends Arg

class MatchBranchExpr extends Expr
case class MatchExpr(to_match : Expr, match_clauses : List[MatchClause]) extends Expr
case class MatchClause(match_expr : MatchBranchExpr, expr : Expr) extends Expr

case class ConsMatchExpr(cons : String, vars : List[MatchBranchExpr]) extends MatchBranchExpr {
  override def toString() =
    cons + "(" + (vars mkString ", ") + ")"
}

case class TupleMatchExpr(vars : List[MatchBranchExpr]) extends MatchBranchExpr {
  override def toString() = 
    "(" + (vars mkString ", ") + ")"
}
case class SimpleMatchExpr(v : String) extends MatchBranchExpr {
  override def toString() = v
}

case class ValInt(v: Int) extends Expr
case class ValString(v: String) extends Expr
case class ValBool(v: Boolean) extends Expr
case class ValDouble(v: Double) extends Expr
case object ValUnit extends Expr

case class VarRef(id: String) extends Expr
case class FunCall(fun: Expr, args: List[Expr]) extends Expr
case class FunDef(args: List[Arg], body: Expr) extends Expr
case class LetBind(name: String, expr: Expr, body: Expr) extends Expr
case class DoBlock(exprs: List[Expr]) extends Expr
case class Val(name: String, expr: Expr) extends Expr
case class IfExpr(cond: Expr, body: Expr, alt:Expr) extends Expr
case class Tuple(exprs : List[Expr]) extends Expr

case class TypeDef(name: String, ptype_bindings: List[String], t: Expr) extends Expr
case class TypeDefs(td : List[TypeDef]) extends Expr
case class NamedTypeExpr(n : String) extends Expr
case class ParametricTypeInst(tn : String, ts : List[Expr]) extends Expr
case class ProductTypeExpr(ts : List[Expr]) extends Expr
case class FunctionTypeExpr(ts : List[Expr]) extends Expr
case class SumTypeExpr(ts : List[(String, Expr)]) extends Expr

case class TypeClassFunExpr(name : String, t : FunctionTypeExpr) extends Expr
case class TypeClassDef(name : String, type_binding : String, funs : List[TypeClassFunExpr]) extends Expr

case class InstanceDef(classname : String, type_expr : Expr, funs : List[InstanceFunExpr]) extends Expr
case class InstanceFunExpr(name : String, fun : FunDef) extends Expr

// Expression type definition
class Expr 
