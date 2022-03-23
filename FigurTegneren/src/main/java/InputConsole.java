import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

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
                List<Integer> values = parseTwoCoordinateInput(input);
                if (values.size() == 4) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.line(values.get(0),values.get(2),values.get(1),values.get(3)), Color.black);
                    inputParsed = true;
                }
            }else if(input.contains("RECTANGLE")){
                /**
                 * DRAW RECTANGLE
                 */
                List<Integer> values = parseTwoCoordinateInput(input);
                if (values.size() == 4) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.square(values.get(0),values.get(2),values.get(1),values.get(3)), Color.black);
                    inputParsed = true;
                }
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
                graphicsPlane.drawText("Hello World :D", 200, 300);

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

    List<Integer> parseTwoCoordinateInput(String input) {
        List<Integer> res = new ArrayList<Integer>();
        Pattern p1 = Pattern.compile("\\(\\d+ \\d+\\) \\(\\d+ \\d+\\)");
        Matcher m1 = p1.matcher(input);

        if (m1.find()) {
            Pattern p2 = Pattern.compile("-?\\d+");
            Matcher m2 = p2.matcher(input);
            while (m2.find()) {
                res.add(Integer.parseInt(m2.group()));
            }
        }
        return res;
    }

    List<Integer> parseThreeDigitInput(String input) {
        List<Integer> res = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("\\(\\d+ \\d+ \\d+\\)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            Pattern integerPattern = Pattern.compile("-?\\d+");
            matcher = pattern.matcher(input);
            while (matcher.find()) {
                res.add(Integer.parseInt(matcher.group()));
            }
        }
        return res;
    }
}

/*
        if (pattern.find()) {
            Pattern integerPattern = Pattern.compile("-?\\d+");
            Matcher matcher = integerPattern.matcher(integerPattern.group());

            List<Integer> res = new ArrayList<Integer>();

            while (matcher.find()) {
                res.add(Integer.parseInt(matcher.group()));
            }
        }
 */