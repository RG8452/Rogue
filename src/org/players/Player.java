package org.players;
/**
 * RG
 * Abstract class that each player will extend
 * Will have the ground rules and fields that each object will need
 * Most of the variable names are either self-explanatory or just basic coordinate and stats
 *
 * NOTE: Later versions to include base stats like base damage, armor, jump velocity?, range [in hitbox]?, crit chance
 * TODO: Add ability to drop through platforms
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.DataRetriever;
import org.world.World;
import org.world.interactable.Interactable;
import org.world.interactable.Ladder;
import org.world.interactable.ManCannon;

public abstract class Player
{
	protected int health, level, maxHealth, curAnimation, elapsedFrames, pWidth, pHeight, xOffset, yOffset; // Basic stats
	protected double x, y, worldX, worldY, xSpeed, ySpeed, jumpDelta; // X and Y are doubles to keep absolute track of the players, while their drawing will be on ints
	protected boolean facingRight = true, onGround = false; // Boolean for direction facing and ground checking
	protected BufferedImage img = null; // Buffered image drawn in animation
	protected BufferedImage[] lAnims; // Array of all animations
	protected BufferedImage[] rAnims;
	protected BufferedImage[] nAnims;
	protected Rectangle pHurtbox; // Player's damage area or hurbox
	private static int framesPerAnimationCycle = 4;// Frames it takes for the animation drawn to change
	private static double flySpeed = 8.5;

	// Enum used to store all possible outputs for the player's stauts
	protected enum STATUS
	{
		IDLING, MOVING, JUMPING, CLIMBING
	};

	protected STATUS status; // Variable used for current status

	public void act() // Reads through the set of all keys and the player moves accordingly
	{
		Set<Integer> readKeys = (TreeSet<Integer>) (DataRetriever.getAllKeys());

		Interactable i = touchingInteractable();

		// If standing on the ground, the player must be Idling
		if (readKeys.size() == 0 && onGround && status != STATUS.CLIMBING)
		{
			// This pattern is followed by most key checks: If already doing something, advance animation; else, begin animation
			if (status == STATUS.IDLING)
			{
				elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 1) ? 0 : elapsedFrames + 1;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else
			{
				elapsedFrames = 0;
				curAnimation = 0;
				status = STATUS.IDLING;
			}

			runCollision();
			return; // Return because you're done
		}

		if (i != null)
		{
			if (i instanceof Ladder)
			{
				if (readKeys.contains(DataRetriever.getUp()) || readKeys.contains(DataRetriever.getDown()) && status != STATUS.CLIMBING)
				{
					status = STATUS.CLIMBING;
					elapsedFrames = 0;
					curAnimation = 0;
				}
			}
			else if (i instanceof ManCannon && onGround)
			{
				ySpeed -= ((ManCannon) i).getUpDelta();
				onGround = false;
			}
			else if (i instanceof ManCannon && !onGround)
			{
				ySpeed = 0;
				ySpeed -= ((ManCannon) i).getUpDelta();
			}
		}

		if (status == STATUS.CLIMBING)
		{
			ySpeed = 0;
			if (readKeys.contains(DataRetriever.getUp()) || readKeys.contains(DataRetriever.getDown()))
			{
				elapsedFrames = (elapsedFrames > 4 * framesPerAnimationCycle - 1) ? 0 : elapsedFrames + 1;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}

			if (readKeys.contains(DataRetriever.getUp()))
			{
				worldY -= xSpeed;
				World.setDrawY();
				y = worldY - World.getDrawY();
				pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
				if (pHurtbox.getY() + World.getDrawY() + pHeight <= i.getY())
				{
					ySpeed = DataRetriever.getGravityConstant();
					status = STATUS.IDLING;
					runCollisionY();
					onGround = true;
					return;
				}
			}
			else if (readKeys.contains(DataRetriever.getDown()))
			{
				worldY += xSpeed;
				World.setDrawY();
				y = worldY - World.getDrawY();
				pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
				if (pHurtbox.getY() + World.getDrawY() + pHeight > i.getY() + i.getHeight() - 5)
				{
					worldY -= 5;
					ySpeed = DataRetriever.getGravityConstant();
					status = STATUS.IDLING;
					runCollisionY();
					onGround = true;
					return;
				}
			}
			if (readKeys.contains(DataRetriever.getJump()) && (readKeys.contains(DataRetriever.getRight()) || readKeys.contains(DataRetriever.getLeft())))
			{
				if (inBlock()) return;
				else
				{
					status = STATUS.JUMPING;
					elapsedFrames = 0;
					curAnimation = 0;
					ySpeed -= jumpDelta * .75;
					worldX = readKeys.contains(DataRetriever.getRight()) ? worldX + xSpeed * 3.5 : worldX - xSpeed * 3.5;
					World.setDrawX();
					x = worldX - World.getDrawX();
					pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
					runCollision();
				}
			}

			World.setDrawY();
			y = worldY - World.getDrawY();
			pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
			return;
		}

		// If right and !left, then must be walking right
		else if (readKeys.contains(DataRetriever.getRight()) && !readKeys.contains(DataRetriever.getLeft()) && !(status == STATUS.CLIMBING))
		{
			worldX += xSpeed;
			x = worldX - World.getDrawX();

			if (facingRight && status == STATUS.MOVING)
			{
				elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 1) ? 0 : elapsedFrames + 1;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else
			{
				elapsedFrames = 0;
				curAnimation = 0;
				facingRight = true;
				status = STATUS.MOVING;
			}
		}

		// If left and !right, then must be walking left
		else if (readKeys.contains(DataRetriever.getLeft()) && !readKeys.contains(DataRetriever.getRight()) && !(status == STATUS.CLIMBING))
		{
			worldX -= xSpeed;
			x = worldX - World.getDrawX();

			if (!facingRight && status == STATUS.MOVING)
			{
				elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 1) ? 0 : elapsedFrames + 1;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else
			{
				elapsedFrames = 0;
				curAnimation = 0;
				facingRight = false;
				status = STATUS.MOVING;
			}
		}

		// If right and left, set the player to idle
		else if (readKeys.contains(DataRetriever.getRight()) && readKeys.contains(DataRetriever.getLeft()))
		{
			if (status == STATUS.IDLING)
			{
				elapsedFrames = (elapsedFrames > 8 * framesPerAnimationCycle - 1) ? 0 : elapsedFrames + 1;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else
			{
				elapsedFrames = 0;
				curAnimation = 0;
				status = STATUS.IDLING;
			}
		}

		// If the player jumps, add a ton to their y velocity
		if (readKeys.contains(DataRetriever.getJump()) && status != STATUS.CLIMBING && onGround) ySpeed -= jumpDelta;

		// If not touching the ground, status must be in mid-air so no animation is chosen
		if (!onGround && status != STATUS.CLIMBING) status = STATUS.JUMPING;

		runCollision();
	}

	// This method adjusts the hitbox and runs collision algorithms on both x and y for the player
	private void runCollision()
	{
		runCollisionX();
		runCollisionY();

		if (onGround) ySpeed = DataRetriever.getGravityConstant(); // If on ground, reset falling speed
		else ySpeed += DataRetriever.getGravityConstant(); // If in air, fall faster
	}

	private void runCollisionX()
	{
		// Reset Hurtbox and then check for collisions with any nearby rect; if colliding, force out of the block
		pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
		World.setDrawX();
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX() - World.getDrawX()), (int) (r.getY() - World.getDrawY()), (int) r.getWidth(), (int) r.getHeight()));
			while (pHurtbox.intersects(r2d)) // pHurtbox.intersects(r)
			{
				worldX = facingRight ? worldX - 1 : worldX + 1;
				x = worldX - World.getDrawX();
				pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
			}
		}

		World.setDrawX();
		x = worldX - World.getDrawX();
	}

	private void runCollisionY()
	{
		worldY += ySpeed; // Change y vars
		World.setDrawY();
		y = worldY - World.getDrawY(); // Adjust screen pos based on world pos
		onGround = false;
		boolean ceilingContact = false;

		// Just like the x, this checks y collisions and stops the player from getting through hitboxes
		pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX() - World.getDrawX()), (int) (r.getY() - World.getDrawY()), (int) r.getWidth(), (int) r.getHeight()));
			while (pHurtbox.intersects(r2d)) // pHurtbox.intersects(r)
			{
				worldY = ySpeed < 0 ? worldY + 1 : worldY - 1;
				y = worldY - World.getDrawY();
				pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
				if (ySpeed > 0) onGround = true;
				else ceilingContact = true;
			}
		}

		World.setDrawY();
		y = worldY - World.getDrawY();
		pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
		if (ceilingContact) ySpeed = DataRetriever.getGravityConstant();
	}

	// Method called in run() if devMode is true; allows the user to fly, while the noclip variable does exactly as it sounds
	public void devAct(boolean noclip)
	{
		Set<Integer> readKeys = (TreeSet<Integer>) (DataRetriever.getAllKeys());

		if (readKeys.size() == 0) return;
		if (readKeys.contains(DataRetriever.getRight()))
		{
			worldX += flySpeed;
			facingRight = true;
		}
		else if (readKeys.contains(DataRetriever.getLeft()))
		{
			worldX -= flySpeed;
			facingRight = false;
		}

		World.setDrawX();
		x = worldX - World.getDrawX();
		pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
		if (!noclip)
		{
			for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
			{
				Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX() - World.getDrawX()), (int) (r.getY() - World.getDrawY()), (int) r.getWidth(), (int) r.getHeight()));;
				while (pHurtbox.intersects(r2d)) // pHurtbox.intersects(r)
				{
					worldX = facingRight ? worldX - 1 : worldX + 1;
					x = worldX - World.getDrawX();
					pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
				}
			}
		}

		World.setDrawX();
		x = worldX - World.getDrawX();

		if (readKeys.contains(DataRetriever.getUp()) || readKeys.contains(DataRetriever.getJump())) worldY -= flySpeed;
		else if (readKeys.contains(DataRetriever.getDown())) worldY += flySpeed;

		y = worldY - World.getDrawY();
		pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
		World.setDrawY();
		if (!noclip)
		{
			for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
			{
				Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX() - World.getDrawX()), (int) (r.getY() - World.getDrawY()), (int) r.getWidth(), (int) r.getHeight()));
				while (pHurtbox.intersects(r2d)) // pHurtbox.intersects(r)
				{
					worldY = readKeys.contains(DataRetriever.getDown()) ? worldY - 1 : worldY + 1;
					y = worldY - World.getDrawY();
					pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
				}
			}
		}

		World.setDrawY();
		y = worldY - World.getDrawY();
		status = STATUS.JUMPING;
	}

	// Method which runs through the list of interactables in the world and checks for collision
	private Interactable touchingInteractable()
	{
		for (Rectangle jadams : DataRetriever.getWorld().getInterTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (jadams.getX() - World.getDrawX()), (int) (jadams.getY() - World.getDrawY()), (int) jadams.getWidth(), (int) jadams.getHeight()));
			if (pHurtbox.intersects(r2d)) { System.out.println("CHECK"); return (Interactable)jadams; }
		}

		return null;
	}

	// Method which returns true if the player is in a block
	private boolean inBlock()
	{
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX() - World.getDrawX()), (int) (r.getY() - World.getDrawY()), (int) r.getWidth(), (int) r.getHeight()));
			if (pHurtbox.intersects(r2d)) return true;
		}
		return false;
	}

	// Method to be overridden that draws each player by importing that file
	public abstract void drawPlayer(Graphics2D g2d);

	// Fills in the player's hitbox
	protected void drawHurtbox(Graphics2D g2d)
	{
		g2d.setColor(new Color(0, 255, 30, 60));
		g2d.fillRect((int) pHurtbox.getX(), (int) pHurtbox.getY(), (int) pHurtbox.getWidth(), (int) pHurtbox.getHeight());
	}

	//@formatter:off
	// All getters
	public double getX() {return x;}
	public double getY() {return y;}
	public double getWorldX() {return worldX;}
	public double getWorldY() {return worldY;}
	public int getXOffset() {return xOffset;}
	public int getHealth() {return health;}
	public int getLevel() {return level;}
	public int getMaxHealth() {return maxHealth;}
	public int getCurAnimation() {return curAnimation;}
	public double getXSpeed() {return xSpeed;}
	public double getYSpeed() {return ySpeed;}
	public Rectangle getHurtbox() {return pHurtbox;}
	public int getWidth() {return pWidth;}
	public int getHeight() {return pHeight;}
	public Rectangle getWorldbox() 
	{
		return new Rectangle((int) (pHurtbox.getX() + World.getDrawX()), (int) (pHurtbox.getY() + World.getDrawY()), (int) pHurtbox.getWidth(), (int) pHurtbox.getHeight());
	}

	// Setter methods
	public void setX(double nX) {x = nX;}
	public void setY(double nY) {y = nY;}
	public void setWorldX(double nWX) {worldX = nWX;}
	public void setWorldY(double nWY) {worldY = nWY;}
	public void setHealth(int nH) {health = nH;}
	public void setMaxHealth(int nMH) {maxHealth = nMH;}
	public void setXSpeed(double nXS) {xSpeed = nXS;}
	public void setYSpeed(double nYS) {ySpeed = nYS;}
	//@formatter:on
}