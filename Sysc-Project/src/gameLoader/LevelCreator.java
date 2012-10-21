package gameLoader;

import gameCore.Room;
import gameCore.Tile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.awt.Point;
import java.io.*;
import java.util.List;
import java.util.Set;

/**
* loads a level from an XML file and instantiates a level
*
* @author Group D
* @author Main Author: Trang Pham
*
* Group D Members
* ---------------
* Karen Madore
* Trang Pham
* Darrell Penner
*
*
* @version 1.0
*
*/

public class LevelCreator {
	
	private Level level;
	private boolean loaded;
	
	public LevelCreator()
	{
		level = new Level();
		loaded = false;
	}
	
	public Level getLevel() { if(loaded)return level; return null; }
	/**
	 * Loads a level from an xml file: parses the xml and instantiates the level
	 * fields
	 * 
	 * @param xmlFilePath the file path to the xml file that holds the level data
	 * @return true if the level is successfully loaded
	 */
	public boolean loadLevel(String xmlFilePath)
	{
		try
		{
			File xmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			Node root = doc.getDocumentElement();
			if(root.getNodeName().equals(XmlTag.LEVEL.toString()))
			{
				if(parseTimer(doc))
					if(parseGrid(doc))
						if(parseRooms(doc))
							if(parseExits(doc))
							{
								loaded = true;
								return true;
							}
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return false;
	}
	/**
	 * Loads the timer value from doc
	 * 
	 * @param doc The xml document that should contain timer value
	 * @return true if timer value was successfully loaded
	 */
	private boolean parseTimer(Document doc)
	{
		//get all nodes with tag timer
		NodeList nodes = doc.getElementsByTagName(XmlTag.TIMER.toString());
		//only care about the first timer set (ignore all other timer elements)
		String timerValue = getAttributeValueWithName(nodes.item(0), XmlTag.VALUE);
		if(timerValue !=null)
		{
			level.setTimer(Integer.parseInt(timerValue));
			return true;
		}
		return false;
	}
	/**
	 * Loads the grid width and height from doc
	 * 
	 * @param doc The xml document that should contain grid width and height
	 * @return true if grid height and width successfully loaded
	 */
	private boolean parseGrid(Document doc)
	{
		NodeList nodes = doc.getElementsByTagName(XmlTag.GRID.toString());
		//only care about the first timer set (ignore all other timer elements)
		String gridWidth= getAttributeValueWithName(nodes.item(0), XmlTag.WIDTH);
		String gridHeight= getAttributeValueWithName(nodes.item(0), XmlTag.HEIGHT);
		if(gridWidth !=null && gridHeight != null)
		{
			level.setLevelSize(Integer.parseInt(gridWidth), Integer.parseInt(gridHeight));
			return true;
		}
		return false;
	}
	/**
	 * Loads and instantiates the rooms and tiles along with any items and characters that are
	 * held inside the tiles
	 * 
	 * @param doc The xml document that should the elevator room (the room that the game starts in)
	 * @return true if the elevator room was loaded
	 */
	private boolean parseRooms(Document doc)
	{
		int x, y;
		boolean rv = false;
		NodeList nodes = doc.getElementsByTagName(XmlTag.ROOM_SECTION.toString());
		NodeList rooms = nodes.item(0).getChildNodes();
		for(int room_num = 0; room_num < rooms.getLength(); room_num++)
		{
			NodeList tiles = rooms.item(room_num).getChildNodes();
			Room r = new Room();
			level.addRoom(r);
			//is elevator room?
			String roomType = getAttributeValueWithName(rooms.item(room_num), XmlTag.TYPE);
			if(roomType.equals(XmlTag.ELEVATOR.toString()))
			{
				//set the room as an elevator
				level.setElevator(r);
				rv = true; 
			}
			for(int tile_num = 0; tile_num < tiles.getLength(); tile_num++)
			{
				x = Integer.parseInt(getAttributeValueWithName(tiles.item(tile_num), XmlTag.X));
				y = Integer.parseInt(getAttributeValueWithName(tiles.item(tile_num), XmlTag.Y));

				Tile t = new Tile(new Point(x, y), r);
				if(level.setTile(x, y, t))
				{
					parseInventory(tiles.item(tile_num), t);
					//hold character?
					parseCharacter(tiles.item(tile_num), t);
				}
				
			}
		}
		return rv;
	}
	private boolean parseExits(Document doc)
	{
		NodeList nodes = doc.getElementsByTagName(XmlTag.EXIT_SECTION.toString());
		NodeList exits = nodes.item(0).getChildNodes();
		for(int exit_num = 0; exit_num < exits.getLength(); exit_num++)
		{
			//At this point all the tiles have been made already
		}
		return false;
	}
	private boolean parseInventory(Node tileNode, Tile tile)
	{
		return false;
	}
	private boolean parseCharacter(Node tileNode, Tile tile)
	{
		return false;
	}
	private String getAttributeValueWithName(Node node, XmlTag name)
	{
		NamedNodeMap attr = node.getAttributes();
		if(attr != null)
		{
			Node attrNode = attr.getNamedItem(name.toString());
			if(attrNode != null)
			{
				return 	attrNode.getNodeValue();
			}
		}
		return null;
	}
}

