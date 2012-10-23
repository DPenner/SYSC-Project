package gameLoader;

import java.util.ArrayList;
import java.util.List;

import gameCore.Direction;
import gameCore.Player;
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
 * Original by Michael Kölling and David J. Barnes
 * @author  Trang Pham
 * @version 2012.09.13
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private Level level;
    private List<Command> undoList;
    private int undo_index;
        
    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
    	undoList = new ArrayList<Command>();
    	undo_index = 0;
        parser = new Parser();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {   
        if(!loadLvl("lvl0.xml"))
        {
        	System.out.println("Unable to load the game.");
        }
        else
        {
	        printWelcome();
	
	        // Enter the main command loop.  Here we repeatedly read commands and
	        // execute them until the game is over.
	                
	        boolean finished = false;
	        while (! finished) {
	            Command command = parser.getCommand();
	            finished = processCommand(command, false);
	        }
	        System.out.println("Thank you for playing.  Good bye.");
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        //System.out.println(currentRoom.getLongDescription());
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
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command, boolean isRedoOrUndo) 
    {
        boolean wantToQuit = false;
        boolean actionDone = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;
                
            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
            	look(command);
            	break;
            	
            case SEARCHITEM:
            	searchForItemOnGround(command);
            	break;

            case PICKUP:
            	actionDone = pickup(command);
            	break;
            	
            case DROP:
            	actionDone = drop(command);
            	break;
            	
            case GO:
                actionDone = go(command);
                break;

            case VIEW:
            	view(command);
            	break;
            	
            case UNDO:
            	undo();
            	break;
            case REDO:
            	redo();
            	break;
            
        }
        if(actionDone && !isRedoOrUndo)
        {
        	saveGameState(command);
        }
        return wantToQuit;
    }

    private void saveGameState(Command command)
    {
    	if(undo_index + 1 != undoList.size())
    	{
    		//override the available redos
    		for(int i = undoList.size(); i > undo_index; i--)
    		{
    			//remove the saved state 
    			undoList.remove(i);
    		}
    	}
    	
    	undoList.add(command);
    	undo_index++;
    }
    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around the maze searching for an exit.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void undo()
    {
    	if(undo_index != 0)
    	{	
    		undo_index--;
    		//undo
    		Command c = undoList.get(undo_index);
    		CommandWord oppositeCommandWord = c.getCommandWord().getOppositeCommand();
    		switch(oppositeCommandWord)
    		{
    		case GO:
    			String oppositeDirection = Direction.valueOf(c.getSecondWord().toUpperCase()).getOppositeDirection().toString();
    			processCommand(new Command(oppositeCommandWord, oppositeDirection), true);
    			break;
    		case PICKUP:
    		case DROP:
    			processCommand(new Command(oppositeCommandWord, c.getSecondWord()), true);
    		}
    		System.out.println("Successfully undone.");
    	}
    	else
    		System.out.println("Nothing to undo.");
    }
    
    private void redo()
    {
    	if(undo_index < undoList.size())
    	{
    		processCommand(undoList.get(undo_index), true);
    		System.out.println("Successfully redone.");
    		undo_index++;
    	}
    	else
    		System.out.println("Nothing to redo.");
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
        	System.out.println("Item is not on the ground. Can't pick up what's not there.");
        	return false;
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
        return true;
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean go(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getSecondWord();

        try
        {
        	if(!player.move(direction))
        	{
        		System.out.println("Cannot move through the wall in " + direction);
        		return false;
        	}
        	else
        	{
        		System.out.println("You have moved " + direction);
        	}
            return true;
        }
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        	return false;
        }
    }

    private void view(Command command)
    {
    	if(!command.hasSecondWord())
    	{
    		System.out.println(player.viewHealth());
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
    			System.out.println(d.toString() +": " + player.look(d.toString()));
    		}
    	}
    	else
    		System.out.println(player.look(command.getSecondWord()));
    }
    /**
     * 
     */
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
