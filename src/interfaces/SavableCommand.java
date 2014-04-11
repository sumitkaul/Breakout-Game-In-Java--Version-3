package interfaces;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface SavableCommand extends Savable {
	public void save(Document doc, Element parentElement, int index );
}
