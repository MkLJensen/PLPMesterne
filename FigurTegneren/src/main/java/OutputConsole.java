import javax.swing.*;
import java.awt.*;

public class OutputConsole extends JScrollPane {

    JTextField outputArea = new JTextField(25);

    public OutputConsole() {
        super();
        outputArea.setEditable(false);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(outputArea);
    }

    public void addTextToField(String text){
        if (outputArea.getText() != null){

            //((JTextField)(outputConsole.getViewport().getView())).setText("HEJ");
            outputArea.setText(outputArea.getText().concat(text).concat("\r\n"));
            updateUI();
        }
    }

}
