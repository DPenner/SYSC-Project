package gameLoader;

import gameCore.Player;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import commands.CommandController;
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
	private Player p;
	private Level l;
	private CommandController cc;
	
	public Serialize(Player p, Level l, CommandController cc){
		this.cc=cc;
		this.l=l;
		this.p=p;
	}
	
	public Player getP() {
		return p;
	}

	public Level getL() {
		return l;
	}

	public CommandController getCc() {
		return cc;
	}
	
	public boolean write_serialize(String fileName){
		boolean writeSuccessful = false;
		FileOutputStream oStream = null;
		
		try {
			oStream = new FileOutputStream(fileName);
		
			ObjectOutputStream objStream = new ObjectOutputStream(oStream);
		
			objStream.writeObject(p);
			objStream.writeObject(cc);
			
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
	
	public boolean read_serialize(String fileName) {
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
					else if(objectIN instanceof CommandController ) 
					{
						this.cc = (CommandController) objectIN;
					}
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
}