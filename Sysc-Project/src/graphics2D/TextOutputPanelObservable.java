package graphics2D;

/**
* TextOutputPanelObservable is added for 
* convenience so that classes don't have
* to add themselves as an observer to the 
* TextOutputPanel and can print the messages 
* easier
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
import java.util.Observable;

public class TextOutputPanelObservable extends Observable {

	public TextOutputPanelObservable()
	{
		this.addObserver(TextOutputPanel.getTextOutputPanel());
	}
	/**
	 * Prints a message to the TextOutputPanel
	 * @param s the string to print
	 */
	public void printMessage(String s)
	{
		/* this class's usefulness is that it removes the 
		 * duplication due to having set the instance as changed
		 * for every single message
		 */
		this.setChanged();
		notifyObservers(s);
	}
}
