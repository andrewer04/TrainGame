package vehicles;

import map.Rail;
import utility.Color;

public abstract class Train {
    private Color color;
    private Rail currentRail;
    private boolean isEmpty;

    public Color getColor() {
        return color;
    }
    public Rail getCurrentRail() {
        return currentRail;
    }
    public boolean isEmpty() {
        return isEmpty;
    }

    //konstruktorok használják
    public void setColor(Color color) {
        this.color = color;
    }
    public void setCurrentRail(Rail currentRail) {
        this.currentRail = currentRail;
    }
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    abstract void move(Rail rail);

}
