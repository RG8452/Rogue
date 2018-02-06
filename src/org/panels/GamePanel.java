package org.panels;
/**
 * RG
 * This class is a JPanel which will be used during actual gameplay functionality
 * It has its own set of listener methods, found in the private class below
 * It will be put onto the frame once the MainMenu panel is closed
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.DataRetriever;
import org.Startup;
import org.enemies.Enemy;
import org.world.World;

public class GamePanel extends JPanel
{
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static int screenX = (int) (screenSize.getWidth()); // Get useful screen info
	public static int hScreenX = (int) (screenX / 2);
	public static int screenY = (int) (screenSize.getHeight());
	public static int hScreenY = (int) (screenY / 2);

	public GamePanel() // Constructor
	{
		GameHandler handled = new GameHandler(); // Panel basics: create handler, add listeners with handler object
		this.addMouseListener(handled);
		this.addKeyListener(handled);

		this.setFocusable(true); // Allows focus (necessary)
		this.setBackground(Color.gray);
	}

	@Override
	protected void paintComponent(Graphics g) // Overriden paintComponent
	{
		super.paintComponent(g); // First call the super method
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		DataRetriever.getWorld().drawVisibleWorld(g2d);
		if (Startup.getRunner().gridEnabled()) drawRulerBoard(g2d);
		g2d.setColor(Color.green);
		g2d.drawString(String.valueOf(DataRetriever.getFrame()), 50, 50);

		DataRetriever.getPlayer().drawPlayer(g2d);
		for (Enemy nya : DataRetriever.getAllEnemies())
			nya.drawEnemy(g2d);
	}

	public void refreshVars() // Method called only once to reset variables to have exact panel size
	{
		screenSize = Startup.getGUI().getContentPane().getSize();

		screenX = (int) (screenSize.getWidth());
		hScreenX = (int) (screenX / 2);
		screenY = (int) (screenSize.getHeight());
		hScreenY = (int) (screenY / 2);
	}

	private void drawRulerBoard(Graphics g) // Draws in a grid where every 32 is black and every 64 is white
	{
		int xStart = 32 - (int)(World.getDrawX()) % 32;
		int yStart = 32 - (int)(World.getDrawY()) % 32;
		for(int badRacismJoke = 0; badRacismJoke <= (int)(screenX / 64); badRacismJoke++)
		{
			g.setColor(Color.white);
			g.drawLine(0, badRacismJoke * 64 + yStart, screenX, badRacismJoke * 64 + yStart);
			g.drawLine(badRacismJoke * 64 + xStart, 0, badRacismJoke * 64 + xStart, screenY);
			
			g.setColor(Color.black);
			g.drawLine(0, badRacismJoke * 64 + 32 + yStart, screenX, badRacismJoke * 64 + yStart + 32);
			g.drawLine(badRacismJoke * 64 + 32 + xStart, 0, badRacismJoke * 64 + 32 + xStart, screenY);
		}
	}

	// Handler class which contains all the logic implemented by the listener.
	private class GameHandler implements KeyListener, MouseListener
	{
		// keyListener methods
		public void keyPressed(KeyEvent e)
		{
			DataRetriever.addKeyPressed(e.getKeyCode()); // Put keys pressed into the set
		}

		public void keyReleased(KeyEvent e)
		{
			DataRetriever.removeKeyReleased(e.getKeyCode()); // Remove keys released from the set
		}

		public void keyTyped(KeyEvent e) // Basic printing method
		{
			// char c = e.getKeyChar();
			// System.out.println("You typed " + c);
		}

		// mouseListener methods
		public void mouseClicked(MouseEvent e)
		{
			System.out.println(String.format("GAME: Clicked at %d, %d", e.getX(), e.getY()));
		}

		//@formatter:off
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		//@formatter:on
	}
}
