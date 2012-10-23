package gameCore;

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

public class Room 
{
    private Set<Tile> tiles;
    private boolean visited;

    /**
     * Creates a room from a set of tiles.
     * @param tiles The set of tiles comprising the room.
     */

    public Room()
    {
    	tiles = new HashSet<Tile>();
    }
    public Room(Set<Tile> tiles) 
    {
    	if (tiles == null || tiles.isEmpty())
    	{
    		throw new IllegalArgumentException("A room must contain at least one tile.");
    	}
        this.tiles = tiles;
        visited = false;
    }

    public void addTile(Tile t)
    {
    	tiles.add(t);
    }

    /**
     * Checks if the room has been visited or not
     * @return True if the room has been visited, false otherwise
     */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Makes the room visited (a room cannot be "un-visited")
	 */
	public void setVisited() {
		this.visited = true;
	}
}

