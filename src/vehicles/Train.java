package vehicles;

import map.Rail;
import utility.Color;

public abstract class Train {
    private Color color;
    private Rail currentRail;
    private Rail prevRail;
    private boolean isEmpty;
    private Train next;
    private Train prev;

    public Color getColor() {
        return color;
    }
    public Rail getCurrentRail() {
        return currentRail;
    }
    public boolean isEmpty() {
        return isEmpty;
    }
    public Train getNext() {
        return next;
    }
    public Train getPrev() {
        return prev;
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
    public void setNext(Train next) {
        this.next = next;
    }
    public void setPrev(Train prev) {
        this.prev = prev;
    }
    public void setPrevRail(Rail prevRail) {
        this.prevRail = prevRail;
    }

    public boolean detectCollision(){
        if(currentRail.getAvailability() >= 2) return true;
        else if (next != null && next.detectCollision()) return true;
        else return false;
    }

    public boolean detectEmptiness(){
        if(next == null && isEmpty == true) return true;
        else if(next == null && isEmpty == false) return false;

        else if (next.detectEmptiness() && isEmpty == true) return true;
        else return false;
    }
    public void move(){
        Rail temp;
        temp = currentRail;
        setCurrentRail(currentRail.getDirection(prevRail));
        prevRail = temp;

        //az elérhetőséget beállítjuk
        prevRail.setAvailability(false);
        currentRail.setAvailability(true);

        if(next != null) next.move();
    }

    public abstract void leave();
}
