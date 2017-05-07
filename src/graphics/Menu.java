package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    public static final int WIDTH = 100, HEIGHT = 100;

    JButton start;
    JButton exit;

    public Menu(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        start =new JButton("Játék indítás");
        start.setBounds(37,11,227,51);
        add(start);
        exit =new JButton("Kilépés");
        exit.setBounds(37,73,227,51);
        add(exit);

        //az első gomb eseménykezelője, amennyiben megnyomják, elindítja a játékot
        start.addActionListener(
                new ActionListener(){
                     public void actionPerformed(ActionEvent e){
                        try {
                            Window.getWindow().startGame();
                        }catch (Exception e1) {
                            System.out.println(e1.getMessage());
                        }
                    }
                }
        );

        //a második gomb eseménykezelője, amennyiben megnyomják, kilép a programból
        exit.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        System.exit(0);
                    }
                }
        );
    }
}
