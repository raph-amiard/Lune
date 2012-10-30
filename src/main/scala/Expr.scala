package main.scala

import scala.collection.immutable.Map

class Arg
case class SimpleArg(str : String) extends Arg
case class TupleMatch(args: List[Arg]) extends Arg

case class ValInt(v: Int) extends Expr
case class ValString(v: String) extends Expr
case class ValBool(v: Boolean) extends Expr
case class ValDouble(v: Double) extends Expr
case object ValUnit extends Expr
case class VarRef(id: String) extends Expr
case class FunCall(fun: Expr, args: List[Expr]) extends Expr
case class FunDef(args: List[Arg], body: Expr) extends Expr
case class LetBind(name: String, expr: Expr, body: Expr) extends Expr
case class Def(name: String, expr: Expr) extends Expr
case class IfExpr(cond: Expr, body: Expr, alt:Expr) extends Expr
case class Tuple(exprs : List[Expr]) extends Expr

case class TypeDef(name: String, ptype_bindings: List[String], t: Expr) extends Expr
case class NamedTypeExpr(n : String) extends Expr
case class ParametricTypeInst(tn : String, ts : List[Expr]) extends Expr
case class ProductTypeExpr(ts : List[Expr]) extends Expr
case class UnionTypeExpr(ts : Map[String, Expr]) extends Expr

// Expression type definition
class Expr 
