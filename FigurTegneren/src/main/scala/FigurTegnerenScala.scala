import scala.annotation.tailrec
import scala.math._
import scala.jdk.CollectionConverters._

object FigurTegnerenScala {

  @tailrec
  private def line(x1: Float, x2: Float, y1: Float, y2: Float, counter: Float, x_coords: List[Float], y_coords: List[Float]): List[java.util.List[Float]] = {
    if (counter == x2) {
      List[java.util.List[Float]](x_coords.reverse.asJava, y_coords.reverse.asJava)
    }
    else {
      line(x1, x2, y1, y2, counter + 1, y_coords.::((y2 - y1) / (x2 - x1) * (x1 + counter + 1) + x1), x_coords.::(counter + x1 + 1))
    }
  }

  def line(x1: Float, x2: Float, y1: Float, y2: Float): java.util.List[java.util.List[Float]] = {
    line(x1, x2, y1, y2, 0, List[Float](), List[Float]()).asJava
  }

  //https://www.mathworks.com/matlabcentral/answers/98665-how-do-i-plot-a-circle-with-a-given-radius-and-center
  @tailrec
  private def circle(x: Float, y: Float, r: Float, counter: Int, smoothness: Int, x_coords: List[Double], y_coords: List[Double]): List[List[Double]] = {
    if (counter == smoothness + 1) {
      List[List[Double]](x_coords.reverse, y_coords.reverse)
    }
    else {
      circle(x, y, r, counter + 1, smoothness, x_coords.::(cos(((Pi * 2) / smoothness) * counter) + x), y_coords.::(sin(((Pi * 2) / smoothness) * counter) + y))
    }
  }

  def circle(x: Float, y: Float, r: Float, smoothness: Int): List[List[Double]] = {
    circle(x, y, r, 0, smoothness, List[Double](), List[Double]())
  }

  @tailrec
  private def square(x1: Float, y1: Float, x2: Float, y2: Float, counter_x: Float, counter_y: Float, x_coords: List[Float], y_coords: List[Float]): List[List[Float]] = {
    if (x_coords.length > ((x2-x1) + (y2-y1)) * 2) {
      List[List[Float]](x_coords.reverse, y_coords.reverse)
    }
    else {
      if (y_coords.length < y2-y1) {
        square(x1, y1, x2, y2, counter_x, counter_y+1, x_coords.::(x1), y_coords.::(counter_y))
      }
      else if(x_coords.length < x2-x1 + y2-y1 + 1) {
        square(x1, y1, x2, y2, counter_x+1, 0, x_coords.::(x1+counter_x), y_coords.::(y2))
      }
      else if (y_coords.length < x2-x1 + ((y2-y1)*2) + 1) {
        square(x1, y1, x2, y2, 0, counter_y-1, x_coords.::(x2), y_coords.::(y2+counter_y-1))
      }
      else {
        square(x1, y1, x2, y2, counter_x-1, counter_y, x_coords.::(x2+counter_x-1), y_coords.::(y1))
      }
    }
  }

  def square(x1: Float, x2: Float, y1: Float, y2: Float): List[List[Float]] = {
    if (x1 < x2 && y1 < y2) {
      return square(x1, y1, x2, y2, x1, y1,List[Float](), List[Float]())
    }
    List[List[Float]]()
  }
}