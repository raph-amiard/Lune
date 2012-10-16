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
case class IfExpr(cond: Expr, body: Expr, alt:Expr) extends Expr

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
        val args_types = args.map(new_varmap.getType(_))
        val new_varmap = varmap.bindNames(args)
        val (texpr, tmap) = body.type_infer(new_varmap, typemap)
        val fun_type = new TypeFunction(args_types :+ texpr.typ)
        (TFunDef(fun_type, args, texpr), tmap)
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
        val nvarmap = varmap.bindName(name)
        val (texpr, tmap) = expr.type_infer(varmap, typemap)
        val ntmap = tmap.unify(nvarmap.getType(name), texpr.typ).simplify()
        val final_texpr = texpr.typeSubst(ntmap)
        val nvarmap2 = nvarmap.withMold(name, final_texpr.typ)
        val (tbody, ntmap2) = body.type_infer(nvarmap2, ntmap)
        println("IN LETBIND", final_texpr.typ, tbody.typ)
        (TLetBind(tbody.typ, name, final_texpr, tbody), ntmap2)
      }

      case IfExpr(cond, body, alt) => {
        val (tcond, tmap1) = cond.type_infer(varmap, typemap)
        val (tbody, tmap2) = body.type_infer(varmap, tmap1)
        val (talt, tmap3) = alt.type_infer(varmap, tmap2)
        val tmap = tmap3.unify(tbody.typ, talt.typ).unify(tcond.typ, TypeBool)
        (TIfExpr(tbody.typ, tcond, tbody, talt), tmap)
      }
	  }
	}
}
