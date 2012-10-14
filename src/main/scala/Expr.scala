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

// Expression type definition
class Expr {
	
	def typecheck(varmap: VarMap, typemap: TypeMap) : TypedExpr = {
	  val (texpr, tmap) = this.type_infer(varmap, typemap)
	  texpr.typeSubst(tmap.simplify())
	}
	
	def type_infer(varmap: VarMap, typemap: TypeMap) : (TypedExpr, TypeMap) = {
	  def withTMap(t: TypedExpr) = (t, typemap)
	  this match {
	    
	    case ValInt(v) => withTMap(TValInt(v))
	    case ValString(v) => withTMap(TValString(v))
		case ValBool(v) => withTMap(TValBool(v))
		case ValDouble(v) => withTMap(TValDouble(v))
		case VarRef(id) => withTMap(TVarRef(varmap.getType(id), id))
		
		case FunDef(args, body) => {
		  val new_varmap = varmap.bindNames(args)
		  val (texpr, new_typemap) = body.type_infer(new_varmap, typemap)
		  val args_types = args.map(new_varmap.getType(_))
		  (TFunDef(new TypeFunction(args_types :+ texpr.typ), args, texpr), new_typemap)
		}
		
		case FunCall(fun, args) => {
		  println("IN FUNCALL", fun, args)
		  val (tfun, ntmap) = fun.type_infer(varmap, typemap)
		  tfun.typ match {
		    case TypePrim() => throw new Exception("Can't call a non function type")
		    case _ => {
		      println("IN MATCH", tfun.typ)
		      var tmap = ntmap
			  val typed_args = args.map(expr => {
			    val (texpr, ntmap) = expr.type_infer(varmap, tmap)
			    tmap = ntmap
			    texpr
			  })
			  val ret_type = new TypePoly()
		      val fun_type = new TypeFunction(typed_args.map(x => x.typ) :+ ret_type)
			  tmap = tmap.unify(tfun.typ, fun_type)
			  (TFunCall(ret_type, tfun, typed_args), tmap)
		    }
		  }
		}
		  
	    case LetBind(name, expr, body) => {
		  val (texpr, tmap) = expr.type_infer(varmap, typemap)
		  val s_tmap = tmap.simplify()
		  val final_texpr = texpr.typeSubst(s_tmap)
		  val new_varmap = varmap.withMold(name, final_texpr.typ)
		  val (tbody, new_tmap) = body.type_infer(new_varmap, s_tmap)
		  (TLetBind(tbody.typ, name, final_texpr, tbody), new_tmap)
		}
	  }
	}
}
