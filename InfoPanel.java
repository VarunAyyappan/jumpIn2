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

import java.awt.Color;     // Classes for Color, Graphics
import java.awt.Graphics;

import java.awt.event.ActionEvent;    // Classes for ActionListener, MouseListener
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;    // Classes for JButton, JPanel
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InfoPanel extends JPanel implements MouseListener, ActionListener 
{
	private GamePanel gpRef;
	private JTextField answers;
	private JButton refresh;
	
	// Initialize field variables and change panel settings
	public InfoPanel(GamePanel gpRefIn, int sizeXIn, int sizeYIn) 
	{
		gpRef = gpRefIn;
		answers = new JTextField();
		refresh = new JButton("Refresh");
		
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
		answers.setLocation(sizeXIn/3*2,20);
		answers.setSize(200, 50);
	}
	
	// Paints panel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	// MouseListener methods
	// Trying to use this for focus switch, not working right now...
	public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

	// From ActionListener
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(refresh))
			gpRef.resetFrog();
		if(e.getSource().equals(answers)){
			// Save answer
			// Send to Stage.java to evaluate
			gpRef.requestFocusInWindow();
		}
	}

}
