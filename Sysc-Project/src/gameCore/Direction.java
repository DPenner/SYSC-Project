package gameCore;

import java.io.Serializable;

public enum Direction implements Serializable   {
	NORTH("north"), EAST("east"), SOUTH("south"), WEST("west");
	private static final long serialVersionUID = 1L;
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

	public Direction getRightDirection() {
		switch(this)
    	{
    	case NORTH:
    		return EAST;
    	case SOUTH:
    		return WEST;
    	case WEST:
    		return NORTH;
    	case EAST:
    		return SOUTH;
    	default:
    		return this;
    	}
	}
	
	public Direction getLeftDirection() {
		return this.getRightDirection().getOppositeDirection();
	}
}
