package gameLoader;

import gameCore.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.awt.Point;
import java.io.*;

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
	
	protected LevelCreator()
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
		Element timer = (Element) nodes.item(0);
		String timerValue = timer.getAttribute(XmlTag.VALUE.toString());
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
		Element gridNode = (Element) nodes.item(0);
		String gridWidth= gridNode.getAttribute(XmlTag.WIDTH.toString());
		String gridHeight= gridNode.getAttribute(XmlTag.HEIGHT.toString());
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
		NodeList nodes = doc.getElementsByTagName(XmlTag.ROOM_SECTION.toString());
		NodeList rooms = ((Element)nodes.item(0)).getElementsByTagName(XmlTag.ROOM.toString());
		for(int room_num = 0; room_num < rooms.getLength(); room_num++)
		{
			Element room = (Element) rooms.item(room_num);
			NodeList tiles = room.getElementsByTagName(XmlTag.TILE.toString());
			Room r = new Room();
			level.addRoom(r);
			//is elevator room?
			String roomType = room.getAttribute(XmlTag.TYPE.toString());
			Tile elevatorTile = null;
			for(int tile_num = 0; tile_num < tiles.getLength(); tile_num++)
			{
				Element tile = (Element) tiles.item(tile_num);
				x = Integer.parseInt(tile.getAttribute(XmlTag.X.toString()));
				y = Integer.parseInt(tile.getAttribute(XmlTag.Y.toString()));

				Tile t = new Tile(new Point(x, y), r);
				if(level.setTile(x, y, t))
				{
					if(tile_num == 0 && roomType.equalsIgnoreCase(XmlTag.ELEVATOR.toString()))
					{
						elevatorTile = t;
					}
					r.addTile(t);
					parseInventory(tile, t);
					//hold character?
					parseCharacter(tile, t);
					
				}	
			}
			if(elevatorTile != null)
			{
				//set the room as an elevator
				level.setElevator(r, elevatorTile);
				return true;
			}
		}
		return false;
	}
	private boolean parseExits(Document doc)
	{
		NodeList nodes = doc.getElementsByTagName(XmlTag.EXIT_SECTION.toString());
		NodeList exits = ((Element)nodes.item(0)).getElementsByTagName(XmlTag.EXIT.toString());
		for(int exit_num = 0; exit_num < exits.getLength(); exit_num++)
		{
			Item key = null;
			Element exit = (Element) exits.item(exit_num);
			//At this point all the tiles have been made already
			NodeList tiles = exit.getElementsByTagName(XmlTag.TILE.toString());
			//get tile1
			Element tile1 = (Element) tiles.item(0);
			int x1 = Integer.parseInt(tile1.getAttribute(XmlTag.X.toString()));
			int y1 = Integer.parseInt(tile1.getAttribute(XmlTag.Y.toString()));
			//get tile2
			Element tile2 = (Element) tiles.item(1);
			int x2 = Integer.parseInt(tile2.getAttribute(XmlTag.X.toString()));
			int y2 = Integer.parseInt(tile2.getAttribute(XmlTag.Y.toString()));
			
			//is it locked 
			String type = exit.getAttribute(XmlTag.TYPE.toString());
			if(type.equalsIgnoreCase(XmlTag.LOCKED.toString()))
			{
				//if so what is the key
				NodeList items = exit.getElementsByTagName(XmlTag.ITEM.toString());
				Element item = (Element) items.item(0);
				String keyname = item.getAttribute(XmlTag.NAME.toString());
				int keyweight = Integer.parseInt(item.getAttribute(XmlTag.WEIGHT.toString()));
				key = new Item(keyname, keyweight);
			}
			level.addEdge(level.getTile(x1,y1),level.getTile(x2,y2), key);
		}
		return true;
	}
	private boolean parseInventory(Element tileNode, Object obj)
	{
		//TODO: 
		if(obj instanceof Tile)
		{
			
		}
		return false;
	}
	private boolean parseCharacter(Element tileNode, Tile tile)
	{
		//TODO:
		return false;
	}
}

