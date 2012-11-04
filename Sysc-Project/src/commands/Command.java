package commands;

import gameLoader.TextOutputPanelObservable;

public abstract class Command extends TextOutputPanelObservable {
	public abstract boolean execute();
	public abstract void undo();
	public abstract void redo();
}
