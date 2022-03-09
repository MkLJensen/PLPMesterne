import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FigurTegnerenGUI extends JFrame{

    public FigurTegnerenGUI() throws HeadlessException {
        setName("FigurTegneren");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int) (screenSize.width*0.7), (int) (screenSize.height*0.7)));

        final OutputConsole outputConsole = new OutputConsole();
        final GraphicsPlane graphicsPlane = new GraphicsPlane();
        //final InputConsole inputConsole = new InputConsole(outputConsole);
        final JScrollPane scrollPane = new JScrollPane(outputConsole,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        final JTextField testInput = new JTextField(25);
        testInput.setEditable(true);
        testInput.addActionListener(e -> {
            outputConsole.addTextToField(testInput.getText());
            testInput.setText(null);
        });


        JSplitPane HorzSplitLine = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphicsPlane, scrollPane);
        HorzSplitLine.setSize(getSize());
        HorzSplitLine.setDividerLocation(0.75);

        JSplitPane VertSplitLine = new JSplitPane(JSplitPane.VERTICAL_SPLIT, HorzSplitLine, testInput);
        VertSplitLine.setSize((int) (getWidth()*0.75),getHeight());
        VertSplitLine.setDividerLocation(0.9);

        add(VertSplitLine);



    }
}