package graphics;

import application.Controller;
import application.MapCreator;
import application.Timer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable{

    public static final int WIDTH = 800, HEIGHT = 800;
    public static MapCreator mapCreator;
    public static Controller controller;
    private Drawer drawer;
    private ArrayList<Drawable> drawables; //ebben gyűjtjük az összes objektumot amit ki lehet rajzolni

    Thread thread;

    public Game(){
        mapCreator = new MapCreator();
        drawer = new Drawer();
        controller = new Controller(mapCreator.build(5));
        drawables = mapCreator.getMapElements();

        thread = new Thread(this,"Game Loop");

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);

        thread.start();
    }

    public void gameOver(){
        JOptionPane.showMessageDialog(
                null,
                "Meghaltál, a játék véget ért",
                "GAME OVER",
                JOptionPane.WARNING_MESSAGE
        );
    }
    public void youWon(){}

    @Override
    public void run() {
        controller.makeTrain(3,drawables);
        while(controller.getStatus()){
            if (Timer.start()){
                controller.run();
            }
            repaint(); //ez mindig meghívja a paint metódust

        }
        if(controller.getLoseFlag())
            gameOver();
        else youWon();
    }

    @Override
    public void paint(Graphics g) {
        drawer.setGraphics(g);
        for(Drawable drawable: drawables){
            drawable.draw(drawer);
        }
    }
}
