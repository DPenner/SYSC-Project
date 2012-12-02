package gameLoader;

import gameCore.*;
import gameCore.Character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
* A level holds all the data pertaining to the current game state:
* configuration of the rooms, tiles, and exits,
* location of items, and characters
* timer value
*
* @author Group D
* @author Main Author: Trang Pham
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

public class Level implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private int timer;
	private int gridWidth;
	private int gridHeight;
	
	private Tile[][] tileGrid;
	private List<Edge> edgeList;
	private Room elevator;
	private Player player;
	
	public Level()
	{
		edgeList = new ArrayList<Edge>();
		timer = 0; 
	}
	
	/**
	 * Returns the player
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * Sets the size of the level which determines how many tiles are on this level
	 * 
	 * @param width the width of the level's map
	 * @param height the height of the level's map
	 */
	public void setLevelSize(int width, int height)
	{
		if(tileGrid == null)
		{
			gridWidth = width;
			gridHeight = height;
			tileGrid = new Tile[height][width];
			for(int i = 0; i < height; i++)
			{
				tileGrid[i] = new Tile[width];
			}
		}
	}
	/**
	 * Sets the tile at a location
	 * 
	 * @param x position
	 * @param y position
	 * @param t tile to set to
	 */
	public boolean setTile(int x, int y, Tile t)
	{
		if(x < gridWidth)
			if(y < gridHeight)
				if(tileGrid != null)
				{
					tileGrid[y][x] = t;
					return true;
				}
		return false;
	}
	
	/**
	 * Gets tile at a location
	 * 
	 * @param x position
	 * @param y position
	 * @return tile
	 */
	public Tile getTile(int x, int y)
	{
		if(tileGrid != null)
			return tileGrid[y][x];
		else throw new NullPointerException("Level getTile() returns null for " + x + "," + y + ".");
	}
	
	public boolean addEdge(Tile tile1, Tile tile2, String direction1, String direction2, Item key, boolean crossable)
	{
		Edge e;
		Direction dir1 = Direction.valueOf(direction1.toUpperCase());
		Direction dir2;
		if(dir1 != null)
		{
			if(direction2.isEmpty()) dir2 = dir1.getOppositeDirection();
			else dir2 = Direction.valueOf(direction2.toUpperCase());
			if(key != null)
			{
				e = new Exit(tile1, tile2, !crossable, dir1, dir2, key);
			}
			else
			{
				e = new Edge(tile1, tile2, crossable, dir1, dir2);
			}
			edgeList.add(e);
			return true;
		}
		return false;
	}
	
	public Character addCharacter(String name, int health, int attack, int x, int y)
	{	
		if(tileGrid[y][x] == null) return null;
		else return new Character(name, health, attack, tileGrid[y][x]);
	}
	
	public boolean addItem(String name, int weight, int x, int y)
	{
		Tile tileToAddItemTo = tileGrid[y][x];
		if(tileToAddItemTo == null)
			return false;
		
		tileToAddItemTo.addItem(new Item(name, weight));
		return true;
	}
	
	public boolean addWeapon(String name, int weight, int attack, int x, int y)
	{
		Tile tileToAddItemTo = tileGrid[y][x];
		if(tileToAddItemTo == null)
			return false;
		
		tileToAddItemTo.addItem(new Weapon(name, weight, attack));
		return true;
	}
	/**
	 * @return the timer
	 */
	public int getTimer() {
		return timer;
	}
	/**
	 * @param timer the timer to set
	 */
	public void setTimer(int timer) {
		if(timer == 0)
		{
			this.timer = timer;
		}
	}
	/**
	 * @return the gridWidth
	 */
	public int getGridWidth() {
		return gridWidth;
	}
	/**
	 * @return the gridHeight
	 */
	public int getGridHeight() {
		return gridHeight;
	}
	/**
	 * @return the elevator
	 */
	public Room getElevator() {
		return elevator;
	}
	/**
	 * @param elevator the room to set as elevator
	 */
	public void setElevator(Room elevator, Tile tileInElevator) {
		if(elevator != null) 
		{
			this.elevator = elevator;
			if(player == null) player = new Player("Babak", 10, 2, 1, tileInElevator);
		}
	}
	
	public void setPlayer(Player p)
	{
		player = p;
	}
	
	/**
	 * Getter to return Tiles for use in MapView
	 *
	 */
	public Tile[][] getTiles(){
		return tileGrid;
	}
	
	/**
	 * Getter to return list of Edges for use in MapView
	 */
	public List<Edge> getEdges(){
		return edgeList;
	}
	
}
