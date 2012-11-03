package commands;

import javax.swing.JOptionPane;

public class DropCommand extends Command{
	private String itemName;
	
	@Override
	public boolean execute() {
		itemName = JOptionPane.showInputDialog("Drop item:\nPlease enter name of item");
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

}
