package drawingprogram
import scala.swing._ 
import scala.collection.mutable.Buffer

object ProgramRunner extends SimpleSwingApplication  {
  
  private var currentUI = new SwingUI(new Drawing)
  
  def makeNewUI(picture: Drawing) = {
    currentUI = new SwingUI(picture)
  }
  
 
    def top = {
      currentUI.makeUI
    }
    

  
 
    
}