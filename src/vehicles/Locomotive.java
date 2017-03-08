package vehicles;

import map.Rail;

public class Locomotive extends Wagon {
    private String colour = "black";
    private boolean isEmpty = true;
    private Train myTrain;

    public String getColour() {
        return colour;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Train getMyTrain() {
        return myTrain;
    }

    public void move(){

    }
    public void checkCollision(){

    }
}
