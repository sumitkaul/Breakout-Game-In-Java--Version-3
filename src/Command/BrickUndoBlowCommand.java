package Command;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import customViews.Board;
import customViews.Brick;

import interfaces.Command;
import interfaces.Savable;

public class BrickUndoBlowCommand implements Command,Savable{

	private BrickBlowCommand brickBlowCommand;
	
	public BrickUndoBlowCommand(BrickBlowCommand brickBlowCommand){
		this.brickBlowCommand = brickBlowCommand;
	}

	@Override
	public void execute(HashMap<String, Object> map) {
		brickBlowCommand.getBrick().setHit(false);
		brickBlowCommand.getBoard().repaint();
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
