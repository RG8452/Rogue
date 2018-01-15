package org.players.hero;
/**
 * RG
 * This is the class for the Hero player. It will extend Player and have all drawing and attacks necessary for the Hero.
 * Consider reading in every single image into an array and changing reference instead of continually reading
 */

import java.awt.Graphics2D;
import java.awt.Color;
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
		pWidth = 28; pHeight = 56;
		xOffset = 13; yOffset =  5;
		pHurtbox = new Rectangle((int)x + xOffset, (int)y + yOffset, pWidth, pHeight);
		jumpDelta = 15;
		
		try	//This little chunk reads in every animation image and stores them into the arrays
		{
			rAnims[0] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight2.png")); 
			rAnims[2] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight3.png")); 
			rAnims[3] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight4.png")); 
			rAnims[4] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight5.png")); 
			rAnims[5] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight6.png")); 
			rAnims[6] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight7.png")); 
			rAnims[7] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroMoveRight8.png")); 
			rAnims[8] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroIdleRight1.png")); 
			rAnims[9] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/RightFacing/HeroIdleRight2.png")); 
			
			lAnims[0] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroMoveLeft8.png"));
			lAnims[8] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroIdleLeft1.png"));
			lAnims[9] = ImageIO.read(new File("src/org/players/hero/PNGAnimations/LeftFacing/HeroIdleLeft2.png"));
		}
		catch(IOException e) {System.out.println("IMAGE READING ERROR: " + e);}
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
    		if(status == STATUS.JUMPING)
    		{
    			//Set image to jumping right hero
    			img = rAnims[8];	//TEMP PLACEHOLDER
    		}
    		
    		else if(status == STATUS.IDLING)
    		{
    			if(curAnimation < 4) img = rAnims[8];
    			else img = rAnims [9];
    		}
    		
    		else if(status == STATUS.MOVING) img = rAnims[curAnimation];
    	}
    	
    	else
    	{
    		if(status == STATUS.JUMPING)
    		{
    			//Set image to jumping right hero
    		}
    		
    		else if(status == STATUS.IDLING)
    		{
    			if(curAnimation < 4) img = lAnims[8];
    			else img = lAnims [9];
    		}
    		
    		else if(status == STATUS.MOVING) img = lAnims[curAnimation];
    	}

		g2d.drawImage(img, (int)x, (int)y, null);
		drawHurtbox(g2d);
	}
    
    private void drawHurtbox(Graphics2D g2d)
    {
    	g2d.setColor(Color.red);
    	g2d.drawRect((int)pHurtbox.getX(), (int)pHurtbox.getY(), (int)pHurtbox.getWidth(), (int)pHurtbox.getHeight());
    }
}

/*
@deprecated

if(facingRight)
		{
			if(status.equals("Jumping"))
			{
				//Set image to jumping right hero, increase ySpeed by whatever gravity ends up being
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

*/