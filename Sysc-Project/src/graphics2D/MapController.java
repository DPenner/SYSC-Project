package graphics2D;

import gameCore.Tile;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapController extends MouseAdapter implements ComponentListener {
	private MapView view;
	private Tile currentTile; //The tile that the mouse is hovering over
	
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
		if (hoverTile == currentTile) return; //no change
	
		unHighLightCurrentTile();
		currentTile = hoverTile;
		highLightCurrentTile();
	}
	
	/**
	* HighLights the tile under which the mouse entered.
	*/
	@Override
	public void mouseEntered(MouseEvent e){
		currentTile = view.getTile(e.getPoint());
		highLightCurrentTile();
	}
	
	/**
	* Unhighlights any highlighed tile
	*/
	@Override
	public void mouseExited(MouseEvent e){
		unHighLightCurrentTile();
		currentTile = null;
	}
	
	//------------Mouse helpers------------//
	//Highlights and unhighlights tiles if they are not null
	private void highLightCurrentTile(){
		if (currentTile != null){
			view.highLight(currentTile);
		}
	}
	
	private void unHighLightCurrentTile(){
		if (currentTile != null){
			view.unHighLight(currentTile);
		}
	}
	
	//-----------Component Listeners------------//
	
	/**
	* Empty implementation
	*/
	@Override
	public void componentHidden(ComponentEvent e) {
	}
	
	/**
	* Empty implementation
	*/
	@Override
	public void componentMoved(ComponentEvent e) {
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
	}
}
