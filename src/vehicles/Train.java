package vehicles;

import map.Rail;
import utility.Color;

public abstract class Train {
    private Color color;
    private Rail currentRail;
    private boolean isEmpty;

    //szerintem el kell tárolni, hogy melyik a next és a prev wagon, mert különben nem tudunk rá hivatkozni :\
    //hasonlóképpen mint a Rail-nél (bár ott bonyolultan, de meg lehetett oldani)


    public Color getColor() {
        return color;
    }
    public Rail getCurrentRail() {
        return currentRail;
    }
    public boolean isEmpty() {
        return isEmpty;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void setCurrentRail(Rail currentRail) {
        this.currentRail = currentRail;
    }
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

}
