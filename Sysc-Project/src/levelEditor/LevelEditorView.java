package levelEditor;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import graphics2D.MapView;

import javax.swing.*;

public class LevelEditorView extends JFrame {

	public static final String SAVE = "Save";
	public static final String HELP = "Help";
	private MapView editorView;
	private EditorController controller;
	
	public LevelEditorView(LevelEditor editor){
		super("Level Editor");

		this.setLayout(new BorderLayout());
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		editorView = new MapView();
		controller = new EditorController(editor, this);
		
		JMenuBar menuBar = new JMenuBar();	
		initFileMenu(menuBar);
		this.setJMenuBar(menuBar);
		
		JScrollPane mapScroller = new JScrollPane();
		mapScroller.getViewport().setLayout(new BorderLayout());
		mapScroller.getViewport().add(editorView, BorderLayout.CENTER);
		this.add(mapScroller);
		
		this.setVisible(true);
	}
	
	private void initFileMenu(JMenuBar menuBar){
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem help = new JMenuItem(HELP);
		JMenuItem save = new JMenuItem(SAVE);
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.VK_CONTROL));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.VK_CONTROL));
		help.setActionCommand(HELP);
		save.setActionCommand(SAVE);
		
		fileMenu.add(help);		
		fileMenu.add(save);
		menuBar.add(fileMenu);
	}
	
	public MapView getEditorView(){
		return editorView;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LevelEditorView(new LevelEditor());
	}

}
