package gameCore;

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
	 *  
	 */
	public void move(String direction){
		Edge nextEdge;
		if(myPosition.canMove(direction)){
			
			// temporarily commented out to test push .... km nextEdge=myPosition.getEdge(direction);
			// I was here....nextEdge.cross(myPosition, this); //cross to the next tile
		}
	}
}
