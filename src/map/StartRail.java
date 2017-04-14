package map;

public class StartRail extends Rail{

    public StartRail(boolean byThestation, Rail possibleRail1, Rail possibleRail2)
    {
        this.setByTheStation(byThestation);
        this.setPossibleRail1(possibleRail1);
        this.setPossibleRail2(possibleRail2);
    }

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
