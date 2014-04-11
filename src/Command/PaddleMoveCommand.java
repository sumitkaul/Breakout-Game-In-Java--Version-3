package Command;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Utility.GameClock;

import customViews.Board;
import customViews.Paddle;
import interfaces.Command;
import interfaces.Savable;
import interfaces.SavableCommand;

public class PaddleMoveCommand implements Command, SavableCommand{

	private Paddle paddle;
	private Board board;
	private int yCoordinate;
//	private double seconds;
	private int hour;
	private int minute;
	private int second;
	
	public PaddleMoveCommand(Paddle paddle, Board board) {
		this.paddle = paddle;
		this.board = board;
//		this.setSeconds(GameClock.getGlobalClock().getSeconds());
		this.setHour(GameClock.getGlobalClock().getHour());
		this.setMinute(GameClock.getGlobalClock().getMinute());
		this.setSecond(GameClock.getGlobalClock().getSecond());
	}
	
	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@Override
	public void execute(HashMap<String, Object> map){
		Integer yCoordinateInteger = (Integer) map.get("yCoordinate");
		setyCoordinate(yCoordinateInteger.intValue());
		paddle.move(getyCoordinate());
		board.repaint();
		
	}

	@Override
	public void redo() {
		paddle.move(getyCoordinate());
		board.repaint();
		
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

/*	public double getSeconds() {
		return seconds;
	}

	public void setSeconds(double seconds) {
		this.seconds = seconds;
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
		
		Element elem = doc.createElement("Index"+index);
		parentElement.appendChild(elem);
		elem.setAttribute("Type","PaddleMoveCommand");
		elem.setAttribute("hour", String.valueOf(hour));
		elem.setAttribute("minute", String.valueOf(minute));
		elem.setAttribute("second", String.valueOf(second));		
		elem.setAttribute("yCoordinate", String.valueOf(getyCoordinate()));		
	}

	@Override
	public void load(Document doc) {
		
	}

	@Override
	public void save(Document doc, Element parentElement) {
		
	}
}
