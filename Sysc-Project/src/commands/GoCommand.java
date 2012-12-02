package commands;
/**
* Go command handles the movement of the player
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
* @version 2.0
*
*/
import java.io.Serializable;

import gameCore.Direction;
import gameLoader.EndGameException;


public class GoCommand extends Command implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private Direction dir;
	
	public GoCommand(Direction dir)
	{
		super();
		this.dir = dir;
	}
	
	@Override
	public boolean execute()
	{
		return go(dir);
	}
	
	@Override
	public void undo() {
		go(dir.getOppositeDirection());	
	}
	/**
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     *
	 * @param dirToGo the direction to move the player 
	 * @return true if the player successfully moved
	 */
	private boolean go(Direction dirToGo)
	{
		try
        {
        	StringBuffer output = new StringBuffer();
        	boolean hasMoved = CommandController.getPlayer().move(dirToGo, output);
        	printMessage(output.toString());
        	return hasMoved;
        }
        catch(Exception e)
        {
        	if(e instanceof EndGameException)
        	{
        		((EndGameException) e).handleExeception();
        	}
        	printMessage(e.getMessage());
        	return false;
        }
	}
	@Override
	public void redo() {
		go(dir);
	}
}
