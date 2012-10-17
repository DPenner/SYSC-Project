package gameCore;

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

public class Exit extends Edge {
	private Item key;
	
	/**
	 * Exit constructor
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
		return !canCross();
	}

	/**
	 * Unlocks the exit
	 * @param key The key to attempt the unlock with
	 * @return True if unlocking succeeded, false otherwise
	 */
	public boolean unlock(Item key){
		if (!isLocked()){
			throw new UnsupportedOperationException("Tried to unlock an already unlocked exit");
		}
		
		if (this.key.equals(key)){
			crossable = true;
		}
		
		return isLocked();
	}
}
