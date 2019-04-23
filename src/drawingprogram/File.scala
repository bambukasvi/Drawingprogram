package drawingprogram
import java.io.File
import java.io.PrintWriter
import scala.io.Source
import java.awt.{Color, Dimension, Graphics, Graphics2D, Point, geom, BasicStroke}

class Write(picture: Drawing) {
  def main(args: Array[String]) {
    val input = picture.getName
    val writer = new PrintWriter(new File(input))
    var fileData = ""
    val shapesAsString = {
      for (undo <- picture.undos.reverse) {
        fileData += {
          if (undo.shape.isInstanceOf[geom.Line2D.Double]) {
            "line,"
          } else if (undo.shape.isInstanceOf[geom.Rectangle2D.Double]) {
            "rectangle,"
          } else if (undo.shape.isInstanceOf[geom.Ellipse2D.Double] && undo.isCircle) {
            "circle, "
          } else {
            "ellipse, "
          }
        }
        fileData += {
           
        }
      }
    }
    writer.write("Hello Developer, Welcome to Scala Programming.")
    writer.close()

    Source.fromFile("Write.txt").foreach { x => print(x) }
  }
  
  //different vals for colors, shapes etc
  

}
