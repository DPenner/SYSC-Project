package gameCore;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Item> items;
	
	public Inventory(){
		items= new ArrayList<Item>();
	}
	
	public void addItem(Item theItem){
		items.add(theItem);
	}
}
