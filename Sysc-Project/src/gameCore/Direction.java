package gameCore;

public enum Direction {
	NORTH("north"), EAST("east"), SOUTH("south"), WEST("west");
	
    // The direction string.
    private String direction;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    Direction(String direction)
    {
        this.direction = direction;
    }
    
    public static Direction getDirection(String direction){
    	return Direction.valueOf(direction.toUpperCase());
    }
    
    /**
     * @return The command word as a string.
     */
    @Override
	public String toString()
    {
        return direction;
    }
    
    public Direction getOppositeDirection()
    {
    	switch(this)
    	{
    	case NORTH:
    		return SOUTH;
    	case SOUTH:
    		return NORTH;
    	case WEST:
    		return EAST;
    	case EAST:
    		return WEST;
    	default:
    		return this;
    	}
    }
}
