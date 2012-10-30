package main.scala

import scala.collection.immutable.HashMap
import scala.collection.immutable.Map

sealed trait AbstractType {
  def get() : Type
}

class TypeMold(t: Type) extends AbstractType {
  override def get() = {
    val (nt, _) = t.getFresh(new HashMap[Type, Type]())
    nt
  }
}

/* ==================================================
 * Basic class representing a type
 * =================================================*/
sealed trait Type extends AbstractType {
  type Ctx = HashMap[Type, Type]
  def concretize(type_env: TypeEnv) : Type = this
  def getFresh(ctx : Ctx) : (Type, Ctx) = (this, ctx)
  override def get() = this
}

/* ==================================================
 * Primitive Types definitions
 * =================================================*/
case class TypePrim() extends Type {
}
case object TypeInt extends TypePrim { override def toString() = "int"}
case object TypeString extends TypePrim { override def toString() = "string"}
case object TypeBool extends TypePrim { override def toString() = "bool"}
case object TypeDouble extends TypePrim { override def toString() = "double"}
case object TypeUnit extends TypePrim { override def toString() = "()"}

/* ==================================================
 * Type Poly type definition
 * a Poly Type is a type that is not bounded 
 * (doesn't have any concrete constraints)
 * =================================================*/
object TypePoly {
  var nPoly : Int = 0
  def nextPoly() = { nPoly = nPoly + 1; nPoly }
}

case class TypePoly(id: Int) extends Type { 
  def this() = this(TypePoly.nextPoly())
  override def concretize(type_env : TypeEnv) : Type = type_env.bestType(this)
  override def toString() = "P" + id.toString
  override def getFresh(ctx : Ctx) = 
    if (ctx.contains(this)) (ctx(this), ctx)
    else {
      val ptype = new TypePoly()
      (ptype, ctx + ((this, ptype)))
    }
}

/* ==================================================
 * TypeFunction type definition 
 * A function type is the type of any function
 * It can contain Poly and/or concrete types
 * =================================================*/
case class TypeFunction(ts: List[Type]) extends Type {
  
  assert(ts.length > 1)
  
  override def concretize(type_env : TypeEnv) : TypeFunction = new TypeFunction(ts.map(_.concretize(type_env)))
  
  def curry() : (Type, Type) = {
    if (ts.length == 2) (ts(0), ts(1)) 
    else (ts(0), new TypeFunction(ts.tail))
  }
  
  override def toString() = {
    ts.mkString(" -> ")
  }
  
  override def getFresh(ctx : Ctx) = {
    var cctx = ctx
    (new TypeFunction(ts.map(x => {
      val (ntype, nctx) = x.getFresh(cctx)
      cctx = nctx
      ntype
    })), cctx)
  }
}

/* ==================================================
 * ADTs
 * =================================================*/

case class ProductType(ts : List[Type]) extends Type {
  
  override def concretize(type_env : TypeEnv) : ProductType = 
    new ProductType(ts.map(_.concretize(type_env)))
  
  override def toString() =
    "(" + (ts mkString " * ") + ")" 
  
  override def getFresh(ctx : Ctx) = {
    var cctx = ctx
    (new ProductType(ts map (x => {
      val (ntype, nctx) = x getFresh cctx
      cctx = nctx
      ntype
    })), cctx)
  }
}

case class SumType(name: String, ts : HashMap[String, Type]) extends Type {
  
  override def concretize(type_env : TypeEnv) : SumType = 
    new SumType(name, ts.map { case (s, t) => (s, t.concretize(type_env))})
  
  override def getFresh(ctx : Ctx) = {
    var cctx = ctx
    (new SumType(name, ts map {
      case (s, t) => {
        val (ntype, nctx) = t getFresh cctx
        cctx = nctx
        (s, ntype)
      }
    }), cctx)
  }
  
  override def toString() = name
}