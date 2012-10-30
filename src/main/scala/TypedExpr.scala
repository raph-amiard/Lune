package main.scala

// TExpr and TypedExpr type definitions
class TypedExpr(t: Type) {
  val typ = t
  def typeSubst(te : TypeEnv) : TypedExpr = this
} 
case class TValInt(v: Int) extends TypedExpr(TypeInt) {
  override def toString() = v.toString
}
case class TValString(v: String) extends TypedExpr(TypeString) {
  override def toString() = "\"" + v + "\""
}
case class TValBool(v: Boolean) extends TypedExpr(TypeBool) {
  override def toString() = v.toString
}
case class TValDouble(v: Double) extends TypedExpr(TypeDouble) {
  override def toString() = v.toString
}
case object TValUnit extends TypedExpr(TypeUnit) {
  override def toString() = "()"
}

case class TVarRef(_type: Type, id: String) extends TypedExpr(_type) {
  override def typeSubst(te : TypeEnv) : TVarRef =
    TVarRef(typ.concretize(te), id)
  override def toString() = id
}

case class TFunCall(_type: Type, fun: TypedExpr, args: List[TypedExpr]) extends TypedExpr(_type) {
  override def typeSubst(te : TypeEnv) : TFunCall = 
    TFunCall(typ.concretize(te), fun.typeSubst(te), args.map(_.typeSubst(te)))
    
  override def toString() = "(" + (fun +: args).map(_.toString).mkString(" ") + ")"
}

case class TFunDef(_type: TypeFunction, args: List[Arg], body: TypedExpr) extends TypedExpr(_type) {
  override def typeSubst(te : TypeEnv) : TFunDef = 
    TFunDef(_type.concretize(te), args, body.typeSubst(te))
    
  override def toString() =
    "fun (" + args.zip(_type.ts).map({
      case (arg, typ) => arg + " : " + typ.toString
    }).mkString(", ") + ") : " + _type.ts.last.toString + " => " + body.toString
    
}

case class TLetBind(_type: Type, name: String, expr: TypedExpr, body: TypedExpr) extends TypedExpr(_type) {
  override def typeSubst(te : TypeEnv) : TLetBind =
    TLetBind(_type.concretize(te), name, expr.typeSubst(te), body.typeSubst(te))
  
  override def toString() =
    "let (" + name + " : " + _type + ") = " + expr.toString + " in " + body.toString
}

case class TDef(_type: Type, name: String, expr: TypedExpr) extends TypedExpr(_type) {

  override def typeSubst(te : TypeEnv) : TDef =
    TDef(_type.concretize(te), name, expr.typeSubst(te))

  override def toString() =
    "def (" + name + " : " + _type + ") = " + expr.toString

}

case class TIfExpr(_type: Type, cond: TypedExpr, body: TypedExpr, alt: TypedExpr) extends TypedExpr(_type) {
  
  override def typeSubst(te : TypeEnv) : TIfExpr =
    TIfExpr(_type.concretize(te), cond.typeSubst(te), body.typeSubst(te), alt.typeSubst(te))
    
  override def toString() =
    "if " + cond + " then " + body + " else " + alt
    
}

case class TTuple(_type: Type, exprs : List[TypedExpr]) extends TypedExpr(_type) {
  
  override def typeSubst(te : TypeEnv) : TTuple = 
    TTuple(_type concretize te , exprs.map(_.typeSubst(te)))
    
  override def toString() = "(" + (exprs mkString ", ") + ")"
}

case class TType(_type : Type) extends TypedExpr(_type)