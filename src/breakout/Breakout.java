
package breakout;

import Utility.Constants;
import customViews.GameScene;

public class Breakout  
{
	public static GameScene gameScene  ;
	public static void main(String[] args) 
	{
		gameScene= new GameScene(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT) ;
		//new GameScene(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
	}
}