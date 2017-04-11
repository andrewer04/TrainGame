package map;

import utility.Color;

public class RailStation extends Field{
    private Color color;
    boolean isEmpty;

    //Konstruktórral létrehozáskor színnel eggyütt hozzuk létre az állomást.
    public RailStation(Color color){
        this.color = color;
        isEmpty = false;
    }

    //Az állomás színét adja vissza.
    @Override
    public Color getColor() {
        return color;
    }

    //Hamár eljutnak odáig a lekérdezés, hogy az állomás állapotára kiváncsiak, akkor biztos hogy fel is szállnak rá
    //amennyiben még nem üres az állomás. Ezért lehet a lekérdezés után egyből üressé tenni.
    @Override
    public boolean isEmpty(){
        if(isEmpty)
            return isEmpty;
        else {
            isEmpty = true;
            return false;
        }
    }
}
