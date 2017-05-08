package graphics;

import map.Switch;
import map.Tunnel;
import application.MapCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Window extends JFrame{

    private static Window instance = null;

    private Game game;
    private Menu menu;
    public static int level = 1;

    Thread gameThread;

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

    /**
     * A jatek elinditasaert felelos metodus
     */
    public void startGame() {

        game = new Game(level);
        game.addMouseListener(new MouseEventHandler());

        gameThread = new Thread(game,"Game Loop");

        remove(menu);
        add(BorderLayout.CENTER,game);

        gameThread.start();

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * A kovetkezo palya betolteseert felelos metodus
     */
    public void nextLevel(){
        remove(game);

        game = new Game(++level);
        game.addMouseListener(new MouseEventHandler());

        gameThread = new Thread(game,"Game Loop");

        add(BorderLayout.CENTER,game);

        gameThread.start();

        pack();
        setLocationRelativeTo(null);
    }
    //Az eger esemenykezeloje
    //Csak a kattintast hasznaljuk, a tobbi fuggveny nem szukseges a jatek szempontjabol
    class MouseEventHandler implements MouseListener {
        //A kattintas esemenyenek kezelese
        @Override
        public void mouseClicked(MouseEvent e) {
          //  System.out.println(e.getX()+ "  " + e.getY());
            int x=e.getX()/40;
            int y=e.getY()/40;
         //   System.out.println(x+ "         " + y);
            //Ha a mezo alagut
            if(MapCreator.getField(x,y) instanceof Tunnel){
                //Ha az alagut ki volt valasztva akkor toroljuk
                if(((Tunnel) MapCreator.getField(x,y)).isSelected()) {
                    ((Tunnel) MapCreator.getField(x, y)).deleting();
                }
                //Ha az alagut nem volt kivalasztva akkor lerakjuk
                else if(!((Tunnel) MapCreator.getField(x,y)).isSelected()){
                    ((Tunnel) MapCreator.getField(x,y)).creating();
                }
            }
            //Ha a mezo valto
            if(MapCreator.getField(x,y) instanceof Switch){
                ((Switch) MapCreator.getField(x,y)).switching();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }
}
