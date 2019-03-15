package drawingprogram

import scala.collection.mutable.Stack
import java.awt.Color
import scala.collection.mutable.Buffer
class Drawing {
  
  private var currentColor = Color.BLACK
  
  private var undos = Stack[Command]() 
  private var redos = Stack[Command]()
  
  def deleteAll = undos.clear()
  
  def drawLine(x1: Int, y1: Int, x2: Int, y2: Int) = ???
  
  def drawRectangle = ???
  
  def drawCircle = ???
  
  def drawEllipse = ???
  
  
}