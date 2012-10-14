package main.scala

// TExpr and TypedExpr type definitions
class TypedExpr(t: Type) {
  val typ = t
  def typeSubst(tm : TypeMap) : TypedExpr = this
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

case class TVarRef(_type: Type, id: String) extends TypedExpr(_type) {
  override def typeSubst(tm : TypeMap) : TVarRef =
    TVarRef(typ.concretize(tm), id)
  override def toString() = id
}

case class TFunCall(_type: Type, fun: TypedExpr, args: List[TypedExpr]) extends TypedExpr(_type) {
  override def typeSubst(tm : TypeMap) : TFunCall = 
    TFunCall(typ.concretize(tm), fun.typeSubst(tm), args.map(_.typeSubst(tm)))
    
  override def toString() = "(" + (fun +: args).map(_.toString).mkString(" ") + ")"
}

case class TFunDef(_type: TypeFunction, args: List[String], body: TypedExpr) extends TypedExpr(_type) {
  override def typeSubst(tm : TypeMap) : TFunDef = 
    TFunDef(_type.concretize(tm), args, body.typeSubst(tm))
    
  override def toString() =
    "fun (" + args.zip(_type.ts).map({
      case (arg, typ) => arg + " : " + typ.toString
    }).mkString(", ") + ") : " + _type.ts.last.toString + " => " + body.toString
    
}

case class TLetBind(_type: Type, name: String, expr: TypedExpr, body: TypedExpr) extends TypedExpr(_type) {
  override def typeSubst(tm : TypeMap) : TLetBind =
    TLetBind(_type.concretize(tm), name, expr.typeSubst(tm), body.typeSubst(tm))
  
  override def toString() =
    "let (" + name + " : " + _type + ") = " + expr.toString + " in " + body.toString
}