package graphics2D;

import gameCore.Inventory;
import gameCore.Player;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public class InventoryPanel extends JPanel implements PlayerListener{
	private Player player;
	private DefaultListModel lmodel;
	private Inventory inv;
	
	public InventoryPanel(Player p){
		player=p;
		inv = player.getInventory();
		
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
}
