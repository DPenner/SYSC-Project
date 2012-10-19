package gameCore;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Item> items;
	
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
