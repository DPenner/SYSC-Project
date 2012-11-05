package graphics2D;

import gameCore.Inventory;
import gameCore.Player;

import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;

public class InventoryPanel extends JPanel implements PlayerListener{
	private Player player;
	private DefaultListModel lmodel;
	private Inventory inventory;
	
	private static int TEXT_OFFSET = 10;
	private static int TEXT_TAB1=20;
	private static int ROW_OFFSET = 25;
	
	public InventoryPanel(Player p){
		
		player=p;
		inventory = player.getInventory();
		
		//addComponentsToInventoryPanel();
		player.addPlayerListener(this);
	}
	
	private void drawInventory(Graphics g, int y){
		for(int i=0; i<inventory.size(); i++)
		{
			g.drawString(inventory.getItem(i).toString(), TEXT_TAB1, y+ i*ROW_OFFSET);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		int top = 12;
		int row = 1;
		
		super.paintComponent(g);
		g.drawString("Player Inventory", TEXT_OFFSET, top);
		drawInventory(g, top + ROW_OFFSET);
	}

	@Override
	public void itemAdded(PlayerEvent e) {
		this.repaint();
	}

	@Override
	public void itemDropped(PlayerEvent e) {
		// TODO Auto-generated method stub
		//lmodel.removeElement(e.getItem());
		
		this.repaint();
	}

	@Override
	public void statsChanged(PlayerEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * private void addComponentsToInventoryPanel() {
	
				
		JList jl;
		lmodel= new DefaultListModel();
		
		jl=new JList();
		jl.setModel(lmodel);
		jl.setName("InventoryList");

		add(jl);

		 	
		for(int i=0; i<inv.size(); i++){
			lmodel.addElement(inv.getItem(i).toString());
		}
	}
	*/
}
