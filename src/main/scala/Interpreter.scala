package main.scala

object Interpreter {
  import scala.collection.mutable.Map
  import scala.math.Numeric
  
  implicit def evalObject(e : TypedExpr) = new EvalObject(e)
  
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
    
  }
  
  type GNumOp = ((Int, Int) => Int,
		  		 (Double, Double) => Double)
		  		 
  abstract class Value(v : Any) {
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
  
  case object UnitValue extends Value(null) {
    override def toString() = "()"
  }
  case class IntValue(v : Int) extends Value(v)
  case class DoubleValue(v: Double) extends Value(v)
  case class StringValue(v: String) extends Value(v)
  case class BoolValue(v: Boolean) extends Value(v)
  case class TupleValue(v: List[Value]) extends Value(v) {
    override def toString() = "(" + (v mkString ", ") + ")"
  }
  case class FunValue() extends Value
  
  abstract class FuncValue(args_vals : List[Value]) extends Value {
    
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
  
  case class NativeFunc(params: List[Arg], e : TypedExpr, env: Environnment, _type : Type, args_vals : List[Value]) extends FuncValue(args_vals) {
    
    def this(params : List[Arg], e : TypedExpr, env : Environnment, _type : Type) = 
      this(params, e, env, _type, List())
      
    def withArgs(args : List[Value]) : NativeFunc = 
      new NativeFunc(params, e, env, _type, args)
    
    def arity = params.length
    
    def run(l : List[Value]) = {
      val new_env = env.fresh()
      
      def bind_val(av : (Arg, Value)) : Unit = av match {
        case (SimpleArg(s), v) => new_env.addBinding(s, v)
        case (TupleMatch(as), TupleValue(vs)) => (as zip vs) foreach bind_val
        case _ => throw new Exception("WTF")
	  }
      
      (params zip l) foreach bind_val
      println("NEW ENV", new_env)
      e.eval(new_env) 
    }
    
    override def toString() = "fun(" + _type + ")"
    
  }
  
  case class PrimFunc(nb_p: Int, func: (List[Value]) => Value, args_vals: List[Value]) extends FuncValue(args_vals) {
    
    def this(nb_p : Int, func : (List[Value]) => Value) = 
      this(nb_p, func, List())
     
      
    def withArgs(args : List[Value]) : PrimFunc = 
      new PrimFunc(nb_p, func, args)
    
    def arity = nb_p
    def run(l : List[Value]) = func(l)
    
  }
  
  val prims = new Environnment(Map(
    ("+" -> new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x + y), ((x, y) => x + y))(lv(1)))),
    ("-" -> new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x - y), ((x, y) => x - y))(lv(1)))),
    ("*" -> new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x * y), ((x, y) => x * y))(lv(1)))),
    ("/" -> new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x / y), ((x, y) => x / y))(lv(1)))),
    ("==" -> new PrimFunc(2, (lv) => new BoolValue(lv(0) == lv(1))))
  ), None)
  
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
        
        case TIfExpr(_, cond, body, alt) => 
          if (cond.eval(env).asInstanceOf[BoolValue].v == true) body.eval(env)
          else alt.eval(env)
        
        case TDef(_, name, expr) => {
          val v = expr.eval(env)
          env.addBinding(name, v)
          v
        }
        
        case TTuple(_, exprs) => {
          val evexprs = exprs.map(_.eval(env))
          TupleValue(evexprs)
        }
      }
    }
    
  }
  
}