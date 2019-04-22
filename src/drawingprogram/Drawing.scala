package drawingprogram

import scala.collection.mutable.Stack
import java.awt.Color
import scala.collection.mutable.Buffer

class Drawing {
  
  private var currentColor = Color.BLACK
  
  val colorUndos = Stack[Color]()
  val colorRedos = Stack[Color]()
  val shapeUndos = Stack[java.awt.Shape]() 
  val shapeRedos = Stack[java.awt.Shape]()
  
  def addUndo(shape: java.awt.Shape) = shapeUndos.push(shape)
  
  def addColor(color: Color) = colorUndos.push(color)
  
  def undo = {
    if (!shapeUndos.isEmpty) {
      val topShape = shapeUndos.pop
      val topColor = colorUndos.pop
      shapeRedos.push(topShape)
      colorRedos.push(topColor)
      
    }
    
  }
  
  def deleteAll() = {
    shapeRedos.pushAll(shapeUndos)
    colorRedos.pushAll(colorUndos)
    shapeUndos.clear()
    colorUndos.clear()
  }
  
  def redo = {
    if (!shapeRedos.isEmpty) {
      val topShape = shapeRedos.pop
      val topColor = colorRedos.pop
      shapeUndos.push(topShape)
      colorUndos.push(topColor)
    }
    
  }
  
  
  def drawLine(x1: Double, y1: Double, x2: Double, y2: Double) = ???
  
  def drawRectangle = ???
  
  def drawCircle = ???
  
  def drawEllipse = ???
  
  
}