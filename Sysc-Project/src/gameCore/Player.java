
package gameCore;

import graphics2D.PlayerEvent;
import graphics2D.PlayerListener;
import gameLoader.EndGameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * A Character is a superclass for all of the animated creatures inside the game.
 * Player, and Monsters (subclass of NPC) are subclasses of character.
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
public class Player extends Character
{	//------------Fields------------//
	private int stamina;
	private List<PlayerListener> pListeners;
	
	//------------Constructors------------//
    /*
	 * Constructs a Player with a name, health, attack, stamina, and a current Tile position inside the game.
	 * @param name - the Name of the Player
	 * @param health - the health of the player.  When the health reaches zero, the player dies.  When the player attacks a monster, the players health=health-monstersAttackValue;
	 * @param attach - the attack value of the player.  
	 * @param stamina - the stamina determines how much inventory the player can carry.  Player's total inventory weight cannot exceed stamina.		
	 **/
	public Player(String name, int health, int attack, int stamina, Tile myPosition){
		super(name,health,attack,myPosition);
		this.stamina=stamina;
		pListeners = new ArrayList<PlayerListener>();
	}
	
	/*
	 * Moves the player in the direction specified by the direction string.  ie north, south, east,etc
	 * @param direction - direction string ie north, south, etc.
	 * @return	
	 */
	public boolean move(Direction direction, StringBuffer output) throws EndGameException{
		boolean hasMoved=false;
		if(myPosition.isCrossable(direction)){
			if (myPosition.hasCharacter(direction))
			{
				Character defender= myPosition.getCharacter(direction);
				if(attack(defender)){
					this.myPosition=myPosition.moveCharacter(direction);  //move character to the next tile
					output.append("You attacked and killed " + defender +". You moved " + direction);
					hasMoved = true;
				}
				else//defender is still alive.
				{
					if(isDead()) throw new EndGameException("Player has died. GAME OVER!");
					
					output.append("You attacked: \n" + defender.toString() +"\n"+ this.toString());
				}
			}
			else
			{
				this.myPosition=myPosition.moveCharacter(direction);  //move character to the next tile
				output.append("You moved "+ direction + ".");
				hasMoved = true;
			}
		}
		else
		{
			output.append(checkIfLockedExit(direction));
		}
		return hasMoved;
	}
	
	/*
	 * Prompt the player to choose the item to pick up
	 * @return the item chosen 
	
	private int promptForItem(){
		Scanner s;
		String itemName;
			
		String pString="Items available for pick-up are: ";
		pString += this.inventory.toString()+ "\n";
		pString+="Please enter the name of the item to pick-up.\n";
		System.out.println(pString);
		
		s = new Scanner(System.in);
		itemName=s.next();
		int index = myPosition.getInventory().getIndex(itemName);
		if (index>=0){
			return index;
		}else{
			System.out.println("No such item to pick up.");
			return -1;
		}
	}
	*/
	
	/**
	 * Pickup method picks up the item on the current tile
	 *  
	 * 	Pick up the item and add it to the player's inventory if the itemName exists
	 * 
	 * @param 	itemName is the string of the item.
	 * @return	true if the item was picked up, false if the item does not exists.
	 */
	public boolean pickUpItem(String itemName){
		
		boolean itemPickedUp=false;
		//check to see if the item is a valid item
		int index = myPosition.getInventory().getIndex(itemName);
		if (index>=0){ //got valid item
			//pick-up the single item from the tile
			Item itemToPickup = myPosition.getInventory().getItem(index);
			this.inventory.addItem(itemToPickup);
			myPosition.removeItem(itemToPickup);
			
			itemPickedUp=true;
			
			notifyItemPickedUp(itemToPickup);
		}
		return itemPickedUp;
	}
	
	/**
	 * Drop method drops the item 
	 * @param	itemName String name of the item to drop
	 * @return	true if the item was dropped successfully, false if the item does not exists.
	 */
	public boolean drop(String itemName){
		boolean itemDropped=false;
		
		int index=inventory.getIndex(itemName);
		if(index>=0){//got a valid item
			//pick-up the single item 
			Item itemToDrop = inventory.getItem(index);
			this.inventory.removeItem(itemToDrop);
			myPosition.addItem(itemToDrop);
			itemDropped=true;
			
			notifyItemDropped(itemToDrop);
		}
		return itemDropped;
	}
	
	/**
	 * Look method any characters it sees in direction requested. If it is a wall in that direction, indicate it.
	 * If an exit is there but is locked, then get the key that is required.
	 * @param 	direction to move
	 * @return	string of characters it sees
	 */
	public boolean look(Direction direction, StringBuffer output){
		if(myPosition.isCrossable(direction))
		{
			if(myPosition.hasCharacter(direction)){//there is a character in the direction the player wishes to move
				output.append(myPosition.getCharacter(direction).toString() + " is located " + direction +" of you.");
			}else{
				output.append("Can move " + direction + ". No character in front of you.");
			}
			return true;
		}
		else //cannot move in that direction
		{
			output.append(checkIfLockedExit(direction));
			return false;
		}
	}
	
	private String checkIfLockedExit(Direction direction)
	{
		String retString;
		//is this a locked exit or is it an uncrossable edge?
		if (myPosition.hasExit(direction) ){
			
			retString = "Exit Locked.  Need " + myPosition.getExitKey(direction);
		}else{
		//if locked exit
			//retString = locked exit require item name to open
		//else
			//this is a wall
			retString = "There is a wall.";
		}
		return retString;
	}

	/**
	 * ViewInventory method returns an string representation of the player's inventory
	 * 
	 * @return	a list of items in the player's inventory as a string
	 */
	public String viewInventory(){
		String retString;
		retString = "";
		if(this.inventory.isEmpty()){
			retString = "Nothing in your inventory.";
		}else{
			retString = "The following items are in your inventory: " + this.inventory.toString();
		}
		return retString;
	}
	
	public String viewHealth(){
		return  "Your health is: " + this.health;
	}

	public String searchForItemOnGround() {
		if(myPosition.getInventory().isEmpty())
		{
			return "Nothing on the ground";
		}
		return "The following items are on the ground: " + myPosition.getInventory().toString();
	}

	/**
	 * Override the adjustHealth of the Character class to provide player with ability to notify PlayerStatusPlanel of updates 
	 */
	
	public void adjustHealth(int value){
		super.adjustHealth(value);
		
		notifyStatsChanged();
	}

	
	private void notifyItemDropped(Item droppedItem){
		PlayerEvent pe=new PlayerEvent(this);
		pe.setItem(droppedItem);
		
		for(PlayerListener pl: pListeners){
			pl.itemDropped(pe);
		}
	}
	/**
	 * Item has been added: notify subscribers
	 * @param newItem - a new item that was added to player's inventory
	 */
	private void notifyItemPickedUp(Item newitem){
		PlayerEvent pe=new PlayerEvent(this);
		pe.setItem(newitem);
		
		for(PlayerListener pl: pListeners){
			pl.itemAdded(pe);
		}
	}
	
	/**
	 * Health or Attack has changed - notify subscribers
	 */
	private void notifyStatsChanged()
	{	
		PlayerEvent pe=new PlayerEvent(this);
		pe.setPlayer(this);
	
		for(PlayerListener pl: pListeners){
			pl.statsChanged(pe);
		}
			
	}
	/**
	 * Allow external classes to subscribe to the PlayerEvents
	 * @param pl an object of PlayerListener which wants to subscribe to player events
	 */
	public void addPlayerListener(PlayerListener pl)
	{
		pListeners.add(pl);
	}
	
	/**
	 * Remove the PlayerListener specified
	 * @param pl the PlayerListener object to unsubscribe
	 */
	public void removePlayerListener(PlayerListener pl){
		pListeners.remove(pl);
	}
	
	
}
