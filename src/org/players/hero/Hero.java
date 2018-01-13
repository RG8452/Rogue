package org.players.hero;
/**
 * RG
 * This is the class for the Hero player. It will extend Player and have all drawing and attacks necessary for the Hero.
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.players.Player;

public class Hero extends Player
{
    public Hero(double h, double k, int maxHP, double xS, double yS)	//Constructor to set important variables
	{
		x = h; y = k;
		maxHealth = maxHP;
		health = maxHP;
		xSpeed = xS; ySpeed = yS;
		curAnimation = 0; elapsedFrames = 0;
		pWidth = 10; pHeight = 27;
		pHurtbox = new Rectangle((int)x, (int)y, pWidth, pHeight);
    }    	
    
    /*
     * This method chekcs the player's current status and direction
     * Depending on the results, it fetched the proper image to draw onto the JPanel
     * Each png is found within the subdirectories of the class
     */
    @Override
	public void drawPlayer(Graphics2D g2d)	
	{
		if(facingRight)
		{
			if(status.equals("Jumping"))
			{
				//Set image to jumping right hero
			}
			
			else if(status.equals("Idling"))
			{
				if(curAnimation < 4)
				{
					try
					{
						img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroIdleRight1.png"));
					}
					catch(IOException e) {System.out.println("IMAGE READING ERROR (RI1): " + e);}
				}
				else
				{
					try
					{
						img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroIdleRight2.png"));
					}
					catch(IOException e) {System.out.println("IMAGE READING ERROR (RI2): " + e);}	
				}
			}
			
			else
			{
				switch(curAnimation)
				{
					case 0:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight1.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (RM1): " + e);}
						break;
					case 1:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight2.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (RM2): " + e);}
						break;
					case 2:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight3.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (RM3): " + e);}
						break;
					case 3:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight4.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (RM4): " + e);}
						break;
					case 4:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight5.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (RM5): " + e);}
						break;
					case 5:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight6.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (RM6): " + e);}
						break;
					case 6:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight7.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (RM7): " + e);}
						break;
					case 7:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight8.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (RM8): " + e);}
						break;
					default:
						System.out.println("ANIMATION OUT OF BOUNDS " + String.valueOf(curAnimation));	
				}
			}
		}
		else
		{
			if(status.equals("Jumping"))
			{
				//Set image to jumping left hero
			}
			
			else if(status.equals("Idling"))
			{
				if(curAnimation < 4)
				{
					try
					{
						img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroIdleLeft1.png"));
					}
					catch(IOException e) {System.out.println("IMAGE READING ERROR (LI2): " + e);}
				}
				else
				{
					try
					{
						img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroIdleLeft2.png"));
					}
					catch(IOException e) {System.out.println("IMAGE READING ERROR (LI1): " + e);}	
				}
			}
			
			else
			{
				switch(curAnimation)
				{
					case 0:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft1.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (LM1): " + e);}
						break;
					case 1:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft2.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (LM2): " + e);}
						break;
					case 2:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft3.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (LM3): " + e);}
						break;
					case 3:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft4.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (LM4): " + e);}
						break;
					case 4:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft5.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (LM5): " + e);}
						break;
					case 5:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft6.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (LM6): " + e);}
						break;
					case 6:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft7.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (LM7): " + e);}
						break;
					case 7:
						try
						{
							img = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft8.png"));
						}
						catch(IOException e) {System.out.println("IMAGE READING ERROR (LM8): " + e);}
						break;
					default:
						System.out.println("ANIMATION OUT OF BOUNDS " + String.valueOf(curAnimation));	
				}
			}
		}
		
		g2d.drawImage(img, (int)x, (int)y, null);
	}
}