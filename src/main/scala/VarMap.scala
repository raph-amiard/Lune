package main.scala

class VarMap(varmap : Map[String, AbstractType]) {
  
  def getType(binding : String) = varmap(binding).get()
  def bindNames(bindings : List[String]) : VarMap = 
    new VarMap(bindings.foldLeft(varmap)((vmap, binding) => vmap + ((binding, new TypePoly()))))
  def withMold(binding: String, t: Type) : VarMap = 
    new VarMap(varmap + ((binding, new TypeMold(t))))
}