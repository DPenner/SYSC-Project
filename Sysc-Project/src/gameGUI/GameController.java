package gameGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the game GUI 
 * 
 * @author Group D
 * @author Main author: Karen Madore
 * 
 * Group D Members
 * ---------------
 * Karen Madore
 * Trang Pham
 * Darrell Penner
 * 
 *
 * @version 1.0
 */
public class GameController implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.printf(event.getActionCommand());
		
	}
	
}
