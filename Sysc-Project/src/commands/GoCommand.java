package commands;

import gameCore.Direction;
import gameLoader.EndGameException;


public class GoCommand extends Command{
	private Direction dir;
	
	public GoCommand(Direction dir)
	{
		super();
		this.dir = dir;
	}
	/** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
	public boolean execute()
	{
		try
        {
        	StringBuffer output = new StringBuffer();
        	boolean hasMoved = CommandController.getPlayer().move(dir, output);
        	notifyObservers(output.toString());
        	return hasMoved;
        }
        catch(Exception e)
        {
        	/*if(e instanceof EndGameException)
        	{
        		endGame = true;
        	}*/
        	notifyObservers(e.getMessage());
        	return false;
        }
	}
}
