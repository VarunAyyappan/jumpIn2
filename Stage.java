/*
 * Varun Ayyappan
 * 04/24/2016
 * Stage.java
 * 
 * Creates and draws the stage for the character to go through. Also creates parabola problems
 * for player to solve.
 * 
 * No testing here.
 */

import java.awt.Color;    // Classes for Color, Graphics
import java.awt.Graphics;

public class Stage 
{	
	private int difLevel;
	private int point1X, point1Y;
	private int point2X, point2Y;
	private int point3X, point3Y;
	private double a;
	private int dotRadius;
	private String answer;
	private boolean solved;
	private GamePanel gpRef;

	// Initialize field vars
	public Stage(GamePanel gpRefIn, int difLevelIn)
	{
		difLevel = difLevelIn;
		point1X = 0;
		point1Y = 0;
		point2X = 0;
		point2Y = 0;
		point3X = 0;
		point3Y = 0;
		answer = "";
		dotRadius = 10;
		solved = false;
		gpRef = gpRefIn;

		createPoints();
		findSolution();
	}

	// Create basis of problems
	public void createPoints()
	{
		if(difLevel==1)
		{
			point1X = ((int)(Math.random()*20))%2;
			point1Y = 0;
			point3X = ((int)(Math.random()*20))%2+point1X;
			point3Y = 0;
			point2X = (point3X-pointX)/2;
			point2Y = ((int)(Math.random()*20))%2;
		}
		else if(difLevel == 2)
		{

		}
		else if(difLevel == 3)
		{

		}
	}

	// Find the solution to problem
	public void findSolution()
	{	
		if(difLevel==1)
		{
			a = ((double)point1Y-point2Y)/(((double)point1X-point2X)*((double)point1X-point2X));
			string = "y="+a+"(x-"+point2X +")+"+"^2"+"+"+point2Y;
		}
		else if(difLevel == 2)
		{

		}
		else if(difLevel == 3)
		{

		}
	}

	// Check answer with string passed in
	public boolean checkAnswer(String answerIn)
	{
		answerIn = answerIn.trim();
		String withAIn = answerIn.substring(answerIn.indexOf("="), answerIn.indexOf("("));
		String withHIn = answerIn.substring(answerIn.indexOf("-"), answerIn.indexOf(")"));
		String withKIn = answerIn.substring(answerIn.indexOf("+"), answerIn.length());

		if(a==Integer.parseInt(withAIn) && point2X==Integer.parseInt(withHIn) && point2Y==Integer.parseInt(withKIn))
			return true;
		else
			return false;
	}

	// Draws stage on game panel
	public void draw(Graphics g) 
	{
		if(!solved)
		{
			g.setColor(Color.YELLOW);
			g.fillOval(290, 355, dotRadius, dotRadius);
			g.drawString("(10,0)", 300, 345);
			g.fillOval(350, 300, dotRadius, dotRadius);
			g.drawString("(5,5)", 360, 290);
			g.fillOval(420, 355, dotRadius, dotRadius);
			g.drawString("(15,0)", 420, 345);
			
			solved = true;
		}
		else
		{
			g.setColor(Color.YELLOW);
			g.drawArc(300, 310, 120, 100, 0, 180);
		}

	}

}