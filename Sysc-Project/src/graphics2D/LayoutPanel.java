package graphics2D;

import gameCore.Edge;
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

abstract class LayoutPanel<T extends LayoutObject> extends JPanel implements Observer {
	protected MapView parentMap;
	protected Set<T> layoutObjects;
	private Queue<T> buffer;
	
	protected LayoutPanel(MapView parentMap){
		if (parentMap == null){
			throw new IllegalArgumentException("parent map cannot be null");
		}
		this.parentMap = parentMap;
		layoutObjects = new HashSet<T>();
		buffer = new ConcurrentLinkedQueue<T>();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		for (T layoutObject : layoutObjects){
			drawLayoutObject(g, layoutObject);
		}
	}
	
	protected abstract void drawLayoutObject(Graphics g, T layoutObject);

	@Override
	public void update(Observable arg0, Object arg1) {
		T layoutObject = (T) arg0;
		repaint(getRepaintRectangle(layoutObject));
	}
	
	protected void addLayoutObject(T layoutObject){
		if (layoutObject == null){
			throw new IllegalArgumentException("layoutObject cannot be null");
		}
		buffer.add(layoutObject);
		layoutObject.addObserver(this);
		
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
            	T newLayoutObject = buffer.remove();
            	layoutObjects.add(newLayoutObject);		
    			repaint(getRepaintRectangle(newLayoutObject));
            }
        });
	}
	
	protected abstract Rectangle getRepaintRectangle(T layoutObject);
}
