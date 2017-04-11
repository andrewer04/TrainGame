package map;

public class StartRail extends Rail{

    //ezt azért definiáltam felül, mert ugye csak egy lehetséges helyre távozhat innen
    //ha majd a mapcreator úgy hozza létre fixen, hogy csak a possibleRail1 lesz az út fixen és a másik null,
    //akkor majd át lehet írni ezt a függvényt is
    @Override
    public Rail getDirection(Rail r) {
        if (r == null)
            return this.getPossibleRail1();
        else if (this.getPossibleRail1() == r)
            return this.getPossibleRail2();
        else
            return this.getPossibleRail1();
    }
}
