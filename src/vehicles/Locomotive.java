package vehicles;

import map.Rail;
import utility.Color;

public class Locomotive extends Train {
    private Rail prevRail;
    private Wagon firstWagon;
    private int trainLength;

    public Locomotive(int trainLength){
        this.setColor(Color.BLACK);
        this.setEmpty(true);
        this.trainLength = trainLength;
    }

    //Itt annyi történik, hogy elmentjük ideiglenesen a jelenlegi sínt, majd azt beállítjuk arra, amit a ő visszaad
    //mint következő sín. Ezután az előző sínbe bemásoljuk a már csak volt jelenlegit.
    public void move(){
        Rail temp;
        temp = this.getCurrentRail();
        this.setCurrentRail(this.getCurrentRail().getDirection(prevRail));
        prevRail = temp;

        //az elérhetőséget beállítjuk
        prevRail.setAvailability(false);
        this.getCurrentRail().setAvailability(true);

        firstWagon.follow(prevRail);
    }
}
