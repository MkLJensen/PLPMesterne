import java.awt.*;
import java.util.List;

public class highlightThread extends Thread{

    Color revertColor;
    GraphicsPlane graphicsPlane;
    List<List<Object>> pixels;

    public highlightThread(GraphicsPlane gp, List<List<Object>> p, Color c) {
        revertColor = c;
        graphicsPlane = gp;
        pixels = p;
    }

    @Override
    public void run() {
        drawShit(Color.green);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        drawShit(revertColor);
        graphicsPlane.revalidate();
        graphicsPlane.repaint();

    }

    private void drawShit(Color color){
        if (graphicsPlane.setup && pixels.size() == 2) {
            for (int i = 0; i < pixels.get(0).size(); i++)
            {
                int pixel_x = (int)pixels.get(0).get(i);
                int pixel_y = graphicsPlane.getHeight() - (int)pixels.get(1).get(i);
                graphicsPlane.bi.setRGB(pixel_x, pixel_y, color.getRGB());
            }
            graphicsPlane.updateUI();
        }
    }

}
