package org.players.hero;
/**
 * RG
 * This is the class for the Hero player. It will extend Player and have all drawing and attacks necessary for the Hero.
 * Consider reading in every single image into an array and changing reference instead of continually reading
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.Startup;
import org.panels.GamePanel;
import org.players.Player;

public class Hero extends Player
{
	public Hero(double h, double k, int maxHP) // Constructor to set important variables
	{
		worldX = h;
		worldY = k;
		maxHealth = maxHP;
		health = maxHP;
		xSpeed = 7;
		ySpeed = 3.5;
		curAnimation = 0;
		elapsedFrames = 0;
		pWidth = 24;
		pHeight = 54;
		xOffset = 20;
		yOffset = 6;

		if (worldX < GamePanel.hScreenX) x = worldX;
		else if (worldX > DataRetriever.getWorld().getWidth() - GamePanel.hScreenX) x = GamePanel.screenX - (DataRetriever.getWorld().getWidth() - worldX);
		else x = GamePanel.hScreenX;

		if (worldY < GamePanel.hScreenY) y = worldY;
		else if (worldY > DataRetriever.getWorld().getHeight() - GamePanel.hScreenY) y = GamePanel.screenY - (DataRetriever.getWorld().getHeight() - worldY) - pHeight;
		else y = GamePanel.hScreenY - pHeight / 2;

		pHurtbox = new Rectangle((int) x + xOffset, (int) y + yOffset, pWidth, pHeight);
		jumpDelta = 16;
		rAnims = new BufferedImage[12];
		lAnims = new BufferedImage[12];
		nAnims = new BufferedImage[2];
		s1lAnims = new BufferedImage[12];
		s1rAnims = new BufferedImage[12];
		s2lAnims = new BufferedImage[8];
		s2rAnims = new BufferedImage[8];

		try // This little chunk reads in every animation image and stores them into the arrays
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
			rAnims[10] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroJumpRight1.png"));
			rAnims[11] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/HeroJumpRight2.png"));

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
			lAnims[10] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroJumpLeft1.png"));
			lAnims[11] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/HeroJumpLeft2.png"));

			nAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/HeroLadder1.png"));
			nAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/HeroLadder2.png"));
			
			s1lAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L1.png"));
			s1lAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L2.png"));
			s1lAnims[2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L3.png"));
			s1lAnims[3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L4.png"));
			s1lAnims[4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L5.png"));
			s1lAnims[5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L6.png"));
			s1lAnims[6] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L7.png"));
			s1lAnims[7] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L8.png"));
			s1lAnims[8] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L9.png"));
			s1lAnims[9] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L10.png"));
			s1lAnims[10] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L11.png"));
			s1lAnims[11] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L12.png"));
			
			s1rAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R1.png"));
			s1rAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R2.png"));
			s1rAnims[2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R3.png"));
			s1rAnims[3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R4.png"));
			s1rAnims[4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R5.png"));
			s1rAnims[5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R6.png"));
			s1rAnims[6] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R7.png"));
			s1rAnims[7] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R8.png"));
			s1rAnims[8] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R9.png"));
			s1rAnims[9] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R10.png"));
			s1rAnims[10] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R11.png"));
			s1rAnims[11] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R12.png"));
			
			s2lAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L1.png"));
			s2lAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L2.png"));
			s2lAnims[2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L3.png"));
			s2lAnims[3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L4.png"));
			s2lAnims[4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L5.png"));
			s2lAnims[5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L6.png"));
			s2lAnims[6] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L7.png"));
			s2lAnims[7] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L8.png"));
			
			s2rAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R1.png"));
			s2rAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R2.png"));
			s2rAnims[2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R3.png"));
			s2rAnims[3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R4.png"));
			s2rAnims[4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R5.png"));
			s2rAnims[5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R6.png"));
			s2rAnims[6] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R7.png"));
			s2rAnims[7] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R8.png"));
			
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (Hero): " + e);
		}

		status = STATUS.IDLING;
	}

	/*
	 * This method checks the player's current status and direction Depending on
	 * the results, it fetched the proper image to draw onto the JPanel Each png
	 * is found within the subdirectories of the class
	 */
	@Override
	public void drawPlayer(Graphics2D g2d)
	{
		if (status == STATUS.CLIMBING) img = curAnimation < 2 ? nAnims[0] : nAnims[1];

		else if (facingRight)
		{
			if (status == STATUS.JUMPING)
			{
				if (ySpeed < 0) img = rAnims[10];
				else img = rAnims[11];
			}

			else if (status == STATUS.IDLING)
			{
				if (curAnimation < 4) img = rAnims[8];
				else img = rAnims[9];
			}

			else if (status == STATUS.MOVING) img = rAnims[curAnimation];
			
			else if (status == STATUS.SKILL1) img = s1rAnims[curAnimation];
			
			else if (status == STATUS.SKILL2) img = s2rAnims[curAnimation];
		}
		
		else
		{
			if (status == STATUS.JUMPING)
			{
				if (ySpeed < 0) img = lAnims[10];
				else img = lAnims[11];
			}

			else if (status == STATUS.IDLING)
			{
				if (curAnimation < 4) img = lAnims[8];
				else img = lAnims[9];
			}

			else if (status == STATUS.MOVING) img = lAnims[curAnimation];

			else if (status == STATUS.SKILL1) img = s1lAnims[curAnimation];

			else if (status == STATUS.SKILL2) img = s2lAnims[curAnimation];
		}

		g2d.drawImage(img, (int) x, (int) y, null);
		if (Startup.getRunner().hitboxesEnabled()) drawHurtbox(g2d);
	}
}