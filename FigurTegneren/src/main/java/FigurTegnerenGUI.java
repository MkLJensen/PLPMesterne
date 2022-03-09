import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FigurTegnerenGUI extends JFrame{

    public FigurTegnerenGUI() throws HeadlessException {
        final double verticalDivider = 0.9;
        final double horizontalDivider = 0.75;

        setName("FigurTegneren");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int) (screenSize.width*0.7), (int) (screenSize.height*0.7)));

        final OutputConsole outputConsole = new OutputConsole();
        final GraphicsPlane graphicsPlane = new GraphicsPlane();
        final InputConsole inputConsole = new InputConsole(outputConsole, graphicsPlane);
        final JScrollPane scrollPane = new JScrollPane(outputConsole,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        JSplitPane HorzSplitLine = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphicsPlane, scrollPane);
        HorzSplitLine.setSize(getSize());
        HorzSplitLine.setDividerLocation(0.75);

        JSplitPane VertSplitLine = new JSplitPane(JSplitPane.VERTICAL_SPLIT, HorzSplitLine, inputConsole);
        VertSplitLine.setSize((int) (getWidth()*horizontalDivider),getHeight());
        VertSplitLine.setDividerLocation(verticalDivider);

        graphicsPlane.setSize((int)((screenSize.width*0.7)*horizontalDivider),(int)((screenSize.height*0.7)*verticalDivider));
        graphicsPlane.SetupWindow();

        add(VertSplitLine);
    }
}