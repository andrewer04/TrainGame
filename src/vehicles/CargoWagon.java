package vehicles;

import map.Rail;
import utility.Color;

public class CargoWagon extends Wagon {

    public CargoWagon(Rail rail, Color color){
        super(rail,Color.GREY);
        this.setEmpty(true);
    }
}
