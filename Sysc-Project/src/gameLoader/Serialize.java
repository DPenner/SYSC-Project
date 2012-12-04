package gameLoader;

import gameCore.Player;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import commands.KeyCommandController;
/**
 *  Save and restore state of the game using serialization
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

public class Serialize {
	//------------Fields------------//
	public static final int MODE_OPEN = 0;
	public static final int MODE_SAVE = 1;
	
	private Player p;
	private Level l;
	
	//------------Constructors------------//
	public Serialize(Player p, Level l){
		this.l=l;
		this.p=p;
	}
	
	//----------- Getters ----------------//
	public Player getP() {
		return p;
	}

	public Level getL() {
		return l;
	}

	/**
	 * Save the state of the game by writing the main objects out using Serialisation
	 * @param fileName - fileName to save the state of the game
	 * @return true if write was successful. Otherwise false.
	 */
	public boolean write_serialize(String fileName){
		boolean writeSuccessful = false;
		FileOutputStream oStream = null;
		
		try {
			oStream = new FileOutputStream(fileName);
		
			ObjectOutputStream objStream = new ObjectOutputStream(oStream);
		
			objStream.writeObject(p);
			//objStream.writeObject(cc);
			
			objStream.writeObject(l);
			writeSuccessful=true;
				
		} catch (FileNotFoundException e) {
			System.out.println("KM: FileNotFoundException" + e.getMessage() + " > caught during serialize write.");
						
		} catch (IOException e) {
			System.out.println("KM: IOException " + e.getMessage() + " > caught during serialize write.");
			
		} finally {	
			try {
				oStream.flush();
				oStream.close();
			} catch (IOException e) {
				//cannot close the iStream
				System.out.println("Saving successfully but cannot close iStream.");
			}
		}
		return writeSuccessful;
			
	}
	
	/**
	 * Restore the game by reading the player and level instance objects back from the file using serialisation
	 * @param fileName - path and name of file to restore game state from
	 * @return true is game state was restored properly.  Otherwise, false.
	 */
	private boolean read_serialize(String fileName) {
		boolean readSuccessful = false;
		FileInputStream iStream = null;
		boolean EOF = false;
		try {
			iStream = new FileInputStream(fileName);
			
			ObjectInputStream objStream;
		
			objStream = new ObjectInputStream(iStream);
		
			while(!EOF) {  
				try {
					//try reading one object in
					Object objectIN;
					objectIN = objStream.readObject();
				
					if(objectIN instanceof Player) 
					{
						this.p = (Player) objectIN;
					} 
					else if(objectIN instanceof Level) 
					{
						this.l = (Level) objectIN;
					} 

					//else if(objectIN instanceof CommandController ) 
					//{
					//	this.cc = (CommandController) objectIN;
					//}

					
					else
					{	//got some other class 
						System.out.println("Got another class " + objectIN.toString());
					}
					
					
				} catch (EOFException e) {
					EOF = true;
				}
			}
			readSuccessful = true;
			
		} catch (FileNotFoundException e) {
			System.out.println("KM: FileNotFoundException" + e.getMessage() + " > caught during serialize read.");
		
		} catch (IOException e) {
			System.out.println("KM: IOException " + e.getMessage() + " > caught during serialize read.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("Invalid serialized class found during serialization.");
		} finally {	
			try {
				iStream.close();
			} catch (IOException e) {
				//cannot close the iStream
				System.out.println("Restoring successfully Cannot close iStream.");
			}
		}
		return readSuccessful;
	}
	
	/**
	 * SaveToFile method to handle dialogs for this operation
	 */
	public void saveToFile() {
		boolean writeSuccess;
		String fileName = selectFile(MODE_SAVE);
		if(fileName !=null) {
			writeSuccess = write_serialize(fileName);
			
			if (writeSuccess) {
				String message = "Game state successfully saved.";
				JOptionPane.showMessageDialog(null, message, "Game State", JOptionPane.INFORMATION_MESSAGE);
			}else {
				String message = "Game state NOT successfully saved.";
				JOptionPane.showMessageDialog(null, message, "Game State", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	/**
	 * Restores the game state by reading from the serialised file.
	 * @return true if the game was restored successfully.  Otherwise, false.
	 */

	public boolean loadFromFile() {
		boolean readSuccessful =false;
		
		String fileName = selectFile(MODE_OPEN);
		if(fileName != null) {
			readSuccessful = read_serialize(fileName);
			if(readSuccessful) {
				String message = "Game state successfully restored.";
				JOptionPane.showMessageDialog(null, message, "Game State", JOptionPane.INFORMATION_MESSAGE);
				p.notifyPlayerRestored();
			}else {
				String message = "Game state NOT successfully restored.";
				JOptionPane.showMessageDialog(null, message, "Game State", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return readSuccessful;
	}
	/**
	 * Function to use the JFileChooser to select a file for save and restore operations of serialisation.
	 * @param mode - open/save dialog selection
	 * @return - filepath and name of the file chosen
	 */
	public String selectFile(int mode) {
			
		String returnVal = null;
		int ret = 0;
		
		final JFileChooser fc = new JFileChooser();
		
		JFrame frame = new JFrame("FileChooser");
		
		//show open or save dialog
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		if(mode == MODE_OPEN) 
		{
			ret = fc.showOpenDialog(frame.getJMenuBar());
		}
		else if (mode == MODE_SAVE) 
		{
			ret = fc.showSaveDialog(frame.getJMenuBar());
		}
		
		if (ret == JFileChooser.CANCEL_OPTION) 
		{
			returnVal = null;
		}
		else if(ret == JFileChooser.APPROVE_OPTION) 
		{
			File file=fc.getSelectedFile();
			
			returnVal=file.getPath();
		}
		
		return returnVal;
	}
}