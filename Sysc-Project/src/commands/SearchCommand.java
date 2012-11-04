package commands;

public class SearchCommand extends Command{
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
