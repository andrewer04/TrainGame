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

public class Controller {
    private Rail start;
    private Train[][] trains;
    private boolean winFlag;

    public Controller(Rail start){
        this.start = start;
        System.out.println("Controller letrejott");
        winFlag = false;
    }

    public boolean getWinFlag() {
        return winFlag;
    }

    public void lose(){
        System.out.println("VESZTETTEL");
        System.exit(1);
        return;
    }
    public void win(){
        System.out.println("GYOZELEM");
        winFlag = true;
        return;
    }

    public void makeTrain(int db, int length){
       trains = new Train[db][length];

       //létrehozza a locomotiveokat
       for (int i = 0; i<db; i++){
           trains[i][0] = new Locomotive(start,length);

           //létrehozza a wagonokat
           for (int j = 1; j<length; j++) {

               //egymás után először egy red, aztán egy yellow, majd green, ... wagonokat hoz létre
               switch (Color.values()[j-1]){
                   case RED:
                       trains[i][j] = new Wagon(null, Color.RED);
                       break;
                   case YELLOW:
                       trains[i][j] = new Wagon(null, Color.YELLOW);
                       break;
                   case GREEN:
                       trains[i][j] = new Wagon(null, Color.GREEN);
                       break;
                   case BLUE:
                       trains[i][j] = new Wagon(null, Color.BLUE);
                       break;
                   case ORANGE:
                       trains[i][j] = new Wagon(null, Color.ORANGE);
                       break;
                   case BROWN:
                       trains[i][j] = new Wagon(null, Color.BROWN);
                       break;
                   default:
                       trains[i][j] = new CargoWagon(null, Color.GREY);
                       break;
               }

           }

           //beállítja a waqgonokat
           for (int j = 1; j<length; j++) {
               if(j == length-1) { //azaz utolsó kocsi
                trains[i][j].setNext(null);
                trains[i][j].setPrev(trains[i][j-1]);
                trains[i][j].setPrevRail(null);
               }
               else{
                   trains[i][j].setNext(trains[i][j+1]);
                   trains[i][j].setPrev(trains[i][j-1]);
                   trains[i][j].setPrevRail(null);
               }
           }

           //beállítja a locomotivokat
           trains[i][0].setPrevRail(null);
           trains[i][0].setPrev(null);
           trains[i][0].setNext(trains[i][1]);
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

    public void startLeaving(){
        for (int i = 0; i<trains.length; i++){
            trains[i][0].leave();
        }
    }
    public void startGetOn(){
        for (int i = 0; i<trains.length; i++){
            trains[i][0].getOn();
        }
    }
    public void startStepping(){
        for (int i = 0; i<trains.length; i++){
            trains[i][0].move();
        }
    }

    public void checkCollision(){
        for(int i = 0; i<trains.length; i++){
            if(trains[i][0].detectCollision()) {
                lose();
            }
        }
    }
    public void checkEmptiness(){
        for(int i = 0; i<trains.length; i++){
            if(trains[i][0].detectEmptiness()) {
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
        return;
    }
}
