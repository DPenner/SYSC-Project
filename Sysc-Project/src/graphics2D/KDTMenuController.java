package graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class KDTMenuController implements ActionListener {
	private JFrame f;
	
	public KDTMenuController(JFrame f){
		this.f=f;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		String command = e.getActionCommand();
		
		if(command.equals("Exit")){
			JFrame optionFrame= new JFrame("");
			int confirmExit = JOptionPane.showConfirmDialog(optionFrame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
			
			if(confirmExit==JOptionPane.YES_OPTION){
				f.dispose();
				
				System.exit(0);	
			}
			
		}
	}

}
