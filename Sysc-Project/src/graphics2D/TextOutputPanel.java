package graphics2D;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class TextOutputPanel extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;

	private static TextOutputPanel oneInstance;
	private JTextArea displayArea;

	TextOutputPanel()
	{
		displayArea = new JTextArea("Starting Text");
		displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(590, 125));
        
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
			displayArea.append("\n" + (String) arg);
			displayArea.setCaretPosition(displayArea.getDocument().getLength());
		}
	}
}
