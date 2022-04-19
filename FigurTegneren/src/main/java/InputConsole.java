import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

import scala.Int;
import scala.Tuple2;

public class InputConsole extends JTextField {

    private final OutputConsole outputConsole;
    private final GraphicsPlane graphicsPlane;
    private BoundingBox boundingBox;

    public InputConsole(OutputConsole _outputConsole, GraphicsPlane _graphicsPlane) {
        super();
        setEditable(true);
        outputConsole = _outputConsole;
        graphicsPlane = _graphicsPlane;
        boundingBox = null;
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
                    graphicsPlane.drawPixels(FigurTegnerenScala.line(values.get(0),values.get(2),values.get(1),values.get(3), boundingBox), Color.black);
                    inputParsed = true;
                }
            }else if(input.contains("RECTANGLE")){
                /**
                 * DRAW RECTANGLE
                 */
                List<Integer> values = parseTwoCoordinateInput(input);
                if (values.size() == 4) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.square(values.get(0),values.get(2),values.get(1),values.get(3), boundingBox), Color.black);
                    inputParsed = true;
                }
            }else if(input.contains("CIRCLE")){
                /**
                 * DRAW CIRCLE
                 */
                List<Integer> values = parseThreeDigitInput(input);
                if (values.size() == 3) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.circle(values.get(0),values.get(1),values.get(2),3000, boundingBox), Color.black);
                    inputParsed = true;
                }
            }else if(input.contains("TEXT-AT")){
                // Wow these first two lines are UGLY
                String[] fullInput = input.split("\\) ");
                String text = fullInput[1];
                List<Integer> coords = parseDigitsFromStringInput(fullInput[0]);
                graphicsPlane.drawText(text.substring(0, text.length()-1), coords.get(0), coords.get(1), graphicsPlane.getHeight());

                inputParsed = true;
            }else if(input.contains("BOUNDING-BOX")){

                /**
                 * Changing Bounding Box Value in Scala
                 */

                if(boundingBox == null) {
                    boundingBox = new BoundingBox(graphicsPlane.getWidth(), graphicsPlane.getHeight());
                }

                List<Integer> coords;
                coords = parseTwoCoordinateInput(input);
                //(BOUNDING-BOX (50 50) (500 500))
                if (coords.size() != 4){
                    JOptionPane.showMessageDialog(null, "ERROR IN COMMAND");
                }else{
                    boundingBox.setBottomLeftCoordinate(coords.get(0), coords.get(1));
                    boundingBox.setTopRightCoordinate(coords.get(2), coords.get(3));
                }

                /*
                if ((boundingBox.getTopRightCoordinate()._1() == null) ||
                    (boundingBox.getTopRightCoordinate()._2() == null) ||
                    (boundingBox.getBottomLeftCoordinate()._1() == null) ||
                    (boundingBox.getBottomLeftCoordinate()._2() == null))
                {
                    JOptionPane.showMessageDialog(null, "Bounding Box Outside of Area");
                }
                */

                /* Dont think we need to draw this?
                else{
                    graphicsPlane.drawPixels(FigurTegnerenScala.square((Integer) boundingBox.getBottomLeftCoordinate()._1(),
                                                                (Integer) boundingBox.getTopRightCoordinate()._1(),
                                                                (Integer) boundingBox.getBottomLeftCoordinate()._2(),
                                                                (Integer) boundingBox.getTopRightCoordinate()._2()), Color.black);
                    graphicsPlane.fillRectangle(Color.GRAY, (Integer) boundingBox.getBottomLeftCoordinate()._1(),
                                                            (Integer) boundingBox.getBottomLeftCoordinate()._2(),
                                                            (Integer) boundingBox.getTopRightCoordinate()._1(),
                                                            (Integer) boundingBox.getTopRightCoordinate()._2());
                }
                */

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
                graphicsPlane.fill(Color.WHITE);
                boundingBox = null;
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
        Pattern p1 = Pattern.compile("\\(\\d+ \\d+\\) \\d+");
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

    List<Integer> parseDigitsFromStringInput(String input){
        //Returns all digits in a string as a list of integers

        List<Integer> res = new ArrayList<Integer>();

        input = input.replaceAll("[^\\d]", " ");
        input = input.trim();
        input = input.replaceAll(" +", " ");
        String[] integerStrings = input.split(" ");

        for (String string: integerStrings){
            res.add(Integer.parseInt(string));
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