package map;

import graphics.Drawer;

public class StartRail extends Rail{

    //Egyedul o kezeli le, hogy a kapott Rail null
    @Override
    public Rail getDirection(Rail r) {
        if (r == null)
            return this.getPossibleRail1();
        else if (this.getPossibleRail1() == r)
            return this.getPossibleRail2();
        else
            return this.getPossibleRail1();
    }

    @Override
    public void draw(Drawer drawer) {
        drawer.drawStartRail(this);
    }
}
