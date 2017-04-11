package map;

import utility.Color;

public class EmptyField extends Field {
    @Override
    public Color getColor(){
        return null;
    }

    @Override
    public boolean isEmpty(){
        return true;
    }
}
