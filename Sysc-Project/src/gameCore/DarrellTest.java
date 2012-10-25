package gameCore;

import java.awt.Point;
import java.util.*;
import textInterface.*;

public class DarrellTest {
    private Parser parser;
    private Tile currentTile;
        
    /**
     * Create the game and initialize its internal map.
     */
    public DarrellTest() 
    {
        createTiles();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createTiles()
    {
    	/*List<Point> tileLocations = new ArrayList<Point>();
    	tileLocations.add(new Point(0, 0));
    	tileLocations.add(new Point(0, 1));
    	tileLocations.add(new Point(1, 0));
    	tileLocations.add(new Point(1, 1));
    	
    	//Comparers to find max/min x/y
    	class XComparer implements Comparator<Point>{
    		public int compare(Point p1, Point p2){
    			return p1.x - p2.x;
    		}
    	}
    	class YComparer implements Comparator<Point>{
    		public int compare(Point p1, Point p2){
    			return p1.y - p2.y;
    		}
    	}
    	
    	int minX = Collections.min(tileLocations, new XComparer()).x;
    	int maxX = Collections.max(tileLocations, new XComparer()).x;
    	int minY = Collections.min(tileLocations, new YComparer()).y;
    	int maxY = Collections.max(tileLocations, new YComparer()).y;
    	
    	Tile[][] tiles = new Tile[maxX - minX + 1][maxY - minY + 1];
    	
    	for (Point location : tileLocations){
    		//fill matrix with tiles that exist (offsetting by minimum values to have no negative values)
    		tiles[location.x - minX][location.y - minY] = new Tile();
    	}*/
    	
        /*SortedSet<Tile> tiles = new TreeSet<Tile>(new Comparator<Tile>(){
        	public int compare(Tile t1, Tile t2)
        	{
        		int retval = t1.getY() - t2.getY();
        		if (retval == 0)
        		{
        			retval = t1.getX() - t2.getX();
        		}
        		return retval;
        	}
        });
   
        Edge oneN, oneE, oneW, oneS; */
        
    	class XComparator implements Comparator<Tile>{
    		public int compare(Tile p1, Tile p2){
    			return p1.getLocation().x - p2.getLocation().x;
    		}
    	}
    	class YComparator implements Comparator<Tile>{
    		public int compare(Tile p1, Tile p2){
    			return p1.getLocation().y - p2.getLocation().y;
    		}
    	}
    	
        //create the tiles
    	
        Tile one = (new Tile(new Point(0, 0), new Room(null)));
        Tile two = (new Tile(new Point(0, 1), new Room(null)));
        Tile three = (new Tile(new Point(1, 0), new Room(null)));
        Tile four = (new Tile(new Point(1, 1), new Room(null)));
        
        Edge oneN, oneE, oneW, oneS, twoN, twoE, twoS, threeW, threeS, threeE, fourS, fourE;
        Item key = new Item("key", 0);
        
        oneN = new Edge(null, one, false);
        one.setEdge("north", oneN);
        oneW = new Edge(null, one, false);
        one.setEdge("west", oneW);
        oneE = new Exit(one, two, false, key);
        one.setEdge("east", oneE);
        two.setEdge("west", oneE);
        oneS = new Edge(one, three, true);
        one.setEdge("south", oneS);
        three.setEdge("north", oneS);
        twoN = new Edge(null, two, false);
        two.setEdge("north", twoN);
        twoE = new Edge(null, two, false);
        two.setEdge("east", twoE);
        twoS = new Edge(two, four, true);
        two.setEdge("south", twoS);
        four.setEdge("north", twoS);
        threeW = new Edge(null, three, false);
        three.setEdge("west", threeW);
        threeS = new Edge(three, null, false);
        three.setEdge("south", threeS);
        threeE = new Edge(three, four, true);
        three.setEdge("east", threeE);
        four.setEdge("west", threeE);
        fourS = new Edge(four, null, false);
        four.setEdge("south", fourS);
        fourE = new Edge(four, null, false);
        four.setEdge("east", fourE);
        
       
        /*int minX = Collections.min(tiles, new XComparator()).getLocation().x;
        int maxX = Collections.max(tiles, new XComparator()).getLocation().x;
        int minY = Collections.min(tiles, new YComparator()).getLocation().y;
        int maxY = Collections.max(tiles, new YComparator()).getLocation().y;*/
        
        //not necessary, but this method makes the top or left tile tile1
        //every tile is responsible for setting
        /*for (Tile t : tiles){
        	if (t.getX() == minX){
        		t.setEdge("west", new Edge(null, t, false));
        	}
        	else {
        	}
        }*/
        //oneN = new Edge(null, two, false);
        //oneE = new Edge()
        Character c = new Character("bob", 0, 0, one);
        currentTile = one;
        one.addCharacter(c);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
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
        System.out.println(currentTile.getLocation().toString());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
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
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        //Try to leave current room
        if (currentTile.canMove(direction)){
        	currentTile = currentTile.moveCharacter(direction);
         	System.out.println("Current Tile: " + currentTile.getLocation().toString());
        }
        else {
        	System.out.println("Cant go that way!");
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
    	DarrellTest dt = new DarrellTest();
    	dt.play();
    }
}
