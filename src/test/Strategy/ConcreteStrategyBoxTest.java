package test.Strategy;
import javax.swing.BoxLayout;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Strategy.ConcreteStrategyBox;


public class ConcreteStrategyBoxTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		ConcreteStrategyBox tester=new ConcreteStrategyBox();
		customViews.GameScene  layoutExecuteTester=new customViews.GameScene(1000,700);

		BoxLayout box=new BoxLayout(layoutExecuteTester.buttonPanel,BoxLayout.Y_AXIS);
		tester.execute(layoutExecuteTester);
		assertEquals("Result","class javax.swing.BoxLayout",layoutExecuteTester.buttonPanel.getLayout().getClass().toString());
	}

}
