package gameLoader;

import java.awt.KeyboardFocusManager;

import commands.CommandController;

import gameCore.Player;
import graphics2D.KDTView;
import graphics2D.TextOutputPanelObservable;


/**
 * The Game loads the level from XML, initiates the game, and waits for commands from the player.
 * 
 * Please see the manual instructions on how to play the game.
 * 
 * @author Group D
 * @author Main author: Trang Pham
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

public class Game extends TextOutputPanelObservable
{
    private Player player;
    private Level level;

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {   
        if(!loadLvl("lvl0.xml"))
        {
        	printMessage("Unable to load the game.");
        }
        else
        {
    		new KDTView(player, level);
    		KeyDispatcherController.initializeKeyDispatchController(player);
            
         	printWelcome(); 
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        printMessage("Welcome to the World of Kraft Dinner Table(KDT) Maze!");
        printMessage("KDT is the first version of an incredibly boring adventure game.");
        printMessage("If you find KD, you win! And the prize is an actual box of KD.\n");
        
        printMessage("You are lost. You are alone. You wander");
        printMessage("around the maze searching for an exit.\n");
        printMessage("Click the help menu option to view help manual.");
    }
    
    /** 
     * Loads a level from an xml file
     * 
     * @param xmlFile The xml file containing the level data
     * @return true If the level is successfully loaded
     */
    private boolean loadLvl(String xmlFile)
    {
    	LevelCreator lc = new LevelCreator();
    	if(lc.loadLevel(xmlFile))
    	{
    		level = lc.getLevel();
    		player = level.getPlayer();
    		return true;
    	}
    	return false;
    }
  
    public static class KeyDispatcherController
    {
        private static CommandController keyEventDispatcher;
        
        public static void initializeKeyDispatchController(Player player)
        {
	    	//Sets it so that all keyboard events go to the CommandController
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	        keyEventDispatcher = new CommandController(player);
			manager.addKeyEventDispatcher(keyEventDispatcher);
        }
        
        public static void removeKeyDispatchController()
        {
        	KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.removeKeyEventDispatcher(keyEventDispatcher);
        }
    }
    
    //Main starts game
    public static void main(String args[])
    {
    	Game g = new Game();
    	g.play();
    }
    
}
