package gameCore;
/**
 * A Item is a basic unit that can be carried in the Inventory. 
 *  
 * @author Group D
 * @author Main author: Karen Madore
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
public class Item {
	//------------Fields------------//
	private String name;
	private int weight;
	
	//------------Constructors------------//
	/**
	 * Construct the item
	 * @param name
	 * @param weight
	 */
	public Item(String name, int weight){
		this.name=name;
		this.weight=weight;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Item)
		{
			Item i = (Item) obj;
			if(this.name.equals(i.name))
				if(this.weight == i.weight)
					return true;
		}
		return false;
	}
	//------------Getters------------//
	/**
	 * Method to get the weight of the item.
	 * 
	 * @return	the weight of the item 
	 */
	public int getWeight(){
		return weight;
	}
	
	/**
	 * Method to the name of the item
	 * 
	 * @return	the name of the item
	 */
	public String getName(){
		return name;
	}

}
