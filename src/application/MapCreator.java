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

    // Segedvaltozok a palya szelessegehez, es hosszahoz
    private static int realWidth;

    private static int realHeight;

    // Segedmatrixok a beolvasott palyahoz es az inicialt palyahoz.
    private static String[][] map = null;
    private static Field[][] Fields = null;

    /*
     * Letrehozza a kivant palyat. A parameterkent atadott palyat kesziti el.
     * Beolvassuk a palyat egy tombbe, az alapjan letrehozzunk
     * egy a palya tipusait tarolo tombot, ahova inicializaljuk a mezoket.
     * Majd pozicio szerinti keresessel beallitjuk a szomszedait
     * es a lehetseges tovabbhaladasi iranyokat
     *
     * @param level palya nehezsegi szintjenek tarolasara hasznalt valtozo
     */
    public Rail build(int level){
        //  System.out.println(" build() - letrehozza a(z) " + level + ". palyat, mezoit aÂ©s azok osszefuggeseit");

        // Segedvaltozo a palya hosszanak es szelessegenek nyilvantartasahoz
        int width = 0,  height = 0;

        // Ebbe a segedmadtrixba olvassuk a palayat
        // R - Rail; E - Empty Field; S - Switch; T - Tunnel; C - CrossRail; szamok-allomasok
        String[][] helpingmatrix = new String[25][25];

        // A szintnek megfelelo txt kivalasztasa a fajlbol valo olvasashoz
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
            // Megnyitjuk olvasasra a megfelelo txt-t
            BufferedReader br = new BufferedReader( new FileReader(leveltxt));

            // Egy beolvasott sort tarol
            String line;
            // Soronkent a fajl vegeig beolvassuk a fajl tartalmat
            while ((line = br.readLine())!= null) {
                // Minden sort beolvasasanal 0-ra allitjuk a szelesseget,
                // Igy az elso helyre tudjuk beirni a beolvasott erteket a matrixba
                height = 0;
                // Daraboljuk a beolvasott sort szokozok alapajan
                String[] splitterstring = line.split(" ");
                // Ameddig a sorban van adat, addig beirjuk a matrix megfelelo helyere
                // A height valtozasat minden elem utan noveljuk
                while (height < splitterstring.length)
                {
                    helpingmatrix[width][height] = splitterstring[height];
                    height++;
                }
                // Miutan beolvastunk egy sort, a sort elmentjuk a matrixban
                width++;
            }
            // Bezarjuk a fajlt
            br.close();
        } catch (IOException e) {
            System.err.println("Exception");
        }

        // Letrehozok egy palya meretu tombot, itt inicializalom es tarolom a palya elemeit
        Field[][] field = new Field[width][height];

        for (int a = 0; a < width; a++)
            for (int b = 0; b < height; b++)
            {
                // Segedvaltozo az inicializalashoz
                Color color = null;

                // Minden beolvasott szam, egy bizonyos szinu allomasnak felel meg
                // Alabb definialjuk, hogy milyenszamnak milyen allomas felel meg
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

        // Vegigmegyek a tomb minden elemen, beallitom a szomszedait
        // A mezok szomszedainak es elemeinek beallitasahoz vizsgaljuk, hogy hol helyezkedik el a mezo a tombben,
        // mivel poziciohoz mas validacio szukseges a nullpointerException hiba elkerulesehez
        for (int a = 0; a < width; a++)
            for (int b = 0; b < height; b++)
            {
                // Segedvaltozok inicializalashoz
                boolean byTheStation = false;
                Rail pos1 = null;
                Rail pos2 = null;
                Rail pos3 = null;
                Rail pos4 = null;

                // 1. eset: A matrix bal felso sarka
                if (a == 0 & b == 0)
                {
                    pos1 = (Rail)field[a][b+1];
                    pos2 = (Rail)field[a+1][b];

                    // Megvizsgaljuk, hogy allomas melett van e a mezo
                    if (!(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // Letrehozzuk a start mezot
                    field[a][b] = new StartRail(byTheStation, (Rail)field[a][b+1], (Rail)field[a+1][b]);

                    // Beallitjuk a mezo szomszedait
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 2. eset: A matrix felso sora a szelei nelkul

                else if (a == 0 & b > 0 & b < height-1)
                {
                    // Megvizsgaljuk, hogy a mezotol balra levo mezore tovabb mehet e a vonat,
                    // ehhez hasznaljuk azt a segedtombot, ahova beolvastuk a fajlbol az adatokat.
                    // Akkor mehet tovabb, ha R, S vagy T a beolvasott mezo, C nem lehet,
                    // mivel nincs 4 szomszedja.
                    // Ha tovabbmehet itt a vonat, akkor beallitjuk a pos1 ertekekent, azaz az egyik tovabbhaladasi iranykent.
                    if ((helpingmatrix[a][b-1].equals("R")) || (helpingmatrix[a][b-1].equals("S")) || (helpingmatrix[a][b-1].equals("T")))
                        pos1 = (Rail)field[a][b-1];

                    // Megvizsgaljuk, hogy tovabbmehet e a mezotol jobbra a vonat.
                    // Ha igen, akkor attol fuggoen, hogy volt e mar tovabbhaladasi iranyunk a pos1 vagy pos2 ertekenek allitjuk be.
                    if ((helpingmatrix[a][b+1].equals("R")) || (helpingmatrix[a][b+1].equals("S")) || (helpingmatrix[a][b+1].equals("T")))
                        if (pos1 == null)
                            pos1 = (Rail)field[a][b+1];
                        else pos2 = (Rail)field[a][b+1];

                    // Megvizsgaljuk, hogy tovabbmehet e a vonat a mezotol lefele levo sinre.
                    // Attol fuggoen, hogy valto vagy nem a pos2 vagy pos3 ertekenek allitjuk be.
                    // Onnan tudjuk hogy valto, hogy mar a pos1 es a pos2 is kapott erteket.
                    if ((helpingmatrix[a+1][b].equals("R")) || (helpingmatrix[a+1][b].equals("S")) || (helpingmatrix[a+1][b].equals("T")))
                        if (pos2 == null)
                            pos2 = (Rail)field[a+1][b];
                        else pos3 = (Rail)field[a+1][b];

                    // Megvizsgaljuk, hogy a mezo allomas mellett van e vagy sem.
                    if (!(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")) & !(helpingmatrix[a+1][b].equals("C")))
                        byTheStation = true;

                    // Beallitjuk a mezo szomszedait
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 3. eset: A matrix jobb felso sarka
                else if (a == 0 & b == height-1)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("T"))
                    {
                        pos1 = (Rail)field[a][b-1];
                        pos2 = (Rail)field[a+1][b];
                    }

                    // Megvizsgaljuk, hogy a mezo allomas mellett van e
                    if (!(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // Beallitjuk a szomszedait
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(null);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 4. eset: A matrix bal oldala a vege es alja nelkul
                else if (a > 0 & a < width-1 & b == 0)
                {
                    // Tovabbhaladasi irany vizsgalatok
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

                    // allomas mellettiseg vizsgalat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E")) & !(helpingmatrix[a][b+1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;
                    //Szomszedok beallitasa
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // Az 5. eset: a matrix belseje
                else if (a > 0 & a < width-1 & b > 0 & b < height-1)
                {
                    // Tovabbhaladasi irany vizsgalat
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

                    // allomas mellettiseg vizsgalat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))  & !(helpingmatrix[a-1][b].equals("C"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E")) & !(helpingmatrix[a][b+1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")) & !(helpingmatrix[a+1][b].equals("C"))
                            || !(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E")) & !(helpingmatrix[a][b-1].equals("C")))
                        byTheStation = true;

                    // Szomszedok beallitasa
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field [a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 6. eset: a matrix jobb oldala a vegei nelkul
                else if (a > 0 & a < width-1 & b == height-1)
                {
                    // Tovabbhaladasi irany vizsgalatok
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

                    // allomas mellettiseg vizsgalat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E")) & !(helpingmatrix[a][b-1].equals("C"))
                            || !(helpingmatrix[a+1][b].equals("R")) & !(helpingmatrix[a+1][b].equals("S")) & !(helpingmatrix[a+1][b].equals("T")) & !(helpingmatrix[a+1][b].equals("E")))
                        byTheStation = true;

                    // Szomszedok beallitasa
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(field[a+1][b]);
                }
                // A 7. eset: A matrix bal also sarka
                else if (a == width-1 & b == 0)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("T"))
                    {
                        pos1 = (Rail)field[a-1][b];
                        pos2 = (Rail)field[a][b+1];
                    }

                    // allomas mellettseg vizsgalat
                    // Itt nem kell tovabbhaladasi iranyt vizsgalni, mivel mindket szomszedjan tovabbmehet
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E")))
                        byTheStation = true;

                    // Szomszedok beallitasa
                    field[a][b].setLeft(null);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }
                // A 8. eset: a matrix also sora a vegei nelkul
                else if (a == width-1 & b > 0 & b < height-1)
                {
                    // Tovabbhaladasi irany vizsgalatok
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

                    // allomas mellettiseg vizsgalat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E")) & !(helpingmatrix[a-1][b].equals("C"))
                            || !(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E"))
                            || !(helpingmatrix[a][b+1].equals("R")) & !(helpingmatrix[a][b+1].equals("S")) & !(helpingmatrix[a][b+1].equals("T")) & !(helpingmatrix[a][b+1].equals("E")))
                        byTheStation = true;

                    // Szomszedok beallitasa
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(field[a][b+1]);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }
                // A 9. eset: a matrix jobb also sarka
                else if (a == width-1 & b == height-1)
                {
                    if (helpingmatrix[a][b].equals("R") || helpingmatrix[a][b].equals("R"))
                    {
                        pos1 = (Rail)field[a][b-1];
                        pos2 = (Rail)field[a-1][b];
                    }

                    // allomas mellettiseg vizsgalat
                    if (!(helpingmatrix[a-1][b].equals("R")) & !(helpingmatrix[a-1][b].equals("S")) & !(helpingmatrix[a-1][b].equals("T")) & !(helpingmatrix[a-1][b].equals("E"))
                            || !(helpingmatrix[a][b-1].equals("R")) & !(helpingmatrix[a][b-1].equals("S")) & !(helpingmatrix[a][b-1].equals("T")) & !(helpingmatrix[a][b-1].equals("E")))
                        byTheStation = true;

                    // Szomszedok beallitasa
                    field[a][b].setLeft(field[a][b-1]);
                    field[a][b].setRight(null);
                    field[a][b].setUp(field[a-1][b]);
                    field[a][b].setDown(null);
                }

                // Beallitom a megfelelo tipusu mezok adatait, melyek vonatkoznak mas mezore is vagy validaciohoz kotottek,
                // igy elso korben nem lehetett beallitani, kulonben null erkekkel szerepelne.
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
        // Beallitom az valtozok ertekeit, hogy mas fuggvenyek is el tudjak erni
        // es a mepcreator ujboli megnyitasara ne kapjunk szemetet ezen valtozok ertekeikent
        Fields = field;
        realWidth = width;
        realHeight = height;
        map = helpingmatrix;

        // Visszaadom a startmezot
        return (Rail)field[0][0];
    }
    /*
     * Egy ID alapjan megmondja, hogy mi van abban a mezoben
     * Ha az ID nagyobb, mint a palyan levo mezok szama, akkor null-t ad vissza
     * Az ID a mezo szamanak felel meg a bal elso sarokbol indulva
     * soronkent haladva es novelve egy 0 kezdo ertekĹ± valtozot
     *
     * @param ID mezo azonositasara hasznalt valtozo.
     * @return Parameterhez tartozo mezo visszaadasa
    */
    public Field searchField(int ID)
    {
        // Segedvaltozok letrehozasa
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
     * Megmondja, hogy a parameterkent kapott mezonek mi az ID-ja
     * Az ID a fent meghatarozott modon a mezo szamat jeloli, amelyben a field van.
     *
     * @param field Lekerdezni kivant mezo
     * @return Lekerdezett mezo ID-jenek visszaadasa
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
        // Itt taroljuk a ket megepitett Tunnel-t.
        Tunnel[] selectedTunnels = new Tunnel[2];

        // A tombbeli helye a megepitett Tunnel-nek.
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