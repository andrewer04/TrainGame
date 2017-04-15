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

    public Controller(Rail start){
        this.start = start;
        winFlag = false;
        loseFlag = false;
    }

    public ArrayList<Train[]> getTrains() {
        return trains;
    }

    public boolean getWinFlag() {
        return winFlag;
    }

    public boolean getLoseFlag() {
        return loseFlag;
    }

    private void lose(){
        System.out.println("VESZTETTEL");
        loseFlag = true;
    }
    private void win(){
        System.out.println("GYOZELEM");
        winFlag = true;
    }

    public void makeTrain(int db, int length){
        for(int d = 0; d<db; d++) {

            Train[] train = new Train[length];

            //létrehozza a locomotiveokat
            train[0] = new Locomotive(start, length);
            start.setAvailability(true);

            //létrehozza a wagonokat
            for (int j = 1; j < length; j++) {

                //egymás után először egy red, aztán egy yellow, majd green, ... wagonokat hoz létre
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
                if (j == length - 1) { //azaz utolsó kocsi
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

    private void startLeaving(){
        for (Train[] train: trains){
            train[0].leave();
        }
    }
    private void startGetOn(){
        for (Train[] train: trains){
            train[0].getOn();
        }
    }
    private void startStepping(){
        for (Train[] train: trains){
            train[0].move();
        }
    }

    private void checkCollision(){
        for (Train[] train: trains){
            if(train[0].detectCollision()) {
                lose();
            }
        }
    }
    private void checkEmptiness(){
        for (Train[] train: trains){
            if(train[0].detectEmptiness()) {
                win();
            }
        }
    }

    public void run(){
        startStepping();
        checkCollision();
        startLeaving();
        startGetOn();
        checkEmptiness();
    }
}
