package org;
/**
 * RG
 * This file operates and governs the JFrame on which the game is run
 * It has little actual functionality besides drawing and swapping out JPanels
 * The only frame needed is instantiated in the Startup file
 */

import org.panels.*;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;

public class GUI extends JFrame 
{
	private JPanel myPanel;		//Panel on which the game is won
	private boolean firstStart = true;
	private Dimension dim;		//Dimension of the JFrame
	
    public GUI()		
    {
    	super("Title");		//Super constructor call to JFrame, creates a frame with window title "Title"
    }
    
    public void init()
    {
    	dim = this.getContentPane().getSize();	//Stores size of the usable Panel as a Dimension
    	myPanel = new MainMenu();			//Instantiates the first panel as the Main Menu
    	add(myPanel, BorderLayout.CENTER);	//Puts the panel onto the frame
    	setFocusTraversalKeysEnabled(true);	//I still don't know
    	myPanel.revalidate();
    	myPanel.repaint();
    }
    
    public void swapPanels(JPanel j)	//Swaps current Panel with arg j
    {
    	this.remove(myPanel);			//Remove the panel from the frame
    	myPanel = j;					//Set the panel to the new address
    	this.add(myPanel, BorderLayout.CENTER);	//Put the panel back on the frame
    	myPanel.requestFocusInWindow();	//Gives new panel focus once more
    	myPanel.revalidate();			//Revalidates panel (no idea why but necessary)
    	
    	if(myPanel instanceof GamePanel && firstStart)		//When you first make a GamePanel, it resets the variables to fit the panel
    	{
    		((GamePanel)(myPanel)).refreshVars();
    		firstStart = false;
    	}    	
    	
    	myPanel.repaint();				//Repaint the panel to see the changes
    }
    
    //Getter methods to retrieve dimensions of workable panel
    public Dimension getDim() {return dim;}
    public int getDimWidth() {return (int)dim.getWidth();}
    public int getDimHeight() {return (int)dim.getHeight();}
    public JPanel getPanel() {return myPanel;}
}
