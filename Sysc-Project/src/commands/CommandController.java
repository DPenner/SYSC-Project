package commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gameCore.Direction;
import gameCore.Player;
import graphics2D.TextOutputPanelObservable;

/**
 *  The CommandController is the superclass for keyCommandController
 *  and is used to support player movement and undo/redo actions
 * 
 * @author Group D
 * @author Main author: Karen Madore/Trang Pham
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

public class CommandController extends TextOutputPanelObservable implements Serializable{
	private static final long serialVersionUID = 1L;
	private static int undo_index;
    private static Player player;
    private static List<Command> undoList;
    
    public CommandController(Player p)
    {
    	player = p;
    	if(undoList != null) return;
    	
    	undoList = new ArrayList<Command>();
    	undo_index = 0;
    }
    
    /*
     * For undo/redo commands
     */
    protected void undoCommand() {
    	if(undo_index == 0) printMessage("Nothing to undo.");
    	else
    	{	
    		undo_index--;
    		//undo
    		Command c = undoList.get(undo_index);
    		c.undo();
    		printMessage("Successfully undone.");
    	} 		
    }
    
    protected void redoCommand() {
    	if(undo_index >= undoList.size()) printMessage("Nothing to redo.");
    	else
    	{
    		undoList.get(undo_index).redo();
    		printMessage("Successfully redone.");
    		undo_index++;
    	} 		
    }
    protected void saveGameState(Command command)
    {
    	if(undo_index != undoList.size())
    	{
    		//override the available redos
    		for(int i = undoList.size(); i > undo_index; i--)
    		{
    			//remove the saved state 
    			undoList.remove(i-1);
    		}
    	}
    	
    	undoList.add(command);
    	undo_index++;
    }

	public static Player getPlayer() {
		return player;
	}

	// -----  Commands to support KDTMouseController ------
	/**
	 * ExecUndo wrapper for executing undo from KDTMouseController
	 */
	public void execUndo() {
		undoCommand();
	}
	
	/**
	 * ExecRedo wrapper for executing redo from KDTMouseController
	 */
	public void execRedo() {
		redoCommand();
	}
	
	/**
	 * ExecGo wrapper for executing go in specifiec direction from KDTMouseController
	 * @param d direction to go
	 * @return	true if executed move, else false
	 */
	public boolean execGo(Direction d) {
		Command c = new GoCommand(d);
		if(c != null && c.execute()) {
			saveGameState(c);
			return true;  //moved
		}
		return false;  //not moved
	}
	
	/**
	 * ExecPickup wrapper for executing Pickup from KDTMouseController
	 * @return true if item picked up, else false
	 */
	public boolean execPickup() {
		 Command c = new PickUpCommand();
		 if(c.execute()) 
		 {
			 saveGameState(c);
			 return true;
		 }
		 return false;
	}
	
	/**
	 * ExecDrop wrapper for executing drop item from KDTMouseController
	 * @return true if item successfully dropped, else return false.
	 */
	public boolean execDrop() {
		DropCommand dc = new DropCommand();
		if( dc.execute()) 
		{
			saveGameState(dc);
			return true;
		}
		return false;
	}
}
