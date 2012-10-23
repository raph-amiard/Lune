package main.scala

import scala.collection.immutable.HashMap
import scala.collection.immutable.Map

/* ==================================================
 * TypeMap class definition
 * A typemap is the structure that keep tracks of type 
 * mappings during type checking. The responsibility of 
 * checking correct type mappings and of simplifying 
 * type relations is also deferred to it
 * =================================================*/
class TypeMap(tmap : HashMap[Type, Type]) extends {
  
  def this() = this(new HashMap[Type, Type])
  
  override def toString() = typemap.toString
  
  val typemap : HashMap[Type, Type] = tmap
  def append(t1: Type, t2: Type) : TypeMap = new TypeMap(typemap + ((t1, t2)))
  
  def bestType(t : Type) : Type = typemap.getOrElse(t, t)
  
  /* Simplify the mappings of polytypes so that
   * all type paths are of length 1
   * if you have the following mapping :
   * A -> B, B -> C
   * you get the following
   * A -> C, B -> C
   */
  def simplify() : TypeMap = {
    var has_changed = false
    var tm = this
    while (has_changed) typemap foreach { 
      case (t1, t2) => if (typemap.contains(t2)) {
        tm = tm.append(t1, typemap(t2))
        has_changed = true
      }
	}
    tm
  }
  
  /* Unify types t1 and t2. It means that refering
   * to one or the other has the same meaning
   * This function handle type checking (checking that
   * t1 and t2 are indeed unifiable or not)
   */
  def unify(t1: Type, t2: Type) : TypeMap = {
    println("IN UNIFY ", t1, t2)
    
    def chain_unify(tpoly: Type, tpoly_binding: Type) = 
      if (typemap.contains(tpoly)) unify(typemap(tpoly), tpoly_binding)
      else if (typemap.contains(tpoly_binding)) unify(tpoly, typemap(tpoly_binding))
      else this.append(tpoly, tpoly_binding)
	  
    t1 match {
      
      case TypePrim() => t2 match {
        case TypePrim() => 
          if (t1 != t2) throw new Exception("Got type " + t2 + " where type " + t1 + " was expected")
          else this
          
        case TypePoly(id) => chain_unify(t2, t1)
        case TypeFunction(_) => throw new Exception("Can't unify Prim type and function type")
        case ProductType(_) => throw new Exception("Can't unify Prim type and tuple type")
      }
      
      case TypePoly(_) => chain_unify(t1, t2)
      
      case ProductType(ts1) => t2 match {
        case TypePoly(_) => chain_unify(t1, t2)
        case ProductType(ts2) => 
          if (ts1.length != ts2.length) throw new Exception("Incompatible tuple types")
          else {
            var ntm = this
            (ts1 zip ts2).map { case (t1, t2) => ntm = ntm.unify(t1, t2) }
            ntm
          }
        case _ => throw new Exception("Incompatible types")
      }
        
      
      case tf1 : TypeFunction => t2 match {
        case ProductType(_) => throw new Exception("Can't unify function type and tuple")
        case TypePoly(_) => chain_unify(t2, t1)
        case TypePrim() => throw new Exception("Can't unify Prim type and function type")
        case tf2 : TypeFunction => {
          val (t1, tail1)  = tf1.curry
          val (t2, tail2)  = tf2.curry
          this.unify(t1, t2).unify(tail1, tail2)
        }
      }
      
    }
  }
}
