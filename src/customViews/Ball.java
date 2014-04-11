package customViews;

import java.awt.Color;
import java.awt.Graphics;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import interfaces.Resizable;
import interfaces.Savable;
import interfaces.Sprite;
import Utility.Constants;
import Utility.Direction;

public class Ball extends CustomView implements Sprite,Savable,Resizable
{

	private Direction xDir;
	private Direction yDir;
	private int velocity;

	Ball(int xC, int yC, int hT, int wD) {
		super(xC, yC, hT, wD);	
		reset();
	}

	public Direction getxDir() {
		return xDir;
	}

	public void setxDir(Direction xDir) {
		this.xDir = xDir;
	}

	public Direction getyDir() {
		return yDir;
	}

	public void setyDir(Direction yDir) {
		this.yDir = yDir;
	}

	// this sets the new coordinates for the ball depending on the direction in
	// which it is moving
	public void move() {

		int xC = this.getxCoordinate();
		int yC = this.getyCoordinate();

		if (xDir == Direction.RIGHT && yDir == Direction.BOTTOM) {

			this.setxCoordinate(xC + velocity);
			this.setyCoordinate(yC + velocity);
		}

		else if (xDir == Direction.RIGHT && yDir == Direction.TOP) {

			this.setxCoordinate(xC + velocity);
			this.setyCoordinate(yC - velocity);
		}

		else if (xDir == Direction.LEFT && yDir == Direction.TOP) {

			this.setxCoordinate(xC - velocity);
			this.setyCoordinate(yC - velocity);
		}

		else if (xDir == Direction.LEFT && yDir == Direction.BOTTOM) {

			this.setxCoordinate(xC - velocity);
			this.setyCoordinate(yC + velocity);
		}
	
		//System.out.println("relXy: "+relX+" s s " +relY);
		//relY=this.getyCoordinate();
		
		//relX=
	}

	@Override
	public void draw(Graphics g) {
		int relativeX,relativeY;
		relativeX=(int)((((Constants.BOARD_WIDTH-breakout.Breakout.gameScene.board.frameWidth)*100.0)/Constants.BOARD_WIDTH));
		relativeY=(int)((((Constants.BOARD_LENGTH-breakout.Breakout.gameScene.board.frameHeight)*100.0)/Constants.BOARD_LENGTH));		
		
		relX=(int) (this.getxCoordinate()-(this.getxCoordinate()*relativeX*1.0)/100.0);
		relY=(int) (this.getyCoordinate()-(this.getyCoordinate()*relativeY*1.0)/100.0);
		
		relH=(int) (this.getHeight()-(this.getHeight()*relativeX*1.0)/100.0);
		relW=(int) (this.getWidth()-(this.getWidth()*relativeX*1.0)/100.0);
		g.setColor(new Color(Constants.BALL_R, Constants.BALL_G, Constants.BALL_B));
		g.fillOval(getRelX(), getRelY(), getRelW(), getRelH());
		
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public void moveTo(int previousX, int previousY) {

		this.setxCoordinate(previousX);
		this.setyCoordinate(previousY);

	}

	public void setBallDirection(Direction previousxDir, Direction previousyDir) {
		this.setxDir(previousxDir);
		this.setyDir(previousyDir);
		
	}

	public void reset() {
		Direction []directionX = new Direction[]{Direction.LEFT, Direction.RIGHT};
		Direction []directionY = new Direction[]{Direction.TOP, Direction.BOTTOM};
		xDir = directionX[(int)Math.floor((2*Math.random()))];
		yDir = directionY[(int)Math.floor((2*Math.random()))];
		
		setxCoordinate(800/10);
		setyCoordinate(800/10);
		setWidth(20);
		setHeight(20);
		
		
	}

	@Override
	public void save(Document doc, Element parentElem) {		
		Element elem = doc.createElement("Ball");
		parentElem.appendChild(elem);
		elem.setAttribute("xCoordinate", String.valueOf(getxCoordinate()));		
		elem.setAttribute("yCoordinate", String.valueOf(getyCoordinate()));
		elem.setAttribute("velocity", String.valueOf(velocity));
		elem.setAttribute("xDir", String.valueOf(xDir));
		elem.setAttribute("yDir", String.valueOf(yDir));	
		
	}

	@Override
	public void load(Document doc) {
		// TODO Auto-generated method stub
		NamedNodeMap nmap = doc.getElementsByTagName("Ball").item(0).getAttributes();
		String str = ((Attr)nmap.getNamedItem("xCoordinate")).getValue();
		setxCoordinate( Integer.parseInt(str));		
		str = ((Attr)nmap.getNamedItem("yCoordinate")).getValue();
		setyCoordinate( Integer.parseInt(str));	
		str = ((Attr)nmap.getNamedItem("velocity")).getValue();
		setVelocity( Integer.parseInt(str));
		str = ((Attr)nmap.getNamedItem("xDir")).getValue();
		//Class<Direction> c =;
		setxDir( Direction.valueOf(Direction.class,str));
		str = ((Attr)nmap.getNamedItem("yDir")).getValue();
		//Class<Direction> c =;
		setyDir( Direction.valueOf(Direction.class,str));
	}

	@Override
	public void Resize(int framewidth, int frameheight) 
	{
		// TODO Auto-generated method stub
//		int widthdiff = framewidth - Constants.BOARD_WIDTH;
//		int heightdiff = frameheight - Constants.BOARD_LENGTH;
//		this.setWidth(Constants.BALL_WIDTH + (int)((widthdiff * 2)/70));
	//	this.setHeight(Constants.BALL_HEIGHT + (int)((heightdiff*2)/50));
		
		
		
	}
}