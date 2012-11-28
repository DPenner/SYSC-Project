package levelEditor;

import java.awt.event.KeyEvent;

import graphics2D.MapView;

import javax.swing.*;

public class LevelEditorView extends JFrame{

	private MapView editorView;
	private ModeSwitcher switcher;
	
	public LevelEditorView(LevelEditor editor){
		super("Level Editor");

		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		
		initFileMenu(menuBar);
		initModeMenu(menuBar);
		
		this.setJMenuBar(menuBar);
		
		editorView = new MapView();
		
		new EditorController(editor, this);
		
		this.add(editorView);	
		this.setVisible(true);
	}
	
	private void initModeMenu(JMenuBar menuBar){
		JMenu modeMenu = new JMenu("Mode");
		ButtonGroup group = new ButtonGroup();
		
		for (Mode m : Mode.values()){
			JMenuItem modeItem = new JRadioButtonMenuItem(m.toString());
			modeItem.setActionCommand(m.toString());
			modeItem.setAccelerator(m.getShortCut());
			group.add(modeItem);
			modeMenu.add(modeItem);
			
			if (m == Mode.TILE){
				group.setSelected(modeItem.getModel(), true);
			}
		}
		
		switcher = new ModeSwitcher(group);
		menuBar.add(modeMenu);
	}
	
	private void initFileMenu(JMenuBar menuBar){
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem help = new JMenuItem("Help");
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.VK_CONTROL));
		
		fileMenu.add(help);
		
		menuBar.add(fileMenu);
	}
	
	public MapView getEditorView(){
		return editorView;
	}
	public ModeSwitcher getModeSwitcher(){
		return switcher;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LevelEditorView(new LevelEditor());
	}

}
