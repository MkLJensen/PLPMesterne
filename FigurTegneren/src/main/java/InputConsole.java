import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputConsole extends JTextField implements KeyListener {

    private final OutputConsole outputConsole;

    public InputConsole(OutputConsole _outputConsole) {
        super();
        setEditable(true);
        setColumns(25);
        outputConsole = _outputConsole;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        outputConsole.addTextToField(getText());
        setText(null);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
