package textInterface;

/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * Original from Michael Kölling and David J. Barnes
 * @author  Trang Pham
 * @version 2012.09.13
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"),
    VIEW("view"), LOOK("look"), 
    PICKUP("pickup"), DROP("drop"), SEARCHITEM("search"),
    UNDO("undo"), REDO("redo");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
    
    public CommandWord getOppositeCommand()
    {
    	switch(this)
    	{
    	case PICKUP:
    		return DROP;
    	case DROP:
    		return PICKUP;
    	default:
    		return this;
    	}
    }
}

