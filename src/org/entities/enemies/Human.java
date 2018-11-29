package org.entities.enemies;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Set;

import org.DataRetriever;
import org.world.World;
import org.world.interactable.Interactable;
import org.world.interactable.Ladder;
import org.world.interactable.ManCannon;
import org.world.interactable.Platform;

public abstract class Human extends Enemy implements AI
{
	private Rectangle destination; // Helper variable that allows the enemy to track different objects based on the current "PATHING" state
	protected boolean onPlatform, inPlatform; // Boolean for direction facing and ground checking
	protected abstract int inRange(); // Int which determines the range at which an enemy will switch between STATUS.PATHING and STATUS.ATTACKING
	
	public void act()
	{
		follow();
		
		// TODO Set movement values for all states (i.e. change y for climbing, x for pathing, etc.)
		if (this.status == STATUS.IDLING)
		{
			if (++elapsedFrames >= 8 * framesPerAnimationCycle) elapsedFrames = 0;
			curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
		}
		
		if (!this.onGround)
		{
			if (this.status == STATUS.CLIMBING)
			{
				if (++elapsedFrames >= 4 * framesPerAnimationCycle) elapsedFrames = 0;
				curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
			}
			else
			{
				this.status = STATUS.JUMPING;
			}
		}
			
		if (this.status == STATUS.PATHING)
		{
			if (facingRight) this.worldX += this.xSpeed;
			else this.worldX -= this.xSpeed;
			
			if (++elapsedFrames >= 8 * framesPerAnimationCycle) elapsedFrames = 0;
			curAnimation = (int) (elapsedFrames / framesPerAnimationCycle);
		}
		
/*		if (this.status == STATUS.ATTACKING)
		{
			
		}
*/
		super.runCollision();
	}
	// Tracks the Player's left to right motions
	public void follow()
	{
//		Interactable i = touchingInteractable();
		
		destination = DataRetriever.getPlayer().getWorldbox();
		
		if (destination.getX() > this.getWorldX())
		{
			facingRight = true;
			if (destination.getX() > this.getWorldX() + (inRange() + this.getXOffset()))
			{
				this.status = STATUS.PATHING;
			}
			else if (destination.getX() <= this.getWorldX() + (inRange() + this.getXOffset()) && destination.equals(DataRetriever.getPlayer().getWorldbox()))
			{
				this.status = STATUS.IDLING; //This will change to STATUS.ATTACKING when attacks are finalized
			}
		}
		else
		{
			facingRight = false;
			if (destination.getX() < this.getWorldX() - (inRange() - this.getXOffset()))
			{
				this.status = STATUS.PATHING;
			}
			else if (destination.getX() >= this.getWorldX() - (inRange() - this.getXOffset()) && destination.equals(DataRetriever.getPlayer().getWorldbox()))
			{
				this.status = STATUS.IDLING; //This will change to STATUS.ATTACKING when attacks are finalized
			}
		}
		
/*		if (destination.getY() < this.getWorldY())
		{
			above();
		}
		else if (destination.getY() > this.getWorldY())
		{
			below();
		}
		else
		{
		
		}
*/
	}
	
/*
 * Methods to be revisited for up and down movement as well as interactables
 * 
	// Finds a place to move downwards to the same floor as the Player
	private void below()
	{
		ArrayList<Rectangle> Ladders = findLadders("down");
		if (Ladders.size() >= 2)
		{
			destination = Ladders.get((int) Math.random());
		}
		else if (!Ladders.isEmpty())
		{
			destination = Ladders.get(0);
		}
		else 
		{
			follow();
		}
		
		if (this.getWorldX() > destination.getX() && !this.getWorldbox().intersects(destination))
		{
			this.worldX -= this.xSpeed;
		}
		else if (this.getWorldX() < destination.getX() && !this.getWorldbox().intersects(destination))
		{
			this.worldX += this.xSpeed;
		}
	}
	
	// Finds a place to move upwards to the same floor as the Player
	private void above()
	{
		ArrayList<Rectangle> Ladders = findLadders("up");
		if (Ladders.size() >= 2)
		{
			destination = Ladders.get((int) Math.random());
		}
		else if (!Ladders.isEmpty())
		{
			destination = Ladders.get(0);
		}
		else 
		{
			follow();
		}
		
		if (this.getWorldX() > destination.getX() && !this.getWorldbox().intersects(destination))
		{
			this.worldX -= this.xSpeed;
		}
		else if (this.getWorldX() < destination.getX() && !this.getWorldbox().intersects(destination))
		{
			this.worldX += this.xSpeed;
		}
	}
	
	private ArrayList<Rectangle> findLadders(String direction)
	{
		ArrayList<Rectangle> Ladders = new ArrayList<Rectangle>();
		// Sets up a list of all the nearby ladders// Chooses one of the closest two Ladders as a destination for the enemy
		for (Rectangle i : DataRetriever.getWorld().getInterTree().retrieve(new ArrayList<Rectangle>(), this.getWorldbox()))
		{
			if (i instanceof Ladder)
			{
				if (!Ladders.isEmpty())
				{
					if (this.getWorldX() - Ladders.get(0).getX() > this.getWorldX() - i.getX())
						Ladders.add(0, i);
					else if (this.getWorldX() - Ladders.get(1).getX() > this.getWorldX() - i.getX())
						Ladders.add(1, i);
					else 
						Ladders.add(i);
				}
				else
					Ladders.add(i);
			}
		}
		switch(direction)
		{
			case "up": 
				for (Rectangle l : Ladders) // Removes ladders that would move the Enemy downwards from the list
				{
					if (l.getY() > this.getWorldY())
					{
						Ladders.remove(l);
					}
				}
				break;
			case "down":
				for (Rectangle l : Ladders) // Removes ladders that would move the Enemy upwards from the list
				{
					if (l.getY() <= this.getWorldY())
					{
						Ladders.remove(l);
					}
				}
				break;
		}
		return Ladders;
	}
	
	// Method which runs through the list of interactables in the world and checks for collision
	protected Interactable touchingInteractable()
	{
		ArrayList<Interactable> fixer = new ArrayList<Interactable>();

		for (Rectangle jadams : DataRetriever.getWorld().getInterTree().retrieve(new ArrayList<Rectangle>(), getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (jadams.getX()), (int) (jadams.getY()), (int) jadams.getWidth(), (int) jadams.getHeight()));
			if (this.worldbox.intersects(r2d))
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

	private void checkInteractables(Interactable i, Set<Integer> readKeys) // TODO replace readKeys values with enemy pathing logic
	{
		if (i != null) //If actually in an interactable
		{
			if (i instanceof Platform) //Platform case
			{
				if (onPlatform && !((Platform) i).getTransparent()) //If you're on a platform and it is solid
				{
					Rectangle2D i2d = (Rectangle2D) (new Rectangle((int) (i.getX()), (int) (i.getY()) + 2, (int) i.getWidth(), (int) i.getHeight() - 2));
					while (worldbox.intersects(i2d)) //Raise the player to the top of the platform
					{
						worldY--;
						worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
					}
					ySpeed = 0;
					onGround = true; //Treat platform as solid ground
				}
				//If the player drops through
				if (readKeys.contains(DataRetriever.getDown()) && readKeys.contains(DataRetriever.getJump()) && onPlatform)
				{
					worldY += 3; //Move down and reset positioning
					World.setDrawY();
					worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
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
						worldbox.setLocation((int) worldX + xOffset, (int) worldY + yOffset);
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
*/

	// Method which returns true if the enemy is in a block
	private boolean inBlock()
	{
		for (Rectangle r : DataRetriever.getWorld().getCollisionTree().retrieve(new ArrayList<Rectangle>(), this.getWorldbox()))
		{
			Rectangle2D r2d = (Rectangle2D) (new Rectangle((int) (r.getX()), (int) (r.getY()), (int) r.getWidth(), (int) r.getHeight()));
			if (this.worldbox.intersects(r2d)) return true;
		}
		return false;
	}
}
