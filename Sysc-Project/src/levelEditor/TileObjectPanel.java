package levelEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import levelEditor.TileObjectDisplayData.TileObjectDisplayDatum;

/**
 * TileObjectPanel helps the TileInfoPanel by handling the display for a single item related a tile
 * @author DarrellPenner
 *
 */
class TileObjectPanel extends JPanel {
	
	private JPanel mainPanel;
	private JPanel typePanel;
	public static final Color HIGH_LIGHT_COLOR = Color.decode("0x2277AA");
	public static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
	private static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
	
	TileObjectDisplayData displayData;
	
	TileObjectPanel(TileInfoPanel parentPanel, TileObjectDisplayData data){	
		this.displayData = data;
		TileObjectController toc = new TileObjectController(parentPanel, this);
		
		//main panel set up
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2));
		
		//Add datum visually, and to controller
		for (TileObjectDisplayDatum datum : data){
			JTextField textBox = new JTextField(datum.getValue());
			mainPanel.add(new JLabel(datum.getName() + ": "));
			mainPanel.add(textBox);
			toc.addTextBoxDatumPair(textBox, datum);
		}
		
		//typePanel setup
		typePanel = new JPanel();
		Font typeFont = TITLE_FONT;
		JLabel typeLabel = new JLabel(data.getTypeName());
		typeLabel.setFont(typeFont);
		typePanel.add(typeLabel);
		
		//this panel set up
		this.setLayout(new BorderLayout());
		
		this.add(typePanel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		mainPanel.setPreferredSize(new Dimension(0, 30*data.size() + typePanel.getHeight()));
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
}