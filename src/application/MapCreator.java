package application;

import map.StartRail;

public class MapCreator {

    public Controller build(int level){
        System.out.println(" build() - letrehozza a(z) " + level + ". palyat, mezoit és azok osszefuggeseit");

        //egyáltalán nem végleges, csak hogy legyen valami
        StartRail start = new StartRail();

        Controller cont = new Controller(level,start);

        return cont;
    }
}
