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
            List<List<Object>> buf2 = FigurTegnerenScala.circle(0,0,5,30);
            outputConsole.addTextToField(getText());


            setText(null);
        }
    }


}
