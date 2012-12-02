package gameLoader;

import java.awt.KeyboardFocusManager;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import levelEditor.LevelEditor;
import levelEditor.LevelEditorView;

import commands.CommandController;
import commands.KeyCommandController;

import java.io.File;

import javax.swing.JFileChooser;
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
	private static final int MODE_OPEN = 0;
	private static final int MODE_SAVE = 1;
	
    private Player player;
    private Level level;

    private void play() 
    {   
        if(!loadLvl("lvl0.xml"))
        {
        	printMessage("Unable to load the game.");
        }
        else
        {	
    		new KDTView(player, level, new CommandController(player));
    		printWelcome(); 
        }
    }
    
    private void playSaved()
    {
    	Serialize s = new Serialize(player, level);
		if(s.loadFromFile())
		{
			new KDTView(s.getP(), s.getL(), new CommandController(s.getP()));
    		printWelcome(); 
		}
    }

    public void selectMode()
    {
		String[] modes = {"Play New Game", "Load Saved Game/Level", "Open Level Creator"};
		String mode =  (String)JOptionPane.showInputDialog(
		           			null,
		                    "Select mode:\n",
		                    "Kraft Dinner Table Game",
		                    JOptionPane.QUESTION_MESSAGE,
		                    null,
		                    modes,
		                    modes[0]);
		if(mode.equals("Play New Game"))
		{
			play();
		}
		else if(mode.equals("Load Saved Game/Level"))
		{
			playSaved();
		}
		else
		{
			new LevelEditorView();
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
  
    /**
    * KeyDispatcherController initializes and removes the KeyEventDispatcher
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
    * @version 1.0
    *
    */
    public static class KeyDispatcherController
    {
        private static KeyCommandController keyEventDispatcher;
        /**
         * initialize the KeyEventDispatcher
         * @param player the current player
         */
        public static void initializeKeyDispatchController(Player player)
        {
	    	//Sets it so that all keyboard events go to the CommandController
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	        keyEventDispatcher = new KeyCommandController(player);
			manager.addKeyEventDispatcher(keyEventDispatcher);
        }
        
        /**
         * Remove the KeyEventDispatcher such that no more keyboard events are being dispatched
         */
        public static void removeKeyDispatchController()
        {
        	KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.removeKeyEventDispatcher(keyEventDispatcher);
        }
        
        /**
         * Return instance of the CommandController so that the KDTMouseController can issue commands as well
         */
        public static KeyCommandController getCommandController() {
        	return keyEventDispatcher;
        }
    }
    
    //Main starts game
    public static void main(String args[])
    {
    	Game g = new Game();
    	g.selectMode();
    }
    
}
