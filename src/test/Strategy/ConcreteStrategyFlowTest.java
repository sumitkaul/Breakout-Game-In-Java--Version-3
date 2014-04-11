package test.Strategy;

import static org.junit.Assert.*;

import java.awt.FlowLayout;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Strategy.ConcreteStrategyFlow;

public class ConcreteStrategyFlowTest {

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
		ConcreteStrategyFlow tester=new ConcreteStrategyFlow();
		customViews.GameScene  layoutExecuteTester=new customViews.GameScene(1000,700);

		FlowLayout flow=new FlowLayout();
		tester.execute(layoutExecuteTester);
		assertEquals("Result","class java.awt.FlowLayout",layoutExecuteTester.buttonPanel.getLayout().getClass().toString());
	}

}
