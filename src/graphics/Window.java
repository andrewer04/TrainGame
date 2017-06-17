package graphics;

import application.Controller;
import map.Switch;
import map.Tunnel;
import application.MapCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

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

        game = new Game();
        game.init(level);

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

        game = new Game();
        game.init(++level);

        game.addMouseListener(new MouseEventHandler());

        gameThread = new Thread(game,"Game Loop");

        add(BorderLayout.CENTER,game);

        gameThread.start();

        pack();
        setLocationRelativeTo(null);
    }

    public void load(){
        JFileChooser loadFile = new JFileChooser();
        loadFile.showOpenDialog(this);

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(loadFile.getSelectedFile()));
            game = new Game();

            //inicializáljuk a game objektumot az init() helyett most betöltéssel
            ArrayList<Drawable> drawables = (ArrayList<Drawable>) is.readObject();
            game.setDrawables(drawables);

            MapCreator mapCreator = (MapCreator) is.readObject();
            Game.setMapCreator(mapCreator);

            Controller controller = (Controller) is.readObject();
            Game.setController(controller);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Nem jó mentés",
                    "WRONG",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //ha sikerült, akkor úgy megyünk tovább mint normál esetben
        game.addMouseListener(new MouseEventHandler());

        gameThread = new Thread(game,"Game Loop");

        remove(menu);
        add(BorderLayout.CENTER,game);

        gameThread.start();

        pack();
        setLocationRelativeTo(null);
    }

    public void save(){
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("save_" + Calendar.getInstance() + ".ser"));
            os.writeObject(game.getDrawables());
            os.writeObject(Game.getMapCreator());
            os.writeObject(Game.getController());

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    class KeyEventHandler implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
