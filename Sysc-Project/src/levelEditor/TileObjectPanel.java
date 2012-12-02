package levelEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import levelEditor.TileObjectDisplayData.TileObjectDisplayDatum;

/**
 * TileObjectPanel helps the TileInfoPanel by handling the display for a single item related a tile
 * @author DarrellPenner
 *
 */
class TileObjectPanel extends JPanel implements MouseListener {
	
	private List<JTextField> textBoxes;
	private JPanel mainPanel;
	private JPanel typePanel;
	public static final Color HIGH_LIGHT_COLOR = Color.decode("0x2277AA");
	public static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
	TileInfoPanel parent;
	TileObjectDisplayData displayData;
	
	TileObjectPanel(TileInfoPanel parentPanel, TileObjectDisplayData data){	
		this.parent = parentPanel;
		this.displayData = data;
		
		//main panel set up
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2));
		
		textBoxes = new ArrayList<JTextField>();
		
		for (TileObjectDisplayDatum datum : data){
			JTextField textBox = new JTextField(datum.getValue());
			textBoxes.add(textBox);	
			mainPanel.add(new JLabel(datum.getName()));
			mainPanel.add(textBox);
			textBox.addMouseListener(this);	
			
			//This bit listens to the textbox having its input changed
			textBox.getDocument().addDocumentListener(new DocumentListener(){
				public void changedUpdate(DocumentEvent arg0) {
				}

				public void insertUpdate(DocumentEvent arg0) {
					parent.setDirty(true);
				}

				public void removeUpdate(DocumentEvent arg0) {	
					parent.setDirty(true);
				}
				
			});
		}
		
		//typePanel setup
		typePanel = new JPanel();
		Font typeFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		JLabel typeLabel = new JLabel(data.getTypeName());
		typeLabel.setFont(typeFont);
		typePanel.add(typeLabel);
		
		//this panel set up
		this.setLayout(new BorderLayout());
		
		this.add(typePanel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		mainPanel.setPreferredSize(new Dimension(0, 30*data.size() + typePanel.getHeight()));
		this.addMouseListener(this);
	}
	
	protected void highlight(){
		mainPanel.setBackground(HIGH_LIGHT_COLOR);
		typePanel.setBackground(HIGH_LIGHT_COLOR);
	}
	protected void unHighlight(){
		mainPanel.setBackground(DEFAULT_COLOR);
		typePanel.setBackground(DEFAULT_COLOR);
	}
	
	protected TileObjectDisplayData getDisplayData(){
		return displayData;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//A bit of an ugly solution: Swing design has it such that a mouse click event is consumed
		//by only one component. When clicking on a text box, the text box consumed this click,
		//rather than this panel, so to get the entire panel to become selected, this panel 
		//had to listen to the click and tell the parent to consider this panel selected
		parent.setSelectedInfo(this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}