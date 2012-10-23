package gameCore;
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
public class Monster extends Character implements NPC {

	//------------Constructors------------//
	public Monster(String name, int health, int attack, Tile myPosition){
		super(name,health, attack, myPosition);
	}
	
	/*
	 * Move the monster
	 * 
	 * ---Implement in second version - monsters are static in current version ---
	 * (non-Javadoc)
	 * @see gameCore.NPC#move(java.lang.String)
	 */
	public void move(String direction) {
		// TODO Auto-generated method stub
		
	}

}
