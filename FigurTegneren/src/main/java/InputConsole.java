import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputConsole extends JTextField {

    private final OutputConsole outputConsole;
    private final GraphicsPlane graphicsPlane;

    public InputConsole(OutputConsole _outputConsole, GraphicsPlane _graphicsPlane) {
        super();
        setEditable(true);
        outputConsole = _outputConsole;
        graphicsPlane = _graphicsPlane;
        final mAction action = new mAction();
        addActionListener(action);
    }

    /**
     * SHOULD REACT TO
     * (LINE (x1 y1) (x2 y2))                   x1,y1 = Start Coordinate x2,y2 = End Coordinate
     * (RECTANGLE (x1 y1) (x2 y2))              x1,y1 = Start Pixel Point, x2,y2 = End Pixel Point
     * (CIRCLE (x1 y1) r)                       x1,y1 = Center Point, r = radius
     * (TEXT-AT (x1 y1) t)                      x1,y1 = Center Point, t = text to draw
     * (BOUNDING-BOX (x1 y1) (x2 y2))           Same @Param as Square
     * (DRAW c g1 g2 g3 ... )                   Several figure, with the preceding commands and color c
     * (FILL c g)                               c = color, g = object to draw
     */


    private class mAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            String input = getText().toUpperCase();
            boolean inputParsed = false;

            if (input.contains("DRAW")){
                /**
                 * MULTIPLE FIGURE NEEDS TO BE DRAWN
                 */
                inputParsed = true;
            }else if (input.contains("LINE")){
                /**
                 * DRAW LINE
                 */
                graphicsPlane.drawPixels(FigurTegnerenScala.line(50,750,50,350), Color.black);
                inputParsed = true;
            }else if(input.contains("RECTANGLE")){
                /**
                 * DRAW RECTANGLE
                 */
                graphicsPlane.drawPixels(FigurTegnerenScala.square(50,750,50,350), Color.black);
                inputParsed = true;
            }else if(input.contains("CIRCLE")){
                /**
                 * DRAW CIRCLE
                 */
                graphicsPlane.drawPixels(FigurTegnerenScala.circle(999/2,668/2,668/2,3000), Color.black);
                inputParsed = true;
            }else if(input.contains("TEXT-AT")){
                /**
                 * DRAW TEXT
                 */
                inputParsed = true;
            }else if(input.contains("BOUNDING-BOX")){
                /**
                 * DRAW BOUNDING BOX
                 */
                inputParsed = true;
            }else if(input.contains("FILL")){
                /**
                 * FILL OBJECT WITH COLOR
                 */
                inputParsed = true;
            }else if(input.compareTo("CLR") == 0){
                graphicsPlane.clear(Color.WHITE);
                inputParsed = true;
            }

            if (inputParsed){
                outputConsole.addTextToField(getText());
                setText(null);
            }else {
                JOptionPane.showMessageDialog(null, "Dafuq u doing my boi");
            }
        }
    }


}
