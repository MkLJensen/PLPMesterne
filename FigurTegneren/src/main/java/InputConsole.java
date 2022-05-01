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

    private void drawMultipleCommands(String input) {
        final Matcher matcher = PatternMatcher.objectPattern.matcher(input);
        while (matcher.find()) {
            String commandInput = matcher.group();
            PatternMatcher.CommandType type = PatternMatcher.matchCommand(commandInput);
            drawCommand(type, commandInput);
        }
    }

    private void drawCommand(PatternMatcher.CommandType type, String input) {
        Color fillColor;
        List<Integer> values;

        switch(type) {
            case Line:
                values = InputParser.parseTwoCoordinateInput(input);
                if (values.size() == 4) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.line(values.get(0),values.get(2),values.get(1),values.get(3), boundingBox), Color.black);
                }
                break;
            case Circle:
                values = InputParser.parseThreeDigitInput(input);
                if (values.size() == 3) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.circle(values.get(0),values.get(1),values.get(2),3000, boundingBox, false), Color.black);
                }
                break;
            case Rectangle:
                values = InputParser.parseTwoCoordinateInput(input);
                if (values.size() == 4) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.square(values.get(0),values.get(2),values.get(1),values.get(3), boundingBox, false), Color.black);
                }
                break;
            case FillCircle:
                values = InputParser.parseThreeDigitInput(input);
                fillColor = InputParser.parseFillColorInput(input);
                if (values.size() == 3) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.circle(values.get(0),values.get(1),values.get(2),values.get(2)*8, boundingBox, true), fillColor);
                }
                break;
            case FillRectangle:
                values = InputParser.parseTwoCoordinateInput(input);
                fillColor = InputParser.parseFillColorInput(input);
                if (values.size() == 4) {
                    graphicsPlane.drawPixels(FigurTegnerenScala.square(values.get(0),values.get(2),values.get(1),values.get(3), boundingBox, true), fillColor);
                }
                break;
            case TextAt:
                values = InputParser.extractCoordinate(input);
                String text = InputParser.extractText(input);
                graphicsPlane.drawText(text,
                        values.get(0),
                        values.get(1),
                        graphicsPlane.getHeight());
                break;
            case BoundingBox:
                if(boundingBox == null) {
                    boundingBox = new BoundingBox(graphicsPlane.getWidth(), graphicsPlane.getHeight());
                }

                values = InputParser.parseTwoCoordinateInput(input);
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
    }

    private class mAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            String input = getText().toUpperCase();

            PatternMatcher.CommandType commandType = PatternMatcher.matchCommand(input);

            if(commandType == PatternMatcher.CommandType.Draw) {
                drawMultipleCommands(input);
            } else {
                drawCommand(commandType, input);
            }

            outputConsole.addTextToField(getText());
            setText(null);
        }
    }
}