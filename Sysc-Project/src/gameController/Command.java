package gameController;

public abstract class Command {
	/**
	 * Execute the command
	 * @return true if execution can be undone/redone
	 */
	public abstract boolean execute();
}
