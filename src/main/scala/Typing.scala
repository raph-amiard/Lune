package main.scala

object Typing {

  implicit def autoTypingExpr(e : Expr) = new AutoTypingExpr(e)

  class AutoTypingExpr(e: Expr) {
    
	def typecheck(type_env : TypeEnv) : (TypedExpr, TypeEnv) = {
	  val (texpr, tenv) = this.type_infer(type_env)
	  val ntenv = tenv.simplify()
	  (texpr.typeSubst(ntenv), ntenv)
	}

	def type_infer(type_env : TypeEnv) : (TypedExpr, TypeEnv) = {
	  def withTypeEnv(t: TypedExpr) = (t, type_env)
	  
	  e match {
	    
	    case ValInt(v) => withTypeEnv(TValInt(v))
	    case ValString(v) => withTypeEnv(TValString(v))
	    case ValBool(v) => withTypeEnv(TValBool(v))
	    case ValDouble(v) => withTypeEnv(TValDouble(v))
	    case VarRef(id) => withTypeEnv(TVarRef(type_env.getVarType(id), id))

        case FunDef(args, body) => {
          var ntenv = type_env
          
          def build(arg : Arg) : Type = arg match {
            case SimpleArg(str) => {
              ntenv = ntenv withVarToPoly str
              ntenv getVarType str
            }
            case TupleMatch(args) => new ProductType(args.map(build))
          }
            
          val args_types = args.map(build)
          
          val (texpr, ntenv2) = body.type_infer(ntenv)
          val fun_type = new TypeFunction(args_types :+ texpr.typ)
          (TFunDef(fun_type, args, texpr), ntenv2)
	    }

	    case FunCall(fun, args) => {
	      println("IN FUNCALL", fun, args)
	      val (tfun, ntenv) = fun.type_infer(type_env)
	      tfun.typ match {
	        case TypePrim() => throw new Exception("Can't call a non function type")
	        case _ => {
	          println("IN MATCH", tfun.typ)
	          var tenv = ntenv
	          val typed_args = args.map(expr => {
	            val (texpr, ntenv) = expr.type_infer(tenv)
	            tenv = ntenv
	            texpr
	          })
	          val ret_type = new TypePoly()
	          val fun_type = new TypeFunction(typed_args.map(x => x.typ) :+ ret_type)
	          tenv = tenv.unifyTypes(tfun.typ, fun_type)
	          (TFunCall(ret_type, tfun, typed_args), tenv)
	        }
	      }
	    }

	    case LetBind(name, expr, body) => {
	      val ntenv = type_env withVarToPoly name
	      val (texpr, ntenv2) = expr.type_infer(ntenv)
	      val ntenv3 = ntenv2.unifyVar(name, texpr.typ).simplify()
	      val final_texpr = texpr.typeSubst(ntenv3)
	      val (tbody, ntenv4) = body.type_infer(ntenv3.withVarToMold(name, final_texpr.typ))
	      println("IN LETBIND", final_texpr.typ, tbody.typ)
	      (TLetBind(tbody.typ, name, final_texpr, tbody), ntenv4)
	    }
	    
	    case Def(name, expr) => {
	      val ntenv = type_env withVarToPoly name
	      val (texpr, ntenv2) = expr.type_infer(ntenv)
	      val ntenv3 = ntenv2.unifyVar(name, texpr.typ).simplify()
	      val final_texpr = texpr.typeSubst(ntenv3)
	      val ntenv4 = ntenv3.withVarToMold(name, final_texpr.typ)
	      (TDef(final_texpr.typ, name, final_texpr), ntenv4)
	    }

	    case IfExpr(cond, body, alt) => {
	      val (tcond, ntenv) = cond.type_infer(type_env)
	      val (tbody, ntenv2) = body.type_infer(ntenv)
	      val (talt, ntenv3) = alt.type_infer(ntenv2)
	      val ntenv4 = ntenv3.unifyTypes(tbody.typ, talt.typ).unifyTypes(tcond.typ, TypeBool)
	      (TIfExpr(tbody.typ, tcond, tbody, talt), ntenv4)
	    }
	    
	    case Tuple(exprs) => {
	      val (tenv, texprs) = exprs.foldLeft(type_env, List[TypedExpr]())((accu, expr) => accu match {
	        case (tenv, le) => {
		      val (txpr, ntenv) = expr.type_infer(tenv)
		      (ntenv, le :+ txpr)
	        }
	      })
	      (TTuple(ProductType(texprs.map(_.typ)), texprs), tenv)
	    }
	    
	    case TypeDef(name, ptype_bindings, t) => {
	      val tenv = ptype_bindings.foldLeft(type_env)((tenv, b) => tenv.withAliasToPoly(b))
	      t.type_infer(tenv) match {
	        case (TType(tt), ntenv) => (TValUnit, ntenv.withAliasToMold(name, tt))
	        case _ => throw new Exception("CAN NOT HAPPEN")
	      }
	    }
	    
	    case NamedTypeExpr(t) => (TType(t match {
	      case "int" => TypeInt
	      case "double" => TypeDouble
	      case "float" => TypeDouble
	      case "bool" => TypeBool
	      case "nil" => TypeUnit
	      case x => type_env.getAlias(x)
	    }), type_env)
	    
	    case ParametricTypeInst(tn, ts) => {
	      val types = ts.map(x => x.type_infer(type_env) match {
	        case (TType(tt), _) => tt
	        case _ => throw new Exception("Can not happen")
	      })
	    }
	    case ProductTypeExpr(ts) =>
	    case UnionTypeExpr(ts) =>
	    
	    
      }
	}
  }
}