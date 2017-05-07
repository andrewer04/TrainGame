package graphics;

import map.Switch;
import map.Tunnel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    //Az eger esemenykezeloje
    //Csak a kattintast hasznaljuk, a tobbi fuggveny nem szukseges a jatek szempontjabol
    class MouseEventHandler implements MouseListener {
        //A kattintas esemenyenek kezelese
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(e.getX()+ "  " + e.getY());
            int x=e.getX()/40;
            int y=e.getY()/40;
            System.out.println(x+ "         " + y);
            //Ha a mezo alagut
            if(game.mapCreator.getField(x,y) instanceof Tunnel){
                //Ha az alagut ki volt valasztva akkor toroljuk
                if(((Tunnel) game.mapCreator.getField(x,y)).isSelected()){
                    ((Tunnel) game.mapCreator.getField(x,y)).deleting();
                }

                //Ha az alagut nem volt kivalasztva akkor lerakjuk
                if(!((Tunnel) game.mapCreator.getField(x,y)).isSelected()){
                    ((Tunnel) game.mapCreator.getField(x,y)).creating();
                }
            }
            //Ha a mezo valto
            if(game.mapCreator.getField(x,y) instanceof Switch){

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
