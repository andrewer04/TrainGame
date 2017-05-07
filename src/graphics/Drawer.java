package graphics;

import application.MapCreator;
import map.*;
import vehicles.*;

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
        g.fillRect(MapCreator.getFieldCoordX(train.getCurrentRail()),MapCreator.getFieldCoordY(train.getCurrentRail()),WIDTH,HEIGHT);
    }

    public void drawEmptyField(EmptyField emptyField){
        g.setColor(Color.GREEN);
        g.fillRect(MapCreator.getFieldCoordX(emptyField),MapCreator.getFieldCoordY(emptyField),WIDTH,HEIGHT);
    }
    public void drawRail(Rail rail){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(MapCreator.getFieldCoordX(rail),MapCreator.getFieldCoordY(rail),WIDTH,HEIGHT);
    }
    public void drawCrossRail(CrossRail crossRail){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(MapCreator.getFieldCoordX(crossRail),MapCreator.getFieldCoordY(crossRail),WIDTH,HEIGHT);
    }
    public void drawRailStation(RailStation railStation){
        g.setColor(railStation.getColor());
        g.fillRect(MapCreator.getFieldCoordX(railStation),MapCreator.getFieldCoordY(railStation),WIDTH,HEIGHT);
    }
    public void drawStartRail(StartRail startRail){
        g.setColor(Color.GRAY);
        g.fillRect(MapCreator.getFieldCoordX(startRail),MapCreator.getFieldCoordY(startRail),WIDTH,HEIGHT);
    }
    public void drawSwitch(Switch sw){
        g.setColor(Color.MAGENTA);
        g.fillRect(MapCreator.getFieldCoordX(sw),MapCreator.getFieldCoordY(sw),WIDTH,HEIGHT);
    }
    public void drawTunnel(Tunnel tunnel) {
        g.setColor(Color.MAGENTA);
        g.fillRect(MapCreator.getFieldCoordX(tunnel),MapCreator.getFieldCoordY(tunnel),WIDTH,HEIGHT);
    }
}
