package levelEditor;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public enum Mode{
	TILE ("Tile", KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.VK_CONTROL)),
	EDGE ("Wall", KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.VK_CONTROL)),
	EXIT ("Door", KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.VK_CONTROL)),
	PLAYER ("Player", KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.VK_CONTROL)),
	MONSTER ("Monster", KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.VK_CONTROL)),
	WEAPON ("Weapon", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.VK_CONTROL)),
	ITEM ("Other Item", KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.VK_CONTROL));
	
	private String modeName;
	private KeyStroke shortcut;
	
	Mode(String modeName, KeyStroke shortcut){
		this.modeName = modeName;
		this.shortcut = shortcut;
	}
	
	public KeyStroke getShortCut(){
		return shortcut;
	}
	
	public static Mode getMode(String modeString){
		for (Mode m : Mode.values()){
			if (m.toString().equals(modeString)){
				return m;
			}
		}
		throw new IllegalArgumentException("String does not match a mode");
	}
	
	@Override
	public String toString(){
		return modeName;
	}
}
