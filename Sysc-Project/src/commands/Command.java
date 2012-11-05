package commands;

import graphics2D.TextOutputPanelObservable;

public abstract class Command extends TextOutputPanelObservable {
	public abstract boolean execute();
	public abstract void undo();
	public abstract void redo();
}
