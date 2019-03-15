package drawingprogram

class drawLineCommand(drawing: Drawing, x1: Int, y1: Int, x2: Int, y2: Int) extends Command {
  
  def Redo = {
    drawing.drawLine(x1, y1, x2, y2)
  }
  
  
}