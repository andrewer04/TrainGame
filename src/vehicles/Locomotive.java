package vehicles;

import map.Rail;
import utility.Color;

public class Locomotive extends Train {
    private Rail prevRail;
    private Wagon firstWagon;
    private int trainLength;

    public Locomotive(Rail rail, int trainLength){
        this.setCurrentRail(rail);
        this.setColor(Color.BLACK);
        this.setEmpty(true);
        this.trainLength = trainLength;
    }
}
