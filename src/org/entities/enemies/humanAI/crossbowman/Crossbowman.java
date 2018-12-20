package org.entities.enemies.humanAI.crossbowman;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.Startup;
import org.entities.enemies.humanAI.Human;
import org.world.World;

public class Crossbowman extends Human
{
	private static int baseDamage = 10, baseHealth = 20;
	private static byte resistanceByte = 0b00000000;
	private int range = 128;
	private static BufferedImage[] rAnims = new BufferedImage[12];
	private static BufferedImage[] lAnims = new BufferedImage[12];
	private static BufferedImage[] nAnims = new BufferedImage[2];
	private static BufferedImage[] rAttack = new BufferedImage[12];
	private static BufferedImage[] lAttack = new BufferedImage[12];

	static
	{
		try //Read in all images for animation
		{
			rAnims[0] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_MoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_MoveRight2.png"));
			rAnims[2] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_MoveRight3.png"));
			rAnims[3] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_MoveRight4.png"));
			rAnims[4] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_MoveRight5.png"));
			rAnims[5] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_MoveRight6.png"));
			rAnims[6] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_MoveRight7.png"));
			rAnims[7] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_MoveRight8.png"));
			rAnims[8] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_IdleRight1.png"));
			rAnims[9] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_IdleRight2.png"));
			rAnims[10] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_JumpRight1.png"));
			rAnims[11] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Crossbowmen_JumpRight2.png"));

			lAnims[0] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_MoveRight1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_MoveRight2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_MoveRight3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_MoveRight4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_MoveRight5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_MoveRight6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_MoveRight7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_MoveRight8.png"));
			lAnims[8] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_IdleRight1.png"));
			lAnims[9] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_IdleRight2.png"));
			lAnims[10] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_JumpRight1.png"));
			lAnims[11] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Crossbowmen_JumpRight2.png"));

			nAnims[0] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/Crossbowmen_Ladder1.png"));
			nAnims[1] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/Crossbowmen_Ladder2.png"));

			rAttack[0] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R1.png"));
			rAttack[1] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R2.png"));
			rAttack[2] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R3.png"));
			rAttack[3] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R4.png"));
			rAttack[4] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R5.png"));
			rAttack[5] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R6.png"));
			rAttack[6] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R7.png"));
			rAttack[7] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R8.png"));
			rAttack[8] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R9.png"));
			rAttack[9] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R10.png"));
			rAttack[10] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R11.png"));
			rAttack[11] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/RightFacing/Attack/CrossbowmenAttack_R12.png"));

			lAttack[0] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L1.png"));
			lAttack[1] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L2.png"));
			lAttack[2] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L3.png"));
			lAttack[3] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L4.png"));
			lAttack[4] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L5.png"));
			lAttack[5] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L6.png"));
			lAttack[6] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L7.png"));
			lAttack[7] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L8.png"));
			lAttack[8] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L9.png"));
			lAttack[9] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L10.png"));
			lAttack[10] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L11.png"));
			lAttack[11] = ImageIO.read(new File("src/org/entities/enemies/humanAI/crossbowman/Animations/LeftFacing/Attack/CrossbowmenAttack_L12.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (Crossbowman): " + e);
		}
	}

	private CrossbowBolt bolt; //Object to represent the bolt a crossbowman fires

	public Crossbowman(double xPos, double yPos, int l)
	{
		worldX = xPos;
		worldY = yPos;
		level = l;
		powerLevel(l);
		maxHealth = baseHealth;
		health = baseHealth;
		damage = baseDamage;
		inFlight = false;
		xSpeed = 5;
		ySpeed = 3; //Set speed variables (final)
		elapsedFrames = 0;
		curAnimation = 0; //Set animation values
		width = 24;
		height = 54;
		xOffset = 0;
		yOffset = 0; //Establish Rectangle info
		worldbox = new Rectangle((int) worldX, (int) worldY, width, height);
		facingRight = (worldX < DataRetriever.getPlayer().getWorldX()); //Determine orientation
		status = STATUS.CLIMBING;
		bolt = null;
	}

	@Override
	public void drawEnemy(Graphics2D g2d)
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

			else if (status == STATUS.PATHING) img = rAnims[curAnimation];
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

			else if (status == STATUS.PATHING) img = lAnims[curAnimation];
		}

		g2d.drawImage(img, (int) (worldX - World.getDrawX()), (int) (worldY - World.getDrawY()), null);
		if (Startup.getRunner().hitboxesEnabled()) drawHurtbox(g2d);
		if (Startup.getRunner().hitboxesEnabled() && hitbox != null) hitbox.drawHitbox(g2d);
	}

	@Override
	public void act()
	{
		// TODO Auto-generated method stub

	}

	//@formatter:off
	protected int getBaseDamage() {return baseDamage;}
	protected int getBaseHealth() {return baseHealth;}
	protected String getClassName() {return "Crossbowman";}
	protected byte getResistanceByte() {return resistanceByte;}
	protected void destroyBolt() {bolt = null;}
	//@formatter:on
	
	@Override
	protected int inRange()
	{
		return range + this.xOffset;
	}

	//Returns true if the enemy can fly or is on the ground
	public boolean onGround()
	{
		if (inFlight) return false; // If the enemy can fly, it doesn't matter
		return true;				// Determine if the enemy is on the ground
	}
}
