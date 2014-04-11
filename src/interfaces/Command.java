package interfaces;

import java.util.HashMap;

public interface Command {
	public void execute(HashMap<String,Object> map);
	public void redo();
}
