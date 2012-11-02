package gameGUI;

import gameCore.Inventory;
import gameCore.Item;
import gameCore.Player;
import gameCore.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;

import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * View for the KDT Maze
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
public class KDTView implements PlayerListener{
	//------------Fields------------//
	private Inventory inv;
	private DefaultListModel lmodel;
	
	private JFrame f;
	
	
	private Container cp;
	private Graphics g;	
	
	//------------Constructors------------//
	
	/**
	 * Constructor for KDTView
	 * -creates the Frame, adds the components onto the frame, and registers the ActionListeners
	 * @param game the reference back to the game
	 */
	public KDTView(){
		//Create the GUI
		f= new JFrame("Kraft Dinner Table Maze");
		
		addMenusToFrame();
		f.setSize(600, 800);
		
		
		cp=f.getContentPane();
		//addComponentsToPaneUsingBAGLayout(cp);
		addComponetsToPaneUsingBorderLayout(cp);
		
		g=f.getGraphics();
						
		//f.pack();
		f.setVisible(true);
				
	}
	
	/**
	 * Add components to Content Pane using BorderLayout
	 */
	private void addComponetsToPaneUsingBorderLayout(Container pane){
		pane.setLayout(new BorderLayout());
		
		KDTMainPanel pMain= new KDTMainPanel();
		pane.add(pMain, BorderLayout.CENTER);
		
		JPanel pPlayer = new PlayerStatusPanel();
		pane.add(pPlayer, BorderLayout.PAGE_START);
				
		JPanel pInventory = new InventoryPanel();
		pane.add(pInventory, BorderLayout.LINE_END);
		createTestInventory();
		addComponentsToInventoryPanel(pane);
		
		//JPanel pInput = new InputPanel();
		//pane.add(pInput, BorderLayout.PAGE_END);
		
		
	}
	

	private void addComponentsToInventoryPanel(Container pane) {
		
		
		JList jl;
		lmodel= new DefaultListModel();
		
		jl=new JList();
		jl.setModel(lmodel);
		jl.setName("InventoryList");
		
		pane.add(jl, BorderLayout.LINE_END);
			
		for(int i=0; i<inv.size(); i++){
			lmodel.addElement(inv.getItem(i).toString());
		}
		
		
	}

	private void createTestInventory(){
		inv = new Inventory();
		Item item1=new Item("redKey",1);
		Item item2=new Item("blueKey",1);
		inv.addItem(item1);
		inv.addItem(item2);
	}
	
	/**
	 * Create the Menus for the Frame
	 */
	private void addMenusToFrame() {
		//--------- MENUS ---------
		JMenu file=new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");
		exit.setToolTipText("Exit Kraft Dinner Table Maze");
		
		JMenu player=new JMenu("Player");
		JMenuItem drop = new JMenuItem("Drop");
		
		
		//exit.addActionListener(new GameController()); //game controller 
		file.add(exit);
				
		JMenuBar mainBar=new JMenuBar();
		mainBar.add(file);
		
		//extra menus for testing
		
		
		f.setJMenuBar(mainBar);
	}
	
	

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KDTView v=new KDTView();
	}

	@Override
	public void itemAdded(PlayerEvent e) {
		// TODO Auto-generated method stub
		lmodel.addElement(e.getItem());
		
	}

	@Override
	public void itemDropped(PlayerEvent e) {
		// TODO Auto-generated method stub
		lmodel.removeElement(e.getItem());
	}

	@Override
	public void statsChanged(PlayerEvent e) {
		// TODO Auto-generated method stub
		
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
