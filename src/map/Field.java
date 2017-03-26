package map;

import utility.Color;

public abstract class Field {
    private Field left;
    private Field right;
    private Field up;
    private Field down;


    public Field getLeft() {
        return left;
    }

    public Field getRight() {
        return right;
    }

    public Field getUp() {
        return up;
    }

    public Field getDown() { return down;}

    public void setLeft(Field left) {
        this.left = left;
    }

    public void setRight(Field right) {
        this.right = right;
    }

    public void setUp(Field up) {
        this.up = up;
    }

    public void setDown(Field down) {
        this.down = down;
    }

    //Azért kellett ezt itt létrehozni, hogy a leave() függvényt bármikor meg lehessen hívni, akkor is, ha csak sima
    //sínen vagyunk, akkor is, ha állomás mellett.
    abstract Color getColor();
}
