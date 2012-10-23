package gameCore;

/**
 * An edge exists between 2 tiles, or a tile and the boundary of a level.
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

public class Edge {
	
	protected Tile tile1;
	protected Tile tile2;
	protected boolean crossable;
	
	/**
	 * Default constructor for internal use
	 */
	private Edge(){}
	
	/**
	 * Creates an edge, setting the two tiles and the crossable variable
	 * If the edge is between a tile and the boundary of a level, the extra tile is to be null
	 * @param tile1 One side of the edge
	 * @param tile2 The other side of the edge
	 * @param crossable True if the two tiles can be directly moved between, false otherwise
	 */
	public Edge(Tile tile1, Tile tile2, boolean crossable)
	{
		if (tile1 == null && tile2 == null){
			throw new IllegalArgumentException("Cannot have edge unattached to a tile");
		}
		if ((tile1 == null || tile2 == null) && crossable){
			throw new IllegalArgumentException("Cannot cross into null tile");
		}
		if (tile1 == tile2){
			throw new IllegalArgumentException("tile1 and tile2 are the same reference");
		}
		this.tile1 = tile1;
		this.tile2 = tile2;
		this.crossable = crossable;
	}
	
	/**
	 * Creates the edge, and sets the edge for the two tiles in the given directions. If the
	 * edge is between a tile and the boundary of a level, the extra tile is to be null.
	 * @param tile1 One side of the edge
	 * @param tile2 The other side of the edge
	 * @param crossable True if the two tiles can be directly moved between, false otherwise
	 * @param direction1 The direction of the edge relative to tile1
	 * @param direction2 The direction of the edge relative to tile2
	 */
	public Edge(Tile tile1, Tile tile2, boolean crossable, String direction1, String direction2)
	{
		this(tile1, tile2, crossable);
		if(tile1 != null) tile1.setEdge(direction1, this);
		if(tile2 != null) tile2.setEdge(direction2, this);
	}
	
	/**
	 * Creates an edge, setting the two tiles and the crossable variable
	 * @param tile1 One side of the edge
	 * @param tile2 The other side of the edge
	 * @param crossable True if the two tiles can be directly moved between, false otherwise
	 * @return The new edge
	 */

	
	/**
	 * Checks if the given character can cross the edge
	 * @return True if it is possible to cross from one tile to the next, false otherwise
	 */
	public boolean canCross(Character crosser)
	{
		//simply returns crossable, without using any character,
		//but it is very possible that a derived class may base whether or not
		//an edge is crossable based on the character crossing the edge
		return crossable;
	}
	
	/**
	 * Places a Character on the other side of the Edge from the given Tile
	 * @param currentTile The current tile the Character resides on
	 * @param crosser The Character crossing the edge
	 * @return The new tile of the Character
	 */
	public Tile cross(Tile currentTile, Character crosser){
		if (crosser == null){
			throw new IllegalArgumentException("crosser can not be null");
		} //other error checks done by getOtherTile method
		
		Tile destination = getOtherTile(currentTile);
		currentTile.removeCharacter();
		destination.addCharacter(crosser);
		return destination;
	}
	
	/**
	 * Gets the tile on the other side of the edge, given one of the tiles
	 * (the Edge can't predict which tile is known and which is wanted)
	 * @param currentTile The originating tile
	 * @return The Tile on the other side of this edge
	 */
	public Tile getOtherTile(Tile currentTile){
		if (currentTile == null){
			throw new IllegalArgumentException("currentTile may not be null");
		}
		if (!crossable){
			throw new UnsupportedOperationException("Cannot retrieve tile when edge is not crossable");
		}
		
		//return which ever tile isn't the one passed in
		if (currentTile == tile1) return tile2;
		return tile1;
	}
}
