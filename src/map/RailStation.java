package map;

import graphics.Drawer;

import java.awt.Color;

public class RailStation extends Field{
    private Color color;
    private boolean isEmpty;

    //Konstruktorral letrehozaskor szinnel eggyutt hozzuk letre az allomast.
    public RailStation(Color color){
        this.color = color;
        isEmpty = false;
    }
    //Az allomas szinet adja vissza.
    @Override
    public Color getColor() {
        return color;
    }

    //Hamar eljutnak odaig a lekerdezes, hogy az allomas allapotara kivancsiak, akkor biztos hogy fel is szallnak ra
    //amennyiben meg nem ures az allomas. Ezert lehet a lekerdezes utan egybol uresse tenni.
    @Override
    public boolean isEmpty(){
        if(isEmpty)
            return isEmpty;
        else {
            isEmpty = true;
            return false;
        }
    }
    
    public boolean isEmptyy(){
        if(isEmpty)
            return isEmpty;
        else {
        	
            return false;
        }
    }

    @Override
    public void draw(Drawer drawer) {
        drawer.drawRailStation(this);
    }
}
