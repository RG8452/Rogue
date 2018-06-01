package org.entities.players.archer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.entities.players.Player;

/**
 * RG
 * Class for the Archer character, extends Player and contains attacks and drawing info 
 */
public class Archer extends Player
{
	private static int bDamage = 5, baseMHP = 100;
	
	public Archer(double h, double k) //Constructor initializes Archer
	{
		worldX = h;
		worldY = k;
		maxHealth = baseMHP;
		xSpeed = 7;
		ySpeed = 3.5;
		curAnimation = 0;
		elapsedFrames = 0;
		width = 24;
		height = 54;
		xOffset = 84;
		yOffset = 6;
		critModifier = 1.75;
		critChance = .02;
		level = 1;
		worldbox = new Rectangle((int)worldX + xOffset, (int)worldY + yOffset, width, height);
		hitbox = null;
		jumpDelta = 18;
		rAnims = new BufferedImage[8];
		lAnims = new BufferedImage[8];
		nAnims = null;
		lSkillAnims = null;
		rSkillAnims = null;
		
		try
		{
			rAnims[0] = ImageIO.read(new File("src/org/entities/players/archer/Animations/RightFacing/Archer_MoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/entities/players/archer/Animations/RightFacing/Archer_MoveRight2.png"));
			rAnims[2] = ImageIO.read(new File("src/org/entities/players/archer/Animations/RightFacing/Archer_MoveRight3.png"));
			rAnims[3] = ImageIO.read(new File("src/org/entities/players/archer/Animations/RightFacing/Archer_MoveRight4.png"));
			rAnims[4] = ImageIO.read(new File("src/org/entities/players/archer/Animations/RightFacing/Archer_MoveRight5.png"));
			rAnims[5] = ImageIO.read(new File("src/org/entities/players/archer/Animations/RightFacing/Archer_MoveRight6.png"));
			rAnims[6] = ImageIO.read(new File("src/org/entities/players/archer/Animations/RightFacing/Archer_MoveRight7.png"));
			rAnims[7] = ImageIO.read(new File("src/org/entities/players/archer/Animations/RightFacing/Archer_MoveRight8.png"));
			
			lAnims[0] = ImageIO.read(new File("src/org/entities/players/archer/Animations/LeftFacing/Archer_MoveLeft1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/entities/players/archer/Animations/LeftFacing/Archer_MoveLeft2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/entities/players/archer/Animations/LeftFacing/Archer_MoveLeft3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/entities/players/archer/Animations/LeftFacing/Archer_MoveLeft4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/entities/players/archer/Animations/LeftFacing/Archer_MoveLeft5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/entities/players/archer/Animations/LeftFacing/Archer_MoveLeft6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/entities/players/archer/Animations/LeftFacing/Archer_MoveLeft7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/entities/players/archer/Animations/LeftFacing/Archer_MoveLeft8.png"));
		}
		catch(IOException e)
		{
			System.out.println("IMAGE READING ERROR (Archer): " + e);
		}
		
		status = STATUS.IDLING;
	}
	
	@Override
	public void drawPlayer(Graphics2D g2d)
	{

	}
	
	@Override
	protected void attackOne()
	{
		
	}
	
	@Override
	protected void attackTwo()
	{
		
	}
	
	@Override
	protected void attackThree()
	{
		
	}
	
	@Override
	protected void attackFour()
	{
		
	}

	//@formatter:on
	protected String getClassName() {return "Archer";}
	protected int getBaseDamage() {return bDamage;}
	protected int getBaseHealth() {return baseMHP;}
	//@formatter:off
}
