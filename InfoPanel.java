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
 * Should Work: Text input via JTextField, Click to shift focus to GamePanel
 * Shouldn't Work: Anything else
 */

import java.awt.Color;     // Classes for Color, Font, Graphics
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;    // Classes for ActionListener, MouseListener
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;    // Classes for JButton, JPanel, JTextField, Timer
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class InfoPanel extends JPanel implements MouseListener, ActionListener
{
	private GamePanel gpRef;
	private RefreshPanels rp;
	private JTextField answers;
	private Stage currentStage;
	private int difLevel;
	private int lives;
	private int trys;
	private int rightFirstTime;
	private int sizeX;
	private int dotRadius;
	private boolean mistakeMsg;
	private boolean praiseMsg;
	
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
		rightFirstTime = 0;
		sizeX = sizeXIn;
		dotRadius = 20;
		mistakeMsg = false;
		praiseMsg = false;

		add(answers);
		answers.addActionListener(this);

		setLayout(null);
		setLocation(0, (sizeYIn/3)*2);
		setSize(sizeX, sizeYIn/3);
		setBackground(Color.LIGHT_GRAY);
		answers.setLocation(sizeX/3*2,100);
		answers.setSize(300, 50);
	}

	public int getRightFirstTime()
	{
		return rightFirstTime;
	}

	// Graphics happens here
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Font normFont = new Font("SansSerif", Font.BOLD, 20);
		Font msgFont = new Font("SansSerif", Font.BOLD, 40);
		g.setFont(normFont);

		g.drawString("Problem Difficulty:", 10, 30);
		g.drawString(""+difLevel, 10, 50);
		g.drawString("Lives:", 250, 30);
		g.drawString(""+lives, 250, 50);
		g.drawString("Trys:", 350, 30);

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

		g.drawString("Find the equation of the parabola in", sizeX/3*2-15, 35);

		if(difLevel==1 || difLevel==2)
			g.drawString("y=a(x-h)^2+k form.", sizeX/3*2-15, 60);
		else if(difLevel==3 || difLevel==4)
			g.drawString("y=ax^2+bx+c form.", sizeX/3*2-15, 60);

		g.drawString("Round decimals to hundreath place.", sizeX/3*2-15, 85);

		g.setFont(msgFont);
		if(mistakeMsg){
			g.drawString("Try again. Press p and go to", 10, 100);
			g.drawString("directions is help is needed.", 10, 150);
		}
		else if(praiseMsg)
			g.drawString("Good Job!", 10, 100);
	}

	// MouseListener methods
	// Trying to use this for focus switch, not working right now...
	public void mousePressed(MouseEvent e) 
	{ 
		gpRef.requestFocusInWindow();
	}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

	// From ActionListener
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(answers))
		{
			if(currentStage.checkAnswer(answers.getText()))
			{
				if(trys==0)
					rightFirstTime++;

				trys = 0;
				gpRef.setIsSolved(true);
				praiseMsg = true;
				mistakeMsg = false;
			}
			else
			{
				answers.setText(answers.getText());
				trys++;
				praiseMsg = false;
				mistakeMsg = true;

				if(trys==3)
				{
					lives--;
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
	}
}