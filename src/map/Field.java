package map;

import utility.Color;

public abstract class Field {
    private Field left;
    private Field right;
    private Field up;
    private Field down;

    /*
     * Mezőtől balra lévő mező viszaadása
     * @return balra lévő Field
     */
    public Field getLeft() {
        return left;
    }
    /*
     * Mezőtől jobbra lévő mező viszaadása
     * @return jobbra lévő Field
     */
    public Field getRight() {
        return right;
    }
    /*
     * Mezőtől felfele lévő mező viszaadása
     * @return fentebbi Field
     */
    public Field getUp() {
        return up;
    }

    /*
     * Mezőtől lefele lévő mező viszaadása
     * @return lentebbi Field
     */
    public Field getDown() { return down;}

 /*
  * Mező pozicionálása
  * @param left balra lévő mező tárolása
  */

    public void setLeft(Field left) {
        this.left = left;
    }
    /*
     * Mező pozicionálása
     * @param right jobbra lévő mező tárolása
     */
    public void setRight(Field right) {
        this.right = right;
    }

    /*
     * Mező pozicionálása
     * @param left fölötte lévő mező tárolása
     */
    public void setUp(Field up) {
        this.up = up;
    }
    /*
     * Mező pozicionálása
     * @param left alatta lévő mező tárolása
     */
    public void setDown(Field down) {
        this.down = down;
    }

    //AzĂ©rt kellett ezt itt lĂ©trehozni, hogy a leave() fĂĽggvĂ©nyt bĂˇrmikor meg lehessen hĂ­vni, akkor is, ha csak sima
    //sĂ­nen vagyunk, akkor is, ha ĂˇllomĂˇs mellett.
    abstract Color getColor();
    abstract boolean isEmpty();
}
