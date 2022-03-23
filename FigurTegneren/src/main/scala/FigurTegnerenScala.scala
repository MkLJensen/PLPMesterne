import java.util
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.math._
import scala.jdk.CollectionConverters._

class BoundingBox(var bottomLeftCoordinate: (Int, Int) = (50,50), var topRightCoordinate: (Int, Int) = (100, 100)) {

}

class FigurTegnerenScala {

  var boundingBox: BoundingBox = new BoundingBox();

  private def clipBoundingBox(coords: List[List[Int]] ): List[java.util.List[Int]]  = {
    val newXCoordinates = new ListBuffer[Int]()
    val newYCoordinates = new ListBuffer[Int]()

    val xValues = coords(0)
    val yValues = coords(1)

    for(i <- 0 to xValues.length-1) {
      val x = xValues(i)
      val y = yValues(i)
      if((x >= boundingBox.bottomLeftCoordinate._1 && x <= boundingBox.topRightCoordinate._1) && (y >= boundingBox.bottomLeftCoordinate._2 && y <= boundingBox.topRightCoordinate._2)) {
        newXCoordinates += x
        newYCoordinates += y
      }
    }

    return List(newXCoordinates.asJava, newYCoordinates.asJava)
  }

  @tailrec
  private def line(x1: Int, x2: Int, y1: Int, y2: Int, counter: Int, x_coords: List[Int], y_coords: List[Int]): List[java.util.List[Int]] = {
    if ((counter == x2 + 1 && x1 != x2) || (x1==x2 && counter == y2)) {
      clipBoundingBox(List[List[Int]](x_coords.reverse, y_coords.reverse));
    }
    else {
      if (x1-x2 == 0) {
        line(x1, x2, y1, y2, counter + 1, x_coords.::(x1), y_coords.::(y1+counter))
      }
      else {
        line(x1, x2, y1, y2, counter + 1, x_coords.::(counter + x1), y_coords.::(((y2 - y1).asInstanceOf[Float] / (x2 - x1).asInstanceOf[Float] * (x1 + counter) + x1).round))
      }
    }
  }

  def line(x1: Int, x2: Int, y1: Int, y2: Int): java.util.List[java.util.List[Int]] = {
    if (x1 == x2 && y1 == y2) {
      List[java.util.List[Int]]().asJava
    }
    else {
      line(x1, x2, y1, y2, 0, List[Int](), List[Int]()).asJava
    }
  }

  //https://www.mathworks.com/matlabcentral/answers/98665-how-do-i-plot-a-circle-with-a-given-radius-and-center
  @tailrec
  private def circle(x: Int, y: Int, r: Int, counter: Int, smoothness: Int, x_coords: List[Int], y_coords: List[Int]): List[java.util.List[Int]] = {
    if (counter == smoothness + 1) {
      List[java.util.List[Int]](x_coords.reverse.asJava, y_coords.reverse.asJava)
    }
    else {
      circle(x, y, r, counter + 1, smoothness, x_coords.::((cos(((Pi * 2) / smoothness) * counter) * r + x).round.asInstanceOf[Int]), y_coords.::((sin(((Pi * 2) / smoothness) * counter) * r + y).asInstanceOf[Int]))
    }
  }

  def circle(x: Int, y: Int, r: Int, smoothness: Int): java.util.List[java.util.List[Int]] = {
    if (r > 0) {
      circle(x, y, r, 0, smoothness, List[Int](), List[Int]()).asJava
    }
    else{
      List[java.util.List[Int]]().asJava
    }
  }

  @tailrec
  private def square(x1: Int, y1: Int, x2: Int, y2: Int, counter_x: Int, counter_y: Int, x_coords: List[Int], y_coords: List[Int]): List[java.util.List[Int]] = {
    if (x_coords.length > ((x2 - x1) + (y2 - y1)) * 2) {
      List[java.util.List[Int]](x_coords.reverse.asJava, y_coords.reverse.asJava)
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

  def square(x1: Int, x2: Int, y1: Int, y2: Int): java.util.List[java.util.List[Int]] = {
    if (x1 < x2 && y1 < y2) {
      square(x1, y1, x2, y2, 0, y1, List[Int](), List[Int]()).asJava
    } else {
      List[java.util.List[Int]]().asJava
    }
  }
}