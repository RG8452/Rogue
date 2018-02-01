package org.world.interactable;
/*
 * RG
 * It's a man cannon
 * It blasts men upwards
 * Each man cannon will have a unique field for y velocity subtracted 
 * The world type will be passed as a parameter and used to generate different textures
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.Startup;
import org.panels.GamePanel;
import org.world.World;
import org.world.dwarvencaverns.DwarvenCaverns;

public class ManCannon extends Interactable
{
	private double upDelta;
	private int curAnimation, elapsedFrames, framesPerAnimation = 2;	//Animation info
	private BufferedImage[] anims;
	
	public ManCannon(int x, int y, int upBlocks, World w)
	{
		box = new Rectangle(block * x, block * y - 2, block, block * 2);

		upDelta = (upBlocks > 4) ? upBlocks + upBlocks + 6 + (8 - upBlocks) : upBlocks * 3 + 4;
		
		anims = new BufferedImage[8];
		
		if(w instanceof DwarvenCaverns)
		{	
			try
			{
				anims[0] = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/DwarfManCannonAnim1.png"));
				anims[1] = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/DwarfManCannonAnim2.png"));
				anims[2] = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/DwarfManCannonAnim3.png"));
				anims[3] = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/DwarfManCannonAnim4.png"));
				anims[4] = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/DwarfManCannonAnim5.png"));
				anims[5] = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/DwarfManCannonAnim6.png"));
				anims[6] = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/DwarfManCannonAnim7.png"));
				anims[7] = ImageIO.read(new File("src/org/world/dwarvencaverns/Interactables/DwarfManCannonAnim8.png"));
			}
			catch(IOException e) {System.out.println("IMAGE READING ERROR (ManCannon): " + e);}
		}
	}
	
	@Override
	public void interact()
	{
		//You can't actually interact with man cannons, so the processing will be done in Player.act()
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		//Both if statements check if the man cannon is on screen whatsoever
		if(getX() + block > World.getDrawX() && getX() < World.getDrawX() + GamePanel.screenX)
		{
			if(getY() < World.getDrawY() + GamePanel.screenY && getY() + block > World.getDrawY())
			{
				//Draws the image, animates, and draws the worldbox if possible
				g2d.drawImage(anims[curAnimation], (int)(getX() - World.getDrawX()), (int)(getY() - World.getDrawY()), null);
				elapsedFrames = (elapsedFrames > 8 * framesPerAnimation - 2) ? 0 : elapsedFrames + 1; curAnimation = (int)(elapsedFrames / framesPerAnimation);
				if(Startup.getRunner().worldboxesEnabled())
				{
					g2d.setColor(new Color(255, 255, 255, 50));
					g2d.fillRect((int)(getX() - World.getDrawX()), (int)(getY() - World.getDrawY()), (int)getWidth(), (int)getHeight());
				}
			}
		}
		else
		{
			elapsedFrames = 0; curAnimation = 0;
		}
	}
	
	public double getUpDelta() {return upDelta;}	//Returns change in velocity
}