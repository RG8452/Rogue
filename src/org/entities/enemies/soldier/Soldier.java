package org.entities.enemies.soldier;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.entities.enemies.Enemy;

public class Soldier extends Enemy
{
	private static int baseDamage = 10, baseHealth = 20;
	private static byte resistanceByte = 0b00000000;
	private BufferedImage[] nAnims;
	
	public Soldier(double xPos, double yPos, int l)
	{
		worldX = xPos;
		worldY = yPos;
		level = l;
		powerLevel(l);
		maxHealth = baseHealth;
		health = baseHealth;
		damage = baseDamage;
		canFly = false;
		rAnims = new BufferedImage[12]; //0-7 for walk, 8-9 for idle, 10-11 for jump
		lAnims = new BufferedImage[12]; //Instantiate the arrays
		nAnims = new BufferedImage[2];
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

		try //Read in all images for animation
		{
			rAnims[0] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_MoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_MoveRight2.png"));
			rAnims[2] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_MoveRight3.png"));
			rAnims[3] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_MoveRight4.png"));
			rAnims[4] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_MoveRight5.png"));
			rAnims[5] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_MoveRight6.png"));
			rAnims[6] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_MoveRight7.png"));
			rAnims[7] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_MoveRight8.png"));
			rAnims[8] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_IdleRight1.png"));
			rAnims[9] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_IdleRight2.png"));
			rAnims[10] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_JumpRight1.png"));
			rAnims[11] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/RightFacing/Soldier_JumpRight2.png"));
			
			lAnims[0] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_MoveLeft1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_MoveLeft2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_MoveLeft3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_MoveLeft4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_MoveLeft5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_MoveLeft6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_MoveLeft7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_MoveLeft8.png"));
			lAnims[8] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_IdleLeft1.png"));
			lAnims[9] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_IdleLeft2.png"));
			lAnims[10] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_JumpLeft1.png"));
			lAnims[11] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/LeftFacing/Soldier_JumpLeft2.png"));
			
			nAnims[0] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/Soldier_Ladder1.png"));
			nAnims[1] = ImageIO.read(new File("src/org/entities/enemies/soldier/Animations/Soldier_Ladder2.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (Soldier): " + e);
		}
	}

	@Override
	public void drawEnemy(Graphics2D g2d)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void act()
	{
		// TODO Auto-generated method stub

	}

	//@formatter:off
	protected int getBaseDamage() {return baseDamage;}
	protected int getBaseHealth() {return baseHealth;}
	protected String getClassName() {return "Soldier";}
	protected byte getResistanceByte() {return resistanceByte;}
	//@formatter:on
}
