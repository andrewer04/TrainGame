package map;

import utility.Color;

public class RailStation extends Field{
    private Color color;

    //Konstruktórral létrehozáskor színnel eggyütt hozzuk létre az állomást.
    public RailStation(Color color){
        this.color = color;
    }

    //Az állomás színét adja vissza.
    @Override
    public Color getColor() {
        return color;
    }
}
