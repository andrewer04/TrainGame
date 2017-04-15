package vehicles;

import map.Rail;
import utility.Color;

public class CargoWagon extends Wagon {

    public CargoWagon(Rail rail){
        super(rail,Color.GREY);
        this.setEmpty(true);
    }
}
