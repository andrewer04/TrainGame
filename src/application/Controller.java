package application;

import graphics.Drawable;
import map.Rail;
import vehicles.CargoWagon;
import vehicles.Locomotive;
import vehicles.Train;
import vehicles.Wagon;

import java.util.ArrayList;
import java.awt.Color;

public class Controller {
    private Rail start;
    private ArrayList<Train[]> trains = new ArrayList<>();
    private boolean winFlag;
    private boolean loseFlag;

    public Controller(Rail start){
        this.start = start;
        winFlag = false;
        loseFlag = false;
    }

    /**
     * A jatek allapotanak lekerdezese.
     * @return Ha meg nem nyert a jatekos, illetve nem vesztett, akkor igazzal ter vissza, egyebkent hamissal.
     */
    public boolean getStatus(){
        if (winFlag || loseFlag)
            return false;
        else return true;
    }

    public boolean getLoseFlag() {
        return loseFlag;
    }

    /**
     * Amennyiben utkozes tortent a palyan, ez a fuggveny allitja be a
     * jatek veget.
     */
    private void lose(){
        loseFlag = true;
    }

    /**
     * Amennyiben az osszes kocsi kiurult, ez a fuggveny allitja be a
     * gyozelmet.
     */
    private void win(){
        winFlag = true;
    }
    /**
     * Vonat letrehozasa, a Locomotive van a StartRail-en
     * @param length a vonat hossza
     * @param drawables a kirajzolando objektumokat tartalmazo lista
     */
    public void makeTrain(int length, ArrayList<Drawable> drawables) {

        Train[] train = new Train[length];

        //letrehozza a locomotiveokat
        train[0] = new Locomotive(start, length);
        start.setAvailability(true);

        //letrehozza a wagonokat
        for (int j = 1; j < length; j++) {
            switch (length-(length-j)) {
                case 1:
                    train[j] = new CargoWagon(null);
                    break;
                case 2:
                    train[j] = new Wagon(null, Color.RED);
                    break;
                case 3:
                    train[j] = new Wagon(null, Color.GREEN);
                    break;
                case 4:
                    train[j] = new Wagon(null, Color.BLUE);
                    break;
                default:
                    train[j] = new Wagon(null, Color.ORANGE);
                    break;
            }
        }

        //beallitja a waqgonokat
        for (int j = 1; j < length; j++) {
            if (j == length - 1) { //azaz utolso kocsi
                train[j].setNext(null);
                train[j].setPrev(train[j - 1]);
                train[j].setPrevRail(null);
            } else {
                train[j].setNext(train[j + 1]);
                train[j].setPrev(train[j - 1]);
                train[j].setPrevRail(null);
            }
        }

        //beallitja a locomotivokat
        train[0].setPrevRail(null);
        train[0].setPrev(null);
        train[0].setNext(train[1]);

        trains.add(train);
        for (int i = 0; i<length; i++){
            drawables.add(train[i]);
        }
    }

    /**
    * Leszallitja a wagonrol az utasokat.
    */
    private void startLeaving(){
        for (Train[] train: trains){
            train[0].leave();
        }
    }
    /**
     * Felszallitja a wagonra az utasokat.
     */
    private void startGetOn(){
        for (Train[] train: trains){
            train[0].getOn();
        }
    }
    /**
     * Egyet lepet az osszes vonaton
     */
    private void startStepping(){
        for (Train[] train: trains){
            train[0].move();
        }
    }
    /**
    * Utkozes ellenorzese
    * Utkozes eseten lose() fuggveny meghivasa
    */
    private void checkCollision(){
        boolean seged = false;
        for (Train[] train: trains){
            if(train[0].detectCollision()) {
                //lose();
                seged = true;
            }
        }
        if (seged) lose();
    }
    /**
     * Vonatok uressegenek vizsgalata
     * Ha mind ures, a jatekos nyert.
     */
    private void checkEmptiness(){
        boolean seged = false;
        for (Train[] train: trains){
            if(train[0].detectEmptiness()) {
                //win();
                seged = true;
            }
        }
        if (seged) win();
    }
    /**
     * Vonattal kapcsolatos tevekenysegek elinditasa.
     */
    public void run(){
        startStepping();
        checkCollision();
        startLeaving();
        startGetOn();
        checkEmptiness();
    }
}
