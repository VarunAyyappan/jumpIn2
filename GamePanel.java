/*
 * Varun Ayyappan
 * 04/24/2017
 * Period 1
 * 
 * GamePanel.java
 * 
 * The class that sets up top portion of main game panel.
 * This is where the player will see the character in a stage and directly
 * control it. The player can also access the pause menu.
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
	
	// For the stages (which instantiate problem within them)
	private int boundary;
	private int bottom;
	private Stage[] stages;
	private int currentStage;
	private String input;
	private boolean needsToGoDown;
	
	// For acess to pause menu
	private JumpIn jiRef;
	
	// Initialize field variables, set up timer, and change panel settings
	public GamePanel(JumpIn jiRefIn, int sizeXIn, int sizeYIn) 
	{
		frogImgName = "22494_Flipped.png";
		getMyImage();
		frogX = 0;
		frogY = 305;
		vx = 10;
		vy = 10;
		
		boundary = sizeXIn;
		bottom = sizeYIn;
		
		createStages();
		
		input = "";
		
		jiRef = jiRefIn;
		
		setLocation(0, 0);
		setSize(sizeXIn, (sizeYIn/3)*2);
		setBackground(Color.CYAN);
		addKeyListener(this);
	}
	
	// Fill up stages array
	public void createStages()
	{
		boolean isFacingUp = false;
		stages = new Stage[40];

		for(int i=0;i<stages.length;i++)
		{
			if(i>=20 && i%2==0)
				isFacingUp = false;
			else
				isFacingUp = true;
			
			if(i<10)
				stages[i]= new Stage(this, 1, boundary, bottom, isFacingUp);
			else if(i<20)
				stages[i]= new Stage(this, 2, boundary, bottom, isFacingUp);
			else if(i<30)
				stages[i]= new Stage(this, 3, boundary, bottom, isFacingUp);
			else
				stages[i]= new Stage(this, 4, boundary, bottom, isFacingUp);
		}

		currentStage = 0;
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
		if(stages[currentStage].getDifLevel()==2)
		{
			frogX = 10;
			frogY = 400;
		}
		else
		{
			frogX = 0;	
			frogY = stages[currentStage].getOrigY();				
		}

		requestFocusInWindow();
	}
	
	// Various getter and setter methods
	public Stage getCurrentStageObj() 
	{
		return stages[currentStage];
	}

	public boolean getIfStageOver()
	{
		if(frogX+100>=boundary)
			return true;
		else
			return false;
	}

	public boolean getNeedsToGoDown()
	{
		return needsToGoDown;
	}

	public void setNeedsToGoDown(boolean needsToGoDownIn)
	{
		needsToGoDown = needsToGoDownIn;	
	} 

	public void setFrogX(int frogXIn)
	{
		frogX = frogXIn;
	}

	public void setFrogY(int frogYIn)
	{
		frogY = frogYIn;
	}

	// Shift to next stage based on number of problems solved on 1st attempt
	public void shiftStage(int rightFirstTimeIn, boolean isCorrect)
	{
		if(rightFirstTimeIn == 2 && stages[currentStage].getDifLevel()==4)
			gameOver();
		else if(rightFirstTimeIn>0 && rightFirstTimeIn==2 && isCorrect)
			currentStage = currentStage-(currentStage%10)+10;
		else
			currentStage++;

		resetFrog();
	}

	// Goes to SaveProgress panel by calling shift method in JumpIn
	public void gameOver()
	{
		jiRef.shift(5, 2);
	}

	// Graphics happens here
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		stages[currentStage].draw(g);

		g.drawImage(frogImg, frogX, frogY, frogX+95, frogY+65, 340, 0, 435, 65, this);

		stages[currentStage].getProblem().draw(g);
	}

	// KeyListener methods
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
		
			if((code==e.VK_A || code==e.VK_LEFT) && frogX>0)
			{
				frogX-=vx;

				// Frog shouldn't walk back and fall into pit
				if(stages[currentStage].getSolved() && frogX>stages[currentStage].getProblemAreaX1() && frogX<stages[currentStage].getProblemAreaX2())
					frogX = stages[currentStage].getProblemAreaX2();
				
				// If frog is on hill, is should move up/down
				if(stages[currentStage].getIfOnHill(frogX))
				{
					if(stages[currentStage].getIsGoingUp())
						frogY+=10;
					else
						frogY-=10;
				}
				else
					frogY = stages[currentStage].getOrigY();	
			}
			else if((code==e.VK_D || code==e.VK_RIGHT) && frogX+95<boundary)
			{
				// Initiate jump when problem is solved;
				if(stages[currentStage].getSolved() && frogX>stages[currentStage].getProblemAreaX1()-95 && frogX<=stages[currentStage].getProblemAreaX1()+85)
				{
					stages[currentStage].getProblem().drawJumpUp(frogX, frogY);
					needsToGoDown = true;
				}
				// If problem isn't' solved, player shouldn't be able to skip it.
				else if(stages[currentStage].getSolved() || frogX<=stages[currentStage].getProblemAreaX1()-95)
					frogX+=vx;

				// If frog is on hill, is should move up/down
				if(stages[currentStage].getIfOnHill(frogX))
				{
					if(stages[currentStage].getIsGoingUp())
						frogY-=10;
					else
						frogY+=10;
				}
			}
			else if(code==e.VK_P)
				jiRef.shift(4, 2);
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}