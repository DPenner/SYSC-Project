package gameGUI;

import java.util.EventObject;

import gameCore.Player;
import gameCore.Item;
import gameCore.Inventory;

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
	
	public PlayerEvent(Object source){
		super(source);
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Item getItem(){
		return item;
	}
	public void setItem(Item item){
		this.item=item;
	}
	
	
}
