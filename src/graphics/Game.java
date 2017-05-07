package graphics;

import application.Controller;
import application.MapCreator;
import application.Timer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable{

    public static final int WIDTH = 520, HEIGHT = 520;
    public static MapCreator mapCreator;
    public static Controller controller;
    private Drawer drawer;
    private ArrayList<Drawable> drawables; //ebben gyűjtjük az összes objektumot amit ki lehet rajzolni

    Thread gameThread;

    public Game(){
        mapCreator = new MapCreator();
        drawer = new Drawer();
        controller = new Controller(mapCreator.build(1));
        drawables = mapCreator.getMapElements();

        gameThread = new Thread(this,"Game Loop");

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);

        gameThread.start();
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
        controller.makeTrain(7,drawables);
        while(controller.getStatus()){
            repaint();
            try{
                Thread.sleep(60);
                controller.run();
            }catch (InterruptedException e){

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
