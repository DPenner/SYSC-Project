package graphics2D;

import gameCore.Tile;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapController extends MouseAdapter {
	private MapView view;
	private Tile currentTile;
	
	public MapController(MapView view){
		this.view = view;
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
	}
	
	
	//------------Mouse Adapter------------//
	
	@Override
	public void mouseMoved(MouseEvent e){
		Tile hoverTile = view.getTile(e.getPoint());
		if (hoverTile == currentTile) return; //no change
		
		unHighLightCurrentTile();
		currentTile = hoverTile;
		highLightCurrentTile();
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		currentTile = view.getTile(e.getPoint());
		highLightCurrentTile();
	}
	
	@Override
	public void mouseExited(MouseEvent e){
		unHighLightCurrentTile();
		currentTile = null;
	}
	
	//------------Mouse helpers------------//
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
}
