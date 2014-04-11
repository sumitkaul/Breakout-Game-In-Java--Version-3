package Strategy;

import customViews.GameScene;


public class Context {
	private interfaces.Strategy strategy;
    private GameScene gameScene;
 
    // Constructor
    public Context(interfaces.Strategy strategy, GameScene gameScene) {
        this.strategy = strategy;
        this.gameScene=gameScene;
    }
 
    public void executeStrategy() {
    	strategy.execute(gameScene);
    }
}
