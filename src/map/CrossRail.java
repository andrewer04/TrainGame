package map;

import graphics.Drawer;

public class CrossRail extends Rail {
    private Rail possibleRail3;
    private Rail possibleRail4;
    /**
     * Haladasi irany beallitasa
     * @param possibleRail3 harmadik lehetseges tovabbhaladasi irany tarolva van ebben
     */
    public void setPossibleRail3(Rail possibleRail3) {
        this.possibleRail3 = possibleRail3;
    }


    /**
    * Haladasi irany beallitasa
    * @param possibleRail4 negyedik lehetseges tovabbhaladasi irany tarolva van ebben
    */
    public void setPossibleRail4(Rail possibleRail4) {
        this.possibleRail4 = possibleRail4;
    }

    /**
     * Az possibleRail1-el szemben a possibleRail2, a possibleRail3-al szemben pedig a possibleRail4
     * @param rail az elozo vonatelem helyet taroljuk. Az ellenkezo iranyba kuldjuk tovabb.
     */
    @Override
    public Rail getDirection(Rail rail){
        if(rail == this.getPossibleRail1()) return this.getPossibleRail2();
        else if (rail == this.getPossibleRail2()) return this.getPossibleRail1();
        else if (rail == possibleRail3) return possibleRail4;
        else return possibleRail3;
    }

    @Override
    public void draw(Drawer drawer) {
        drawer.drawCrossRail(this);
    }
}
