package Command;

import java.util.HashMap;

import customViews.Board;
import interfaces.Command;


public class MoveBackCommand implements Command{
	
	private MoveForwardCommand moveForwardCommand;

	public MoveBackCommand(MoveForwardCommand moveForwardCommand) {
		this.moveForwardCommand = moveForwardCommand;
	}
	@Override
	public void execute(HashMap<String,Object> map) {
		
		moveForwardCommand.getBall().moveTo(moveForwardCommand.getCurrentX(),moveForwardCommand.getCurrentY());
		moveForwardCommand.getBall().setBallDirection(moveForwardCommand.getxDir(),moveForwardCommand.getyDir());
		moveForwardCommand.getBoard().repaint();
		
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}


}
