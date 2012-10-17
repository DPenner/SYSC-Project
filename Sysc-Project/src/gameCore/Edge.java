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
	 * 
	 * @return True if it is possible to cross from one tile to the next, false otherwise
	 */
	public boolean canCross(Character crosser)
	{
		return crossable;
	}
	
	/**
	 * Places a Character on the Tile on the other side of the Edge
	 * @param currentTile The current tile the Character resides on
	 * @param crosser The Character crossing the edge
	 * @return
	 */
	public Tile cross(Tile currentTile, Character crosser){
		if (crosser == null){
			throw new IllegalArgumentException("crosser can not be null");
		} //other error checks done by getOtherTile method
		
		Tile destination = getOtherTile(currentTile);
		destination.addCharacter(crosser);
		return destination;
	}
	/**
	 * 
	 * @param the originating tile
	 * @return the Tile on the other side of this edge
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
