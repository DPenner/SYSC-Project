package levelEditor;

import gameCore.Direction;
import gameCore.Player;
import gameCore.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

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
		switch (NavigatorEditingButton.getNavigatorEditingButton(e.getActionCommand())){
		case ADD_ITEM:
			infoPanel.add(TileObjectDisplayData.getItemDisplayData());
			break;
		case ADD_WEAPON:
			infoPanel.add(TileObjectDisplayData.getWeaponDisplayData());
			break;
		case ADD_MONSTER:
			infoPanel.add(TileObjectDisplayData.getMonsterDisplayData());
			break;
		case ADD_PLAYER:
			infoPanel.add(TileObjectDisplayData.getPlayerDisplayData());
			break;
		case ADD_WALL:
			infoPanel.add(TileObjectDisplayData.getWallDisplayData(Direction.NORTH)); //TEMP
			break;
		case ADD_DOOR:
			infoPanel.add(TileObjectDisplayData.getDoorDisplayData(Direction.NORTH));
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
			navigator.dispose();
			break;
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
			case ADD_DOOR:
				break;
			case ADD_ITEM:
				button.setEnabled(true);
				break;
			case ADD_MONSTER:
				button.setEnabled(!hasCharacter);
				break;
			case ADD_PLAYER:
				boolean hasPlayerElseWhere = editor.hasPlayer() && !(activeTile.getCharacter() instanceof Player);
				button.setEnabled(!hasCharacter || !hasPlayerElseWhere);
				break;
			case ADD_WALL:
				break;
			case ADD_WEAPON:
				button.setEnabled(true);
				break;
			case CLOSE:
				button.setEnabled(true);
				break;
			case REMOVE_ALL:
				button.setEnabled(!infoPanel.isEmpty());
				break;
			case REMOVE_SELECTION:
				button.setEnabled(infoPanel.hasSelection());
				break;
			case RESET_TILE:
				button.setEnabled(infoPanel.isDirty());
				break;
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
