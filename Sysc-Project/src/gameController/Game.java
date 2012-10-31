package gameController;

import gameCore.Player;
import gameLoader.Level;
import gameLoader.LevelCreator;
import graphics2D.TextOutputPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import textInterface.Command;
import textInterface.CommandWord;
import textInterface.Parser;

/**
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it load a level 
 *  from an xml file, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
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
public class Game extends Observable {
    private Player player;
    private Level level;
    private List<Command> undoList;
    private int undo_index;
private boolean endGame;

    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
    	undoList = new ArrayList<Command>();
    	undo_index = 0;
    	this.addObserver(TextOutputPanel.getTextOutputPanel());
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {   
        if(!loadLvl("lvl0.xml"))
        {
        	notifyObservers("Unable to load the game.");
        	return;
        }
        printWelcome();
	
		notifyObservers("Thank you for playing.  Good bye.");
        
    }
    

    /**
     * Print out the opening message for the player.
     */
    private String printWelcome()
    {
        return "Welcome to the World of Kraft Dinner Table(KDT) Maze!\n KDT is the first version of an incredibly boring adventure game.\nType '" + CommandWord.HELP + "' if you need help.\nIf you find KD, you win! And the prize is an actual box of KD.";
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
    	
    	if(!lc.loadLevel(xmlFile)) return false;
    	
		level = lc.getLevel();
		player = level.getPlayer();
		return true;
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

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private String printHelp() 
    {
    	StringBuffer rv = new StringBuffer("You are lost. You are alone. You wander\n around the maze searching for an exit.\n\nYour command words are:");
        for(CommandWord commandWord: CommandWord.values())
        {
        	rv.append(commandWord + " ");
        }
    	return rv.toString();
    }

}
