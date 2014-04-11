package Command;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Utility.GameClock;

import customViews.Board;
import customViews.Brick;

import interfaces.Command;
import interfaces.Savable;
import interfaces.SavableCommand;

public class BrickBlowCommand implements Command,SavableCommand {

	private Brick brick;
	private Board board;
	
	private int hour;
	private int minute;
	private int second;
	
	public Brick getBrick() {
		return brick;
	}

	public void setBrick(Brick brick) {
		this.brick = brick;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public BrickBlowCommand(Brick brick, Board board){
		this.brick = brick;
		this.board = board;
	}
	
	@Override
	public void execute(HashMap<String, Object> map) {
		brick.setHit(true);
		board.repaint();
		
		this.setHour(GameClock.getGlobalClock().getHour());
		this.setMinute(GameClock.getGlobalClock().getMinute());
		this.setSecond(GameClock.getGlobalClock().getSecond());
	}

	@Override
	public void redo() {
		brick.setHit(true);
		board.repaint();
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	@Override
	public void save(Document doc, Element parentElement) {
		// blank
		
	}

	@Override
	public void load(Document doc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Document doc, Element parentElement, int index) {
		Element elem = doc.createElement("Index" + index);
		parentElement.appendChild(elem);
		elem.setAttribute("Type", "BrickBlowCommand");
		elem.setAttribute("hour", String.valueOf(hour));
		elem.setAttribute("minute", String.valueOf(minute));
		elem.setAttribute("second", String.valueOf(second));
		
		elem.setAttribute("BrickX", String.valueOf(brick.getxCoordinate()));
		elem.setAttribute("BrickY", String.valueOf(brick.getyCoordinate()));		
		elem.setAttribute("isHit", String.valueOf(brick.isHit()));
	}

}
