package main.scala

class VarMap(varmap : Map[String, AbstractType]) {
  
  def getType(binding : String) = varmap(binding).get()

  def bindName(binding: String) : VarMap =
    new VarMap(varmap + (binding -> new TypePoly))
  
  def withType(binding : String, type_ : Type) : VarMap =
    new VarMap(varmap + (binding -> type_))

  def bindNames(bindings : List[String]) : VarMap = 
    new VarMap(bindings.foldLeft(varmap)((vmap, binding) => vmap + ((binding, new TypePoly()))))

  def withMold(binding: String, t: Type) : VarMap = 
    new VarMap(varmap + (binding -> new TypeMold(t)))

  override def toString() = varmap.toString
}

object VarMap {
  val tpoly = new TypePoly()
  val default = new VarMap(
    List(
      ("+", TypeFunction(List(TypeInt, TypeInt, TypeInt))),
      ("*", TypeFunction(List(TypeInt, TypeInt, TypeInt))),
      ("-", TypeFunction(List(TypeInt, TypeInt, TypeInt))),
      ("==", new TypeMold(TypeFunction(List(tpoly, tpoly, TypeBool))))
    ).toMap
  )
}
