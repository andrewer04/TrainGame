package graphics;

public interface Drawable {

    /**
     * Ez a metodust ugy kell feluldefinialni, hogy minden osztaly
     * a sajat magara vonatkozo kirajzolo metodust hivja meg a parameterben
     * atadott drawer osztalybol.
     * @param drawer a kirajzolo osztaly
     */
    void draw(Drawer drawer);
}
