package drawingprogram
import java.io._
import scala.io.Source
import java.awt.{Color, Dimension, Graphics, Graphics2D, Point, geom, BasicStroke}
import scala.collection.mutable.Buffer
import scala.collection.mutable.Stack

object Write {
  
  private var fileName = "drawing" 
  def changeName(s: String) = fileName = s 
  
  /**
   * data is show like this:
   * 
   * L,BLA,3,352.0389.0565.0225.0
	 * L,BLA,3,279.0205.0683.0439.0
	 * 
	 * Shape, color, line thickness, coordinates, coordinates(if line) / coordinates, width and height (if something else)
   */

  def save(undos: Stack[Shape]) {
    var fileData = Buffer[String]()
    for (undo <- undos.reverse) {
      val geometry = undo.shape match { 
        case line: geom.Line2D.Double => "L,"
        case rectangle: geom.Rectangle2D.Double => "R,"
        case ellipse: geom.Ellipse2D.Double => if (undo.isCircle) "C," else "E,"
      }        
      fileData += (geometry + undo.colorName + undo.stroke + "," + undo.x1 + "," + 
            undo.y1 + "," + undo.x2 + "," + undo.y2  + "\n")          
    }
    val file = new File("SaveFiles/" + fileName + ".txt")
    val writer = new PrintWriter(file)
    fileData.foreach(string => writer.write(string))
    writer.close()
    
  }
  

}

object Read {

  def readFile(f: File): Option[Drawing] = {
    val picture = new Drawing
    try {
      val lines = Source.fromFile(f).getLines.toArray
      for (line <- lines) {
        val values = line.split(',')
        if (values.size != 7) throw new CorruptedFileException("Failed to read data from file")
          val color = values(1) match {
            case "BLA" => Color.black
            case "RED" => Color.red
            case "MAG" => Color.MAGENTA
            case "WHI" => Color.WHITE
            case "PIN" => Color.PINK
            case "GRE" => Color.green
            case "DGR" => Color.DARK_GRAY
            case "GRA" => Color.gray
            case "BLU" => Color.blue
            case "YEL" => Color.yellow
            case "CYA" => Color.CYAN
            case "ORA" => Color.orange
            case _ => throw new CorruptedFileException("Failed to read data from file")
        }
        values(0) match {
          case "L" => {
            picture.undos.push(new Shape(color, values(1), new geom.Line2D.Double, values(2).toInt, false, 
                values(3).toDouble, values(4).toDouble, values(5).toDouble, values(6).toDouble))
          } 
          case "R" => {
            picture.undos.push(new Shape(color, values(1), new geom.Rectangle2D.Double, values(2).toInt, false, 
                values(3).toDouble, values(4).toDouble, values(5).toDouble, values(6).toDouble))
          }
          case "C" => {
            picture.undos.push(new Shape(color, values(1), new geom.Ellipse2D.Double, values(2).toInt, true, 
                values(3).toDouble, values(4).toDouble, values(5).toDouble, values(6).toDouble))
          }
          case "E" => {
            picture.undos.push(new Shape(color, values(1), new geom.Ellipse2D.Double, values(2).toInt, false, 
                values(3).toDouble, values(4).toDouble, values(5).toDouble, values(6).toDouble))
          }
          case _ => throw new CorruptedFileException("Failed to read data from file")
        }
      }
      Some(picture)
     
    } catch {
      case v: FileNotFoundException =>
      println("File not found")
      None
      case e: IOException =>
      println("Reading finished with an error")
      None
      case c: CorruptedFileException => 
      println(c.message)
      None
    }
  }
  
  
  
}
// exception for failed file reading
case class CorruptedFileException(message: String) extends java.lang.Exception(message) 
