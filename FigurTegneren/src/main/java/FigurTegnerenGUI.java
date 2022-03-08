import javax.swing.*;
import java.awt.*;

public class FigurTegnerenGUI extends JFrame{

    public FigurTegnerenGUI() throws HeadlessException {
        setName("FigurTegneren");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int) (screenSize.width*0.7), (int) (screenSize.height*0.7)));

        JSplitPane splitLine = new JSplitPane();
        splitLine.setSize(getSize());
        splitLine.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitLine.setDividerLocation(0.75);

        final JTextField outputArea = new JTextField(25);
        JScrollPane scrollText = new JScrollPane(outputArea);
        outputArea.setEditable(false);
        splitLine.setRightComponent(scrollText);
        add(splitLine);



    }
}