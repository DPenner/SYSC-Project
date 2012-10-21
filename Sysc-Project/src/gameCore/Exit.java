package gameCore;

/**
 * An exit is an Edge with a "lock." When locked, it is crossable only by a character with the specified key.
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

public class Exit extends Edge {
	private Item key;
	
	/**
	 * Constructs an exit
	 * 
	 * @param tile1 One side of the edge
	 * @param tile2 The other side of the edge
	 * @param locked Whether the door is locked or not
	 * @param key The key that unlocks the door (can be null if door starts unlocked)
	 */
	public Exit(Tile tile1, Tile tile2, boolean locked, Item key) {
		//Internally, a "locked" Exit is implemented as a "non crossable" Exit for code reuse
		super(tile1, tile2, locked);
		this.key = key;
	}
	
	/**
	 * Checks if the exit is locked
	 * @return True if the exit is unlocked, false otherwise
	 */
	public boolean isLocked(){
		return !crossable; //not crossable = is Locked
	}

	/**
	 * Checks whether the Exit is unlocked or locked and the character has the key
	 * @return True if it is possible to cross from one tile to the next, false otherwise
	 */
	@Override
	public boolean canCross(Character crosser)
	{
		return !isLocked() || crosser.hasItem(key);
	}
	
	/**
	 * Places a Character on the Tile on the other side of the Edge
	 * @param currentTile The current tile the Character resides on
	 * @param crosser The Character crossing the edge
	 * @return The Character's new tile
	 * @throws IllegalArgumentException crosser was either null or did not have a key. Use canCross(Character) first.
	 */ 
	@Override
	public Tile cross(Tile currentTile, Character crosser) throws IllegalArgumentException {
		if (crosser == null){
			throw new IllegalArgumentException("crosser can not be null");
		} //other error checks done by getOtherTile method
		
		if (unlock(key))
		{
			return super.cross(currentTile, crosser);
		}
		else throw new IllegalArgumentException("crosser did not have key");
	}
	
	/**
	 * Unlocks the exit
	 * @param key The key to attempt the unlock with
	 * @return True if unlocking succeeded, false otherwise
	 */
	public boolean unlock(Item key){
		if (this.key.equals(key)){
			crossable = true;
		}
		
		System.out.println("unlocking exit");
		return isLocked();
	}
}