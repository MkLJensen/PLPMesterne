import java.util
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.math._
import scala.jdk.CollectionConverters._

class BoundingBox(private var maxX: Int, private var maxY: Int) {

  private var _bottomLeftCoordinate: (Int, Int) = _
  private var _topRightCoordinate: (Int, Int) = _

  def setBottomLeftCoordinate(x: Int, y: Int): Unit = {
    if((x >= 0) & (y >= 0)) _bottomLeftCoordinate = (x,y) else printWarning()
  }

  def setTopRightCoordinate(x: Int, y: Int): Unit = {
    if((x <= maxX) & (y <= maxY)) _topRightCoordinate = (x,y) else printWarning()
  }

  def getBottomLeftCoordinate() : (Int, Int) = {
    _bottomLeftCoordinate
  }

  def getTopRightCoordinate() : (Int, Int) = {
    _topRightCoordinate
  }
  private def printWarning(): Unit = println("WARNING: Out of bounds")

}

object FigurTegnerenScala {

  private def clipBoundingBox(boundingBox: BoundingBox,
                              coords: List[List[Int]] ): List[java.util.List[Int]]  = {
    if(boundingBox == null ||
      boundingBox.getBottomLeftCoordinate() == null ||
      boundingBox.getTopRightCoordinate() == null) {
      return List(coords(0).asJava, coords(1).asJava)
    }

    val newXCoordinates = new ListBuffer[Int]()
    val newYCoordinates = new ListBuffer[Int]()

    val xValues = coords(0)
    val yValues = coords(1)

    for(i <- xValues.indices) {
      val x = xValues(i)
      val y = yValues(i)
      if((x >= boundingBox.getBottomLeftCoordinate()._1 && x <= boundingBox.getTopRightCoordinate()._1) &&
        (y >= boundingBox.getBottomLeftCoordinate()._2 && y <= boundingBox.getTopRightCoordinate()._2)) {
        newXCoordinates += x
        newYCoordinates += y
      }
    }

    return List(newXCoordinates.asJava, newYCoordinates.asJava)
  }

  @tailrec
  private def line(x1: Int, x2: Int, y1: Int, y2: Int, counter: Int, x_coords: List[Int], y_coords: List[Int]): List[List[Int]] = {
    if ((counter == x2 - x1 + 1 && x1 != x2) || (y_coords.reverse.last == y2 && x_coords.reverse.last == x2) || (x1==x2 && counter == y2)) {
      List(x_coords.reverse, y_coords.reverse)
    }
    else {
      if (x1-x2 == 0) {
        line(x1, x2, y1, y2, counter + 1, x_coords.::(x1), y_coords.::(y1+counter))
      }
      else {
        line(x1, x2, y1, y2, counter + 1, x_coords.::(counter + x1), y_coords.::(((y2 - y1).asInstanceOf[Float] / (x2 - x1).asInstanceOf[Float] * counter + y1).round))
      }
    }
  }

  def line(x1: Int,
           x2: Int,
           y1: Int,
           y2: Int,
           boundingBox: BoundingBox): java.util.List[java.util.List[Int]] = {
    if (x1 == x2 && y1 == y2) {
      List[java.util.List[Int]]().asJava
    }
    else {
      clipBoundingBox(boundingBox, line(x1, x2, y1, y2, 1, List[Int](x1), List[Int](y1))).asJava
    }
  }

  //https://www.mathworks.com/matlabcentral/answers/98665-how-do-i-plot-a-circle-with-a-given-radius-and-center
  @tailrec
  private def circle(x: Int, y: Int, r: Int, counter: Int, smoothness: Int, x_coords: List[Int], y_coords: List[Int]): List[List[Int]] = {
    if (counter == smoothness + 1) {
      List(x_coords.reverse, y_coords.reverse)
    }
    else {
      circle(x, y, r, counter + 1, smoothness, x_coords.::((cos(((Pi * 2) / smoothness) * counter) * r + x).round.asInstanceOf[Int]), y_coords.::((sin(((Pi * 2) / smoothness) * counter) * r + y).asInstanceOf[Int]))
    }
  }

  def circle(x: Int,
             y: Int,
             r: Int,
             smoothness: Int,
             boundingBox: BoundingBox,
             fill: Boolean): java.util.List[java.util.List[Int]] = {
    if (r > 0) {
      if(!fill) {
        clipBoundingBox(boundingBox, circle(x, y, r, 0, smoothness, List[Int](), List[Int]())).asJava
      } else {
        clipBoundingBox(boundingBox, filledCircle(x, y, r, 0, smoothness, List[Int](), List[Int]())).asJava
      }
    }
    else{
      List[java.util.List[Int]]().asJava
    }
  }

  @tailrec
  private def square(x1: Int, y1: Int, x2: Int, y2: Int, counter_x: Int, counter_y: Int, x_coords: List[Int], y_coords: List[Int]): List[List[Int]] = {
    if (x_coords.length > ((x2 - x1) + (y2 - y1)) * 2) {
      List[List[Int]](x_coords.reverse, y_coords.reverse)
    }
    else {
      if (y_coords.length < y2 - y1) {
        square(x1, y1, x2, y2, counter_x, counter_y + 1, x_coords.::(x1), y_coords.::(counter_y))
      }
      else if (x_coords.length < x2 - x1 + y2 - y1 + 1) {
        square(x1, y1, x2, y2, counter_x + 1, 0, x_coords.::(x1 + counter_x), y_coords.::(y2))
      }
      else if (y_coords.length < x2 - x1 + ((y2 - y1) * 2) + 1) {
        square(x1, y1, x2, y2, 0, counter_y - 1, x_coords.::(x2), y_coords.::(y2 + counter_y - 1))
      }
      else {
        square(x1, y1, x2, y2, counter_x - 1, counter_y, x_coords.::(x2 + counter_x - 1), y_coords.::(y1))
      }
    }
  }

  def square(x1: Int,
             x2: Int,
             y1: Int,
             y2: Int,
             boundingBox: BoundingBox,
             fill: Boolean = false): java.util.List[java.util.List[Int]] = {
    if (x1 < x2 && y1 < y2) {
      if(!fill) {
        clipBoundingBox(boundingBox, square(x1, y1, x2, y2, 0, y1, List[Int](), List[Int]())).asJava
      } else {
        clipBoundingBox(boundingBox, filledSquare(x1, y1, x2, y2, List[Int](), List[Int]())).asJava
      }
    } else {
      List[java.util.List[Int]]().asJava
    }
  }

  private def filledCircle(x: Int, y: Int, r: Int, counter: Int, smoothness: Int, x_coords: List[Int], y_coords: List[Int]): List[List[Int]] = {
    if (r<=0) {
      List(x_coords.reverse, y_coords.reverse)
    }
    else {
      if(counter == smoothness + 1){
        filledCircle(x, y, r-1, 0, smoothness, x_coords, y_coords)
      } else {
        filledCircle(x, y, r, counter + 1, smoothness, x_coords.::((cos(((Pi * 2) / smoothness) * counter) * r + x).round.asInstanceOf[Int]), y_coords.::((sin(((Pi * 2) / smoothness) * counter) * r + y).asInstanceOf[Int]))
      }
    }
  }

  private def filledSquare(x1: Int,
                           y1: Int,
                           x2: Int,
                           y2: Int,
                           xCoords: List[Int],
                           yCoords: List[Int]): List[List[Int]] = {
    if(x1 >= x2){
      List[List[Int]](xCoords, yCoords)
    } else {
      val yCoordinates = y1 to y2 by 1
      val xCoordinates = List.fill(yCoordinates.length)(x1)
      filledSquare(x1+1, y1, x2, y2, xCoords++xCoordinates, yCoords++yCoordinates)

      /* Other solution. Both seem slow
      if(y1 >= y2) {
        filledSquare(x1+1, yCoords.last, x2, y2, xCoords.::(x1), yCoords.::(y1))
      } else {
        filledSquare(x1, y1+1, x2, y2, xCoords.::(x1), yCoords.::(y1))
      }
      */
    }
  }
}