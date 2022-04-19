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
        if (setup && pixels.size() == 2) {
            for (int i = 0; i < pixels.get(0).size(); i++)
            {
                int pixel_x = (int)pixels.get(0).get(i);
                int pixel_y = getHeight() - (int)pixels.get(1).get(i);
                bi.setRGB(pixel_x, pixel_y, color.getRGB());
            }
            updateUI();
        }
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

    public void fillRectangle(Color color, int x1, int y1, int x2, int y2) {
        if (setup) {
            for (int x = x1; x < x2; x++)
            {
                for (int y = getHeight() - y1; y > getHeight()-y2; y--)
                {
                    bi.setRGB(x, y, color.getRGB());
                }
            }
            updateUI();
        }
    }


}
