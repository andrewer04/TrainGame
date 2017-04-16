package map;

import utility.Color;

public class Rail extends Field{
    private boolean byTheStation;
    private int availability = 0;
    private Rail possibleRail1;
    private Rail possibleRail2;


    /*
     *  A paraméterként megadott sín a vonatelem előző helyét jelenti.
     *  Megnézzük, hogy az aktuális sínről hová lehet tovább menni.
     *  A két lehetséges eset közül azt adjuk vissza,
     *  amelyik nem egyezik meg a vonatelem előző pozíciójával.
     *
     *  @param r Előző hely tárolása
     *  @return Lehetséges haladási irány visszaadása
     */
    public Rail getDirection(Rail r){
        if (possibleRail1 == r)
            return possibleRail2;
        else
            return possibleRail1;
    }

    public boolean isItStation() {
        return byTheStation;
    }
    public int getAvailability() {
        return availability;
    }
    /*
     * Visszaadja az első lehetséges továbbhaladási irányt
     */
    public Rail getPossibleRail1() {
        return possibleRail1;
    }

    /*
     * Visszaadja a második lehetséges továbbhaladási irányt.
     */
    public Rail getPossibleRail2() {
        return possibleRail2;
    }

    /*
     * Beállítja, hogy a sín állomás mellett áll-e vagy sem.
     *
     * @param byTheStation ebben tároljuk, hogy a sín mellett van e az állomás.
     */
    public void setByTheStation(boolean byTheStation) {
        this.byTheStation = byTheStation;
    }
    /*
     * Beállítjuk az első lehetséges továbbhaladási irányt.
     *
     * @param possibleRail1 az erre használt változó
     */
    public void setPossibleRail1(Rail possibleRail1) {
        this.possibleRail1 = possibleRail1;
    }


    /*
     * Beállítjuk a második lehetséges továbbhaladási irányt.
     *
     * @param possibleRail2 az erre használt változó
     */
    public void setPossibleRail2(Rail possibleRail2) {
        this.possibleRail2 = possibleRail2;
    }

    /*
     * Sín foglaltságát állítja be
     *
     * @param onTheRail igaz, ha a mezőn van valami
     */
    public void setAvailability(boolean onTheRail) {
        if (onTheRail) availability++;
        else availability--;
    }

    //A mellette lévő állomás színét adja, ha van ilyen. Egyébként null-t.
    @Override
    public Color getColor(){
        if(byTheStation) {
            if (this.getUp() != null && this.getUp().getColor() != null) return this.getUp().getColor();
            else if (this.getDown() != null && this.getDown().getColor() != null) return this.getDown().getColor();
            else if (this.getRight() != null && this.getRight().getColor() != null) return this.getRight().getColor();
            else if (this.getLeft() != null && this.getLeft().getColor() != null) return this.getLeft().getColor();
        }
        return null;
    }


    /*
     * Visszaadja, ha állomás mellett helyezkedik el a sín, hogy az állomás üres-e.
     * Ha a mező nem állomás mellett van, akkor visszatérési értéke true.
     *
     * @return állomás ürrességének tárolása
     */
    @Override
    public boolean isEmpty(){
        if (byTheStation){
            if (this.getUp() != null && this.getUp().isEmpty() == false) return false;
            else if (this.getDown() != null && this.getDown().isEmpty() == false) return false;
            else if (this.getRight() != null && this.getRight().isEmpty() == false) return false;
            else if (this.getLeft() != null && this.getLeft().isEmpty() == false) return false;
        }
        return true;
    }
}
