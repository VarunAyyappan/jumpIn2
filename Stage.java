/*
 * Varun Ayyappan
 * 05/14/2016
 * Stage.java
 * 
 * Creates parabola Stages for player to go through.
 * Instatiates Problem.java for problem.
 * 
 * No testing here.
 */

import java.awt.Color;    // Classes for Color, Graphics
import java.awt.Font;
import java.awt.Graphics;

import java.text.DecimalFormat;   // Class for DecimalFormat

public class Stage 
{	
	// For the Stage
	private Problem problem;
    private int difLevel;
	private int boundary;
	private int[] triangleXPoints;
	private int[] triangleYPoints;
	private boolean isGoingUp;    // If triangle is for uphill or downhill section
	
	// For checking user input
	private GamePanel gpRef;

	// Initialize field vars
	public Stage(GamePanel gpRefIn, int difLevelIn, int boundaryIn, boolean isUpIn)
	{
        problem = new Problem(gpRefIn, this ,difLevelIn, isUpIn);
        difLevel = difLevelIn;
		boundary = boundaryIn;
		
        gpRef = gpRefIn;

		createTrianglePoints(boundary);
    }

    // Various getter methods
    public Problem getProblem()
    {
        return problem;
    }

	public int getOrigY()
	{
		return 305;
	}

	public int getDifLevel()
	{
		return difLevel;
	}

	public boolean getSolved()
	{
		return problem.getSolved();
	}

	public boolean getIfOnHill(int frogXIn)
	{
		if(triangleXPoints!=null && frogXIn>=triangleXPoints[0] && frogXIn<=triangleXPoints[2])
			return true;
		else
			return false;
	}

	public boolean getIsGoingUp()
	{
		return isGoingUp;
	}

	// Create triangle points for uphill/downhill section of stage
	public void createTrianglePoints(int boundaryIn)
	{
		if(difLevel ==1)
		{
			triangleXPoints = new int[3];
			triangleYPoints = new int[3];
			
			triangleXPoints[0] = boundaryIn-265;
			triangleXPoints[1] = boundaryIn;
			triangleXPoints[2] = boundaryIn;

			triangleYPoints[0] = 365;
			triangleYPoints[1] = 100;
			triangleYPoints[2] = 365;

			isGoingUp = true;
		}
		else if(difLevel == 2)
		{
			triangleXPoints = new int[3];
			triangleYPoints = new int[3];

			isGoingUp = true;
		}
		else if(difLevel == 4)
		{
			triangleXPoints = new int[3];
			triangleYPoints = new int[3];

			isGoingUp = false;
		}
	}

    // Draws Stage
    public void draw(Graphics g)
    {
        g.setColor(Color.GREEN);
	    g.fillRect(0, 365, boundary, 300);

        if(difLevel==1)
		{
			g.fillPolygon(triangleXPoints, triangleYPoints, 3);
			g.setColor(Color.BLACK);
			g.fillRect(300, 365, 120, 300);
		}
		else if(difLevel==2)
		{

		}
		else if(difLevel==3)
		{
			
		}
		else if(difLevel==4)
		{
			// level 4 stage here
		}
    }

}