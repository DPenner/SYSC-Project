package levelEditor;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * TileInfoPanel displays all things editable about a given Tile
 * 
 * @author Group D
 * @author Main Author: Darrell Penner
 * 
 * Group D Members
 * ---------------
 * Karen Madore
 * Trang Pham
 * Darrell Penner
 * 
 *
 * @version 1.0
 *
 */

class TileInfoPanel extends JPanel implements Scrollable
{
	List<SingleObjectInfo> itemInfos;
	SingleObjectInfo selectedInfo;
	
	protected TileInfoPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		itemInfos = new ArrayList<SingleObjectInfo>();
	}
	
	protected void add(String type, String[] names, String[] initialValues){
		SingleObjectInfo newInfo = new SingleObjectInfo(type, names, initialValues);
		this.add(newInfo);
		itemInfos.add(newInfo);
		this.setSelectedInfo(newInfo);
		this.revalidate();
	}
	
	protected void setSelectedInfo(SingleObjectInfo info){
		clearSelected(); //only one can be selected at a time
		selectedInfo = info;
		selectedInfo.highlight();
	}
	
	private SingleObjectInfo clearSelected(){
		if (selectedInfo != null){
			selectedInfo.unHighlight();
		}
		SingleObjectInfo retval = selectedInfo;
		selectedInfo = null;
		return retval;
	}
	protected void removeSelected(){
		SingleObjectInfo removed = clearSelected();
		itemInfos.remove(removed);
		this.remove(removed);
		this.revalidate();
	}
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return 1;
	}
	
	/**
	 * This private nested class helps the TileInfoPanel by handling the display for a single item related a tile
	 * @author DarrellPenner
	 *
	 */
	private static class SingleObjectInfo extends JPanel implements MouseListener {
		
		private JTextField[] textBoxes;
		private JPanel mainPanel;
		private JPanel typePanel;
		public static final Color HIGH_LIGHT_COLOR = Color.decode("0x2277AA");
		public static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
		
		SingleObjectInfo(String type, String[] labels, String[] defaultValues){
			if (labels.length != defaultValues.length){
				throw new IllegalArgumentException("Labels and default values are meant to be parallel arrays");
			}
			int numEntries = labels.length;
			
			//main panel set up
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 2));
			
			textBoxes = new JTextField[numEntries];
			
			for (int i = 0; i < numEntries; i++){
				textBoxes[i] = new JTextField(defaultValues[i]);		
				mainPanel.add(new JLabel(labels[i]));
				mainPanel.add(textBoxes[i]);
				textBoxes[i].addMouseListener(this);	
			}
			
			//typePanel setup
			typePanel = new JPanel();
			typePanel.add(new JLabel(type));
			
			this.setLayout(new BorderLayout());
			
			this.add(typePanel, BorderLayout.NORTH);
			this.add(mainPanel, BorderLayout.CENTER);
			
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.setPreferredSize(new Dimension(200, 50*numEntries + typePanel.getHeight()));
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
		
		@Override
		public void mouseClicked(MouseEvent e) {
			//A bit of an ugly solution: Swing design has it such that a mouse click event is consumed
			//by only one component. Clicking on a text box consumed this click, so to get the entire
			//panel to become selected, this panel had to listen to the click and tell the parent to consider
			//this panel selected
			((TileInfoPanel) this.getParent()).setSelectedInfo(this);
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

	
}