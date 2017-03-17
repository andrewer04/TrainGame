package vehicles;

import map.Rail;
import utility.Color;

public class Wagon extends Train {
    private Wagon next;

    //azért kell Train-nek lennie, mert ha wagon lenne, akkor az első wagon nem tudna hivatkozni az előtte lévő locomotive-ra
    private Train prev;

    public void leave(Color stationColor){
        if (prev.isEmpty() && this.getColor() == stationColor){
            this.setEmpty(true);
            if (this.isItTheLast()){
                //ide kéne kitalálni valamit, hogy hogy legyen a győzelem
            }
        }
    }

    public void follow(Rail prevTrainRail){
        Rail temp;
        temp = this.getCurrentRail();
        this.setCurrentRail(prevTrainRail);

        this.getCurrentRail().setAvailability(true);
        temp.setAvailability(false);

        if (!isItTheLast()) {
            next.follow(temp);
        }
    }

    private boolean isItTheLast(){
        if(next == null) return true;
        else return false;
    }
}
