package levelEditor;

import java.awt.Frame;

import javax.swing.JDialog;
import gameCore.*;

class ItemDialog extends JDialog {
	private Inventory items;
	
	public ItemDialog(Frame owner, boolean modal, Tile tile){
		super(owner, "Items on the tile", true);
		items = tile.getInventory();
		//this.add(new )
	}
}
