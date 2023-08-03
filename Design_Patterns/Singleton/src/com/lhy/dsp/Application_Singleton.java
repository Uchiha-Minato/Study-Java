package com.lhy.dsp;

import javax.swing.*;
import java.awt.*;

public class Application_Singleton {

    public static void main(String[] args) {
        MyFrame frame1 = new MyFrame("张三看月亮");
        MyFrame frame2 = new MyFrame("李四看月亮");
        frame1.setBounds(10, 10, 360, 150);
        frame2.setBounds(370, 10, 360, 150);
        frame1.validate();
        frame2.validate();
    }

}

class MyFrame extends JFrame {
    String string;

    MyFrame(String title) {
        setTitle(title);
        Moon moon = Moon.getMoon();
        string = moon.show();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        repaint();
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setFont(new Font("宋体", Font.BOLD, 14));
        graphics.drawString(string, 5, 100);
    }
}