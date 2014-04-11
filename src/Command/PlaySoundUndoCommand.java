package Command;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import customViews.GameScene;

import interfaces.Command;
import interfaces.Savable;

public class PlaySoundUndoCommand implements Command, Savable {
	
	private PlaySoundCommand playSoundCommand;

	public PlaySoundUndoCommand(PlaySoundCommand playSoundCommand) {
		this.playSoundCommand=playSoundCommand;
	}
	@Override
	public void execute(HashMap<String, Object> map) {
		GameScene.playSound(playSoundCommand.getSound());
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(Document doc, Element parentElement) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void load(Document doc) {
		// TODO Auto-generated method stub
		
	}

}
