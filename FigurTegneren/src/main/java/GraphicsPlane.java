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
        clear(Color.WHITE);
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

    public void drawText(String text, int x, int y) {
        Graphics2D t = bi.createGraphics();
        t.setComposite(AlphaComposite.Src);
        t.setPaint(Color.BLACK);
        t.drawString(text, x,y);
        updateUI();
    }

    public void clear(Color color) {
        if (setup) {
            for (int x = 0; x < bi.getWidth(); x++)
            {
                for (int y = 0; y < bi.getHeight(); y++)
                {
                    bi.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
            updateUI();
        }
    }
}
