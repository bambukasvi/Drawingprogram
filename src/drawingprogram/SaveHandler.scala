package drawingprogram
import java.io._
import scala.io.Source
import java.awt.{Color, Dimension, Graphics, Graphics2D, Point, geom, BasicStroke}
import scala.collection.mutable.Buffer

object Write {
  
  private var fileName = "drawing" 
  def changeName(s: String) = fileName = s 
  
  /**
   * data is show like this:
   * 
   * L,BLA,3,352.0389.0565.0225.0
	 * L,BLA,3,279.0205.0683.0439.0
	 * 
	 * Shape, color, line thickness, coordinates/width and height
   */

  def save(fileData: Array[String]) {
    val file = new File("SaveFiles/" + fileName)
    val writer = new PrintWriter(file)
    fileData.foreach(string => writer.write(string))
    writer.close()
    
  }
  

}

object Read {

  def readFile(f: File) = {
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
     
    } catch {
      case v: FileNotFoundException =>
      println("File not found")
      case e: IOException =>
      println("Reading finished with an error")
    }
  }
  
}
// exception for failed file reading
case class CorruptedFileException(message: String) extends java.lang.Exception(message) 
