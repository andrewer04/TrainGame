package vehicles;

import map.Rail;
import utility.Color;

public class Wagon extends Train {
    private Train next;
    private Train prev;

    public void leave(){
        if (prev.isEmpty() && this.getColor() == this.getCurrentRail().getColor()){
            this.setEmpty(true);
            if (this.isItTheLast()){
                //ide kéne kitalálni valamit, hogy hogy legyen a győzelem
            }
        }
    }

    //Ez eredetileg a follow függvény volt, de hogy OOP legyen, az abstract class-ban lévő függvényt
    //overrideoljuk itt
    @Override
    public void move(Rail prevTrainRail){
        Rail temp;
        temp = this.getCurrentRail();
        this.setCurrentRail(prevTrainRail);

        this.getCurrentRail().setAvailability(true);
        temp.setAvailability(false);

        if (!isItTheLast()) {
            next.move(temp);
        }
    }

    private boolean isItTheLast(){
        if(next == null) return true;
        else return false;
    }
}
