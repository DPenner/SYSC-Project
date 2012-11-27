package gameCore;
import gameLoader.EndGameException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
/**
 * A Monster is a subclass of NPC.  Monsters will try to move toward the player.  
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
public class Monster extends Character{
	private static final long serialVersionID = 1;
	
	Direction directions[]=Direction.values();
	//------------Constructors------------//
	public Monster(String name, int health, int attack, Tile myPosition){
		super(name,health, attack, myPosition);
	}
	
	/**
	 * Move the Monster and attack player is player is on adjacent tile, otherwise, do a random move
	 *  ---NOT IN USE FOR THIS VERSION ---
	 * @param output - output to status panel on KDTView
	 * @throws EndGameException 
	 */
	public void move(StringBuffer output) throws EndGameException {
		Direction directionToMove = getDirectionToMove();  //directionToMove should be crossable
		
		if(myPosition.isCrossable(directionToMove)){
			if (myPosition.hasCharacter(directionToMove))//attack
			{
				Character defender= myPosition.getCharacter(directionToMove);
				if(attack(defender)){
					this.myPosition=myPosition.moveCharacter(directionToMove);  //move monster to the next tile
					output.append("Monster attacked and killed " + defender +". Monster moved " + directionToMove);
					
					if(defender.isDead()) throw new EndGameException("Player has died. GAME OVER!");
					
				}
				else//player is still alive.
				{
					output.append("Monster attacked: \n" + defender.toString() +"\n"+ this.toString());
				}
			}
			else //just move the monster
			{
				this.myPosition=myPosition.moveCharacter(directionToMove);  //move character to the next tile
			}
		}
		
	}

	/**
	 * getDirectionToMove - returns the direction the monster should move.
	 * 
	 * @return - the direction of the adjacent tile if player is on it, otherwise, a random direction out of available choices  
	 */
	private Direction getDirectionToMove() {
		ArrayList<Direction> crossableDirections = new ArrayList<Direction>();
		
		Direction directionToMove;
		int numDirections;
		
		Set<Direction> directionSet=myPosition.getAllDirections(); //get set of all available positions to move off this tile
		
		for(Direction d: directionSet){
			if(myPosition.isCrossable(d)){
				crossableDirections.add(d);
			}
		}
		
		//search for player adjacent to me.  This is the preferred direction to move.
		for(Direction d: crossableDirections){
			if(myPosition.getCharacter(d) instanceof Player)
			{
				return d;
			}
		}
		
		//generate a random direction
		Random randomGenerator = new Random();
		int randomDirectionIndex = randomGenerator.nextInt(crossableDirections.size());
		
		directionToMove = directions[randomDirectionIndex];
		
		return directionToMove;
	}
}
