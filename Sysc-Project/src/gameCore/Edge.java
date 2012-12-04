package gameCore;

import java.awt.Point;
import java.io.Serializable;

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

public class Edge extends LayoutObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Tile tile1;
	protected Tile tile2;
	protected boolean crossable;
	
	
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
	public Edge(Tile tile1, Tile tile2, boolean crossable, Direction direction1, Direction direction2)
	{
		this(tile1, tile2, crossable);
		connectTiles(tile1, direction1, tile2, direction2);
	}

	/**
	 * Sets this edge as the edge for the the two tiles in their respective given directions
	 * @param tile1 One side of the edge
	 * @param tile2 The other side of the edge
	 * @param direction1 The direction of the edge relative to tile1
	 * @param direction2 The direction of the edge relative to tile2
	 */
	protected void connectTiles(Tile tile1, Direction direction1, Tile tile2, Direction direction2){
		if(tile1 != null) tile1.setEdge(direction1, this);
		if(tile2 != null) tile2.setEdge(direction2, this);
	}
	
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
	 * Checks if the edge is crossable by default (character-independent)
	 * @return True if the edge is crossable, false otherwise.
	 */
	public final boolean isCrossableByDefault(){
		return crossable;
	}
	
	/**
	 * Places a Character on the other side of the Edge from the given Tile
	 * @param currentTile The current tile the Character resides on
	 * @param crosser The Character crossing the edge
	 * @return The new tile of the Character
	 * @exception IllegalArgumentException The currentTile or crosser is null, or the character is not capable
	 * of crossing this Edge
	 */
	public Tile cross(Tile currentTile, Character crosser){
		if (crosser == null || currentTile == null){
			throw new IllegalArgumentException("currentTile and crosser cannot be null");
		}
		if (!crosser.equals(currentTile.getCharacter())){
			throw new IllegalArgumentException("The given character must be on the given tile");
		}
		if (!canCross(crosser)){
			throw new IllegalArgumentException("The given character is not capable of crossing this edge");
		}
		
		Tile destination = getOtherTile(currentTile);
		currentTile.removeCharacter();
		destination.addCharacter(crosser);
		return destination;
	}
	
	/**
	 * Gets the tile on the other side of the edge, given one of the tiles
	 * (the Edge can't predict which tile is known and which is wanted).
	 * @param currentTile The originating tile.
	 * @return The Tile on the other side of this edge.
	 */
	public Tile getOtherTile(Tile currentTile){
		if (currentTile == null){
			throw new IllegalArgumentException("currentTile may not be null");
		}

		if (currentTile == tile1) return tile2;
		return tile1;
	}
	
	/**
	 * Gets the direction of this edge relative to its first tile.
	 * @return The direction of this edge relative to the first tile.
	 */
	public Direction getDirection1(){
		if (tile1 == null) return null;
		
		return tile1.getEdgeDirection(this);
	}
	/**
	 * Gets the direction of this edge relative to its second tile.
	 * @return The direction of this edge relative to the second tile.
	 */
	public Direction getDirection2(){
		if (tile2 == null) return null;
		
		return tile2.getEdgeDirection(this);
	}
	
	/**
	 * Gets the location of this edge's first Tile.
	 * @return The location of the first Tile.
	 */
	public Point getLocation1(){
		if (tile1 == null) return null;
		return tile1.getLocation();
	}
	/**
	 * Gets the location of this edge's second Tile
	 * @return The location of the second Tile
	 */
	public Point getLocation2(){
		if (tile2 == null) return null;
		return tile2.getLocation();
	}
	
	/**
	 * Checks if this edge is visited. It is considered visited if either of its Tiles are visited.
	 * @return True if the edge is visited, false otherwise.
	 */
	public final boolean isVisited(){
		if (isStranded()) return false;
		if (tile1 == null){
			return tile2.isVisited();
		}
		if (tile2 == null){
			return tile1.isVisited();
		}
		return tile1.isVisited() || tile2.isVisited();
	}
	
	/**
	 * Disconnects this edge from the given Tile
	 * @param t The Tile to disconnect
	 */
	public void disconnect(Tile t){
		if (t == null){
			throw new IllegalArgumentException("Can't disconnect null");
		}
		if (t == tile1){
			tile1 = null;
		}
		else {
			tile2 = null;
		}
		setChanged();   //this is so observers can detect if it is stranded, and remove it if they like
		notifyObservers();
	}
	
	public void connect(Tile t){
		if (isStranded() || isFullyConnected()){
			throw new UnsupportedOperationException("Nowhere to connect");
		}
		if (tile1 == null){
			tile1 = t;
		}
		else {
			tile2 = t;
		}
	}
	
	/**
	 * Returns whether an Edge is stranded or not - that is not connected to any Tile.
	 * This can occur after disconnects.
	 * @return True if the Edge is stranded, false otherwise
	 */
	public boolean isStranded(){
		return tile1 == null && tile2 == null;
	}
	
	public boolean isFullyConnected(){
		return tile1 != null && tile2 != null;
	}
}
