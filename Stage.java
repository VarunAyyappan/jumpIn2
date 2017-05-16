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
	private int bottom;
	private int origY;
	private int problemAreaX1, problemAreaX2;
	private int[] triangleXPoints, triangleYPoints;
	private boolean isGoingUp;    // If triangle is for uphill or downhill section
	private boolean isUp;
	private GamePanel gpRef;

	// Initialize field vars
	public Stage(GamePanel gpRefIn, int difLevelIn, int boundaryIn, int bottomIn, boolean isUpIn)
	{
        difLevel = difLevelIn;
		boundary = boundaryIn;
		bottom = bottomIn;
		problemAreaX1 = 0;
		problemAreaX2 = 0;
		origY = 0;
        gpRef = gpRefIn;
		isGoingUp = false;
		isUp = isUpIn;
		
		createPoints(boundary);
		
		problem = new Problem(gpRefIn, this ,difLevelIn, problemAreaX1, problemAreaX2, isUp);
    }

    // Various getter methods
    public Problem getProblem()
    {
        return problem;
    }

	public int getOrigY()
	{
		return origY;
	}

	public int getProblemAreaX1()
	{
		return problemAreaX1;
	}

	public int getProblemAreaX2()
	{
		return problemAreaX2;
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

	// Create various points for stage
	public void createPoints(int boundaryIn)
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

			problemAreaX1 = 300;
			problemAreaX2 = 500;

			origY = 305;
		}
		else if(difLevel == 2)
		{
			triangleXPoints = new int[3];
			triangleYPoints = new int[3];
			
			triangleXPoints[0] = 0;
			triangleXPoints[1] = 0;
			triangleXPoints[2] = 100;

			triangleYPoints[0] = 465;
			triangleYPoints[1] = 365;
			triangleYPoints[2] = 365;

			isGoingUp = true;

			problemAreaX1 = 300;
			problemAreaX2 = 500;

			origY = 305;
		}
		else if(difLevel == 3)
		{
			triangleXPoints = null;
			triangleYPoints = null;

			problemAreaX1 = 200;
			problemAreaX2 = 600;

			origY = 305;
		}
		else if(difLevel == 4)
		{
			triangleXPoints = new int[3];
			triangleYPoints = new int[3];

			triangleXPoints[0] = boundaryIn-100;
			triangleXPoints[1] = boundaryIn;
			triangleXPoints[2] = boundaryIn;

			triangleYPoints[0] = 365;
			triangleYPoints[1] = 465;
			triangleYPoints[2] = 365;

			isGoingUp = false;

			problemAreaX1 = 200;
			problemAreaX2 = 600;				

			origY = 305;
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
			g.fillRect(problemAreaX1, 365, problemAreaX2-problemAreaX1, 300);
		}
		else if(difLevel==2)
		{
			g.setColor(Color.CYAN);
			g.fillPolygon(triangleXPoints, triangleYPoints, 3);
			g.setColor(Color.BLACK);
			g.fillRect(problemAreaX1, 365, problemAreaX2-problemAreaX1, 300);
		}
		else if(difLevel==3)
		{
			if(isUp)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.BLUE);

			g.fillRect(problemAreaX1, 365, problemAreaX2-problemAreaX1, 300);
		}
		else if(difLevel==4)
		{
			if(isUp)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.BLUE);

			g.fillRect(problemAreaX1, 365, problemAreaX2-problemAreaX1, 300);
			g.setColor(Color.CYAN);
			g.fillPolygon(triangleXPoints, triangleYPoints, 3);
		}
    }

}