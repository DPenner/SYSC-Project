package commands;
/**
* Command class for handling user commands
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
* @version 1.0
*
*/
import graphics2D.TextOutputPanelObservable;

public abstract class Command extends TextOutputPanelObservable {
	/**
	 * Executes the command
	 * @return true if the command can be undone
	 */
	public abstract boolean execute();
	/**
	 * Undo this command
	 */
	public abstract void undo();
	/**
	 * Redo this command
	 */
	public abstract void redo();
}
