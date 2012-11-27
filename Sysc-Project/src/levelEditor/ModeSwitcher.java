package levelEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

class ModeSwitcher implements ActionListener {
	Mode currentMode;
	
	protected ModeSwitcher(ButtonGroup modes){
		this(modes, Mode.TILE);
	}
	
	protected ModeSwitcher(ButtonGroup modes, Mode startMode){
		if (startMode == null){
			throw new IllegalArgumentException();
		}
		currentMode = startMode;
		
		Enumeration<AbstractButton> buttons = modes.getElements();
		while (buttons.hasMoreElements()){
			buttons.nextElement().addActionListener(this);
		}
	}
	
	private void switchMode(Mode newMode){
		if (newMode == null){
			throw new IllegalArgumentException();
		}
		currentMode = newMode;
	}
	
	public Mode getMode(){
		return currentMode;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switchMode(Mode.getMode(arg0.getActionCommand()));
	}
}
