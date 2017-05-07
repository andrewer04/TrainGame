package map;

import graphics.Drawer;

import java.awt.Color;

public class EmptyField extends Field {
    @Override
    public Color getColor(){
        return null;
    }

    @Override
    public boolean isEmpty(){
        return true;
    }

    @Override
    public void draw(Drawer drawer) {
        drawer.drawEmptyField(this);
    }
}
