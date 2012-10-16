package gameCore;
import java.awt.Point;
import java.util.*;

/**
 * A Tile is the minimal unit in the game. They have a location and 
 * edges. It can contain items (in the form of an inventory) and a character.
 * 
 * @author Group D
 * @author Main author: Darrell Penner
 * 
 * Group D Members
 * ---------------
 * Karen Madore
 * Trang Pham
 * Darrell Penner
 * 
 *
 * @version 1.0
 */

public class Tile {
	
	//------------Fields------------//
	private Point location;  //a unique identifier
	private Map<String, Edge> edges; //CONSIDER: String (direction) can be enum?
	private Inventory inventory;
	private Character character;
	private Room containingRoom;
	
	//------------Constructors------------//
    /**
	 * Constructs a Tile form a point and room
	 * @param Location of the Tile
	 */
	public Tile(Point location, Room containingRoom)
	{
		if (location == null || containingRoom == null){
			throw new IllegalArgumentException("A tile must have a location and a containing room");
		}
		
		edges = new HashMap<String, Edge>();
		inventory = new Inventory(); //start empty, avoids null pointers
		character = null; //no character
		
		this.location = location;
		this.containingRoom = containingRoom;
	}
	
	//------------Getters------------//
	/**
	 * 
	 * @return the items on the tile
	 */
	public Inventory getInventory(){
		return inventory;
	}
	public Character getCharacter(){
		return character;
	}
	public Point getLocation(){
		return location;
	}
	public Room getContainingRoom(){
		return containingRoom;
	}

	public Edge getEdge(String direction){
		if (!edges.containsKey(direction))
		{
			throw new IllegalArgumentException("Edge does not exist");
		}
		return edges.get(direction);
	}
	
	public Tile getNextTile(String direction){
		if (!canMove(direction))
		{
			throw new IllegalStateException("Tried get next tile, but edge between is not crossable");
		}
		return getEdge(direction).getOtherTile(this);
	}
	
	//------------Setters------------//
	public void setEdge(String direction, Edge edge){
		edges.put(direction, edge);
	}
	
	//------------Adding and Removing------------//
	/**
	 * Adds a character to the tile
	 * @param c the Character to be added
	 */
	public void addCharacter(Character c){
		if (character != null){
			throw new IllegalStateException("There's already a character in this tile");
		}
		
		character = c;
	}
	
	/**
	 * Removes the Character on the tile
	 * @return the Character that was on the tile, null if none
	 */
	public Character removeCharacter(){
		Character retval = character;
		character = null;
		return retval;
	}
	public void addItem(Inventory item){ //TEMP
		//inventory.add(item);
	}
	
	/*public Item removeItem(){ //TEMP
		if (inventory.isEmpty()){
			throw new IllegalStateException("No items to remove!");
		}
		return inventory.get(0); //doesn't actually remove yet
	}*/
	
	//------------Miscellaneous------------//
	public boolean isEmpty(){ //TEMP
		return /*inventory.isEmpty() && */ character == null; 
	}
	
	public boolean canMove(String direction){
		return getEdge(direction).canCross();
	}
}
