package gameCore;

import java.util.ArrayList;
import java.util.List;
/**
 * Inventory is a collection of Items.  Items can be Weapons in this first revision.
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

public class Inventory {
	private List<Item> items;
	/*
	 * Constr
	 */
	public Inventory(){
		items= new ArrayList<Item>();
	}
	
	public void addItem(Item i){
		items.add(i);
	}
	
	public void removeItem(Item i){
		items.remove(i);
	}
	
	public boolean containsItem(Item i){
		if(items.contains(i)){
			return true;
		}else{
			return false;
		}
	}
	public boolean isEmpty(){
		if (items.size()==0){
			return true;
		}else{
			return false;
		}
	}
	
	public int getTotalWeight(){
		int total =0;
		for(Item i: items){
			total +=i.getWeight();
		}
		return total;
	}
}
