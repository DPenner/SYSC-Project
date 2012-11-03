package commands;

import gameLoader.TextOutputPanelObservable;
import graphics2D.TextOutputPanel;

import java.util.Observable;

public abstract class Command extends TextOutputPanelObservable {
	public abstract boolean execute();
	public abstract void undo();
}
