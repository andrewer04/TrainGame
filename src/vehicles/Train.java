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
    public Rail getPrevRail() {
        return prevRail;
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

    //konstruktorok hasznaljak
    public void setColor(Color color) {
        this.color = color;
    }
    /*
 * Beallitja az aktualis sint, ahol a vonatelem van.
 *
 * @param currentRail sin tarolasa
 */
    public void setCurrentRail(Rail currentRail) {
        this.currentRail = currentRail;
    }
    /*
     * Beallitja, hogy ures e a vagon vagy sem.
     */
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    /*
     * Beallitja, a kovetkezo vonatelemet a parameterben megadottra.
     *
     * @param next vonatelem parametere
     */
    public void setNext(Train next) {
        this.next = next;
    }

    /*
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

    /*
     * utkozes deketalas.
     * Ha egy mezon 2, vagy tobb vonatelem van, utkozes tortent.
     */
    public boolean detectCollision(){
        if(currentRail != null && currentRail.getAvailability() >= 2) return true;
        else if (next != null && next.detectCollision()) return true;
        else return false;
    }
    /*
     * Vonat kiurulesenek vizsgalata
     */
    public boolean detectEmptiness(){
        if(next == null && isEmpty == true) return true;
        else if(next == null && isEmpty == false) return false;

        else if (next.detectEmptiness() && isEmpty == true) return true;
        else return false;
    }

    /*
     * Vonatelem mozgatasa
     */
    public void move(){

        if (currentRail == null){
            currentRail = prev.getPrevRail();
            if (currentRail != null) currentRail.setAvailability(true);
        }
        else {

            Rail temp;
            temp = currentRail;
            setCurrentRail(currentRail.getDirection(prevRail));
            prevRail = temp;

            //az elerhetoseget beallitjuk
            prevRail.setAvailability(false);
            currentRail.setAvailability(true);
        }

        if(next != null) next.move();
    }

    public abstract void leave();
    public abstract void getOn();
}
