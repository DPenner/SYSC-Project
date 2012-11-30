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
	List<TileObjectPanel> itemInfos;
	TileObjectPanel selectedInfo;
	boolean isDirty;
	
	protected TileInfoPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		itemInfos = new ArrayList<TileObjectPanel>();
		isDirty = false;
	}
	
	protected void add(TileObjectDisplayData data){
		TileObjectPanel newInfo = new TileObjectPanel(this, data);
		this.add(newInfo);
		itemInfos.add(newInfo);
		this.setSelectedInfo(newInfo);
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
	
	protected void removeSelected(){
		TileObjectPanel removed = unSelect();
		itemInfos.remove(removed);
		this.remove(removed);
		this.setDirty(true);
		this.revalidate();
	}
	protected void clear(){
		unSelect();
		for (TileObjectPanel info : itemInfos){
			this.remove(info);
		}
		itemInfos.clear();
		this.setDirty(true);
		this.revalidate();
	}
	
	protected boolean isDirty(){
		return isDirty;
	}
	protected void setDirty(boolean b){
		isDirty = b;
	}
	protected boolean isEmpty(){
		return itemInfos.isEmpty();
	}
	protected boolean hasSelection(){
		return selectedInfo != null;
	}
	
	protected boolean hasCharacter(){
		for (TileObjectPanel info : itemInfos){
			if (info.isCharacterPanel){
				return true;
			}
		}
		return false;
	}
	
	//------------Scrolling support-----------//
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