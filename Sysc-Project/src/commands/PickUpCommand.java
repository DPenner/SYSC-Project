package commands;
/**
* Pick up command handles the player trying to pick up an item from a tile
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
import javax.swing.JOptionPane;

public class PickUpCommand extends Command{
	private static final long serialVersionID = 1;
	
	private String itemName;
	@Override
	public boolean execute() {
		String[] itemnames = CommandController.getPlayer().searchForItemOnGround().split(", ");
		
		itemName =  (String)JOptionPane.showInputDialog(
		           			null,
		                    "Select item to pick up:\n",
		                    "Pick Up Item",
		                    JOptionPane.QUESTION_MESSAGE,
		                    null,
		                    itemnames,
		                    itemnames[0]);

		return pickUp(itemName);
	}

	/**
	 * pick up an item and put it in the player inventory
	 * @param itemName name of the item to pick up
	 * @return true if the item was successfully picked up
	 */
	public boolean pickUp(String itemName)
	{
		if(!CommandController.getPlayer().pickUpItem(itemName))
        {
        	printMessage(itemName + " is not on the ground. Can't pick up what's not there.");
        	return false;
        }
        else
        {
        	printMessage("Picked up " + itemName);
        }
        return true;
	}
	@Override
	public void undo() {
		DropCommand d = new DropCommand();
		d.drop(itemName);
	}

	@Override
	public void redo() {
		pickUp(itemName);
	}

}
