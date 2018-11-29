package org.world;
/*
 * RG
 * This is the class for a data type known as a QuadTree
 * This class will be use to store and check collisions in the maps
 * Source: https://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
 * Essentially stores Rectangles into recursively split Cartesian planes
 * Typically instantated by passing bounds of the screen
 * i.e. QuadTree q = new QuadTree(0, new Rectangle(0,0,screenX,screenY));
 * You could refresh it by clear()-ing and insert()-ing all objects every frame
 * You get the nearby collisions by writing a method somewhat like this:
 * List<Rectangle> returnObjects = new ArrayList<Rectangle>();
 * for(int i = 0; i < allObjects.size(); i++){
 * 	returnObjects.clear();
 * 	q.retrieve(returnObjects, objects.get(i));
 * 	for(int x: returnObjects)
 * 		run collision algorithm
 * }
 */

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuadTree
{
	private int MAX_OBJECTS = 10;
	private int MAX_LEVELS = 5;

	private int level;
	private List<Rectangle> objects;
	private Rectangle bounds;
	private QuadTree[] nodes;

	// Constructor
	public QuadTree(int pLevel, Rectangle pBounds)
	{
		level = pLevel;
		objects = new ArrayList<Rectangle>();
		bounds = pBounds;
		nodes = new QuadTree[4];
	}

	// Overloaded Constructor
	public QuadTree(int pLevel, Rectangle pBounds, int maxObs, int maxLvls)
	{
		level = pLevel;
		bounds = pBounds;
		nodes = new QuadTree[4];
		MAX_OBJECTS = maxObs;
		MAX_LEVELS = maxLvls;
	}

	// Clears the quadtree recursively
	public void clear()
	{
		objects.clear();

		for (int i = 0; i < nodes.length; i++)
		{
			if (nodes[i] != null)
			{
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	// Splits the node into 4 subnodes
	private void split()
	{
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();

		nodes[0] = new QuadTree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new QuadTree(level + 1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new QuadTree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new QuadTree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
	}

	/*
	 * Determine which node the obct belongs to. -1 means object cannot completely
	 * fit within a child node & is part of the parent node.
	 */
	private int getIndex(Rectangle pRect)
	{
		int index = -1;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

		// Object can completely fit within the top quadrants
		boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
		// Object can completely fit within the bottom quadrants
		boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

		// Object can completely fit within the left quadrants
		if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint)
		{
			if (topQuadrant) index = 1;
			else if (bottomQuadrant) index = 2;
		}

		// Object can fit completely within the right quadrants
		else if (pRect.getX() > verticalMidpoint)
		{
			if (topQuadrant) index = 0;
			else if (bottomQuadrant) index = 3;
		}

		return index;
	}

	private int[] getAllIndices(Rectangle pRect)
	{
		double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

		// Object can completely fit within the top quadrants
		boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
		// Object can completely fit within the bottom quadrants
		boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

		// Object can completely fit within the left quadrants
		if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint)
		{
			if (topQuadrant) return new int[] { 1 };
			else if (bottomQuadrant) return new int[] { 2 };
			else return new int[] { 1, 2 };
		}

		// Object can fit completely within the right quadrants
		else if (pRect.getX() > verticalMidpoint)
		{
			if (topQuadrant) return new int[] { 0 };
			else if (bottomQuadrant) return new int[] { 3 };
			else return new int[] { 0, 3 };
		}

		// If object can't fit in either horizontal side
		else
		{
			if (topQuadrant) return new int[] { 0, 1 };
			else if (bottomQuadrant) return new int[] { 2, 3 };
			else return new int[] { 0, 1, 2, 3 };
		}
	}

	/*
	 * Insert the object into the quadtree. If the node exceeds the capacity, it
	 * will split and add all objects to their corresponding nodes
	 */
	public void insert(Rectangle pRect)
	{
		if (nodes[0] != null)
		{
			int index = getIndex(pRect);
			if (index != -1)
			{
				nodes[index].insert(pRect);
				return;
			}
		}

		objects.add(pRect);

		if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS)
		{
			if (nodes[0] == null) split();

			int i = 0;
			while (i < objects.size())
			{
				int index = getIndex(objects.get(i));
				if (index != -1)
				{
					nodes[index].insert(objects.remove(i));
				}
				else i++;
			}
		}
	}

	// Return all objects using getAllIndices to to force child nodes to be included
	public List<Rectangle> retrieve(List<Rectangle> returnObjects, Rectangle pRect)
	{
		int[] indices = getAllIndices(pRect);
		for (int i : indices)
		{
			if (nodes[0] != null) nodes[i].retrieve(returnObjects, pRect);
		}

		// printLevelInfo(indices, pRect);
		returnObjects.addAll(objects);

		return returnObjects;
	}

	public List<Rectangle> retrieveAll(List<Rectangle> returnObjects)
	{
		for (int i = 0; i < 4; i++)
		{
			if (nodes[i] != null) nodes[i].retrieveAll(returnObjects);
		}

		returnObjects.addAll(objects);

		return returnObjects;
	}

	private void printLevelInfo(int[] indices, Rectangle pRect)
	{
		System.out.print("LEVEL: " + level + " OBS: " + objects.size());
		System.out.print("\tBOUNDS: x - " + (int) bounds.getX() + " y - " + (int) bounds.getY() + " w - " + (int) bounds.getWidth() + " h - " + (int) bounds.getHeight());
		System.out.print("\tPLAYER: x - " + (int) pRect.getX() + " y - " + (int) pRect.getY() + " w - " + (int) pRect.getWidth() + " h - " + (int) pRect.getHeight());
		System.out.print("\tINDICES: " + Arrays.toString(indices) + "\n");
	}
}
