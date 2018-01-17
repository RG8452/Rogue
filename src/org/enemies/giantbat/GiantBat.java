package org.enemies.giantbat;
/*
 * Class file for the Giant Bat enemy
 * Extends Enemy, will have basic pathing and stuff
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.Startup;
import org.enemies.Enemy;
import org.panels.GamePanel;

public class GiantBat extends Enemy
{
	private int lastAttackFrame;
	private int framesPerAttack = 180;	//Frames between each attack
	private int distFromCenter = 176;	//Distance the bat flies away over the player's center
	private int distAboveCenter = 132;	//Distance over the player's head the bird flies
	private enum STATUS{PATHING, HOVERING, ATTACKING};	//Local enum
	private STATUS status; private int framesPerAnimationCycle = 6;
	
	public GiantBat(double xPos, double yPos, int l)
	{
		x = xPos; y = yPos; level = l;
		canFly = true;
		rAnims = new BufferedImage[4]; lAnims = new BufferedImage[4];
		xSpeed = 4.25; ySpeed = 3;
		status = STATUS.PATHING;
		elapsedFrames = 0; curAnimation = 0;
		eWidth = 64; eHeight = 52; xOffset = 0; yOffset = 10;
		eHurtbox = new Rectangle((int)x + xOffset, (int)y + yOffset, 64, 52);
		
		try
		{
			rAnims[0] = ImageIO.read(new File("src/org/enemies/giantbat/Animations/RightFacing/GBatRightIdle1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/enemies/giantbat/Animations/RightFacing/GBatRightIdle2.png"));
			rAnims[2] = ImageIO.read(new File("src/org/enemies/giantbat/Animations/RightFacing/GBatRightIdle3.png"));
			rAnims[3] = ImageIO.read(new File("src/org/enemies/giantbat/Animations/RightFacing/GBatRightIdle4.png"));
			
			lAnims[0] = ImageIO.read(new File("src/org/enemies/giantbat/Animations/LeftFacing/GBatLeftIdle1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/enemies/giantbat/Animations/LeftFacing/GBatLeftIdle2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/enemies/giantbat/Animations/LeftFacing/GBatLeftIdle3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/enemies/giantbat/Animations/LeftFacing/GBatLeftIdle4.png"));
		}
		catch(IOException e) {System.out.println("IMAGE READING ERROR (GBat): " + e);}
	}
	
	@Override
	public void act()
	{
		pX = ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getX();
		pY = ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getY();
		
		if(status == STATUS.PATHING)
		{
			if(y < pY - distAboveCenter - 3) y += ySpeed;
			else if(y > pY - distAboveCenter + 3) y -= ySpeed;
			
			if(x > pX + distFromCenter) x -= xSpeed;
			else if(x < pX - distFromCenter) x += xSpeed;
			
			if(pY - distAboveCenter - 3 > y && pY - distAboveCenter + 3 > y && pX - distFromCenter < x && pX + distFromCenter > x)
			{
				lastAttackFrame = DataRetriever.getFrame();
				status = STATUS.HOVERING;
			}
			
			elapsedFrames = (elapsedFrames > 4 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1;
			curAnimation = elapsedFrames / framesPerAnimationCycle;
		}
		
		else if(status == STATUS.HOVERING)
		{
			if(y < pY - distAboveCenter - 3) y += ySpeed;
			else if(y > pY - distAboveCenter + 3) y -= ySpeed;
			
			if(x > pX + distFromCenter) x -= xSpeed;
			else if(x < pX - distFromCenter) x += xSpeed;
			
			elapsedFrames = (elapsedFrames > 4 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1;
			curAnimation = elapsedFrames / framesPerAnimationCycle;
		}
		
		eHurtbox.setLocation((int)x + xOffset, (int)y + yOffset);
	}
	
	@Override
	public void drawEnemy(Graphics2D g2d)
	{
		if(facingRight)
		{
			if(status != STATUS.ATTACKING) img = rAnims[curAnimation];
			else img = rAnims[0];
		}
		else
		{
			if(status != STATUS.ATTACKING) img = lAnims[curAnimation];
			else img = lAnims[0];
		}
		g2d.drawImage(img, (int)x, (int)y, null);
		drawHurtbox(g2d);
	}
	
	private void drawHurtbox(Graphics2D g2d)
    {
    	g2d.setColor(Color.red);
    	g2d.drawRect((int)eHurtbox.getX(), (int)eHurtbox.getY(), (int)eHurtbox.getWidth(), (int)eHurtbox.getHeight());
    }
}
