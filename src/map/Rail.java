package map;

public class Rail extends Field{
    private boolean byTheStation;
    private int availability = 0;
    private Rail possibleRail1;
    private Rail possibleRail2;

    public Rail() {
        this.setType("Rail");
    }

    public Rail getDirection(Rail r){
        if (possibleRail1 == r)
            return possibleRail2;
        else
            return possibleRail1;
    }

    public boolean isItStation() {
        return byTheStation;
    }
    public int getAvailability() {
        return availability;
    }

    public Rail getPossibleRail1() {
        return possibleRail1;
    }
    public Rail getPossibleRail2() {
        return possibleRail2;
    }

    public void setByTheStation(boolean byTheStation) {
        this.byTheStation = byTheStation;
    }

    public void setPossibleRail1(Rail possibleRail1) {
        this.possibleRail1 = possibleRail1;
    }
    public void setPossibleRail2(Rail possibleRail2) {
        this.possibleRail2 = possibleRail2;
    }

    public void setAvailability(boolean onTheRail) {
        if (onTheRail) availability++;
        else availability--;
    }
}
