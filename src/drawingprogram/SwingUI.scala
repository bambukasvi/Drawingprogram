package drawingprogram
import scala.swing._
import java.awt.{Color, Dimension, Graphics, Graphics2D, Point, geom, BasicStroke}
import scala.swing.event._


object SwingUI extends SimpleSwingApplication {
  
  val height = 1000
  val width = 1500
  val colorButtonSize = new Dimension(50, 50)
  
  val picture = new Drawing
  private var currentStroke = 3
  private var currentColor = Color.black
  private var colorName = "black"
  private var currentShape: java.awt.Shape = new geom.Line2D.Double
  // differentiate between a circle and an ellipse
  private var isCircle = false
  
  def changeColor(color: Color) = currentColor = color
  def changeShape(shape: java.awt.Shape) = currentShape = shape   
  def changeStroke(thickness: Int) = currentStroke = thickness
    
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
  
  val colorButtons = Vector[Button](blackButton, darkGrayButton, grayButton, redButton, orangeButton, yellowButton,  blueButton, magentaButton, cyanButton, greenButton, pinkButton, whiteButton)
  
  //  buttons for different shapes
  val lineButton = new Button("Line") {
    background = Color.WHITE
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Line"
  }
  
  val rectangleButton = new Button("Rectangle") {
    background = Color.WHITE
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Rectangle"
  }
  
  val circleButton = new Button("Circle") {
    background = Color.WHITE
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Circle"
  }
  
  val ellipseButton = new Button("Ellipse") {
    background = Color.WHITE
    preferredSize = colorButtonSize
    maximumSize = colorButtonSize
    tooltip = "Ellipse"
  }
  
  val shapeButtons = Vector[Button](lineButton, rectangleButton, circleButton, ellipseButton)
  // slider for shape thickness
  val slider = new Slider {
          name = "Thickness"
          min = 1
          max = 10
          value = 3
          majorTickSpacing = 1
          
        }
  // case class for drawing event 
  
  case class DrawingStartEvent(val x: Int, val y: Int) extends Event
  
  case class DrawingStopEvent(val x: Int, val y: Int) extends Event
  
   // panel where the drawing happens 
  
  class DrawPanel  extends Panel {
    
    listenTo(mouse.clicks, mouse.moves, keys)
    focusable = true
    background = Color.WHITE
    
    private var startPoint = (0.0, 0.0)

    // What happens when you press and drag your mouse on the canvas
    reactions += {
      case e: MousePressed  => 
        startPoint = (e.point.x, e.point.y) 
        // Remove the redos if we start drawing a new shape
        picture.redos.clear()
        requestFocusInWindow()
      case e: MouseDragged  => lineTo(e.point)
      case e: MouseReleased => {
        lineTo(e.point)
        picture.undos.push(new Shape(currentColor, currentShape, currentStroke, isCircle, startPoint._1, startPoint._2, e.point.x, e.point.y))
        println(currentColor)
        println(currentShape.formatted("asd"))
        println(currentStroke)
        initiateShape()
      }          
      case KeyTyped(_,'c',_,_) =>         
        picture.deleteAll
        initiateShape()
        repaint()
      case KeyTyped(_,'z',_,_) =>       
        picture.undo
        initiateShape()
        repaint()
      case KeyTyped(_,'r',_,_) =>
        picture.redo
        initiateShape()
        repaint()
      case _: FocusLost => repaint()
    }
    
    // draws the currently selected shape  
    def lineTo(p: Point) = { 
      currentShape match {
        case line: geom.Line2D.Double => line.setLine(startPoint._1, startPoint._2, p.x, p.y)
        
        case rectangle: geom.Rectangle2D.Double => {
          if (startPoint._1 < p.x && startPoint._2 < p.y) {
            rectangle.setRect(startPoint._1, startPoint._2, p.x - startPoint._1, p.y - startPoint._2)
          } else if (startPoint._1 >= p.x && startPoint._2 >= p.y) {
            rectangle.setRect(p.x, p.y, startPoint._1 - p.x, startPoint._2 - p.y)
          } else if (startPoint._1 < p.x && startPoint._2 > p.y) {
            rectangle.setRect(startPoint._1 , p.y, p.x - startPoint._1, startPoint._2 - p.y)
          } else if (startPoint._1 > p.x && startPoint._2 < p.y) {
            rectangle.setRect(p.x, startPoint._2, startPoint._1 - p.x, p.y - startPoint._2)
          }          
        }
        
        // ellipse case handels both circle shape and ellipse shape        
        case ellipse: geom.Ellipse2D.Double => {
          if (!isCircle) {
            if (startPoint._1 < p.x && startPoint._2 < p.y) {
              ellipse.setFrame(startPoint._1, startPoint._2, p.x - startPoint._1, p.y - startPoint._2)
            } else if (startPoint._1 >= p.x && startPoint._2 >= p.y) {
              ellipse.setFrame(p.x, p.y, startPoint._1 - p.x, startPoint._2 - p.y)
            } else if (startPoint._1 < p.x && startPoint._2 > p.y) {
              ellipse.setFrame(startPoint._1 , p.y, p.x - startPoint._1, startPoint._2 - p.y)
            } else if (startPoint._1 > p.x && startPoint._2 < p.y) {
              ellipse.setFrame(p.x, startPoint._2, startPoint._1 - p.x, p.y - startPoint._2)
            } 
          } else {
            if (startPoint._1 < p.x && startPoint._2 < p.y) {
              ellipse.setFrame(startPoint._1, startPoint._2, p.x - startPoint._1, p.x - startPoint._1)
            } else if (startPoint._1 >= p.x && startPoint._2 >= p.y) {
              ellipse.setFrame(p.x, startPoint._2 - (startPoint._1 - p.x), startPoint._1 - p.x, startPoint._1 - p.x)
            } else if (startPoint._1 < p.x && startPoint._2 > p.y) {
              ellipse.setFrame(startPoint._1 ,startPoint._2 - (p.x - startPoint._1), p.x - startPoint._1, p.x - startPoint._1)
            } else if (startPoint._1 > p.x && startPoint._2 < p.y) {
              ellipse.setFrame(p.x, startPoint._2, startPoint._1 - p.x, startPoint._1 - p.x)
            }
          }
        }
      }
      
      repaint() 
    }

    def initiateShape() = {
      if (currentShape.isInstanceOf[geom.Line2D.Double]) {
          currentShape = new geom.Line2D.Double 
        } else if (currentShape.isInstanceOf[geom.Rectangle2D.Double]) {
          currentShape = new geom.Rectangle2D.Double          
        } else if (currentShape.isInstanceOf[geom.Ellipse2D.Double]) {
          currentShape = new geom.Ellipse2D.Double  
        }
    }
    
 
    override def paintComponent(g: Graphics2D) = {
      super.paintComponent(g)
      g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
		   java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
      g.setColor(new Color(100,100,100))
      g.drawString("Press left mouse button and drag to paint." + 
                   (" Press 'c' to clear, 'z' to undo and 'r' to redo. Adjust the slider to change line thickness. " ), 10, size.height-10)
      for (shape <- picture.undos.reverse) {
        g.setColor(shape.color)
        g.setStroke(new BasicStroke(shape.stroke))
        g.draw(shape.shape)
      }
      g.setStroke(new BasicStroke(currentStroke))
      g.setColor(currentColor)
      g.draw(currentShape)
      
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
            val r = Dialog.showInput(new Label("Save"), "Name the file", initial = picture.getName)
            r match {
              case Some(s) => picture.changeName(s)
              case None => 
            }
          })
        }
        
        contents += new Menu("Edit") {
          contents += new MenuItem(Action("Undo") {
            picture.undo
            drawPanel.initiateShape()
            drawPanel.repaint()
          })
          contents += new MenuItem(Action("Redo") {
            picture.redo
            drawPanel.initiateShape()
            drawPanel.repaint()
          })
        }
      }
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Vertical) {
        maximumSize = new Dimension(width/5, height)
        minimumSize = new Dimension(width/5, height)
        contents += Swing.VStrut(10)
        // GridPanel for the color buttons
        contents += new GridPanel(4,3) {
          contents ++= colorButtons
          maximumSize = new Dimension(colorButtonSize.width * 3, colorButtonSize.height * 5)
        }          
        contents += Swing.VStrut(10)
        // GridPanel for shape buttons 
        contents += new GridPanel(shapeButtons.length,1) {          
          contents ++= shapeButtons
          maximumSize = new Dimension(colorButtonSize.width * 3, colorButtonSize.height * 5)
        }
        contents += Swing.VStrut(30)
        contents += slider
      }
      contents += drawPanel
    }
      
     
     size     = new Dimension(width, height) 
      
      
    
    
   
  }
  
  
  
  //EVENTS
  

  this.listenTo(drawPanel)
  this.listenTo(slider)
  for (button <- colorButtons) {
    this.listenTo(button)
  }
  for (button <- shapeButtons) {
    this.listenTo(button)
  }
  this.reactions += {      
    case ButtonClicked(`redButton`) => {
      changeColor(Color.red)
    }    
    case ButtonClicked(`blackButton`) => {
      changeColor(Color.black)
    }    
    case ButtonClicked(`whiteButton`) => {
      changeColor(Color.WHITE)
      println(currentShape.getBounds)
    }
    case ButtonClicked(`blueButton`) => {
      changeColor(Color.blue)
    }
    case ButtonClicked(`cyanButton`) => {
      changeColor(Color.CYAN)
    }
    case ButtonClicked(`orangeButton`) => {
      changeColor(Color.orange)
    }
    case ButtonClicked(`magentaButton`) => {
      changeColor(Color.magenta)
    }
    case ButtonClicked(`grayButton`) => {
      changeColor(Color.gray)
    }
    case ButtonClicked(`darkGrayButton`) => {
      changeColor(Color.DARK_GRAY)
    }
    case ButtonClicked(`yellowButton`) => {
      changeColor(Color.yellow)
    }
    case ButtonClicked(`pinkButton`) => {
      changeColor(Color.PINK)
    }
    case ButtonClicked(`greenButton`) => {
      changeColor(Color.green)
    }
    case ButtonClicked(`lineButton`) => {
      currentShape = new geom.Line2D.Double
    }
    case ButtonClicked(`rectangleButton`) => {
      currentShape = new geom.Rectangle2D.Double
    }
    case ButtonClicked(`circleButton`) => {
     isCircle = true
     currentShape = new geom.Ellipse2D.Double
    }
    case ButtonClicked(`ellipseButton`) => {
      isCircle = false
      currentShape = new geom.Ellipse2D.Double
    }
    case ValueChanged(`slider`) => {
      if (!slider.adjusting) {
        this.changeStroke(slider.value)
      }
     
    }
      
  }
  
}