package graphics2D;
/**
* TextOutputPanel is a graphical output area for any messages to the user
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
* @version 2.0
*
*/
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextOutputPanel extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;

	private static TextOutputPanel oneInstance;
	private JTextArea displayArea;

	TextOutputPanel()
	{
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(530, 150));
        
		this.add(scrollPane);

	}
	/**
	 * TextOutputPanel uses the singleton pattern, this method
	 * returns the one instance of TextOutputPanel or creates a 
	 * new one if it's null and returns the new instance
	 * @return the instance of TextOutputPanel
	 */
	public static TextOutputPanel getTextOutputPanel() 
	{
		if(oneInstance == null) oneInstance = new TextOutputPanel();
		return oneInstance;
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{
		if(arg instanceof String) 
		{
			displayArea.append((String) arg + "\n");
			displayArea.setCaretPosition(displayArea.getDocument().getLength());
		}
	}
}
