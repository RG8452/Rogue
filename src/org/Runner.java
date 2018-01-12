package org;
/**
 * RG
 * This is the class which will make user inputs actually apply to characters and regulate frames
 * It implements Runnable to help prevent thread pooling and make the frame rate actually do stuff.
 */

import org.Startup;
import org.panels.GamePanel;

public class Runner implements Runnable
{
	private static int frameDelay = (int)(1000/DataRetriever.getFrameRate());	//This retrieves, in milliseconds, the time to wait between frames
	
	@Override
	public void run() //Overridden "Run" for the Thread to execute
	{
		Startup.getGUI().swapPanels(new GamePanel());	//Firstly, substitute panels
		Startup.getGUI().getPanel().repaint();
		
		try
		{
			runGame();	//Call the run game method, needs to be tried to throw exception for sleeping
		}
		catch(Exception e) {}
	}	
	
	public static void runGame() throws Exception
	{
		while(true)
		{
			play();		//Handles all processing
			DataRetriever.incrementFrame();	//Count frames for universal access to passage of time
			
			try		//Delay
			{
				Thread.sleep(frameDelay);
			}
			catch(Exception e) {System.out.println(e);}
			
			Startup.getGUI().getPanel().repaint();
		}
	}

	public static void play()	//Method that will eventually handle all processing for enemies and players
	{
		((GamePanel)(Startup.getGUI().getPanel())).getPlayer().act();
	}
}