import javax.swing.*;
import java.awt.*;

public class mainGUI {
    public static void main(String[] args) {
        FigurTegnerenScala.line(0,6, 0,10);

        Runnable runnable = () -> {
            JFrame frame = new FigurTegnerenGUI();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        };
        EventQueue.invokeLater(runnable);
    }
}
