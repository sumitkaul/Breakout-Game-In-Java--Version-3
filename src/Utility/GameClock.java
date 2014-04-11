package Utility;

import interfaces.Clock;
import interfaces.ClockObserver;
import interfaces.Command;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Command.BrickBlowCommand;
import Command.BrickUndoBlowCommand;
import Command.MoveBackCommand;
import Command.MoveForwardCommand;
import Command.PaddleMoveCommand;
import Command.PaddleUndoMoveCommand;
import Command.PlaySoundCommand;
import Command.PlaySoundUndoCommand;

public class GameClock implements Runnable,Clock{
	
	private GameState gameState;
	private static GameClock sharedClock;
//	private double seconds;
	private double interval;
	private int hour, minute, second;
	private ArrayList<ClockObserver> delegates;
	private ArrayList<Command> commandList;
	private ChangeSize state;
	
	
	private GameClock(){
		//setGameState(GameState.INITIAL);
		
//		seconds = 0.0;
		interval = 0.0;
		delegates = new ArrayList<ClockObserver>();
		this.commandList = new ArrayList<Command>();
	}  
	
	public static GameClock getGlobalClock() {
	      if(sharedClock == null) {
	    	  sharedClock = new GameClock();
	      }
	      return sharedClock;
	   }

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(gameState == GameState.RUNNING){
				updateClock();
			}
			else if(gameState == GameState.UNDOING){
				int lastIndex = commandList.size()-1;
				for(int i=lastIndex-1; i>=0; i--){
					if(gameState == GameState.PAUSED ){
//						interval = getSeconds();
						hour=getHour();
						minute=getMinute();
						second=getSecond();						
						break;
					}
					Command command = commandList.get(i);
						if(command instanceof MoveForwardCommand){		
							MoveForwardCommand moveforwardCommand = (MoveForwardCommand) command;
							MoveBackCommand moveBackwardCommand = new MoveBackCommand(moveforwardCommand);
							moveBackwardCommand.execute(null);
							
//							this.setSeconds(moveforwardCommand.getSeconds());
							this.setHour(moveforwardCommand.getHour());
							this.setMinute(moveforwardCommand.getMinute());
							this.setSecond(moveforwardCommand.getSecond());
							
						}
						else if(command instanceof PaddleMoveCommand){
							PaddleMoveCommand paddleMoveCommand = (PaddleMoveCommand) command;
							PaddleUndoMoveCommand paddleUndoMoveCommand = new PaddleUndoMoveCommand(paddleMoveCommand);
							paddleUndoMoveCommand.execute(null);
							
							this.setHour(paddleMoveCommand.getHour());
							this.setMinute(paddleMoveCommand.getMinute());
							this.setSecond(paddleMoveCommand.getSecond());
				
						}
						else if(command instanceof BrickBlowCommand){
							BrickBlowCommand brickBlowCommand = (BrickBlowCommand)command;
							
							BrickUndoBlowCommand brickUndoBlowCommand = new BrickUndoBlowCommand(brickBlowCommand);
							brickUndoBlowCommand.execute(null);
							
							this.setHour(brickBlowCommand.getHour());
							this.setMinute(brickBlowCommand.getMinute());
							this.setSecond(brickBlowCommand.getSecond());

						}
						else if(command instanceof PlaySoundCommand) {
							PlaySoundCommand playSoundCommand = (PlaySoundCommand)command;
							
							PlaySoundUndoCommand playSoundUndoCommand = new PlaySoundUndoCommand(playSoundCommand);
							playSoundUndoCommand.execute(null);
							
							this.setHour(playSoundCommand.getHour());
							this.setMinute(playSoundCommand.getMinute());
							this.setSecond(playSoundCommand.getSecond());
						}
						
						updateClock();
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						commandList.remove(i);
				}
				hour=getHour();
				minute=getMinute();
				second=getSecond();
				setGameState(GameState.PAUSED);
			}
			else if(gameState == GameState.REPLAY){
				int lastIndex = commandList.size()-1;
				for(int i=0; i<lastIndex; i++){
					if(gameState == GameState.PAUSED){
						hour=getHour();
						minute=getMinute();
						second=getSecond();
						break;
					}
					Command command = commandList.get(i);
					command.redo();
					if(command instanceof MoveForwardCommand){
						this.setHour(((MoveForwardCommand)command).getHour());
						this.setMinute(((MoveForwardCommand)command).getMinute());
						this.setSecond(((MoveForwardCommand)command).getSecond());
					}
					else if(command instanceof PaddleMoveCommand){
						this.setHour(((PaddleMoveCommand)command).getHour());
						this.setMinute(((PaddleMoveCommand)command).getMinute());
						this.setSecond(((PaddleMoveCommand)command).getSecond());
					}
					else if(command instanceof PlaySoundCommand) {
						this.setHour(((PlaySoundCommand)command).getHour());
						this.setHour(((PlaySoundCommand)command).getMinute());
						this.setHour(((PlaySoundCommand)command).getSecond());
					}
					
					updateClock();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				setGameState(GameState.PAUSED);
			}
			
		}
	}
	
	public ArrayList<Command> getCommandList() {
		return commandList;
	}

	public void setCommandList(ArrayList<Command> commandList) {
		this.commandList = commandList;
	}

	private void updateClock(){
		
		if(this.gameState == GameState.RUNNING){
			interval += 0.005;
			DecimalFormat dtime = new DecimalFormat("#.###"); 
			interval= Double.valueOf(dtime.format(interval));

/*			if(interval%1.0 == 0.0){
				setSeconds(interval);
			}*/
			if(interval%1.0 == 0.0){
				second+=1;
				setSecond(second);
				if (second%60 == 0 && minute!=59){
					minute+=1;
					second=0;
					setMinute(minute);
					setSecond(second);
					interval=0.0;
				}else if(second%60==0 && minute%59==0){
					hour+=1;
					minute=0;
					second=0;
					setHour(hour);
					setMinute(minute);
					setSecond(second);
				}
			}
		}
		notifyAllObservers();
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
	
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public void setSecond(int second) {
		this.second = second;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public int getSecond() {
		return second;
	}
	
	public String getTime(){
		 return getHour()+":"+getMinute()+":"+getSecond();
	}

	private void notifyAllObservers(){
		for(ClockObserver clockObserver : delegates){
			clockObserver.update(this);
		}
	}

	@Override
	public void addObserver(ClockObserver clockObserver) {
		delegates.add(clockObserver);
		
	}

	@Override
	public void removeObserver(ClockObserver clockObserver) {
		delegates.remove(clockObserver);		
	}

	@Override
	public void getState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setState(String state) {
		// TODO Auto-generated method stub
		
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
		if(delegates.size() > 0 && delegates!=null){
			for(ClockObserver clockObserver : delegates){
				clockObserver.stateChanged(this.gameState);
			}
		}
		
	}
	
	public void setChangeSize(ChangeSize state)
	{
		this.state = state;
	}
	
	public ChangeSize getChangeSize()
	{
		
		return state;	
	}

	public void reset() {
		commandList.clear();
		hour = 0;
		minute = 0;
		second = 0;
		interval = 0;
		
	}
	
}
