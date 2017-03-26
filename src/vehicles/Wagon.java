package vehicles;

import map.Rail;
import utility.Color;

public class Wagon extends Train {

    public Wagon(Rail rail, Color color){
        this.setCurrentRail(rail);
        this.setColor(color);
        this.setEmpty(false);
    }

    public void leave(){
        if (this.getPrev().isEmpty() && this.getColor() == this.getCurrentRail().getColor()){
            this.setEmpty(true);
            if (this.getNext() == null){
                //ide kéne kitalálni valamit, hogy hogy legyen a győzelem
            }
        }
    }

    public void getOn(){}
}
