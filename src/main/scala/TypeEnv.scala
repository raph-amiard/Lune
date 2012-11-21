package main.scala

import scala.collection.immutable.HashMap
import scala.collection.immutable.Map
import scala.collection.immutable.HashSet

class TypeClass(val name : String, val ptype : TypePoly, val funcs : Map[String, TypeFunction]) {
  def getFuncs() = {
    val (fresh_ptype, ctx) = ptype.getFresh(new HashMap[Type, Type])
    (funcs map { case (n, tf) => (n, tf.getFresh(ctx)._1) }).toMap
  }
  
  def getFuncsWithClassConstraint() = {
    val ctx : HashMap[Type, Type] = HashMap(ptype -> new TypePoly(Set(name)))
    (funcs map { case (n, tf) => (n, tf.getFresh(ctx)._1) }).toMap
  }
}

object TypeEnv {
  
  val tpoly = new TypePoly()
  
  type VarMap = Map[String, AbstractType]
  type TypeMap = HashMap[Type, Type]
  type AliasMap = HashMap[String, AbstractType]
  type TypeConsMap = HashMap[String, SumType]
  type ConstraintsMap = HashMap[Type, Set[String]]
  type ClassImplMap = HashMap[Type, Set[String]]
  type TypeClassesMap = HashMap[String, TypeClass]
  
  val default_varmap : VarMap = Map(
    "+" -> TypeFunction(List(TypeInt, TypeInt, TypeInt)),
    "*" -> TypeFunction(List(TypeInt, TypeInt, TypeInt)),
    "-" -> TypeFunction(List(TypeInt, TypeInt, TypeInt)),
    "/" -> TypeFunction(List(TypeInt, TypeInt, TypeInt)),
    "=="-> new TypeMold(TypeFunction(List(tpoly, tpoly, TypeBool)))
  )
  
}

case class TypeEnv(varmap : TypeEnv.VarMap,
			       tmap : TypeEnv.TypeMap,
			       amap : TypeEnv.AliasMap,
			       tconsmap : TypeEnv.TypeConsMap,
			       ctypename : Option[String],
			       match_type : Option[Type],
			       in_type_def : Boolean,
			       constraints_map : TypeEnv.ConstraintsMap,
			       class_impl_map : TypeEnv.ClassImplMap,
			       typeclasses_map : TypeEnv.TypeClassesMap,
			       types_being_defined : HashMap[String, PlaceHolderType]) {
  
  def this() = this(TypeEnv.default_varmap, 
		  			new TypeEnv.TypeMap, 
		  			new TypeEnv.AliasMap, 
		  			new TypeEnv.TypeConsMap, 
		  			None, None, false, 
		  			new HashMap[Type, HashSet[String]],
		  			new HashMap[Type, HashSet[String]],
		  			new HashMap[String, TypeClass],
		  			new HashMap[String, PlaceHolderType])
  
  def copyWith(varmap : TypeEnv.VarMap = varmap,
		  	   tmap : TypeEnv.TypeMap = tmap,
		  	   amap : TypeEnv.AliasMap = amap,
		  	   tconsmap : TypeEnv.TypeConsMap = tconsmap,
		  	   ctypename : Option[String] = ctypename,
		  	   match_type : Option[Type] = match_type,
		  	   in_type_def : Boolean = in_type_def,
		  	   constraints_map : TypeEnv.ConstraintsMap = constraints_map,
		  	   class_impl_map : TypeEnv.ClassImplMap = class_impl_map,
		  	   typeclasses_map : TypeEnv.TypeClassesMap = typeclasses_map,
		  	   types_being_defined : HashMap[String, PlaceHolderType] = types_being_defined) = 
    new TypeEnv(varmap, tmap, amap, tconsmap, ctypename, 
                match_type, in_type_def, constraints_map, 
                class_impl_map, typeclasses_map, types_being_defined)
  
  def withTypeDef() = copyWith(in_type_def = true)
  
  def withTmpType(name : String, ptype : AbstractType) = 
    copyWith(types_being_defined + (name -> ptype))
  
  def withTypeClass(name : String, tc : TypeClass) = 
    copyWith(typeclasses_map = typeclasses_map + (name -> tc))
  
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
  
  def withClassImpl(t : Type, tclass : String) = {
    val class_impls = class_impl_map.getOrElse(t, Set()) + tclass
    copyWith(class_impl_map = class_impl_map + (t -> class_impls))
  }
  
  def withClassConstraints(t1 : TypePoly, t2: Type) : TypeEnv = {
    val t1_constraints = constraints_map.getOrElse(t1, t1.classes)
    t2 match {
      case tp2 : TypePoly => {
	    val t2_constraints = constraints_map.getOrElse(tp2, tp2.classes)
	    val total_constraints = t1_constraints.union(t2_constraints)
	    copyWith(constraints_map = constraints_map + (t1 -> total_constraints) + (t2 -> total_constraints))
      }
      case _ => {
        val missing_classes = t1_constraints.diff(class_impl_map.getOrElse(t2, Set()))
        if (missing_classes.isEmpty) this
        else throw new Exception("Non respected type classes constraints : " + missing_classes)
      }
    }
  }
  
  /* Simplify the mappings of polytypes so that
   * all type paths are of length 1
   * if you have the following mapping :
   * A -> B, B -> C
   * you get the following
   * A -> C, B -> C
   */
  def simplify() : TypeEnv = {
    var has_changed = true
    var ntmap = tmap
    while (has_changed) {
      has_changed = false
      var newmappings = ntmap
      ntmap foreach { 
        case (t1, t2) if ntmap contains t2 => {
          newmappings = newmappings + (t1 -> ntmap(t2))
          has_changed = true
        }
        case _ => 
	  }
      ntmap = newmappings
    }
    copyWith(tmap = ntmap)
  }
  
   def unifyVar(v : String, t : Type) = unifyTypes(getVarType(v), t)
  
  /* Unify types t1 and t2. It means that refering
   * to one or the other has the same meaning
   * This function handle type checking (checking that
   * t1 and t2 are indeed unifiable or not)
   */
  def unifyTypes(t1: Type, t2: Type, cu_sum_type : Option[SumType] = None) : TypeEnv = {
    println("IN UNIFY ", t1, t2)
    
    def chain_unify(tpoly: TypePoly, tpoly_binding: Type) = 
      if (tpoly == tpoly_binding) this
      else if (tmap.contains(tpoly)) unifyTypes(tmap(tpoly), tpoly_binding, cu_sum_type)
      else if (tmap.contains(tpoly_binding)) unifyTypes(tpoly, tmap(tpoly_binding), cu_sum_type)
      else this.withTypeMapping(tpoly, tpoly_binding).withClassConstraints(tpoly, tpoly_binding)
	  
    t1 match {
      
      case TypePrim() => t2 match {
        case TypePrim() => 
          if (t1 != t2) throw new Exception("Got type " + t2 + " where type " + t1 + " was expected")
          else this
          
        case tp2 : TypePoly => chain_unify(tp2, t1)
        case TypeFunction(_) => throw new Exception("Can't unify Prim type and function type")
        case ProductType(_) => throw new Exception("Can't unify Prim type and tuple type")
        case SumType(_, _) => throw new Exception("Can't unify Prim type and sum type")
      }
      
      case tp1 : TypePoly => chain_unify(tp1, t2)
      
      case ProductType(ts1) => t2 match {
        case tp2 : TypePoly => chain_unify(tp2, t1)
        case ProductType(ts2) => 
          if (ts1.length != ts2.length) throw new Exception("Incompatible tuple types")
          else {
            var ntm = this
            (ts1 zip ts2).map { case (t1, t2) => ntm = ntm.unifyTypes(t1, t2, cu_sum_type) }
            ntm
          }
        case _ => throw new Exception("Incompatible types")
      }
        
      case tf1 : TypeFunction => t2 match {
        case ProductType(_) => throw new Exception("Can't unify function type and tuple")
        case tp2 : TypePoly => chain_unify(tp2, t1)
        case TypePrim() => throw new Exception("Can't unify Prim type and function type")
        case tf2 : TypeFunction => {
          val (t1, tail1)  = tf1.curry
          val (t2, tail2)  = tf2.curry
          this.unifyTypes(t1, t2, cu_sum_type).unifyTypes(tail1, tail2, cu_sum_type)
        }
        case _ => throw new Exception("Incompatible types")
      }
      
      case SumType(name, ts) => t2 match {
        case tp2 : TypePoly => chain_unify(tp2, t1)
        case SumType(name2, ts2) =>
          if (name == name2) cu_sum_type match {
            case Some(st) if st.name == name2 => this
            case _ => (ts.values zip ts2.values).foldLeft(this)((tenv, ts) => ts match {
              case (tt1, tt2) => tenv.unifyTypes(tt1, tt2, Some(t1.asInstanceOf[SumType]))
            })
          }
          else throw new Exception("Incompatible sum types")
        case _ => throw new Exception("Incompatible types")
      }
      
    }
  }

  override def toString() = 
    "{VARMAP : " + varmap.toString + " TYPEMAP : " + tmap.toString + "}"
  
}