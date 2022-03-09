import scala.Console;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InputConsole extends JTextField {

    private final OutputConsole outputConsole;

    public InputConsole(OutputConsole _outputConsole) {
        super();
        setEditable(true);
        outputConsole = _outputConsole;
        final mAction action = new mAction();
        addActionListener(action);
    }

    private class mAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<List<Object>> buf = FigurTegnerenScala.line((float) 0,(float) 5,(float) 0,(float) 5);
            outputConsole.addTextToField(getText());


            setText(null);
        }
    }


}
