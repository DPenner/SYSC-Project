package commands;
/**
* Drop command handles player trying to drop an item.
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
* @version 2.0
*
*/
import java.io.Serializable;

import javax.swing.JOptionPane;

public class DropCommand extends Command implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private String itemName;
	
	@Override
	public boolean execute() {
		String[] itemnames = CommandController.getPlayer().viewInventory().split(", ");
		
		itemName =  (String)JOptionPane.showInputDialog(
		           			null,
		                    "Select item to pick up:\n",
		                    "Pick Up Item",
		                    JOptionPane.QUESTION_MESSAGE,
		                    null,
		                    itemnames,
		                    itemnames[0]);
		return drop(itemName);
	}

	@Override
	public void undo() {
		PickUpCommand p = new PickUpCommand();
		p.pickUp(itemName);
		
	}

	/**
	 * Drops item on the polayer's current position
	 * @param itemname the name of the item to drop
	 * @return true if the item was successfully dropped
	 */
	public boolean drop(String itemname) {
		if(!CommandController.getPlayer().drop(itemname))
        {
        	printMessage("Item is not in your inventory. Can't drop what you don't have.");
        	return false;
        }
        else
        {
        	printMessage("Dropped " + itemname);
        }
        return true;
	}

	@Override
	public void redo() {
		drop(itemName);
	}

}
