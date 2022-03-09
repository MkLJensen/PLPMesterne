import javax.swing.*;
import java.awt.*;

public class mainGUI {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            JFrame frame = new FigurTegnerenGUI();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        };
        EventQueue.invokeLater(runnable);
    }
}
