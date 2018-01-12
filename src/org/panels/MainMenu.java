package org.panels;
/**
 * RG
 * Panel to handle Main Menu input
 * Only needs a mouseListener, will have buttons to do all the functionality
 */

import org.Button;
import org.Runner;
import org.Startup;

import java.util.ArrayList;
import java.util.Arrays;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class MainMenu extends JPanel
{
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	static int screenX = (int)(screenSize.getWidth()); //Get useful screen info
	static int hScreenX = (int)(screenX / 2);
	static int screenY = (int)(screenSize.getHeight());
	static int hScreenY = (int)(screenY / 2);

	private ArrayList<Button> buttons;

    public MainMenu() 		//Constructor which just puts a mouseListener on the panel and makes it focusable
    {
    	this.addMouseListener(new MenuHandler());
    	this.setFocusable(true);
    	this.setBackground(Color.BLACK);

    	buttons = new ArrayList<Button>();		//Instantiate list of buttons
    	this.addButton(new Button(hScreenX - 200, hScreenY - 100, 400, 200, "Play", Color.blue, Color.yellow));	//Add play button
    	this.addButton(new Button(screenX - 200, 100, 100, 100, "Options", Color.gray, Color.white, 7) {
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
    }

    public void addButton(Button b)	//Basic method for adding button to the list, but available publicly
    {
    	buttons.add(b);
    }

    @Override
    protected void paintComponent(Graphics g)	//Method for drawing when on the Main Menu
    {
    	super.paintComponent(g);

    	g.setFont(new Font("TimesRoman", Font.BOLD, 50)); //set new font
    	g.setColor(Color.green);				//Draws the "Main Menu" text
    	g.drawString("Main Menu", hScreenX - (int)(g.getFontMetrics().stringWidth("Main Menu") / 2), 300);

    	//Loops through all available buttons and draws the button as well as its border
    	for(Button butter: buttons)
    	{
    		butter.drawButton(g);
    		butter.drawButtonBorder(g);
    	}

    	g.setFont(new Font("TimesNewRoman", Font.PLAIN, 100));	//Set a new Font
    	buttons.get(0).drawMessage(g, "PLAY");					//Draws the message on the Button
    }

    private class MenuHandler implements MouseListener		//Handler class for menu listeners
    {
    	//mouseListener methods
   		public void mouseClicked(MouseEvent e)
   		{
// 			System.out.println(String.format("MENU: Clicked at %d, %d", e.getX(), e.getY()));	//Debug for getting coords

			for(Button butt: buttons)	//Loop through every button
			{
				if(butt.inBounds(e.getX(), e.getY()))	//If that button was clicked
				{
					//This switch will loop through all possible names and give each button a different function
					switch(butt.getName())
					{
						case "Play":	
							//This next stuff has to do with thread pooling. Essentially, hitting "Play" calls up a new thread which begins to run through actual gameplay.
							try
							{
								Thread t = new Thread(new Runner());
								t.start();
								return;
							}
							catch(Exception except)
							{
								break;
							}
						case "Options":
							Startup.getGUI().swapPanels(new OptionsPanel());
							break;
						default:
							System.out.println("ERROR IN MAIN MENU");
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
