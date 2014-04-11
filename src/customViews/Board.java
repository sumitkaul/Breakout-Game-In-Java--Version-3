package customViews;

import interfaces.ClockObserver;
import interfaces.Resizable;
import interfaces.Savable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JPanel;


import Command.BrickBlowCommand;
import Command.MoveForwardCommand;
import Command.PaddleMoveCommand;
import Command.PlaySoundCommand;
import Utility.Constants;
import Utility.Direction;
import Utility.GameClock;
import Utility.GameState;
import Utility.enums.Sounds;

import java.io.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.apache.log4j.Logger;


public class Board extends JPanel implements KeyListener, MouseMotionListener, ClockObserver,Savable, Resizable
{

	public int frameHeight;
	
	public int frameWidth;
	private Ball ball;
	private Brick brick[] = new Brick [6];
	private Paddle paddle;

	// the wall variables are used to determine the position of the 4 boundary
	private int bottomWall;
	private int topWall;
	private int rightWall;
	private int leftWall;
	private static Logger logger = Logger.getLogger(Board.class);
	

	Board(int frameWidth, int frameHeight) 
	{
		this.frameHeight = frameHeight;
		this.frameWidth = frameWidth;

		ball = new Ball(frameWidth / 10, frameHeight / 10, Constants.BALL_HEIGHT, Constants.BALL_WIDTH);
		paddle = new Paddle(frameWidth / 2, frameHeight / 4, Constants.PADDLE_HEIGHT, Constants.PADDLE_WIDTH);

		int xstart = frameWidth/2 + frameWidth/4;
		int ystart = frameHeight/4;
		for(int i =0; i<Constants.BRICK_COUNT; i++)
		{
			if((i%2) == 0)
			{
				brick[i] = new Brick(xstart,ystart,Constants.BRICK_HEIGHT,Constants.BRICK_WIDTH,i);
				ystart = ystart + 50;
			}
			else
			{
				brick[i] = new Brick(xstart,ystart,Constants.BRICK_HEIGHT,Constants.BRICK_WIDTH,i);
				ystart = ystart - 50;
				xstart = xstart + 50;
			}
		}

		bottomWall = frameHeight - 36;
		topWall = 0;
		rightWall = frameWidth -22;
		leftWall = 0;

																																																
		initComponents();
	}

	private void initComponents() {
		setBackground(new Color(Constants.BG_BOARD_R,Constants.BG_BOARD_G,Constants.BG_BOARD_B));
		setSize(new Dimension(frameWidth,frameHeight));
		addKeyListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		requestFocus();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if ((GameClock.getGlobalClock().getGameState() == GameState.INITIAL) ||
				(GameClock.getGlobalClock().getGameState() == GameState.RUNNING) ||
				(GameClock.getGlobalClock().getGameState() == GameState.PAUSED) ||
				(GameClock.getGlobalClock().getGameState() == GameState.REPLAY) ||
				(GameClock.getGlobalClock().getGameState() == GameState.UNDOING)){
			ball.draw(g);
			paddle.draw(g);
			int count = 0;
			for(int i =0; i<Constants.BRICK_COUNT; i++){
				if(brick[i].isHit()==false){
					count++;
					brick[i].draw(g);
				}
			}
			if(count == 0)
				GameClock.getGlobalClock().setGameState(GameState.STOPPED);

			
		}
		else if(GameClock.getGlobalClock().getGameState() == GameState.STOPPED)
		{
			Font finalTextFont = new Font("Serif", Font.ITALIC | Font.BOLD, Constants.STOPPED_FONT_SIZE);
			g.setFont(finalTextFont);
			g.setColor(Constants.STOPPED_COLOR);
//			/g.drawString("Game Over!!!", frameWidth / 2 - 50, frameHeight / 2);
			g.drawString("You Won!!!", frameWidth / 2 - 50, frameHeight / 2);
			GameScene.playSound(Sounds.Win);
		}
	}

	public void refreshView() {
		checkForDirectionChange();
		MoveForwardCommand moveForwardCommand = new MoveForwardCommand(ball,Board.this);
		GameClock.getGlobalClock().getCommandList().add(moveForwardCommand);
		moveForwardCommand.execute(null);
	}

	protected void checkForDirectionChange() {
		boolean paddleCollision=false;
		//GameClock game = new GameClock();
		int ballStartXC = ball.getxCoordinate();
		int ballEndXC = ballStartXC + ball.getWidth();
		int ballStartYC = ball.getyCoordinate();
		int ballEndYC = ballStartYC + ball.getHeight();

		int paddleStartXC = paddle.getxCoordinate();
		int paddleEndXC = paddleStartXC + paddle.getWidth();
		int paddleStartYC = paddle.getyCoordinate();
		int paddleEndYC = paddle.getyCoordinate() + paddle.getHeight();

		Direction xDir = ball.getxDir();
		Direction yDir = ball.getyDir();

		if (ballStartYC >= bottomWall) {
			ball.setyDir(Direction.TOP);
		}

		if (ballStartYC <= topWall) {
			ball.setyDir(Direction.BOTTOM);
		}

		if (ballStartXC >= rightWall)
			ball.setxDir(Direction.LEFT);

		if (ballStartXC <= leftWall)
			ball.setxDir(Direction.RIGHT);

		// check for collision with paddle

		// ball moving from top left to bottom right
		// ball's x range should be between paddle's x range
		if (xDir == Direction.RIGHT && yDir == Direction.BOTTOM && ballEndXC >= paddleStartXC
				&& ballEndXC <= paddleEndXC) {

			if ((ballEndYC >= paddleStartYC && ballEndYC <= paddleEndYC && (ballStartXC >= paddleStartXC))) {
				ball.setxDir(Direction.RIGHT);
				ball.setyDir(Direction.TOP);
				paddleCollision=true;
			}

			else if ((ballEndYC >= paddleStartYC && ballEndYC <= paddleEndYC)
					|| (ballStartYC >= paddleStartYC && ballStartYC <= paddleEndYC)) {
				ball.setxDir(Direction.LEFT);
				ball.setyDir(Direction.BOTTOM);
				paddleCollision=true;
			}
		}

		// ball moving from bottom left to top right
		if (xDir == Direction.RIGHT && yDir == Direction.TOP && ballEndXC >= paddleStartXC && ballEndXC <= paddleEndXC) {
			if ((ballStartYC <= paddleEndYC && ballStartYC >= paddleStartYC && ballStartXC >= paddleStartXC)) {

				ball.setxDir(Direction.RIGHT);
				ball.setyDir(Direction.BOTTOM);
				paddleCollision=true;
			}

			else if ((ballStartYC >= paddleStartYC && ballStartYC <= paddleEndYC)
					|| (ballEndYC >= paddleStartYC && ballEndYC <= paddleEndYC)) {

				ball.setxDir(Direction.LEFT);
				ball.setyDir(Direction.TOP);
				paddleCollision=true;
			}
		}

		// ball moving from top right to bottom left
		if (xDir == Direction.LEFT && yDir == Direction.BOTTOM && ballStartXC <= paddleEndXC
				&& ballStartXC >= paddleStartXC) {

			if ((ballEndYC >= paddleStartYC && ballEndYC <= paddleEndYC && ballEndXC <= paddleEndXC)) {

				ball.setxDir(Direction.LEFT);
				ball.setyDir(Direction.TOP);
				paddleCollision=true;


			} else if ((ballEndYC >= paddleStartYC && ballEndYC <= paddleEndYC)
					|| (ballStartYC >= paddleStartYC && ballStartYC <= paddleEndYC)) {

				ball.setxDir(Direction.RIGHT);
				ball.setyDir(Direction.BOTTOM);
				paddleCollision=true;


			}
		}

		// ball moving from bottom right to top left
		if (xDir == Direction.LEFT && yDir == Direction.TOP && ballStartXC <= paddleEndXC
				&& ballStartXC >= paddleStartXC) {

			if ((ballStartYC <= paddleEndYC && ballStartYC >= paddleStartYC && ballEndXC <= paddleEndXC)) {

				ball.setxDir(Direction.LEFT);
				ball.setyDir(Direction.BOTTOM);
				paddleCollision=true;

			}

			else if ((ballEndYC >= paddleStartYC && ballEndYC <= paddleEndYC)
					|| (ballStartYC >= paddleStartYC && ballStartYC <= paddleEndYC)) {
				ball.setxDir(Direction.RIGHT);
				ball.setyDir(Direction.TOP);
				paddleCollision=true;
			}
		}
		
		if(paddleCollision==true){
			
			PlaySoundCommand playSoundCommand = new PlaySoundCommand(Sounds.PaddleHit);
			playSoundCommand.execute(null);
			GameClock.getGlobalClock().getCommandList().add(playSoundCommand);
		}
		
		// check for collision with brick
		int brXS[] = new int [6];
		int brXE[] = new int [6];
		int brYS[] = new int [6];
		int brYE[] = new int [6];
		
		for(int i = 0;i<6;i++)
		{
			brXS[i] = brick[i].getxCoordinate();
			brXE[i] = brick[i].getxCoordinate() + brick[i].getWidth();
			brYS[i] = brick[i].getyCoordinate();
			brYE[i] = brick[i].getyCoordinate() + brick[i].getHeight();
		}
		
		int count = 0;
		
		//checking for the actual collision
		for(int i = 0; i<Constants.BRICK_COUNT; i++)
		{
			//if(((ballStartXC<=brXE[i])||(ballEndXC>=brXS[i])) && ((ballStartYC<=brYE[i])||(ballEndYC>=brYS[i])))
			if(brick[i].isHit()==false)
			{	
				if(((brXS[i]<= ballStartXC) && (ballStartXC <=brXE[i])) && ((brYS[i]<= ballStartYC) && (ballStartYC <=brYE[i])) || 
				((brXS[i]<= ballEndXC) && (ballEndXC <=brXE[i])) && ((brYS[i]<= ballEndYC) && (ballEndYC <=brYE[i])))
				{
					BrickBlowCommand brickBlowCommand = new BrickBlowCommand(brick[i], this);
					brickBlowCommand.execute(null);
					GameClock.getGlobalClock().getCommandList().add(brickBlowCommand);
					count++;
					PlaySoundCommand playSoundCommand = new PlaySoundCommand(Sounds.BrickBlow);
					playSoundCommand.execute(null);
					GameClock.getGlobalClock().getCommandList().add(playSoundCommand);
					/*if(count==6)
					{
						GameClock.setGameState(GameState.STOPPED);
					}*/

					logger.debug("Direction = \t"+xDir+"\t"+yDir);
					logger.debug("Nair Ball X = S "+ballStartXC+" E "+ballEndXC);
					logger.debug("Ball Y = S "+ballStartYC+" E "+ballEndYC);
					logger.debug("Brick X = S "+brXS[i]+" E "+brXE[i]+"\t"+i);
					logger.debug("Brick Y = S "+brYS[i]+" E "+brYE[i]);
					if (xDir == Direction.RIGHT && yDir == Direction.BOTTOM && ((ballEndXC == brXS[i]) || (ballStartXC == brXS[i])))// **&& ballStartYC < brYE[i])
					{
						ball.setxDir(Direction.LEFT);
						ball.setyDir(Direction.BOTTOM);
						logger.debug("Direction after hit : \t"+xDir+"\t"+yDir);
					}
					else if (xDir == Direction.RIGHT && yDir == Direction.BOTTOM && ((ballEndYC >= brYS[i]) || (ballStartYC >= brYS[i])))// **&& ballStartXC < brXE[i])
					{
						ball.setyDir(Direction.TOP);
						ball.setxDir(Direction.RIGHT);
						logger.debug("Direction after hit : \t"+xDir+"\t"+yDir);
					}
					else if (xDir == Direction.RIGHT && yDir == Direction.TOP && ((ballEndXC == brXS[i]) || (ballStartXC == brXS[i])))// **&& ballEndYC > brYS[i])
					{
						ball.setxDir(Direction.LEFT);
						ball.setyDir(Direction.TOP);
						logger.debug("Direction after hit : \t"+xDir+"\t"+yDir);
					}
					else if (xDir == Direction.RIGHT && yDir == Direction.TOP && ((ballStartYC <= brYE[i]) || (ballEndYC <= brYE[i])))// && ballStartXC < brXE[i])
					{
						ball.setyDir(Direction.BOTTOM);
						ball.setxDir(Direction.RIGHT);
						logger.debug("Direction after hit : \t"+xDir+"\t"+yDir);
					}
					else if (xDir == Direction.LEFT && yDir == Direction.BOTTOM && ((ballStartXC == brXE[i]) || (ballEndXC == brXE[i])))// **&& ballStartYC < brYE[i])
					{
						ball.setxDir(Direction.RIGHT);
						ball.setyDir(Direction.BOTTOM);
						logger.debug("Direction after hit : \t"+xDir+"\t"+yDir);
					}
					else if (xDir == Direction.LEFT && yDir == Direction.BOTTOM && ((ballStartYC >= brYS[i]) || (ballEndYC >= brYS[i])))// && ballEndXC > brXS[i])
					{
						ball.setyDir(Direction.TOP);
						ball.setxDir(Direction.LEFT);
						logger.debug("Direction after hit : \t"+xDir+"\t"+yDir);
					}
					else if (xDir == Direction.LEFT && yDir == Direction.TOP && ((ballStartXC == brXE[i]) || (ballEndXC == brXE[i])))// **&& ballEndYC > brYS[i])
					{
						ball.setxDir(Direction.RIGHT);
						ball.setyDir(Direction.TOP);
						logger.debug("Direction after hit : \t"+xDir+"\t"+yDir);
					}
					else if (xDir == Direction.LEFT && yDir == Direction.TOP && ((ballStartYC <= brYE[i]) || (ballEndYC <= brYE[i])))// **&& ballEndXC >brXS[i])
					{
						ball.setyDir(Direction.BOTTOM);
						ball.setxDir(Direction.LEFT);
						logger.debug("Direction after hit : \t"+xDir+"\t"+yDir);
					}
					
				}
			}
		}
	
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		int paddleXCoordinate = paddle.getxCoordinate();
		int paddleYCoordinate = paddle.getyCoordinate();
		int paddleEndingYCoordinate = paddleYCoordinate + paddle.getHeight();

		int keyCode = arg0.getKeyCode();

		if (keyCode == KeyEvent.VK_DOWN && paddleEndingYCoordinate <= (bottomWall + 18))
			paddle.setyCoordinate(paddleYCoordinate + 10);

		else if (paddleYCoordinate >= (topWall + 4) && keyCode == KeyEvent.VK_UP)
			paddle.setyCoordinate(paddleYCoordinate - 10);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int yMouseCoordinate = e.getY();
		int paddleHeight = paddle.getHeight();
		int estimatedYCoordinate = yMouseCoordinate + paddleHeight;

		if (yMouseCoordinate >= topWall && estimatedYCoordinate <= (bottomWall + 20)) {
			
			if(GameClock.getGlobalClock().getGameState() == GameState.RUNNING)
			{
				PaddleMoveCommand paddleMoveCommand = new PaddleMoveCommand(paddle,Board.this);
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("yCoordinate",Integer.valueOf(yMouseCoordinate));
				paddleMoveCommand.execute(map);
				GameClock.getGlobalClock().getCommandList().add(paddleMoveCommand);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	//ClockObserver Methods
	@Override
	public void update(GameClock clock) {
		if(GameClock.getGlobalClock().getGameState() == GameState.RUNNING)
			refreshView();
	}

	@Override
	public void stateChanged(GameState state){
		if(state == GameState.REPLAY){
			resetBricks();
		}
		else if(state == GameState.INITIAL){
			GameClock.getGlobalClock().reset();
			resetBricks();
			ball.reset();
		}
	}
		

		private void resetBricks(){
			for(int i=0; i<Constants.BRICK_COUNT; i++){
				brick[i].setHit(false);
				repaint();
			}
		}
	
	//Getter Setter methods
	
	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}

	@Override
	public void save(Document doc, Element parentElem) {
		Element elem = doc.createElement("BoardPanel");
		parentElem.appendChild(elem);
		ball.save(doc, elem);
		paddle.save(doc, elem);
		for(int i =0; i<Constants.BRICK_COUNT; i++)
		{
			brick[i].save(doc, elem);
		}
	}

	@Override
	public void load(Document doc) {
		ball.load(doc);
		paddle.load(doc);	
		for(int i =0; i<Constants.BRICK_COUNT; i++)
		{
			brick[i].load(doc);
		}
	}
	
	public Brick[] getBricks()
	{
	    return brick;	
	}

	@Override
	public void Resize(int framewidth, int frameheight) 
	{
		// TODO Auto-generated method stub
		int widthdiff = framewidth - Constants.FRAME_WIDTH;
		int heightdiff = frameheight - Constants.FRAME_HEIGHT;
		this.frameWidth = Constants.BOARD_WIDTH + (int)((widthdiff*7)/10);
		this.frameHeight = Constants.BOARD_LENGTH + (int)((heightdiff*5)/7);
		
		//paddle.Resize(this.frameWidth, this.frameHeight);
		//ball.Resize(this.frameWidth, this.frameHeight);
		//for(int i=0; i<Constants.BRICK_COUNT; i++)
		//{
		//		brick[i].Resize(this.frameWidth, this.frameHeight);
		//}
		//bottomWall = frameHeight - 36;
		topWall = 0;
	//	rightWall = frameWidth -22;
		leftWall = 0;

																																																
		initComponents();

		
	}
	

}
