import javax.swing.*;
import java.awt.*;

//public class OutputConsole extends JScrollPane {
public class OutputConsole extends JTextArea {
    //final JTextArea outputArea = new JTextArea();

    public OutputConsole() {
        super();
        setEditable(false);
        setLineWrap(true);
        setSize(getSize());
        setBackground(new Color(192,192,192));
        setFont(getFont().deriveFont(14f).deriveFont(Font.BOLD));
    }

    public void addTextToField(String text){
        if (getText() != null){
            setText(getText().concat(text.toUpperCase()).concat("\r\n"));
        }
    }

}
