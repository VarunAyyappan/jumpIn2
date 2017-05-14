/*
 * Varun Ayyappan
 * 05/12/2017
 * Stage.java
 * 
 * Called when player has lost to have them input their names.
 * This will then save their progress in a text file.
 * 
 * Testing:
 * Should Work: Type into text field, click on button
 * Shouldn't Work: Anything else
 */

import java.awt.Color;     // Classes for Color, Font, Graphics
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;    // Classes for ActionListener
import java.awt.event.ActionListener;

import java.io.File;                     // Classes for File IO
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;    // Classes for JButton, JPanel, JTextField
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveProgress extends JPanel implements ActionListener
{
    // Refs of other classes
    private JumpIn jiRef;
    private InfoPanel ipRef;

    private JTextField nameIn;
    private JButton submit;
    private boolean ifPlayerLost;
    private int attemptedProblems;
    private int problemsSolved;
    private int firstAttemptSolved;
    private String name;

    private PrintWriter ouputToFile; 
   
   // Initialize field variables and 
    public SaveProgress(JumpIn jiRefIn, InfoPanel ipRefIn, int sizeXIn, int sizeYIn)
    {
        jiRef = jiRefIn;
        ipRef = ipRefIn;

        nameIn = new JTextField();
        submit = new JButton("Submit Name and Save Progress");
        ifPlayerLost = false;
        attemptedProblems = 0;
        problemsSolved = 0;
        firstAttemptSolved = 0;
        name = "";

        add(nameIn);
		nameIn.addActionListener(this);
        add(submit);
		submit.addActionListener(this);

        setLayout(null);
        setBackground(Color.GREEN);
        nameIn.setLocation(10,400);
	    nameIn.setSize(300, 50);
        submit.setSize(400, 100);
		submit.setLocation((sizeXIn/2)-(submit.getWidth()/2), sizeYIn-(submit.getHeight()+50));
    }

    // Sets up printWriter to write to file
	// Prints error to terminal if it fails
	public void createPrintWriter()
	{
		String inFileName = "Progress.txt";
		File outFile = new File(inFileName);
		
		try
		{
			ouputToFile = new PrintWriter(new FileWriter(outFile, true));
		}
		catch(IOException e)
		{
		    System.err.println("ERROR: "+inFileName+" can't be acessed.");
            e.printStackTrace();
		}
	}

    // Prints to files via printf
    public void writeProgress()
    {
        ouputToFile.printf("%-15s %-23d %-19d %-21d\n", name, attemptedProblems, problemsSolved, firstAttemptSolved);
        ouputToFile.close();
    }

   // Graphics happens here
    public void paintComponent(Graphics g)
	{
        ifPlayerLost = ipRef.getIfLost();
        attemptedProblems = ipRef.getAttemptedProblems();
        problemsSolved = ipRef.getFinishedProblems();
        firstAttemptSolved = ipRef.getRightFirstTime();
        Font headerFont = new Font("SansSerif", Font.BOLD, 50);
        Font normFont = new Font("SansSerif", Font.BOLD, 25);

        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.setFont(headerFont);

        if(ifPlayerLost)
            g.drawString("Game Over...", 10, 50);
        else
            g.drawString("Congratulations!", 10, 50);

        g.setFont(normFont);
        g.drawString("Problems Received: " + attemptedProblems, 10, 140);
        g.drawString("Problems Solved: " + attemptedProblems, 10, 180);
        g.drawString("Problems Solved in First Attempt: " + attemptedProblems, 10, 220);
        g.drawString("Progress will be stored in Progress.txt.", 10, 300);
        g.drawString("Enter your name here:", nameIn.getX(), nameIn.getY());
    }

    // From Action Listener
    public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(submit)) 
        {
            name = nameIn.getText();
            createPrintWriter();
            writeProgress();
            jiRef.shift(1, 5);
        }
	}
}