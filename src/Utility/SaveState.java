package Utility;
import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Command.CommandArrayContainer;

import customViews.GameScene;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class SaveState {
	Document doc;
	GameScene gameScene;
	
	public SaveState(GameScene gameScene) throws ParserConfigurationException
	{
/////////////////////////////
//Creating an empty XML Document

//We need a Document
		this.gameScene=gameScene;
	}
	
	public void save() throws ParserConfigurationException
	{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
  	    doc = docBuilder.newDocument();
		Element root = doc.createElement("Game");
		doc.appendChild(root);
		gameScene.save(doc,root);
		
		CommandArrayContainer arrayContainer = new CommandArrayContainer(GameClock.getGlobalClock().getCommandList());
		arrayContainer.save(doc,root, -1);
		// This  writes a DOM document to a file
		
		try {
		        // Prepare the DOM document for writing
		        Source source = new DOMSource(doc);

		        // Prepare the output file
		        File file = new File("game.xml");
		        Result result = new StreamResult(file);

		        // Write the DOM document to the file
		        Transformer xformer = TransformerFactory.newInstance().newTransformer();
		        xformer.transform(source, result);
		    } catch (TransformerConfigurationException e) {}
		     catch (TransformerException e) {}		    
		
	      }
	
	public void load()
	{
		DocumentBuilderFactory builderFactory =
		        DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
		    builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();  
		}
		
		try {
		      doc = builder.parse(
		            new FileInputStream("game.xml"));
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		gameScene.load(doc);
		CommandArrayContainer arrayContainer = new CommandArrayContainer(GameClock.getGlobalClock().getCommandList());
		arrayContainer.setGameScene(gameScene);
		arrayContainer.load(doc);	
		GameClock.getGlobalClock().setGameState(GameState.RUNNING);
	}
	
}
