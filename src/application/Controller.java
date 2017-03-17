package application;

import map.Rail;

public class Controller {
    private int level;
    private Rail start;

    public void lose(){
        System.out.println("lose");
    }
    public void win(){
        System.out.println("win");
    }
    public void makeTrain(){

    }
    public void observer(){

    }
    public void startStepping(){

    }
    public void exit(){

    }

    //a Controller lose() metódusát nem tudjuk meghívni a vonatokból, mert ahhoz el kéne tárolni
    //az egyetlen Controller objektumot, amit majd a main-ben hozunk létre
    //mellesleg, ha a léptetés után hívjuk meg a checkcollision függvényt, akkor elkerüljük azt, hogy a léptetés
    //miatt fals ütküzés legyen
    public void checkCollision(Rail start){}
}
