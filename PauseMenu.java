/*
 * Varun Ayyappan
 * 04/25/2017
 * Period 1
 * 
 * PauseMenu.java
 * 
 * The pause menu panel. Holds three buttons: first one goes back to game,
 * secound button goes to directions, third button goes to main menu.
 * 
 * Testing:
 * Should Work: Click on three buttons
 * Shouldn't Work: Anything else
 */

import java.awt.Color;     // Classes for Color, Font, Graphics
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;     // Classes for ActionListener
import java.awt.event.ActionListener;

import javax.swing.JButton;  // Classes for JButton and JPanel
import javax.swing.JPanel;

public class PauseMenu extends JPanel implements ActionListener
{
	
	private JumpIn jiRef;
	private JButton playButton;
	private JButton dirButton;
	private JButton quitButton;
	
	// Initialize field variables and change panel settings
	public PauseMenu(JumpIn jiRefIn) 
	{
		jiRef = jiRefIn;
		playButton = new JButton("Play Game");
		dirButton = new JButton("Directions");
		quitButton = new JButton("Quit");
		
		add(playButton);
		add(dirButton);
		add(quitButton);
		
		playButton.addActionListener(this);
		dirButton.addActionListener(this);
		quitButton.addActionListener(this);
		
		setBackground(Color.GREEN);
		setLayout(null);
		playButton.setLocation(10, 100);
		playButton.setSize(100, 50);
		dirButton.setLocation(10, 200);
		dirButton.setSize(100, 50);
		quitButton.setLocation(10, 300);
		quitButton.setSize(100, 50);
	}
	
	// Paint panel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		
		Font font = new Font("SansSerif", Font.BOLD, 50);
		g.setFont(font);
		
		g.drawString("Paused", 10, 50);
	}

	// From ActionListener
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(playButton))
			jiRef.shift(2, 4);
		else if(e.getSource().equals(dirButton))
			jiRef.shift(3, 4);
		else if(e.getSource().equals(quitButton))
			jiRef.shift(1, 4);
	}
	
}
