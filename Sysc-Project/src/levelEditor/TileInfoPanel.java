package levelEditor;

import gameCore.Character;
import gameCore.Direction;
import gameCore.Tile;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import gameCore.*;

/**
 * TileInfoPanel displays all things editable about a given Tile including its Edges which
 * are shared with neighbouring Tiles
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

class TileInfoPanel extends JPanel implements TileInfoListener, Scrollable
{
	
	private List<TileObjectPanel> tileObjectPanels;
	private TileObjectPanel selectedInfo;
	
	boolean isDirty;
	
	protected TileInfoPanel(TileInfoModel model){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		tileObjectPanels = new ArrayList<TileObjectPanel>();
		isDirty = false;
		
		model.addListener(this);
		model.reloadTile(); //quick way to get the model to fire necessary events for view to update without changing the model
	}
	
	private void add(TileObjectDisplayData data){
		TileObjectPanel newInfo = new TileObjectPanel(this, data);
		this.add(newInfo);
		tileObjectPanels.add(newInfo);
		this.setSelectedInfo(newInfo);
		this.setDirty(true);
		this.revalidate();
	}
	
	private void remove(TileObjectDisplayData data){
		if (data == selectedInfo.getDisplayData()){
			unSelect();
		}
		TileObjectPanel panelToRemove = null;
		for (TileObjectPanel panel : tileObjectPanels){
			if (panel.getDisplayData() == data){
				panelToRemove = panel;
				break;
			}
		}
		
		tileObjectPanels.remove(panelToRemove);
		this.remove(panelToRemove);
		this.setDirty(true);
		this.revalidate();
	}
	
	
	protected void setSelectedInfo(TileObjectPanel info){
		unSelect(); //only one can be selected at a time
		selectedInfo = info;
		selectedInfo.highlight();
	}
	
	private TileObjectPanel unSelect(){
		if (selectedInfo != null){
			selectedInfo.unHighlight();
		}
		TileObjectPanel retval = selectedInfo;
		selectedInfo = null;
		return retval;
	}
	
	protected TileObjectDisplayData getSelected(){
		if (selectedInfo == null){
			return null;
		}
		return selectedInfo.getDisplayData();
	}
	
	/**
	 * This method clears the panel
	 */
	protected void clear(){
		unSelect();
		for (TileObjectPanel info : tileObjectPanels){
			this.remove(info);
		}
		tileObjectPanels.clear();
		this.setDirty(true);
		this.revalidate();
	}
	
	/**
	 * Checks if this TileInfoPanel is dirty (changed since last save) or not
	 * @return True if the panel is dirty, false otherwise
	 */
	protected boolean isDirty(){
		return isDirty;
	}
	
	/**
	 * Sets this panel as dirty or clean
	 * @param b True for dirty, false for clean
	 */
	protected void setDirty(boolean b){
		isDirty = b;
	}
	protected boolean hasSelection(){
		return selectedInfo != null;
	}
	
	//------------TileInfoListener methods------------//
	@Override
	public void dataAdded(TileInfoEvent e) {
		add(e.getDisplayData());
	}

	@Override
	public void dataRemoved(TileInfoEvent e) {
		remove(e.getDisplayData());
	}

	@Override
	public void dataCleared(TileInfoEvent e) {
		clear();
	}
	
	@Override
	public void dataSynched(TileInfoEvent e){
		setDirty(false);
	}
	
	//------------Scrollable methods-----------//
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
}