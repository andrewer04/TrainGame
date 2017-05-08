package graphics;

import application.MapCreator;
import map.*;
import vehicles.Train;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Drawer {

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    private static Graphics g = null;

	private BufferedImage ffield;
	private BufferedImage not_selected;
	private BufferedImage selected;
	private BufferedImage rrail;
	private BufferedImage green_e;
	private BufferedImage green_f;
	private BufferedImage blue_e;
	private BufferedImage blue_f;
	private BufferedImage red_e;
	private BufferedImage red_f;
	private BufferedImage orange_e;
	private BufferedImage orange_f;
	private BufferedImage orange_w;
	private BufferedImage blue_w;
	private BufferedImage red_w;
	private BufferedImage green_w;
	private BufferedImage sRL;
	private BufferedImage sRU;
	private BufferedImage sRD;
	private BufferedImage sLU;
	private BufferedImage sLD;
	private BufferedImage sDU;

	public Drawer(){
		try {
			ffield = ImageIO.read(new File("Resources/fu.jpg"));
			not_selected = ImageIO.read(new File("Resources/not_selected.jpg"));
			selected = ImageIO.read(new File("Resources/selected.jpg"));
			rrail = ImageIO.read(new File("Resources/sin.jpg"));
			green_e = ImageIO.read(new File("Resources/s_green_e.jpg"));
			green_f = ImageIO.read(new File("Resources/s_green.jpg"));
			red_e = ImageIO.read(new File("Resources/s_red_e.jpg"));
			red_f = ImageIO.read(new File("Resources/s_red.jpg"));
			orange_e = ImageIO.read(new File("Resources/s_orange_e.jpg"));
			orange_f = ImageIO.read(new File("Resources/s_orange.jpg"));
			blue_e = ImageIO.read(new File("Resources/s_blue_e.jpg"));
			blue_f = ImageIO.read(new File("Resources/s_blue.jpg"));
			blue_w = ImageIO.read(new File("Resources/w_blue.jpg"));
			red_w = ImageIO.read(new File("Resources/w_red.jpg"));
			orange_w = ImageIO.read(new File("Resources/w_orange.jpg"));
			green_w = ImageIO.read(new File("Resources/w_green.jpg"));
			sRL = ImageIO.read(new File("Resources/sw_jobb_bal.jpg"));
			sRU = ImageIO.read(new File("Resources/sw_jobb_fel.jpg"));
			sRD = ImageIO.read(new File("Resources/sw_jobb_le.jpg"));
			sLD = ImageIO.read(new File("Resources/sw_bal_le.jpg"));
			sLU = ImageIO.read(new File("Resources/sw_bal_fel.jpg"));
			sDU = ImageIO.read(new File("Resources/sw_le_fel.jpg"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
    
    public void setGraphics(Graphics gr) {
        g = gr;
    }

	/**
	 * A vonat kirajzolasaert felelos metodus
	 * @param train a kirajzolando vonat
	 */
	public void drawTrain(Train train){
		g.setColor(train.getColor());
		if(train.isEmpty()==false){
			if(train.getCurrentRail() != null){
				if(train.getColor()==Color.RED){
					g.drawImage(red_w,MapCreator.getFieldCoordY(train.getCurrentRail())*HEIGHT,MapCreator.getFieldCoordX(train.getCurrentRail())*HEIGHT,WIDTH,HEIGHT,null);

				}
				else if(train.getColor()==Color.GREEN){
					g.drawImage(green_w,MapCreator.getFieldCoordY(train.getCurrentRail())*HEIGHT,MapCreator.getFieldCoordX(train.getCurrentRail())*HEIGHT,WIDTH,HEIGHT,null);

				}
				else if(train.getColor()==Color.BLUE){
					g.drawImage(blue_w,MapCreator.getFieldCoordY(train.getCurrentRail())*HEIGHT,MapCreator.getFieldCoordX(train.getCurrentRail())*HEIGHT,WIDTH,HEIGHT,null);

				}
				else if(train.getColor()==Color.ORANGE){
					g.drawImage(orange_w,MapCreator.getFieldCoordY(train.getCurrentRail())*HEIGHT,MapCreator.getFieldCoordX(train.getCurrentRail())*HEIGHT,WIDTH,HEIGHT,null);

				}
			}

		}
		else{
			if(train.getCurrentRail() != null)
				g.fillRect(MapCreator.getFieldCoordY(train.getCurrentRail())*HEIGHT,MapCreator.getFieldCoordX(train.getCurrentRail())*HEIGHT,WIDTH,HEIGHT);
		}
	}

	/**
	 * Az ures mezo kirajzolasaert felelos metodus
	 * @param emptyField a kirajzolando ures mezo
	 */
	public void drawEmptyField(EmptyField emptyField){
		g.drawImage(ffield,MapCreator.getFieldCoordY(emptyField)*HEIGHT,MapCreator.getFieldCoordX(emptyField)*WIDTH,WIDTH,HEIGHT,null);
	}

	/**
	 * A sin kirajzolasaert felelos metodus
	 * @param rail a kirajzolando sin
	 */
	public void drawRail(Rail rail){
		g.drawImage(rrail,MapCreator.getFieldCoordY(rail)*HEIGHT,MapCreator.getFieldCoordX(rail)*WIDTH,WIDTH,HEIGHT,null);
	}

	/**
	 * A keresztut kirajzolasaert felelos metodus
	 * @param crossRail a kirajzolando keresztut
	 */
	public void drawCrossRail(CrossRail crossRail){
		g.drawImage(rrail,MapCreator.getFieldCoordY(crossRail)*HEIGHT,MapCreator.getFieldCoordX(crossRail)*WIDTH,WIDTH,HEIGHT,null);
	}

	/**
	 * Az allomas kirajzolasaert felelos metodus
	 * @param railStation a kirajzolando allomas
	 */
	public void drawRailStation(RailStation railStation){
		if(railStation.isEmptyy()==true){
			if(railStation.getColor()==Color.RED){
				g.drawImage(red_e,MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT,null);
			}
			else if(railStation.getColor()==Color.GREEN){
				g.drawImage(green_e,MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT,null);
			}
			else if(railStation.getColor()==Color.ORANGE){
				g.drawImage(orange_e,MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT,null);
			}
			else if(railStation.getColor()==Color.BLUE){
				g.drawImage(blue_e,MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT,null);
			}

		}
		else if(railStation.isEmptyy()==false){
			if(railStation.getColor()==Color.RED){
				g.drawImage(red_f,MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT,null);
			}
			else if(railStation.getColor()==Color.GREEN){
				g.drawImage(green_f,MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT,null);
			}
			else if(railStation.getColor()==Color.ORANGE){
				g.drawImage(orange_f,MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT,null);
			}
			else if(railStation.getColor()==Color.BLUE){
				g.drawImage(blue_f,MapCreator.getFieldCoordY(railStation)*HEIGHT,MapCreator.getFieldCoordX(railStation)*WIDTH,WIDTH,HEIGHT,null);
			}

		}
	}

	/**
	 * A startRail kirajzolasaert felelos metodus
	 * @param startRail a kirajzolando startRail
	 */
	public void drawStartRail(StartRail startRail){
		g.setColor(Color.GRAY);
		g.fillRect(MapCreator.getFieldCoordY(startRail)*HEIGHT,MapCreator.getFieldCoordX(startRail)*WIDTH,WIDTH,HEIGHT);
	}

	/**
	 * A valto kirajzolasaert felelos metodus
	 * @param sw a kirajzolando valto
	 */
	public void drawSwitch(Switch sw){
		if (sw.getSelectedRail() == sw.getRight() && sw.getFixRail() == sw.getUp() ||
				sw.getSelectedRail() == sw.getUp() && sw.getFixRail() == sw.getRight())
			g.drawImage(sRU,MapCreator.getFieldCoordY(sw)*HEIGHT,MapCreator.getFieldCoordX(sw)*WIDTH,WIDTH,HEIGHT,null);
		else if (sw.getSelectedRail() == sw.getRight() && sw.getFixRail() == sw.getLeft() ||
				sw.getSelectedRail() == sw.getLeft() && sw.getFixRail() == sw.getRight())
			g.drawImage(sRL,MapCreator.getFieldCoordY(sw)*HEIGHT,MapCreator.getFieldCoordX(sw)*WIDTH,WIDTH,HEIGHT,null);
		else if (sw.getSelectedRail() == sw.getRight() && sw.getFixRail() == sw.getDown() ||
				sw.getSelectedRail() == sw.getDown() && sw.getFixRail() == sw.getRight())
			g.drawImage(sRD,MapCreator.getFieldCoordY(sw)*HEIGHT,MapCreator.getFieldCoordX(sw)*WIDTH,WIDTH,HEIGHT,null);
		else if (sw.getSelectedRail() == sw.getUp() && sw.getFixRail() == sw.getLeft() ||
				sw.getSelectedRail() == sw.getLeft() && sw.getFixRail() == sw.getUp())
			g.drawImage(sLU,MapCreator.getFieldCoordY(sw)*HEIGHT,MapCreator.getFieldCoordX(sw)*WIDTH,WIDTH,HEIGHT,null);
		else if (sw.getSelectedRail() == sw.getDown() && sw.getFixRail() == sw.getLeft() ||
				sw.getSelectedRail() == sw.getLeft() && sw.getFixRail() == sw.getDown())
			g.drawImage(sLD,MapCreator.getFieldCoordY(sw)*HEIGHT,MapCreator.getFieldCoordX(sw)*WIDTH,WIDTH,HEIGHT,null);
		else if (sw.getSelectedRail() == sw.getDown() && sw.getFixRail() == sw.getUp() ||
				sw.getSelectedRail() == sw.getUp() && sw.getFixRail() == sw.getDown())
			g.drawImage(sDU,MapCreator.getFieldCoordY(sw)*HEIGHT,MapCreator.getFieldCoordX(sw)*WIDTH,WIDTH,HEIGHT,null);
	}

	/**
	 * Az alagut kirajzolasaert felelos metodus
	 * @param tunnel a kirajzolando valto
	 */
	public void drawTunnel(Tunnel tunnel) {
		if (tunnel.isSelected() == true)
			g.drawImage(selected,MapCreator.getFieldCoordY(tunnel)*HEIGHT,MapCreator.getFieldCoordX(tunnel)*WIDTH,WIDTH,HEIGHT,null);
		else
			g.drawImage(not_selected,MapCreator.getFieldCoordY(tunnel)*HEIGHT,MapCreator.getFieldCoordX(tunnel)*WIDTH,WIDTH,HEIGHT,null);

	}
}
