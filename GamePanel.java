/*
 * Varun Ayyappan
 * 04/24/2017
 * Period 1
 * 
 * GamePanel.java
 * 
 * The class that sets up top portion of main game panel.
 * This is where the player will see the character in a stage and directly
 * control it. The player can also acess the pause menu.
 * 
 * Testing:
 * Should Work: a, d, and p keys
 * Shouldn't Work: Anything else
 */

import java.awt.Color;     // Classes for Color, Graphics, Image
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.KeyEvent;    // Classes for KeyListener
import java.awt.event.KeyListener;


import java.io.File;         // Classes for getting image
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;   // Classes for JPanel, Timer

public class GamePanel extends JPanel implements KeyListener
{
	// For frog image
	private Image frogImg;
	private String frogImgName;
	private int frogX, frogY, vx, vy;
	
	// For the stage and problem
	private int boundary;
	private Stage[] stages;
	private int currentStage;
	private boolean isSolved;
	private boolean gameOver;
	private String input;
	
	// For acess to pause menu
	private JumpIn jiRef;
	
	// Initialize field variables, set up timer, and change panel settings
	public GamePanel(JumpIn jiRefIn, int sizeXIn, int sizeYIn) 
	{
		frogImgName = "22494_Flipped.png";
		getMyImage();
		frogX = 0;
		frogY = 300;
		vx = 10;
		vy = 10;
		
		boundary = sizeXIn;
		stages = new Stage[40];
		isSolved = false;
		gameOver = false;
		
		for(int i=0;i<stages.length;i++)
		{
			if(i<10)
				stages[i]= new Stage(this, 1, true);
			else if(i<20)
				stages[i]= new Stage(this, 2, true);
			else if(i<30)
				stages[i]= new Stage(this, 3, true);
			else
				stages[i]= new Stage(this, 4, true);
		}

		currentStage = 0;
		input = "";
		
		jiRef = jiRefIn;
		
		setLocation(0, 0);
		setSize(sizeXIn, (sizeYIn/3)*2);
		setBackground(Color.CYAN);
		addKeyListener(this);
	}
	
	// Gets image, sends error message it it failed
	public void getMyImage()
	{
		try
		{
			frogImg = ImageIO.read(new File(frogImgName));
		}
		catch(IOException e)
		{
			System.err.println("\n\nERROR: "+frogImgName+" can't be found.\n\n");
			e.printStackTrace();
		}
	}
	
	// Resets Frogs position to the beginning position
	public void resetFrog() 
	{
		frogX = 0;
		frogY = 300;
		requestFocusInWindow();
	}
	
	// Some getter and setter methods
	public void setIsSolved(boolean isSolvedIn)
	{
		isSolved = isSolvedIn;
	}

	public Stage getCurrentStageObj() 
	{
		return stages[currentStage];
	}

	public void setCurrentStage(int stageNum)
	{
		currentStage = stageNum;
		resetFrog();
	}

	// Graphics happens here
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(frogImg, frogX, frogY, frogX+95, frogY+65, 340, 0, 435, 65, this);

		g.setColor(Color.GREEN);
		g.fillRect(0, 365, boundary, 300);
		
		g.setColor(Color.BLACK);
		g.fillRect(300, 365, 120, 300);
		
		stages[currentStage].draw(g);
	}
	
	// KeyListener methods
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
		
		if(!gameOver)
		{
			if((code==e.VK_A || code==e.VK_UP) && frogX>0)
			frogX-=vx;
			else if((code==e.VK_D || code==e.VK_DOWN) && frogX+95<boundary)
			{
				if(isSolved)
					frogX+=vx;
				else if(!isSolved && frogX<=200)
					frogX+=vx;
			}
			else if(code==e.VK_P)
				jiRef.shift(4, 2);
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}