package main.scala

// TExpr and TypedExpr type definitions
class TypedExpr(t: Type) {
  val typ = t
  def typeSubst(te : TypeEnv) : TypedExpr = this
} 


case class TValInt(v: Int) extends TypedExpr(TypeInt) { override def toString() = v.toString }
case class TValString(v: String) extends TypedExpr(TypeString) { override def toString() = "\"" + v + "\"" }
case class TValBool(v: Boolean) extends TypedExpr(TypeBool) { override def toString() = v.toString }
case class TValDouble(v: Double) extends TypedExpr(TypeDouble) { override def toString() = v.toString }
case object TValUnit extends TypedExpr(TypeUnit) { override def toString() = "()" }


case class TVarRef(_type: Type, id: String) extends TypedExpr(_type) {
  override def typeSubst(te : TypeEnv) : TVarRef =
    TVarRef(typ.concretize(te), id)
  override def toString() = id
}


case class TFunCall(_type: Type, fun: TypedExpr, args: List[TypedExpr]) extends TypedExpr(_type) {
  override def typeSubst(te : TypeEnv) : TFunCall = 
    TFunCall(typ.concretize(te), fun.typeSubst(te), args.map(_.typeSubst(te)))
    
  override def toString() = "(" + (fun +: args).map(_.toString).mkString(" ") + ") : " + _type
}


case class TFunDef(_type: TypeFunction, args: List[Arg], body: TypedExpr) extends TypedExpr(_type) {
  override def typeSubst(te : TypeEnv) : TFunDef = {
    println("IN  TFUNDEF : _type = " + _type)
    val _nt = _type.concretize(te)
    val n = TFunDef(_nt, args, body.typeSubst(te))
    println("OUT TFUNDEF : _type = "  + _nt)
    println("OUT TFUNDEF : " + n)
    n
  }
    
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


case class TDoBlock(_type: Type, exprs: List[TypedExpr]) extends TypedExpr(_type) {
  
  override def typeSubst(te : TypeEnv) : TDoBlock =
    TDoBlock(_type.concretize(te), exprs map (_.typeSubst(te)))

  override def toString() =
    "do " + (exprs mkString "; ") + " end"
    
}


case class TVal(_type: Type, name: String, expr: TypedExpr) extends TypedExpr(_type) {

  override def typeSubst(te : TypeEnv) : TVal =
    TVal(_type.concretize(te), name, expr.typeSubst(te))

  override def toString() =
    "val (" + name + " : " + _type + ") = " + expr.toString

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
case class TAbstractType(_type : AbstractType) extends TypedExpr(TypeUnit)


case class TMatchExpr(_type : Type, to_match : TypedExpr, match_clauses : List[TMatchClause]) extends TypedExpr(_type) {
  override def typeSubst(te : TypeEnv) : TMatchExpr =
    TMatchExpr(_type.concretize(te), to_match.typeSubst(te), match_clauses.map(_.typeSubst(te)))
  
  override def toString() =
    "match " + to_match + " with " + (match_clauses mkString " | ")
}


case class TMatchClause(_type : Type, match_expr : MatchBranchExpr, expr : TypedExpr) extends TypedExpr(_type){
  override def typeSubst(te : TypeEnv) : TMatchClause =
    TMatchClause(_type.concretize(te), match_expr, expr.typeSubst(te))
    
  override def toString() =
    match_expr + " -> " + expr
}


case class TInstanceDef(_type : Type, classname : String, funs : List[TInstanceFunExpr]) extends TypedExpr(_type)
case class TInstanceFunExpr(_type : Type, name : String, fun : TFunDef) extends TypedExpr(_type)
case class TTypeClassDef(name : String, funs : Map[String, TypeFunction]) extends TypedExpr(TypeUnit)