package gameCore;
import java.awt.Point;
import java.io.Serializable;
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

public class Tile extends LayoutObject implements Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//------------Fields------------//
	private Point location;
	private Map<Direction, Edge> edges;
	private Inventory inventory;
	private Character character;	
	private Room containingRoom;
	private boolean visited; //A tile is visited if its room has been visited
	
	//------------Constructors------------//
   /**
    * Constructs a Tile from a Point and Room.
    * 
    * @param location The location of the tile
    * @param containingRoom The room containing the tile
    * @exception IllegalArgumentException if either parameter is null.
    */
	public Tile(Point location, Room containingRoom){
		if (location == null || containingRoom == null){
			throw new IllegalArgumentException("A tile must have a location and a containing room");
		}
		
		edges = new HashMap<Direction, Edge>();
		inventory = new Inventory(); //start empty, avoids null pointers
		character = null; //no character
		
		this.location = location;
		this.containingRoom = containingRoom;
	}
	
	//------------Getters------------//
	//Note: no getter for containingRoom since that would allow 
	//arbitrary access to other tiles in the room
	
	/**
	 * Gets the items on the current tile.
	 * @return The inventory on the tile
	 */
	public Inventory getInventory(){
		return inventory;
	}
	
	/**
	 * Gets the items on the adjacent tile in the given direction. The character must be able
	 * to move to the tile in that direction.
	 * 
	 * @param direction The direction to look in.
	 * @return The inventory on the adjacent tile
	 * @exception IllegalArgumentException if the character on this tile cannot move to the tile in the given direction
	 */
	public Inventory getInventory(Direction direction){
		checkDirection(direction);
		if (!canMove(direction)){
			throw new IllegalArgumentException("Cannot cross that edge!");
		}
		
		return getNextTile(direction).getInventory();
	}
	
	/**
	 * Gets the character on this tile.
	 * @return The character if one exists, null otherwise
	 */
	public Character getCharacter(){
		return character;
	}
	
	/**
	 * Gets the character on the adjacent tile in the given direction.
	 * @param direction The direction to search for
	 * @return the Character on the adjacent tile, null if none exists 
	 * @exception IllegalArgumentException if the character on this tile cannot cross the edge in the given direction
	 */
	public Character getCharacter(Direction direction) {
		checkDirection(direction);
		if (!isCrossable(direction)){
			throw new IllegalArgumentException("Cannot cross that edge!");
		}
		
		return getNextTile(direction).getCharacter();
	}
	
	/**
	 * Gets the location of the tile.
	 * @return A deep copy of the location on the tile.
	 */
	public Point getLocation(){
		return new Point(location); //deep copy, no modifying
	}

	/**
	 * Gets the edge in the given direction.
	 * @param direction The direction in which to get the edge.
	 * @return The edge in the given direction.
	 * @exception if there is no Edge in that direction.
	 */
	private Edge getEdge(Direction direction) {
		checkDirection(direction);
		return edges.get(direction);
	}
	
	/**
	 * Gets the next tile in the specified direction.
	 * 
	 * @param direction The direction of the next tile.
	 * @return the Tile in the specified direction.
	 * @exception if there is no Tile in the specified direction.
	 */
	private Tile getNextTile(Direction direction) {
		checkDirection(direction);
		Tile nextTile = getEdge(direction).getOtherTile(this);
		if (nextTile == null){
			throw new IllegalArgumentException("No tile in that direction");
		}
		return nextTile;
	}
	
	/**
	 * Gets the String representation of the key needed to get across the Exit.
	 * @param direction The direction in which to search.
	 * @return The string representation of the needed key.
	 * @exception IllegalArgumentException if there is no Exit in the given direction.
	 */
	public String getExitKey(Direction direction) {
		checkDirection(direction);
		if (!hasExit(direction))
		{
			throw new IllegalArgumentException("No exit in that direction!");
		}
		return ((Exit)getEdge(direction)).getKeyName();
	}
	
	public Room getRoom()
	{
		return containingRoom;
	}
	
	//------------Setters------------//
	/**
	 * Sets an edge of a tile.
	 * @param direction The direction in which to set the edge
	 * @param edge The edge to be set
	 * @exception IllegalArgumentException if the edge has already been set. This is to prevent an erroneous state.
	 */
	public void setEdge(Direction direction, Edge edge) {
		if (hasDirection(direction)){
			throw new IllegalArgumentException("This edge has already been set");
		}
		if (edge == null){
			throw new IllegalArgumentException("The edge may not be null");
		}
		
		edges.put(direction, edge);
	}
	
	/**
	 * Sets the tile as visited.
	 */
	public void setVisited(){
		visited = true;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Sets the containing room as visited.
	 */
	private void setRoomAsVisited(){
		containingRoom.setVisited();
	}
	
	//------------Adding and Removing------------//
	/**
	 * Adds a character to the tile.
	 * @param c the Character to be added
	 * @exception UnsupportedOperationException if there is already a Character on this Tile
	 */
	public void addCharacter(Character c){
		if (hasCharacter()){
			throw new UnsupportedOperationException("Can't add character to tile - there already is one");
		}
		
		if (c instanceof Player){
			setRoomAsVisited();
		}
		character = c;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Removes the Character on the tile.
	 * @return the Character that was on the tile, null if none
	 */
	public Character removeCharacter(){
		Character removed = character;
		character = null;
		setChanged();
		notifyObservers();
		return removed;
	}
	
	/**
	 * Adds an item to the tile.
	 * @param item the Item to be added
	 */
	public void addItem(Item item){
		inventory.addItem(item);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Removes an Item from the tile.
	 * @param item item to remove
	 */
	public void removeItem(Item item){
		inventory.removeItem(item);
		setChanged();
		notifyObservers();
	}
	
	public void removeEdge(Direction direction){
		checkDirection(direction);
		Edge e = edges.remove(direction);
		e.disconnect(this);
	}
	
	//------------Character Movement------------//
	/**
	 * Checks whether the edge in the given direction is crossable by the character in the tile
	 * @param direction The direction of desired crossing
	 * @return True if the edge is crossable, false otherwise
	 */
	public boolean isCrossable(Direction direction) {
		return isCrossable(character, direction);
	}
	
	public boolean isCrossableByDefault(Direction direction){
		if (!hasDirection(direction)){
			return false;
		}
		return getEdge(direction).isCrossableByDefault(); 
	}
	/**
	 * Checks whether the edge is crossable in the given direction by the given Character
	 * @param c The character to check
	 * @param direction The direction to check
	 * @return True if the character can cross the edge, false otherwise
	 */
	private boolean isCrossable(Character c, Direction direction){
		if (!hasDirection(direction)){
			return false;
		}
		return getEdge(direction).canCross(c);
	}
	
	/**
	 * Checks if the character on this tile can move in the given direction. Movement is possible if the
	 * edge in between is crossable, and there is no character on the destination tile.
	 * @param direction The direction of possible movement.
	 * @return True if movement is possible in the given direction, false otherwise.
	 */
	public boolean canMove(Direction direction){
		return canMove(character, direction);
	}
	
	/**
	 * Checks if the given character can move in the given direction. Movement is possible if the
	 * edge in between is crossable, and there is no character on the destination tile.
	 * @param c The character to check.
	 * @param direction The direction to check.
	 * @return True if movement is possible for the character, false otherwise.
	 */
	private boolean canMove(Character c, Direction direction){
		if (!hasDirection(direction)){
			return false;
		}
		return isCrossable(c, direction) && !getNextTile(direction).hasCharacter();
	}
	
	/*
	 * This method will be public in future iteration, it is currently still under construction.
	 * @param destination
	 * @return
	 */
	/*private Direction getDirectionTowards(Tile destination)
	{	
		List<ShortestPathNode> path = getPath(destination);
		if (path == null || path.isEmpty()) {
			return null; //no path exists
		}
		
		return path.get(0).direction;
	}*/
	
	/**
	 * Moves the character to the tile in the given direction.
	 * @param direction The direction to move the player
	 * @exception IllegalArgumentException When the player cannot move in the given direction. Try calling canMove(direction) first.
	 * @return The new tile of the character
	 */
	public Tile moveCharacter(Direction direction) {
		checkDirection(direction);
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
	public boolean hasCharacter(Direction direction)
	{
		if (!hasDirection(direction)){
			return false;
		}
		return isCrossable(direction) && getNextTile(direction).hasCharacter();
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
	public boolean isEmpty(Direction direction){
		if (!hasDirection(direction)){
			return false;
		}
		return isCrossable(direction) && getNextTile(direction).isEmpty();
	}
	
	/**
	 * Checks whether there is an Exit in a given the direction.
	 * @param direction The direction in which to check.
	 * @return True if there is an Exit, false otherwise.
	 */
	public boolean hasExit(Direction direction){
		if (!hasDirection(direction)){
			return false;
		}
		return getEdge(direction) instanceof Exit;
	}
	
	/**
	 * Gets whether or not a tile is visited.
	 * @return True if the Tile is visited, false otherwise
	 */
	public boolean isVisited(){
		return visited;
	}
	
	//------------Direction handling------------//
	/**
	 * Validates the given direction for this Tile.
	 * @param direction The direction to validate
	 * @throws IllegalArgumentException if the direction does not exist for this Tile.
	 */
	private void checkDirection(Direction direction) throws IllegalArgumentException {
		//Runtime exception is advertised, since throwing is the point of this method
		if (!hasDirection(direction)){
			throw new IllegalArgumentException("That direction does not exist!");
		}
	}
	
	/**
	 * Checks if a Tile has an Edge in the given direction.
	 * @param direction The direction in which to check.
	 * @return True if the direction exists, false otherwise.
	 */
	public boolean hasDirection(Direction direction){
		return edges.containsKey(direction);
	}
	
	/**
	 * Gets all the possible directions for the Tile.
	 * @return The set of all possible directions.
	 */
	public Set<Direction> getAllDirections(){
		return edges.keySet();
	}
	
	/**
	 * Gets the direction of a particular Edge.
	 * @param edge The edge in which to retrieve the direction.
	 * @return The direction of the given edge.
	 * @exception IllegalArgumentException if the edge is not on this tile.
	 */
	public Direction getEdgeDirection(Edge edge) {		
		for (Map.Entry<Direction, Edge> e : edges.entrySet()){
			if (e.getValue() == edge){
				return e.getKey();
			}
		}
		
		throw new IllegalArgumentException("Edge is not set on this Tile");
	}
	
	//-----------Path Finding-----------//
	/*
	 * A private class used to aid in path finding.
	 */
	/*private class ShortestPathNode {
		Tile tile;
		ShortestPathNode previousNode;
		Direction direction; //the direction from the previousTile to the current
		int minSteps;
		
		ShortestPathNode(Tile tile, ShortestPathNode previousNode, Direction dir, int minSteps){
			this.tile = tile;
			this.previousNode = previousNode;
			this.minSteps = minSteps;
			this.direction = dir;
		}
		
		@Override 
		public boolean equals(Object obj){
			if (!(obj instanceof ShortestPathNode)) return false;
			return tile == ((ShortestPathNode) obj).tile;
		}
	}*/
	
	/*
	 * Currently unused method (except by other private unused methods), still under construction
	 * Finds shortest path between this tile and the destination for the character on this tile.
	 * @param destination The destination of the character
	 * @return The list of Tiles in order. If this Tile is the destination, returns an empty list. If no path can be found, returns null.
	 */
	/*private List<ShortestPathNode> getPath(Tile destination){
		if (!hasCharacter()){
			throw new UnsupportedOperationException("A shortest path cannot be determined without a character on the tile");
		}
		//this method is an implementation of Dijkstra's algorithm
		
		//initializing visited and unvisited sets
		Set<ShortestPathNode> visited = new HashSet<ShortestPathNode>();
		List<ShortestPathNode> unvisited = new ArrayList<ShortestPathNode>(); //list is to keep order, for consistency
		
		//Getting directions, sorting directions simply makes sure if there are multiple shortest paths, a consistent one is chosen 
		List<Direction> directions = new ArrayList<Direction>(getAllDirections());
		Collections.sort(directions); 
		
		ShortestPathNode currentNode = new ShortestPathNode(this, null, null, 0);
		unvisited.add(currentNode);
		
		while (currentNode.tile != destination && !unvisited.isEmpty())
		{
			
			//Look at all nodes adjacent to the current node
			for (Direction dir : directions){
				int tentativeDistance = currentNode.minSteps + 1;
				ShortestPathNode nextNode = new ShortestPathNode(getNextTile(dir), currentNode, dir, tentativeDistance);
			
				//checks to see if the node is eligible to be added to the unvisited list
				if (!visited.contains(nextNode) && (currentNode.tile.canMove(character, dir) 
					|| (nextNode.tile.hasCharacter(dir) && nextNode.tile == destination))){
					
					//Node is new, add it.
					if (!unvisited.contains(nextNode)){
						unvisited.add(nextNode);		
					}
					
					//Node has been seen before, figure out if this tentativeDistance is minimal or not
					else {
						for (int i = 0; i < unvisited.size(); i++){
							ShortestPathNode existingNode = unvisited.get(i);
							if (existingNode.equals(nextNode)){
								if (tentativeDistance < unvisited.get(i).minSteps){
									//minimal, overwrite the previous node
									existingNode.minSteps = tentativeDistance; 
									existingNode.direction = nextNode.direction;
									existingNode.previousNode = nextNode.previousNode;
								}
								break;
							}
						}
					}
				}
			}
			
			//all adjacent nodes have been checked, update for next iteration
			unvisited.remove(currentNode);
			visited.add(currentNode);
			
			if (unvisited.isEmpty()) return null; //no path exists
			
			ShortestPathNode minimumNode = unvisited.get(0);
			for (int i = 0; i < unvisited.size(); i++){
				if (unvisited.get(i).minSteps < minimumNode.minSteps){
					minimumNode = unvisited.get(i);
				}
			}
			
			currentNode = minimumNode;
		}
		
		List<ShortestPathNode> path = new ArrayList<ShortestPathNode>();
		
		//retrace the path
		while (currentNode.previousNode != null){
			path.add(currentNode);
			currentNode = currentNode.previousNode;
		}
		Collections.reverse(path);
		
		return path;
	}*/
}
