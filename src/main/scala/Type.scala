package main.scala

import scala.collection.immutable.HashMap
import scala.collection.immutable.Map

sealed trait AbstractType {
  def get() : Type
}

class TypeMold(t: Type) extends AbstractType {
  override def get() = t.getFresh
}

/* ==================================================
 * Basic class representing a type
 * =================================================*/
sealed trait Type extends AbstractType {
  def concretize(typemap: TypeMap) : Type = this
  def getFresh() = this
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
  override def concretize(typemap: TypeMap) : Type = typemap.bestType(this)
  override def toString() = "P" + id.toString
  override def getFresh() = new TypePoly()
}

/* ==================================================
 * TypeFunction type definition 
 * A function type is the type of any function
 * It can contain Poly and/or concrete types
 * =================================================*/
case class TypeFunction(ts: List[Type]) extends Type {
  
  assert(ts.length > 1)
  
  override def concretize(typemap: TypeMap) : TypeFunction = new TypeFunction(ts.map(_.concretize(typemap)))
  
  def curry() : (Type, Type) = {
    if (ts.length == 2) (ts(0), ts(1)) 
    else (ts(0), new TypeFunction(ts.tail))
  }
  
  override def toString() = {
    ts.mkString(" -> ")
  }
  
  override def getFresh() = new TypeFunction(ts.map(_.getFresh))
}