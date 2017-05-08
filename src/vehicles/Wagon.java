package vehicles;

import map.Rail;

import java.awt.*;

public class Wagon extends Train {

    public Wagon(Rail rail, Color color){
        this.currentRail = rail;
        this.color = color;
        this.isEmpty = false;
    }
    /**
     * Megvizsgalja, hogy leszallhat e a vagonrol az utas.
     * Amennyiben igen, ugy leszallitja oket.
     */
    @Override
    public void leave(){
        if (this.prev.isEmpty && this.currentRail != null && this.color == this.currentRail.getColor()){
            this.isEmpty = true;
        }
        if (this.next != null) this.next.leave();
    }
    /**
     * Az utasok felszallasasert felelo fuggveny
     */
    @Override
    public void getOn(){
        if (this.currentRail != null && this.color == this.currentRail.getColor() && this.isEmpty && this.currentRail.isEmpty() == false){
            this.isEmpty = false;
        }
        if(this.next != null) this.next.getOn();
    }

    @Override
    public void move() {

        Rail temp;
        temp = currentRail;
        currentRail =  prev.prevRail;
        prevRail = temp;

        if (currentRail != null) currentRail.setAvailability(true);
        if (prevRail != null) prevRail.setAvailability(false);
        if(next != null) next.move();

    }
}
