package main.scala

import scala.collection.immutable.Map

case class ValInt(v: Int) extends Expr
case class ValString(v: String) extends Expr
case class ValBool(v: Boolean) extends Expr
case class ValDouble(v: Double) extends Expr
case class VarRef(id: String) extends Expr
case class FunCall(fun: Expr, args: List[Expr]) extends Expr
case class FunDef(args: List[String], body: Expr) extends Expr
case class LetBind(name: String, expr: Expr, body: Expr) extends Expr
case class Def(name: String, expr: Expr) extends Expr
case class IfExpr(cond: Expr, body: Expr, alt:Expr) extends Expr

// Expression type definition
class Expr {
}
