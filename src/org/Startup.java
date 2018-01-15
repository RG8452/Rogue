package org;
/**
 * RG 
 * File to execute
 * Just creates the frame and initializes
*/

import javax.swing.JFrame;

public class Startup 
{
	private static GUI go;
	
    public static void main(String[] args) 
    {  	
        go = new GUI();
        go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setExtendedState(JFrame.MAXIMIZED_BOTH);		//Method to make auto-full screen
		
        go.setVisible(true);
        go.init();
    }
    
    //Getter method for the JFrame itself
    public static GUI getGUI() {return go;}
}
