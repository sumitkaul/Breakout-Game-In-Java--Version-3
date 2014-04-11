package interfaces;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Savable {
	
	public void save(Document doc, Element parentElement);
	public void load(Document doc);

}
