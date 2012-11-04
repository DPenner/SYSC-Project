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
		return go(dir);
	}
	
	@Override
	public void undo() {
		go(dir.getOppositeDirection());	
	}
	
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
