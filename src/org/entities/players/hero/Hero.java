package org.entities.players.hero;
/**
 * RG
 * This is the class for the Hero player. It will extend Player and have all drawing and attacks necessary for the Hero.
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.DataRetriever;
import org.Hitbox;
import org.Startup;
import org.entities.players.Player;
import org.world.World;
import org.world.interactable.Interactable;
import org.world.interactable.ManCannon;
import org.world.interactable.Platform;

public class Hero extends Player
{
	private static int bDamage = 5, baseMHP = 100;
	private HWave wave = null;
	private boolean touchedMCOnRetreat = false;

	public Hero(double h, double k) // Constructor to initialize everything
	{
		worldX = h;
		worldY = k;
		damage = bDamage;
		health = maxHealth;
		xSpeed = 7;
		ySpeed = 3.5;
		curAnimation = 0;
		elapsedFrames = 0;
		width = 24;
		height = 54;
		xOffset = 84;
		yOffset = 6;
		critModifier = 1.5;
		critChance = .01;
		level = 1;
		worldbox = new Rectangle((int) worldX + xOffset, (int) worldY + yOffset, width, height);
		hitbox = null;
		jumpDelta = 16;
		rAnims = new BufferedImage[12];
		lAnims = new BufferedImage[12];
		nAnims = new BufferedImage[2];
		lSkillAnims = new BufferedImage[][] { new BufferedImage[12], new BufferedImage[8], new BufferedImage[6], new BufferedImage[8] };
		rSkillAnims = new BufferedImage[][] { new BufferedImage[12], new BufferedImage[8], new BufferedImage[6], new BufferedImage[8] };

		try // This little chunk reads in every animation image and stores them into the arrays
		{
			rAnims[0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_MoveRight1.png"));
			rAnims[1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_MoveRight2.png"));
			rAnims[2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_MoveRight3.png"));
			rAnims[3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_MoveRight4.png"));
			rAnims[4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_MoveRight5.png"));
			rAnims[5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_MoveRight6.png"));
			rAnims[6] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_MoveRight7.png"));
			rAnims[7] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_MoveRight8.png"));
			rAnims[8] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_IdleRight1.png"));
			rAnims[9] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_IdleRight2.png"));
			rAnims[10] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_JumpRight1.png"));
			rAnims[11] = ImageIO.read(new File("src/org/entities/players/hero/Animations/RightFacing/Hero_JumpRight2.png"));

			lAnims[0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_MoveLeft1.png"));
			lAnims[1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_MoveLeft2.png"));
			lAnims[2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_MoveLeft3.png"));
			lAnims[3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_MoveLeft4.png"));
			lAnims[4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_MoveLeft5.png"));
			lAnims[5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_MoveLeft6.png"));
			lAnims[6] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_MoveLeft7.png"));
			lAnims[7] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_MoveLeft8.png"));
			lAnims[8] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_IdleLeft1.png"));
			lAnims[9] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_IdleLeft2.png"));
			lAnims[10] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_JumpLeft1.png"));
			lAnims[11] = ImageIO.read(new File("src/org/entities/players/hero/Animations/LeftFacing/Hero_JumpLeft2.png"));

			nAnims[0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Hero_Ladder1.png"));
			nAnims[1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Hero_Ladder2.png"));

			lSkillAnims[0][0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L1.png"));
			lSkillAnims[0][1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L2.png"));
			lSkillAnims[0][2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L3.png"));
			lSkillAnims[0][3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L4.png"));
			lSkillAnims[0][4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L5.png"));
			lSkillAnims[0][5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L6.png"));
			lSkillAnims[0][6] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L7.png"));
			lSkillAnims[0][7] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L8.png"));
			lSkillAnims[0][8] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L9.png"));
			lSkillAnims[0][9] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L10.png"));
			lSkillAnims[0][10] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L11.png"));
			lSkillAnims[0][11] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_L12.png"));

			rSkillAnims[0][0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R1.png"));
			rSkillAnims[0][1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R2.png"));
			rSkillAnims[0][2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R3.png"));
			rSkillAnims[0][3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R4.png"));
			rSkillAnims[0][4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R5.png"));
			rSkillAnims[0][5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R6.png"));
			rSkillAnims[0][6] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R7.png"));
			rSkillAnims[0][7] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R8.png"));
			rSkillAnims[0][8] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R9.png"));
			rSkillAnims[0][9] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R10.png"));
			rSkillAnims[0][10] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R11.png"));
			rSkillAnims[0][11] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S1/HeroS1_R12.png"));

			lSkillAnims[1][0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_L1.png"));
			lSkillAnims[1][1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_L2.png"));
			lSkillAnims[1][2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_L3.png"));
			lSkillAnims[1][3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_L4.png"));
			lSkillAnims[1][4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_L5.png"));
			lSkillAnims[1][5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_L6.png"));
			lSkillAnims[1][6] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_L7.png"));
			lSkillAnims[1][7] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_L8.png"));

			rSkillAnims[1][0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_R1.png"));
			rSkillAnims[1][1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_R2.png"));
			rSkillAnims[1][2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_R3.png"));
			rSkillAnims[1][3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_R4.png"));
			rSkillAnims[1][4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_R5.png"));
			rSkillAnims[1][5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_R6.png"));
			rSkillAnims[1][6] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_R7.png"));
			rSkillAnims[1][7] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S2/HeroS2_R8.png"));

			lSkillAnims[2][0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_L1.png"));
			lSkillAnims[2][1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_L2.png"));
			lSkillAnims[2][2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_L3.png"));
			lSkillAnims[2][3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_L4.png"));
			lSkillAnims[2][4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_L5.png"));
			lSkillAnims[2][5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_L6.png"));

			rSkillAnims[2][0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_R1.png"));
			rSkillAnims[2][1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_R2.png"));
			rSkillAnims[2][2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_R3.png"));
			rSkillAnims[2][3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_R4.png"));
			rSkillAnims[2][4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_R5.png"));
			rSkillAnims[2][5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S3/HeroS3_R6.png"));

			lSkillAnims[3][0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_L1.png"));
			lSkillAnims[3][1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_L2.png"));
			lSkillAnims[3][2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_L3.png"));
			lSkillAnims[3][3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_L4.png"));
			lSkillAnims[3][4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_L5.png"));
			lSkillAnims[3][5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_L6.png"));

			rSkillAnims[3][0] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_R1.png"));
			rSkillAnims[3][1] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_R2.png"));
			rSkillAnims[3][2] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_R3.png"));
			rSkillAnims[3][3] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_R4.png"));
			rSkillAnims[3][4] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_R5.png"));
			rSkillAnims[3][5] = ImageIO.read(new File("src/org/entities/players/hero/Animations/Abilities/S4/HeroS4_R6.png"));
		}
		catch (IOException e)
		{
			System.out.println("IMAGE READING ERROR (Hero): " + e);
		}

		status = STATUS.IDLING;
	}

	//This method is called when the player is drawn
	//Each image is stored in the subdirectories of hero/Animations and inside the anims[] arrays
	//Depending on status and orientation, the proper image is chosen
	@Override
	public void drawPlayer(Graphics2D g2d)
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

			else if (status == STATUS.MOVING) img = rAnims[curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL1) img = rSkillAnims[0][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL2) img = rSkillAnims[1][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL3) img = rSkillAnims[2][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL4) img = rSkillAnims[3][curAnimation];
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

			else if (status == STATUS.MOVING) img = lAnims[curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL1) img = lSkillAnims[0][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL2) img = lSkillAnims[1][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL3) img = lSkillAnims[2][curAnimation];

			else if (status == STATUS.ATTACKING && skill == SKILL.SKILL4) img = lSkillAnims[3][curAnimation];
		}

		if (wave != null)
		{
			wave.draw(g2d);
			wave.render();
		}

		g2d.drawImage(img, (int) (worldX - World.getDrawX()), (int) (worldY - World.getDrawY()), null);
		if (Startup.getRunner().hitboxesEnabled()) drawHurtbox(g2d);
		if (Startup.getRunner().hitboxesEnabled() && hitbox != null) hitbox.drawHitbox(g2d);
	}

	@Override
	protected void attackOne() //Basic Slash FINISHED
	{
		if (++elapsedFrames > 12 * framesPerAnimationCycle - 1) //If the animation has run out of frames
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			elapsedFrames = 0;
			curAnimation = 0;
			hitbox = null;
		}
		else //Progress the animation
		{
			curAnimation = elapsedFrames / framesPerAnimationCycle;
			if (curAnimation != 0 && curAnimation % 4 == 0) //If the player has used one of the three phases
			{
				if (!(DataRetriever.getAllKeys().contains(DataRetriever.getSkillOne()))) //Check if the player wants to attack again
				{
					status = STATUS.IDLING; //If the player stops hitting attack, stop attacking, else progress
					skill = SKILL.NONE;
					elapsedFrames = 0;
					curAnimation = 0;
					hitbox = null;
					return;
				}
			}
			if (curAnimation == 4 || curAnimation == 5 || curAnimation == 9 || curAnimation == 10) //On frames 5,6,10,11 move the player
			{
				worldX += facingRight ? 2 : -2;
			}
			else if (curAnimation == 2) //On frame 3, generate a hitbox
			{
				if (elapsedFrames == 2 * framesPerAnimationCycle) //R:{54,3}to{66,25}; L:{29,3}to{41,25}
				{
					hitbox = new Hitbox(facingRight ? (int) worldX + 106 : (int) worldX + 56, (int) worldY + 4, 28, 48);
				}
				hitbox.render(Math.random() > critChance ? damage : (int) (damage * critModifier), false);
				return;
			}
			if (curAnimation == 6) //On frame 7, generate a hitbox
			{
				if (elapsedFrames == 6 * framesPerAnimationCycle) //R:{61,1}to{69,25}; L:{31,1}to{39,25}
				{
					hitbox = new Hitbox(facingRight ? (int) worldX + 120 : (int) worldX + 60, (int) worldY, 20, 52);
				}
				hitbox.render(Math.random() > critChance ? damage : (int) (damage * critModifier), false);
				return;
			}
			if (curAnimation == 10) //On frame 11, generate a hitbox
			{
				if (elapsedFrames == 10 * framesPerAnimationCycle) //R:{53,9}to{66,16}; L:{29,9}to{42,16}
				{
					hitbox = new Hitbox(facingRight ? (int) worldX + 104 : (int) worldX + 56, (int) worldY + 16, 36, 18);
				}
				hitbox.render(Math.random() > critChance ? damage : (int) (damage * critModifier), false);
				return;
			}
		}
	}

	@Override
	protected void attackTwo() //Dash Stab FINISHED
	{
		if (++elapsedFrames > 8 * framesPerAnimationCycle - 1)
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			elapsedFrames = 0;
			curAnimation = 0;
			hitbox = null;
		}
		else
		{
			curAnimation = elapsedFrames / framesPerAnimationCycle;
			worldX += facingRight ? 4 : -4;

			if (curAnimation > 2 && curAnimation < 6) //Frames 4-6
			{
				if (elapsedFrames == 3 * framesPerAnimationCycle) //R:{54,15}to{67,18}; L:{25,15}to{39,18}
				{
					hitbox = new Hitbox(facingRight ? (int) worldX + 102 : (int) worldX + 52, (int) worldY + 28, 36, 12);
				}
				hitbox.setLocation(facingRight ? hitbox.x + 4 : hitbox.x - 4, hitbox.y);
				hitbox.render(Math.random() > critChance ? damage : (int) (damage * critModifier), false);
			}
			else if (curAnimation == 6 || curAnimation == 7) //Frames 7 and 8
			{
				if (elapsedFrames == 6 * framesPerAnimationCycle) //Generate a hitbox that renders twice
				{
					hitbox = new Hitbox(0, 0, 0, 0);
				}
				//First box: R:{54,15}to{67,18}; L:{25,15}to{39,18}
				hitbox.setLocation(facingRight ? (int) worldX + 96 : (int) worldX + 43, (int) worldY + 28);
				hitbox.setSize(48, 12);
				hitbox.render(Math.random() > critChance ? damage : (int) (damage * critModifier), false);
				//Second box: R:{65,10}to{70,23}; L:{28,10}to{33,23}
				hitbox.setLocation(facingRight ? (int) worldX + 125 : (int) worldX + 54, (int) worldY + 18);
				hitbox.setSize(17, 40);
				hitbox.render(Math.random() > critChance ? damage : (int) (damage * critModifier), false);
			}
		}
	}

	@Override
	protected void attackThree() //Retreat FINISHED
	{
		if (elapsedFrames == 0 && !onGround && !onPlatform) //If the attack starts in the air, quick fail
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			hitbox = null;
			return;
		}
		if (++elapsedFrames > 6 * framesPerAnimationCycle - 1) //If the attack runs out of time
		{
			worldY -= 5;
			yOffset = 6;
			worldbox.setSize(width, height);
			status = STATUS.IDLING; //Reset gravity, positioning, and animation
			skill = SKILL.NONE;
			elapsedFrames = 0;
			curAnimation = 0;
			ySpeed = DataRetriever.getGravityConstant();
			World.setDrawY();

			Interactable nyeh = touchingInteractable(); //Check for platform interaction
			if (nyeh instanceof Platform) ((Platform) nyeh).setTransparent(false);
			touchedMCOnRetreat = false;
		}
		else //Proceed animation
		{
			yOffset = 32;
			worldbox.setSize(width, height - 26);
			curAnimation = elapsedFrames / framesPerAnimationCycle;
			worldX += facingRight ? touchedMCOnRetreat ? -4 : -6 : touchedMCOnRetreat ? 4 : 6;
			facingRight = !facingRight; //Push the player backwards, and flip direction quickly for wall checking
			runCollisionX();
			facingRight = !facingRight; //Flip back after wall checking
			if (curAnimation < 1) worldY -= 2; //Change position
			else if (curAnimation > 4) worldY += 2;

			Interactable nyeh = touchingInteractable(); //Run through platform checking & MCannons
			if (nyeh instanceof Platform) ((Platform) nyeh).setTransparent(true);
			else if (nyeh instanceof ManCannon)
			{
				ySpeed -= ((ManCannon) nyeh).getUpDelta();
				onGround = false;
				touchedMCOnRetreat = true;
				return;
			}
			if (!touchedMCOnRetreat) worldY -= ySpeed; //If the player hit a mancannon, launch
		}
	}

	protected void attackFour() //Great Slash FINISHED
	{
		if (elapsedFrames == 0 && wave != null) return;
		if (++elapsedFrames > 6 * framesPerAnimationCycle - 1 && wave != null && wave.getFrame() > 2) //If the animation is over, finish
		{
			status = STATUS.IDLING;
			skill = SKILL.NONE;
			elapsedFrames = 0;
			curAnimation = 0;
			hitbox = null;

			return;
		}
		else if (wave != null) return;
		else
		{
			curAnimation = elapsedFrames / framesPerAnimationCycle;

			if (curAnimation == 2) //Frame 3
			{
				if (elapsedFrames == 2 * framesPerAnimationCycle)
				{
					hitbox = new Hitbox(facingRight ? (int) worldX + 104 : (int) worldX + 56, (int) worldY, 32, 56);
				}
				hitbox.render(Math.random() > critChance ? damage : (int) (damage * critModifier), false);
			}

			if (curAnimation == 5) //Frame 6
				wave = new HWave(facingRight ? (int) worldX + 32 : (int) worldX - 32, (int) worldY, facingRight, damage);
		}
	}

	//@formatter:off
	protected String getClassName() {return "Hero";}
	protected int getBaseDamage() {return bDamage;}
	protected int getBaseHealth() {return baseMHP;}
	public void destroyWave() {wave = null;}
	//@formatter:on
}