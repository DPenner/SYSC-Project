package gameController;

import java.awt.event.ActionEvent;

public class CommandEvent extends ActionEvent{
	private Command commandSource;
	
	public CommandEvent(Command source, int id, String command) {
		super(source, id, command);
		
		commandSource = source;
	}
	
	public Command getCommand() {
		return commandSource;
	}
	
	private static final long serialVersionUID = 1L;
	
}
