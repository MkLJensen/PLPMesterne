import javax.swing.*;

public class FigurTegnerenGUI extends JFrame{
    public static void main(String[] args) {
        JFrame f=new JFrame();//creating instance of JFrame

        JButton b=new JButton("click");//creating instance of JButton
        b.setBounds(130,100,100, 40);//x axis, y axis, width, height

        int x = 2;
        b.addActionListener(e -> JOptionPane.showMessageDialog(b, "Sum is: " + FigurTegnerenScala.fact(10)));

        f.add(b);//adding button in JFrame

        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }
}