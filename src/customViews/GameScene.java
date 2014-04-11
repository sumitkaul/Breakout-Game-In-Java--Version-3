package customViews;

import interfaces.ClockObserver;
import interfaces.Command;
import interfaces.Resizable;
import interfaces.Savable;
import java.awt.event.*;
import java.applet.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Command.MoveBackCommand;
import Command.MoveForwardCommand;
import Command.PaddleMoveCommand;
import Command.PaddleUndoMoveCommand;
import Utility.ChangeSize;
import Utility.Constants;
import Utility.GameClock;
import Utility.GameState;
import Utility.SaveState;
import Utility.enums;
import Utility.enums.Layouts;
import Utility.enums.Sounds;
import org.apache.log4j.Logger;



public class GameScene implements ClockObserver, Savable, ActionListener, Resizable
{
	private static Logger logger = Logger.getLogger(GameScene.class);
	private static AudioClip clip;
	private static enums.Sounds currentSound; 
	public static AudioClip bounce;
	public static AudioClip brickHit;
	public static AudioClip applause;
	private ClockDisplay clockDisplay;
	private JButton undoButton;
	private JButton replayButton;
	private JButton startButton;
	private JButton resetButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton layoutButton;
	public JPanel buttonPanel;
	public Board board;
	private Thread gameThread;
	private JFrame frame;
	private enums.Layouts CurrentLayout;
	//not a good way to do 
	private SaveState saveState;
	public enums.Layouts getCurrentLayout() {
		return CurrentLayout;
	}
	public static enums.Sounds getCurrentSound() {
		return currentSound;
	}

	public static void setCurrentSound(enums.Sounds currentSound) {
		GameScene.currentSound = currentSound;
	}
	public static AudioClip getClip() {
		return clip;
	}

	public static void setClip(AudioClip clip) {
		GameScene.clip = clip;
	}

	public GameScene(int frameWidth, int frameHeight){
		frame=new JFrame();
	
		String relative="/Sounds/";
		String soundFile = null;
	    
	    
	    soundFile="bounce.au";
	    soundFile=relative+soundFile;
    	bounce=Applet.newAudioClip( GameScene.class.getResource(soundFile));

    	soundFile="Brick.au";
    	soundFile=relative+soundFile;
    	brickHit=Applet.newAudioClip( GameScene.class.getResource(soundFile));
    	
    	soundFile="applause.wav";
    	soundFile=relative+soundFile;
    	applause=Applet.newAudioClip( GameScene.class.getResource(soundFile));
	
		
		Dimension gameSceneSize = frame.getSize();
		 //board = new Board(Constants.BOARD_LENGTH,gameSceneSize.height);
		board = new Board(Constants.BOARD_WIDTH,Constants.BOARD_LENGTH);
		board.setLocation(Constants.BOARD_X,Constants.BOARD_Y);
		CurrentLayout=Layouts.FLOW;
		
		clockDisplay = new ClockDisplay(Constants.CLOCK_WIDTH, Constants.CLOCK_HEIGHT);
		clockDisplay.jpanel.setLocation(Constants.CLOCK_X,Constants.CLOCK_Y);
		//frame.add(clockDisplay.jpanel);
		
		buttonPanel = new JPanel();
		//buttonPanel.setLocation(850, 250);
		buttonPanel.setSize(Constants.BUTTON_PANEL_WIDTH, Constants.BUTTON_PANEL_LENGTH);
		buttonPanel.setBackground(new Color(Constants.BG_CONTENT_R,Constants.BG_CONTENT_G,Constants.BG_CONTENT_B));
		
		
		GameClock gameClock = GameClock.getGlobalClock();
		gameClock.setGameState(GameState.INITIAL);
		gameClock.addObserver(clockDisplay);
		gameClock.addObserver(board);
		gameClock.addObserver(this);
		gameThread = new Thread(gameClock);
		
		
				
		startButton = new JButton();
		startButton.setText("Start");
		startButton.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
			buttonPanel.add(startButton);
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				
				String actionCommandString = event.getActionCommand();
				
				if((actionCommandString.equalsIgnoreCase("Start")) || 
						(actionCommandString.equalsIgnoreCase("Resume"))){
					GameClock.getGlobalClock().setGameState(GameState.RUNNING);
				}
				else if(actionCommandString.equalsIgnoreCase("Pause")){
					GameClock.getGlobalClock().setGameState(GameState.PAUSED);
				}
			}
		});
		
		
		undoButton = new JButton();
		undoButton.setText("Undo");
		undoButton.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
			buttonPanel.add(undoButton);
		undoButton.setEnabled(false);

		
		undoButton.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				logger.debug("Mouse pressed");
				//GameClock.getGlobalClock().setUndoButtonPressed(true);
				GameClock.getGlobalClock().setGameState(GameState.UNDOING);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				logger.debug("Mouse released");
				//GameClock.getGlobalClock().setUndoButtonPressed(false);
				GameClock.getGlobalClock().setGameState(GameState.PAUSED);
				
			}
			
		});
		
		
		replayButton = new JButton();
		replayButton.setText("Replay");
		replayButton.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
			buttonPanel.add(replayButton);
		replayButton.setEnabled(false);
		replayButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				GameClock.getGlobalClock().setGameState(GameState.REPLAY);
			}
			
		});
		
		resetButton = new JButton();
		resetButton.setText("Reset");
		resetButton.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
		buttonPanel.add(resetButton);
		resetButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				GameClock.getGlobalClock().setGameState(GameState.INITIAL);
			}
			
		});
	layoutButton = new JButton();
		layoutButton.setText("Toggle Layout");
layoutButton.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);

buttonPanel.add(layoutButton);
				
		saveButton = new JButton();
		saveButton.setText("Save");
		saveButton.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
		
		buttonPanel.add(saveButton);

	layoutButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				//GameClock.getGlobalClock().setGameState(GameState.INITIAL);
				doToggleLayout();
			}
			
		});		
		saveButton.setEnabled(true);
		try {
			saveState  = new SaveState(this);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				try {
					saveState.save();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		loadButton = new JButton();
		loadButton.setText("Load");
		loadButton.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
		
		buttonPanel.add(loadButton);		
		loadButton.setEnabled(true);
		try {
			saveState  = new SaveState(this);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		loadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				saveState.load();
			}			
		});
		
	frame.getContentPane().setBackground(new Color(Constants.BG_CONTENT_R,Constants.BG_CONTENT_G,Constants.BG_CONTENT_B));
		//frame.setLayout(new GridLayout(1, 3));
		/*frame.setLayout(new BorderLayout());
		frame.getContentPane().add(board,BorderLayout.CENTER);
		frame.getContentPane().add(clockDisplay.jpanel,BorderLayout.EAST);
		frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);*/
		
		//frame.setLayout();
		frame.getContentPane().add(board);
		frame.getContentPane().add(clockDisplay.jpanel);
		frame.getContentPane().add(buttonPanel);
		
		frame.setMinimumSize(new Dimension(250,200));
		frame.setMaximumSize(new Dimension(1500,1200));
		frame.setSize(frameWidth, frameHeight);
		frame.setLocationRelativeTo(null);
		frame.setTitle("BREAKOUT");
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
		//frame.getContentPane().setBackground(new Color(Constants.BG_CONTENT_R,Constants.BG_CONTENT_G,Constants.BG_CONTENT_B));
		frame.setLayout(null);
		frame.addComponentListener(new ComponentAdapter() 
		{
            public void componentResized(ComponentEvent e) 
            {
            	GameClock.getGlobalClock().setChangeSize(ChangeSize.RESIZE);
            	
            	Resize(frame.getWidth(), frame.getHeight());
            }
        });
		
	
	}

	@Override
	public void update(GameClock clock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(GameState state) {
		if(state == GameState.PAUSED){
			startButton.setText("Resume");	
			undoButton.setEnabled(true);
			replayButton.setEnabled(true);
		}
		else if(state == GameState.RUNNING){
			if((GameClock.getGlobalClock().getGameState() == GameState.INITIAL) ||
					!gameThread.isAlive()){
				gameThread.start();
				
				PaddleMoveCommand paddleMoveCommand = new PaddleMoveCommand(board.getPaddle(), board);
				HashMap<String,Object> map = new HashMap<String,Object>();
				int y= board.getPaddle().getyCoordinate();
				map.put("yCoordinate",y);
				paddleMoveCommand.execute(map);
				GameClock.getGlobalClock().getCommandList().add(paddleMoveCommand);
			}
			startButton.setText("Pause");
		}
		else if(state == GameState.UNDOING){
			replayButton.setEnabled(false);
			startButton.setText("Pause");
		}
		else if(state == GameState.REPLAY){
			undoButton.setEnabled(false);
			startButton.setText("Pause");
		}
		else if(state == GameState.INITIAL){
			startButton.setText("Start");	
			undoButton.setEnabled(false);
			replayButton.setEnabled(false);
		}
		else if(state == GameState.STOPPED){
			startButton.setText("Start");	
		}
		
	}

	@Override
	public void save(Document doc, Element parentElem) {
		Element elem = doc.createElement("GameScene");
		parentElem.appendChild(elem);
		board.save(doc, elem);			
	}

	@Override
	public void load(Document doc) {
		board.load(doc);		
	}

@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public Board getBoard()
	{
		return board;
	}

	 public void doToggleLayout(){
	    	if(CurrentLayout==Layouts.FLOW){
	    		Strategy.Context context=new Strategy.Context(new Strategy.ConcreteStrategyBox(),this);		 
	    	    context.executeStrategy();
	    	    CurrentLayout=Layouts.BOX;
	    		logger.debug(this.buttonPanel.getLayout().getClass());
	    		this.getBoard().refreshView();
	    		
	    	}
	    	else
	    		if(CurrentLayout==Layouts.BOX){
	    			Strategy.Context context=new Strategy.Context(new Strategy.ConcreteStrategyFlow(),this);		 
		    	    context.executeStrategy();
		    	    CurrentLayout=Layouts.FLOW;
		    	    this.getBoard().refreshView();
	    		}			
	    }

	 
	 
	 public static void playSound(Utility.enums.Sounds sound )
     {
	 	
		if(sound==Sounds.BrickBlow)
		{	
			currentSound=sound;
			setClip(brickHit);
		}
	    	if(sound==Sounds.PaddleHit)
	    {	
	    	currentSound=sound;
	    	setClip(bounce);
	    }
	    	if(sound==Sounds.Win)
	    	{
	    		currentSound=sound;
	    		setClip(applause);
	    	}
	    		
	    	
 	    try {
 	    
 	    	getClip().play();
       }
       // return null if soundFile does not exist
 	        catch ( NullPointerException nullPointerException ) {
       }
    }
	@Override
	public void Resize(int framewidth, int frameheight) //implementing the Resizable interface
	{
		// TODO Auto-generated method stub
		int widthdiff = framewidth - Constants.FRAME_WIDTH;
		int heightdiff = frameheight - Constants.FRAME_HEIGHT;
		
		buttonPanel.setSize(Constants.BUTTON_PANEL_WIDTH + (int)(widthdiff*0.6), Constants.BUTTON_PANEL_LENGTH + (int)((heightdiff*2)/7));
		if(GameClock.getGlobalClock().getChangeSize() == ChangeSize.RESIZE)
		{
			board.setLocation(Constants.BOARD_X + (int)((widthdiff*12)/100), Constants.BOARD_Y + (int)((heightdiff*1)/5));
			board.Resize(frame.getWidth(),frame.getHeight());
			clockDisplay.jpanel.setLocation(Constants.CLOCK_X + (int)((widthdiff*82)/100), Constants.CLOCK_Y + (int)((heightdiff*1)/5));
			clockDisplay.Resize(frame.getWidth(), frame.getHeight());
			GameClock.getGlobalClock().setChangeSize(ChangeSize.NORMAL);
		}
	}

}

