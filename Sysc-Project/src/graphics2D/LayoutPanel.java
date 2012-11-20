package graphics2D;

import gameCore.LayoutObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A LayoutPanel is a panel displaying a set of LayoutObjects of the given type
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
 * @param <T> The type of LayoutObject that this panel is observing.
 */
abstract class LayoutPanel<T extends LayoutObject> extends JPanel implements Observer {
	protected MapView parentMap;
	protected Set<T> layoutObjects;
	private Queue<T> buffer; //For synchronization purposes
	private Queue<T> removeBuffer;
	
	/**
	 * Initialize the LayoutPanel with the given MapView
	 * @param parentMap The MapView that this LayoutPanel belongs to
	 */
	protected LayoutPanel(MapView parentMap){
		if (parentMap == null){
			throw new IllegalArgumentException("parentMap cannot be null");
		}
		this.parentMap = parentMap;
		layoutObjects = new HashSet<T>();
		buffer = new ConcurrentLinkedQueue<T>();
		removeBuffer = new ConcurrentLinkedQueue<T>();
	}
	
	/**
	 * Paints every layoutObject that this layoutPanel is observing
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		for (T layoutObject : layoutObjects){
			drawLayoutObject(g, layoutObject);
		}
	}
	
	/**
	 * Draws an individual layoutObject
	 * @param g The component's graphics
	 * @param layoutObject The object to draw
	 */
	protected abstract void drawLayoutObject(Graphics g, T layoutObject);

	/**
	 * Repaints the object that this LayoutPanel is observing
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (!(arg0 instanceof LayoutObject)){
			throw new UnsupportedOperationException("The LayoutPanel was observing something that was not a LayoutObject");
		}
		
		//Cast was giving warning, though it is known that arg0 is of type LayoutObject at this point
		@SuppressWarnings("unchecked")
		T layoutObject = (T) arg0;
		repaint(getRepaintRectangle(layoutObject));
	}
	
	/**
	 * Adds a layoutObject for this layoutPanel to observe
	 * @param layoutObject The object to observe
	 */
	protected void addLayoutObject(T layoutObject){
		if (layoutObject == null){
			throw new IllegalArgumentException("layoutObject cannot be null");
		}

		buffer.add(layoutObject); //place on buffer to be added to main set later
		layoutObject.addObserver(this);
		
		//Queues an event that adds the layoutObject to the main set and repaints the area that the 
		//object is added in. Synchronization is needed since the paintComponent method iterates
		//through the same set that this method is adding to (otherwise a ConcurrentModificationException may occur).
	    SwingUtilities.invokeLater(new Runnable(){
	    @Override
		public void run() {
	        T newLayoutObject = buffer.remove();
	       	layoutObjects.add(newLayoutObject);		
	   		repaint(getRepaintRectangle(newLayoutObject));
	    	}
	    });
	}
	
	/**
	 * Removes a layoutObject for this panel to observe
	 * @param layoutObject The object to stop observing
	 */
	protected void removeLayoutObject(T layoutObject){
		if (layoutObject == null){
			throw new IllegalArgumentException("layoutObject cannot be null");
		}
		removeBuffer.add(layoutObject);
		layoutObject.deleteObserver(this);
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				T newLayoutObject = removeBuffer.remove();
				layoutObjects.remove(newLayoutObject);
				repaint(getRepaintRectangle(newLayoutObject));
			}
		});
	}
	
	/**
	 * Gets the area which needs to be repainted for a given object.
	 * @param layoutObject The object which needs to be repainted
	 * @return A Rectangle encompassing the area to be repainted
	 */
	protected abstract Rectangle getRepaintRectangle(T layoutObject);
}
