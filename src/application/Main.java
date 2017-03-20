package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Controller controller;
        MapCreator mapcreator;

        int valasz = 0;
        String sor;

        System.out.print("Akarsz kezdeni uj jatekot? 0-nem, 1-igen:\t");

        try {
            sor = reader.readLine();
            valasz = Integer.parseInt(sor);}
        catch(IOException e){ System.out.println("\nDANGER, hiba a beolvasasban!!\n"); }

        if (valasz == 1){
            mapcreator = new MapCreator();


            controller = mapcreator.build(1);
        }
        else{
            System.out.println("\nAkkor csa!");
            System.exit(1);
            return;
        }
    }
    void gameStart(int level) {

    }
}
