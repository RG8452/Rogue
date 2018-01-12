package org.panels;
/**
 * RG
 * This panel will be used for the Options menu and opens when the gear on the MainMenu is clicked
 * It will be the location where keys can be reset and volume and such can eventually be adjusted
 */

import org.Startup;
import org.Button;
import org.DataRetriever;

import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OptionsPanel extends JPanel 
{
	private ArrayList<Button> buttons;	//List of buttons
	public int focusedButton = -1;		//Currently focused button, based on position in list (-1 is no focus)
	
    public OptionsPanel() 	//Constructor which adds listeners and buttons
    {
    	OptionsHandler opts = new OptionsHandler();
    	this.addKeyListener(opts);
    	this.addMouseListener(opts);
    	
    	this.setFocusable(true);
    	this.setBackground(Color.black);
    	
    	buttons = new ArrayList<Button>();	//Instantiate list
    	this.addButton(new Button(MainMenu.screenX - 200, 100, 100, 100, "Back", Color.red, Color.white, 7)	//Back button
    	{
    		@Override
    		public void drawButton(Graphics g)	//Override to redraw the button with a different appearance
    		{
    			g.setColor(Color.red);
    			g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    			
    			g.setColor(Color.blue);
    			g.drawLine(this.getX() + (int)(this.getWidth() * 3/4), this.getY() + 25, this.getX() + (int)(this.getWidth() * 3/4), this.getY() + 75);
    			g.drawLine(this.getX() + (int)(this.getWidth() * 3/4), this.getY() + 75, this.getX() + (int)(this.getWidth() * .2), this.getY() + 75);
    			g.drawLine(this.getX() + (int)(this.getWidth() * .2), this.getY() + 75, this.getX() + (int)(this.getWidth() * .55), this.getY() + 60);
    			g.drawLine(this.getX() + (int)(this.getWidth() * .2), this.getY() + 75, this.getX() + (int)(this.getWidth() * .55), this.getY() + 90);
    			g.setColor(Color.white);
    		}
    	});
    	
    	//Add a button per control in the game
    	this.addButton(new Button(50, 350, 250, 40, "Right", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 410, 250, 40, "Left", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 470, 250, 40, "Up", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 530, 250, 40, "Down", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 590, 250, 40, "Jump", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 650, 250, 40, "Skill One", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 710, 250, 40, "Skill Two", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 770, 250, 40, "Skill Three", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 830, 250, 40, "Skill Four", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 890, 250, 40, "Interact", Color.gray, Color.white, 5));
    	this.addButton(new Button(50, 950, 250, 40, "Pause", Color.gray, Color.white, 5));
    	
    	this.addButton(new Button(350, 370, 200, 60, "Reset", Color.red, Color.white, 5));		//Reset button
    }
    
    @Override
    protected void paintComponent(Graphics g)	//Overridden draw method to show that you're on the options menu
    {
    	super.paintComponent(g);
    	
    	g.setFont(new Font("SANS_SERIF", Font.PLAIN, 100));
    	g.setColor(Color.white);
    	g.drawString("Options", 50, 150);
    	g.setFont(new Font("SANS_SERIF", Font.PLAIN, 40));
    	g.drawString("Controls (Click and type a new key to change):", 50, 300);
    	
    	//Draw every button
    	g.setFont(new Font("TimesRoman", Font.BOLD, 30));
    	for(Button controls: buttons)
    	{
    		controls.drawButton(g);
    		controls.drawButtonBorder(g);
    	}
    	
    	//Draws text on each button
    	buttons.get(1).drawMessage(g, "Right: " + ((DataRetriever.getRight() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getRight())));
    	buttons.get(2).drawMessage(g, "Left: " + ((DataRetriever.getLeft() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getLeft())));
    	buttons.get(3).drawMessage(g, "Up: " + ((DataRetriever.getUp() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getUp())));
    	buttons.get(4).drawMessage(g, "Down: " + ((DataRetriever.getDown() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getDown())));
    	buttons.get(5).drawMessage(g, "Jump: " + ((DataRetriever.getJump() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getJump())));
    	buttons.get(6).drawMessage(g, "Skill One: " + ((DataRetriever.getSkillOne() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getSkillOne())));
    	buttons.get(7).drawMessage(g, "Skill Two: " + ((DataRetriever.getSkillTwo() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getSkillTwo())));
    	buttons.get(8).drawMessage(g, "Skill Three: " + ((DataRetriever.getSkillThree() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getSkillThree())));
    	buttons.get(9).drawMessage(g, "Skill Four: " + ((DataRetriever.getSkillFour() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getSkillFour())));
    	buttons.get(10).drawMessage(g, "Interact: " + ((DataRetriever.getInteract() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getInteract())));
    	buttons.get(11).drawMessage(g, "Pause: " + ((DataRetriever.getPause() == -1) ? "?" : KeyEvent.getKeyText(DataRetriever.getPause())));
    	
    	g.setFont(new Font("Helvetica", Font.BOLD, 40));
    	buttons.get(12).drawMessage(g, "Reset all");
    }
    
    public void addButton(Button b)	//Basic method for adding button to the list, but available publicly
    {
    	buttons.add(b);
    }
    
    private void unboundError(Graphics g)	//Called if the user tries to exit with controls unbound
    {
    	ArrayList<Integer> unboundKeys = DataRetriever.getControlKeys();	//Gets list of keys
    	for(int lol = 0; lol < unboundKeys.size(); lol++)		
    	{
    		if(unboundKeys.get(lol) == -1)				//If a key is unselected, set it to orange
    			buttons.get(lol+1).setCol(Color.orange);
    	}
    	repaint();
    }
    
    private class OptionsHandler implements KeyListener,		//Handler class to do listening for the options menu
    										MouseListener
	{
		//keyListener methods
		public void keyPressed(KeyEvent e) 		//Swaps controls if a button is highlighted and a key is pressed
		{
			if(focusedButton != -1)
			{
				ArrayList<Integer> keee = DataRetriever.getControlKeys();
				if(keee.contains(e.getKeyCode()))
				{
					switch(keee.indexOf(e.getKeyCode()))
					{
						case 0:
							DataRetriever.setRight(-1);	break;
						case 1:
							DataRetriever.setLeft(-1);	break;
						case 2:
							DataRetriever.setUp(-1);	break;
						case 3:
							DataRetriever.setDown(-1);	break;
						case 4:
							DataRetriever.setJump(-1);	break;
						case 5:
							DataRetriever.setSkillOne(-1);	break;
						case 6:
							DataRetriever.setSkillTwo(-1);	break;
						case 7:
							DataRetriever.setSkillThree(-1);break;
						case 8:
							DataRetriever.setSkillFour(-1);	break;
						case 9:
							DataRetriever.setInteract(-1);	break;
						case 10:
							DataRetriever.setPause(-1);		break;
					}				
				}
				
				switch(focusedButton)	//Sets the correct variable based on choice
				{
					case 1:
						DataRetriever.setRight(e.getKeyCode());
						break;
					case 2:
						DataRetriever.setLeft(e.getKeyCode());
						break;
					case 3:
						DataRetriever.setUp(e.getKeyCode());
						break;
					case 4:
						DataRetriever.setDown(e.getKeyCode());
						break;
					case 5:
						DataRetriever.setJump(e.getKeyCode());
						break;
					case 6:
						DataRetriever.setSkillOne(e.getKeyCode());
						break;
					case 7:
						DataRetriever.setSkillTwo(e.getKeyCode());
						break;
					case 8:
						DataRetriever.setSkillThree(e.getKeyCode());
						break;
					case 9:
						DataRetriever.setSkillFour(e.getKeyCode());
						break;
					case 10:
						DataRetriever.setInteract(e.getKeyCode());
						break;
					case 11:
						DataRetriever.setPause(e.getKeyCode());
						break;
				}
				
				buttons.get(focusedButton).setCol(Color.gray);		//When a control has been swapped, the button is unfocused
				focusedButton = -1;
				
				Startup.getGUI().getPanel().repaint();
			}
		}
		
		public void keyReleased(KeyEvent e) {}
				
		public void keyTyped(KeyEvent e) {}
		
		//mouseListener methods
   		public void mouseClicked(MouseEvent e){		//Checks if you've pressed in a button and, if so, does a different function based on button's name
// 			System.out.println(String.format("GAME: Clicked at %d, %d", e.getX(), e.getY()));

			for(int n = 0; n < buttons.size(); n++)	//Loop through every button
			{	
				Button butt = buttons.get(n);
				if(butt.inBounds(e.getX(), e.getY()))	//If that button was clicked
				{
					if(n == 0)	//If the first key is pressed
					{
						boolean mapped = true;
						for(int q: DataRetriever.getControlKeys())	//Check if any keys are unbound
						{
							if(q == -1) mapped = false;
						}
						if(mapped)	//If all keys are bound then leave this menu
						{
							Startup.getGUI().swapPanels(new MainMenu());
							return;
						}
						else		//If any key is unbound
						{
							unboundError(Startup.getGUI().getPanel().getGraphics()); //Don't let the user leave & color the unbound keys oragnge
						}
					}
					else if (n == 12) //If the reset key is pressed
					{
						if(focusedButton != -1) buttons.get(focusedButton).setCol(Color.gray);		//Resets all colors to gray
						for(int thru = 0; thru < DataRetriever.getControlKeys().size(); thru++)
						{
							if(DataRetriever.getControlKeys().get(thru) == -1)
								buttons.get(thru + 1).setCol(Color.gray);
						}
						DataRetriever.resetControls();								//Reset the keys to defaults
					}
					else if(focusedButton > 0 && DataRetriever.getControlKeys().get(focusedButton-1) != -1)	//If a buton is pressed
						buttons.get(focusedButton).setCol(Color.gray);
					else if(focusedButton != -1)
						buttons.get(focusedButton).setCol(Color.orange);
					butt.setCol(Color.red);
					focusedButton = n;

				}
			}
			
			Startup.getGUI().getPanel().repaint();
   		}
   		
   		public void mousePressed(MouseEvent e){}
   		
   		public void mouseReleased(MouseEvent e){}	
   		
   		public void mouseEntered(MouseEvent e){}
   		
   		public void mouseExited(MouseEvent e){}
	}
}

//DEPRECATED DEBUG CODE
					//This switch will loop through all possible names and give each button a different function, found in the mouseClicked loop
/*					switch(butt.getName())
					{
						case "Back":
							Startup.getGUI().swapPanels(new MainMenu());
							focusedButton = -1;
							break;
						case "Right":
//							System.out.println("Clicked Right");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(1).setCol(Color.red);
							focusedButton = 1;
							break;
						case "Left":
//							System.out.println("Clicked Left");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(2).setCol(Color.red);
							focusedButton = 2;
							break;
						case "Up":
//							System.out.println("Clicked Up");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(3).setCol(Color.red);
							focusedButton = 3;
							break;
						case "Down":
//							System.out.println("Clicked Down");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(4).setCol(Color.red);
							focusedButton = 4;
							break;
						case "Jump":
//							System.out.println("Clicked Jump");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(5).setCol(Color.red);
							focusedButton = 5;
							break;
						case "Skill One":
//							System.out.println("Clicked Skill One");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(6).setCol(Color.red);
							focusedButton = 6;
							break;
						case "Skill Two":
//							System.out.println("Clicked Skill Two");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(7).setCol(Color.red);
							focusedButton = 7;
							break;
						case "Skill Three":
//							System.out.println("Clicked Skill Three");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(8).setCol(Color.red);
							focusedButton = 8;
							break;
						case "Skill Four":
//							System.out.println("Clicked Skill Four");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(9).setCol(Color.red);
							focusedButton = 9;
							break;
						case "Interact":
//							System.out.println("Clicked Interact");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(10).setCol(Color.red);
							focusedButton = 10;
							break;
						case "Pause":
//							System.out.println("Clicked Pause");
							if(focusedButton != -1)
								buttons.get(focusedButton).setCol(Color.gray);
							buttons.get(11).setCol(Color.red);
							focusedButton = 11;
							break;
						default:
							System.out.println("ERROR IN OPTIONS");
					}*/