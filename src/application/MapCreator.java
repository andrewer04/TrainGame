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

    private int realWidth,  realHeight;

    private Field[][] Fields = null;


    public Rail build(int level){
        //  System.out.println(" build() - letrehozza a(z) " + level + ". palyat, mezoit Ã©s azok osszefuggeseit");

        // Segédváltozó a pálya hosszának és szélességének nyilvántartásához
        int width = 0,  height = 0;

        // Ebbe a segédmátrixba olvassuk be a pályát.
        // R - Rail; E - Empty Field; S - Switch; T - Tunnel; C - CrossRail; számok - állomások
        String[][] helpingmatrix = new String[25][25];

        // A szintnek megfelelő txt kiválasztása a fájlból való beolvasáshoz
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
            // Megnyitjuk olvasásra a megfelelő txt-t.
            BufferedReader br = new BufferedReader( new FileReader(leveltxt));

            // Egy beolvasott sort tárol
            String line;
            // Soronként a fájl végéig beolvassuk a tartalmát
            while ((line = br.readLine())!= null) {
                // Minden sort beolvasásánál 0-ra állítjuk a szélességet, így az első helyre tudjuk beírni a beolvasott értéket a mátrixba
                height = 0;
                // Daraboljuk a beolvasott sort szóközök alapján
                String[] splitterstring = line.split(" ");
                // Ameddig a sorban van adat, addig beírjuk a mátrix megfelelő helyére
                // A height változót minden elem után növeljük
                while (height < splitterstring.length)
                {
                    helpingmatrix[width][height] = splitterstring[height];
                    height++;
                }
                // Miután beolvastunk egy sort, sort emelünk a mátrixban is
                width++;
            }
            // Bezárjuk a fájlt
            br.close();
        } catch (IOException e) {
            System.err.println("Exception");
        }

        // Létrehozok egy pálya méretű tömböt, itt inicializálom és tárolom a pálya elemeit
        Field[][] field = new Field[width][height];

        for (int a = 0; a < width; a++)
            for (int b = 0; b < height; b++)
            {
                // Segédváltozók az inicializáláshoz
                Color color = null;

                // Minden beolvasott szám egy bizonyos színű állomásnak felel meg
                // Alább definiáljuk, hogy milyen számnak milyen színű állomás felel meg
                if (helpingmatrix[a][b] == "1") color = Color.RED;
                else if (helpingmatrix[a][b] == "2") color = Color.BLUE;
                else if (helpingmatrix[a][b] == "3") color = Color.GREEN;
                else if (helpingmatrix[a][b] == "4") color = Color.ORANGE;
                else if (helpingmatrix[a][b] == "5") color = Color.YELLOW;
                else if (helpingmatrix[a][b] == "6") color = Color.BROWN;

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

        // Végigmegyek a tömb minden elemén beállítom a szomszédait
        // A mezők szomszédainak és elemeinek beállításához vizsgáljuk, hogy hol helyezkedik el a mező a tömbben,
        // mivel pozícióhoz más validáció szükséges a nullpointerException hiba elkerüléséhez
        for (int a = 0; a < width; a++)
            for (int b = 0; b < height; b++)
            {
                // Segédváltozók az inicializáláshoz
                boolean byTheStation = false;
                Rail pos1 = null;
                Rail pos2 = null;
                Rail pos3 = null;
                Rail pos4 = null;

                // 1. eset: A mátrix bal felső sarka
                if (a == 0 & b == 0)
                {
                    pos1 = (Rail)field[a][b+1];
                    pos2 = (Rail)field[a+1][b];

                    // Megvizsgáljuk, hogy állomás melett van e a mező
                    if (!(helpingmatrix[a][b+1].equals("R")) || !(helpingmatrix[a][b+1].equals("S")) || !(helpingmatrix[a][b+1].equals("T")) || !(helpingmatrix[a][b+1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) || !(helpingmatrix[a+1][b].equals("S")) || !(helpingmatrix[a+1][b].equals("T")) || !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // Létrehozzuk a start mezőt
                    field[a][b] = new StartRail(byTheStation, (Rail)field[a][b+1], (Rail)field[a+1][b]);

                    // Beállítjuk a mező szomszédait
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 2. eset: A mátrix felső sora a szélei nélkül

                else if (a == 0 & b > 0 & b < height-1)
                {
                    // Megvizsgáljuk, hogy a mezőtől balra lévő mezőre tovább mehet e a vonat,
                    // ehhez használjuk azt a segédtömböt, ahová beolvastuk a fájlból az adatokat.
                    // Akkor mehet tovább, ha R, S vagy T a beolvasott mező, C nem lehet,
                    // mivel nincs 4 szomszédja.
                    // Ha továbbmehet itt a vonat, akkor beállítjuk a pos1 értékeként, azaz az egyik továbbhaladási irányként.
                    if ((helpingmatrix[a][b-1].equals("R")) || (helpingmatrix[a][b-1].equals("S")) || (helpingmatrix[a][b-1].equals("T")))
                        pos1 = (Rail)field[a][b-1];

                    // Megvizsgáljuk, hogy továbbmehet e a mezőtől jobbra a vonat.
                    // Ha igen, akkor attól függően, hogy volt e már továbbhaladási irányunk a pos1 vagy pos2 értékének állítjuk be.
                    if ((helpingmatrix[a][b+1].equals("R")) || (helpingmatrix[a][b+1].equals("S")) || (helpingmatrix[a][b+1].equals("T")))
                        if (pos1 == null)
                            pos1 = (Rail)field[a][b+1];
                        else pos2 = (Rail)field[a][b+1];

                    // Megvizsgáljuk, hogy továbbmehet e a vonat a mezőtől lefelé lévő sínre.
                    // Attól függően, hogy váltó vagy nem a pos2 vagy pos3 értékének állítjuk be.
                    // Onnan tudjuk hogy váltó, hogy már a pos1 és a pos2 is kapott értéket.
                    if ((helpingmatrix[a+1][b].equals("R")) || (helpingmatrix[a+1][b].equals("S")) || (helpingmatrix[a+1][b].equals("T")))
                        if (pos2 == null)
                            pos2 = (Rail)field[a+1][b];
                        else pos3 = (Rail)field[a+1][b];

                    // Megvizsgáljuk, hogy a mező állomás mellett van e vagy sem.
                    if (!(helpingmatrix[a][b-1].equals("R")) || !(helpingmatrix[a][b-1].equals("S")) || !(helpingmatrix[a][b-1].equals("T")) || !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) || !(helpingmatrix[a][b+1].equals("S")) || !(helpingmatrix[a][b+1].equals("T")) || !(helpingmatrix[a][b+1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) || !(helpingmatrix[a+1][b].equals("S")) || !(helpingmatrix[a+1][b].equals("T")) || !(helpingmatrix[a+1][b].equals("E")) || !(helpingmatrix[a+1][b].equals("C")))
                        byTheStation = true;

                    // Beállítjuk a mező szomszédait
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 3. eset: A mátrix jobb felső sarka
                else if (a == 0 & b == height-1)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("T"))
                    {
                        pos1 = (Rail)field[a][b-1];
                        pos2 = (Rail)field[a+1][b];
                    }

                    // Megvizsgáljuk, hogy a mező állomás mellett van e
                    if (!(helpingmatrix[a][b-1].equals("R")) || !(helpingmatrix[a][b-1].equals("S")) || !(helpingmatrix[a][b-1].equals("T")) || !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) || !(helpingmatrix[a+1][b].equals("S")) || !(helpingmatrix[a+1][b].equals("T")) || !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // Beállítjuk a szomszédait
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 4. eset: A mátrix bal oldala a vége és alja nélkül
                else if (a > 0 & a < width-1 & b == 0)
                {
                    // Továbbhaladási irány vizsgálatok
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

                    // Állomás mellettiség vizsgálat
                    if (!(helpingmatrix[a-1][b].equals("R")) || !(helpingmatrix[a-1][b].equals("S")) || !(helpingmatrix[a-1][b].equals("T")) || !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) || !(helpingmatrix[a][b+1].equals("S")) || !(helpingmatrix[a][b+1].equals("T")) || !(helpingmatrix[a][b+1].equals("E")) || (helpingmatrix[a][b+1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) || !(helpingmatrix[a+1][b].equals("S")) || !(helpingmatrix[a+1][b].equals("T")) || !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    //Szomszédok beállítása
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // Az 5. eset: a mátrix belseje
                else if (a > 0 & a < width-1 & b > 0 & b < height-1)
                {
                    // Továbbhaladási irány vizsgálat
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

                    // Állomás mellettiség vizsgálat
                    if (!(helpingmatrix[a-1][b].equals("R")) || !(helpingmatrix[a-1][b].equals("S")) || !(helpingmatrix[a-1][b].equals("T")) || (helpingmatrix[a-1][b].equals("E"))  || (helpingmatrix[a-1][b].equals("C"))
                            || !(helpingmatrix[a][b+1].equals("R")) || !(helpingmatrix[a][b+1].equals("S")) || !(helpingmatrix[a][b+1].equals("T")) || (helpingmatrix[a][b+1].equals("E")) || (helpingmatrix[a][b+1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) || !(helpingmatrix[a+1][b].equals("S")) || !(helpingmatrix[a+1][b].equals("T")) || (helpingmatrix[a+1][b].equals("E")) || (helpingmatrix[a+1][b].equals("C"))
                            || !(helpingmatrix[a][b-1].equals("R")) || !(helpingmatrix[a][b-1].equals("S")) || !(helpingmatrix[a][b-1].equals("T")) || (helpingmatrix[a][b-1].equals("E")) || (helpingmatrix[a][b-1].equals("C")))
                        byTheStation = true;

                    // Szomszédok beállítása
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field [a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 6. eset: a mátrix jobb oldala a végei nélkül
                else if (a > 0 & a < width-1 & b == height-1)
                {
                    // Továbbhaladási irány vizsgálatok
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

                    // Állomás mellettiség vizsgálat
                    if (!(helpingmatrix[a-1][b].equals("R")) || !(helpingmatrix[a-1][b].equals("S")) || !(helpingmatrix[a-1][b].equals("T")) || !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b-1].equals("R")) || !(helpingmatrix[a][b-1].equals("S")) || !(helpingmatrix[a][b-1].equals("T")) || !(helpingmatrix[a][b-1].equals("E")) || !(helpingmatrix[a][b-1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) || !(helpingmatrix[a+1][b].equals("S")) || !(helpingmatrix[a+1][b].equals("T")) || !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // Szomszédok beállítása
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 7. eset: A mátrix bal alsó sarka
                else if (a == width-1 & b == 0)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("T"))
                    {
                        pos1 = (Rail)field[a-1][b];
                        pos2 = (Rail)field[a][b+1];
                    }

                    // Állomás mellettség vizsgálat
                    // Itt nem kell továbbhaladási irányt vizsgálni, mivel mindkét szomszédján továbbmehet
                    if (!(helpingmatrix[a-1][b].equals("R")) || !(helpingmatrix[a-1][b].equals("S")) || !(helpingmatrix[a-1][b].equals("T")) || !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) || !(helpingmatrix[a][b+1].equals("S")) || !(helpingmatrix[a][b+1].equals("T")) || !(helpingmatrix[a][b+1].equals("E")))
                        byTheStation = true;

                    // Szomszédok beállítása
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }
                // A 8. eset: a mátrix alsó sora a végei nélkül
                else if (a == width-1 & b > 0 & b < height-1)
                {
                    // Továbbhaladási irány vizsgálatok
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

                    // Állomás mellettiség vizsgálat
                    if (!(helpingmatrix[a-1][b].equals("R")) || !(helpingmatrix[a-1][b].equals("S")) || !(helpingmatrix[a-1][b].equals("T")) || !(helpingmatrix[a-1][b].equals("E")) || !(helpingmatrix[a-1][b].equals("C"))
                            || !(helpingmatrix[a][b-1].equals("R")) || !(helpingmatrix[a][b-1].equals("S")) || !(helpingmatrix[a][b-1].equals("T")) || !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) || !(helpingmatrix[a][b+1].equals("S")) || !(helpingmatrix[a][b+1].equals("T")) || !(helpingmatrix[a][b+1].equals("E")))
                        byTheStation = true;

                    // Szomszédok beállítása
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }
                // A 9. eset: a mátrix jobb alsó sarka
                else if (a == width-1 & b == height-1)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("R"))
                    {
                        pos1 = (Rail)field[a][b-1];
                        pos2 = (Rail)field[a-1][b];
                    }

                    // Állomás mellettiség vizsgálat
                    if (!(helpingmatrix[a-1][b].equals("R")) || !(helpingmatrix[a-1][b].equals("S")) || !(helpingmatrix[a-1][b].equals("T")) || !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b-1].equals("R")) || !(helpingmatrix[a][b-1].equals("S")) || !(helpingmatrix[a][b-1].equals("T")) || !(helpingmatrix[a][b-1].equals("E")))
                        byTheStation = true;

                    // Szomszédok beállítása
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }

                // Beállítom a megfelelő típusú mezők adatait, melyek vonatkoznak más mezőre is vagy validációhoz kötöttek,
                // így első körben nem lehetett beállítani, különben null érkékkel szerepelne.
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
        // Beállítom az változók értékeit, hogy más függvények is el tudják érni
        // és a mepcreator újbóli megnyitására ne kapjunk szemetet ezen változók értékeiként
        Fields = field;
        realWidth = width;
        realHeight = height;

        // Visszaadom a startmezőt
        return (Rail)field[0][0];
    }

    // Egy ID alapján megmondja, hogy mi van abban a mezőben
    // Ha az ID nagyobb, mint a pályán lévő mezők száma, akkor null-t ad vissza
    // Az ID a mező számának felel meg a bal első sarokból indulva
    // soronként haladva és növelve egy 0 kezdő értékű változót
    public Field searchField(int ID)
    {
        // Segédváltozók létrehozása
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

    // Megmondja, hogy a paraméterként kapott mezőnek mi az ID-ja.
    // Az ID a fent meghatározott módon a mező számát jelöli, amelyben a field van.
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
}
