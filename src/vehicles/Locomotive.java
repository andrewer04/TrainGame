package vehicles;

import map.Rail;
import utility.Color;

public class Locomotive extends Train {
    private int trainLength;

    public Locomotive(Rail rail, int trainLength){
        this.setCurrentRail(rail);
        this.setColor(Color.BLACK);
        this.setEmpty(true);
        this.trainLength = trainLength;
    }

    @Override
    public void leave(){
        this.getNext().leave();
    }
}
