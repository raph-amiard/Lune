package main.scala

import scala.collection.immutable.HashMap

object Interpreter {
  type VarMap = HashMap[String, Value]
  
  implicit def evalObject(e : TypedExpr) = new EvalObject(e)
  
  class RuntimeFunc(params: List[String], e : TypedExpr) {
    def apply(env: VarMap, pvalues: List[Value]) {
    }
  }
  
  class Value(v : Any) {
    override def toString() = v.toString
  }
  case class IntValue(v : Int) extends Value(v)
  case class StringValue(v: String) extends Value(v)
  case class BoolValue(v: Boolean) extends Value(v)
  case class DoubleValue(v: Double) extends Value(v)
  case class FunValue() extends Value
  
  
  class EvalObject(e: TypedExpr) {
    def eval(env : VarMap) {
      e match {
        case TValInt(v) => IntValue(v)
        case TValString(v) => StringValue(v)
        case TValBool(v) => BoolValue(v)
        case TValDouble(v) => DoubleValue(v)
        case TVarRef(_, id) => env(id)
        case TFunDef(_, )
        
      }
    }
  }
}