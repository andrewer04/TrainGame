package application;

import map.Rail;
import vehicles.Train;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    private int level;
    private Rail start;
    private Train[] trains;

    public Controller(int level, Rail start){
        this.level = level;
        this.start = start;
        System.out.println("Controller letrejott");
    }

    public void lose(){
        System.out.println("VESZTETTEL");
        System.exit(1);
        return;
    }
    public boolean win(){
        System.out.println("GYOZELEM");
        return true;
    }
    public void makeTrain(){

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
    public void startStepping(){

    }

    //a Controller lose() metódusát nem tudjuk meghívni a vonatokból, mert ahhoz el kéne tárolni
    //az egyetlen Controller objektumot, amit majd a main-ben hozunk létre
    //mellesleg, ha a léptetés után hívjuk meg a checkcollision függvényt, akkor elkerüljük azt, hogy a léptetés
    //miatt fals ütküzés legyen
    public void checkCollision(Rail start){}
}
