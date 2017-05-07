package vehicles;

import graphics.Drawer;
import map.Rail;

import java.awt.*;

public class Locomotive extends Train {
    private int trainLength;
    /**
     * Az osztaly konstruktora, beallitja az aktualis helyet a
     * parameterkent kapott sinnel, a vonat hosszat pedig a parameterkent
     * kapott szammal, a szinet feketere, telitettseget pedig uresre allitja.
     *
     * @param rail hely tárolására használt változó
     * @param trainLength vonat hossza
     */
    public Locomotive(Rail rail, int trainLength){
        this.setCurrentRail(rail);
        this.setColor(Color.BLACK);
        this.setEmpty(true);
        this.trainLength = trainLength;
    }

    public int getTrainLength() {
        return trainLength;
    }

    @Override
    public void leave(){
        this.getNext().leave();
    }

    @Override
    public void getOn(){
        this.getNext().getOn();
    }

}
