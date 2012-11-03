package commands;

import graphics2D.TextOutputPanel;

import java.util.Observable;

public abstract class Command extends Observable {
	public Command()
	{
		this.addObserver(TextOutputPanel.getTextOutputPanel());
		this.setChanged();
	}

	public abstract boolean execute();
}
