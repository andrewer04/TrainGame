package map;

public class StartRail extends Rail{


    /*
     * Beallitja a lehetseges utvonalat az adott mezobol es azt, hogy allomas mellett all-e.
     *
     * @param byThestation allomas mezo van-e a sin mellett
     * @param possibleRail1 tovabbhaladasiirany1 tarolasa
     * @param possibleRail2 tovabbhaladasiirany2 tarolasa
     */
    public StartRail(boolean byThestation, Rail possibleRail1, Rail possibleRail2)
    {
        this.setByTheStation(byThestation);
        this.setPossibleRail1(possibleRail1);
        this.setPossibleRail2(possibleRail2);
    }

    @Override
/*
 * Irany visszaadasa
 *
 * @param r Vonatelem elozo helye
 * @return Tovabbhaladasi irany visszaadasa
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
