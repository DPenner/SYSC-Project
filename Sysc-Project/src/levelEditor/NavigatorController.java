package levelEditor;

import gameCore.Direction;
import gameCore.Player;
import gameCore.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

class NavigatorController implements ActionListener {

	private LevelEditor editor;
	private TileNavigator navigator;
	private TileInfoPanel infoPanel;
	private List<JButton> buttons;
	private Tile activeTile;
	
	public NavigatorController(LevelEditor editor, TileNavigator navigator, Tile activeTile){
		this.editor = editor;
		this.navigator = navigator;
		this.activeTile = activeTile;
		infoPanel = navigator.getTileInfoPanel();
		buttons = new ArrayList<JButton>();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (NavigatorEditingButton.isNavigatorEditingbutton(e.getActionCommand())){
			switch (NavigatorEditingButton.getNavigatorEditingButton(e.getActionCommand())){
			case ADD_ITEM:
				infoPanel.addItem();
				break;
			case ADD_WEAPON:
				infoPanel.addWeapon();
				break;
			case ADD_MONSTER:
				infoPanel.addMonster();
				break;
			case ADD_PLAYER:
				infoPanel.addPlayer();
				break;
			case ADD_WALL:
				infoPanel.addWall(navigator.getSelectedDirection());
				break;
			case ADD_DOOR:
				infoPanel.addDoor(navigator.getSelectedDirection()); 
				break;
			case RESET_TILE:
				break;
			case REMOVE_SELECTION:
				infoPanel.removeSelected();
				break;
			case REMOVE_ALL:
				infoPanel.clear();
				break;
			case SAVE_TILE:
				break;
			case CLOSE:
				if (infoPanel.isDirty()){
					if (JOptionPane.showConfirmDialog(navigator, "There are unsaved changes to the tile, discard them?",
	                    "Unsaved changes", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						navigator.dispose();
					}
				}
				else navigator.dispose();
				break;
			}
		}
		
		refreshButtons();
	}
	
	protected void addButton(JButton button){
		buttons.add(button);
		button.addActionListener(this);
		refreshButtons();
	}
	
	private void refreshButtons(){
		boolean hasCharacter = infoPanel.hasCharacter();
		
		for (JButton button : buttons){
			switch (NavigatorEditingButton.getNavigatorEditingButton(button.getActionCommand())){
			case ADD_WALL:
			case ADD_DOOR:
				button.setEnabled(!infoPanel.hasEdge(navigator.getSelectedDirection()));
				break;
			case CLOSE:
			case ADD_WEAPON:
			case ADD_ITEM:
			case REMOVE_SELECTION:
				button.setEnabled(true);
				break;
			case ADD_MONSTER:
				button.setEnabled(!hasCharacter);
				break;
			case ADD_PLAYER:
				boolean hasPlayerElseWhere = editor.hasPlayer() && !(activeTile.getCharacter() instanceof Player);
				button.setEnabled(!hasCharacter && !hasPlayerElseWhere);
				break;
			case REMOVE_ALL:
				button.setEnabled(!infoPanel.isEmpty());
				break;
			case RESET_TILE:
			case SAVE_TILE:
				button.setEnabled(infoPanel.isDirty());
				break;
			}
		}
	}
	
	private void clearActiveTile(){
		editor.clearTile(activeTile);
	}
}
