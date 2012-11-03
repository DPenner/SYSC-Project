package gameLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import gameCore.Direction;
import gameCore.Player;
import graphics2D.TextOutputPanel;
import graphics2D.KDTView;

import textInterface.Command;
import textInterface.CommandWord;
import textInterface.Parser;

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


    /**
     * Try to pick up an item. If the item exists, pick it up, otherwise print
     * an error message
     */
    
    private boolean pickup(Command command)
    {
    	if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Pick up what?");
            return false;
        }

        String itemname = command.getSecondWord();

        if(!player.pickUpItem(itemname))
        {
        	System.out.println(itemname + " is not on the ground. Can't pick up what's not there.");
        	return false;
        }
        else
        {
        	System.out.println("Picked up " + itemname);
        }
        return true;
    }
    
    private void searchForItemOnGround(Command command)
    {
    	if(command.hasSecondWord())
    	{
    		System.out.println("Cannot serach for " + command.getSecondWord());
    		return;
    	}
    	System.out.println(player.searchForItemOnGround());
    }
    
    /**
     * Try to drop an item. If the item is in player's inventory then drop it, otherwise
     * print error message
     */
    
    private boolean drop(Command command)
    {
    	if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
            return false;
        }

        String itemname = command.getSecondWord();

        if(!player.drop(itemname))
        {
        	System.out.println("Item is not in your inventory. Can't drop what you don't have.");
        	return false;
        }
        else
        {
        	System.out.println("Dropped " + itemname);
        }
        return true;
    }
    
    
    private void view(Command command)
    {
    	if(!command.hasSecondWord())
    	{
    		System.out.println(player.toString());
    		//System.out.println(player.viewHealth());
    		System.out.println(player.viewInventory());
    	}
    	else
    	{
    		String secondWord = command.getSecondWord();
    		if(secondWord.equalsIgnoreCase("inventory"))
    		{
    			System.out.println(player.viewInventory());
    		}
    		else if(secondWord.equalsIgnoreCase("health"))
    		{
    			System.out.println(player.viewHealth());
    		}
    		else
    		{
    			System.out.println("View what now?");
    		}
    	}
    }
    private void look(Command command)
    {
    	if(!command.hasSecondWord())
    	{
    		//look in all directions
    		for(Direction d:Direction.values())
    		{
    			try
    			{
    				StringBuffer output = new StringBuffer();
    				player.look(d, output);
    				System.out.println(d.toString() +": " + output);
    			}
    			catch(Exception e)
    			{
    				System.out.println(d.toString() +": " + e.getMessage());
    			}
    		}
    	}
    	else
    	{
    		try
    		{
    			StringBuffer output = new StringBuffer();
				player.look(Direction.getDirection(command.getSecondWord()), output);
				System.out.println(output);
    		}
    		catch(Exception e)
    		{
    			System.out.println(e.getMessage());
    		}
    	}
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    //Main starts game
    public static void main(String args[])
    {
    	Game g = new Game();
    	g.play();
    }
}
