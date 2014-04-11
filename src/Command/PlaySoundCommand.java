package Command;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import customViews.GameScene;

import Utility.GameClock;

import interfaces.Command;
import interfaces.SavableCommand;

public class PlaySoundCommand implements Command, SavableCommand {

	private int hour;
	private int minute;
	private int second ;
	private Utility.enums.Sounds sound;
	
	public Utility.enums.Sounds getSound() {
		return sound;
	}
	public PlaySoundCommand(Utility.enums.Sounds sound) {
		this.sound=sound;
	}
	@Override
	public void execute(HashMap<String, Object> map) {
		this.setHour(GameClock.getGlobalClock().getHour());
		this.setMinute(GameClock.getGlobalClock().getMinute());
		this.setSecond(GameClock.getGlobalClock().getSecond());
		GameScene.playSound(sound);
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
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	@Override
	public void redo() {
		GameScene.playSound(sound);
		
	}
	@Override
	public void save(Document doc, Element parentElement) {
		
		
	}
	@Override
	public void load(Document doc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(Document doc, Element parentElement, int index) {
		// TODO Auto-generated method stub
		Element elem = doc.createElement("Index"+index);
		parentElement.appendChild(elem);
		elem.setAttribute("Type", "PlaySoundCommand");
		elem.setAttribute("hour", String.valueOf(hour));
		elem.setAttribute("minute", String.valueOf(minute));
		elem.setAttribute("second", String.valueOf(second));		
		elem.setAttribute("sound", String.valueOf(sound));		
	}
}
