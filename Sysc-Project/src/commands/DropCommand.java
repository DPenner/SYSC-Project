package commands;

import javax.swing.JOptionPane;

public class DropCommand extends Command{
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
