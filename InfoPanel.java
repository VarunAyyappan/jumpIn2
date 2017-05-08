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

import java.awt.Color;     // Classes for Color, Graphics
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
	private JButton refresh;
	private Stage currentStage;
	private int trys;
	private int rightFirstTime;
	
	// Initialize field variables and change panel settings
	public InfoPanel(GamePanel gpRefIn, int sizeXIn, int sizeYIn) 
	{
		gpRef = gpRefIn;
		rp = new RefreshPanels(gpRef, this, 50);
		answers = new JTextField();
		refresh = new JButton("Refresh");
		currentStage = gpRef.getCurrentStageObj();
		
		add(refresh);
		refresh.addActionListener(this);
		add(answers);
		answers.addActionListener(this);
		
		setLayout(null);
		setLocation(0, (sizeYIn/3)*2);
		setSize(sizeXIn, sizeYIn/3);
		setBackground(Color.LIGHT_GRAY);
		refresh.setLocation(10, 10);
		refresh.setSize(100, 50);
		answers.setLocation(sizeXIn/3*2,100);
		answers.setSize(200, 50);
	}
	
	// Graphics happens here
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	// MouseListener methods
	// Trying to use this for focus switch, not working right now...
	public void mousePressed(MouseEvent e) { 
		gpRef.requestFocusInWindow();
	}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

	// From ActionListener
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(refresh))
		{
			gpRef.resetFrog();
		}
		if(e.getSource().equals(answers))
		{
			if(currentStage.checkAnswer(answers.getText()))
			{
				trys = 0;
				
			}
			else
			{
				answers.setText(answers.getText());
				trys++;
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
	
	public void actionPerformed(ActionEvent e)
	{
		gpRef.repaint();
		ipRef.repaint();
	}
}
