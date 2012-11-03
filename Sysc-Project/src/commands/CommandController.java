package commands;

import gameCore.Direction;
import gameCore.Player;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandController implements KeyEventDispatcher{
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
    }
    
    /* 
     * Protected Getters
     */
    
    protected static Player getPlayer() { return player;}
    protected static void undoCommand() {
    }
    protected static void redoCommand() {
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
		Command c = keyToCommandMap.get((Integer)arg0.getKeyCode());
		if(c.execute()) saveGameState(c);
		return false;
	}
}
