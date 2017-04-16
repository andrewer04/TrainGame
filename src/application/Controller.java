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
     * konstruktor, alapállapot megadása
     */
    public Controller(Rail start){
        this.start = start;
        winFlag = false;
        loseFlag = false;
    }
    /*
     * eltárolt vonatok visszaadása
     */
    public ArrayList<Train[]> getTrains() {
        return trains;
    }

    /*
     * winFlag visszadása
     */
    public boolean getWinFlag() {
        return winFlag;
    }
/*
 * loseFlag visszaadása
 */

    public boolean getLoseFlag() {
        return loseFlag;
    }
    /*
     * loseFlag beállítása
     */
    private void lose(){
        System.out.println("VESZTETTEL");
        loseFlag = true;
    }
    /*
     * winFlag beállítása
     */
    private void win(){
        System.out.println("GYOZELEM");
        winFlag = true;
    }
    /*
     * Vonat létrehozása a startmezőre.
     * A vonat vége van a start sínen.
     * @param db db-számú vonatot hozunk létre
     * @param length a vonat hosszának tárolására használt változó
     */
    public void makeTrain(int db, int length){
        for(int d = 0; d<db; d++) {

            Train[] train = new Train[length];

            //létrehozza a locomotiveokat
            train[0] = new Locomotive(start, length);
            start.setAvailability(true);

            //létrehozza a wagonokat
            for (int j = 1; j < length; j++) {

                //egymás után, először red, majd yellow, stb ... wagonokat hoz létre
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

            //beállítja a waqgonokat
            for (int j = 1; j < length; j++) {
                if (j == length - 1) { //azaz utolsĂł kocsi
                    train[j].setNext(null);
                    train[j].setPrev(train[j - 1]);
                    train[j].setPrevRail(null);
                } else {
                    train[j].setNext(train[j + 1]);
                    train[j].setPrev(train[j - 1]);
                    train[j].setPrevRail(null);
                }
            }

            //beállítja a locomotivokat
            train[0].setPrevRail(null);
            train[0].setPrev(null);
            train[0].setNext(train[1]);

            trains.add(train);
        }
    }

    /*
     * Játékos tevékenységét figyelő függvény
     * @return Játékos álltal választott tevékenységnek a sorszáma
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
 * Az adott vagon leszállítását elindító függvény
 */

    private void startLeaving(){
        for (Train[] train: trains){
            train[0].leave();
        }
    }

    /*
     * Vagon felszállítását elindító függvény
     */
    private void startGetOn(){
        for (Train[] train: trains){
            train[0].getOn();
        }
    }

    /*
     * Elindítja a léptetést
     */
    private void startStepping(){
        for (Train[] train: trains){
            train[0].move();
        }
    }

/*
 * Ütközés ellenőrzése.
 * Ütközés esetén lose() függvény meghívása.
 */

    private void checkCollision(){
        for (Train[] train: trains){
            if(train[0].detectCollision()) {
                lose();
            }
        }
    }
    /*
     * Vonatok ürességének vizsgálata.
     * Ha mind üres, a játékos nyert.
     */
    private void checkEmptiness(){
        for (Train[] train: trains){
            if(train[0].detectEmptiness()) {
                win();
            }
        }
    }
    /*
     * Vonattal kapcsolatos tevékenységek elindítása
     */
    public void run(){
        startStepping();
        checkCollision();
        startLeaving();
        startGetOn();
        checkEmptiness();
    }
}
