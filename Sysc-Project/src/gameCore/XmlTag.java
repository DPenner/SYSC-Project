package gameCore;
/**
* All xml keywords (tags and attributes)
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
public enum XmlTag {
	
	//tags
	LEVEL("level"), TIMER("timer"), GRID("grid"), 
	ROOM_SECTION("rooms"), ROOM("room"),  
	EXIT_SECTION("exits"), EXIT("exit"), KEY("key"),
	CHARACTER("character"), 
	IVENTORY("inventory"), ITEM("item"),
	TILE("tile"),
	
	//attributes
	VALUE("value"), WIDTH("width"), HEIGHT("height"),
	X("x"), Y("y"), TYPE("type"), NAME("name"),
	
	//special attribute values
	ELEVATOR("elevator"), LOCKED("locked");
	
    // The xml tag or attribute string.
    private String tag;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    XmlTag(String tag)
    {
        this.tag = tag;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return tag;
    }
}
