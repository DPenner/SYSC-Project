package gameLoader;
/**
* EndGameException throw to end game
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
@SuppressWarnings("serial")
public class EndGameException extends Exception{
	public EndGameException()
	{
		super();
	}
	public EndGameException(String string) {
		super(string);
	}
	public void handleExeception()
	{
		Game.KeyDispatcherController.removeKeyDispatchController();
	}
}
