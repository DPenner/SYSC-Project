package graphics2D;

import gameCore.Inventory;
import gameCore.Player;
import gameLoader.Level;
import graphics3D.FirstPersonView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import commands.CommandController;


/**
 * View for the KDT Maze
 * 
 * Container for the MAPview, InventoryPanel, PlayerStatusPanel, and TextOutputPanel
 * 
 * @author Group D
 * @author Main author: Karen Madore
 * 
 * Group D Members
 * ---------------
 * Karen Madore
 * Trang Pham
 * Darrell Penner
 * 
 *
 * @version 2.0
 *  
 */
public class KDTView {
	//------------Fields------------//
	private Level level;
	private Player player;
	
	private Inventory inv;
	
	private JFrame f;
	
	private KDTMouseController kdtMouseController;
	private CommandController kdtCC;
	
	private Container cp;
	private Graphics g;	
	
	//------------Constructors------------//
	
	/**
	 * Constructor for KDTView
	 * -creates the Frame, adds the components onto the frame, and registers the ActionListeners
	 * @param player the player instance
	 * @param level the current level
	 */
	public KDTView(Player player, Level level, CommandController commandController){
		kdtCC = commandController;
				
		//Create the GUI
		f= new JFrame("Kraft Dinner Table Maze");
		
		this.player=player;
		this.level=level;
		
		addMenusToFrame();
		f.setSize(600, 700);
		f.setMinimumSize(new Dimension(600, 700));
		
		cp=f.getContentPane();
		
		//addComponentsToPaneUsingBAGLayout(cp);
		addComponentsToPaneUsingBorderLayout(cp);
		
		g=f.getGraphics();
						
		//f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	/**
	 * Add components to Content Pane using BorderLayout
	 */
	private void addComponentsToPaneUsingBorderLayout(Container pane){
		pane.setLayout(new BorderLayout());			
		

		MapView pMap = new MapView(level);
		//km added for testing KDT MouseListener
		//KDTMouseController kdtMouseController = new KDTMouseController(kdtCC, pMap);
		//pMap.addMouseListener(kdtMouseController);
		
		//MapController mController = new MapController(pMap);
		pane.add(pMap, BorderLayout.CENTER);
		
		/*MapView pMap = new MapView(level);
		MapController mController = new MapController(pMap);
		pane.add(pMap, BorderLayout.CENTER);*/
		
		JPanel pOutput = TextOutputPanel.getTextOutputPanel();
		pane.add(pOutput, BorderLayout.PAGE_END);
		
		//side panel
		JPanel pPlayer = new PlayerStatusPanel(player);
		JPanel pInventory = new InventoryPanel(player);
		pInventory.addMouseListener(kdtMouseController);
		
		JPanel sidePanel = new JPanel(new GridLayout(2,0));
		
		sidePanel.add(pPlayer);
		sidePanel.add(pInventory);

		pane.add(sidePanel, BorderLayout.LINE_END);
		
		
		FirstPersonView fpView = new FirstPersonView(player);
		
		kdtMouseController = new KDTMouseController(kdtCC, fpView);
		fpView.addMouseListener(kdtMouseController);
		
		pane.add(fpView, BorderLayout.CENTER);

	}
	
	
	/**
	 * Create the Menus for the Frame
	 */
	private void addMenusToFrame() {
		
		KDTMenuController kdtMenuController=new KDTMenuController(f, kdtCC);	
		//--------- MENUS ---------
		JMenu file=new JMenu("File");
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.setToolTipText("Exit Kraft Dinner Table Maze");
		exit.addActionListener(kdtMenuController);
		
		JMenu edit=new JMenu("Edit");
		JMenuItem undo = new JMenuItem("Undo");
		undo.addActionListener(kdtMenuController);
		
		JMenuItem redo = new JMenuItem("Redo");
		undo.addActionListener(kdtMenuController);
		
		JMenuItem help = new JMenuItem("Help");
		help.setToolTipText("View help manual");
		help.addActionListener(new HelpListener());
			
		file.add(help);
		file.add(exit);
		
		edit.add(undo);
		edit.add(redo);
				
		JMenuBar mainBar=new JMenuBar();
		mainBar.add(file);
		mainBar.add(edit);
		
		f.setJMenuBar(mainBar);
	}
			
}
