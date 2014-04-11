package customViews;

import interfaces.ClockObserver;
import interfaces.Resizable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Utility.Constants;
import Utility.GameClock;
import Utility.GameState;

public class ClockDisplay implements ClockObserver, Resizable
{
	
	/**
	 * 
	 */
	JPanel jpanel=new JPanel();
	private JLabel timeLabel; 
	
	public ClockDisplay(int frameWidth, int frameHeight){
		initComponents(frameWidth,frameHeight);
	}
	
	private void initComponents(int frameWidth, int frameHeight){
		jpanel.removeAll();
		jpanel.setBackground(new Color(160,180,140));
		jpanel.setSize(new Dimension(frameWidth,frameHeight));
		jpanel.setLayout(null);
		
		String timeString = "00:00:00";
		timeLabel = new JLabel(timeString,JLabel.CENTER);
		timeLabel.setSize(frameWidth, frameHeight);
		timeLabel.setForeground(Color.blue);
		jpanel.add(timeLabel);
		
	}

	@Override
	public void update(GameClock clock) {
		String hour=(clock.getHour()<=9?("0"+clock.getHour()):clock.getHour()).toString();
		String minute= (clock.getMinute()<=9?("0"+clock.getMinute()):clock.getMinute()).toString();
		String second= (clock.getSecond()<=9?("0"+clock.getSecond()):clock.getSecond()).toString();
		timeLabel.setText(hour+":"+minute+":"+second);
	}
	
	@Override
	public void stateChanged(GameState state){
		
	}

	@Override
	public void Resize(int framewidth, int frameheight) 
	{
		// TODO Auto-generated method stub
		int widthdiff = framewidth - Constants.FRAME_WIDTH;
		int heightdiff = frameheight - Constants.FRAME_HEIGHT;
		initComponents(Constants.CLOCK_WIDTH + (int)((widthdiff*1)/10), Constants.CLOCK_HEIGHT + (int)((heightdiff*5)/70));
		
	}

}

