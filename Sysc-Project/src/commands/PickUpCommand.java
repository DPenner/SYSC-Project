package commands;

import java.awt.KeyboardFocusManager;

import javax.swing.JOptionPane;

public class PickUpCommand extends Command{
	private String itemName;
	@Override
	public boolean execute() {
		
		itemName = JOptionPane.showInputDialog("Pick up item:\nPlease enter name of item");
        
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

}
