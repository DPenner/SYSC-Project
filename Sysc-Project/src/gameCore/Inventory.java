package gameCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Inventory implements Iterable<Item>, Serializable   {
	private static final long serialVersionUID = 1L;

	//------------Fields------------//
	private List<Item> items;
	

	//------------Constructors------------//
	public Inventory(){
		items= new ArrayList<Item>();
	}
	
	//------------getters/setters------------//
	public void addItem(Item i){
		items.add(i);
	}
	
	/*
	 * Given the index in the inventory, retrieve and return the Item from the Inventory.
	 * @return item at the given index
	 */
	public Item getItem(int index){
		if(index>items.size()-1){
			return null;
		}else{
			return items.get(index);
		}
	}
	
	public int getTotalWeight(){
		int total =0;
		for(Item i: items){
			total +=i.getWeight();
		}
		return total;
	}
	
	public void removeItem(Item i){
		items.remove(i);
	}
	
	//------------Status------------//
	/*
	 * Get the number of items in the inventory
	 * @return count of items contained in the inventory
	 */
	public int size(){
		return items.size();
	}
	
	public boolean containsItem(Item i){
		if(items.contains(i)){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * Query for the index of the item in the inventory by name
	 * @return index of item found in the inventory, -1 if item is not in the inventory
	 */
	
	public int getIndex(String s){
		int index=0;
		for (Item i: items){
			if(i.getName().equals(s)) return index;
			index++;
		}
		return -1;
	}
	
	public boolean isEmpty(){
		if (items.size()==0){
			return true;
		}else{
			return false;
		}
	}
	
	//------------standard/Misc------------//
	/*
	 * Returns the string representation of the inventory
	 * @return	names of all items in the inventory
	 */
	@Override
	public String toString(){
		String s="";
		for(Item i: items){
			s+=i.toString() +", ";
		}
		return s;
	}

	@Override
	public Iterator<Item> iterator() {
		return items.listIterator();
	}
}

