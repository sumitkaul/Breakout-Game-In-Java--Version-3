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


public class Brick extends CustomView implements Sprite,Savable, Resizable
{
	private boolean isHit;
	private int index;
	
	public boolean isHit() 
	{
		return isHit;
	}

	public void setHit(boolean isHit) 
	{
		this.isHit = isHit;
	}

	Brick(int xC, int yC, int hT, int wD, int ind) {
		super(xC, yC, hT, wD);
		isHit=false;
		index = ind;
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
		g.setColor(new Color(Constants.BRICK_R, Constants.BRICK_G, Constants.BRICK_B));
		g.fillRect(getRelX(), getRelY(), getRelW(), getRelH());
		
	}

	@Override
	public void save(Document doc, Element parentElement) {
		Element elem = doc.createElement("Brick" + String.valueOf(index));
		parentElement.appendChild(elem);
		elem.setAttribute("isHit", String.valueOf(isHit));	
	}

	@Override
	public void load(Document doc) {
		NamedNodeMap nmap = doc.getElementsByTagName("Brick" + String.valueOf(index)).item(0).getAttributes();
		String str = ((Attr)nmap.getNamedItem("isHit")).getValue();
		setHit( Boolean.valueOf(str));		
	}

	@Override
	public void Resize(int framewidth, int frameheight) 
	{
		// TODO Auto-generated method stub
//		int xstart = framewidth/2 + framewidth/4;
//		int ystart = frameheight/4;
//		int widthdiff = framewidth - Constants.BOARD_WIDTH;
//		int heightdiff = frameheight - Constants.BOARD_LENGTH;
//		if(this.isHit()==false)
//		{
//			this.setWidth(Constants.BRICK_WIDTH + (int)((widthdiff * 4)/70));
//			this.setHeight(Constants.BRICK_HEIGHT + (int)((heightdiff * 4)/50));
//			//this.setxCoordinate((int)(this.getxCoordinate()+(int)(widthdiff * 0.08)));
//			//this.setyCoordinate((int)(this.getyCoordinate()+(int)(heightdiff * 0.08)));
//		}
	}
}