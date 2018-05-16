package org.players;
/**
 * RG
 * Abstract class that each player will extend
 * Will have the ground rules and fields that each object will need
 * Most of the variable names are either self-explanatory or just basic coordinate and stats
 * TODO: Actually add in stats like health, damage, crit chance, etc.
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
import org.Hitbox;
import org.world.World;
import org.world.interactable.Interactable;
import org.world.interactable.Ladder;
import org.world.interactable.ManCannon;
import org.world.interactable.Platform;

public abstract class Player
{
	protected int curAnimation, elapsedFrames, pWidth, pHeight, xOffset, yOffset; //Positioning variables
	protected int health, level, maxHealth, damage; // Basic stats
	protected double critChance, critModifier; //Chance of a hit being critical; probability goes up to 1.0, uses Math.random for calculation
	protected double armor; //Armor stat to reduce damage from hits
	protected double x, y, worldX, worldY, xSpeed, ySpeed, jumpDelta; // X and Y are doubles to keep absolute track of the players, while their drawing will be on ints
	protected boolean facingRight = true, onGround = false, onPlatform, inPlatform; // Boolean for direction facing and ground checking
	protected BufferedImage img = null; // Buffered image drawn in animation

	// Array of all animations
	protected BufferedImage[] lAnims; // Left motion
	protected BufferedImage[] rAnims; // Right motion
	protected BufferedImage[] nAnims; // Direction Neutral
	//All Attack Animations
	protected BufferedImage[][] lSkillAnims;
	protected BufferedImage[][] rSkillAnims;

	protected Rectangle pHurtbox; // Player's damage area or hurtbox
	protected Hitbox pHitbox; // Player's currently functioning hitbox
	protected static int framesPerAnimationCycle = 4;// Frames it takes for the animation drawn to change
	private static double flySpeed = 8.5;

	// Enum used to store all possible outputs for the player's status
	protected enum STATUS
	{
		IDLING, MOVING, JUMPING, CLIMBING, ATTACKING
	};

	protected STATUS status; // Variable used for current status

	protected enum SKILL
	{
		SKILL1, SKILL2, SKILL3, SKILL4, NONE
	};

	protected SKILL skill = SKILL.NONE;

	public void act() // Reads through the set of all keys and the player moves accordingly
	{
		boolean recognized = false;
		Set<Integer> readKeys = (TreeSet<Integer>) (DataRetriever.getAllKeys());
		Interactable i = touchingInteractable();

		//Check if player isn't attacking and if an attack is used
		if (readKeys.contains(DataRetriever.getSkillOne()) && skill == SKILL.NONE)
		{
			status = STATUS.ATTACKING; //You must be attacking
			skill = SKILL.SKILL1; //Change respective skill
			elapsedFrames = 0; //Reset animation
			curAnimation = 0; //Repeat for all attacks
		}
		else if (readKeys.contains(DataRetriever.getSkillTwo()) && skill == SKILL.NONE)
		{
			status = STATUS.ATTACKING;
			skill = SKILL.SKILL2;
			elapsedFrames = 0;
			curAnimation = 0;
		}
		else if (readKeys.contains(DataRetriever.getSkillThree()) && skill == SKILL.NONE)
		{
			status = STATUS.ATTACKING;
			skill = SKILL.SKILL3;
			elapsedFrames = 0;
			curAnimation = 0;
		}
		else if (readKeys.contains(DataRetriever.getSkillFour()) && skill == SKILL.NONE)
		{
			status = STATUS.ATTACKING;
			skill = SKILL.SKILL4;
			elapsedFrames = 0;
			curAnimation = 0;
		}

		if (status == STATUS.ATTACKING) //If the player is attacking
		{
			attack(); //Call attack method to get animations & hitboxes
			runCollision(); //Check for bumping into walls, falling, etc.
			checkInteractables(i, readKeys); //Check for hitting things
			return; //You're finished
		}

		// If standing on the ground, the player must be Idling
		if (readKeys.size() == 0 && onGround && status != STATUS.CLIMBING)
		{
			// This pattern is followed by most key checks: If already doing something, advance animation; else, begin animation
			if (status == STATUS.IDLING)
			{
				if (++elapsedFrames >= 8 * framesPerAnimationCycle) elapsedFrames = 0;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else //If you begin idling, reset status and animation
			{
				elapsedFrames = 0;
				curAnimation = 0;
				status = STATUS.IDLING;
			}

			runCollisionY(); //Idling, so you really just have to check Y collision
			return; // Return because you're done
		}

		checkInteractables(i, readKeys); //Check if you're hitting an interactable

		if (status == STATUS.CLIMBING) //If the player is climbing
		{
			ySpeed = 0;
			if (readKeys.contains(DataRetriever.getUp()) || readKeys.contains(DataRetriever.getDown())) //If moving
			{
				if (++elapsedFrames >= 4 * framesPerAnimationCycle) elapsedFrames = 0;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}

			if (readKeys.contains(DataRetriever.getUp())) //If climbing up
			{
				worldY -= xSpeed; //Move Up
				if (worldY < World.block) worldY += xSpeed; //Prevent climbing out of the world
				World.setDrawY(); //Reset world view
				y = worldY - World.getDrawY(); //Reset hitbox positioning
				pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset); //reset actual hitbox
				if (pHurtbox.getY() + World.getDrawY() + pHeight <= i.getY()) //If climbing off the top of the ladder
				{
					ySpeed = DataRetriever.getGravityConstant(); //Reset gravity
					status = STATUS.IDLING; //Back to idling
					runCollisionY(); //Run collision for blocks, force upwards
					onGround = true; //Must be on the ground
					return; //Finished, so return void
				}
			}
			else if (readKeys.contains(DataRetriever.getDown())) //Same as up, but down
			{
				worldY += xSpeed;
				World.setDrawY();
				y = worldY - World.getDrawY();
				pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
				if (pHurtbox.getY() + World.getDrawY() + pHeight > i.getY() + i.getHeight() - 5) //If at the bottom of the ladder
				{
					worldY -= 5; //Push up
					ySpeed = DataRetriever.getGravityConstant();
					status = STATUS.IDLING;
					runCollisionY(); //Fall to ground
					onGround = true;
					return;
				}
			}
			//If the player hits jump and a direction off of the ladder
			if (readKeys.contains(DataRetriever.getJump()) && (readKeys.contains(DataRetriever.getRight()) || readKeys.contains(DataRetriever.getLeft())))
			{
				if (inBlock()) return; //Immediately check if the player is in the block; if so, don't jump
				else
				{
					status = STATUS.JUMPING; //Set to jumping
					elapsedFrames = 0; //Reset animation
					curAnimation = 0;
					ySpeed -= jumpDelta * .75; //Give yourself some upwards velocity
					worldX = readKeys.contains(DataRetriever.getRight()) ? worldX + xSpeed * 3.5 : worldX - xSpeed * 3.5; //Launch off
					World.setDrawX(); //Reset the world view, player position, and hurtbox
					x = worldX - World.getDrawX();
					pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
					runCollision(); //Run your collision
					return; //All done
				}
			}

			World.setDrawY(); //Assumption: if you are on a ladder and don't jump off, you don't need to check y
			y = worldY - World.getDrawY();
			pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
			return;
		}

		// If right and !left, then must be walking right
		else if (readKeys.contains(DataRetriever.getRight()) && !readKeys.contains(DataRetriever.getLeft()) && !(status == STATUS.CLIMBING))
		{
			recognized = true; //Recognized input, avoid idling
			worldX += xSpeed; //Move right & reset position
			x = worldX - World.getDrawX();

			if (facingRight && status == STATUS.MOVING)
			{
				if (++elapsedFrames >= 8 * framesPerAnimationCycle) elapsedFrames = 0;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else
			{
				elapsedFrames = 0; //Reset animation to walking
				curAnimation = 0;
				facingRight = true; //Must be facing right
				status = STATUS.MOVING;
			}
		}

		// If left and !right, then must be walking left
		else if (readKeys.contains(DataRetriever.getLeft()) && !readKeys.contains(DataRetriever.getRight()) && !(status == STATUS.CLIMBING))
		{
			recognized = true; //Identical to walking right but the other right
			worldX -= xSpeed;
			x = worldX - World.getDrawX();

			if (!facingRight && status == STATUS.MOVING)
			{
				if (++elapsedFrames >= 8 * framesPerAnimationCycle) elapsedFrames = 0;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else
			{
				elapsedFrames = 0; //Reset animation
				curAnimation = 0;
				facingRight = false;
				status = STATUS.MOVING;
			}
		}

		// If right and left, set the player to idle
		else if (readKeys.contains(DataRetriever.getRight()) && readKeys.contains(DataRetriever.getLeft()))
		{
			recognized = true;
			if (status == STATUS.IDLING)
			{
				if (++elapsedFrames >= 8 * framesPerAnimationCycle) elapsedFrames = 0;
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
		if (readKeys.contains(DataRetriever.getJump()) && status != STATUS.CLIMBING && onGround)
		{
			recognized = true; //Found a recognized key
			ySpeed -= jumpDelta; //Push the player up
			onPlatform = false; //They player cannot be on or in a platform anymore
			inPlatform = false;
		}

		// If not touching the ground, status must be in mid-air so no animation is chosen
		if (!onGround && status != STATUS.CLIMBING) status = STATUS.JUMPING;

		if (onGround && !recognized && status != STATUS.CLIMBING) //If you haven't figured out what's happening, assume idle
		{
			if (status == STATUS.IDLING)
			{
				if (++elapsedFrames >= 8 * framesPerAnimationCycle) elapsedFrames = 0;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else
			{
				elapsedFrames = 0;
				curAnimation = 0;
				status = STATUS.IDLING;
			}
		}

		if (!onPlatform) runCollision(); //Run final collisions
		else runCollisionX();
	}

	//Method which checks the interactables and does all the logic for each
	private void checkInteractables(Interactable i, Set<Integer> readKeys)
	{
		if (i != null) //If actually in an interactable
		{
			if (i instanceof Platform) //Platform case
			{
				if (onPlatform && !((Platform) i).getTransparent()) //If you're on a platform and it is solid
				{
					Rectangle2D i2d = (Rectangle2D) (new Rectangle((int) (i.getX() - World.getDrawX()), (int) (i.getY() - World.getDrawY()) + 2, (int) i.getWidth(), (int) i.getHeight() - 2));
					while (pHurtbox.intersects(i2d)) //Raise the player to the top of the platform
					{
						worldY--;
						y = worldY - World.getDrawY();
						pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
					}
					ySpeed = 0;
					onGround = true; //Treat platform as solid ground
				}
				//If the player drops through
				if (readKeys.contains(DataRetriever.getDown()) && readKeys.contains(DataRetriever.getJump()) && onPlatform)
				{
					worldY += 3; //Move down and reset positioning
					World.setDrawY();
					y = worldY - World.getDrawY();
					pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
					if (!inBlock()) //If the player doesn't drop through into a block
					{
						onPlatform = false; //No longer on but in a platform
						inPlatform = true;
						ySpeed = 4 * DataRetriever.getGravityConstant(); //Launch the player out
						((Platform) i).setTransparent(true); //Set the platform to transparent
						onGround = false; //He's still in the air
					}
					else //If dropping into a block i.e. only halfway on platform
					{
						worldY -= 3; //Bump back up onto platform
						World.setDrawY();
						y = worldY - World.getDrawY();
						pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
					}
				}
			}

			if (i instanceof Ladder) //Ladder case
			{
				//If the player isn't already climbing and they hit a climb key
				if (readKeys.contains(DataRetriever.getUp()) || readKeys.contains(DataRetriever.getDown()) && status != STATUS.CLIMBING)
				{
					status = STATUS.CLIMBING; //Reset animation to climbing, movement handled above
					elapsedFrames = 0;
					curAnimation = 0;
				}
			}
			else if (i instanceof ManCannon && onGround) //If on ground and you bump a mancannon
			{
				ySpeed -= ((ManCannon) i).getUpDelta(); //Subtract some velocity
				onGround = false;
			}
			else if (i instanceof ManCannon && !onGround) //If mancannon but not onground
			{
				ySpeed = 0; //Reset speed (to counter plummeting)
				ySpeed -= ((ManCannon) i).getUpDelta();
			}
		}
	}

	//Method that is used for attack animations & hitbox generation
	public abstract void attack();

	// This method adjusts the hitbox and runs collision algorithms on both x and y for the player
	private void runCollision()
	{
		runCollisionX();
		runCollisionY();

		if (onGround) ySpeed = DataRetriever.getGravityConstant(); // If on ground, reset falling speed
		else ySpeed += DataRetriever.getGravityConstant(); // If in air, fall faster
	}

	protected void runCollisionX()
	{
		// Reset Hurtbox and then check for collisions with any nearby rect; if colliding, force out of the block
		World.setDrawX();
		x = worldX - World.getDrawX();
		pHurtbox.setLocation((int) x + xOffset, (int) y + yOffset);
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

	protected void runCollisionY()
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
				Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX() - World.getDrawX()), (int) (r.getY() - World.getDrawY()), (int) r.getWidth(), (int) r.getHeight()));
				;
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
	protected Interactable touchingInteractable()
	{
		ArrayList<Interactable> fixer = new ArrayList<Interactable>();

		for (Rectangle jadams : DataRetriever.getWorld().getInterTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (jadams.getX() - World.getDrawX()), (int) (jadams.getY() - World.getDrawY()), (int) jadams.getWidth(), (int) jadams.getHeight()));
			if (pHurtbox.intersects(r2d))
			{
				if (jadams instanceof Platform) //PLATFORM LOGIC
				{
					if (ySpeed < 0) //If the player is going up
					{
						((Platform) jadams).setTransparent(true); //Platform non-solid
						inPlatform = true; //In but nnot on
						onPlatform = false;
					}
					else if (!((Platform) jadams).getTransparent()) //If falling and the platform is solid
					{
						onPlatform = true; //Must be on platform
						((Platform) jadams).setTransparent(false);
					}
				}
				fixer.add((Interactable) jadams); //Put into AL for multi-collision handling
			}
			else if (jadams instanceof Platform) //If the player isn't hitting a platform
			{
				((Platform) jadams).setTransparent(false); //Make it solid
			}
		}
		if (fixer.size() != 0) //Drop the player onto the lowest platform
		{
			Interactable lowest = fixer.get(0);
			for (Interactable j : fixer)
			{
				if (j.getY() > lowest.getY()) lowest = j;
			}
			return lowest;
		}

		inPlatform = false;
		onPlatform = false;
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

	// Fills in the player's hurtbox
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