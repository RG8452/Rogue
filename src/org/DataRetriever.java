package org;
/**
 * RG
 * Equivalent of a Struct, serves solely to store data for retrieval
 */

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import java.awt.event.KeyEvent;

public class DataRetriever
{
	//All Variables, with self-explanatory names
	private static final Set<Integer> allKeys = new TreeSet<Integer>();
	private static int frameRate = 60;
	private static int frame = 0;

	//Individual functions based on key
	private static int right = KeyEvent.VK_RIGHT;
	private static int left = KeyEvent.VK_LEFT;
	private static int up = KeyEvent.VK_UP;
	private static int down = KeyEvent.VK_DOWN;
	private static int jump = KeyEvent.VK_SPACE;
	private static int skillOne = KeyEvent.VK_Z;
	private static int skillTwo = KeyEvent.VK_X;
	private static int skillThree = KeyEvent.VK_C;
	private static int skillFour = KeyEvent.VK_V;
	private static int interact = KeyEvent.VK_A;
	private static int pause = KeyEvent.VK_ESCAPE;

	public static ArrayList<Integer> getControlKeys()		//Returns an ArrayList containing every single key
	{
		ArrayList<Integer> keonIsStupid = new ArrayList<Integer>();

		keonIsStupid.add(right); keonIsStupid.add(left); keonIsStupid.add(up);
		keonIsStupid.add(down); keonIsStupid.add(jump);
		keonIsStupid.add(skillOne); keonIsStupid.add(skillTwo);
		keonIsStupid.add(skillThree); keonIsStupid.add(skillFour);
		keonIsStupid.add(interact); keonIsStupid.add(pause);

		return keonIsStupid;
	}

	public static void resetControls()			//Resets every control to its default
	{
		right = KeyEvent.VK_RIGHT;
		left = KeyEvent.VK_LEFT;
		up = KeyEvent.VK_UP;
		down = KeyEvent.VK_DOWN;
		jump = KeyEvent.VK_SPACE;
		skillOne = KeyEvent.VK_Z;
		skillTwo = KeyEvent.VK_X;
		skillThree = KeyEvent.VK_C;
		skillFour = KeyEvent.VK_V;
		interact = KeyEvent.VK_A;
		pause = KeyEvent.VK_ESCAPE;
	}

	//Key Getters
	public static int getRight() {return right;}
	public static int getLeft() {return left;}
	public static int getUp() {return up;}
	public static int getDown() {return down;}
	public static int getJump() {return jump;}
	public static int getSkillOne() {return skillOne;}
	public static int getSkillTwo() {return skillTwo;}
	public static int getSkillThree() {return skillThree;}
	public static int getSkillFour() {return skillFour;}
	public static int getInteract() {return interact;}
	public static int getPause() {return pause;}

	//Key Setters
	public static void setRight(int r) {right = r;}
	public static void setLeft(int l) {left = l;}
	public static void setUp(int u) {up = u;}
	public static void setDown(int d) {down = d;}
	public static void setJump(int j) {jump = j;}
	public static void setSkillOne(int one) {skillOne = one;}
	public static void setSkillTwo(int two) {skillTwo = two;}
	public static void setSkillThree(int three) {skillThree = three;}
	public static void setSkillFour(int four) {skillFour = four;}
	public static void setInteract(int i) {interact = i;}
	public static void setPause(int p) {pause = p;}

	//Key control variables
	public static void addKeyPressed(int e) {allKeys.add(e);}
	public static void removeKeyReleased(int e) {allKeys.remove(e);}
	public static Set<Integer> getAllKeys() {return allKeys;}

	//Time retrieval vars
	public static int getNanoTime() {return (int)(System.nanoTime());}
	public static int getMicroTime() {return (int)(System.nanoTime() / 1000);}
	public static int getMilliTime() {return (int)(System.nanoTime() / 1000000);}

	//Frame data
	public static int getFrameRate() {return frameRate;}
	public static int getFrame() {return frame;}
	public static void incrementFrame() {frame++;}
	public static void setFrame(int q) {frame = q;}
}