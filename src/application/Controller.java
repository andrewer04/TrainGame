package application;

import map.Rail;
import vehicles.Train;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    private Rail start;
    private Train[] trains;
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

    public void makeTrain(int level){

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
        for (Train locomotives: trains){
            locomotives.leave();
        }
    }
    public void startStepping(){
        for (Train locomotives: trains){
            locomotives.move();
        }
    }

    public void checkCollision(){
        for(Train locomotives: trains){
            if(locomotives.detectCollision()) {
                lose();
            }
        }
    }
    public void checkEmptiness(){
        for(Train locomotives: trains){
            if(locomotives.detectEmptiness()) {
                win();
            }
        }
    }

    public void run(){
        startStepping();
        checkCollision();
        startLeaving();
        checkEmptiness();
        return;
    }
}
