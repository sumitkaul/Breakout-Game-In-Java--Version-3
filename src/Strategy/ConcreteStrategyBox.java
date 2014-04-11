package Strategy;

import javax.swing.BoxLayout;

import customViews.GameScene;

import interfaces.Strategy;

public class ConcreteStrategyBox implements Strategy {

	@Override
	public int execute(GameScene gameScene) {
		BoxLayout boxLayout = new BoxLayout(gameScene.buttonPanel,BoxLayout.Y_AXIS);
		
		gameScene.buttonPanel.setLayout(boxLayout);
		gameScene.buttonPanel.revalidate();
		return 0;
	}

}
