import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FigurTegnerenGUI extends JFrame{

    public FigurTegnerenGUI() throws HeadlessException {
        final double windowWidth = 0.9;
        final double windowHeight = 0.9;
        final double verticalDivider = 0.9;
        final double horizontalDivider = 0.75;

        setName("FigurTegneren");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int) (screenSize.width*windowWidth), (int) (screenSize.height*windowHeight)));
        setResizable(false); // IF this is not done we can resize the window fucking the drawing up!
        // MAX SIZE LINE (0 0) (1295 862)
        final OutputConsole outputConsole = new OutputConsole();
        final GraphicsPlane graphicsPlane = new GraphicsPlane();
        final InputConsole inputConsole = new InputConsole(outputConsole, graphicsPlane);
        final JScrollPane scrollPane = new JScrollPane(outputConsole,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        JSplitPane HorzSplitLine = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphicsPlane, scrollPane);
        HorzSplitLine.setSize(getSize());
        HorzSplitLine.setDividerLocation(horizontalDivider);

        JSplitPane VertSplitLine = new JSplitPane(JSplitPane.VERTICAL_SPLIT, HorzSplitLine, inputConsole);
        VertSplitLine.setSize((int) (getWidth()*horizontalDivider),getHeight());
        VertSplitLine.setDividerLocation(verticalDivider);

        graphicsPlane.setSize((int)((screenSize.width*windowWidth)*horizontalDivider),(int)((screenSize.height*windowHeight)*verticalDivider));
        graphicsPlane.SetupWindow();

        add(VertSplitLine);
    }
}