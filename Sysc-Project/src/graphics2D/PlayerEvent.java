package graphics2D;

import java.util.EventObject;

import gameCore.Player;
import gameCore.Item;
import gameCore.Inventory;
import gameCore.Tile;

/**
 * A Player Event used by Player to notify it's subscribers of events.
 * 
 * @author Group D
 * @author Main author: Karen Madore
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
public class PlayerEvent extends EventObject {
	private Inventory inv;
	private Item item;
	private Player player;
	
	/**
	 * Constructor for a PlayerEvent
	 * @param source - source of the event
	 */
	public PlayerEvent(Object source){
		super(source);
		
		if(source instanceof Player) this.player = (Player) source;
	}
	/**
	 * Get the Player for the listeners
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the Player for this event
	 * 
	 * @param player - the player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Get the item from the event that was added 
	 * @return - the item 
	 */
	public Item getItem(){
		return item;
	}
	
	/**
	 * Set the item for the event
	 * @param item - the item that was added or dropped
	 */
	public void setItem(Item item){
		this.item=item;
	}
	
	/**
	 * Get the player's location
	 * @return tile - the tile the player is on
	 */
	public Tile getPosition()
	{
		return player.getPosition();
	}
	
	
}
