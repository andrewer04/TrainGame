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

    //Azért kell argumentum, mert a Wagon-ban is ez a függvény van overrideolva, és muszáj, hogy legyen (majd adunk neki
    //egy null-t.
    @Override
    public void move(Rail doesntMatter){
        Rail temp;
        temp = this.getCurrentRail();
        this.setCurrentRail(this.getCurrentRail().getDirection(prevRail));
        prevRail = temp;

        //az elérhetőséget beállítjuk
        prevRail.setAvailability(false);
        this.getCurrentRail().setAvailability(true);

        firstWagon.move(prevRail);
    }
}
