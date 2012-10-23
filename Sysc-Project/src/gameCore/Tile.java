package gameCore;
import java.awt.Point;
import java.util.*;

/**
 * A Tile is the minimal unit in the game. They have a location and 
 * edges. It can contain items (in the form of an inventory) and a character.
 * A Tile can look at adjacent tiles, but no farther (relative to the public API anyways).
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
	private Point location;
	private Map<String, Edge> edges; //CONSIDER: String (direction) can be enum?
	private Inventory inventory;
	private Character character;	
	private Room containingRoom;
	
	//------------Constructors------------//
    /**
	 * Constructs a Tile from a point and room
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
	//Note: no getter for containingRoom since that would allow 
	//arbitrary access to other tiles in the room
	
	/**
	 * Gets the items on the current tile 
	 * @return The inventory on the tile
	 */
	public Inventory getInventory(){
		return inventory;
	}
	
	/**
	 * Gets the items on the adjacent tile in the given direction.
	 * @param direction The direction to look in.
	 * @return The inventory on the adjacent tile
	 */
	public Inventory getInventory(String direction){
		if (!canCrossEdge(direction)){
			throw new IllegalArgumentException("Cannot cross that edge!");
		}
		
		return getNextTile(direction).getInventory();
	}
	
	/**
	 * Gets the character on the current tile
	 * @return The character if one exists, null otherwise
	 */
	public Character getCharacter(){
		return character;
	}
	
	/**
	 * Gets the character on the adjacent tile in the given direction
	 * @param direction The direction to search for
	 * @return the Character on the adjacent tile, null if none exists 
	 */
	public Character getCharacter(String direction)
	{
		if (!canCrossEdge(direction)){
			throw new IllegalArgumentException("Cannot cross that edge!");
		}
		
		return getNextTile(direction).getCharacter();
	}
	
	/**
	 * The location of the tile
	 * @return A deep copy of the location on the tile.
	 */
	public Point getLocation(){
		return new Point(location); //deep copy, no modifying
	}

	/**
	 * Gets the edge in the given direction
	 * @param direction The direction in which to get the edge
	 * @return The edge in the given direction
	 */
	private Edge getEdge(String direction){
		if (!edges.containsKey(direction))
		{
			throw new IllegalArgumentException("Edge does not exist in that direction");
		}
		return edges.get(direction);
	}
	
	/**
	 * Gets the next tile in the specified direction. Throws if the edge between the two
	 * tiles cannot be crossed.
	 * 
	 * @param direction The direction of the next tile
	 * @return the Tile in the specified direction
	 */
	private Tile getNextTile(String direction){
		if (!canCrossEdge(direction))
		{
			throw new IllegalArgumentException("Tried get next tile, but edge between is not crossable");
		}
		return getEdge(direction).getOtherTile(this);
	}
	
	//------------Setters------------//
	/**
	 * Sets an edge of a tile
	 * @param direction The direction in which to set the edge
	 * @param edge The edge to be set
	 */
	public void setEdge(String direction, Edge edge){
		if (edges.containsKey(direction)){
			throw new IllegalArgumentException("This edge has already been set");
		}
		
		edges.put(direction, edge);
	}
	
	//------------Adding and Removing------------//
	/**
	 * Adds a character to the tile
	 * @param c the Character to be added
	 */
	public void addCharacter(Character c){
		if (hasCharacter()){
			throw new UnsupportedOperationException("Can't add character to tile - there already is one");
		}
		
		character = c;
	}
	
	/**
	 * Removes the Character on the tile
	 * @return the Character that was on the tile, null if none
	 */
	public Character removeCharacter(){
		Character removed = character;
		character = null;
		return removed;
	}
	
	/**
	 * Adds an item to the tile
	 * @param item the Item to be added
	 */
	public void addItem(Item item){
		inventory.addItem(item);
	}
	
	/**
	 * removes an Item from the tile
	 * @param the Item to be removed
	 */
	public void removeItem(Item item){
		inventory.removeItem(item);
	}
	
	//------------Character Movement------------//
	/**
	 * Checks whether the edge in the given direction is crossable by the character in the tile
	 * @param direction The direction of desired crossing
	 * @return True if the edge is crossable, false otherwise
	 * @throws UnsupportedOperationException When this method is called when there is no character standing on the tile
	 */
	private boolean canCrossEdge(String direction) throws UnsupportedOperationException {
		if (!hasCharacter()){
			throw new UnsupportedOperationException("There is no character on this tile!");
		}
		return getEdge(direction).canCross(character);
	}
	
	/**
	 * Can only move if capable of crossing the edge, and another character is not
	 * currently occupying the destination tile.
	 * @param direction The direction of possible movement 
	 * @return whether or not the character can move in that direction
	 */
	public boolean canMove(String direction){
		return canCrossEdge(direction) && !getNextTile(direction).hasCharacter();
	}

	public boolean isCrossable(String direction){
		return canCrossEdge(direction);
	}
	/**
	 * Moves the character to the tile in the given direction.
	 * @param direction The direction to move the player
	 * @throws IllegalArgumentException When the player cannot move in the given direction. Try calling canMove(direction) first.
	 * @return The new tile of the character
	 */
	public Tile moveCharacter(String direction) throws IllegalArgumentException {
		if (!canMove(direction)){
			throw new IllegalArgumentException("Cannot move in that direction, something is blocking the way!");
		}
		
		return getEdge(direction).cross(this, character); //cross and return the character's new Tile
	}
	
	//------------Checks------------//
	/**
	 * Checks if the tile has a Character on it
	 * @return True if the tile has a character, false otherwise
	 */
	public boolean hasCharacter(){
		return character != null;
	}
	
	/**
	 * Checks if the adjacent tile in the given direction has a Character
	 * @param direction The direction in which to check for a character
	 * @return True if the adjacent Tile has a Character, false otherwise
	 */
	public boolean hasCharacter(String direction)
	{
		return canCrossEdge(direction) && getNextTile(direction).hasCharacter();
	}
	
	/**
	 * Checks if the tile has any items
	 * @return True if the tile has items, false otherwise.
	 */
	public boolean hasItems(){
		return !inventory.isEmpty();
	}
	
	/**
	 * Checks if the tile is empty. A tile is considered empty if it has no Items an on Character
	 * @return True if the tile is empty, false otherwise
	 */
	public boolean isEmpty(){
		return !hasItems() && !hasCharacter();
	}
	
	/**
	 * Checks if the adjacent tile is empty. A tile is considered empty if it has no Items and no Character
	 * @param direction The direction in which to check
	 * @return True if the adjacent tile is empty, false otherwise
	 */
	public boolean isEmpty(String direction){
		return getNextTile(direction).isEmpty();
	}
	
	/**
	 * Checks whether there is an Exit in a given the direction
	 * @param direction The direction in which to check
	 * @return True if there is an Exit, false otherwise
	 */
	public boolean hasExit(String direction){
		return getEdge(direction) instanceof Exit;
	}
	
	/**
	 * Returns the String representation of the key needed to get across the Exit
	 * @param direction The direction in which to search
	 * @return The string representation of the needed key
	 */
	public String getExitKey(String direction){
		if (!hasExit(direction))
		{
			throw new IllegalArgumentException("No exit in that direction!");
		}
		return ((Exit)getEdge(direction)).getKeyName();
	}
	
	/* REMOVED - Character class should handle these
	public void attackCharacter(String direction) throws IllegalArgumentException{
		if (!hasCharacter(direction)){
			throw new IllegalArgumentException("Cannot attack in that direction");
		}
		
		//This tile's character attacks the next tile's character
		//character.attack(getNextTile(direction).getCharacter()); TEMP
		//TEMP check for death
	}
	
	//------------Item Handling------------//
	public boolean takeItemFromCharacter(Item i){ //TEMP
		return false;
	}
	public boolean giveItemToCharacter(Item i){ //TEMP
		return false;
	}
	
	//------------Looking------------//
	//Note that all "lookFor*object*" methods return a copy so that the original
	//cannot be modified
	public Character lookForCharacter(String direction){
		if (!hasCharacter(direction)){
			return null;
		}

		return null; //TEMP deep copy of character
	}
	
	public Inventory lookForItems(String direction){
		return null; //TEMP deep copy of inventory
	}*/
	
}
