package gameLoader;
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
	EDGE_SECTION("edges"), EDGE("edge"), KEY("key"),
	CHARACTER("character"), CHARACTER_SECTION("characters"),
	ITEM_SECTION("items"), ITEM("item"),
	TILE("tile"),
	
	//attributes
	VALUE("value"), WIDTH("width"), HEIGHT("height"),
	X("x"), Y("y"), DIRECTION("direction"),
	TYPE("type"), NAME("name"), WEIGHT("weight"), 
	ATTACK("attack"), HEALTH("health"),
	LOCKED("locked"), CROSSABLE("crossable"), 
	
	//special attribute values
	ELEVATOR("elevator"), EXIT("exit"), BOUNDRIES("boundries"),
	
	EMPTYSTRING("");
	
	
    // The xml tag or attribute string.
    private String tag;
    
    /**
     * Initialise with the corresponding command string.
     * @param tag the xml tag string.
     */
    XmlTag(String tag)
    {
        this.tag = tag;
    }
    
    /**
     * @return The xml keyword as a string.
     */
    public String toString()
    {
        return tag;
    }
}
