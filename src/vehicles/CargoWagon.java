package vehicles;

import map.Rail;

import java.awt.Color;

public class CargoWagon extends Wagon {

    public CargoWagon(Rail rail){
        super(rail,Color.DARK_GRAY);
        this.isEmpty = true;
    }
}
