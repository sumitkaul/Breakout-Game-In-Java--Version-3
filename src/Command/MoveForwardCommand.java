package Command;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Utility.Direction;
import Utility.GameClock;

import interfaces.Command;
import interfaces.Savable;
import interfaces.SavableCommand;
import customViews.Ball;
import customViews.Board;

public class MoveForwardCommand implements Command, SavableCommand{
	private Ball ball;
	private Board board;
	private int currentX;
	private int currentY;
	private Direction xDir;
	private Direction yDir;
	
//	private double seconds;
	
	private int hour;
	private int minute;
	private int second;

	public MoveForwardCommand(Ball ball,Board board) {
		this.ball = ball;
		this.board = board;
		this.setCurrentX(ball.getxCoordinate());
		this.setCurrentY(ball.getyCoordinate());
		this.setxDir(ball.getxDir());
		this.setyDir(ball.getyDir());
//		this.setSeconds(GameClock.getGlobalClock().getSeconds());
		this.setHour(GameClock.getGlobalClock().getHour());
		this.setMinute(GameClock.getGlobalClock().getMinute());
		this.setSecond(GameClock.getGlobalClock().getSecond());
	}
	
	@Override
	public void execute(HashMap<String, Object> map){
		ball.setVelocity(2);
		ball.move();
		board.repaint();
	}
	
	@Override
	public void redo() {
		ball.moveTo(currentX,currentY);
		ball.setxDir(getxDir());
		ball.setyDir(getyDir());
		board.repaint();
		
	}

	public Board getBoard() {
		return board;
	}


	public void setBoard(Board board) {
		this.board = board;
	}


	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public Ball getBall() {
		return ball;
	}


	public int getCurrentX() {
		return currentX;
	}


	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}


	public int getCurrentY() {
		return currentY;
	}


	public void setCurrentY(int currentY) {
		this.currentY = currentY;
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

/*	public double getSeconds() {
		return seconds;
	}

	public void setSeconds(double d) {
		this.seconds = d;
	}*/

	public void setHour(int hour) {
		this.hour = hour;	
	}
	
	public int getHour() {
		return hour;
	}
	
	public void setMinute(int minute) {
		this.minute = minute;	
	}
	
	public int getMinute() {
		return minute;
	}
	
	public void setSecond(int second) {
		this.second = second;	
	}
	
	public int getSecond() {
		return second;
	}

	@Override
	public void save(Document doc, Element parentElement, int index) {
		Element elem = doc.createElement("Index" + index);
		parentElement.appendChild(elem);
		elem.setAttribute("Type", "MoveForwardCommand");
		elem.setAttribute("hour", String.valueOf(hour));
		elem.setAttribute("minute", String.valueOf(minute));
		elem.setAttribute("second", String.valueOf(second));
		
		elem.setAttribute("currentX", String.valueOf(getCurrentX()));		
		elem.setAttribute("currentY", String.valueOf(getCurrentY()));		
		elem.setAttribute("xDir", String.valueOf(xDir));
		elem.setAttribute("yDir", String.valueOf(yDir));		
	}

	@Override
	public void load(Document doc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Document doc, Element parentElement) {
		// TODO Auto-generated method stub
		
	}
}
