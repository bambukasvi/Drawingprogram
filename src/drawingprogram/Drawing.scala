package drawingprogram

import scala.collection.mutable.Stack
import java.awt.{Color, BasicStroke}

class Drawing {
  
  private var name = "drawing"
  
  def getName = name
  def changeName(n: String) = name = n

  val undos = Stack[Shape]()
  val redos = Stack[Shape]()
  
  def undo = if (!undos.isEmpty) redos.push(undos.pop)
  
  def redo = if (!redos.isEmpty) undos.push(redos.pop)
     
  def deleteAll() = {
    redos.clear()
    undos.clear()
  }
    
  
  
  
}