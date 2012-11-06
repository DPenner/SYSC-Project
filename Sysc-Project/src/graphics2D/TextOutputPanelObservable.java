package graphics2D;


import java.util.Observable;

public class TextOutputPanelObservable extends Observable {
	public TextOutputPanelObservable()
	{
		this.addObserver(TextOutputPanel.getTextOutputPanel());
	}
	public void printMessage(String s)
	{
		this.setChanged();
		notifyObservers(s);
	}
}