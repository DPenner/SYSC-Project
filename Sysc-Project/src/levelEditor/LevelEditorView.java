package levelEditor;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import graphics2D.MapView;

import javax.swing.*;

public class LevelEditorView extends JFrame {

	private MapView editorView;
	
	public LevelEditorView(LevelEditor editor){
		super("Level Editor");

		this.setLayout(new BorderLayout());
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		
		initFileMenu(menuBar);
		
		this.setJMenuBar(menuBar);
		
		editorView = new MapView();
		new EditorController(editor, this);
		
		JScrollPane mapScroller = new JScrollPane();
		mapScroller.getViewport().setLayout(new BorderLayout());
		mapScroller.getViewport().add(editorView, BorderLayout.CENTER);

		this.add(mapScroller);
		
		this.setVisible(true);
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LevelEditorView(new LevelEditor());
	}

}
