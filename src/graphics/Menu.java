package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    public static int WIDTH = 1, HEIGHT = 100;

    startButton start;
    exitButton exit;

    public Menu(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        start = new startButton("Jatek inditas");
        start.setBounds(37,11,227,51);
        add(start);
        exit =new exitButton("Kilepes");
        exit.setBounds(37,73,227,51);
        add(exit);

        start.addActionListener(start);
        exit.addActionListener(exit);
    }
    private class startButton extends JButton implements ActionListener{

        public startButton(String text){
            super(text);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Window.getWindow().startGame();
        }
    }

    private class exitButton extends JButton implements ActionListener{

        public exitButton(String text){
            super(text);
        }
        @Override
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
}
