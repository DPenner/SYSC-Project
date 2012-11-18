package graphics2D;

import gameCore.Inventory;
import gameCore.Player;
import gameLoader.Level;

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
 * @version 1.0
 */
public class KDTView {
	//------------Fields------------//
	private Level level;
	private Player player;
	
	private Inventory inv;
	
	private JFrame f;
	
	private KDTMouseController kdtMC;
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
		KDTMouseController kdtMC = new KDTMouseController(kdtCC, pMap);
		pMap.addMouseListener(kdtMC);
		
		//MapController mController = new MapController(pMap);
		pane.add(pMap, BorderLayout.CENTER);
		
		JPanel pOutput = TextOutputPanel.getTextOutputPanel();
		pane.add(pOutput, BorderLayout.PAGE_END);
		
		//side panel
		JPanel pPlayer = new PlayerStatusPanel(player);
		JPanel pInventory = new InventoryPanel(player);
		
		JPanel sidePanel = new JPanel(new GridLayout(2,0));
		
		sidePanel.add(pPlayer);
		sidePanel.add(pInventory);
		
		//sidePanel.setLayout(new BorderLayout());
		//sidePanel.add(pPlayer, BorderLayout.PAGE_START);

		//JPanel pInventory = new InventoryPanel(player);
		//sidePanel.add(pInventory, BorderLayout.CENTER);
		pane.add(sidePanel, BorderLayout.LINE_END);

	}
	
	
	/**
	 * Create the Menus for the Frame
	 */
	private void addMenusToFrame() {
		
		KDTMenuController kdtMC=new KDTMenuController(f);	
		//--------- MENUS ---------
		JMenu file=new JMenu("File");
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.setToolTipText("Exit Kraft Dinner Table Maze");
		exit.addActionListener(kdtMC);
		
		JMenuItem help = new JMenuItem("Help");
		help.setToolTipText("View help manual");
		help.addActionListener(new HelpListener());
			
		file.add(help);
		file.add(exit);
				
		JMenuBar mainBar=new JMenuBar();
		mainBar.add(file);
		
		f.setJMenuBar(mainBar);
	}
	
		
	/**
	 * Add the components to the Content Pane using GridBagLayout
	 * ---- NOT USING this layout ---
	 */
	/*
	private void addComponentsToPaneUsingBAGLayout(Container pane) {
		GridBagConstraints c;
				
	    pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	   	    
	    KDTMainPanel pMain= new KDTMainPanel();
	    pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
	        
	    JButton button;
		pane.setLayout(new GridBagLayout());
		
		
		c = new GridBagConstraints();
	
		//------------Main Panel------------//
		//c.ipady = 600;      //make this component tall
		//c.ipadx = 400;
		c.weightx = 1.0;
		//JButton jb=new JButton("Test");
		c.gridwidth = 2;
		c.gridheight= 2;
		//c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.VERTICAL;
		pane.add(pMain, c);
		//pane.setSize(300, 200);
		
		//------------Player Status Panel------------//
		JPanel pPlayer = new PlayerStatusPanel();
		pPlayer.setLayout(new BoxLayout(pPlayer, BoxLayout.Y_AXIS));
		c.ipadx=0;
		c.ipady=0;
		//c.ipadx=200;
		//c.ipady = 300;
		c.weightx = .25;
		c.gridx = 2;
		c.gridy = 0;
		//c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		pane.add(pPlayer, c);
		
		JPanel pInventory = new InventoryPanel();
		pInventory.setLayout(new BoxLayout(pInventory, BoxLayout.Y_AXIS));
		c.ipadx=200;
		c.ipady = 300;
		c.weightx = 0.25;
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		//pane.add(pInventory, c);

		//------------Input and Status Panel------------//
		JPanel pInput = new InputPanel();
		
		c.ipady = 0;       //reset to default
		c.weighty = 1.5;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 0;       //aligned with button 2
		c.gridy = 2;       //third row
		c.gridwidth = 3;   //2 columns wide
		c.fill = GridBagConstraints.HORIZONTAL;
		pane.add(pInput, c);
	    

	}
	*/

}
