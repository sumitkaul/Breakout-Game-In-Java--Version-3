package test.Utility;
import Utility.GameClock;
import Utility.GameState;
import Utility.SaveState;
import static org.junit.Assert.*; 
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import breakout.Breakout;

public class SaveStateTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {		
        customViews.GameScene gameScene = new customViews.GameScene(1000,700);
        Breakout.gameScene = gameScene;
        Utility.SaveState saveObj;
        int paddleyBefore = gameScene.getBoard().getPaddle().getyCoordinate();
		try {
			saveObj = new SaveState(gameScene);
			//saveObj.load();
			
			saveObj.save();
			saveObj.load();
			GameClock.getGlobalClock().setGameState(GameState.PAUSED);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		
        int paddleyAfter = gameScene.getBoard().getPaddle().getyCoordinate();
        
		assertEquals("Results",paddleyBefore, paddleyAfter );
	}

}
