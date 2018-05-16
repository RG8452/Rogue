package org.panels;
/**
 * RG
 * This is a basic class which will just allow the user to put sections of responsivity on a panel
 * Essentially its a class to make drawing and checking buttons much easier
 * Saves time in the listeners and makes the bound-checking much easier
 */

import java.awt.Color;
import java.awt.Graphics;

public class Button
{
	private int x, y, width, height, borderWidth; // locations
	private String name; // name
	private Color col, outlineCol; // colors

	// LOTS of overloaded constructors, just to establish all the various
	// variables if needed
	public Button(int h, int k, int w, int t)
	{
		x = h;
		y = k;
		width = w;
		height = t;
		borderWidth = -1;
	}

	public Button(int h, int k, int w, int t, String nawa)
	{
		x = h;
		y = k;
		width = w;
		height = t;
		name = nawa;
		borderWidth = -1;
	}

	public Button(int h, int k, int w, int t, String nawa, Color fill)
	{
		x = h;
		y = k;
		width = w;
		height = t;
		name = nawa;
		col = fill;
		borderWidth = -1;
	}

	public Button(int h, int k, int w, int t, String nawa, Color fill, Color outline)
	{
		x = h;
		y = k;
		width = w;
		height = t;
		name = nawa;
		col = fill;
		outlineCol = outline;
		borderWidth = -1;
	}

	public Button(int h, int k, int w, int t, String nawa, Color fill, Color outline, int bWidth)
	{
		x = h;
		y = k;
		width = w;
		height = t;
		name = nawa;
		col = fill;
		outlineCol = outline;
		borderWidth = bWidth;
	}

	// Boolean which, when given (ideally) location clicked, checks to see if that click is inbounds
	public boolean inBounds(int checkX, int checkY)
	{
		return (checkX > x && checkX < x + width && checkY > y && checkY < y + height);
	}

	// Draws the button itself on the screen using color given
	public void drawButton(Graphics g)
	{
		if (this.col != null) g.setColor(this.col);
		g.fillRect(x, y, width, height);
	}

	// Draws a border of width borderWidth around the button; if borderWidth is unspecified,it's ten
	public void drawButtonBorder(Graphics g)
	{
		if (this.outlineCol != null) g.setColor(this.outlineCol);
		if (this.borderWidth != -1)
		{
			g.fillRect(x - borderWidth, y - borderWidth, width + 2 * borderWidth, borderWidth); // TL to TR
			g.fillRect(x - borderWidth, y, borderWidth, height + borderWidth); // TL to BL
			g.fillRect(x + width, y, borderWidth, height + borderWidth); // TR to BR
			g.fillRect(x, y + height, width, borderWidth); // BL to BR
		}
		else
		{
			g.fillRect(x - 10, y - 10, width + 20, 10); // TL to TR
			g.fillRect(x - 10, y, 10, height + 10); // TL to BL
			g.fillRect(x + width, y, 10, height + 10); // TR to BR
			g.fillRect(x, y + height, width, 10); // BL to BR
		}
	}

	// Overloaded border method to draw border with arg bWidth width
	public void drawButtonBorder(Graphics g, int bWidth)
	{
		if (this.outlineCol != null) g.setColor(this.outlineCol);
		g.fillRect(x - bWidth, y - bWidth, width + 2 * bWidth, bWidth); // TL to
																		// TR
		g.fillRect(x - bWidth, y, bWidth, height + bWidth); // TL to BL
		g.fillRect(x + width, y, bWidth, height + bWidth); // TR to BR
		g.fillRect(x, y + height, width, bWidth); // BL to BR
	}

	// Method to draw a string onto the button, auto-centers it, BL at 2/3 height
	public void drawMessage(Graphics g, String message)
	{
		int messageWidth = g.getFontMetrics().stringWidth(message); // Get width of message
		int mid = x + (int) (width / 2); // Get midpoint of button
		int xLoc = mid - (int) (messageWidth / 2); // Get location where string begins

		g.drawString(message, xLoc, y + (int) (height * 2 / 3));
	}

	//@formatter:off
	// Series of getter methods for all of the priavte variables
	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public String getName() {return name;}
	public Color getCol() {return col;}
	public Color getOutlineCol() {return outlineCol;}

	// Series of setter methods for private vars
	public void setX(int newX) {x = newX;}
	public void setY(int newY) {y = newY;}
	public void setWidth(int newWidth) {width = newWidth;}
	public void setHeight(int newHeight) {height = newHeight;}
	public void setName(String newName) {name = newName;}
	public void setCol(Color newCol) {col = newCol;}
	public void setOutlineCol(Color newOutCol) {outlineCol = newOutCol;}
	//@formatter:on
}
