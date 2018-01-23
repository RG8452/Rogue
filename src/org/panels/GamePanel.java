package org.panels;
/**
 * RG
 * This class is a JPanel which will be used during actual gameplay functionality
 * It has its own set of listener methods, found in the private class below
 * It will be put onto the frame once the MainMenu panel is closed
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.DataRetriever;
import org.Startup;
import org.enemies.Enemy;
import org.enemies.giantbat.GiantBat;
import org.players.Player;
import org.players.hero.Hero;

public class GamePanel extends JPanel
{
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static int screenX = (int)(screenSize.getWidth()); //Get useful screen info
	public static int hScreenX = (int)(screenX / 2);
	public static int screenY = (int)(screenSize.getHeight());
	public static int hScreenY = (int)(screenY / 2);

	private Player p1;	//List of players, not single object for future implementation of multiplayer
	private ArrayList<Enemy> allEnemies;

    public GamePanel() //Constructor
    {
    	GameHandler handled = new GameHandler();	//Panel basics: create handler, add listeners with handler object
    	this.addMouseListener(handled);
    	this.addKeyListener(handled);

    	this.setFocusable(true);		//Allows focus (necessary)
    	this.setBackground(Color.gray);

    	p1 = new Hero(hScreenX, hScreenY, 100);
    	allEnemies = new ArrayList<Enemy>();
    }

    @Override
    protected void paintComponent(Graphics g)	//Overriden paintComponent
    {
    	super.paintComponent(g);		//First call the super method
  		Graphics2D g2d = (Graphics2D) g;

    	g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));	//Set new Font
    	g2d.setColor(Color.red);			//Here, you would begin drawing things as they appear in the game itself
    	g2d.drawString("GAME", 1050, 800);

    	drawRulerBoard(g2d);
    	g2d.setColor(Color.green);
    	g2d.drawString(String.valueOf(DataRetriever.getFrame()), 50, 50);

    	p1.drawPlayer(g2d);
    	for(Enemy nya: allEnemies) nya.drawEnemy(g2d);
    }
    
    public void refreshVars()	//Method called only once to reset variables to have exact panel size
    {
    	screenSize = Startup.getGUI().getContentPane().getSize();

    	screenX = (int)(screenSize.getWidth());
    	hScreenX = (int)(screenX / 2);
    	screenY = (int)(screenSize.getHeight());
    	hScreenY = (int)(screenY / 2);
    }

    private void drawRulerBoard(Graphics g) //Draws in a grid where every 32 is black and every 64 is white
    {
    	g.setColor(Color.white);
    	for(int supremacy = 0; supremacy <= (int)(screenX / 64); supremacy += 1)
    	{
    		g.drawLine(0, supremacy * 64, screenX, supremacy * 64);
    		g.drawLine(supremacy * 64, 0, supremacy * 64, screenY);
    	}

    	g.setColor(Color.black);
    	for(int racismIsntFunny = 32; racismIsntFunny < screenX + 64; racismIsntFunny += 64)
    	{
    		g.drawLine(0, racismIsntFunny, screenX, racismIsntFunny);
    		g.drawLine(racismIsntFunny, 0, racismIsntFunny, screenY);
    	}
    }

    public void addEnemy(Enemy e) {allEnemies.add(e);}
    public void removeEnemy(Enemy e) {allEnemies.remove(e);}
    
    public Player getPlayer() {return p1;}
    public ArrayList<Enemy> getAllEnemies() {return allEnemies;}

    //Handler class which contains all the logic implemented by the listener.
    private class GameHandler implements KeyListener,
    									 MouseListener
    {
    	//keyListener methods
		public void keyPressed(KeyEvent e)
		{
			DataRetriever.addKeyPressed(e.getKeyCode());	//Put keys pressed into the set

/*			String output = "List of keys pressed: [";		//Prints out the set in a readable fashion
			for(int i: (TreeSet<Integer>)(DataRetriever.getAllKeys()))
				output += KeyEvent.getKeyText(i) + ", ";
			System.out.println(output.substring(0, output.length()-2) + "]");*/
		}

		public void keyReleased(KeyEvent e)
		{
			DataRetriever.removeKeyReleased(e.getKeyCode());	//Remove keys released from the set
		}

   		public void keyTyped(KeyEvent e){		//Basic printing method
//			char c = e.getKeyChar();
// 			System.out.println("You typed " + c);
   		}

   		//mouseListener methods
   		public void mouseClicked(MouseEvent e){
   			System.out.println(String.format("GAME: Clicked at %d, %d", e.getX(), e.getY()));
   		}

   		public void mousePressed(MouseEvent e){}

   		public void mouseReleased(MouseEvent e){}

   		public void mouseEntered(MouseEvent e){}

   		public void mouseExited(MouseEvent e){}
    }
}
