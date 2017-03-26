package application;

import map.Rail;
import map.StartRail;

public class MapCreator {

    public Rail build(int level){
        System.out.println(" build() - letrehozza a(z) " + level + ". palyat, mezoit és azok osszefuggeseit");

        //egyáltalán nem végleges, csak hogy legyen valami
        StartRail start = new StartRail();

        return start;
    }
}
