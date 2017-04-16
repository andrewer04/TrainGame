package vehicles;

import map.Rail;
import utility.Color;

public class Wagon extends Train {

    public Wagon(Rail rail, Color color){
        this.setCurrentRail(rail);
        this.setColor(color);
        this.setEmpty(false);
    }
    /*
     * Megvizsgálja, hogy leszállhat e a vagonról az utas.
     * Amennyiben igen, úgy leszállítja őket.
     */
    @Override
    public void leave(){
        if (this.getPrev().isEmpty() && this.getCurrentRail() != null && this.getColor() == this.getCurrentRail().getColor()){
            this.setEmpty(true);
        }
        if (this.getNext() != null) this.getNext().leave();
    }
    /*
     * Az utasok felszállásáért felelő függvény
     */
    @Override
    public void getOn(){
        if (this.getCurrentRail() != null && this.getColor() == this.getCurrentRail().getColor() && this.isEmpty() && this.getCurrentRail().isEmpty() == false){
            this.setEmpty(false);
        }
        if(this.getNext() != null) this.getNext().getOn();
    }
}
