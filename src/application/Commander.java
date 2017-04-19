package application;

import map.RailStation;
import map.Switch;
import map.Tunnel;
import utility.Commands;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Commander {

    private Controller controller;
    private MapCreator2 mapCreator;
    private ArrayList<String> output = new ArrayList<>();
    boolean log;


    public Commander(){
        log = false;
    }
    /**
     * Jatekos tevekenyseget figyelo fuggveny
     */
    public void observer() {
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
            System.out.println("Nincs ilyen parancs: " + e.getMessage());
        }
    }

    public void autoMode(boolean log) {
        while (true) {
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
                            + "9.:Kilepes"
            );

            Scanner input = new Scanner(System.in);
            int answer = input.nextInt();

            //megadott teszteset tesztelese
            switch (answer) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    newTest(answer, log, answer);
                    break;
                case 9:
                    System.exit(0);
                default:
                    System.out.println("Nincs ilyen eset!");
            }
        }
    }
    private void manualMode(boolean log){
        while (true){
            System.out.println("Lehetseges parancsok:");
            for (int i = 0; i< Commands.values().length-4; i++){
                System.out.print(Commands.values()[i].name() + " | ");
            }
            System.out.print("\n");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            prog(command,log,0);
        }
    }
    /**
     * kivalasztja, hogy melyik test.txt-t kell meghivni, es vegrehajtatja az abban levo parancsokat
     * @param number test.txt szamanak a tarolasa
     * @param log login-off tarolasara hasznalt parameter
     * @param test hanyadik teszteset van kivalasztva
     */
    private void newTest(int number, boolean log, int test) {
        try {
            Scanner fileScan = new Scanner(new File("test" + number + ".txt"));
            String row;
            while (fileScan.hasNextLine()) {
                row = fileScan.nextLine();
                prog(row, log, test);
            }
        }catch (FileNotFoundException e){
            System.out.println("Nincs ilyen fajl: " + e.getMessage());
        }
    }
    /**
     * vegrehajtja a kapott parancsot
     * @param row txt-bol kiolvasott, egy sornyi parancs
     * @param log a login-off tarolasa
     * @param test teszteset szama
     */
    private void prog(String row, boolean log, int test){
        StringTokenizer stk = new StringTokenizer(row);
        ArrayList<String> options = new ArrayList<>();

        while (stk.hasMoreTokens()) {
            String token = stk.nextToken();
            options.add(token);
        }

        try{
            Commands command = Commands.valueOf(options.get(0).toUpperCase());
            options.remove(0);

            String stringArg1;
            int arg1 = 999;

            //ha van meg mas modosito akkor azt ide elmentjuk (max 2 lehet)
            if  (!(options.isEmpty())){
                stringArg1 = options.get(0);
                options.remove(0);
                arg1 = Integer.parseInt(stringArg1);
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
                    //System.exit(0);
                    break;
            }
        }catch (IllegalArgumentException e){
            System.out.println("A hiba oka: " + e.getMessage());
        }
        catch (FileNotFoundException e){
            System.out.println("A hiba oka: " + e.getLocalizedMessage());
        }
    }
    /**
     * jatek inditasa
     * @param lvl szint tarolasara hasznalt valtozo
     */
    private void start(int lvl){
        mapCreator = new MapCreator2();
        controller = new Controller(mapCreator.build(lvl));
    }

    /**
     * vonat palyahoz adasa
     * @param wagonNumber vonat azonositasahoz hasznalt valtozo
     */
    private void addTrain(int wagonNumber){
        controller.makeTrain(wagonNumber);
    }

    /**
     * Vonatok mozgatasara hasznalt fuggveny
     * @param steps hany lepest hajtsunk vegre
     */
    private void move(int steps){
        for (int i = 0; i<steps; i++){
            controller.run();
        }
    }

    /**
     * Alagut torlesere hasznalt fuggveny
     * @param id alagut helyenek meghatarozasara hasznalt valtozo
     */
    private void deleteTunnel(int id){
        Tunnel tunnel = (Tunnel) mapCreator.searchField(id);
        tunnel.deleting();
    }
    /**
     * Alagut palyahoz adasa
     * @param id valtozo, mely segitsegevel megadott helyre rakhato az alagut
     */
    private void setTunnel(int id){
        Tunnel tunnel = (Tunnel) mapCreator.searchField(id);
        tunnel.creating();
    }
    /**
     * Valto allitasa
     * @param id ugyanaz, mint az alagutaknal
     */
    private void setSwitch(int id){
        Switch sw = (Switch) mapCreator.searchField(id);
        sw.switching();
    }
    /**
     * Vonatok poziciojanak kiirasa konzolra, teszteleshez hasznalando fuggveny
     * @param log ugyanaz mint eddig
     */
    private void getPositions(boolean log){
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
            for (int j = 0; j < controller.getTrains().size(); j++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<" + mapCreator.searchID(controller.getTrains().get(j)[0].getCurrentRail()) + ">");
                for (int k = 1; k < controller.getTrains().get(j).length; k++) {
                    stringBuilder.append("<" + mapCreator.searchID(controller.getTrains().get(j)[k].getCurrentRail()) + "," + !(controller.getTrains().get(j)[k].isEmpty()) + ">");
                }
                output.add(stringBuilder.toString());
            }
        }
    }
    /**
     * Alagut kivalasztasa, kivalasztas ellenorzese
     * @param id mezo kivalasztasahoz hasznalt valtozo
     * @param log login-off, mint eddig
     */
    private void getTunnel(int id, boolean log){
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

    /**
     * allomas allapotanak lekerdezese, konzolra kiirasa
     * @param id azonosito
     * @param log login/logoff tarolasa
     */
    private void getStation(int id, boolean log){
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
    /**
     * Jatek allapotanak lekerdezese
     * Nyertunk/vesztettunk/tart e meg a jatek?
     * @param log log allapot tarolasa/mint eddig/
     */
    private void getStatus(boolean log){
        if (!log) {
            if (controller.getWinFlag()) System.out.println("<win>");
            else if (controller.getLoseFlag()) System.out.println("<lose>");
            else System.out.println("<progress>");
        }else {
            if (controller.getWinFlag()) output.add("<win>");
            else if (controller.getLoseFlag()) output.add("<lose>");
            else output.add("<progress>");
        }
    }
    /**
     * Fajlba iro fuggveny
     * Konzolon levo informaciok fajlba mentese
     * @param test teszteset szamanak tarolasara hasznalt valtozo
     */
    private void writeToFile(int test) throws FileNotFoundException
    {
        PrintWriter writer = new PrintWriter("output"+test+".txt");
        for (String string: output){
            writer.println(string);
            output = new ArrayList<>();
        }
        writer.close();
    }
}
