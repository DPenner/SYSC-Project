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

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyCommandController extends CommandController implements KeyEventDispatcher {
	private static Map<Integer, Command> keyToCommandMap;
    
    
    public KeyCommandController(Player p)
    {
    	super(p);
    	
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
}
