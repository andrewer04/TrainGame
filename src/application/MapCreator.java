package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import map.CrossRail;
import map.EmptyField;
import map.Field;
import map.Rail;
import map.RailStation;
import map.StartRail;
import map.Switch;
import map.Tunnel;
import utility.Color;

public class MapCreator {

    // SegĂ©dvĂˇltozĂł a pĂˇlya szĂ©lessĂ©gĂ©hez Ă©s hosszĂˇhoz.
    private static int realWidth;

    private static int realHeight;

    // SegĂ©dmĂˇtrixok a beolvasott pĂˇlyĂˇhoz Ă©s az iniciĂˇlt pĂˇlyĂˇhoz.
    private static String[][] map = null;
    private static Field[][] Fields = null;

    /*
     * Létrehozza a kívánt pályát. A paraméterként átadott pályát készíti el.
     * Beolvassuk a pályát egy tömbbe, az alapján létrehozzunk
     * egy a pálya típusait tároló tömböt, ahová inicializáljuk a mezőket.
     * Majd pozíció szerinti kereséssel beállítjuk a szomszédait
     * és a lehetséges továbbhaladási irányokat
     *
     * @param level pálya nehézségi szintjének tárolására használt változó
     */
    public Rail build(int level){
        //  System.out.println(" build() - letrehozza a(z) " + level + ". palyat, mezoit Ă�Â©s azok osszefuggeseit");

        // SegĂ©dvĂˇltozĂł a pĂˇlya hosszĂˇnak Ă©s szĂ©lessĂ©gĂ©nek nyilvĂˇntartĂˇsĂˇhoz
        int width = 0,  height = 0;

        // Ebbe a segĂ©dmĂˇtrixba olvassuk be a pĂˇlyĂˇt.
        // R - Rail; E - Empty Field; S - Switch; T - Tunnel; C - CrossRail; szĂˇmok - ĂˇllomĂˇsok
        String[][] helpingmatrix = new String[25][25];

        // A szintnek megfelelĹ‘ txt kivĂˇlasztĂˇsa a fĂˇjlbĂłl valĂł beolvasĂˇshoz
        String leveltxt = null;
        if (level == 1) leveltxt = "1.txt";
        else if (level == 2) leveltxt = "2.txt";
        else if (level == 3) leveltxt = "3.txt";
        else if (level == 4) leveltxt = "4.txt";
        else if (level == 5) leveltxt = "5.txt";
        else if (level == 6) leveltxt = "6.txt";
        else if (level == 7) leveltxt = "7.txt";
        else if (level == 8) leveltxt = "8.txt";
        else if (level == 9) leveltxt = "9.txt";

        try {
            // Megnyitjuk olvasĂˇsra a megfelelĹ‘ txt-t.
            BufferedReader br = new BufferedReader( new FileReader(leveltxt));

            // Egy beolvasott sort tĂˇrol
            String line;
            // SoronkĂ©nt a fĂˇjl vĂ©gĂ©ig beolvassuk a tartalmĂˇt
            while ((line = br.readLine())!= null) {
                // Minden sort beolvasĂˇsĂˇnĂˇl 0-ra ĂˇllĂ­tjuk a szĂ©lessĂ©get, Ă­gy az elsĹ‘ helyre tudjuk beĂ­rni a beolvasott Ă©rtĂ©ket a mĂˇtrixba
                height = 0;
                // Daraboljuk a beolvasott sort szĂłkĂ¶zĂ¶k alapjĂˇn
                String[] splitterstring = line.split(" ");
                // Ameddig a sorban van adat, addig beĂ­rjuk a mĂˇtrix megfelelĹ‘ helyĂ©re
                // A height vĂˇltozĂłt minden elem utĂˇn nĂ¶veljĂĽk
                while (height < splitterstring.length)
                {
                    helpingmatrix[width][height] = splitterstring[height];
                    height++;
                }
                // MiutĂˇn beolvastunk egy sort, sort emelĂĽnk a mĂˇtrixban is
                width++;
            }
            // BezĂˇrjuk a fĂˇjlt
            br.close();
        } catch (IOException e) {
            System.err.println("Exception");
        }

        // LĂ©trehozok egy pĂˇlya mĂ©retĹ± tĂ¶mbĂ¶t, itt inicializĂˇlom Ă©s tĂˇrolom a pĂˇlya elemeit
        Field[][] field = new Field[width][height];

        for (int a = 0; a < width; a++)
            for (int b = 0; b < height; b++)
            {
                // SegĂ©dvĂˇltozĂłk az inicializĂˇlĂˇshoz
                Color color = null;

                // Minden beolvasott szĂˇm egy bizonyos szĂ­nĹ± ĂˇllomĂˇsnak felel meg
                // AlĂˇbb definiĂˇljuk, hogy milyen szĂˇmnak milyen szĂ­nĹ± ĂˇllomĂˇs felel meg
                if (helpingmatrix[a][b].equals("1")) color = Color.RED;
                else if (helpingmatrix[a][b].equals("2")) color = Color.BLUE;
                else if (helpingmatrix[a][b].equals("3")) color = Color.GREEN;
                else if (helpingmatrix[a][b].equals("4")) color = Color.ORANGE;
                else if (helpingmatrix[a][b].equals("5")) color = Color.YELLOW;
                else if (helpingmatrix[a][b].equals("6")) color = Color.BROWN;

                if (helpingmatrix[a][b].equals("R"))
                {
                    field[a][b] = new Rail();
                }
                else if (helpingmatrix[a][b].equals("E"))
                {
                    field[a][b] = new EmptyField();
                }
                else if (helpingmatrix[a][b].equals("S"))
                {
                    field[a][b] = new Switch();
                }
                else if (helpingmatrix[a][b].equals("T"))
                {
                    field[a][b] = new Tunnel(false, 0, null);
                }
                else if (helpingmatrix[a][b].equals("C"))
                {
                    field[a][b] = new CrossRail();
                }
                else
                    field[a][b] = new RailStation(color);
                System.out.println(helpingmatrix[a][b]);
                System.out.println(field[a][b]);
            }

        // VĂ©gigmegyek a tĂ¶mb minden elemĂ©n beĂˇllĂ­tom a szomszĂ©dait
        // A mezĹ‘k szomszĂ©dainak Ă©s elemeinek beĂˇllĂ­tĂˇsĂˇhoz vizsgĂˇljuk, hogy hol helyezkedik el a mezĹ‘ a tĂ¶mbben,
        // mivel pozĂ­ciĂłhoz mĂˇs validĂˇciĂł szĂĽksĂ©ges a nullpointerException hiba elkerĂĽlĂ©sĂ©hez
        for (int a = 0; a < width; a++)
            for (int b = 0; b < height; b++)
            {
                // SegĂ©dvĂˇltozĂłk az inicializĂˇlĂˇshoz
                boolean byTheStation = false;
                Rail pos1 = null;
                Rail pos2 = null;
                Rail pos3 = null;
                Rail pos4 = null;

                // 1. eset: A mĂˇtrix bal felsĹ‘ sarka
                if (a == 0 & b == 0)
                {
                    pos1 = (Rail)field[a][b+1];
                    pos2 = (Rail)field[a+1][b];

                    // MegvizsgĂˇljuk, hogy ĂˇllomĂˇs melett van e a mezĹ‘
                    if (!(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // LĂ©trehozzuk a start mezĹ‘t
                    field[a][b] = new StartRail(byTheStation, (Rail)field[a][b+1], (Rail)field[a+1][b]);

                    // BeĂˇllĂ­tjuk a mezĹ‘ szomszĂ©dait
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 2. eset: A mĂˇtrix felsĹ‘ sora a szĂ©lei nĂ©lkĂĽl

                else if (a == 0 & b > 0 & b < height-1)
                {
                    // MegvizsgĂˇljuk, hogy a mezĹ‘tĹ‘l balra lĂ©vĹ‘ mezĹ‘re tovĂˇbb mehet e a vonat,
                    // ehhez hasznĂˇljuk azt a segĂ©dtĂ¶mbĂ¶t, ahovĂˇ beolvastuk a fĂˇjlbĂłl az adatokat.
                    // Akkor mehet tovĂˇbb, ha R, S vagy T a beolvasott mezĹ‘, C nem lehet,
                    // mivel nincs 4 szomszĂ©dja.
                    // Ha tovĂˇbbmehet itt a vonat, akkor beĂˇllĂ­tjuk a pos1 Ă©rtĂ©kekĂ©nt, azaz az egyik tovĂˇbbhaladĂˇsi irĂˇnykĂ©nt.
                    if ((helpingmatrix[a][b-1].equals("R")) || (helpingmatrix[a][b-1].equals("S")) || (helpingmatrix[a][b-1].equals("T")))
                        pos1 = (Rail)field[a][b-1];

                    // MegvizsgĂˇljuk, hogy tovĂˇbbmehet e a mezĹ‘tĹ‘l jobbra a vonat.
                    // Ha igen, akkor attĂłl fĂĽggĹ‘en, hogy volt e mĂˇr tovĂˇbbhaladĂˇsi irĂˇnyunk a pos1 vagy pos2 Ă©rtĂ©kĂ©nek ĂˇllĂ­tjuk be.
                    if ((helpingmatrix[a][b+1].equals("R")) || (helpingmatrix[a][b+1].equals("S")) || (helpingmatrix[a][b+1].equals("T")))
                        if (pos1 == null)
                            pos1 = (Rail)field[a][b+1];
                        else pos2 = (Rail)field[a][b+1];

                    // MegvizsgĂˇljuk, hogy tovĂˇbbmehet e a vonat a mezĹ‘tĹ‘l lefelĂ© lĂ©vĹ‘ sĂ­nre.
                    // AttĂłl fĂĽggĹ‘en, hogy vĂˇltĂł vagy nem a pos2 vagy pos3 Ă©rtĂ©kĂ©nek ĂˇllĂ­tjuk be.
                    // Onnan tudjuk hogy vĂˇltĂł, hogy mĂˇr a pos1 Ă©s a pos2 is kapott Ă©rtĂ©ket.
                    if ((helpingmatrix[a+1][b].equals("R")) || (helpingmatrix[a+1][b].equals("S")) || (helpingmatrix[a+1][b].equals("T")))
                        if (pos2 == null)
                            pos2 = (Rail)field[a+1][b];
                        else pos3 = (Rail)field[a+1][b];

                    // MegvizsgĂˇljuk, hogy a mezĹ‘ ĂˇllomĂˇs mellett van e vagy sem.
                    if (!(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")) & !(helpingmatrix[a+1][b].equals("C")))
                        byTheStation = true;

                    // BeĂˇllĂ­tjuk a mezĹ‘ szomszĂ©dait
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 3. eset: A mĂˇtrix jobb felsĹ‘ sarka
                else if (a == 0 & b == height-1)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("T"))
                    {
                        pos1 = (Rail)field[a][b-1];
                        pos2 = (Rail)field[a+1][b];
                    }

                    // MegvizsgĂˇljuk, hogy a mezĹ‘ ĂˇllomĂˇs mellett van e
                    if (!(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // BeĂˇllĂ­tjuk a szomszĂ©dait
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 4. eset: A mĂˇtrix bal oldala a vĂ©ge Ă©s alja nĂ©lkĂĽl
                else if (a > 0 & a < width-1 & b == 0)
                {
                    // TovĂˇbbhaladĂˇsi irĂˇny vizsgĂˇlatok
                    if ((helpingmatrix[a-1][b].equals("R")) || (helpingmatrix[a-1][b].equals("S")) || (helpingmatrix[a-1][b].equals("T")))
                        pos1 = (Rail)field[a-1][b];

                    if ((helpingmatrix[a][b+1].equals("R")) || (helpingmatrix[a][b+1].equals("S")) || (helpingmatrix[a][b+1].equals("T")) || (helpingmatrix[a][b+1].equals("C")))
                        if (pos1 == null)
                            pos1 = (Rail)field[a][b+1];
                        else pos2 = (Rail)field[a][b+1];

                    if ((helpingmatrix[a+1][b].equals("R")) || (helpingmatrix[a+1][b].equals("S")) || (helpingmatrix[a+1][b].equals("T")))
                        if (pos2 == null)
                            pos2 = (Rail)field[a+1][b];
                        else pos3 = (Rail)field[a+1][b];

                    // Ă�llomĂˇs mellettisĂ©g vizsgĂˇlat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E")) & !(helpingmatrix[a][b+1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;
                    //SzomszĂ©dok beĂˇllĂ­tĂˇsa
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // Az 5. eset: a mĂˇtrix belseje
                else if (a > 0 & a < width-1 & b > 0 & b < height-1)
                {
                    // TovĂˇbbhaladĂˇsi irĂˇny vizsgĂˇlat
                    if ((helpingmatrix[a-1][b].equals("R")) || (helpingmatrix[a-1][b].equals("S")) || (helpingmatrix[a-1][b].equals("T")) || (helpingmatrix[a-1][b].equals("C")))
                        pos1 = (Rail)field[a-1][b];

                    if ((helpingmatrix[a+1][b].equals("R")) || (helpingmatrix[a+1][b].equals("S")) || (helpingmatrix[a+1][b].equals("T")) || (helpingmatrix[a+1][b].equals("C")))
                        if (pos1 == null)
                            pos1 = (Rail)field[a+1][b];
                        else pos2 = (Rail)field[a+1][b];

                    if ((helpingmatrix[a][b+1].equals("R")) || (helpingmatrix[a][b+1].equals("S")) || (helpingmatrix[a][b+1].equals("T")) || (helpingmatrix[a][b+1].equals("C")))
                        if (pos1 == null)
                            pos1 = (Rail)field[a][b+1];
                        else if (pos2 == null)
                            pos2 = (Rail)field[a][b+1];
                        else
                            pos3 = (Rail)field[a][b+1];

                    if ((helpingmatrix[a][b-1].equals("R")) || (helpingmatrix[a][b-1].equals("S")) || (helpingmatrix[a][b-1].equals("T")) || (helpingmatrix[a][b-1].equals("C")))
                        if (pos2 == null)
                            pos2 = (Rail)field[a][b-1];
                        else if (pos3 == null)
                            pos3 = (Rail)field[a][b-1];
                        else pos4 = (Rail)field[a][b-1];

                    // Ă�llomĂˇs mellettisĂ©g vizsgĂˇlat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))  & !(helpingmatrix[a-1][b].equals("C"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E")) & !(helpingmatrix[a][b+1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")) & !(helpingmatrix[a+1][b].equals("C"))
                            || !(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E")) & !(helpingmatrix[a][b-1].equals("C")))
                        byTheStation = true;

                    // SzomszĂ©dok beĂˇllĂ­tĂˇsa
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field [a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 6. eset: a mĂˇtrix jobb oldala a vĂ©gei nĂ©lkĂĽl
                else if (a > 0 & a < width-1 & b == height-1)
                {
                    // TovĂˇbbhaladĂˇsi irĂˇny vizsgĂˇlatok
                    if ((helpingmatrix[a-1][b].equals("R")) || (helpingmatrix[a-1][b].equals("S")) || (helpingmatrix[a-1][b].equals("T")))
                        pos1 = (Rail)field[a-1][b];

                    if ((helpingmatrix[a][b-1].equals("R")) || (helpingmatrix[a][b-1].equals("S")) || (helpingmatrix[a][b-1].equals("T")) || (helpingmatrix[a][b-1].equals("C")))
                        if (pos1 == null)
                            pos1 = (Rail)field[a][b-1];
                        else pos2 = (Rail)field[a][b-1];

                    if ((helpingmatrix[a+1][b].equals("R")) || (helpingmatrix[a+1][b].equals("S")) || (helpingmatrix[a+1][b].equals("T")))
                        if (pos2 == null)
                            pos2 = (Rail)field[a+1][b];
                        else
                            pos3 = (Rail)field[a+1][b];

                    // Ă�llomĂˇs mellettisĂ©g vizsgĂˇlat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E")) & !(helpingmatrix[a][b-1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // SzomszĂ©dok beĂˇllĂ­tĂˇsa
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 7. eset: A mĂˇtrix bal alsĂł sarka
                else if (a == width-1 & b == 0)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("T"))
                    {
                        pos1 = (Rail)field[a-1][b];
                        pos2 = (Rail)field[a][b+1];
                    }

                    // Ă�llomĂˇs mellettsĂ©g vizsgĂˇlat
                    // Itt nem kell tovĂˇbbhaladĂˇsi irĂˇnyt vizsgĂˇlni, mivel mindkĂ©t szomszĂ©djĂˇn tovĂˇbbmehet
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E")))
                        byTheStation = true;

                    // SzomszĂ©dok beĂˇllĂ­tĂˇsa
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }
                // A 8. eset: a mĂˇtrix alsĂł sora a vĂ©gei nĂ©lkĂĽl
                else if (a == width-1 & b > 0 & b < height-1)
                {
                    // TovĂˇbbhaladĂˇsi irĂˇny vizsgĂˇlatok
                    if ((helpingmatrix[a][b-1].equals("R")) || (helpingmatrix[a][b-1].equals("S")) || (helpingmatrix[a][b-1].equals("T")))
                        pos1 = (Rail)field[a][b-1];

                    if ((helpingmatrix[a-1][b].equals("R")) || (helpingmatrix[a-1][b].equals("S")) || (helpingmatrix[a-1][b].equals("T")) || (helpingmatrix[a-1][b].equals("C")))
                        if (pos1 == null)
                            pos1 = (Rail)field[a-1][b];
                        else pos2 = (Rail)field[a-1][b];

                    if ((helpingmatrix[a][b+1].equals("R")) || (helpingmatrix[a][b+1].equals("S")) || (helpingmatrix[a][b+1].equals("T")))
                        if (pos2 == null)
                            pos2 = (Rail)field[a][b+1];
                        else
                            pos3 = (Rail)field[a][b+1];

                    // Ă�llomĂˇs mellettisĂ©g vizsgĂˇlat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E")) & !(helpingmatrix[a-1][b].equals("C"))
                            || !(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E")))
                        byTheStation = true;

                    // SzomszĂ©dok beĂˇllĂ­tĂˇsa
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }
                // A 9. eset: a mĂˇtrix jobb alsĂł sarka
                else if (a == width-1 & b == height-1)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("R"))
                    {
                        pos1 = (Rail)field[a][b-1];
                        pos2 = (Rail)field[a-1][b];
                    }

                    // Ă�llomĂˇs mellettisĂ©g vizsgĂˇlat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E")))
                        byTheStation = true;

                    // SzomszĂ©dok beĂˇllĂ­tĂˇsa
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }

                // BeĂˇllĂ­tom a megfelelĹ‘ tĂ­pusĂş mezĹ‘k adatait, melyek vonatkoznak mĂˇs mezĹ‘re is vagy validĂˇciĂłhoz kĂ¶tĂ¶ttek,
                // Ă­gy elsĹ‘ kĂ¶rben nem lehetett beĂˇllĂ­tani, kĂĽlĂ¶nben null Ă©rkĂ©kkel szerepelne.
                if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("S") ||
                        helpingmatrix[a][b].equals("T") || helpingmatrix[a][b].equals("C"))
                {
                    ((Rail)field[a][b]).setByTheStation(byTheStation);
                    ((Rail)field[a][b]).setPossibleRail1(pos1);
                    ((Rail)field[a][b]).setPossibleRail2(pos2);

                    if (helpingmatrix[a][b].equals("S"))
                    {
                        ((Switch)field[a][b]).setSelectedRail(pos1);
                        ((Switch)field[a][b]).setNotselectedRail(pos2);
                        ((Switch)field[a][b]).setFixRail(pos3);
                    }
                    else if (helpingmatrix[a][b].equals("C"))
                    {
                        ((CrossRail)field[a][b]).setPossibleRail3(pos3);
                        ((CrossRail)field[a][b]).setPossibleRail4(pos4);
                    }
                }
            }
        // BeĂˇllĂ­tom az vĂˇltozĂłk Ă©rtĂ©keit, hogy mĂˇs fĂĽggvĂ©nyek is el tudjĂˇk Ă©rni
        // Ă©s a mepcreator ĂşjbĂłli megnyitĂˇsĂˇra ne kapjunk szemetet ezen vĂˇltozĂłk Ă©rtĂ©keikĂ©nt
        Fields = field;
        realWidth = width;
        realHeight = height;
        map = helpingmatrix;

        // Visszaadom a startmezĹ‘t
        return (Rail)field[0][0];
    }
    /*
     * Egy ID alapjĂˇn megmondja, hogy mi van abban a mezĹ‘ben
     * Ha az ID nagyobb, mint a pĂˇlyĂˇn lĂ©vĹ‘ mezĹ‘k szĂˇma, akkor null-t ad vissza
     * Az ID a mezĹ‘ szĂˇmĂˇnak felel meg a bal elsĹ‘ sarokbĂłl indulva
     * soronkĂ©nt haladva Ă©s nĂ¶velve egy 0 kezdĹ‘ Ă©rtĂ©kĹ± vĂˇltozĂłt
     *
     * @param ID mező azonosítására használt változó.
     * @return Paraméterhez tartozó mező visszaadása
    */
    public Field searchField(int ID)
    {
        // SegĂ©dvĂˇltozĂłk lĂ©trehozĂˇsa
        int helpingcounter = 0;
        int helpingWidth = 0;
        int helpingheight = 0;

        if (ID > realWidth*realHeight)
            return null;
        else
        {
            for (int i = 0; i < realWidth; i++)
                for (int j = 0; j < realHeight; j++)
                {
                    helpingcounter++;
                    if (helpingcounter == ID)
                    {
                        helpingWidth = i;
                        helpingheight = j;
                    }
                }
            return Fields[helpingWidth][helpingheight];
        }
    }

    /*
     * Megmondja, hogy a paramĂ©terkĂ©nt kapott mezĹ‘nek mi az ID-ja.
     * Az ID a fent meghatĂˇrozott mĂłdon a mezĹ‘ szĂˇmĂˇt jelĂ¶li, amelyben a field van.
     *
     * @param field Lekérdezni kivánt mező
     * @return Lekérdezett mező ID-jének visszaadása
     */
    public int searchID(Field field)
    {
        int helpingcounter = 0;
        int returning = 0;
        for (int i = 0; i < realWidth; i++)
            for (int j = 0; j < realHeight; j++)
            {
                helpingcounter++;
                if (field == Fields[i][j])
                    returning =  helpingcounter;
            }
        return returning;
    }

    public static Tunnel[] searchselectedTunnels()
    {
        // Itt tĂˇroljuk a kĂ©t megĂ©pĂ­tett Tunnel-t.
        Tunnel[] selectedTunnels = new Tunnel[2];

        // A tĂ¶mbbeli helye a megĂ©pĂ­tett Tunnel-nek.
        int selectedNumber = 0;

        for (int i = 0; i < realWidth; i++)
            for (int j = 0; j < realHeight; j++)
            {
                if (map[i][j].equals("T"))
                    if (((Tunnel)Fields[i][j]).isSelected() == true)
                    {
                        selectedTunnels[selectedNumber] = (Tunnel)Fields[i][j];
                        selectedNumber++;
                    }
            }
        return selectedTunnels;
    }
}