package org;
/**
 * RG
 * This is the class which will make user inputs actually apply to characters and regulate frames
 * It implements Runnable to help prevent thread pooling and make the frame rate actually do stuff.
 * Instantiated in the Startup class, one object is used to thread all games each time the player starts.
 * 
 * TODO: Later include a boolean for drawing hitboxes and rulerboard? ALso make a developer's console option
 */

import java.awt.Color;

import org.enemies.Enemy;
import org.enemies.giantbat.GiantBat;
import org.panels.GamePanel;
import org.panels.PausePanel;
import org.players.hero.Hero;
import org.world.World;
import org.world.dwarvencaverns.DwarvenCaverns;

public class Runner implements Runnable
{
	private static int pauseFrame; //Frame in which the pause key is first pressed to force a delay
	private boolean paused, stopped, devMode = false, noclip, hitboxes = true, worldboxes = true;	//Game status
	public static GamePanel tempPanel;	//Temporary panel used when swapping stuff
	private static int frameDelay = (int)(1000/DataRetriever.getFrameRate());	//This retrieves, in milliseconds, the time to wait between frames
	public static GamePanel accessPanel;
	
	@Override
	public void run() //Overridden "Run" for the Thread to execute
	{
		DataRetriever.setWorld(new DwarvenCaverns());
		DataRetriever.setPlayer(new Hero(DataRetriever.getWorld().getSpawnX(), DataRetriever.getWorld().getSpawnY(), 100));
		World.setDrawX(); World.setDrawY();
		DataRetriever.addEnemy(new GiantBat(300, 200, 1));
		Startup.getGUI().swapPanels(new GamePanel());	//Firstly, substitute panels
		accessPanel = ((GamePanel)Startup.getGUI().getPanel());
		accessPanel.repaint();
		
		try
		{
			runGame();	//Call the run game method, needs to be tried to throw exception for sleeping
		}
		catch(Exception e) {}
	}	
	
	public void runGame() throws Exception
	{
		while(!stopped)
		{
			play();		//Handles all processing
			DataRetriever.incrementFrame();	//Count frames for universal access to passage of time
			
			try		//Delay
			{
				Thread.sleep(frameDelay);
			}
			catch(Exception e) {System.out.println(e);}
			
			if(Startup.getGUI().getPanel() instanceof GamePanel) accessPanel.repaint();
			else Startup.getGUI().getPanel().repaint();
		}
	}

	public void play()	//Method that will eventually handle all processing for enemies and players
	{
		if(DataRetriever.getAllKeys().contains(DataRetriever.getPause()))
		{
			if(!paused && DataRetriever.getFrame() > pauseFrame + 20)
			{
				paused = true;
				tempPanel = (GamePanel)Startup.getGUI().getPanel();
				Startup.getGUI().swapPanels(new PausePanel());
				pauseFrame = DataRetriever.getFrame();
			}
			else if(paused && DataRetriever.getFrame() > pauseFrame + 20)
			{
				paused = false;
				pauseFrame = DataRetriever.getFrame();
				PausePanel.fadedGray = new Color(40, 40, 40, 1);
				Startup.getGUI().swapPanels(tempPanel);
				DataRetriever.getAllKeys().clear();
			}
		}
		
		if(!paused)
		{
			if(!devMode) DataRetriever.getPlayer().act();
			else DataRetriever.getPlayer().devAct(noclip);
			
			for(Enemy e: DataRetriever.getAllEnemies())
			{
				e.act();
			}
		}
		else
		{
			for(Enemy e: DataRetriever.getAllEnemies())
			{
				e.delayLAF();
			}
			Startup.getGUI().getPanel().repaint();
		}
	}
	
	public void stop() 	//Method called if game is stopped, i.e. user returns to main menu
	{
		this.stopped = true;		//Stops running the while loop
		DataRetriever.reset();		//Resets data
		tempPanel = null;			//Resets object pointer
		paused = false;				//Unpause
		pauseFrame = 0;				//Reset pause frame
	}
	
	public boolean hitboxesEnabled() {return hitboxes;}
	public boolean worldboxesEnabled() {return worldboxes;}
}