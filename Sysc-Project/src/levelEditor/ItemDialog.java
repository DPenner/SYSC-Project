package levelEditor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.AbstractList;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import gameCore.*;

class ItemDialog extends JDialog {
	
	private static final Color highlightColor = Color.decode("0x2277AA");
	//private JPanel itemPanel;
	//private List<ItemInfo> itemInfos;
	private ItemInfos itemInfos;
	private JButton addButton;
	private JButton removeButton;
	private JButton saveButton;
	private JButton cancelButton;
	//private ItemInfo selectedInfo;
	
	public ItemDialog(Frame owner, Tile tile){
		super(owner, "Items on the tile", true);
		
		this.setLayout(new BorderLayout());
		itemInfos = new ItemInfos();
		
		for (Item item : tile.getInventory()){
			itemInfos.add(new JLabel(item.getName()));
		}
		this.add(itemInfos, BorderLayout.CENTER);
		
		addButton = new JButton("Add Item");
		removeButton = new JButton("Remove Item");
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				itemInfos.add("New Item", 0);
			}
		});
		
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				itemInfos.removeSelected();
			}
		});
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
		buttonPane.add(addButton);
		buttonPane.add(removeButton);
		buttonPane.add(saveButton);
		buttonPane.add(cancelButton);
		buttonPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(buttonPane, BorderLayout.EAST);
		
		this.setSize(300, 300);
		this.setVisible(true);
	}
	
	private class ItemInfos extends JPanel implements MouseListener{
		List<ItemInfo> itemInfos;
		ItemInfo selectedInfo;
		
		protected ItemInfos(){
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			this.addMouseListener(this);
			itemInfos = new ArrayList<ItemInfo>();
		}
		
		//TEMP, not working
		protected void add(String name){
			this.add(new JLabel(name));
		}
		
		protected void add(String name, int weight){
			ItemInfo newInfo = new ItemInfo(name, weight);
			this.add(newInfo);
			itemInfos.add(newInfo);
			this.setSelectedInfo(newInfo);
			this.revalidate();
		}
		
		private void setSelectedInfo(ItemInfo info){
			clearSelected();
			selectedInfo = info;
			selectedInfo.highlight();
		}
		
		private ItemInfo clearSelected(){
			if (selectedInfo != null){
				selectedInfo.unHighlight();
			}
			ItemInfo retval = selectedInfo;
			selectedInfo = null;
			return retval;
		}
		protected void removeSelected(){
			ItemInfo removed = clearSelected();
			itemInfos.remove(removed);
			this.remove(removed);
			this.revalidate();
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getButton() == MouseEvent.BUTTON1 && !itemInfos.isEmpty()){
				int index = arg0.getPoint().y/(this.getHeight()/itemInfos.size());
				setSelectedInfo(itemInfos.get(index));
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {	
		}
		
		private class ItemInfo extends JPanel {
			
			//public attributes, but they are only visible to the upper class since its nested and private
			public JTextField nameBox;
			public JTextField weightBox;
			
			ItemInfo(String name, int weight){
				this.setLayout(new GridLayout(0, 2));
				this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				initPanel("Name: ", nameBox, name);
				initPanel("Weight: ", weightBox, Integer.toString(weight));
			}
			
			protected void initPanel(String label, JTextField textBox, String initialText){
				Dimension defaultTextSize = new Dimension(100, 20);
				textBox = new JTextField(initialText);
				textBox.setPreferredSize(defaultTextSize);
				textBox.setMaximumSize(defaultTextSize);
				JLabel name = new JLabel(label);
				this.add(name);
				this.add(textBox);
			}
			
			protected void highlight(){
				this.setBackground(highlightColor);
			}
			protected void unHighlight(){
				this.setBackground(Color.GRAY);
			}
		}

		
	}
}
