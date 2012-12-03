package levelEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

class NavigatorController implements ActionListener {

	private LevelEditor editor;
	private TileNavigator navigator;
	private TileInfoModel infoModel;
	private TileInfoPanel infoPanel;
	private List<JButton> buttons;
	
	public NavigatorController(LevelEditor editor, TileNavigator navigator, TileInfoModel infoModel, TileInfoPanel infoPanel){
		this.editor = editor;
		this.navigator = navigator;
		this.infoModel = infoModel;
		this.infoPanel = infoPanel;
		buttons = new ArrayList<JButton>();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (NavigatorEditingButton.isNavigatorEditingbutton(e.getActionCommand())){
			switch (NavigatorEditingButton.getNavigatorEditingButton(e.getActionCommand())){
			case ADD_ITEM:
				infoModel.addItem();
				break;
			case ADD_WEAPON:
				infoModel.addWeapon();
				break;
			case ADD_MONSTER:
				infoModel.addMonster();
				break;
			case ADD_PLAYER:
				infoModel.addPlayer();
				break;
			case ADD_WALL:
				infoModel.addWall(navigator.getSelectedDirection());
				break;
			case ADD_DOOR:
				infoModel.addDoor(navigator.getSelectedDirection()); 
				break;
			case RESET_TILE:
				infoModel.reloadTile();
				break;
			case REMOVE_SELECTION:
				if (infoPanel.hasSelection()){
					infoModel.remove(infoPanel.getSelected());
				}
				else {
					JOptionPane.showMessageDialog(navigator, "There is nothing selected!");
				}
				break;
			case REMOVE_ALL:
				infoModel.clearData();
				break;
			case SAVE_TILE:
				infoModel.saveTile();
				break;
			case CLOSE:
				if (infoPanel.isDirty()){
					int opt = JOptionPane.showConfirmDialog(navigator, "There are unsaved changes to the tile, would you like to save them?");
					if (opt == JOptionPane.OK_OPTION){
						infoModel.saveTile();
						navigator.dispose();
					}
					else if (opt == JOptionPane.NO_OPTION){
						navigator.dispose();
					} // if option is cancel, than do nothing
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
		boolean hasCharacter = infoModel.hasCharacter();
		
		for (JButton button : buttons){
			switch (NavigatorEditingButton.getNavigatorEditingButton(button.getActionCommand())){
			case ADD_WALL:
			case ADD_DOOR:
				button.setEnabled(!infoModel.hasEdge(navigator.getSelectedDirection()));
				break;
			case CLOSE:
			case ADD_WEAPON:
			case ADD_ITEM:
			case REMOVE_SELECTION:   //remove selection always enabled to avoid extra complexity of listening to a new object for only one button
				button.setEnabled(true);
				break;
			case ADD_MONSTER:
				button.setEnabled(!hasCharacter);
				break;
			case ADD_PLAYER:
				boolean hasPlayerElseWhere = editor.hasPlayer() && !(infoModel.hasPlayer());
				button.setEnabled(!hasCharacter && !hasPlayerElseWhere);
				break;
			case REMOVE_ALL:
				button.setEnabled(!infoModel.isEmpty());
				break;
			case RESET_TILE:
			case SAVE_TILE:
				button.setEnabled(infoPanel.isDirty());
				break;
			}
		}
	}
}
