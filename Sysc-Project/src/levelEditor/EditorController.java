package levelEditor;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import gameCore.*;

import graphics2D.MapController;

class EditorController extends MapController implements ActionListener {
	
	LevelEditorView levelEditorFrame;
	LevelEditor editor;
	
	public EditorController(LevelEditor editor, LevelEditorView levelEditorFrame){
		super(levelEditorFrame.getEditorView());
		this.levelEditorFrame = levelEditorFrame;
		this.view = levelEditorFrame.getEditorView();
		this.editor = editor;
	}
	

	//------------Mouse Events------------//
	@Override
	public void mouseClicked(MouseEvent e){
		Point offsettedLocation = e.getPoint();
		if (e.getButton() == MouseEvent.BUTTON1){
			boolean tileExists = view.hasTile(offsettedLocation);
			Point tileLocation = view.getTileLocation(offsettedLocation);
			
			if (tileExists){
				Tile removingTile = editor.getTile(tileLocation);
				editor.removeTile(removingTile);
				view.removeTile(removingTile);
			}
			else { //add tile
				Tile newTile = new Tile(tileLocation, new Room());
				newTile.setVisited();
				editor.addTile(newTile);
				view.addTile(newTile);
			}
		}
		else {
			itemClick(offsettedLocation);
		}
	}
	
	private void itemClick(Point offsettedLocation){
		Tile itemTile = view.getTile(offsettedLocation);	
		new TileNavigator(levelEditorFrame, editor, itemTile);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Help")){
			new HelpFrame();
		}
	}
	
}
