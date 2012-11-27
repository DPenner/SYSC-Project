package commands;
/**
* Search command handles the user trying to search the tile for items
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
public class SearchCommand extends Command{
	private static final long serialVersionID = 1;
	
	@Override
	public void undo(){}
	@Override
	public boolean execute()
	{
		printMessage(CommandController.getPlayer().searchForItemOnGround());
		return false;
	}
	@Override
	public void redo() {}
}
