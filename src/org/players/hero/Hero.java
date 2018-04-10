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

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.Startup;
import org.panels.GamePanel;
import org.players.Player;
import org.world.World;
import org.world.interactable.Interactable;
import org.world.interactable.Platform;

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
		xOffset = 84;
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
		lSkillAnims = new BufferedImage[][] { new BufferedImage[12], new BufferedImage[8], new BufferedImage[6], new BufferedImage[8] };
		rSkillAnims = new BufferedImage[][] { new BufferedImage[12], new BufferedImage[8], new BufferedImage[6], new BufferedImage[8] };

		try // This little chunk reads in every animation image and stores them into the arrays
		{
			rAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_MoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_MoveRight2.png"));
			rAnims[2] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_MoveRight3.png"));
			rAnims[3] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_MoveRight4.png"));
			rAnims[4] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_MoveRight5.png"));
			rAnims[5] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_MoveRight6.png"));
			rAnims[6] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_MoveRight7.png"));
			rAnims[7] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_MoveRight8.png"));
			rAnims[8] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_IdleRight1.png"));
			rAnims[9] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_IdleRight2.png"));
			rAnims[10] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_JumpRight1.png"));
			rAnims[11] = ImageIO.read(new File("src/org/players/hero/Animations/RightFacing/Hero_JumpRight2.png"));

			lAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_MoveLeft1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_MoveLeft2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_MoveLeft3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_MoveLeft4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_MoveLeft5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_MoveLeft6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_MoveLeft7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_MoveLeft8.png"));
			lAnims[8] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_IdleLeft1.png"));
			lAnims[9] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_IdleLeft2.png"));
			lAnims[10] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_JumpLeft1.png"));
			lAnims[11] = ImageIO.read(new File("src/org/players/hero/Animations/LeftFacing/Hero_JumpLeft2.png"));

			nAnims[0] = ImageIO.read(new File("src/org/players/hero/Animations/Hero_Ladder1.png"));
			nAnims[1] = ImageIO.read(new File("src/org/players/hero/Animations/Hero_Ladder2.png"));

			lSkillAnims[0][0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L1.png"));
			lSkillAnims[0][1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L2.png"));
			lSkillAnims[0][2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L3.png"));
			lSkillAnims[0][3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L4.png"));
			lSkillAnims[0][4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L5.png"));
			lSkillAnims[0][5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L6.png"));
			lSkillAnims[0][6] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L7.png"));
			lSkillAnims[0][7] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L8.png"));
			lSkillAnims[0][8] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L9.png"));
			lSkillAnims[0][9] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L10.png"));
			lSkillAnims[0][10] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L11.png"));
			lSkillAnims[0][11] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_L12.png"));

			rSkillAnims[0][0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R1.png"));
			rSkillAnims[0][1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R2.png"));
			rSkillAnims[0][2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R3.png"));
			rSkillAnims[0][3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R4.png"));
			rSkillAnims[0][4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R5.png"));
			rSkillAnims[0][5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R6.png"));
			rSkillAnims[0][6] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R7.png"));
			rSkillAnims[0][7] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R8.png"));
			rSkillAnims[0][8] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R9.png"));
			rSkillAnims[0][9] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R10.png"));
			rSkillAnims[0][10] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R11.png"));
			rSkillAnims[0][11] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S1/HeroS1_R12.png"));

			lSkillAnims[1][0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L1.png"));
			lSkillAnims[1][1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L2.png"));
			lSkillAnims[1][2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L3.png"));
			lSkillAnims[1][3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L4.png"));
			lSkillAnims[1][4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L5.png"));
			lSkillAnims[1][5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L6.png"));
			lSkillAnims[1][6] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L7.png"));
			lSkillAnims[1][7] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_L8.png"));

			rSkillAnims[1][0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R1.png"));
			rSkillAnims[1][1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R2.png"));
			rSkillAnims[1][2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R3.png"));
			rSkillAnims[1][3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R4.png"));
			rSkillAnims[1][4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R5.png"));
			rSkillAnims[1][5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R6.png"));
			rSkillAnims[1][6] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R7.png"));
			rSkillAnims[1][7] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S2/HeroS2_R8.png"));

			lSkillAnims[2][0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_L1.png"));
			lSkillAnims[2][1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_L2.png"));
			lSkillAnims[2][2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_L3.png"));
			lSkillAnims[2][3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_L4.png"));
			lSkillAnims[2][4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_L5.png"));
			lSkillAnims[2][5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_L6.png"));

			rSkillAnims[2][0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_R1.png"));
			rSkillAnims[2][1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_R2.png"));
			rSkillAnims[2][2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_R3.png"));
			rSkillAnims[2][3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_R4.png"));
			rSkillAnims[2][4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_R5.png"));
			rSkillAnims[2][5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S3/HeroS3_R6.png"));

			lSkillAnims[3][0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_L1.png"));
			lSkillAnims[3][1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_L2.png"));
			lSkillAnims[3][2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_L3.png"));
			lSkillAnims[3][3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_L4.png"));
			lSkillAnims[3][4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_L5.png"));
			lSkillAnims[3][5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_L6.png"));

			rSkillAnims[3][0] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_R1.png"));
			rSkillAnims[3][1] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_R2.png"));
			rSkillAnims[3][2] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_R3.png"));
			rSkillAnims[3][3] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_R4.png"));
			rSkillAnims[3][4] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_R5.png"));
			rSkillAnims[3][5] = ImageIO.read(new File("src/org/players/hero/Animations/Abilities/S4/HeroS4_R6.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (Hero): " + e);
		}

		status = STATUS.IDLING;
	}

	/*
	 * This method checks the player's current status and direction Depending on the
	 * results, it fetched the proper image to draw onto the JPanel Each png is
	 * found within the subdirectories of the class
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

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL1)
			{
				img = rSkillAnims[0][curAnimation];
			}

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL2) img = rSkillAnims[1][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL3) img = rSkillAnims[2][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL4) img = rSkillAnims[3][curAnimation];
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

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL1)
			{
				img = lSkillAnims[0][curAnimation];
			}

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL2) img = lSkillAnims[1][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL3) img = lSkillAnims[2][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL4) img = lSkillAnims[3][curAnimation];
		}

		g2d.drawImage(img, (int) x, (int) y, null);
		if (Startup.getRunner().hitboxesEnabled()) drawHurtbox(g2d);
	}

	//The method that the player calls instead of actually moving
	public void attack()
	{
		switch(skill)
		{
			case SKILL1:
				attackOne();
				return;
			case SKILL2:
				attackTwo();
				return;
			case SKILL3:
				attackThree();
				return;
			case SKILL4:
				attackFour();
				return;
			case NONE:
				return;
		}
	}

	private void attackOne() //Basic Slash
	{
		//TODO: Generate hitboxes in all attacks
		if (++elapsedFrames > 12 * framesPerAnimationCycle - 1)
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			elapsedFrames = 0;
			curAnimation = 0;
		}
		else
		{
			curAnimation = elapsedFrames / framesPerAnimationCycle;
			if(curAnimation != 0 && curAnimation%4 == 0)
			{
				if(!(DataRetriever.getAllKeys().contains(DataRetriever.getSkillOne())))
				{
					status = STATUS.IDLING;
					skill = SKILL.NONE;
					elapsedFrames = 0;
					curAnimation = 0;
				}
			}
			if (curAnimation == 4 || curAnimation == 5 || curAnimation == 9 || curAnimation == 10)
			{
				worldX += facingRight ? 2 : -2;
			}
		}
	}

	private void attackTwo() //Dash Stab
	{
		if (++elapsedFrames > 8 * framesPerAnimationCycle - 1)
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			elapsedFrames = 0;
			curAnimation = 0;
		}
		else
		{
			curAnimation = elapsedFrames / framesPerAnimationCycle;
		}
	}

	private void attackThree() //Retreat
	{
		if(elapsedFrames == 0 && !onGround && !onPlatform)
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			return;
		}
		if (++elapsedFrames > 6 * framesPerAnimationCycle - 1)
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			elapsedFrames = 0;
			curAnimation = 0;
			ySpeed = DataRetriever.getGravityConstant();
			World.setDrawY();
			y = worldY - World.getDrawY();
			
			Interactable nyeh = touchingInteractable();
			if(nyeh instanceof Platform)
				((Platform)nyeh).setTransparent(false);
		}
		else
		{
			curAnimation = elapsedFrames / framesPerAnimationCycle;
			worldX += facingRight ? -6 : 6;
			facingRight = !facingRight;
			runCollisionX();
			facingRight = !facingRight;
			if(curAnimation < 1) worldY -= 2;
			else if(curAnimation > 4) worldY += 2;
			worldY -= ySpeed;
			
			Interactable nyeh = touchingInteractable();
			if(nyeh instanceof Platform)
				((Platform)nyeh).setTransparent(true);
		}
	}

	private void attackFour() //Great Slash
	{
		if (++elapsedFrames > 6 * framesPerAnimationCycle - 1)
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			elapsedFrames = 0;
			curAnimation = 0;
		}
		else
		{
			curAnimation = elapsedFrames / framesPerAnimationCycle;
		}
	}
}