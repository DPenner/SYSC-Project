package gameCore;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * A Room is a collection of Tiles. It facilitates actions that must be done
 * on all Tiles, and provides the necessary support for concepts that only
 * apply to a grouping of tiles.
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

public class Room 
{
    private Set<Tile> tiles; //a set ensures that Tiles were not accidentally duplicated
    private boolean visited; //supports future idea that when GUI is implemented, only visited rooms are visible
    
    /**
     * Create a room from a set of Tiles
     * @param tiles The set of tiles from which the room is composed
     */
    public Room(Set<Tile> tiles) 
    {
    	this.tiles = tiles;
    	visited = false; //room starts unvisited
    }
    
    public List<Inventory> getInventories(){
    	List<Inventory> inventories = new ArrayList<Inventory>();
    	for (Tile t : tiles){
    		inventories.add(t.getInventory());
    	}
    	return inventories; //CONSIDER making a deep copy?
    }
    
    public List<Character> getCharacters(){
    	List<Character> character = new ArrayList<Character>();
    	for (Tile t : tiles){
    		character.add(t.getCharacter());
    	}
    	return character; //CONSIDER making a deep copy?
    }
    
    public boolean isVisited(){
    	return visited;
    }
    public void setVisited(boolean visited){
    	this.visited = visited;
    }
}


