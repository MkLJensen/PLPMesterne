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

    private static class PatternMatcher {
        enum CommandType {
            Line,
            Circle,
            Rectangle,
            FillCircle,
            FillRectangle,
            BoundingBox,
            TextAt,
            Clear,
            Unknown
        }

        static Pattern recPattern = Pattern.compile("\\(RECTANGLE\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\)", Pattern.CASE_INSENSITIVE);
        static Pattern circPattern = Pattern.compile("\\(CIRCLE\\s\\(\\d+\\s\\d+\\)\\s\\d+\\)", Pattern.CASE_INSENSITIVE);
        static Pattern linePattern = Pattern.compile("\\(LINE\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\)", Pattern.CASE_INSENSITIVE);
        static Pattern filledRecPattern = Pattern.compile("\\(FILL-RECTANGLE\\s[a-z]\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\)", Pattern.CASE_INSENSITIVE);
        static Pattern filledCircPattern = Pattern.compile("\\(FILL-CIRCLE\\s[a-z]\\s\\(\\d+\\s\\d+\\)\\s\\d+\\)", Pattern.CASE_INSENSITIVE);
        static Pattern bbPattern = Pattern.compile("\\(BOUNDING-BOX\\s\\(\\d+\\s\\d+\\)\\s\\(\\d+\\s\\d+\\)\\)", Pattern.CASE_INSENSITIVE);
        static Pattern textAtPattern = Pattern.compile("\\(TEXT-AT\\s\\(\\d+\\s\\d+\\)\\s.*\\)", Pattern.CASE_INSENSITIVE);
        static Pattern clrPattern = Pattern.compile("(CLR)", Pattern.CASE_INSENSITIVE);

        public static CommandType matchCommand(String input) {
            if(recPattern.matcher(input).find()) {
                return CommandType.Rectangle;
            } else if (circPattern.matcher(input).find()) {
                return CommandType.Circle;
            } else if (linePattern.matcher(input).find()) {
                return CommandType.Line;
            } else if (filledCircPattern.matcher(input).find()) {
                return CommandType.FillCircle;
            } else if (filledRecPattern.matcher(input).find()) {
                return CommandType.FillRectangle;
            } else if (bbPattern.matcher(input).find()) {
                return CommandType.BoundingBox;
            } else if (textAtPattern.matcher(input).find()) {
                return CommandType.TextAt;
            } else if (clrPattern.matcher(input).find()) {
                return CommandType.Clear;
            } else {
                return CommandType.Unknown;
            }
        }
    }

    private class mAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            String input = getText().toUpperCase();

            List<Integer> values;
            PatternMatcher.CommandType commandType = PatternMatcher.matchCommand(input);

            switch(commandType) {
                case Line:
                    values = parseTwoCoordinateInput(input);
                    if (values.size() == 4) {
                        graphicsPlane.drawPixels(FigurTegnerenScala.line(values.get(0),values.get(2),values.get(1),values.get(3), boundingBox), Color.black);
                    }
                    break;
                case Circle:
                    values = parseThreeDigitInput(input);
                    if (values.size() == 3) {
                        graphicsPlane.drawPixels(FigurTegnerenScala.circle(values.get(0),values.get(1),values.get(2),3000, boundingBox, false), Color.black);
                    }
                    break;
                case Rectangle:
                    values = parseTwoCoordinateInput(input);
                    if (values.size() == 4) {
                        graphicsPlane.drawPixels(FigurTegnerenScala.square(values.get(0),values.get(2),values.get(1),values.get(3), boundingBox, false), Color.black);
                    }
                    break;
                case FillCircle:
                    values = parseThreeDigitInput(input);
                    if (values.size() == 3) {
                        graphicsPlane.drawPixels(FigurTegnerenScala.circle(values.get(0),values.get(1),values.get(2),3000, boundingBox, true), Color.black);
                    }
                    break;
                case FillRectangle:
                    values = parseTwoCoordinateInput(input);
                    if (values.size() == 4) {
                        graphicsPlane.drawPixels(FigurTegnerenScala.square(values.get(0),values.get(2),values.get(1),values.get(3), boundingBox, true), Color.black);
                    }
                    break;
                case TextAt:
                    values = extractCoordinate(input);
                    String text = extractText(input);
                    graphicsPlane.drawText(text,
                            values.get(0),
                            values.get(1),
                            graphicsPlane.getHeight());
                    break;
                case BoundingBox:
                    if(boundingBox == null) {
                        boundingBox = new BoundingBox(graphicsPlane.getWidth(), graphicsPlane.getHeight());
                    }

                    values = parseTwoCoordinateInput(input);
                    //(BOUNDING-BOX (50 50) (500 500))
                    if (values.size() != 4){
                        JOptionPane.showMessageDialog(null, "ERROR IN COMMAND");
                    }else{
                        boundingBox.setBottomLeftCoordinate(values.get(0), values.get(1));
                        boundingBox.setTopRightCoordinate(values.get(2), values.get(3));
                    }
                    break;
                case Clear:
                    graphicsPlane.fill(Color.WHITE);
                    boundingBox = null;
                    break;
                case Unknown:
                    JOptionPane.showMessageDialog(null, "Dafuq u doing my boi");
                    return;
            }

            outputConsole.addTextToField(getText());
            setText(null);
        }
    }

    List<Integer> extractCoordinate(String input) {
        Pattern textAtPattern = Pattern.compile("\\(TEXT-AT\\s\\((\\d+)\\s(\\d+)\\)\\s(.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = textAtPattern.matcher(input);

        if(matcher.find()) {
            List<Integer> res = new ArrayList<Integer>();
            res.add(Integer.parseInt(matcher.group(1)));
            res.add(Integer.parseInt(matcher.group(2)));
            return res;
        }

        return new ArrayList<Integer>();
    }

    String extractText(String input) {
        Pattern textAtPattern = Pattern.compile("\\(TEXT-AT\\s\\(\\d+\\s\\d+\\)\\s(.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = textAtPattern.matcher(input);

        if(matcher.find()) {
            return matcher.group(1);
        }

        return "";
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