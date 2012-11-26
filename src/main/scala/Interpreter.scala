package main.scala

import scala.collection.mutable.HashMap

object Interpreter {
  import scala.collection.mutable.Map
  import scala.math.Numeric
  
  
  /* TYPECLASSES STUFF */
  // classes["foo"]["bar"][typ]

  var classes = new HashMap[String, HashMap[String, HashMap[Type, FuncValue]]]

  implicit def evalObject(e : TypedExpr) = new EvalObject(e)

  def make_constructor(t : Type, name : String) = (lv : List[Value]) => new ConstructorValue(t, name, lv(0))

  class Environnment (h : Map[String, Value], parent : Option[Environnment]) {

    def get(str : String) : Value = 
      h.getOrElse(str, parent match {
        case Some(p) => p.get(str)
        case None => throw new Exception("Unfound key " + str + " in env")
      })

    def addBinding(s : String, v : Value) = 
      if (!h.contains(s)) h += s->v
      else throw new Exception("Already bound var : " + s)

    def withBinding(s : String, v : Value) = new Environnment(Map(s->v), Option(this))

    def fresh() = new Environnment(Map(), Option(this))

    def withBindings(vars : List[String], vals : List[Value]) : Environnment = 
      new Environnment(Map() ++= (vars zip vals).toMap, Option(this))

    def addConstructors(te : TypeEnv) = 
      te.tconsmap foreach { case (x, t) => h += (x->new PrimFunc(1, make_constructor(t, x), te.varmap(x).get))}
  }

  type GNumOp = ((Int, Int) => Int,
		  		 (Double, Double) => Double)

  abstract class Value(v : Any, val t : Type) {
    override def toString() = v.toString

    def numop(g : GNumOp)(other : Value) : Value = {
      (this, other) match {
        case (IntValue(v1), IntValue(v2)) => IntValue(g._1(v1, v2))
        case (DoubleValue(v1), IntValue(v2)) => DoubleValue(g._2(v1, v2))
        case (IntValue(v1), DoubleValue(v2)) => DoubleValue(g._2(v1, v2))
        case (DoubleValue(v1), DoubleValue(v2)) => DoubleValue(g._2(v1, v2))
        case _ => throw new Exception("Can't numop on non nums")
      }
    }

  }

  case object UnitValue extends Value(null, TypeUnit) {
    override def toString() = "()"
  }
  case class IntValue(v : Int) extends Value(v, TypeInt)
  case class DoubleValue(v: Double) extends Value(v, TypeDouble)
  case class StringValue(v: String) extends Value(v, TypeString)
  case class BoolValue(v: Boolean) extends Value(v, TypeBool)
  case class TupleValue(_type : Type, v: List[Value]) extends Value(v, _type) {
    override def toString() = "(" + (v mkString ", ") + ")"
  }
  case class ConstructorValue(_type : Type, cons : String, v : Value) extends Value(v, _type) {
    override def toString() = cons + "(" + v + ")"
  }

  abstract class FuncValue(v : Any, _type : Type, args_vals : List[Value]) extends Value(v, _type) {

    def run(l : List[Value]) : Value
    def withArgs(args : List[Value]) : FuncValue
    def arity : Int

    def app(pvalues: List[Value]) : Value = {
      val (needed, rest) = pvalues.splitAt(arity - args_vals.length)
      val args = args_vals ++ needed
	    if (args.length < arity) this withArgs args
	    else this.run(args) match {
	      case f : FuncValue => f.app(rest)
	      case f : Value => f
	      case _ if rest.length > 0 => throw new Exception("Shouldn't happen")
	    }
    }

  }

  case class NativeFunc(params: List[Arg], e : TypedExpr, env: Environnment, _type : Type, args_vals : List[Value]) extends FuncValue(e, _type, args_vals) {

    def this(params : List[Arg], e : TypedExpr, env : Environnment, _type : Type) = 
      this(params, e, env, _type, List())

    def withArgs(args : List[Value]) : NativeFunc = 
      new NativeFunc(params, e, env, _type, args)

    def arity = params.length

    def run(l : List[Value]) = {
      println("IN RUN OF NATIVEFUNC")
      val new_env = env.fresh()

      def bind_val(av : (Arg, Value)) : Unit = av match {
        case (SimpleArg(s), v) => new_env.addBinding(s, v)
        case (TupleMatch(as), TupleValue(_, vs)) => (as zip vs) foreach bind_val
        case _ => throw new Exception("WTF")
	  }

      (params zip l) foreach bind_val
      println("NEW ENV", new_env)
      e.eval(new_env) 
    }

    override def toString() = "function : " + _type

  }

  case class PrimFunc(nb_p: Int, func: (List[Value]) => Value, _type : Type, args_vals: List[Value]) extends FuncValue(func, _type, args_vals) {

    def this(nb_p : Int, func : (List[Value]) => Value, _type : Type) = 
      this(nb_p, func, _type, List())

    def withArgs(args : List[Value]) : PrimFunc = 
      new PrimFunc(nb_p, func, _type, args)

    def arity = nb_p
    def run(l : List[Value]) = func(l)

  }

  val prims = new Environnment(Map(
    ("+" -> new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x + y), ((x, y) => x + y))(lv(1)), TypeEnv.default_varmap("+").get)),
    ("-" -> new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x - y), ((x, y) => x - y))(lv(1)), TypeEnv.default_varmap("-").get)),
    ("*" -> new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x * y), ((x, y) => x * y))(lv(1)), TypeEnv.default_varmap("*").get)),
    ("/" -> new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x / y), ((x, y) => x / y))(lv(1)), TypeEnv.default_varmap("/").get)),
    ("==" -> new PrimFunc(2, (lv) => new BoolValue(lv(0) == lv(1)), TypeEnv.default_varmap("==").get))
  ), None)

  var match_val : Option[Value] = None
  class EvalObject(e: TypedExpr) {
    def eval(env : Environnment) : Value = {
      e match {
        case TValInt(v) => IntValue(v)
        case TValString(v) => StringValue(v)
        case TValBool(v) => BoolValue(v)
        case TValDouble(v) => DoubleValue(v)
        case TValUnit => UnitValue
        case TVarRef(_, id) => env.get(id)
        case TFunDef(typ, args, body) => new NativeFunc(args, body, env, typ)

        case TFunCall(_, fun, args) => 
          fun.eval(env).asInstanceOf[FuncValue].app(args.map(_.eval(env)))

        case TLetBind(_, name, expr, body) =>
          body.eval(env.withBinding(name, expr.eval(env)))
          
        case TDoBlock(_, exprs) => exprs.map(e => e.eval(env)).last

        case TIfExpr(_, cond, body, alt) => 
          if (cond.eval(env).asInstanceOf[BoolValue].v == true) body.eval(env)
          else alt.eval(env)

        case TVal(_, name, expr) => {
          val v = expr.eval(env)
          env.addBinding(name, v)
          v
        }

        case TTuple(_type, exprs) => {
          val evexprs = exprs.map(_.eval(env))
          TupleValue(_type, evexprs)
        }

        case TMatchExpr(_type, to_match, match_clauses) => {
          val evexpr = to_match.eval(env)
          def matches(m : MatchBranchExpr, v : Value) : Boolean = m match {
            case ConsMatchExpr(cons, vars) => v match {
              case ConstructorValue(_, cons2, vv) => {
                val subexpr = if (vars.length > 1) TupleMatchExpr(vars) else vars(0)
                (cons == cons2) && matches(subexpr, vv)
              }
              case _ => false
            }
            case TupleMatchExpr(vars) => v match {
              case TupleValue(_, lv) => (vars zip lv).foldLeft(true)((acc, tup) => tup match {
                case (vr, vl) => acc && matches(vr, vl)
              })
              case _ => false
            }
            case SimpleMatchExpr(s) => true
          }
          val clause = match_clauses.dropWhile(x => !matches(x.match_expr, evexpr)).head
          match_val = Some(evexpr)
          clause.eval(env)
        }

        case TMatchClause(_, match_expr, expr) => {
          val new_env = env.fresh()
          def bind(m : MatchBranchExpr, v : Value) : Unit = m match {
            case ConsMatchExpr(cons, vars) => v match {
              case ConstructorValue(_, cons2, vv) => {
                val subexpr = if (vars.length > 1) TupleMatchExpr(vars) else vars(0)
                bind(subexpr, vv)
              }
            }
            case TupleMatchExpr(vars) => v match {
              case TupleValue(_, lv) => (vars zip lv) foreach {
                case (vr, vl) => bind(vr, vl)
              } 
            }
            case SimpleMatchExpr(s) => new_env.addBinding(s, v)
          }
          bind(match_expr, match_val.get)
          expr.eval(new_env)
        }

        case TTypeClassDef(name, fun_names) => {
          val classhashmap = new HashMap[String, HashMap[Type, FuncValue]]
          classes(name) = classhashmap
          fun_names foreach {
            case (funcname, t) => {
              val classfuncs = new HashMap[Type, FuncValue]
              classhashmap(funcname) = classfuncs
              env.addBinding(funcname, new PrimFunc(t.ts.length-1, (l => classfuncs(l(0).t).run(l)), t))
            }
          }
          UnitValue
        }

        case TInstanceDef(typ, classname, funs) => {
          val classhashmap = classes(classname)
          funs foreach {
            case TInstanceFunExpr(funtyp, funcname, fun) => {
              val funcval = fun.eval(env).asInstanceOf[FuncValue]
              classhashmap(funcname)(typ) = funcval
            }
          }
          println("CLASSHMAP " + classhashmap)
          UnitValue
        }
      }
    }
  }
}