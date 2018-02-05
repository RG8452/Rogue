package org.panels;
/*
 * RG
 * This is the panel which will be responsible for handling the Dev console
 * It will open when the tilde is hit while in pause panel and allow commands to be passed
 * Its function is to allow the rapid change of variables eg god mode, flight, etc. while in game
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.Startup;

public class DevPanel extends JPanel
{
	private ArrayList<String> commandList; // List of typed commands, for visual aid
	private String curCommand = ""; // Current command getting typed, ends on Enter
	private PausePanel prev; // Panel to swap back to when tilde is pressed
	private boolean curCycle;
	private int elapsedFrames, cursorPos, maxCommands = 14; // Cursor data
	private static int framesPerCycle = 10;

	// Constructor, does exactly as all panels do with instantiation
	public DevPanel(PausePanel p)
	{
		DevHandler hax = new DevHandler();
		addKeyListener(hax);
		setFocusable(true);
		prev = p;
		commandList = new ArrayList<>();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); // Repaints everything under the third alpha tier
		prev.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g; // Fills in a white alpha rectangle for typing
		g2d.setColor(new Color(240, 240, 240, 150));
		g2d.fillRect(0, (int) (GamePanel.screenY * .66), GamePanel.screenX, 1000);

		g2d.setColor(Color.black); // Fills in cursor and all commands
		g2d.setFont(new Font("Bodona MT Condensed", Font.PLAIN, 18));
		if (curCycle) g2d.fillRect(cursorPos, GamePanel.screenY - 20, 15, 5);
		for (int sub = 0; sub < commandList.size(); sub++)
			g2d.drawString(commandList.get(sub), 25, GamePanel.screenY - 20 * (sub + 2));

		g2d.drawString(curCommand, 25, GamePanel.screenY - 20);

		cursorPos = g2d.getFontMetrics().stringWidth(curCommand) + 25; // Do cursor animation and stuff
		elapsedFrames = (elapsedFrames > 2 * framesPerCycle - 1) ? 0 : elapsedFrames + 1;
		curCycle = elapsedFrames < 3;
	}

	// Private class for key handling and stuff
	private class DevHandler implements KeyListener
	{
		// KeyListener methods
		public void keyPressed(KeyEvent e) // Pressed is a method that must exist for keys that aren't typing eg enter and backspace
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER) // If enter
			{
				if (curCommand.length() != 0) // If non-empty string, add it to the list, add the response to the list, and empty current string
				{
					commandList.add(0, curCommand);
					if (commandList.size() > maxCommands) commandList.remove(commandList.size() - 1);

					commandList.add(0, Startup.getRunner().parseCommand(curCommand.toLowerCase()));
					if (commandList.size() > maxCommands) commandList.remove(commandList.size() - 1);

					curCommand = "";
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) // Remove one character if backspace
			{
				if (curCommand.length() != 0) curCommand = curCommand.substring(0, curCommand.length() - 1);
			}
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				Startup.getGUI().swapPanels(prev);
				return;
			}
		}

		public void keyReleased(KeyEvent e) // Releasing enter or backspace removes the typed character
		{
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if (curCommand.length() != 0) curCommand = curCommand.substring(0, curCommand.length() - 1);
			}
		}

		public void keyTyped(KeyEvent e) // Types latest key, and exits panel on tilde
		{
			if (e.getKeyChar() == '`' || e.getKeyChar() == '~')
			{
				Startup.getGUI().swapPanels(prev);
				return;
			}
			else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				System.out.println("STOP"); // No idea why this doesn't work
			}
			else curCommand += e.getKeyChar(); // Concatenate stuff
		}
	}
}
