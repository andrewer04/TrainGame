package graphics;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{

    private static Window instance = null;

    private Game game;
    private Menu menu;

    /*  A konstruktorban beallitjuk az ablakunk tulajdonsagait, singleton mintat hasznaltunk a
    *   megvalositasahoz, igy private konstruktorral rendelkezik, illetve vissza tud magarol adni
    *   egy peldanyt, ami statikus, igy egyetlen darab letezhet a windowbol.
    */
    private Window() {
        setFocusable(true);
        setTitle("TrainGame");

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        menu = new Menu();
        add(BorderLayout.CENTER,menu);

        this.pack();
        this.setVisible(true);

    }

    public static Window getWindow() {
        if (instance == null) {
            instance = new Window();
        }
        return instance;
    }

    public void startGame() {

        game = new Game();

        remove(menu);
        add(BorderLayout.CENTER,game);

        pack();
        setLocationRelativeTo(null);
    }
}
