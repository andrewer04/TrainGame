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
            g.fillRect(MapCreator.getFieldCoordY(train.getCurrentRail())*HEIGHT,MapCreator.getFieldCoordX(train.getCurrentRail())*HEIGHT,WIDTH,HEIGHT);
    }

    public void drawEmptyField(EmptyField emptyField){
        g.setColor(Color.GREEN);
        g.fillRect(MapCreator.getFieldCoordY(emptyField)*HEIGHT,MapCreator.getFieldCoordX(emptyField)*WIDTH,WIDTH,HEIGHT);
    }
    public void drawRail(Rail rail){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(MapCreator.getFieldCoordY(rail)*HEIGHT,MapCreator.getFieldCoordX(rail)*WIDTH,WIDTH,HEIGHT);
    }
    public void drawCrossRail(CrossRail crossRail){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(MapCreator.getFieldCoordY(crossRail)*HEIGHT,MapCreator.getFieldCoordX(crossRail)*WIDTH,WIDTH,HEIGHT);
    }
    public void drawRailStation(RailStation railStation){
        g.setColor(railStation.getColor());
        g.fillRect(MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT);
    }
    public void drawStartRail(StartRail startRail){
        g.setColor(Color.GRAY);
        g.fillRect(MapCreator.getFieldCoordY(startRail)*HEIGHT,MapCreator.getFieldCoordX(startRail)*WIDTH,WIDTH,HEIGHT);
    }
    public void drawSwitch(Switch sw){
        g.setColor(Color.MAGENTA);
        g.fillRect(MapCreator.getFieldCoordY(sw)*HEIGHT,MapCreator.getFieldCoordX(sw)*WIDTH,WIDTH,HEIGHT);
    }
    public void drawTunnel(Tunnel tunnel) {
        g.setColor(Color.CYAN);
        g.fillRect(MapCreator.getFieldCoordY(tunnel)*HEIGHT,MapCreator.getFieldCoordX(tunnel)*WIDTH,WIDTH,HEIGHT);
    }
}
