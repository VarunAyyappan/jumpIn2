/*
 * Varun Ayyappan
 * 04/24/2017
 * Period 1
 * 
 * InfoPanel.java
 * 
 * The class that sets up the bottom portion of the main game panel.
 * This will hold the variety of info the player needs in game and
 * the input for the math problems.
 * 
 * Testing:
 * Should Work: Text input via JTextField
 * Shouldn't Work: Anything else
 */

import java.awt.Color;     // Classes for Color, Font, Graphics
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;    // Classes for ActionListener
import java.awt.event.ActionListener;

import javax.swing.JButton;    // Classes for JButton, JPanel, JTextField, Timer
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class InfoPanel extends JPanel implements ActionListener
{
	private GamePanel gpRef;
	private RefreshPanels rp;
	private JTextField answers;
	private Stage currentStage;
	private int difLevel;
	private int lives;
	private int trys;
	private int attemptedProblems;
	private int finishedProblems;
	private int rightFirstTime;
	private int sizeX;
	private int dotRadius;
	private boolean mistakeMsg;
	private boolean praiseMsg;
	private boolean lossLife;
	
	// Initialize field variables and change panel settings
	public InfoPanel(GamePanel gpRefIn, int sizeXIn, int sizeYIn) 
	{
		gpRef = gpRefIn;
		rp = new RefreshPanels(gpRef, this, 50);
		answers = new JTextField();
		currentStage = gpRef.getCurrentStageObj();
		difLevel = currentStage.getDifLevel();
		lives = 3;
		trys = 0;
		attemptedProblems = 0;
		finishedProblems = 0;
		rightFirstTime = 0;
		sizeX = sizeXIn;
		dotRadius = 20;
		mistakeMsg = false;
		praiseMsg = false;
		lossLife = false;

		add(answers);
		answers.addActionListener(this);

		setLayout(null);
		setLocation(0, (sizeYIn/3)*2);
		setSize(sizeX, sizeYIn/3);
		setBackground(Color.LIGHT_GRAY);
		answers.setLocation(sizeX/3*2,100);
		answers.setSize(300, 50);
	}
	 
	 // Some getter methods
	public int getRightFirstTime()
	{
		return rightFirstTime;
	}

	public int getAttemptedProblems()
	{
		return attemptedProblems;
	}

	public int getFinishedProblems()
	{
		return finishedProblems;
	}

	public boolean getIfLost()
	{
		// uses the amount of lives remaing to check if player lost
		if(lives == -1)
			return true;
		else
			return false;
	}

	// Shifts the problem and the stage
	public void shiftToNextStage() 
	{
		gpRef.shiftStage(rightFirstTime, true);
		currentStage = gpRef.getCurrentStageObj();
		difLevel = currentStage.getDifLevel();
		answers.setText("");
	}

	// Graphics happens here
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Font normFont = new Font("SansSerif", Font.BOLD, 20);
		Font msgFont = new Font("SansSerif", Font.BOLD, 40);
		g.setFont(normFont);

		// Problem difficulty and number of lives remaining
		g.drawString("Problem Difficulty:", 10, 30);
		g.drawString(""+difLevel, 10, 50);
		g.drawString("Lives:", 250, 30);
		g.drawString(""+lives, 250, 50);
		
		// Uses filled and unfilled circles to show amount of tries remaining
		g.drawString("Tries:", 350, 30);

		if(trys==0)
		{
			g.drawOval(350, 50, dotRadius, dotRadius);
			g.drawOval(350+(3*dotRadius/2), 50, dotRadius, dotRadius);
			g.drawOval(350+(3*dotRadius), 50, dotRadius, dotRadius);
		}
		else if(trys==1)
		{
			g.fillOval(350, 50, dotRadius, dotRadius);
			g.drawOval(350+(3*dotRadius/2), 50, dotRadius, dotRadius);
			g.drawOval(350+(3*dotRadius), 50, dotRadius, dotRadius);
		}
		else if(trys==2)
		{
			g.fillOval(350, 50, 20, 20);
			g.fillOval(350+(3*dotRadius/2), 50, 20, 20);
			g.drawOval(350+(3*dotRadius), 50, 20, 20);
		}
		else if(trys==3)
		{
			g.fillOval(350, 50, 20, 20);
			g.fillOval(350+(3*dotRadius/2), 50, 20, 20);
			g.fillOval(350+(3*dotRadius), 50, 20, 20);
		}

		// Number of Problems left ing current stage
		g.drawString("Problems Left Untill next Stage:", 450, 30);
		g.drawString(""+(2-rightFirstTime%2), 450, 50);

		// Prompt input
		g.drawString("Find the equation of the parabola in", sizeX/3*2-5, 35);

		if(difLevel==1 || difLevel==2)
			g.drawString("y=a(x-h)^2+k form.", sizeX/3*2-5, 60);
		else if(difLevel==3 || difLevel==4)
			g.drawString("y=ax^2+bx+c form.", sizeX/3*2-5, 60);

		g.drawString("Round decimals to hundreth place.", sizeX/3*2-5, 85);

		// Rest for printing messages relavent to player
		g.setFont(msgFont);

		if(mistakeMsg)
		{
			g.drawString("Try again. Press p and go to", 10, 110);
			g.drawString("directions if help is needed.", 10, 160);
		}
		else if(praiseMsg)
			g.drawString("Good job! Keep up the Good Work!", 10, 110);
		else if(lossLife)
		{
			g.drawString("Sorry, the answer was", 10, 110);
			g.drawString(currentStage.getProblem().getAnswer()+"...", 10, 160);
		}
		else
			g.drawString("Messages will appear here!", 10, 150);
	}

	// From ActionListener
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(answers))
		{
			if(currentStage.getProblem().checkAnswer(answers.getText()))
			{
				if(trys==0)
					rightFirstTime++;
				
				finishedProblems++;
				attemptedProblems++;
				trys = 0;
				mistakeMsg = false;
				lossLife = false;
				praiseMsg = true;
			}
			else
			{
				trys++;
				lossLife = false;
				praiseMsg = false;
				mistakeMsg = true;

				if(trys==3)
				{
					lives--;
					attemptedProblems++;
					mistakeMsg = false;
					lossLife = true;
					trys=0;

					if(lives==-1)
						gpRef.gameOver();
					else
					{
						gpRef.shiftStage(rightFirstTime, false);
						currentStage = gpRef.getCurrentStageObj();
						difLevel = currentStage.getDifLevel();
					}
				}
			}

			gpRef.requestFocusInWindow();
		}
	}

}

// Timer class to repaint periodically
class RefreshPanels implements ActionListener
{
	// Reference of panels to refreash
	private GamePanel gpRef;
	private InfoPanel ipRef;

	private Timer timer;

	// Initialize field variables
	public RefreshPanels(GamePanel gpRefIn, InfoPanel ipRefIn, int timeSlice)
	{
		gpRef = gpRefIn;
		ipRef = ipRefIn;
		timer = new Timer(timeSlice, this);
		timer.setInitialDelay(200);
		timer.start();
		timer.addActionListener(this);
	}
	
	// Called periodicaly by timer
	public void actionPerformed(ActionEvent e)
	{
		gpRef.repaint();
		ipRef.repaint();

		// If the player reached end of level go to next one
		if(gpRef.getIfStageOver())
			ipRef.shiftToNextStage();

		// If jump animation needs to continue
		if(gpRef.getNeedsToGoDown())
		{
			// This will make the program wait 500 milliseconds
			long sleeptime = 500;
			long expectedtime = System.currentTimeMillis() + sleeptime;
			while(System.currentTimeMillis() < expectedtime) {}
   			expectedtime += sleeptime;
			
			gpRef.getCurrentStageObj().getProblem().drawJumpDown(gpRef.getCurrentStageObj().getOrigY());
			gpRef.setNeedsToGoDown(false);
			gpRef.requestFocusInWindow();
		}
	}
}