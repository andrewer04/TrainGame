package map;

public class StartRail extends Rail{


    /*
     * Beállítja a lehetséges útvonalat az adott mezőből és azt, hogy állomás mellett áll-e.
     *
     * @param byThestation állomás mező van-e a sín mellett
     * @param possibleRail1 továbbhaladásiirány1 tárolása
     * @param possibleRail2 továbbhaladásiirány2 tárolása
     */
    public StartRail(boolean byThestation, Rail possibleRail1, Rail possibleRail2)
    {
        this.setByTheStation(byThestation);
        this.setPossibleRail1(possibleRail1);
        this.setPossibleRail2(possibleRail2);
    }

    @Override
/*
 * Irány visszaadása
 *
 * @param r Vonatelem előző helye
 * @return Továbbhaladási irány visszaadása
 */
    public Rail getDirection(Rail r) {
        if (r == null)
            return this.getPossibleRail1();
        else if (this.getPossibleRail1() == r)
            return this.getPossibleRail2();
        else
            return this.getPossibleRail1();
    }
}
