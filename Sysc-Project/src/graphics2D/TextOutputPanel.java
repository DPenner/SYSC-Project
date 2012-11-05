package graphics2D;

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
        scrollPane.setPreferredSize(new Dimension(590, 150));
        
		this.add(scrollPane);

	}
	
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
