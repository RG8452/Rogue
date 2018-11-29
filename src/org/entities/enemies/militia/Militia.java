package org.entities.enemies.militia;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.Startup;
import org.entities.enemies.Human;
import org.world.World;

public class Militia extends Human
{
	private static int baseDamage = 10, baseHealth = 20;
	private static byte resistanceByte = 0b00000000;
	private static int range = 64;
	private static BufferedImage[] rAnims = new BufferedImage[12]; //0-7 for walk, 8-9 for idle, 10-11 for jump
	private static BufferedImage[] lAnims = new BufferedImage[12];
	private static BufferedImage[] nAnims = new BufferedImage[2];

	static
	{
		try //Read in all images for animation
		{
			rAnims[0] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_MoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_MoveRight2.png"));
			rAnims[2] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_MoveRight3.png"));
			rAnims[3] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_MoveRight4.png"));
			rAnims[4] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_MoveRight5.png"));
			rAnims[5] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_MoveRight6.png"));
			rAnims[6] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_MoveRight7.png"));
			rAnims[7] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_MoveRight8.png"));
			rAnims[8] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_IdleRight1.png"));
			rAnims[9] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_IdleRight2.png"));
			rAnims[10] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_JumpRight1.png"));
			rAnims[11] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/RightFacing/Militia_JumpRight2.png"));

			lAnims[0] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_MoveLeft1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_MoveLeft2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_MoveLeft3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_MoveLeft4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_MoveLeft5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_MoveLeft6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_MoveLeft7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_MoveLeft8.png"));
			lAnims[8] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_IdleLeft1.png"));
			lAnims[9] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_IdleLeft2.png"));
			lAnims[10] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_JumpLeft1.png"));
			lAnims[11] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/LeftFacing/Militia_JumpLeft2.png"));

			nAnims[0] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/Militia_Ladder1.png"));
			nAnims[1] = ImageIO.read(new File("src/org/entities/enemies/militia/Animations/Militia_Ladder2.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (Militia): " + e);
		}
	}

	public Militia(double xPos, double yPos, int l)
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
		xOffset = 84;
		yOffset = 6; //Establish Rectangle info
		worldbox = new Rectangle((int) worldX, (int) worldY, width, height);
		facingRight = (worldX < DataRetriever.getPlayer().getWorldX()); //Determine orientation
	}

	public void drawEnemy(Graphics2D g2d)
	{
		if (this.status == STATUS.CLIMBING)img = curAnimation < 2 ? nAnims[0] : nAnims[1];
		
		else if (this.facingRight)
		{
			if (this.status == STATUS.JUMPING)
			{
				if (ySpeed < 0) img = rAnims[10];
				else img = rAnims[11];
			}
			
			else if (this.status == STATUS.IDLING) img = curAnimation < 4 ? rAnims[8] : rAnims[9];
			
			else if (this.status == STATUS.PATHING) img = rAnims[curAnimation];
		}
		
		else
		{
			if (this.status == STATUS.JUMPING)
			{
				if (ySpeed < 0) img = lAnims[10];
				else img = lAnims[11];
			}
			
			else if (this.status == STATUS.IDLING) img = curAnimation < 4 ? lAnims[8] : lAnims[9];
			
			else if (this.status == STATUS.PATHING) img = lAnims[curAnimation]; 
		}
		
		g2d.drawImage(img, (int) (worldX - World.getDrawX()), (int) (worldY - World.getDrawY()), null);
		if (Startup.getRunner().hitboxesEnabled()) drawHurtbox(g2d);
	}

	//@formatter:off
	protected int getBaseDamage() {return baseDamage;}
	protected int getBaseHealth() {return baseHealth;}
	protected String getClassName() {return "Militia";}
	protected byte getResistanceByte() {return resistanceByte;}
	//@formatter:on

	@Override
	protected int inRange()
	{
		return range + this.xOffset;
	}
}
