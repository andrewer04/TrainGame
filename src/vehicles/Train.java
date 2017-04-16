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

    //konstruktorok használják
    public void setColor(Color color) {
        this.color = color;
    }
    /*
 * Beállítja az aktuális sínt, ahol a vonatelem van.
 *
 * @param currentRail sín tárolása
 */
    public void setCurrentRail(Rail currentRail) {
        this.currentRail = currentRail;
    }
    /*
     * Beállítja, hogy üres e a vagon vagy sem.
     */
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    /*
     * Beállítja, a következő vonatelemet a paraméterben megadottra.
     *
     * @param next vonatelem paramétere
     */
    public void setNext(Train next) {
        this.next = next;
    }

    /*
     * Beállítja, az előző vonatelemet a paraméterben megadottra.
     *
     * @param prev a vonatelem paramétere
     */
    public void setPrev(Train prev) {
        this.prev = prev;
    }
    public void setPrevRail(Rail prevRail) {
        this.prevRail = prevRail;
    }

    /*
     * Ütközés deketálás.
     * Ha egy mezőn 2, vagy több vonatelem van, ütközés történt.
     */
    public boolean detectCollision(){
        if(currentRail != null && currentRail.getAvailability() >= 2) return true;
        else if (next != null && next.detectCollision()) return true;
        else return false;
    }
    /*
     * Vonat kiürülésének vizsgálata
     */
    public boolean detectEmptiness(){
        if(next == null && isEmpty == true) return true;
        else if(next == null && isEmpty == false) return false;

        else if (next.detectEmptiness() && isEmpty == true) return true;
        else return false;
    }

    /*
     * Vonatelem mozgatása
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

            //az elérhetőséget beállítjuk
            prevRail.setAvailability(false);
            currentRail.setAvailability(true);
        }

        if(next != null) next.move();
    }

    public abstract void leave();
    public abstract void getOn();
}
