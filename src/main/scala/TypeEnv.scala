package main.scala

import scala.collection.immutable.HashMap
import scala.collection.immutable.Map

object TypeEnv {
  
  val tpoly = new TypePoly()
  
  type VarMap = Map[String, AbstractType]
  type TypeMap = HashMap[Type, Type]
  type AliasMap = HashMap[String, AbstractType]
  type TypeConsMap = HashMap[String, SumType]
  
  val default_varmap : VarMap = Map(
    "+" -> TypeFunction(List(TypeInt, TypeInt, TypeInt)),
    "*" -> TypeFunction(List(TypeInt, TypeInt, TypeInt)),
    "-" -> TypeFunction(List(TypeInt, TypeInt, TypeInt)),
    "=="-> new TypeMold(TypeFunction(List(tpoly, tpoly, TypeBool)))
  )
  
}

case class TypeEnv(varmap : TypeEnv.VarMap, 
			       tmap : TypeEnv.TypeMap, 
			       amap : TypeEnv.AliasMap, 
			       tconsmap : TypeEnv.TypeConsMap,
			       ctypename : Option[String],
			       match_type : Option[Type]) {
  
  def this() = this(TypeEnv.default_varmap, new TypeEnv.TypeMap, new TypeEnv.AliasMap, new TypeEnv.TypeConsMap, None, None)
  
  def copyWith(varmap : TypeEnv.VarMap = varmap,
		  	   tmap : TypeEnv.TypeMap = tmap,
		  	   amap : TypeEnv.AliasMap = amap,
		  	   tconsmap : TypeEnv.TypeConsMap = tconsmap,
		  	   ctypename : Option[String] = ctypename,
		  	   match_type : Option[Type] = match_type) = 
    new TypeEnv(varmap, tmap, amap, tconsmap, ctypename, match_type)
  
  def withMatchType(t : Type) = copyWith(match_type = Option(t))
  
  def withTypeName(name : String) = copyWith(ctypename = Option(name))
  
  def withTCons(s : String, typ : SumType) = 
    copyWith(tconsmap = tconsmap + (s -> typ))
  
  def withAliasToMold(name : String, t : Type) = copyWith(amap = amap + (name -> new TypeMold(t)))
  def withAliasToPoly(name : String) = copyWith(amap = amap + (name -> new TypePoly))
  def withAlias(name : String, t : AbstractType) = copyWith(amap = amap + (name -> t))
  def getAlias(name : String) = amap(name).get()
  def getAliasRaw(name : String) = amap(name)
  
  def getVarType(binding : String) = varmap(binding).get()

  def withVarToPoly(binding: String) : TypeEnv =
    copyWith(varmap = varmap + (binding -> new TypePoly))
    
  def withVarToType(binding : String, type_ : Type) : TypeEnv =
    copyWith(varmap = varmap + (binding -> type_))

  def withVarToMold(binding: String, t: Type) : TypeEnv = 
    copyWith(varmap = varmap + (binding -> new TypeMold(t)))
    
  def withTypeMapping(t1: Type, t2: Type) : TypeEnv = copyWith(tmap = tmap + (t1 -> t2))
  
  def bestType(t : Type) : Type = tmap.getOrElse(t, t)
  
  /* Simplify the mappings of polytypes so that
   * all type paths are of length 1
   * if you have the following mapping :
   * A -> B, B -> C
   * you get the following
   * A -> C, B -> C
   */
  def simplify() : TypeEnv = {
    var has_changed = false
    var te = this
    while (has_changed) tmap foreach { 
      case (t1, t2) => if (tmap contains t2) {
        te = te.withTypeMapping(t1, tmap(t2))
        has_changed = true
      }
	}
    te
  }
  
  def unifyVar(v : String, t : Type) = unifyTypes(getVarType(v), t)
  
  /* Unify types t1 and t2. It means that refering
   * to one or the other has the same meaning
   * This function handle type checking (checking that
   * t1 and t2 are indeed unifiable or not)
   */
  def unifyTypes(t1: Type, t2: Type) : TypeEnv = {
    println("IN UNIFY ", t1, t2)
    
    def chain_unify(tpoly: Type, tpoly_binding: Type) = 
      if (tmap.contains(tpoly)) unifyTypes(tmap(tpoly), tpoly_binding)
      else if (tmap.contains(tpoly_binding)) unifyTypes(tpoly, tmap(tpoly_binding))
      else this.withTypeMapping(tpoly, tpoly_binding)
	  
    t1 match {
      
      case TypePrim() => t2 match {
        case TypePrim() => 
          if (t1 != t2) throw new Exception("Got type " + t2 + " where type " + t1 + " was expected")
          else this
          
        case TypePoly(id) => chain_unify(t2, t1)
        case TypeFunction(_) => throw new Exception("Can't unify Prim type and function type")
        case ProductType(_) => throw new Exception("Can't unify Prim type and tuple type")
        case SumType(_, _) => throw new Exception("Can't unify Prim type and sum type")
      }
      
      case TypePoly(_) => chain_unify(t1, t2)
      
      case ProductType(ts1) => t2 match {
        case TypePoly(_) => chain_unify(t1, t2)
        case ProductType(ts2) => 
          if (ts1.length != ts2.length) throw new Exception("Incompatible tuple types")
          else {
            var ntm = this
            (ts1 zip ts2).map { case (t1, t2) => ntm = ntm.unifyTypes(t1, t2) }
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
          this.unifyTypes(t1, t2).unifyTypes(tail1, tail2)
        }
        case _ => throw new Exception("Incompatible types")
      }
      
      case SumType(name, ts) => t2 match {
        case TypePoly(_) => chain_unify(t2, t1)
        case SumType(name2, ts2) =>
          if (name == name2)
            (ts.values zip ts2.values).foldLeft(this)((tenv, ts) => ts match {
              case (tt1, tt2) => tenv.unifyTypes(tt1, tt2)
            })
          else throw new Exception("Incompatible sum types")
        case _ => throw new Exception("Incompatible types")
      }
      
    }
  }

  override def toString() = 
    "{VARMAP : " + varmap.toString + " TYPEMAP : " + tmap.toString + "}"
  
}