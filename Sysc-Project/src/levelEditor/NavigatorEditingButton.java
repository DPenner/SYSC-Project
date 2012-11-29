package levelEditor;

enum NavigatorEditingButton {
	ADD_ITEM ("Add Item"),
	ADD_WEAPON ("Add Weapon"),
	ADD_PLAYER ("Add Player"),
	ADD_MONSTER ("Add Monster"),
	ADD_WALL("Add Wall"),
	ADD_DOOR("Add Door"),
	REMOVE_SELECTION ("Remove Selection"),
	REMOVE_ALL("Remove All"),
	SAVE_TILE ("Save Tile"),
	RESET_TILE ("Reset Tile"),
	CLOSE ("Close");
	
	private String name;
	NavigatorEditingButton(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public static NavigatorEditingButton getNavigatorEditingButton(String name){
		for (NavigatorEditingButton neb : NavigatorEditingButton.values()){
			if (neb.toString().equals(name)){
				return neb;
			}
		}
		throw new IllegalArgumentException("Button name not found");
	}
}
