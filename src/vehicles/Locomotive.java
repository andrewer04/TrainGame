package vehicles;

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
        this.currentRail = rail;
        this.color = Color.BLACK;
        this.isEmpty = true;
        this.trainLength = trainLength;
    }

    @Override
    public void leave(){
        this.next.leave();
    }

    @Override
    public void getOn(){
        this.next.getOn();
    }

    @Override
    public void move() {
        super.move();
    }
}
