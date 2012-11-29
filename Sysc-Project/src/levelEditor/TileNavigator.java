package levelEditor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.AbstractList;

import javax.swing.*;

import gameCore.*;


class TileNavigator extends JDialog {
	private String type = "Item";
	private String names[] = {"Name: ", "Weight: "};
	private String initialValues[] = {"New Item", "0"};

	private TileInfoPanel itemInfos;
	private JButton addButton;
	private JButton removeButton;
	private JButton saveButton;
	private JButton cancelButton;
	
	public TileNavigator(Frame owner, Tile tile){
		super(owner, "Items on the tile", true);
		
		this.setLayout(new BorderLayout());
		itemInfos = new TileInfoPanel();
		JScrollPane scrollPane = new JScrollPane(itemInfos);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		addButton = new JButton("Add Item");
		removeButton = new JButton("Remove Item");
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				itemInfos.add(type, names, initialValues);
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
		
		this.setSize(800, 500);
		this.setVisible(true);
	}
}
