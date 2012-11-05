package commands;

import javax.swing.JOptionPane;

public class PickUpCommand extends Command{
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
