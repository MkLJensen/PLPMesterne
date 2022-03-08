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




        final JTextField testInput = new JTextField(25);
        testInput.setEditable(true);
        testInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputConsole.addTextToField(testInput.getText());
                testInput.setText(null);
            }
        });


        JSplitPane HorzSplitLine = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphicsPlane, outputConsole);
        HorzSplitLine.setSize(getSize());
        HorzSplitLine.setDividerLocation(0.75);

        JSplitPane VertSplitLine = new JSplitPane(JSplitPane.VERTICAL_SPLIT, HorzSplitLine, testInput);
        VertSplitLine.setSize((int) (getWidth()*0.75),getHeight());
        VertSplitLine.setDividerLocation(0.9);

        add(VertSplitLine);



    }
}