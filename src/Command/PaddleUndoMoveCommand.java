package Command;

import java.util.HashMap;

import customViews.Board;
import customViews.Paddle;
import interfaces.Command;

public class PaddleUndoMoveCommand implements Command{

	private PaddleMoveCommand paddleMoveCommand;
	
	public PaddleUndoMoveCommand(PaddleMoveCommand paddleMoveCommand) {
		this.paddleMoveCommand = paddleMoveCommand;
	}
	
	@Override
	public void execute(HashMap<String,Object> map){
		int yCoordinate = paddleMoveCommand.getyCoordinate();
		paddleMoveCommand.getPaddle().move(yCoordinate);
		paddleMoveCommand.getBoard().repaint();
	}
	
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
