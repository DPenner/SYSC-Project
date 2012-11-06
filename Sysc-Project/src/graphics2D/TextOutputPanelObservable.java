package graphics2D;

<<<<<<< HEAD

import java.util.Observable;

public class TextOutputPanelObservable extends Observable {
=======
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

>>>>>>> 416f7ddd1c67c8ad15541671ddc40c61796ff67c
	public TextOutputPanelObservable()
	{
		this.addObserver(TextOutputPanel.getTextOutputPanel());
	}
<<<<<<< HEAD
	public void printMessage(String s)
	{
=======
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
>>>>>>> 416f7ddd1c67c8ad15541671ddc40c61796ff67c
		this.setChanged();
		notifyObservers(s);
	}
}
