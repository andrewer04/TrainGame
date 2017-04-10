package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main {

    public static void main(String[] args) throws IOException {

        boolean log = false;

        int valasz = 0;

        /*for(int level = 1; level <=2; level++){
            controller = new Controller(mapcreator.build(level));
            controller.makeTrain(level);
            while(controller.getWinFlag()){
                controller.observer();
                if (timer.start()){
                    controller.run();
                }
            }
        }*/
        System.out.print("Hogy akarsz tesztelni?\n");
        System.out.print("\nA lehetseges parancsok: autoMode | manualMode | logOn | logOff\n");

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        if (command.equalsIgnoreCase("autoMode")){
            autoMode(log);
        }
        else if(command.equalsIgnoreCase("manualMode")){
        }
        else if (command.equalsIgnoreCase("logOn"))
            log = true;
        else if (command.equalsIgnoreCase("logOff"))
            log = false;
        else {
            System.out.println("Nincs ilyen parancs!");
            return;
        }
    }

    public static void autoMode(boolean log) throws FileNotFoundException {
        System.out.print("Melyik tesztet szeretned?\n");
        System.out.println(
                "1.:Vonat mozgatasa\n"
                + "2.:Lerakott alagut mukodesenek ellenorzese\n"
                + "3.:Alagut felvetele a palyarol\n"
                + "4.:3-as valto allitasa\n"
                + "5.:Leszallas\n"
                + "6.:Felszallas\n"
                + "7.:Utkozes\n"
                + "8.:Jatek vege\n"
                + "9.:Osszes teszt"
        );

        Scanner input = new Scanner(System.in);
        int answer = input.nextInt();

        switch (answer){
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                newTest(answer);
                break;
            case 9:
                for(int i = 1; i<=8; i++) {
                    newTest(i);
                }
                break;
            default: System.out.println("Nincs ilyen eset!");
            }
        }
    public static void manualMode(boolean log){

    }

    public static void newTest(int number) throws FileNotFoundException {
        Scanner fileScan = new Scanner(new File("C:/Users/baran/IdeaProjects/TrainGame/src/tests/test" + number + ".txt"));
        String row;
        while (fileScan.hasNextLine()) {
            row = fileScan.nextLine();
            prog(row);
            //System.out.println(row);
        }
    }
    public static void prog(String row){
        StringTokenizer stk = new StringTokenizer(row);
        String command = stk.nextToken();

        String stringArg1, stringArg2;
        int arg1 = 999;
        int arg2 = 999;

        if  (stk.hasMoreTokens()){
            stringArg1 = stk.nextToken();
            arg1 = Integer.parseInt(stringArg1);
        }
        if  (stk.hasMoreTokens()){
            stringArg2 = stk.nextToken();
            arg2 = Integer.parseInt(stringArg2);
        }

        if  (command.equalsIgnoreCase("next")){
            next();
        }
        if  (command.equalsIgnoreCase("getPositions")){
            getPositions();
        }
        if  (command.equalsIgnoreCase("getTunnels")){
            getTunnels();
        }
        if  (command.equalsIgnoreCase("getStations")){
            getStations();
        }
        if  (command.equalsIgnoreCase("getStatus")){
            getStatus();
        }
        if  (command.equalsIgnoreCase("start") && arg1 != 999){
            start(arg1);
        }
        if  (command.equalsIgnoreCase("addTrain") && arg1 != 999){
            addTrain(arg1);
        }
        if  (command.equalsIgnoreCase("move") && arg1 != 999){
            move(arg1);
        }
        if  (command.equalsIgnoreCase("deleteTunnel") && arg1 != 999){
            deleteTunnel(arg1);
        }
        if  (command.equalsIgnoreCase("setTunnel") && arg1 != 999){
            setTunnel(arg1);
        }
        if  (command.equalsIgnoreCase("setSwitch") && arg1 != 999 && arg2 != 999){
            setSwitch(arg1,arg2);
        }

    }
    public static void start(int lvl){

    }
    public static void addTrain(int wagonNumber){

    }
    public static void move(int steps){

    }
    public static void next(){

    }
    public static void deleteTunnel(int id){

    }
    public static void setTunnel(int id){

    }
    public static void setSwitch(int id, int dir){

    }
    public static void getPositions(){

    }
    public static void getTunnels(){

    }
    public static void getStations(){

    }
    public static void getStatus(){

    }
}
