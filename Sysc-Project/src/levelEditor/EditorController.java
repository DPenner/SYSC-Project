package levelEditor;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;

import gameCore.*;

import graphics2D.MapView;

class EditorController extends MouseAdapter implements ComponentListener {
	
	LevelEditorView levelEditorFrame;
	MapView editorView;
	ModeSwitcher switcher;
	LevelEditor editor;
	
	public EditorController(LevelEditor editor, LevelEditorView levelEditorFrame){
		this.levelEditorFrame = levelEditorFrame;
		this.editorView = levelEditorFrame.getEditorView();
		this.switcher = levelEditorFrame.getModeSwitcher();
		this.editor = editor;
		editorView.addMouseListener(this);
		editorView.addComponentListener(this);
	}
	

	//------------Mouse Events------------//
	
	@Override
	public void mouseClicked(MouseEvent e){
		if (e.getButton() == MouseEvent.BUTTON1){
			processClick(switcher.getMode(), e.getPoint());
		}
	}
	
	private void processClick(Mode mode, Point offsettedLocation){
		boolean objectExists = hasObject(mode, offsettedLocation);
		boolean tileExists = hasObject(Mode.TILE, offsettedLocation);
		
		switch (mode){
		case TILE:
			tileClick(offsettedLocation, objectExists);
			break;
		case EDGE:
			break;
		case EXIT:
			break;
		case ITEM:
			if (tileExists){
				itemClick(offsettedLocation, objectExists);
			}
			break;
		case MONSTER:
			if (tileExists){
				monsterClick(offsettedLocation, objectExists);
			}
			break;
		case PLAYER:
			if (tileExists){
				playerClick(offsettedLocation, objectExists);
			}
			break;
		case WEAPON:
			break;
		default:
			break;
		
		}
	}
	
	private void tileClick(Point offsettedLocation, boolean removeObject){
		Point tileLocation = editorView.getTileLocation(offsettedLocation);
		
		if (removeObject){
			Tile removingTile = editor.getTile(tileLocation);
			editor.removeTile(removingTile);
			editorView.removeTile(removingTile);
		}
		else { //add tile
			Tile newTile = new Tile(tileLocation, new Room());
			editor.addTile(newTile);
			editorView.addTile(newTile);
		}
	}
	
	private void playerClick(Point offsettedLocation, boolean removeObject){
		editor.removePlayer();

		if (!removeObject) {
			Tile playerTile = editorView.getTile(offsettedLocation);
			editor.addPlayer("Babak", 10, 10, 10, playerTile);
		}
	}
	
	private void monsterClick(Point offsettedLocation, boolean removeObject){
		Tile monsterTile = editorView.getTile(offsettedLocation);
		
		if (removeObject){
			editor.removeMonster(monsterTile);
		}
		if (!removeObject){
			editor.addMonster("Babak", 10, 10, monsterTile);
		}
	}
	
	private void itemClick(Point offsettedLocation, boolean removeObject){
		Tile itemTile = editorView.getTile(offsettedLocation);	
		new TileNavigator(levelEditorFrame, itemTile);
	}
	
	private boolean hasObject(Mode mode, Point offsettedLocation){
		switch (mode){
		case TILE:
			return editorView.hasTile(offsettedLocation);
		case EDGE:
			break;
		case EXIT:
			break;
		case ITEM:
			return hasObject(Mode.TILE, offsettedLocation)
					&& editorView.getTile(offsettedLocation).hasItems();
		case MONSTER:
			return hasObject(Mode.TILE, offsettedLocation)
					&& editorView.getTile(offsettedLocation).getCharacter() instanceof Monster;
		case PLAYER:
			return hasObject(Mode.TILE, offsettedLocation)
					&& editorView.getTile(offsettedLocation).getCharacter() instanceof Player;
		case WEAPON:
			if (!hasObject(Mode.TILE, offsettedLocation)) return false;
			Inventory inv = editorView.getTile(offsettedLocation).getInventory();
			for (int i = 0; i < inv.size(); i++){
				if (inv.getItem(i) instanceof Weapon){
					return true;
				}
			}
			return false;
		default:
			break;
		}
		return false;
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
		editorView.setPanelBounds();
	}
	
	/**
	 * Empty implementation
	 */
	@Override
	public void componentShown(ComponentEvent e) {	
	}
}
