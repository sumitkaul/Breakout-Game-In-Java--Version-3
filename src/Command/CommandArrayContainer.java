/**
 * 
 */
package Command;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import Utility.Direction;
import Utility.GameClock;

import customViews.Board;
import customViews.Brick;
import customViews.GameScene;

import interfaces.Command;
import interfaces.Savable;
import interfaces.SavableCommand;

/**
 * @author GOD
 *
 */
public class CommandArrayContainer implements SavableCommand {

	ArrayList<Command> cmdArray;
	GameScene gameScene;
	/* (non-Javadoc)
	 * @see interfaces.Savable#save(org.w3c.dom.Document, org.w3c.dom.Element)
	 */
	
	public CommandArrayContainer(ArrayList<Command> cmdArray)
	{
		this.cmdArray = cmdArray;
	}
	@Override
	public void save(Document doc, Element parentElement, int index) 
	{
		Element elem = doc.createElement("CommandArray");
		parentElement.appendChild(elem);
		for(int i=0; i < cmdArray.size(); i++)
		{
		    SavableCommand savable = (SavableCommand) cmdArray.get(i);
		    savable.save(doc, elem , i);
		}

	}
	
	@Override
	public void save(Document doc, Element parentElement) 
	{

	}

	/* (non-Javadoc)
	 * @see interfaces.Savable#load(org.w3c.dom.Document)
	 */
	@Override
	public void load(Document doc) {
		// TODO Auto-generated method stub
		cmdArray.clear();
		Element elem = (Element)doc.getElementsByTagName("CommandArray").item(0);
		boolean flag =true ; int index =0;
		while(flag)
		{
		    NodeList nodeList = elem.getElementsByTagName("Index"+index);
		    if(nodeList.getLength() == 0)
		    { flag = false; break;}

            String type = nodeList.item(0).getAttributes().getNamedItem("Type").getNodeValue();
            if(type.equals("MoveForwardCommand"))
            {
            	MoveForwardCommand mfc =  new MoveForwardCommand(gameScene.getBoard().getBall(), gameScene.getBoard());
            	NamedNodeMap map= nodeList.item(0).getAttributes();
            	mfc.setHour(Integer.valueOf(map.getNamedItem("hour").getNodeValue()));
            	mfc.setMinute(Integer.valueOf(map.getNamedItem("minute").getNodeValue()));
            	mfc.setSecond(Integer.valueOf(map.getNamedItem("second").getNodeValue()));
            	
            	mfc.setCurrentY(Integer.valueOf(map.getNamedItem("currentY").getNodeValue()));
            	mfc.setCurrentX(Integer.valueOf(map.getNamedItem("currentX").getNodeValue()));
            	mfc.setxDir( Direction.valueOf(Direction.class,map.getNamedItem("xDir").getNodeValue()));
            	mfc.setyDir( Direction.valueOf(Direction.class,map.getNamedItem("yDir").getNodeValue()));
            	cmdArray.add(mfc);
            }
            else if (type.equals("PaddleMoveCommand"))
            { 
            	PaddleMoveCommand pmc = new PaddleMoveCommand(gameScene.getBoard().getPaddle(),gameScene.getBoard());
            	NamedNodeMap map= nodeList.item(0).getAttributes();
            	pmc.setHour(Integer.valueOf(map.getNamedItem("hour").getNodeValue()));
            	pmc.setMinute(Integer.valueOf(map.getNamedItem("minute").getNodeValue()));
            	pmc.setSecond(Integer.valueOf(map.getNamedItem("second").getNodeValue()));
            	pmc.setyCoordinate(Integer.valueOf(map.getNamedItem("yCoordinate").getNodeValue()));
            	cmdArray.add(pmc);
            }
            else if (type.equals("BrickBlowCommand")){
            	//BrickBlowCommand brickBlowCommand = new BrickBlowCommand(brick[i], this);
            	//search for the brick in the array for the x and y values
            	NamedNodeMap map= nodeList.item(0).getAttributes();
            	int x = Integer.valueOf(map.getNamedItem("BrickX").getNodeValue());
            	int y = Integer.valueOf(map.getNamedItem("BrickY").getNodeValue());
            	int numOfBricks = 6;
            	Brick bricks[] = gameScene.getBoard().getBricks() ;
            	//get the brick
            	int i = 0;
            	for(; i < numOfBricks ;++i )
            	{
            		if(bricks[i].getxCoordinate() == x && bricks[i].getyCoordinate() == y)
            		{
            			break;
            		}            		
            	}            	
            	
            	BrickBlowCommand bbc = new BrickBlowCommand(bricks[i], gameScene.getBoard());
            	bbc.setHour(Integer.valueOf(map.getNamedItem("hour").getNodeValue()));
            	bbc.setMinute(Integer.valueOf(map.getNamedItem("minute").getNodeValue()));
            	bbc.setSecond(Integer.valueOf(map.getNamedItem("second").getNodeValue()));
                bbc.getBrick().setHit(Boolean.valueOf(map.getNamedItem("isHit").getNodeValue()));
                cmdArray.add(bbc);
            }
            else if(type.equals("PlaySoundCommand"))
            {
            	NamedNodeMap map= nodeList.item(0).getAttributes();
            	Utility.enums.Sounds sound = Utility.enums.Sounds.valueOf(Utility.enums.Sounds.class,map.getNamedItem("sound").getNodeValue());
            	PlaySoundCommand psc= new PlaySoundCommand(sound);
            	psc.setHour(Integer.valueOf(map.getNamedItem("hour").getNodeValue()));
            	psc.setMinute(Integer.valueOf(map.getNamedItem("minute").getNodeValue()));
            	psc.setSecond(Integer.valueOf(map.getNamedItem("second").getNodeValue()));
            	cmdArray.add(psc);            	
            }
            index++;
		}
		
	// set the time in the timer
	 elem = (Element)doc.getElementsByTagName("CommandArray").item(0);
	 NodeList nodelist = elem.getElementsByTagName("Index"+(cmdArray.size() -1));
	 if(nodelist.getLength() != 0){
	 NamedNodeMap map= nodelist.item(0).getAttributes();
	 GameClock.getGlobalClock().setHour(Integer.valueOf(map.getNamedItem("hour").getNodeValue()));
	 GameClock.getGlobalClock().setMinute(Integer.valueOf(map.getNamedItem("minute").getNodeValue()));
	 GameClock.getGlobalClock().setSecond(Integer.valueOf(map.getNamedItem("second").getNodeValue()));
	 }
	}
	
	public void setCommandList(ArrayList<Command> cmdArray)
	{
		this.cmdArray = cmdArray;
	}
	
	public void setGameScene(GameScene gameScene)
	{
		this.gameScene = gameScene;
	}
}
