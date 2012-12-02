package commands;
/**
* CommandController handles the keyboard input for each command.
* It also handles the logic for redoing and undoing commands.
* Added methods to handle mouse events from the KDTMouseController.
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
* @version 3.0
*
*/
import gameCore.Direction;
import gameCore.Player;
import graphics2D.TextOutputPanelObservable;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandController extends TextOutputPanelObservable implements KeyEventDispatcher, Serializable {
	private static final long serialVersionUID = 1L;
	
	private static List<Command> undoList;
	private static Map<Integer, Command> keyToCommandMap;
    private static int undo_index;
    private static Player player;
    
    public CommandController(Player p)
    {
    	player = p;
    	if(undoList != null) return;
    	
    	undoList = new ArrayList<Command>();
    	undo_index = 0;
    	keyToCommandMap = new HashMap<Integer, Command>();
    	
    	initializeKeyToCommandMap();
    }
        
    private void initializeKeyToCommandMap()
    {
    	keyToCommandMap.put(KeyEvent.VK_UP, new GoCommand(Direction.NORTH));
    	keyToCommandMap.put(KeyEvent.VK_DOWN, new GoCommand(Direction.SOUTH));
    	keyToCommandMap.put(KeyEvent.VK_LEFT, new GoCommand(Direction.WEST));
    	keyToCommandMap.put(KeyEvent.VK_RIGHT, new GoCommand(Direction.EAST));
    	
    	keyToCommandMap.put(KeyEvent.VK_S, new SearchCommand());
    	keyToCommandMap.put(KeyEvent.VK_P, new PickUpCommand());
    	keyToCommandMap.put(KeyEvent.VK_D, new DropCommand());
    }
    
    /* 
     * Protected Getters
     */
    protected static Player getPlayer() { return player;}
    
    /*
     * For undo/redo commands
     */
    private void undoCommand() {
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
    private void redoCommand() {
    	if(undo_index >= undoList.size()) printMessage("Nothing to redo.");
    	else
    	{
    		undoList.get(undo_index).redo();
    		printMessage("Successfully redone.");
    		undo_index++;
    	} 		
    }
    private void saveGameState(Command command)
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

	@Override
	public boolean dispatchKeyEvent(KeyEvent arg0) {
		//this method is called twice: once for key pressed and again for released, ignore release
		//otherwise it will try to execute the command twice
		
		if(arg0.getID() != KeyEvent.KEY_PRESSED) return false;
		
		int keyPressed = arg0.getKeyCode();
		if (keyPressed ==  KeyEvent.VK_U) undoCommand();
		else if (keyPressed == KeyEvent.VK_R) redoCommand();
		else
		{
			Command c = keyToCommandMap.get(keyPressed);
			if(c != null) if(c.execute()) saveGameState(c);
		}
		return false;
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
		 if(c.execute()) return true;
		 
		 return false;
	}
	
	/**
	 * ExecDrop wrapper for executing drop item from KDTMouseController
	 * @return true if item successfully dropped, else return false.
	 */
	public boolean execDrop() {
		DropCommand dc = new DropCommand();
		if( dc.execute()) return true;
		
		return false;
	}
}
