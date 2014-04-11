package customViews;

import java.awt.Color;
import java.awt.Graphics;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import Utility.Constants;

import interfaces.Resizable;
import interfaces.Savable;
import interfaces.Sprite;


public class Paddle extends CustomView implements Sprite,Savable, Resizable
{

	Paddle(int xC, int yC, int hT, int wD) {
		super(xC, yC, hT, wD);
	}
	
	public void move(int yC) {
		
		this.setyCoordinate(yC);
		
		relX=(int)((((Constants.BOARD_WIDTH-breakout.Breakout.gameScene.board.frameWidth)*100.0)/Constants.BOARD_WIDTH));
		relY=(int)((((Constants.BOARD_LENGTH-breakout.Breakout.gameScene.board.frameHeight)*100.0)/Constants.BOARD_LENGTH));
		
		
		relX=(int) (this.getxCoordinate()-(this.getxCoordinate()*relX*1.0)/100.0);
		relY=(int) (this.getyCoordinate()-(this.getyCoordinate()*relY*1.0)/100.0);
		

	}
	
	@Override
	public void draw(Graphics g) {
		int relativeX, relativeY;
		relativeX=(int)((((Constants.BOARD_WIDTH-breakout.Breakout.gameScene.board.frameWidth)*100.0)/Constants.BOARD_WIDTH));
		relativeY=(int)((((Constants.BOARD_LENGTH-breakout.Breakout.gameScene.board.frameHeight)*100.0)/Constants.BOARD_LENGTH));		
		
		relX=(int) (this.getxCoordinate()-(this.getxCoordinate()*relativeX*1.0)/100.0);
		relY=(int) (this.getyCoordinate()-(this.getyCoordinate()*relativeY*1.0)/100.0);
		
		relH=(int) (this.getHeight()-(this.getHeight()*relativeX*1.0)/100.0);
		relW=(int) (this.getWidth()-(this.getWidth()*relativeX*1.0)/100.0);
		g.setColor(new Color(Constants.PADDLE_R, Constants.PADDLE_G, Constants.PADDLE_R));
		g.fillRect(getRelX(), getRelY(),getRelW(), getRelH());
	}

	@Override
	public void save(Document doc, Element parentElem) {
		Element elem = doc.createElement("Paddle");
		parentElem.appendChild(elem);		
		elem.setAttribute("yCoordinate", String.valueOf(getyCoordinate()));	
	}

	@Override
	public void load(Document doc) {
		NamedNodeMap nmap = doc.getElementsByTagName("Paddle").item(0).getAttributes();
		String str = ((Attr)nmap.getNamedItem("yCoordinate")).getValue();
		setyCoordinate( Integer.parseInt(str));				
	}

	@Override
	public void Resize(int framewidth, int frameheight) 
	{
		// TODO Auto-generated method stub
		int widthdiff = framewidth - Constants.BOARD_WIDTH;
		int heightdiff = frameheight - Constants.BOARD_LENGTH;
		this.setWidth(Constants.PADDLE_WIDTH + (int)((widthdiff * 2)/70));
		this.setHeight(Constants.PADDLE_HEIGHT + (int)((heightdiff * 1)/5));
	}
}