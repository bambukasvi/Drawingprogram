package drawingprogram

import scala.swing._
import java.awt.Color
import java.awt.BasicStroke
import java.awt.image.BufferedImage
import scala.swing.event._

object SwingUI extends SimpleSwingApplication {
  
  val height = 1000
  val width = 1500
  val colorButtonSize = new Dimension(50, 50)
  
  private var currentColor = Color.black
  
  def changeColor(color: Color) = currentColor = color
  
  private var currentStroke = new BasicStroke(3)
  
  def changeStroke(thickness: Int) = currentStroke = new BasicStroke(thickness)
  
  def buttonAction = ???
 
  //Buttons for different colors
  
  val redButton = new Button("") {
    background = Color.red
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val blueButton = new Button("") {
    background = Color.blue
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val greenButton = new Button("") {
    background = Color.green
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val orangeButton = new Button("") {
    background = Color.orange
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val yellowButton = new Button("") {
    background = Color.yellow
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val cyanButton = new Button("") {
    background = Color.CYAN
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val blackButton = new Button("") {
    background = Color.BLACK
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val grayButton = new Button("") {
    background = Color.gray
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val magentaButton = new Button("") {
    background = Color.magenta
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val pinkButton = new Button("") {
    background = Color.pink
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val whiteButton = new Button("") {
    background = Color.white
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  val darkGrayButton = new Button("") {
    background = Color.DARK_GRAY
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
  }
  
   // panel where the drawing happens 
  
  class DrawPanel(color: Color) extends Panel {
 
    override def paintComponent(g : Graphics2D) = {
      g.setColor(Color.WHITE)
      g.setStroke(currentStroke)
      g.fillRect(0, 0, width, height)
      
      
      
      

    }
  
  }
  val drawPanel = new DrawPanel(currentColor)
  
//  val drawing = new BufferedImage(drawPanel.maximumSize.width, drawPanel.maximumSize.height, BufferedImage.TYPE_INT_ARGB)
//  val g = drawing.getGraphics.asInstanceOf[Graphics2D]
//  
  
  def top = new MainFrame {
    title    = "Drawing program"
    resizable = false
    
    
      menuBar = new MenuBar {
        background = Color.white
       
        contents += new Menu("File") {
          contents += new MenuItem(Action("Open") {
            buttonAction
          })
          contents += new MenuItem(Action("Save") {
            buttonAction
          })
        }
        
        contents += new Menu("Edit") {
          contents += new MenuItem(Action("Undo") {
            buttonAction
          })
          contents += new MenuItem(Action("Redo") {
            buttonAction
          })
        }
//        contents += new Separator {
//          preferredSize = new Dimension(500, 25)
//        }
        contents += new MenuItem(Action("Exit") {
          sys.exit(0)
        })
      }
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Vertical) {
        maximumSize = new Dimension(width/5, height)
        minimumSize = new Dimension(width/5, height)
        // GridPanel for the color buttons
        contents += new GridPanel(4,3) {
          contents ++= Vector[Button](blackButton, darkGrayButton, grayButton, redButton, orangeButton, yellowButton,  blueButton, magentaButton, cyanButton, greenButton, pinkButton, whiteButton)
          maximumSize = new Dimension(colorButtonSize.width * 3, colorButtonSize.height * 5)
        }
        contents += new Separator
      }
      contents += drawPanel
    }
      
     
     size     = new Dimension(width, height) 
      
      
    
    
   
  }
  
  //EVENTS
  
  this.listenTo(drawPanel.mouse.clicks)
  this.reactions += {
  
    case clickEvent: MousePressed => {
      println("h")
    }
    case clickEvent: MouseReleased => {
      println("o")
    }
      
  }
}