package application;

import map.Rail;
import utility.Color;
import vehicles.CargoWagon;
import vehicles.Locomotive;
import vehicles.Train;
import vehicles.Wagon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Controller {
    private Rail start;
    //private Train[][] trains;
    private ArrayList<Train[]> trains = new ArrayList<>();;
    private boolean winFlag;
    private boolean loseFlag;
    /*
     * konstruktor, alapallapot megadasa
     */
    public Controller(Rail start){
        this.start = start;
        winFlag = false;
        loseFlag = false;
    }
    /*
     * eltarolt vonatok visszaadasa
     */
    public ArrayList<Train[]> getTrains() {
        return trains;
    }

    /*
     * winFlag visszaadasa
     */
    public boolean getWinFlag() {
        return winFlag;
    }
/*
 * loseFlag visszaadasa
 */

    public boolean getLoseFlag() {
        return loseFlag;
    }
    /*
     * loseFlag beallitasa
     */
    private void lose(){
        System.out.println("VESZTETTEL");
        loseFlag = true;
    }
    /*
     * winFlag beallitasa
     */
    private void win(){
        System.out.println("GYOZELEM");
        winFlag = true;
    }
    /*
     * Vonat letrehozasa a startmezore
     * A vonat vege van a start sinen
     * @param db db-szamu vonatot hozunk letre
     * @param length a vonat hosszanak tarolasara hasznalt valtozo
     */
    public void makeTrain(int db, int length){
        for(int d = 0; d<db; d++) {

            Train[] train = new Train[length];

            //letrehozza a locomotiveokat
            train[0] = new Locomotive(start, length);
            start.setAvailability(true);

            //letrehozza a wagonokat
            for (int j = 1; j < length; j++) {

                //egymas utan, eloszor red, majd yellow, stb ... wagonokat hoz letre
                switch (Color.values()[j - 1]) {
                    case RED:
                        train[j] = new Wagon(null, Color.RED);
                        break;
                    case YELLOW:
                        train[j] = new Wagon(null, Color.YELLOW);
                        break;
                    case GREEN:
                        train[j] = new Wagon(null, Color.GREEN);
                        break;
                    case BLUE:
                        train[j] = new Wagon(null, Color.BLUE);
                        break;
                    case ORANGE:
                        train[j] = new Wagon(null, Color.ORANGE);
                        break;
                    case BROWN:
                        train[j] = new Wagon(null, Color.BROWN);
                        break;
                    default:
                        train[j] = new CargoWagon(null);
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
        }
    }

    /*
     * Jatekos tevekenyseget figyelo fuggveny
     * @return Jatekos alltal valasztott tevekenyseg sorszama
     */
    public int observer(){
        System.out.println("Observer(): felhasznaloi interakcio kezelese:");
        int valasz = 0;
        String sor;
        BufferedReader reader5 = new BufferedReader(new InputStreamReader(System.in));
        try {
            sor = reader5.readLine();
            valasz = Integer.parseInt(sor);
        } catch (IOException e) {System.out.println("\nDANGER, hiba a beolvasasban!!\n");}
        return valasz;

    }
/*
 * Az adott vagon leszallitasat elindito fuggveny
 */

    private void startLeaving(){
        for (Train[] train: trains){
            train[0].leave();
        }
    }

    /*
     * Vagon felszallitasat elindito fg
     */
    private void startGetOn(){
        for (Train[] train: trains){
            train[0].getOn();
        }
    }

    /*
     * Elinditja a leptetest
     */
    private void startStepping(){
        for (Train[] train: trains){
            train[0].move();
        }
    }

/*
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
    /*
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
    /*
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
