package drawingprogram

import scala.collection.mutable.Stack
import java.awt.{Color, BasicStroke, geom, Point}

// this class holds information of a drawn shape
class Shape(val color: Color, val shape: java.awt.Shape, val stroke: Int, val isCircle: Boolean, 
    val x1: Double, val y1: Double, val x2: Double, val y2: Double) {
  
}