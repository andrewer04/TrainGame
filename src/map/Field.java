package map;

import graphics.Drawable;

import java.awt.Color;
import java.io.Serializable;

public abstract class Field implements Drawable, Serializable {
    private Field left;
    private Field right;
    private Field up;
    private Field down;

    /**
     * Mezotol balra levo mezo viszaadasa
     * @return balra levo Field
     */
    public Field getLeft() {
        return left;
    }
    /**
     * Mezotol jobbra levo mezo viszaadasa
     * @return jobbra levo Field
     */
    public Field getRight() {
        return right;
    }
    /**
     * Mezotol felfele levo mezo viszaadasa
     * @return fentebbi Field
     */
    public Field getUp() {
        return up;
    }

    /**
     * Mezotol lefele levo mezo viszaadasa
     * @return lentebbi Field
     */
    public Field getDown() { return down;}

 /**
  * Mezo pozicionalasa
  * @param left balra levo mezo tarolasa
  */

    public void setLeft(Field left) {
        this.left = left;
    }
    /**
     * Mezo pozicionalasa
     * @param right jobbra levo mezo tarolasa
     */
    public void setRight(Field right) {
        this.right = right;
    }

    /**
     * Mezo pozicionalasa
     * @param up folotte levo mezo tarolasa
     */
    public void setUp(Field up) {
        this.up = up;
    }
    /**
     * Mezo pozicionalasalása
     * @param down alatta levo mezo tarolasa
     */
    public void setDown(Field down) {
        this.down = down;
    }

    //Azert kellett ezt itt lerehozni, hogy a leave() fuggvenyt barmikor meg lehessen hivni, akkor is, ha csak sima
    //sinen vagyunk, akkor is, ha allomas mellett.
    abstract Color getColor();
    abstract boolean isEmpty();
}
