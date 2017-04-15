package map;

import utility.Color;

public class Rail extends Field{
    private boolean byTheStation;
    private int availability = 0;
    private Rail possibleRail1;
    private Rail possibleRail2;

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

    //A mellette lévő állomás színét adja, ha van ilyen. Egyébként null-t.
    @Override
    public Color getColor(){
        if(byTheStation) {
            if (this.getUp() != null && this.getUp().getColor() != null) return this.getUp().getColor();
            else if (this.getDown() != null && this.getDown().getColor() != null) return this.getDown().getColor();
            else if (this.getRight() != null && this.getRight().getColor() != null) return this.getRight().getColor();
            else if (this.getLeft() != null && this.getLeft().getColor() != null) return this.getLeft().getColor();
        }
        return null;
    }

    @Override
    public boolean isEmpty(){
        if (byTheStation){
            if (this.getUp() != null && this.getUp().isEmpty() == false) return false;
            else if (this.getDown() != null && this.getDown().isEmpty() == false) return false;
            else if (this.getRight() != null && this.getRight().isEmpty() == false) return false;
            else if (this.getLeft() != null && this.getLeft().isEmpty() == false) return false;
        }
        return true;
    }
}
