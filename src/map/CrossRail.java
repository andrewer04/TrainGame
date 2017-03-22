package map;

public class CrossRail extends Rail {
    private Rail possibleRail3;
    private Rail possibleRail4;

    //Az possibleRail1-el szemben a possibleRail2, a possibleRail3-al szemben pedig a possibleRail4
    @Override
    public Rail getDirection(Rail rail){
        if(rail == this.getPossibleRail1()) return this.getPossibleRail2();
        else if (rail == this.getPossibleRail2()) return this.getPossibleRail1();
        else if (rail == possibleRail3) return possibleRail4;
        else return possibleRail3;
    }
}
