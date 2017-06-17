package application;

import graphics.Drawable;
import map.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Color;

public class MapCreator implements Serializable {

    private static Field[][] map;
    transient private static char[][] tmpMap;
    private static int column;
    private static int row;

    public MapCreator(){
        column = 0;
        row = 0;
        map = null;
        tmpMap = null;
    }

    /**
     * Megepiti a palyat, es visszater a start mezojevel.
     * @param level a palya szinje
     * @return start mezo
     */
    public Rail build(int level){
        try {
            setDimensions(level);
            setTmpMap(level);
            initMap();
            setMap();

        }catch (FileNotFoundException e){
            System.out.println("Nincs ilyen palya");
        }
        return (Rail) map[1][1];
    }

    /**
     * Megkeresi egy kapott adott koordinatahoz tartozo mezot.
     * @param x, y
     * @return mezo
     */
    public static Field getField(int x,int y){
        return map[y][x];
    }

    /**
     * Egy listába teszi az összes kirajzolandó mezőt.
     * @return a lista
     */
    public ArrayList<Drawable> getMapElements(){
        ArrayList<Drawable> drawables = new ArrayList<>();
        for (int i = 0; i<row; i++){
            for (int j = 0; j<column; j++){
                drawables.add(map[i][j]);
            }
        }
        return drawables;
    }

    /**
     * Megkeresi egy kapott mezohoz tartozo Y koordinátát.
     * @param field
     * @return ID
     */
    public static int getFieldCoordY(Field field){
        if (field == null) return 0;
        for(int i = 0; i<column; i++){
            for(int j = 0; j<row; j++){
                if (field.equals(map[i][j]))
                    return j;
            }
        }
        return -1;
    }
    /**
     * Megkeresi egy kapott mezohoz tartozo X koordinátát.
     * @param field
     * @return ID
     */
    public static int getFieldCoordX(Field field){
        if (field == null) return 0;
        for(int i = 0; i<column; i++){
            for(int j = 0; j<row; j++){
                if (field.equals(map[i][j]))
                    return i;
            }
        }
        return -1;
    }

    /**
     * Meghivaskor megkeresi a palyan levo 2 letett Tunnelt,
     * es ezek tombjevel ter vissza.
     * @return a megepitett ket Tunnel tombje
     */
    public static Tunnel[] searchSelectedTunnels(){
        // Itt taroljuk a ket megepitett Tunnel-t.
        Tunnel[] selectedTunnels = new Tunnel[2];

        // A tombbeli helye a megepitett Tunnel-nek.
        int selectedNumber = 0;

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                if (tmpMap[i][j] == 'T') {
                    if (((Tunnel) map[i][j]).isSelected()) {
                        selectedTunnels[selectedNumber] = (Tunnel) map[i][j];
                        selectedNumber++;
                    }
                }
            }
        }
        return selectedTunnels;
    }

    /**
     * Beallitja a mapcreator szelesseget es magassagat
     * @param level a palya szintje
     * @throws FileNotFoundException
     */
    private void setDimensions(int level)throws FileNotFoundException{
        Scanner init = new Scanner(new File("" + level + ".txt"));
        StringTokenizer stk = new StringTokenizer(init.nextLine()," ");

        row = stk.countTokens();

        column++;
        while (init.hasNextLine()){
            column++;
            init.nextLine();
        }
    }

    /**
     * Beallit egy segedmatrixot.
     * @param level a palya szintje
     * @throws FileNotFoundException
     */
    private void setTmpMap(int level)throws FileNotFoundException{
        tmpMap = new char[column][row];
        Scanner read = new Scanner(new File("" + level + ".txt"));

        byte r = 0;
        byte c = 0;
        while ( r < row && read.hasNextLine()) {
            tmpMap[c][r] = read.next().charAt(0);
            r++;
            if (r == row && read.hasNextLine()){
                read.nextLine();
                r = 0;
                c++;
            }
        }
        read.close();
    }

    /**
     * Inicializalja a palyat.
     */
    private void initMap(){
        map = new Field[column][row];

        int r = 0;
        int c = 0;
        while (c < column && r < row) {
            switch (tmpMap[c][r]){
                case 'R':
                    map[c][r] = new Rail();
                    break;
                case 'S':
                    map[c][r] = new Switch();
                    break;
                case 'T':
                    map[c][r] = new Tunnel();
                    break;
                case 'E':
                    map[c][r] = new EmptyField();
                    break;
                case 'C':
                    map[c][r] = new CrossRail();
                    break;
                case '1':
                    map[c][r] = new RailStation(Color.RED);
                    break;
                case '2':
                    map[c][r] = new RailStation(Color.BLUE);
                    break;
                case '3':
                    map[c][r] = new RailStation(Color.GREEN);
                    break;
                case '4':
                    map[c][r] = new RailStation(Color.ORANGE);
                    break;
                case '5':
                    map[c][r] = new RailStation(Color.YELLOW);
                    break;
            }
            r++;
            if (r == row){
                r = 0;
                c++;
            }
        }
        map[0][0] = new StartRail();
    }

    /**
     * Beallitja a palyaelemek osszefuggeseit.
     */
    private void setMap(){

        int r = 0;
        int c = 0;
        while (c < column && r < row) {
            switch (tmpMap[c][r]){
                case 'R':
                    Rail tmp;
                    tmp = (Rail) map[c][r];
                    initRail(tmp, c, r);
                    break;
                case 'S':
                    Switch tmpS;
                    tmpS = (Switch) map[c][r];
                    initSwitch(tmpS,c,r);
                    break;
                case 'T':
                    Tunnel tmpT;
                    tmpT = (Tunnel) map[c][r];
                    initRail(tmpT,c,r);
                    break;
                case 'C':
                    CrossRail tmpC;
                    tmpC = (CrossRail) map[c][r];
                    initCrossRail(tmpC,c,r);
                    break;
                default:
                    break;
            }
            r++;
            if (r == row){
                r = 0;
                c++;
            }
        }
    }

    /**
     * Beallitja a Rail osszes tutajdonsagat
     * @param rail
     * @param c oszlop
     * @param r sor
     */
    private void initRail(Rail rail, int c, int r){
        setNeighbours(rail,c,r);
        rail.setByTheStation(isByTheStation(c,r));
        rail.setPossibleRail1(findPossibleRail(null,c,r));
        rail.setPossibleRail2(findPossibleRail(rail.getPossibleRail1(),c,r));
    }

    /**
     * Beallitja a Switch osszes tulajdonsagat
     * @param swtch
     * @param c
     * @param r
     */
    private void initSwitch(Switch swtch, int c, int r){
        initRail(swtch,c,r);
        setSwitchRail(swtch,c,r);

    }

    /**
     * Beallitja a CrossRail osszes tulajdonsagat.
     * @param rail
     * @param c
     * @param r
     */
    private void initCrossRail(CrossRail rail, int c, int r){
        setNeighbours(rail,c,r);
        rail.setByTheStation(false);

        Rail rail1 = (Rail) map[c+1][r];
        Rail rail2 = (Rail) map[c-1][r];
        Rail rail3 = (Rail) map[c][r+1];
        Rail rail4 = (Rail) map[c][r-1];

        rail.setPossibleRail1(rail1);
        rail.setPossibleRail2(rail2);
        rail.setPossibleRail3(rail3);
        rail.setPossibleRail4(rail4);
    }

    /**
     * Megnezi, hogy allomas mellett van e a mezo.
     * @param c oszlop index
     * @param r sor index
     * @return igaz, ha van mellette allomas, hamis ha nem
     */
    private boolean isByTheStation(int c, int r) {
        if (c+1 < column && (tmpMap[c+1][r] == '1' || tmpMap[c+1][r] == '2' || tmpMap[c+1][r] == '3' || tmpMap[c+1][r] == '4' || tmpMap[c+1][r] == '5')) return true;
        else if (c-1 >= 0 && (tmpMap[c-1][r] == '1' || tmpMap[c-1][r] == '2' || tmpMap[c-1][r] == '3' || tmpMap[c-1][r] == '4' || tmpMap[c-1][r] == '5')) return true;
        else if (r-1 >= 0 && (tmpMap[c][r-1] == '1' || tmpMap[c][r-1] == '2' || tmpMap[c][r-1] == '3' || tmpMap[c][r-1] == '4' || tmpMap[c][r-1] == '5')) return true;
        else if (r+1 < row && (tmpMap[c][r+1] == '1' || tmpMap[c][r+1] == '2' || tmpMap[c][r+1] == '3' || tmpMap[c][r+1] == '4' || tmpMap[c][r+1] == '5')) return true;
        else return false;
    }

    /**
     * Beallitja a mezo szomszedait.
     * @param field
     * @param c
     * @param r
     */
    private void setNeighbours(Field field, int c, int r){
        if (c+1 < column)field.setDown(map[c+1][r]);
        if (r-1 >= 0)field.setLeft(map[c][r-1]);
        if (r+1 < row)field.setRight(map[c][r+1]);
        if (c-1 >= 0)field.setUp(map[c-1][r]);
    }

    /**
     * Megkeresi egy Rail egyik Rail szomszedjat.
     * @param usedRail Ha eloszor hivtuk meg akkor null, ha masodszor akkor az elso talalat
     * @param c oszlop
     * @param r sor
     * @return Elso illetve masodik Rail parametertol fuggoen
     */
    private Rail findPossibleRail(Rail usedRail,int c, int r){
        if (usedRail == null) {
            if (isItRail(c-1, r)) return (Rail) map[c-1][r];
            else if(isItRail(c,r-1)) return (Rail) map[c][r-1];
            else if (isItRail(c,r+1)) return (Rail) map[c][r+1];
            else if (isItRail(c+1,r)) return (Rail) map[c+1][r];
            else {
                System.out.print("HIBA A RAIL ELKESZITESE KOZBEN");
                return null;
            }
        }
        if (isItRail(c - 1, r) && !(usedRail.equals(map[c-1][r]))) return (Rail) map[c-1][r];
        else if (isItRail(c,r+1)&& !(usedRail.equals(map[c][r+1]))) return (Rail) map[c][r+1];
        else if (isItRail(c+1,r)&& !(usedRail.equals(map[c+1][r]))) return (Rail) map[c+1][r];
        else if(isItRail(c,r-1) && !(usedRail.equals(map[c][r-1]))) return (Rail) map[c][r-1];
        else {
            System.out.print("HIBA A RAIL ELKESZITESE KOZBEN");
            return null;
        }
    }

    /**
     * Beallitja a switch agait.
     * @param sw beallitando switch
     * @param c oszlop
     * @param r sor
     */
    private void setSwitchRail(Switch sw, int c, int r) {
        sw.setFixRail(sw.getPossibleRail1());
        sw.setSelectedRail(sw.getPossibleRail2());

        if (isItRail(c + 1, r) && !(map[c + 1][r].equals(sw.getFixRail())) && !(map[c + 1][r].equals(sw.getSelectedRail())))
            sw.setNotselectedRail((Rail) map[c + 1][r]);
        else if (isItRail(c, r + 1) && !(map[c][r+1].equals(sw.getFixRail())) && !(map[c][r+1].equals(sw.getSelectedRail())))
            sw.setNotselectedRail((Rail) map[c - 1][r]);
        else if (isItRail(c - 1, r) && !(map[c-1][r].equals(sw.getFixRail())) && !(map[c-1][r].equals(sw.getSelectedRail())))
            sw.setNotselectedRail((Rail) map[c][r + 1]);
        else if (isItRail(c, r - 1) && !(map[c][r - 1].equals(sw.getFixRail())) && !(map[c][r - 1].equals(sw.getSelectedRail())))
            sw.setNotselectedRail((Rail) map[c][r - 1]);
        else {
            System.out.print("HIBA A SWITCH ELKESZITESE KOZBEN");
        }
    }

    /**
     * Megmondja, hogy van e mellette sin.
     * @param c oszlop
     * @param r sor
     * @return true ha van, false ha nincs
     */
    private boolean isItRail(int c, int r){
        return  (c < column && c >= 0 && r < row && r >=0 && (tmpMap[c][r] == 'R' || tmpMap[c][r] == 'C' || tmpMap[c][r] == 'T' || tmpMap[c][r] == 'S'));
    }

}
