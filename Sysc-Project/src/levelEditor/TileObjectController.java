package levelEditor;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import levelEditor.TileObjectDisplayData.TileObjectDisplayDatum;

/**
 * TileObject controller controls and objectPanel's behaviour within its infoPanel.
 * It occasionally must affect the infoPanel when an action on the objectPanel requires
 * the infoPanel's reaction (e.g. selection)
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

public class TileObjectController extends MouseAdapter implements DocumentListener {
	
	private Integer currentKey;
	private Map<Integer, JTextField> textBoxes;
	private Map<Integer, Document> textBoxDocuments;
	private Map<Integer, TileObjectDisplayDatum> data;
	
	private TileInfoPanel infoPanel;
	private TileObjectPanel objectPanel;
	
	public TileObjectController(TileInfoPanel infoPanel, TileObjectPanel objectPanel){
		this.objectPanel = objectPanel;
		this.infoPanel = infoPanel;
		currentKey = 0;
		
		textBoxes = new HashMap<Integer, JTextField>();
		textBoxDocuments = new HashMap<Integer, Document>();
		data = new HashMap<Integer, TileObjectDisplayDatum>();
	}
	
	public void addTextBoxDatumPair(JTextField textBox, TileObjectDisplayDatum datum){
		Document d = textBox.getDocument();
		
		textBoxes.put(currentKey, textBox);
		textBoxDocuments.put(currentKey, d);
		data.put(currentKey, datum);
		
		textBox.addMouseListener(this);
		d.addDocumentListener(this);
		
		currentKey = new Integer(currentKey + 1);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		update(e);
	}
	
	private void update(DocumentEvent e){
		Integer key = null;
		Document eventDocument = e.getDocument();
		
		//find the key
		for (Entry<Integer, Document> entry: textBoxDocuments.entrySet()){
			if (entry.getValue() == eventDocument){
				key = entry.getKey();
				break;
			}
		}
		
		//find textbox and value
		JTextField textBox = textBoxes.get(key);
		TileObjectDisplayDatum datum = data.get(key);
		
		//process the data
		datum.setValue(textBox.getText());
		if (datum.isValid()){
			textBox.setBackground(Color.WHITE);
		}
		else {
			textBox.setBackground(Color.PINK);
		}
		
		infoPanel.setDirty(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//A bit of an ugly solution: Swing design has it such that a mouse click event is consumed
		//by only one component. When clicking on a text box, the text box consumed this click,
		//rather than this controller's objectPanel, so to get the entire objectPanel to become selected, this 
		//controller had to listen to the click and tell the infoPanel to consider the objectPanel selected
		infoPanel.setSelectedInfo(objectPanel);
	}
}
