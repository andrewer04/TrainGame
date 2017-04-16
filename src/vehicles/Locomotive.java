package vehicles;

import map.Rail;
import utility.Color;

public class Locomotive extends Train {
    private int trainLength;
    /*
     * Az osztály konstruktora, beállítja az aktuális helyét a
     * paraméterként kapott sínnel, a vonat hosszát pedig a paraméterként
     * kapott számmal, a színét feketére, telítettségét pedig üresre állítja.
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
