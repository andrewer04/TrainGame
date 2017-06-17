package vehicles;

import graphics.Drawer;
import map.Rail;
import graphics.Drawable;

import java.awt.Color;
import java.io.Serializable;

public abstract class Train implements Drawable, Serializable{
    protected Color color;
    protected Rail currentRail;
    protected Rail prevRail;
    protected boolean isEmpty;
    protected Train next;
    protected Train prev;

    public Color getColor() {
        return color;
    }
    public Rail getCurrentRail() {
        return currentRail;
    }
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Beallitja, a kovetkezo vonatelemet a parameterben megadottra.
     *
     * @param next vonatelem parametere
     */
    public void setNext(Train next) {
        this.next = next;
    }

    /**
     * Beallitja, az elozo vonatelemet a parameterben megadottra.
     *
     * @param prev a vonatelem parametere
     */
    public void setPrev(Train prev) {
        this.prev = prev;
    }
    public void setPrevRail(Rail prevRail) {
        this.prevRail = prevRail;
    }

    /**
     * utkozes deketalas.
     * Ha egy mezon 2, vagy tobb vonatelem van, utkozes tortent.
     */
    public boolean detectCollision(){
        if(currentRail != null && currentRail.getAvailability() >= 2) return true;
        else if (next != null && next.detectCollision()) return true;
        else return false;
    }
    /**
     * Vonat kiurulesenek vizsgalata
     */
    public boolean detectEmptiness(){
        if(next == null && isEmpty == true) return true;
        else if(next == null && isEmpty == false) return false;

        else if (next.detectEmptiness() && isEmpty == true) return true;
        else return false;
    }

    /**
     * Vonatelem mozgatasa
     */
    public void move(){

        if (currentRail == null){
            currentRail = prev.prevRail;
            if (currentRail != null) currentRail.setAvailability(true);
        }
        else {

            Rail temp;
            temp = currentRail;
            currentRail =  currentRail.getDirection(prevRail);
            prevRail = temp;

            //az elerhetoseget beallitjuk
            prevRail.setAvailability(false);
            currentRail.setAvailability(true);
        }

        if(next != null) next.move();
    }

    public abstract void leave();
    public abstract void getOn();

    @Override
    public void draw(Drawer drawer) {
        drawer.drawTrain(this);
    }
}
