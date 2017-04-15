package map;

public class StartRail extends Rail{

    public StartRail(boolean byThestation, Rail possibleRail1, Rail possibleRail2)
    {
        this.setByTheStation(byThestation);
        this.setPossibleRail1(possibleRail1);
        this.setPossibleRail2(possibleRail2);
    }

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
