package test.customViews;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Utility.enums.Layouts;
import Utility.enums.Sounds;
public class GameSceneTest {
	
	
	//private enums.Layouts CurrentLayout;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//CurrentLayout=Layouts.FLOW;
	}

	@After
	public void tearDown() throws Exception {
	}

	/*@Test
	public void testStateChanged() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testDoToggleLayout() {
		//fail("Not yet implemented");
		
		customViews.GameScene  layoutTester=new customViews.GameScene(1000,700);
		layoutTester.doToggleLayout();
		assertEquals("Result",Layouts.BOX,layoutTester.getCurrentLayout());
		layoutTester.doToggleLayout();
		assertEquals("Result",Layouts.FLOW,layoutTester.getCurrentLayout());
	}
	
	@Test
	public void testPlaySound() {
		//fail("Not yet implemented");
		//customViews.GameScene  soundTester=new customViews.GameScene(1000,700);
		
		customViews.GameScene.playSound(Sounds.BrickBlow);
		assertEquals("Result",Sounds.BrickBlow,customViews.GameScene.getCurrentSound());
		customViews.GameScene.playSound(Sounds.PaddleHit);
		assertEquals("Result",Sounds.PaddleHit,customViews.GameScene.getCurrentSound());
		customViews.GameScene.playSound(Sounds.Win);
		assertEquals("Result",Sounds.Win,customViews.GameScene.getCurrentSound());
	}

}