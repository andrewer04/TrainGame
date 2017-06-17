package graphics;

import application.Controller;
import application.MapCreator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable{

    public static final int WIDTH = 520, HEIGHT = 520;
    private final int DELAY = 100;
    public static MapCreator mapCreator;
    public static Controller controller;
    private Drawer drawer;
    private ArrayList<Drawable> drawables; //ebben gyujtjuk az osszes objektumot amit ki lehet rajzolni

    public Game(){
        drawer = new Drawer();

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
    }

    public static void setMapCreator(MapCreator mapCreator) {
        Game.mapCreator = mapCreator;
    }

    public static void setController(Controller controller) {
        Game.controller = controller;
    }

    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    public static MapCreator getMapCreator() {
        return mapCreator;
    }

    public static Controller getController() {
        return controller;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    /**
     * A jatek veget jelzo ablak
     */
    public void gameOver(){
        JOptionPane.showMessageDialog(
                null,
                "Meghaltal, a jatek veget ert",
                "GAME OVER",
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * A gyozelmet jelzo ablak
     */
    public void youWon(){
        JOptionPane.showMessageDialog(
                null,
                "Gyozelem",
                "WIN",
                JOptionPane.WARNING_MESSAGE
        );
        if(Window.level == 1){
            Window.getWindow().nextLevel();
        }
        else{
            JOptionPane.showMessageDialog(
                    null,
                    "A jatek vege, nyertel",
                    "WON",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Palyat inicializaolo metodus
     * @param level a palya szintje
     */
    public void init(int level){
        mapCreator = new MapCreator();
        controller = new Controller(mapCreator.build(level));
        drawables = mapCreator.getMapElements();
    }

    @Override
    public void run() {
        int length = 6;
        int ticker = 0;
        controller.makeTrain(length,drawables);
        while(controller.getStatus()){
            if(ticker == DELAY){
                if (length >= 3)
                    controller.makeTrain(--length,drawables);
                ticker = 0;
            }
            repaint();
            try{
                Thread.sleep(100);
                ticker++;
                controller.run();
            }catch (InterruptedException e){

            }

            repaint(); //ez mindig meghivja a paint metodust

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
