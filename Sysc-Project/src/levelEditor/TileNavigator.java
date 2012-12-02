package levelEditor;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import gameCore.*;

/**
 * TileNavigator helps edit a specific Tile including the Edges and contents.
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

class TileNavigator extends JDialog {
	//extends JDialog so that it can be modal (ie blocking) -> once opened, this frame must be dealt with

	private TileInfoPanel itemInfos;
	private JComboBox directionBox;
	
	public TileNavigator(Frame owner, LevelEditor editor, Tile tile){
		super(owner, "Tile Navigator", true);
		
		this.setLayout(new BorderLayout());
		itemInfos = new TileInfoPanel(tile);
		JScrollPane scrollPane = new JScrollPane(itemInfos);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.add(scrollPane, BorderLayout.CENTER);
		this.setSize(800, 600);
		NavigatorController nc = new NavigatorController(editor, this, tile);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
		buttonPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//combo-box
		directionBox = new JComboBox(Direction.values());
		buttonPane.add(directionBox);
		directionBox.addActionListener(nc);
		
		//buttons
		for (NavigatorEditingButton neb : NavigatorEditingButton.values()){
			JButton button = new JButton(neb.toString());
			button.setToolTipText(neb.getToolTip());
			nc.addButton(button);
			buttonPane.add(button);
		}
		
		JScrollPane buttonScroll = new JScrollPane(buttonPane);
		buttonScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		buttonScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(buttonScroll, BorderLayout.EAST);
		
		this.setVisible(true);
	}
	
	protected TileInfoPanel getTileInfoPanel(){
		return itemInfos;
	}
	
	protected Direction getSelectedDirection(){
		return (Direction) directionBox.getSelectedItem();
	}
}
