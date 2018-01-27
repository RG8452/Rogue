package org.players.hero;
/**
 * RG
 * This is the class for the Hero player. It will extend Player and have all drawing and attacks necessary for the Hero.
 * Consider reading in every single image into an array and changing reference instead of continually reading
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.panels.GamePanel;
import org.players.Player;

public class Hero extends Player
{
    public Hero(double h, double k, int maxHP)	//Constructor to set important variables
	{
    	worldX = h; worldY = k;
		maxHealth = maxHP;
		health = maxHP;
		xSpeed = 7; ySpeed = 3.5;
		curAnimation = 0; elapsedFrames = 0;
		pWidth = 24; pHeight = 54;
		xOffset = 20; yOffset =  6;
		
		if(worldX < GamePanel.screenX / 2) {x = worldX;}
		else if(worldX > DataRetriever.getWorld().getWidth() - GamePanel.screenX/2) x = GamePanel.screenX/2 + (GamePanel.screenX/2 - (DataRetriever.getWorld().getWidth() - worldX));
		else x = GamePanel.screenX/2;
		
		y = k;
			
		pHurtbox = new Rectangle((int)x + xOffset, (int)y + yOffset, pWidth, pHeight);
		jumpDelta = 16;
		rAnims = new BufferedImage[13]; lAnims = new BufferedImage[13];
		worldX = (int)(x + xOffset + pWidth/2); worldY = (int)(y + yOffset + pHeight);
		
		try	//This little chunk reads in every animation image and stores them into the arrays
		{
			rAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroMoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroMoveRight2.png")); 
			rAnims[2] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroMoveRight3.png")); 
			rAnims[3] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroMoveRight4.png")); 
			rAnims[4] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroMoveRight5.png")); 
			rAnims[5] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroMoveRight6.png")); 
			rAnims[6] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroMoveRight7.png")); 
			rAnims[7] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroMoveRight8.png")); 
			rAnims[8] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroIdleRight1.png")); 
			rAnims[9] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroIdleRight2.png"));
			rAnims[10] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroCrouchRight.png"));
			rAnims[11] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroJumpRight1.png"));
			rAnims[12] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroJumpRight2.png"));			
			
			lAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroMoveLeft1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroMoveLeft2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroMoveLeft3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroMoveLeft4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroMoveLeft5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroMoveLeft6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroMoveLeft7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroMoveLeft8.png"));
			lAnims[8] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroIdleLeft1.png"));
			lAnims[9] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroIdleLeft2.png"));
			lAnims[10] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroCrouchLeft.png"));
			lAnims[11] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroJumpLeft1.png"));
			lAnims[12] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroJumpLeft2.png"));
		}
		catch(IOException e) {System.out.println("IMAGE READING ERROR (Hero): " + e);}
    }    	
    
    /*
     * This method checks the player's current status and direction
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
    			if(ySpeed < 0) img = rAnims[11];
    			else img = rAnims[12];
    		}
    		
    		else if(status == STATUS.IDLING)
    		{
    			if(curAnimation < 4) img = rAnims[8];
    			else img = rAnims [9];
    		}
    		
    		else if(status == STATUS.MOVING) img = rAnims[curAnimation];
    		
    		else if(status == STATUS.CROUCHED) img = rAnims[10];
    	}
    	
    	else
    	{
    		if(status == STATUS.JUMPING)
    		{
    			if(ySpeed < 0) img = lAnims[11];
    			else img = lAnims[12];
    		}
    		
    		else if(status == STATUS.IDLING)
    		{
    			if(curAnimation < 4) img = lAnims[8];
    			else img = lAnims [9];
    		}
    		
    		else if(status == STATUS.MOVING) img = lAnims[curAnimation];
    		
    		else if(status == STATUS.CROUCHED) img = lAnims[10];
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