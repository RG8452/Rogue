package org.entities.enemies.huntingdog;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.entities.enemies.Enemy;

public class HuntingDog extends Enemy
{
	private static int baseDamage = 10, baseHealth = 10;
	private static byte resistanceByte = 0b00000000;
	private static BufferedImage[] rAnims = new BufferedImage[9]; //0-7 for walk, 8 for idle
	private static BufferedImage[] lAnims = new BufferedImage[9];
	
	static
	{
		try //Read in all images
		{
			rAnims[0] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveRight2.png"));
			rAnims[2] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveRight3.png"));
			rAnims[3] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveRight4.png"));
			rAnims[4] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveRight5.png"));
			rAnims[5] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveRight6.png"));
			rAnims[6] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveRight7.png"));
			rAnims[7] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveRight8.png"));
			rAnims[8] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_IdleRight1"));
			
			lAnims[0] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveLeft1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveLeft2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveLeft3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveLeft4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveLeft5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveLeft6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveLeft7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_MoveLeft8.png"));
			lAnims[8] = ImageIO.read(new File("src/org/entities/enemies/huntingdog/Animations/HuntingDog_IdleLeft1"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (HundingDog): " + e);
		}
	}
	
	public HuntingDog(double xPos, double yPos, int l)
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
	}
	
	@Override
	public void follow(boolean PlayerY)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onGround()
	{
		// TODO Auto-generated method stub
		return false;
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
	protected String getClassName() {return "Spearman";}
	protected byte getResistanceByte() {return resistanceByte;}
	//@formatter:on
}
