package levelEditor;

enum NavigatorEditingButton {
	ADD_ITEM ("Add Item", "Adds a generic item to the tile."),
	ADD_WEAPON ("Add Weapon", "Adds a weapon to the tile."),
	ADD_PLAYER ("Add Player", "Adds a player to the tile. Note that only one player may exist in the game."),
	ADD_MONSTER ("Add Monster", "Adds a monster to the tile. Only one character can exist on a tile."),
	ADD_WALL("Add Wall", "Adds a wall in the set direction. Only one wall/door per direction."),
	ADD_DOOR("Add Door", "Adds a door in the set direction. Only one wall/door per direction."),
	REMOVE_SELECTION ("Remove Selection", "Removes the selected object (if one is selected)"),
	REMOVE_ALL("Remove All", "Removes everything associated with the Tile. " +
			"Note this also removes walls and doors which may be associated with other Tiles"),
	SAVE_TILE ("Save Tile", "Saves all changes made to the Tile"),
	RESET_TILE ("Reset Tile", "Resets the Tile to the last saved state"),
	CLOSE ("Close", "Close the navigator");
	
	private String name;
	private String tooltip;
	NavigatorEditingButton(String name, String tooltip){
		this.name = name;
		this.tooltip = tooltip;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public static boolean isNavigatorEditingbutton(String name){
		for (NavigatorEditingButton neb : NavigatorEditingButton.values()){
			if (neb.toString().equals(name)){
				return true;
			}
		}
		return false;
	}
	public static NavigatorEditingButton getNavigatorEditingButton(String name){
		for (NavigatorEditingButton neb : NavigatorEditingButton.values()){
			if (neb.toString().equals(name)){
				return neb;
			}
		}
		throw new IllegalArgumentException("Button name not found");
	}
	public String getToolTip(){
		return tooltip;
	}
}
