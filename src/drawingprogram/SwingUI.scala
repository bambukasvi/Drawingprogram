package drawingprogram

import scala.swing._

import java.awt.{Color, Dimension, Graphics, Graphics2D, Point, geom, BasicStroke}


import scala.swing.event._
import scala.swing.event.Key._

object SwingUI extends SimpleSwingApplication {
  
  val height = 1000
  val width = 1500
  val colorButtonSize = new Dimension(50, 50)
  
  val picture = new Drawing
  
  
  private var currentStroke = new BasicStroke(3)
  
  //indicates the current shape drawn
  
  private var currentShape = "line"
  
  def changeStroke(thickness: Int) = currentStroke = new BasicStroke(thickness)
  
  
  
  
  def buttonAction = ???
 
  //Buttons for different colors
  
  val redButton = new Button("") {
    background = Color.red
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Red"
    
  }
  val blueButton = new Button("") {
    background = Color.blue
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Blue"
  }
  val greenButton = new Button("") {
    background = Color.green
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Green"
  }
  val orangeButton = new Button("") {
    background = Color.orange
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Orange"
    
  } 
  val yellowButton = new Button("") {
    background = Color.yellow
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Yellow"
  }
  val cyanButton = new Button("") {
    background = Color.CYAN
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Cyan"
  }
  val blackButton = new Button("") {
    background = Color.BLACK
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Black"
  }
  val grayButton = new Button("") {
    background = Color.gray
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Gray"
  }
  val magentaButton = new Button("") {
    background = Color.magenta
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Magenta"
  }
  val pinkButton = new Button("") {
    background = Color.pink
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Pink"
  }
  val whiteButton = new Button("") {
    background = Color.white
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "White"
  }
  val darkGrayButton = new Button("") {
    background = Color.DARK_GRAY
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Dark gray"
  }
  
  val buttons = Vector[Button](blackButton, darkGrayButton, grayButton, redButton, orangeButton, yellowButton,  blueButton, magentaButton, cyanButton, greenButton, pinkButton, whiteButton)
  
  // case class for drawing event 
  
  case class DrawingStartEvent(val x: Int, val y: Int) extends Event
  
  case class DrawingStopEvent(val x: Int, val y: Int) extends Event
  
   // panel where the drawing happens 
  
  class DrawPanel  extends Panel {
    
    listenTo(mouse.clicks, mouse.moves, keys)
    focusable = true
    background = Color.WHITE
    
    var startPoint = (0.0, 0.0)
    var endPoint = (0.0, 0.0)
    
    reactions += {
      case e: MousePressed  => 
        moveTo(e.point)
        startPoint = (e.point.getX, e.point.getY)
        requestFocusInWindow()
      case e: MouseDragged  => lineTo(e.point)
      case e: MouseReleased => {
        lineTo(e.point)
        endPoint = (e.point.getX, e.point.getY)
        if (currentShape == "line") {
          picture.addUndo(new drawLineCommand(picture, startPoint._1, startPoint._2, endPoint._1, endPoint._2, currentColor))
        }
      }
     
     
      case KeyTyped(_,'c',_,_) => 
        path = new geom.GeneralPath
        repaint()
      case KeyTyped(_,'z',moControl,_) =>
        picture.undo
        
      case _: FocusLost => repaint()
    }
    
    var path = new geom.GeneralPath

    def lineTo(p: Point) = { 
      path.lineTo(p.x, p.y)
      repaint() 
    }
    def moveTo(p: Point) = { 
      path.moveTo(p.x, p.y)
      repaint() 
    }
    private var currentColor = Color.black
  
    def changeColor(color: Color) = currentColor = color
 
    override def paintComponent(g: Graphics2D) = {
      super.paintComponent(g)
      g.setColor(new Color(100,100,100))
      g.drawString("Press left mouse button and drag to paint." + 
                   (if(hasFocus) " Press 'c' to clear." else ""), 10, size.height-10)
      g.setColor(currentColor)
      g.draw(path)
    }
  
  }
  val drawPanel = new DrawPanel
  
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
          contents ++= buttons
          maximumSize = new Dimension(colorButtonSize.width * 3, colorButtonSize.height * 5)
        }
        contents += new Separator
      }
      contents += drawPanel
    }
      
     
     size     = new Dimension(width, height) 
      
      
    
    
   
  }
  
  
  
  //EVENTS
  

  this.listenTo(drawPanel)
  for (button <- buttons) {
    this.listenTo(button)
  }
  this.reactions += {
  
    
    case clickEvent: MouseReleased => {
      println("o")
    }
    
    case ButtonClicked(`redButton`) => {
      drawPanel.changeColor(Color.red)
    }
    
    case ButtonClicked(`blackButton`) => {
      drawPanel.changeColor(Color.black)
    }
    
    case ButtonClicked(`whiteButton`) => {
      drawPanel.changeColor(Color.WHITE)
    }
    case ButtonClicked(`blueButton`) => {
      drawPanel.changeColor(Color.blue)
    }
    case ButtonClicked(`cyanButton`) => {
      drawPanel.changeColor(Color.CYAN)
    }
    case ButtonClicked(`orangeButton`) => {
      drawPanel.changeColor(Color.orange)
    }
    case ButtonClicked(`magentaButton`) => {
      drawPanel.changeColor(Color.magenta)
    }
    case ButtonClicked(`grayButton`) => {
      drawPanel.changeColor(Color.gray)
    }
    case ButtonClicked(`darkGrayButton`) => {
      drawPanel.changeColor(Color.DARK_GRAY)
    }
    case ButtonClicked(`yellowButton`) => {
      drawPanel.changeColor(Color.yellow)
    }
    case ButtonClicked(`pinkButton`) => {
      drawPanel.changeColor(Color.PINK)
    }
    case ButtonClicked(`greenButton`) => {
      drawPanel.changeColor(Color.green)
    }
      
  }
}