package drawingprogram

class drawLineCommand(drawing: Drawing, x1: Double, y1: Double, x2: Double, y2: Double, color: java.awt.Color) extends Command {
  
  def Redo = {
    drawing.drawLine(x1, y1, x2, y2)
  }
  
  def Undo = {
    ???
  }
}