package main.scala

import scala.collection.immutable.HashMap
import scala.collection.immutable.Map
import scala.collection.immutable.HashSet
import scala.collection.immutable.Set

sealed trait AbstractType {
  def get() : Type
}

class TypeMold(t: Type) extends AbstractType {
  override def get() = {
    val (nt, _) = t.getFresh(new HashMap[Type, Type]())
    nt
  }
  
  override def toString() = t.toString()
}

class ParametricType(var t : Option[Type], polytypes : List[Type]) extends AbstractType {
  
  def instanciate(ts : List[Type]) : Type = {
    if (ts.length != polytypes.length) 
      throw new Exception("Incorrect number of types for parametric type instanciation")
    val hh = polytypes.zip(ts).foldLeft(new HashMap[Type, Type])((h, tpair) => tpair match {
      case (t1, t2) => h + (t1 -> t2)
    })
    t.get.getFresh(hh)._1
  }
  
  def parametrize(ts : List[Type]) : AbstractType = new ParametrizedType(this, ts)
  
  override def get() = {
    val (nt, _) = t.get.getFresh(new HashMap[Type, Type]())
    nt
  }
  
  override def toString() = t.toString
    
}

object Type {
  type Ctx = HashMap[Type, Type]
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

class WrappedType(var t : Option[Type]) extends Type {
  override def concretize(type_env: TypeEnv) : Type = get.concretize(type_env)
  override def getFresh(ctx : Ctx) : (Type, Ctx) = get.getFresh(ctx)
  override def get() : Type = t.get
}

class ParametrizedType(ptype : ParametricType, ts : List[Type]) extends Type {
  var t : Option[Type]
  override def get() = {
    t match {
      case Some(ty) => ty
      case None => {
        t = Some(ptype.instanciate(ts))
        t.get
      }
    }
  }
  override def concretize(type_env: TypeEnv) : Type = get.concretize(type_env)
  override def getFresh(ctx : Ctx) : (Type, Ctx) = {
    val (nt, nctx) = get.getFresh(ctx)
  }
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

case class TypePoly(id: Int, classes : Set[String]) extends Type { 
  def this() = this(TypePoly.nextPoly(), new HashSet[String])
  def this(classes : Set[String]) = this(TypePoly.nextPoly(), classes)
  override def concretize(type_env : TypeEnv) : Type = type_env.bestType(this)
  override def toString() = "P" + id.toString + "(" + (classes mkString ",") + ")"
  override def getFresh(ctx : Ctx) = 
    if (ctx.contains(this)) (ctx(this), ctx)
    else {
      val ptype = new TypePoly(classes)
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

case class SumType(name : String, val ts : Map[String, Type]) extends Type {
  
  override def concretize(type_env : TypeEnv) : SumType =
    new SumType(name, ts.map { case (s, t) => (s, t.concretize(type_env))})
  
  override def getFresh(ctx : Ctx) : (SumType, Ctx) = {
    var cctx = ctx 
    val nst = new SumType(name, ts map {
      case (s, t) => {
        val (ntype, nctx) = t getFresh cctx
        cctx = nctx
        (s, ntype)
      }
    })
    (nst , cctx)
  }
  
  override def hashCode() : Int = name.hashCode()
  
  override def equals(that : Any) = that match {
    case SumType(nname, ts) => name == nname
    case _ => false
  }
  
  override def toString() = 
    (ts map { case (cons, typ) => cons + " of " + typ }) mkString " | "
    
}
