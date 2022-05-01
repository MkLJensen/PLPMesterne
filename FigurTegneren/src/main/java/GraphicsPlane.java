import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

//https://stackoverflow.com/questions/2163544/re-paint-on-translucent-frame-panel-component/2166500#2166500

public class GraphicsPlane extends JPanel {

    boolean setup = false;
    BufferedImage bi;

    public GraphicsPlane() throws HeadlessException {
        super();
        setBackground(Color.WHITE);
        setVisible(true);
    }

    public void SetupWindow() {
        bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ImageIcon icon = new ImageIcon( bi );
        add( new JLabel(icon) );

        for (int x = 0; x < getWidth(); x++)
        {
            for (int y = 0; y < getHeight(); y++)
            {
                bi.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
        setup = true;
        fill(Color.WHITE);
    }

    public void drawPixels(List<List<Object>> pixels, Color color) {
        Thread t = new highlightThread(this, pixels, color);
        t.start();
    }

    public void drawText(String text, int x, int y, int screenHeight) {
        Graphics2D t = bi.createGraphics();
        t.setComposite(AlphaComposite.Src);
        t.setPaint(Color.BLACK);
        Tuple<Integer, Integer> converted = convertTextCoordinates(x,y, screenHeight);
        t.drawString(text, converted.getFirst(),converted.getSecond());
        updateUI();
    }

    private Tuple<Integer, Integer> convertTextCoordinates(int x, int y, int screenHeight) {
        int convertedY = screenHeight - y;
        return new Tuple<>(x, convertedY);
    }

    public void fill(Color color) {
        if (setup) {
            for (int x = 0; x < bi.getWidth(); x++)
            {
                for (int y = 0; y < bi.getHeight(); y++)
                {
                    bi.setRGB(x, y, color.getRGB());
                }
            }
            updateUI();
        }
    }
}
