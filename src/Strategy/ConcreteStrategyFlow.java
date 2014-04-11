package Strategy;

import java.awt.FlowLayout;

import customViews.GameScene;
import interfaces.Strategy;

public class ConcreteStrategyFlow implements Strategy {

	
	@Override
	public int execute(GameScene gameScene) {
		gameScene.buttonPanel.setLayout(new FlowLayout());
		gameScene.buttonPanel.revalidate();
		return 0;
	}

	

}
