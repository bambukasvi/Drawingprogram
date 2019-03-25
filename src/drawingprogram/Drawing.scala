package drawingprogram

import scala.collection.mutable.Stack
import java.awt.Color
import scala.collection.mutable.Buffer

class Drawing {
  
  private var currentColor = Color.BLACK
  
  private var undos = Stack[Command]() 
  private var redos = Stack[Command]()
  
  def addUndo(shape: Command) = undos.push(shape)
  
  def undo = {
    val topElement = undos.pop
    topElement.Undo
    redos.push(topElement)
  }
  
  def deleteAll = undos.clear()
  
  def drawLine(x1: Double, y1: Double, x2: Double, y2: Double) = ???
  
  def drawRectangle = ???
  
  def drawCircle = ???
  
  def drawEllipse = ???
  
  
}