package gameCore;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.*;

/**
* A level holds the map and loads a level from an XML file
*
* @author Group D
* @author Main Author: Darrell Penner
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
public class Level {
	
	private int timer;
	private int gridWidth;
	private int gridHeight;
	
	/**
	 * Instantiates a level with the data in the xmlFilePath
	 * 
	 * @param xmlFilePath the file path to the xml file that holds the level data
	 */
	public Level(String xmlFilePath)
	{
		loadLevel(xmlFilePath);
	}
	
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
			if(root.getNodeName().equals("level"))
			{
				if(parseTimer(doc))
					if(parseGrid(doc))
						if(parseRooms(doc))
							if(parseExits(doc))
								return true;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return false;
	}
	
	private boolean parseTimer(Document doc)
	{
		//get all nodes with tag timer
		NodeList nodes = doc.getElementsByTagName("timer");
		//only care about the first timer set (ignore all other timer elements)
		String timerValue = getAttributeValueWithName(nodes.item(0), "value");
		if(timerValue !=null)
		{
			this.setTimer(Integer.parseInt(timerValue));
			return true;
		}
		return false;
	}
	private boolean parseGrid(Document doc)
	{
		NodeList nodes = doc.getElementsByTagName("grid");
		//only care about the first timer set (ignore all other timer elements)
		String gridWidth= getAttributeValueWithName(nodes.item(0), "width");
		String gridHeight= getAttributeValueWithName(nodes.item(0), "height");
		if(gridWidth !=null && gridHeight != null)
		{
			this.gridWidth = Integer.parseInt(gridWidth);
			this.gridHeight = Integer.parseInt(gridHeight);
			return true;
		}
		return false;
	}
	private boolean parseRooms(Document doc)
	{
		return false;
	}
	private boolean parseExits(Document doc)
	{
		return false;
	}
	private String getAttributeValueWithName(Node node, String name)
	{
		NamedNodeMap attr = node.getAttributes();
		if(attr != null)
		{
			Node attrNode = attr.getNamedItem(name);
			if(attrNode != null)
			{
				return 	attrNode.getNodeValue();
			}
		}
		return null;
	}


	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}
}
