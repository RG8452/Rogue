package org;
/**
 * RG 
 * File to execute
 * Just creates the frame and initializes
*/

import javax.swing.JFrame;

public class Startup 
{
	private static GUI go;		//Frame that the whole game uses
	private static Runner r;	//Runnable that the main thread employs
	
    public static void main(String[] args) 
    {  	
        go = new GUI();
        go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setExtendedState(JFrame.MAXIMIZED_BOTH);		//Method to make auto-full screen
			
        go.setVisible(true);		//Basic frame stuff
        go.init();
        r = new Runner();	
    }
    
    //Getters and setters
    public static GUI getGUI() {return go;}
    public static Runner getRunner() {return r;}
    public static void setRunner(Runner yeet) {r = yeet;}
}