/*
 * Varun Ayyappan
 * 04/24/2017
 * Period 1
 * 
 * DirectionsPanel.java
 * 
 * The class that sets up and holds the direction panel.
 * 
 * Testing:
 * Should Work: Click on the button
 * Shouldn't Work: Anything else
 */

import java.awt.Color;       // Classes for Color, Font, Graphics
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;     // Classes for ActionListener
import java.awt.event.ActionListener;

import java.io.File;                     // Classes for File IO
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;     // Classes for JButton, JPanel, JTextArea
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DirectionsPanel extends JPanel implements ActionListener
{
	// For the back button
	private JumpIn jiRef;
	private JButton back;
	private int source;
	
	// To receive instructions from text file
	private Scanner inputFromFile;
	private boolean ifScanFailed;
	
	// To display directions
	private JTextArea directions;

	//Initialize field variables and 
	public DirectionsPanel(JumpIn jiRefIn, int sizeXIn, int sizeYIn)
	{
		jiRef = jiRefIn;
		back = new JButton("Back");
		source = 0;
		ifScanFailed = false;
		directions = new JTextArea();
		
		add(back);
		add(directions);
		back.addActionListener(this);
		
		setBackground(Color.GREEN);
		setLayout(null);
		back.setSize(400, 100);
		back.setLocation((sizeXIn/2)-(back.getWidth()/2), sizeYIn-(back.getHeight()+50));
		directions.setSize(sizeXIn-200, sizeYIn-300);
		directions.setLocation(10, 120);
		directions.setEditable(false);
		
		createScanner();
		fillDirections();
	}
	
	// Sets up scanner to read file with directions,
	// Sets boolean that will put error on GUI if it fails
	public void createScanner()
	{
		String inFileName = "Directions.txt";
		File inFile = new File(inFileName);
		
		try
		{
			inputFromFile = new Scanner(inFile);
		}
		catch(FileNotFoundException e)
		{
			ifScanFailed = true;
		}
	}
	
	public void fillDirections()
	{
		Font dirFont = new Font("SansSerif", Font.BOLD, 15);
		directions.setFont(dirFont);
		directions.setBackground(Color.GREEN);
		
		if(ifScanFailed)
		{
			directions.append("ERROR: Directions file not found.\n");
			directions.append("Directions cannot be displayed as a result...");
		}
		else
		{	
			int currentY = 150;
			
			while(inputFromFile.hasNext())
			{
				directions.append(inputFromFile.nextLine()+"\n");
				currentY+=50;
			}
		}
	}
	
	// Setter method to set which panel made this panel appear
	public void setSource(int sourceIn)
	{
		source = sourceIn;
	}
	
	// Paints panel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Font headerFont = new Font("SansSerif", Font.BOLD, 50);
		g.setFont(headerFont);
		g.setColor(Color.BLUE);
		g.drawString("Directions: ", 0, 50);
	}
	
	// From ActionListener
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(back))
			jiRef.shift(source, 3);
	}
	
}