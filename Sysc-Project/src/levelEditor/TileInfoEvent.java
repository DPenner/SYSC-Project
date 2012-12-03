package levelEditor;

import java.util.EventObject;

public class TileInfoEvent extends EventObject {

	private TileObjectDisplayData displayData; 
	
	public TileInfoEvent(Object source, TileObjectDisplayData displayData) {
		super(source);
		this.displayData = displayData;
	}
	
	public TileObjectDisplayData getDisplayData(){
		return displayData;
	}
}
