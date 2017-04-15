package application;

import map.Rail;
import map.RailStation;
import map.Switch;
import map.Tunnel;
import utility.Commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main {

   static Controller controller;
   static MapCreator mapCreator;
   private static ArrayList<String> output = new ArrayList<String>();

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
        while(true) {
            System.out.println("Hogy akarsz tesztelni?");
            System.out.println("A lehetseges parancsok: autoMode | manualMode | logOn | logOff");
            System.out.println("Log: "+ (log == true ? "on" : "off"));

            try {
                Scanner scanner = new Scanner(System.in);
                Commands command = Commands.valueOf(scanner.nextLine().toUpperCase());

                switch (command) {
                    case AUTOMODE:
                        autoMode(log);
                        break;
                    case MANUALMODE:
                        manualMode(log);
                        break;
                    case LOGON:
                        log = true;
                        break;
                    case LOGOFF:
                        log = false;
                        break;
                    default:
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("A hiba oka: " + e.getMessage());
            }
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
                newTest(answer,log, answer);
                break;
            case 9:
                for(int i = 1; i<=8; i++) {
                    newTest(i,log, answer);
                }
                break;
            default: System.out.println("Nincs ilyen eset!");
            }
        }
    public static void manualMode(boolean log){
        while (true){
            System.out.println("Lehetseges parancsok:");
            for (int i = 0; i<Commands.values().length-4; i++){
                System.out.print(Commands.values()[i].name() + " | ");
            }
            System.out.print("\n");

            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            prog(command,log,0);
        }
    }

    //kiválasztja, hogy melyik test.txt-t kell meghívni, és végrehajtatja az abban lévő parancsokat
    public static void newTest(int number, boolean log, int test) throws FileNotFoundException {
        Scanner fileScan = new Scanner(new File("test" + number + ".txt"));
        String row;
        while (fileScan.hasNextLine()) {
            row = fileScan.nextLine();
            prog(row,log,test);
        }
    }

    //végrehajtja a kapott parancsot
    public static void prog(String row, boolean log, int test){
        StringTokenizer stk = new StringTokenizer(row);
        ArrayList<String> options = new ArrayList<String>();

        while (stk.hasMoreTokens()) {
            String token = stk.nextToken();
            options.add(token);
        }

        try{
            Commands command = Commands.valueOf(options.get(0).toUpperCase());
            options.remove(0);

            String stringArg1, stringArg2;
            int arg1 = 999;
            int arg2 = 999;

            //ha van még más módosító akkor azt ide elmentjük (max 2 lehet)
            if  (!(options.isEmpty())){
                stringArg1 = options.get(0);
                options.remove(0);
                arg1 = Integer.parseInt(stringArg1);
            }
            if  (!(options.isEmpty())){
                stringArg2 = options.get(0);
                arg2 = Integer.parseInt(stringArg2);
            }

            switch (command){
                case START:
                    start(arg1);
                    break;
                case GETSTATUS:
                    getStatus(log);
                    break;
                case GETTUNNEL:
                    getTunnel(arg1,log);
                    break;
                case GETSTATION:
                    getStation(arg1,log);
                    break;
                case GETPOSITIONS:
                    getPositions(log);
                    break;
                case ADDTRAIN:
                    addTrain(arg1);
                    break;
                case MOVE:
                    move(arg1);
                    break;
                case DELETETUNNEL:
                    deleteTunnel(arg1);
                    break;
                case SETSWITCH:
                    setSwitch(arg1);
                    break;
                case SETTUNNEL:
                    setTunnel(arg1);
                    break;
                case EXIT:
                    writeToFile(test);
                    System.exit(0);
                    break;
            }
        }catch (IllegalArgumentException e){
            System.out.println("A hiba oka: " + e.getMessage());
        }
        catch (FileNotFoundException e){
            System.out.println("A hiba oka: " + e.getLocalizedMessage());
        }
    }

    public static void start(int lvl){
        mapCreator = new MapCreator();
        controller = new Controller(mapCreator.build(lvl));
    }
    public static void addTrain(int wagonNumber){
        controller.makeTrain(1,wagonNumber);
    }
    public static void move(int steps){
        for (int i = 0; i<steps; i++){
            controller.run();
        }
    }
    public static void deleteTunnel(int id){
        Tunnel tunnel = (Tunnel) mapCreator.searchField(id);
        tunnel.deleting();
    }
    public static void setTunnel(int id){
        Tunnel tunnel = (Tunnel) mapCreator.searchField(id);
        tunnel.creating();
    }
    public static void setSwitch(int id){
        Switch sw = (Switch) mapCreator.searchField(id);
        sw.switching();
    }
    public static void getPositions(boolean log){
        if (!log) {
            for (int j = 0; j < controller.getTrains().size(); j++) {
                System.out.print("<" + mapCreator.searchID(controller.getTrains().get(j)[0].getCurrentRail()) + ">");
                for (int k = 1; k < controller.getTrains().get(j).length; k++) {
                    System.out.print("<" + mapCreator.searchID(controller.getTrains().get(j)[k].getCurrentRail()) + "," + !(controller.getTrains().get(j)[k].isEmpty()) + ">");
                }
                System.out.print("\n");
            }
        }
        else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < controller.getTrains().size(); j++) {
                stringBuilder.append("<" + mapCreator.searchID(controller.getTrains().get(j)[0].getCurrentRail()) + ">");
                for (int k = 1; k < controller.getTrains().get(j).length; k++) {
                    stringBuilder.append("<" + mapCreator.searchID(controller.getTrains().get(j)[k].getCurrentRail()) + "," + !(controller.getTrains().get(j)[k].isEmpty()) + ">");
                }
            }
            output.add(stringBuilder.toString());
        }
    }
    public static void getTunnel(int id, boolean log){
        Tunnel tunnel = (Tunnel) mapCreator.searchField(id);
        try {
            if (!log) {
                System.out.print("<" + id + "><" + tunnel.isSelected() + ">\n");
            } else {
               output.add("<" + id + "><" + tunnel.isSelected() + ">\n");
            }
        }
        catch (ClassCastException e){
            System.out.println("Az nem Tunnel volt!");
        }
    }
    public static void getStation(int id, boolean log){
        RailStation station = (RailStation) mapCreator.searchField(id);
        try{
            if (!log){
                System.out.print("<" + id + "><" + !(station.testIsEmpty()) + ">\n");
            } else {
                output.add("<" + id + "><" + !(station.testIsEmpty()) + ">\n");
            }
        }catch (ClassCastException e){
            System.out.println("Az nem Railstation volt!");
        }

    }
    public static void getStatus(boolean log){
        if (!log) {
            if (controller.getWinFlag() == true) System.out.println("<win>");
            else if (controller.getLoseFlag() == true) System.out.println("<lose>");
            else System.out.println("<progress>");
        }else {
            if (controller.getWinFlag() == true) output.add("<win>");
            else if (controller.getLoseFlag() == true) output.add("<lose>");
            else output.add("<progress>");
        }
    }
    public static void writeToFile(int test) throws FileNotFoundException		//fájlba író metódus
    {
        PrintWriter writer = new PrintWriter("output"+test+".txt");				//a tömb összes elemét kiírja a fileba, majd bezárja
        for (int i = 0 ; i < output.size(); i++){
            writer.println(output.get(i));
        }
        writer.close();
    }
}
