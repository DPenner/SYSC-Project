package graphics2D;

import gameCore.Player;
import gameCore.Tile;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapController extends MouseAdapter implements ComponentListener {
	protected MapView view;
	protected Tile actionTile; //The tile that an action is to be performed on
	private Player player; //for future use
	
	/**
	 * Initializes the MapController, which controls the given MapView
	 * @param view The view which this controller is to control
	 */
	public MapController(MapView view){
		this.view = view;
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		view.addComponentListener(this);
	}
	
	//------------Mouse Adapter------------//
	/**
	 * HighLights tiles under which the mouse moves.
	 */
	@Override
	public void mouseMoved(MouseEvent e){
		Tile hoverTile = view.getTile(e.getPoint());
		if (hoverTile == actionTile) return; //no change
		
		performActionOnExitedTile();
		actionTile = hoverTile;
		performActionOnEnteredTile();
	}
	
	/**
	 * HighLights the tile under which the mouse entered.
	 */
	@Override
	public void mouseEntered(MouseEvent e){
		actionTile = view.getTile(e.getPoint());
		performActionOnEnteredTile();
	}
	
	/**
	 * Unhighlights any highlighed tile
	 */
	@Override
	public void mouseExited(MouseEvent e){
		performActionOnExitedTile();
		actionTile = null;
	}
	
	//------------Mouse helpers------------//
	//Highlights and unhighlights tiles if they are not null
	protected void performActionOnEnteredTile(){
		if (actionTile != null){
			view.highLight(actionTile);
		}
	}
	protected void performActionOnExitedTile(){
		if (actionTile != null){
			view.unHighLight(actionTile);
		}
	}

	//-----------Component Listeners------------//
	
	/**
	 * Empty implementation
	 */
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Empty implementation
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Ensures that the MapView displays all in its new size
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		view.setPanelBounds();
	}
	
	/**
	 * Empty implementation
	 */
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
