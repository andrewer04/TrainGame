package map;

public class StartRail extends Rail{

    public StartRail() {
        this.setType("StartRail");
    }

    //ezt azért definiáltam felül, mert ugye csak egy lehetséges helyre távozhat innen
    //ha majd a mapcreator úgy hozza létre fixen, hogy csak a possibleRail1 lesz az út fixen és a másik null,
    //akkor majd át lehet írni ezt a függvényt is
    @Override
    public Rail getDirection(Rail r) {
        if (this.getPossibleRail1() == null)
            return this.getPossibleRail2();
        else
            return this.getPossibleRail1();
    }

}
