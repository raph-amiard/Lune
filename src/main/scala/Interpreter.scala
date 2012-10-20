package main.scala

import scala.collection.immutable.HashMap
import scala.math.Numeric

object Interpreter {
  
  implicit def evalObject(e : TypedExpr) = new EvalObject(e)
  
  class Environnment (h : Map[String, Value]) {
    def get(s : String) = h(s)
    
    def addBinding(s : String, v : Value) = new Environnment(h + ((s, v)))
    
    def addBindings(vars : List[String], vals : List[Value]) : Environnment =
      new Environnment((vars zip vals).foldLeft(this.h)((hmap, t) => hmap + t))
  }
  
  def add[T](x: T, y : T)(implicit num : Numeric[T]) = {
    import num._
    x + y
  }
  
  
  type GNumOp = ((Int, Int) => Int,
		  		 (Double, Double) => Double)
		  		 
  val a : GNumOp = (((x, y) => x + y),
		  			((x, y) => x + y))
  
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
  
  case class IntValue(v : Int) extends Value(v)
  case class DoubleValue(v: Double) extends Value(v)
  case class StringValue(v: String) extends Value(v)
  case class BoolValue(v: Boolean) extends Value(v)
  case class FunValue() extends Value
  
  abstract class FuncValue(args_vals : List[Value]) extends Value {
    
    def run(l : List[Value]) : Value
    def withArgs(args : List[Value]) : FuncValue
    def arity : Int
    
    def app(pvalues: List[Value]) : Value = {
      val (needed, rest) = pvalues.splitAt(arity - args_vals.length)
      println("IN APP", needed, rest)
      val args = args_vals ++ needed
	    if (args.length < arity) this withArgs args
	    else this.run(args) match {
	      case f : FuncValue => f.app(rest)
	      case f : Value => f
	      case _ if rest.length > 0 => throw new Exception("Shouldn't happen")
	    }
    }
    
  }
  
  case class NativeFunc(params: List[String], e : TypedExpr, env: Environnment, args_vals : List[Value]) extends FuncValue(args_vals) {
    
    def this(params : List[String], e : TypedExpr, env : Environnment) = 
      this(params, e, env, List())
      
    def withArgs(args : List[Value]) : NativeFunc = 
      new NativeFunc(params, e, env, args)
    
    def arity = params.length
    def run(l : List[Value]) = e.eval(env.addBindings(params, l)) 
    
  }
  
  case class PrimFunc(nb_p: Int, func: (List[Value]) => Value, args_vals: List[Value]) extends FuncValue(args_vals) {
    
    def this(nb_p : Int, func : (List[Value]) => Value) = 
      this(nb_p, func, List())
     
      
    def withArgs(args : List[Value]) : PrimFunc = 
      new PrimFunc(nb_p, func, args)
    
    def arity = nb_p
    def run(l : List[Value]) = func(l)
    
  }
  
  val prims = new Environnment(List(
    ("+" ,new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x + y), ((x, y) => x + y))(lv(1)))),
    ("-" ,new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x - y), ((x, y) => x - y))(lv(1)))),
    ("*" ,new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x * y), ((x, y) => x * y))(lv(1)))),
    ("/" ,new PrimFunc(2, (lv) => lv(0).numop(((x, y) => x / y), ((x, y) => x / y))(lv(1)))),
    ("==", new PrimFunc(2, (lv) => new BoolValue(lv(0) == lv(1))))
  ).toMap)
  
  class EvalObject(e: TypedExpr) {
    
    def eval(env : Environnment) : Value = {
      e match {
        case TValInt(v) => IntValue(v)
        case TValString(v) => StringValue(v)
        case TValBool(v) => BoolValue(v)
        case TValDouble(v) => DoubleValue(v)
        case TVarRef(_, id) => env.get(id)
        case TFunDef(_, args, body) => new NativeFunc(args, body, env)
        case TFunCall(_, fun, args) => 
          fun.eval(env).asInstanceOf[FuncValue].app(args.map(_.eval(env)))
        case TLetBind(_, name, expr, body) => 
          body.eval(env.addBinding(name, expr.eval(env)))
        case TIfExpr(_, cond, body, alt) => 
          if (cond.eval(env).asInstanceOf[BoolValue].v == true) body.eval(env)
          else alt.eval(env)
      }
    }
    
  }
  
}