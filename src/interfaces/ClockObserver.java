package interfaces;

import Utility.GameClock;
import Utility.GameState;

public interface ClockObserver {
	public void update(GameClock clock);
	public void stateChanged(GameState state);
}
