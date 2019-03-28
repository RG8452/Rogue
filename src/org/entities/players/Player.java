package org.entities.players;
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
import java.util.HashSet;

import org.DataRetriever;
import org.entities.Entity;
import org.world.World;
import org.world.interactable.Interactable;
import org.world.interactable.Ladder;
import org.world.interactable.ManCannon;
import org.world.interactable.Platform;

public abstract class Player extends Entity
{
	protected double critChance, critModifier; //Chance of a hit being critical; probability goes up to 1.0, uses Math.random for calculation
	protected double jumpDelta; //Velocity change that occurs when the player jumps
	protected boolean onPlatform, inPlatform; // Boolean for direction facing and ground checking
	protected BufferedImage img = null; // Buffered image drawn in animation

	protected BufferedImage[] rAnims; //Note:: Animations are declared in Player but not enemy
	protected BufferedImage[] lAnims; //The reasoning is that players are only ever constructed once, so there's no reason to statically read
	protected BufferedImage[] nAnims; // Idling animations
	protected BufferedImage[][] lSkillAnims; // L Attack animations
	protected BufferedImage[][] rSkillAnims; // R Attack animations

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
		Set<Integer> readKeys = (HashSet<Integer>) (DataRetriever.getAllKeys());
		Interactable i = touchingInteractable();

		//@formatter:off
		//Check if player isn't attacking and if an attack is used
		//Sets status, skill, and animation for the given attack skill
		if (readKeys.contains(DataRetriever.getSkillOne()) && skill == SKILL.NONE)
			setAttacking(SKILL.SKILL1);
		else if (readKeys.contains(DataRetriever.getSkillTwo()) && skill == SKILL.NONE)
			setAttacking(SKILL.SKILL2);
		else if (readKeys.contains(DataRetriever.getSkillThree()) && skill == SKILL.NONE)
			setAttacking(SKILL.SKILL3);
		else if (readKeys.contains(DataRetriever.getSkillFour()) && skill == SKILL.NONE)
			setAttacking(SKILL.SKILL4);
		//@formatter:on

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
			animateIdle(); //Idle away
			runCollisionY(); //Idling, so you really just have to check Y collision			
			return; // Return because you're done
		}

		checkInteractables(i, readKeys); //Check if you're hitting an interactable

		if (status == STATUS.CLIMBING) //If the player is climbing
		{
			climb(readKeys, i); //Do the climbing process

			World.setDrawY(); //Assumption: if you are on a ladder and don't jump off, you don't need to check y
			worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
			return;
		}

		// If right and !left, then must be walking right
		else if (readKeys.contains(DataRetriever.getRight()) && !readKeys.contains(DataRetriever.getLeft()) && !(status == STATUS.CLIMBING))
		{
			recognized = true; //Recognized input, avoid idling
			worldX += xSpeed; //Move right & reset position
			animateWalk(facingRight);
			facingRight = true;
		}

		// If left and !right, then must be walking left
		else if (readKeys.contains(DataRetriever.getLeft()) && !readKeys.contains(DataRetriever.getRight()) && !(status == STATUS.CLIMBING))
		{
			recognized = true; //Identical to walking right but the other right
			worldX -= xSpeed;
			animateWalk(!facingRight);
			facingRight = false;
		}

		// If right and left, set the player to idle
		else if (readKeys.contains(DataRetriever.getRight()) && readKeys.contains(DataRetriever.getLeft()))
		{
			recognized = true;
			animateIdle();
		}

		// If the player jumps, give them upward velocity and set status
		if (readKeys.contains(DataRetriever.getJump()) && onGround)
		{
			recognized = true; //Found a recognized key
			ySpeed -= jumpDelta; //Push the player up
			onPlatform = false; //They player cannot be on or in a platform anymore
			inPlatform = false;
			status = STATUS.JUMPING;
		}

		if (onGround && !recognized && status != STATUS.CLIMBING) //If you haven't figured out what's happening, assume idle
			animateIdle();
		
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
					Rectangle2D i2d = (Rectangle2D) (new Rectangle((int) (i.getX()), (int) (i.getY()) + 2, (int) i.getWidth(), (int) i.getHeight() - 2));
					if(worldbox.intersects(i2d))
					{
						int yDiff = (int) (worldbox.getY() + worldbox.getHeight() - i2d.getY());
						worldY -= yDiff;
						worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
					}
					ySpeed = 0;
					onGround = true; //Treat platform as solid ground
				}
				//If the player drops through
				if (readKeys.contains(DataRetriever.getDown()) && readKeys.contains(DataRetriever.getJump()) && onPlatform)
				{
					worldY += 3; //Move down and reset positioning
					worldbox.setLocation(worldbox.x, worldbox.y + 3);
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
						worldbox.setLocation(worldbox.x, worldbox.y - 3);
					}
					World.setDrawY();
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
			else if (i instanceof ManCannon) //If you bump a mancannon
			{
				ySpeed = -((ManCannon) i).getUpDelta(); //Subtract some velocity
				onGround = false;
			}
		}
	}

	//Method that is used for attack animations & hitbox generation
	public void attack()
	{
		switch (skill)
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
	
	//@formatter:off
	protected abstract void attackOne();
	protected abstract void attackTwo();
	protected abstract void attackThree();
	protected abstract void attackFour();
	//@formatter:off

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
		worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			//If you're intersecting a given block
			if(worldbox.intersects(r))
			{
				int xDiff;
				if(facingRight) //Find diff between player right and block left
					xDiff = worldbox.x + worldbox.width - r.x;
				else //Find diff between player left and block right
					xDiff = worldbox.x - (r.x + r.width);
				worldX -= xDiff;
				worldbox.setLocation((int) worldX + xOffset, worldbox.y);
				break;
			}
		}

		World.setDrawX();
	}

	protected void runCollisionY()
	{
		worldY += ySpeed; // Change y vars
		onGround = false;

		// Just like the x, this checks y collisions and stops the player from getting through hitboxes
		worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			if(worldbox.intersects(r))
			{
				if(ySpeed >= 0.0)
				{
					onGround = true;
					int yDiff = worldbox.y + worldbox.height - r.y;
					worldY -= yDiff;
					worldbox.setLocation(worldbox.x, worldbox.y -= yDiff);
					ySpeed = 0.0;
				}
				else
				{
					int yDiff = r.y + r.height - worldbox.y;
					worldY += yDiff;
					worldbox.setLocation(worldbox.x, worldbox.y += yDiff);
					ySpeed = DataRetriever.getGravityConstant();
				}
				break;
			}
		}

		World.setDrawY();
	}

	// Method called in run() if devMode is true; allows the user to fly, while the noclip variable does exactly as it sounds
	public void devAct(boolean noclip)
	{
		Set<Integer> readKeys = (HashSet<Integer>) (DataRetriever.getAllKeys());

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
		worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
		if (!noclip)
		{
			for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
			{
				if(worldbox.intersects(r))
				{
					int xDiff;
					if(facingRight) //Find diff between player right and block left
						xDiff = worldbox.x + worldbox.width - r.x;
					else //Find diff between player left and block right
						xDiff = worldbox.x - (r.x + r.width);
					worldX -= xDiff;
					worldbox.setLocation((int) worldX + xOffset, worldbox.y);
					break;
				}
			}
		}

		World.setDrawX();

		if (readKeys.contains(DataRetriever.getUp()) || readKeys.contains(DataRetriever.getJump())) worldY -= flySpeed;
		else if (readKeys.contains(DataRetriever.getDown())) worldY += flySpeed;

		worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
		World.setDrawY();
		if (!noclip)
		{
			for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
			{
				if(worldbox.intersects(r))
				{
					if(ySpeed > 0)
					{
						onGround = true;
						int yDiff = worldbox.y + worldbox.height - r.y;
						worldY -= yDiff;
						worldbox.setLocation(worldbox.x, worldbox.y -= yDiff);
						ySpeed = 0;
					}
					else
					{
						int yDiff = r.y + r.height - worldbox.y;
						worldY += yDiff;
						worldbox.setLocation(worldbox.x, worldbox.y += yDiff);
						ySpeed = 0;
					}
					break;
				}
			}
		}

		World.setDrawY();
		status = STATUS.JUMPING;
	}

	// Method which runs through the list of interactables in the world and checks for collision
	protected Interactable touchingInteractable()
	{
		ArrayList<Interactable> fixer = new ArrayList<Interactable>();

		for (Rectangle jadams : DataRetriever.getWorld().getInterTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			if (worldbox.intersects(jadams))
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
			if (worldbox.intersects(r)) return true;
		return false;
	}
	
	//Method to set the player's status to attacking, with skill status skill
	private void setAttacking(SKILL attack)
	{
		elapsedFrames = 0; //Reset anim
		curAnimation = 0;
		skill = attack; //Set attack
		status = STATUS.ATTACKING; //Set status
	}

	//Player idles
	private void animateIdle()
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
	}
	
	//Player walk command, primarily for animating
	private void animateWalk(boolean facingDir)
	{
		if (facingDir && status == STATUS.MOVING)
		{
			if (++elapsedFrames >= 8 * framesPerAnimationCycle) elapsedFrames = 0;
			curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
		}
		else
		{
			elapsedFrames = 0; //Reset animation to walking
			curAnimation = 0;
			status = STATUS.MOVING;
		}
	}
	
	//Climbing subsection
	private void climb(Set<Integer> readKeys, Interactable i)
	{
		//Reset velocity to ensure it's zero
		ySpeed = 0.0;
		
		//Animate; both up and down use the same keys
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
			worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset); //reset actual hitbox
			if (worldbox.getY() + height <= i.getY()) //If climbing off the top of the ladder
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
			worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
			if (worldbox.getY() + height > i.getY() + i.getHeight() - 5) //If at the bottom of the ladder
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
			//Immediately check if the player is in the block; if so, don't jump
			if(!inBlock())
			{
				status = STATUS.JUMPING; //Set to jumping
				elapsedFrames = 0; //Reset animation
				curAnimation = 0;
				ySpeed -= jumpDelta * .75; //Give yourself some upwards velocity
				worldX = readKeys.contains(DataRetriever.getRight()) ? worldX + xSpeed * 3.5 : worldX - xSpeed * 3.5; //Launch off
				World.setDrawX(); //Reset the world view, player position, and hurtbox
				worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
				runCollisionY(); //Run your collision
				return; //All done
			}
		}
	}
	
	// Method to be overridden that draws each player by importing that file
	public abstract void drawPlayer(Graphics2D g2d);

	// Fills in the player's hurtbox
	protected void drawHurtbox(Graphics2D g2d)
	{
		g2d.setColor(new Color(0, 255, 30, 60));
		g2d.fillRect((int) (worldbox.getX() - World.getDrawX()), (int) (worldbox.getY() - World.getDrawY()), (int)worldbox.getWidth(), (int)worldbox.getHeight());
	}
}