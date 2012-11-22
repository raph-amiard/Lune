package main.scala

import scala.collection.immutable.HashMap

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
	  def typesFromExprs(ts : List[Expr], te : TypeEnv = type_env) = ts.map(x => x.type_infer(te) match {
        case (TType(tt), _) => tt
        //case (TParTypeRef())
        case _ => throw new Exception("Can not happen")
	  })
	  
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
	      println("TFUN TYP", tfun.typ)
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
	          println("FUNCALL UNIFYTYPES ", tfun.typ, fun_type)
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
	    
	    case TypeDefs(defs) => {
	      
	      val defs_and_polys = defs map (t => t match {
	        case TypeDef(_, ptype_bindings, _) => (t, ptype_bindings.map(_ => new TypePoly))
	      })
	      
	      // Add temporary types to the environment, so that defined types can refer to each other
	      val with_tmptypes_env = defs_and_polys.foldLeft(type_env)((ntenv, typedef) => typedef match {
	        case (TypeDef(name, _ , t), polytypes) => {
	          val phtype = if (!polytypes.isEmpty) new ParametricType(None, polytypes)
			    	       else new WrappedType(None)
	          ntenv.withAlias(name, phtype)
	        }
	      })
	      
	      var ntenv = with_tmptypes_env
	      val types = defs_and_polys map {
	        case (TypeDef(name, ptype_bindings, t), polytypes) => {
	      
	          ntenv = (ptype_bindings zip polytypes).foldLeft(ntenv)((tenv, b) => b match {
	            case (s, t) => tenv.withAlias(s, t)
	          }).withTypeName(name)
	      
	      
	          t.type_infer(ntenv.withTypeDef) match {
	            case (TType(tt), nntenv) => {
	              println("IN TYPE INFER" , nntenv)
	              ntenv = nntenv
	              val subt = nntenv.amap(name)
	              subt match {
	                case subtt : ParametricType => subtt.t = Some(tt)
	                case subtt : WrappedType => subtt.t = Some(tt)
	                case _  =>
	              }
	              val ptype : AbstractType = if (polytypes.isEmpty) tt else subt
	              println("TYPE : " + ptype)
	              (name, ptype)
	            }
	            case _ => throw new Exception("CAN NOT HAPPEN")
			   }
	        }
	      }
	      
	      ntenv = types.foldLeft(ntenv)((ntenv, n_and_t) => n_and_t match {
	        case (name, ptype) => ntenv.withAlias(name, ptype)
	      })
	      
	      println("DAST ENV : ", ntenv)
	      
          (TValUnit, ntenv)
	    }
	    
	    case NamedTypeExpr(t) => (TType(t match {
	      case "int" => TypeInt
	      case "double" => TypeDouble
	      case "float" => TypeDouble
	      case "bool" => TypeBool
	      case "nil" => TypeUnit
	      case x => if (type_env.in_type_def) type_env.getAliasRaw(x).asInstanceOf[Type]
	                else type_env.getAlias(x)
	    }), type_env)
	    
	    case ParametricTypeInst(tn, ts) => {
	      val types = typesFromExprs(ts)
	      val ptype_instance = type_env.getAliasRaw(tn) match {
	        case t : ParametricType => if (type_env.in_type_def) t.parametrize(types) else t.instanciate(types)
	        case _ => throw new Exception("Trying to instanciate a non parametric type")
	      }
	      (TType(ptype_instance), type_env)
	    }

	    case ProductTypeExpr(ts) => (TType(ProductType(typesFromExprs(ts))), type_env)
	    case FunctionTypeExpr(ts) => (TType(TypeFunction(typesFromExprs(ts))), type_env)
	    
	    case SumTypeExpr(ts) => {
	      type_env.ctypename match {
	        case Some(name) => {
	          // Get constructor names and type expressions
	          val (conses, texprs) = ts.unzip
	          val types = typesFromExprs(texprs.toList, te = type_env)
	          val m = (conses zip types).toMap
	          val sum_type = SumType(name, m)
	          var ntenv = type_env
	          sum_type.ts foreach {
	            case (cons, typ) => {
	              val fntype = new TypeFunction(List(typ, sum_type))
	              // The type of the constructors need to be polymorphic
	              // in case of parametric sum types
	              ntenv = ntenv.withTCons(cons, sum_type)
	              			   .withVarToMold(cons, fntype)
	            }
	          }
	          (TType(sum_type), ntenv)
	        }
	        case None => throw new Exception("Can't have a sum type decl outside of a type def")
	      }
	    }
	      
	    case SimpleMatchExpr(v) => {
	      val ntenv = type_env.withVarToPoly(v)
	      (TValUnit, type_env.match_type match {
	        case Some(t) => ntenv.unifyVar(v, t)
	        case None => ntenv
	      })
	    }
	    
	    case TupleMatchExpr(vars) => {
          val polys = vars.map(_ => new TypePoly)
          val ttype = new ProductType(polys)
          val ntenv = (vars zip polys).foldLeft(type_env)((tenv, v) => v match {
            case (v, t) => v.type_infer(tenv.withMatchType(t))._2
          })
	      (TValUnit, type_env.match_type match {
	        case Some(match_type) => ntenv.unifyTypes(ttype, match_type)
	        case None => ntenv
	      })
	    }
	    
	    case ConsMatchExpr(cons, vars) => {
	      val sum_type = type_env.tconsmap(cons)
	    		  				 .getFresh(new HashMap[Type, Type])._1
	      val cons_type = sum_type.getTypeForCons(cons)
	      val ntenv = if (vars.length > 1) {
	        cons_type match {
	          case ProductType(types) => (vars zip types).foldLeft(type_env)((tenv, v) => 
	            v match { case (v, t) => v.type_infer(tenv.withMatchType(t))._2 }
	          )
	          case _ => throw new Exception("Bad mapping")
	        }
	      } else {
	        val var1 = vars(0)
	        var1.type_infer(type_env.withMatchType(cons_type))._2
	      }
	      val match_type = type_env.match_type.get
	      (TValUnit, ntenv.unifyTypes(sum_type, match_type))
	    }
	    
	    case MatchClause(match_expr, expr) => {
	      val (_, ntenv) = match_expr.type_infer(type_env)
	      val (texpr, ntenv2) = expr.type_infer(ntenv)
	      (TMatchClause(texpr.typ, match_expr, texpr), ntenv2)
	    }
	    
	    case MatchExpr(to_match, match_clauses) => {
	      val (tto_match, ntenv) = to_match.type_infer(type_env)
	      var tenv = ntenv
	      val ret_type = new TypePoly
	      val tmatch_clauses = match_clauses.map(x => {
	        val (tmatch_clause, ntenv) = x.type_infer(tenv.withMatchType(tto_match.typ))
	        tenv = ntenv.unifyTypes(tmatch_clause.typ, ret_type)
	        tmatch_clause.asInstanceOf[TMatchClause]
	      })
	      (TMatchExpr(ret_type, tto_match, tmatch_clauses), tenv)
	    }
	    
	    case TypeClassDef(name, type_binding, funs) => {
	      val tp = new TypePoly
	      val ntenv = type_env.withAlias(type_binding, tp)
	      val fun_types = typesFromExprs(funs.map(_.t), ntenv) map (_.asInstanceOf[TypeFunction])
	      val names = funs.map(_.name)
	      val mmap = names.zip(fun_types).toMap
	      val tc = new TypeClass(name, tp, mmap)
	      var tenv = ntenv
	      tc.getFuncsWithClassConstraint map {
	        case (n, ft) => tenv = tenv.withVarToMold(n, ft)
	      }
	      (TTypeClassDef(name, (names zip fun_types).toMap), tenv.withTypeClass(name, tc))
	    }
	    
	    case InstanceDef(classname, type_expr, funs) => {
	      val typ = typesFromExprs(List(type_expr))(0)
	      println("DAS TYPE IST " + typ)
	      var ntenv = type_env.withClassImpl(typ, classname)
	      val tclass_funcs = ntenv.typeclasses_map(classname).getFuncs
	      val tfuns = funs map {
	        case InstanceFunExpr(name, fun) => {
	          val (tfun, nte) = fun.type_infer(ntenv)
	          ntenv = nte.unifyTypes(tfun.typ, tclass_funcs(name)).simplify()
	          val tfun2 = tfun.typeSubst(ntenv)
	          TInstanceFunExpr(tfun2.typ, name, tfun2.asInstanceOf[TFunDef])
	        }
	      }
	      (TInstanceDef(typ, classname, tfuns), ntenv)
	      
	    }
      }
	}
  }
}
