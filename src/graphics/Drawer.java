package graphics;

import application.MapCreator;
import map.*;
import vehicles.Train;

import java.awt.*;

public class Drawer {

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    private static Graphics g = null;

    public void setGraphics(Graphics gr)
    {
        g = gr;
    }

    public void drawTrain(Train train){
        g.setColor(train.getColor());
        if(train.getCurrentRail() != null)
            g.fillRect(MapCreator.getFieldCoordX(train.getCurrentRail())*WIDTH,MapCreator.getFieldCoordY(train.getCurrentRail())*HEIGHT,WIDTH,HEIGHT);
    }

    public void drawEmptyField(EmptyField emptyField){
        g.setColor(Color.GREEN);
        g.fillRect(MapCreator.getFieldCoordX(emptyField)*WIDTH,MapCreator.getFieldCoordY(emptyField)*HEIGHT,WIDTH,HEIGHT);
    }
    public void drawRail(Rail rail){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(MapCreator.getFieldCoordX(rail)*WIDTH,MapCreator.getFieldCoordY(rail)*HEIGHT,WIDTH,HEIGHT);
    }
    public void drawCrossRail(CrossRail crossRail){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(MapCreator.getFieldCoordX(crossRail)*WIDTH,MapCreator.getFieldCoordY(crossRail)*HEIGHT,WIDTH,HEIGHT);
    }
    public void drawRailStation(RailStation railStation){
        g.setColor(railStation.getColor());
        g.fillRect(MapCreator.getFieldCoordX(railStation)*WIDTH,MapCreator.getFieldCoordY(railStation)*HEIGHT,WIDTH,HEIGHT);
    }
    public void drawStartRail(StartRail startRail){
        g.setColor(Color.GRAY);
        g.fillRect(MapCreator.getFieldCoordX(startRail)*WIDTH,MapCreator.getFieldCoordY(startRail)*HEIGHT,WIDTH,HEIGHT);
    }
    public void drawSwitch(Switch sw){
        g.setColor(Color.MAGENTA);
        g.fillRect(MapCreator.getFieldCoordX(sw)*WIDTH,MapCreator.getFieldCoordY(sw)*HEIGHT,WIDTH,HEIGHT);
    }
    public void drawTunnel(Tunnel tunnel) {
        g.setColor(Color.MAGENTA);
        g.fillRect(MapCreator.getFieldCoordX(tunnel)*WIDTH,MapCreator.getFieldCoordY(tunnel)*HEIGHT,WIDTH,HEIGHT);
    }
}
