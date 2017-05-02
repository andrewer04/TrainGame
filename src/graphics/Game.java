package graphics;

import application.Controller;
import application.MapCreator2;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {

    public static final int WIDTH = 800, HEIGHT = 800;

    public Game(){
        MapCreator2 mapCreator = new MapCreator2();
        Controller controller = new Controller(mapCreator.build(1));

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
    }

    public void gameOver(){
        JOptionPane.showMessageDialog(
                null,
                "Meghaltál, a játék véget ért",
                "GAME OVER",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
