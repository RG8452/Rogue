package org.panels;
/*
 * This panel will be the menu that the player sees when paused.
 * It will have the ability to go to the options menu and stuff
 * May eventually have settings and later functionality
 * Borrows code from Button, MainMenu, etc.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.Button;
import org.DataRetriever;
import org.Runner;
import org.Startup;

public class PausePanel extends JPanel
{
	private ArrayList<Button> buttons;		//List of all buttons
	public static Color fadedGray = new Color(40, 40, 40, 1);	//Faded gray for background
	
	public PausePanel()
	{
		PauseHandler pHandler = new PauseHandler();	//Basic listener stuff
		this.addKeyListener(pHandler);
		this.addMouseListener(pHandler);
		
		this.setFocusable(true);
		buttons = new ArrayList<Button>();		//Instantiate list
		addButton(new Button(MainMenu.screenX - 200, 100, 100, 100, "Options", Color.gray, Color.white, 7) {
    		@Override
    		public void drawButton(Graphics g)	//Overridden drawButton which draws the button with a gear on it
    		{
    			int tX = this.getX(); int tY = this.getY();		//Retrieve useful variables
    			int tW = this.getWidth(); int tH = this.getHeight();

    			g.setColor(Color.gray);				//Fill in button background
    			g.fillRect(tX, tY, tW, tH);

    			ArrayList<Integer> xList = new ArrayList<Integer>();	//ArrayLists to store poitns
    			ArrayList<Integer> yList = new ArrayList<Integer>();

    			int x1, x2, x3, x4, y1, y2, y3, y4;

    			for(int rotate = 0; rotate < 8; rotate++)		//Rotates around a circle and makes 4 point spokes
    			{
    				x1 = (int)(20 * Math.cos(Math.toRadians(rotate * 45 - 10)) + tX + (int)(tW / 2));
    				x2 = (int)(25 * Math.cos(Math.toRadians(rotate * 45 - 5)) + tX + (int)(tW / 2));
    				x3 = (int)(25 * Math.cos(Math.toRadians(rotate * 45 + 5)) + tX + (int)(tW / 2));
    				x4 = (int)(20 * Math.cos(Math.toRadians(rotate * 45 + 10)) + tX + (int)(tW / 2));
    				xList.add(x1); xList.add(x2); xList.add(x3); xList.add(x4);

    				y1 = (int)(20 * Math.sin(Math.toRadians(rotate * 45 - 10)) + tY + (int)(tH / 2));
    				y2 = (int)(25 * Math.sin(Math.toRadians(rotate * 45 - 5)) + tY + (int)(tH / 2));
    				y3 = (int)(25 * Math.sin(Math.toRadians(rotate * 45 + 5)) + tY + (int)(tH / 2));
    				y4 = (int)(20 * Math.sin(Math.toRadians(rotate * 45 + 10)) + tY + (int)(tH / 2));
    				yList.add(y1); yList.add(y2); yList.add(y3); yList.add(y4);
    			}

    			//Array of points for gear, converted from previous arrayLists
    			int[] xPoints = new int[xList.size()];
    			int[] yPoints = new int[yList.size()];
    			for(int meow = 0; meow < xList.size(); meow++)
    			{
    				xPoints[meow] = xList.get(meow);
    				yPoints[meow] = yList.get(meow);
    			}

    			g.setColor(Color.black);
    			g.fillOval(tX + (int)(tW / 2) - 10, tY + (int)(tH / 2) - 10, 20, 20);	//Outermost circle
    			g.fillPolygon(xPoints, yPoints, xPoints.length);						//Polygon
    			g.setColor(Color.gray);
    			g.fillOval(tX + (int)(tW / 2) - 9, tY + (int)(tH / 2) - 9, 18, 18);		//Inner Space
    			g.setColor(Color.black);
    			g.fillOval(tX + (int)(tW / 2) - 4, tY + (int)(tH / 2) - 4, 8, 8);		//Smallest Point
    		}
    	});			//Add options button
		
		addButton(new Button(MainMenu.hScreenX - 100, 1050, 200, 75, "Menu", Color.black, Color.white, 4));	//MainMenu button
	}
	
	private void addButton(Button b)	//Basic method for adding button to the list, but available publicly
    {
    	buttons.add(b);
    }
	
	@Override
	protected void paintComponent(Graphics g)	//Overridden repainting for the Pausecomponent
	{
		super.paintComponent(g);
		Runner.tempPanel.paintComponent(g);	//Draws the game in
		Graphics2D g2d = (Graphics2D)g;		//Generates 2D graphics
		g2d.setColor(fadedGray); g2d.fillRect(0, 0, 5000, 3000);	//Fills in a rect with high alpha to get fade
		
		//Text stuff
		g2d.setColor(Color.white);	
		g2d.setFont(new Font("TimesRoman", Font.BOLD, 150));
		g2d.drawString("Paused", MainMenu.hScreenX - (int)(g2d.getFontMetrics().stringWidth("Paused") / 2), 300);
		
		for(Button surelythelengthofavariablenameindicatesitsquality: buttons)	//Draws every button
		{	
			surelythelengthofavariablenameindicatesitsquality.drawButton(g2d);
			surelythelengthofavariablenameindicatesitsquality.drawButtonBorder(g2d);
		}
		
		g2d.setFont(new Font("TimesNewRoman", Font.PLAIN, 30));	//Draws text on buttons
		buttons.get(1).drawMessage(g2d, "Main Menu");
		
		fadedGray = new Color(40, 40, 40, (fadedGray.getAlpha() < 150) ? fadedGray.getAlpha() + 2 : 150);	//Creates fade
	}
	
	//Private class used to handle input and listening
	private class PauseHandler implements KeyListener,
										  MouseListener
	{
		//keyListener methods
		public void keyPressed(KeyEvent e)
		{
			DataRetriever.addKeyPressed(e.getKeyCode());	//Put keys pressed into the set
		}

		public void keyReleased(KeyEvent e)
		{
			DataRetriever.removeKeyReleased(e.getKeyCode());	//Remove keys released from the set
		}

   		public void keyTyped(KeyEvent e){		//Basic printing method
//					char c = e.getKeyChar();
//		 			System.out.println("You typed " + c);
   		}

   		//mouseListener methods
   		public void mouseClicked(MouseEvent e)
   		{
   			System.out.println(String.format("PAUSE: Clicked at %d, %d", e.getX(), e.getY()));
   			
   			for(Button butt: buttons)	//Loop through every button
			{
				if(butt.inBounds(e.getX(), e.getY()))	//If that button was clicked
				{
					//This switch will loop through all possible names and give each button a different function
					switch(butt.getName())
					{
						case "Options":
							Startup.getGUI().swapPanels(new OptionsPanel(new PausePanel()));
							break;
						case "Menu":
							Startup.getRunner().stop();
							Startup.setRunner(new Runner());
							Startup.getGUI().swapPanels(new MainMenu());
							return;
						default:
							System.out.println("ERROR IN PAUSE MENU");
					}
				}
			}
   		}

   		public void mousePressed(MouseEvent e){}

   		public void mouseReleased(MouseEvent e){}

   		public void mouseEntered(MouseEvent e){}

   		public void mouseExited(MouseEvent e){}
	}
}
