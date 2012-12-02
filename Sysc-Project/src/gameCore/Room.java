package gameCore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A room is a collection of tiles. It provides methods which can
 * deal with a collection of tiles at once.
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

public class Room implements Serializable  
{
	private static final long serialVersionUID = 1L;
	
    private Set<Tile> tiles;

    /**
     * Creates a room with no tiles
     */
    public Room(){
    	tiles = new HashSet<Tile>();
    }
    
    /**
     * Creates a room from a set of tiles.
     */
    public Room(Set<Tile> tiles) 
    {
    	if (tiles == null || tiles.isEmpty())
    	{
    		throw new IllegalArgumentException("A room must contain at least one tile.");
    	}
        this.tiles = tiles;
    }

    /**
     * Adds a tile to the room.
     * @param t The tile to add.
     * @exception IllegalArgumentException if t is null.
     */
    public void addTile(Tile t){
    	if (t == null){
    		throw new IllegalArgumentException("Tile cannot be null");
    	}
    	tiles.add(t);
    }

	/**
	 * Makes the room visited (a room cannot be "unvisited") by setting
	 * all of its tiles as visited.
	 */
	public void setVisited() {
		for (Tile t : tiles){
			t.setVisited();
		}
	}
}

