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
	private int lastAttackFrame;		//Frame that the last attack was finished
	private int framesPerAttack = 180;	//Frames between each attack
	private int distFromCenter = 176;	//Distance the bat flies away over the player's center
	private int distAboveCenter = 132;	//Distance over the player's head the bird flies
	private enum STATUS{PATHING, HOVERING, ATTACKING};	//Local enum
	private STATUS status; private int framesPerAnimationCycle = 6; 	//Status variable and frames per cycle
	private double attackStartX, attackStartY; 	//Coords where the attack begins, used in math
	private double attackPXMid;	//Player coords at beginning of attack
	private boolean leftPass = false; private int attackDuration = 110;	//Bool for passing left in hover and frames during attack
	
	//Constructor
	public GiantBat(double xPos, double yPos, int l)
	{
		x = xPos; y = yPos; level = l;	//Set basic variables
		canFly = true;					//All giant bats can fly
		rAnims = new BufferedImage[4]; lAnims = new BufferedImage[4];	//Instantiate the arrays
		xSpeed = 4.25; ySpeed = 3;		//Set speed variables (final)
		status = STATUS.PATHING;		//Status always begins as PATHING
		elapsedFrames = 0; curAnimation = 0;	//Set animation values
		eWidth = 64; eHeight = 52; xOffset = 0; yOffset = 10;	//Establish Rectangle info
		eHurtbox = new Rectangle((int)x + xOffset, (int)y + yOffset, 64, 52);	//Instantiate hurtbox
		facingRight = (x < ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getX());			//Determine orientation
		
		try	//Read in all images for animation
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
	
	/*
	 * This is a stupid override that just forces the enemy to follow a pre-laid path
	 * Essentially, while PATHING it locks into the correct y and x coordinate ranges 
	 * While HOVERING it will move back and forth within the range determined by distFromCenter
	 * After a length of time (framesPerAttack) pass, it begins ATTACKING
	 * When ATTACKING, it uses an exponential formula to determine a nice parabolic arc towards the center of the player
	 * After the attack, all necessary variables are reset and it begins PATHING again
	 */
	@Override
	public void act()
	{
		pX = ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getX();	//Retrieve neat player coords
		pY = ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getY();
		double pXMid = pX + ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getWidth()/2 + ((GamePanel)Startup.getGUI().getPanel()).getPlayer().getXOffset();
		
		if(status == STATUS.PATHING)
		{
			if(y < pY - distAboveCenter - 4) y += ySpeed;		//Move into the Y range
			else if(y > pY - distAboveCenter + 4) y -= ySpeed;
			
			if(x > pXMid + distFromCenter) x -= xSpeed;			//Move into the X range
			else if(x < pXMid - distFromCenter) x += xSpeed;
			
			if(pY - distAboveCenter - 4 < y && pY - distAboveCenter + 4 > y && pXMid - distFromCenter < x && pXMid + distFromCenter > x)
			{
				lastAttackFrame = DataRetriever.getFrame();	//If in range, then begin HOVERING and reset needed variables
				status = STATUS.HOVERING;
				leftPass = !facingRight;
			}
			
			elapsedFrames = (elapsedFrames > 4 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1;	//Animate
			curAnimation = elapsedFrames / framesPerAnimationCycle;
		}
		
		else if(status == STATUS.HOVERING)
		{
			if(leftPass)	//If on the way left
			{
				x -= xSpeed;	//Move left
				if(x < pXMid - distFromCenter) {leftPass = false; facingRight = true;}	//If all the way left, start going right
			}
			else			//If on the way right
			{
				x += xSpeed;	//Move right
				if(x > pXMid + distFromCenter) {leftPass = true; facingRight = false;}	//If all the way right, start going left
			}
			
			elapsedFrames = (elapsedFrames > 4 * framesPerAnimationCycle - 2) ? 0 : elapsedFrames + 1;	//Animate
			curAnimation = elapsedFrames / framesPerAnimationCycle;	
			
			if(DataRetriever.getFrame() - framesPerAttack > lastAttackFrame)	//If enough time has passed between attacks
			{
				status = STATUS.ATTACKING;	//Reset variables and begin Attacking
				lastAttackFrame = DataRetriever.getFrame();
				facingRight = (x < pXMid);
				attackStartX = x; attackStartY = y;
				attackPXMid = pXMid;
			}
		}
		
		else if(status == STATUS.ATTACKING)
		{
			//Arc is determined as an x^2 parabola of height distAboveCenter centered at x=attackPXMid
			//X is the linear exact point where a parametrized version would be on a frame by frame, beginning at the starting point
			//Y is the approximation of the x point squared (assuming cartesian plane where attackPXMid is 0)
			x = (attackStartX - (DataRetriever.getFrame() - lastAttackFrame) * (2 * (attackStartX-attackPXMid) / attackDuration));
			y = (attackStartY + distAboveCenter - (Math.pow((x-attackPXMid)/(attackStartX-attackPXMid), 2) * distAboveCenter));
			
			if(DataRetriever.getFrame() - lastAttackFrame > attackDuration) //If you've been attacking for the length of an attack
			{
				lastAttackFrame = DataRetriever.getFrame();	//Reset variables and begin pathing
				status = STATUS.PATHING;
				facingRight = (x < pXMid);
			}
		}
		
		eHurtbox.setLocation((int)x + xOffset, (int)y + yOffset);	//Reset hitbox
	}
	
	@Override
	public void drawEnemy(Graphics2D g2d)
	{
		if(facingRight)
		{
			if(status != STATUS.ATTACKING) img = rAnims[curAnimation];	//Get correct png for animated frame
			else img = rAnims[0];	//Recycle movement png for the attack png
		}
		else
		{
			if(status != STATUS.ATTACKING) img = lAnims[curAnimation];	//Same as right but left
			else img = lAnims[0];
		}
		g2d.drawImage(img, (int)x, (int)y, null);
		drawHurtbox(g2d);
	}
	
	private void drawHurtbox(Graphics2D g2d)	//Draws the hurtbox where the enemy would be vulnerable
    {
    	g2d.setColor(Color.red);
    	g2d.drawRect((int)eHurtbox.getX(), (int)eHurtbox.getY(), (int)eHurtbox.getWidth(), (int)eHurtbox.getHeight());
    }
}
