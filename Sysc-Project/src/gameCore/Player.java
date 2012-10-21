
package gameCore;

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
	}
	
	/*
	 * Moves the player in the direction specified by the direction string.  ie north, south, east,etc
	 * @param direction - direction string ie north, south, etc.
	 */
	public void move(String direction){
		
		if(myPosition.canMove(direction)){
			this.myPosition=myPosition.moveCharacter(direction);  //move character to the next tile
		}
	}
	
	/*
	 * Prompt the player to choose the item to pick up
	 * @return the item chosen 
	 */
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
	
	/*
	 * Pickup method picks up the item on the current tile
	 * -- present a list of items in the inventory for selection if more than one item in the inventory, 
	 * -- otherwise, pick-up the item and add it to the player's inventory
	 */
	public void pickUpItem(){
		switch (myPosition.getInventory().size()){
		case 0:
			System.out.printf("Nothing to pick-up.");
			break;
		case 1: //pick-up the single item from the tile
			this.inventory.addItem(myPosition.getInventory().getItem(0));
			break;
		default:
			int i=promptForItem();
			if(i>=0){ //add it to my inventory and call tile to remove it from its inventory
				Item iToPickup = myPosition.getInventory().getItem(i);
				this.inventory.addItem(iToPickup);  //add to my inventory
				myPosition.removeItem(iToPickup);	//call tile to remove from its inventory
				
			}
			break;
		}		
	}
}
