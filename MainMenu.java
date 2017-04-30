/*
 * Varun Ayyappan
 * 04/24/2017
 * Period 1
 * 
 * MainMenu.java
 * 
 * The class that holds and sets up the main menu.
 * 
 * Testing:
 * Should Work: Click on two buttons
 * Shouldn't Work: Anything else
 */

import java.awt.Color;     // Classes for Color, Font, Graphics
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;     // Classes for ActionListener
import java.awt.event.ActionListener;

import javax.swing.JButton;  // Classes for JButton and JPanel
import javax.swing.JPanel;

public class MainMenu extends JPanel implements ActionListener
{
	private JumpIn jiRef;
	private JButton playButton;
	private JButton dirButton;
	
	// Initialize field variables and change panel settings
	public MainMenu(JumpIn jiRefIn) 
	{
		jiRef = jiRefIn;
		playButton = new JButton("Play Game");
		dirButton = new JButton("Directions");
		
		add(playButton);
		add(dirButton);
		
		playButton.addActionListener(this);
		dirButton.addActionListener(this);
		
		setBackground(Color.GREEN);
		setLayout(null);
		playButton.setLocation(10, 100);
		playButton.setSize(100, 50);
		dirButton.setLocation(10, 200);
		dirButton.setSize(100, 50);
	}
	
	// Paint panel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		
		Font font = new Font("SansSerif", Font.BOLD, 50);
		g.setFont(font);
		
		g.drawString("Jump In!", 10, 50);
	}

	// From ActionListener
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(playButton))
			jiRef.shift(2, 1);
		else if(e.getSource().equals(dirButton))
			jiRef.shift(3, 1);
	}
}
