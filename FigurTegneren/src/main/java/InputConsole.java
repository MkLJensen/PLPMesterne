import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

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

    private class mAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getText().compareTo("CIRCLE") == 0) {
                graphicsPlane.drawPixels(FigurTegnerenScala.circle(500,300,300,3000), Color.black);
            }
            else if (getText().compareTo("LINE") == 0) {
                graphicsPlane.drawPixels(FigurTegnerenScala.line(50,750,50,350), Color.black);
            }
            else if (getText().compareTo("SQUARE") == 0) {
                graphicsPlane.drawPixels(FigurTegnerenScala.square(50,750,50,350), Color.black);
            }
            else if (getText().compareTo("CLR") == 0) {
                graphicsPlane.clear(Color.WHITE);
            }
            outputConsole.addTextToField(getText());
            setText(null);
        }
    }


}
