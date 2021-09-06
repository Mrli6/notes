import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Test {
    public static void main(String[] args) throws NoSuchMethodException {
        ActionListener listener = new TimePrinter();

        // 一秒打印一次
        Timer t = new Timer(1000, listener);
        t.start();

        JOptionPane.showMessageDialog(null, "Quit program");
        System.exit(0);
    }
}

class TimePrinter implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("at the tone, the time is " + new Date());
        Toolkit.getDefaultToolkit().beep();
    }
}