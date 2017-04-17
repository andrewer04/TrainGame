package map;

import utility.Color;

public class Rail extends Field{
    private boolean byTheStation;
    private int availability = 0;
    private Rail possibleRail1;
    private Rail possibleRail2;


    /*
     *  A parameterkent megadott sin a vonatelem elozo helyet jelenti.
     *  Megnezzuk, hogy az aktualis sinrol hova lehet tovabb menni.
     *  A ket lehetseges eset kozul azt adjuk vissza,
     *  amelyik nem egyezik meg a vonatelem elozo poziciojaval.
     *
     *  @param r Elozo hely tarolasa
     *  @return Lehetseges haladasi irány visszaadasa
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
     * Visszaadja az elso lehetseges tovabbhaladasi iranyt
     */
    public Rail getPossibleRail1() {
        return possibleRail1;
    }

    /*
     * Visszaadja a masodik lehetseges tovabbhaladasi iranyt.
     */
    public Rail getPossibleRail2() {
        return possibleRail2;
    }

    /*
     * Beallitja, hogy a sin allomas mellett all-e vagy sem.
     *
     * @param byTheStation ebben taroljuk, hogy a sin mellett van e az allomas.
     */
    public void setByTheStation(boolean byTheStation) {
        this.byTheStation = byTheStation;
    }
    /*
     * Beallitjuk az elso lehetseges tovabbhaladasi iranyt.
     *
     * @param possibleRail1 az erre hasznalt valtozo
     */
    public void setPossibleRail1(Rail possibleRail1) {
        this.possibleRail1 = possibleRail1;
    }


    /*
     * Beallitjuk a masodik lehetseges tovabbhaladasi iranyt.
     *
     * @param possibleRail2 az erre hasznalt valtozo
     */
    public void setPossibleRail2(Rail possibleRail2) {
        this.possibleRail2 = possibleRail2;
    }

    /*
     * Sin foglaltsagat allitja be
     *
     * @param onTheRail igaz, ha a mezon van valami
     */
    public void setAvailability(boolean onTheRail) {
        if (onTheRail) availability++;
        else availability--;
    }

    //A mellette levo allomas szinet adja, ha van ilyen. Egyebkent null-t.
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
     * Visszaadja, ha allomas mellett helyezkedik el a sin, hogy az allomas ures-e.
     * Ha a mezo nem allomas mellett van, akkor visszateresi erteke true.
     *
     * @return allomas urressegenek tarolasa
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
