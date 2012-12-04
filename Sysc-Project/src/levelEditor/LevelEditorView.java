package levelEditor;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import gameLoader.Level;
import graphics2D.MapView;

import javax.swing.*;

/**
 * LevelEditorView displays the main Level Editor window
 * 
 * @author Group D
 * @author Main Author: Darrell Penner
 * 
 * Group D Members
 * ---------------
 * Karen Madore
 * Trang Pham
 * Darrell Penner
 * 
 *
 * @version 1.0
 *
 */

public class LevelEditorView extends JFrame {

	public static final String SAVE = "Save";
	public static final String HELP = "Help";
	private MapView editorView;
	//private LevelEditor controller;
	
	public LevelEditorView(){
		super("Level Editor");

		this.setLayout(new BorderLayout());
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		editorView = new MapView();
		LevelEditor controller = new LevelEditor(this);
		
		JMenuBar menuBar = new JMenuBar();	
		initFileMenu(menuBar, controller);
		this.setJMenuBar(menuBar);
		
		JScrollPane mapScroller = new JScrollPane();
		mapScroller.getViewport().setLayout(new BorderLayout());
		mapScroller.getViewport().add(editorView, BorderLayout.CENTER);
		this.add(mapScroller);
		
		this.setVisible(true);
	}
	
	private void initFileMenu(JMenuBar menuBar, LevelEditor controller){
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem help = new JMenuItem(HELP);
		JMenuItem save = new JMenuItem(SAVE);
		
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.VK_CONTROL));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.VK_CONTROL));
		help.setActionCommand(HELP);
		save.setActionCommand(SAVE);
		
		help.addActionListener(controller);
		save.addActionListener(controller);
		
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
		new LevelEditorView();
	}
}
