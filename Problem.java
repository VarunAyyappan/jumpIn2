/*
 * Varun Ayyappan
 * 04/24/2016
 * Problem.java
 * 
 * Creates parabola problems for player to solve.
 * Can also check if answer is correct.
 * 
 * No testing here.
 */

import java.awt.Color;    // Classes for Color, Graphics
import java.awt.Font;
import java.awt.Graphics;

import java.text.DecimalFormat;   // Class for DecimalFormat

public class Problem 
{	
	// For the problem
	private int difLevel;
	private boolean isUp;
	private int point1X, point1Y;
	private int point2X, point2Y;
	private int point3X, point3Y;
	private int problemAreaX1, problemAreaX2;
	private double a;
	private double b;
	private double c;
	private int dotRadius;
	private String answer;
	private Stage sRef;
	
	// For checking user input
	private boolean solved;
	private GamePanel gpRef;

	// Initialize field vars
	public Problem(GamePanel gpRefIn, Stage sRefIn, int difLevelIn, int problemAreaX1In, int problemAreaX2In,boolean isUpIn)
	{
		difLevel = difLevelIn;
		isUp = isUpIn;
		point1X = 0;
		point1Y = 0;
		point2X = 0;
		point2Y = 0;
		point3X = 0;
		point3Y = 0;
		problemAreaX1 = problemAreaX1In;
		problemAreaX2 = problemAreaX2In;
		answer = "";
		dotRadius = 10;
		solved = false;
		gpRef = gpRefIn;
		sRef = sRefIn;

		createPoints();
		findSolution();
	}
	
	// Various getter methods
	
	public boolean getSolved()
	{
		return solved;
	}

	public String getAnswer()
	{
		return answer;
	}
	
	// Create basis of problems
	public void createPoints()
	{
		if(difLevel==1 || difLevel==2  || difLevel==3)
		{
			point1X = ((int)(Math.random()*5)+1)*2;
			point1Y = 0;
			point3X = ((int)(Math.random()*5)+1)*2+point1X;
			point3Y = 0;
			point2X = (point3X+point1X)/2;
			point2Y = ((int)(Math.random()*5)+1)*2;
		}
		else if(difLevel == 4)
		{
			// Insert difLevel 4 creation here
		}
	}

	// Find the solution to problem
	public void findSolution()
	{	
		if(difLevel==1 || difLevel==2)
		{
			a = ((double)point1Y-point2Y)/(((double)point1X-point2X)*((double)point1X-point2X));
			a = roundToHundreth(a);
			answer = "y="+a+"(x-"+point2X +")^2+"+point2Y;
		}
		else if(difLevel == 3)
		{
			a = ((double)point1Y-point2Y)/(((double)point1X-point2X)*((double)point1X-point2X));
			b = 2*(-point2X)*a;
			c = point2Y+(point2X*point2X);

			a = roundToHundreth(a);
			b = roundToHundreth(b);
			c = roundToHundreth(c);

			if(b<0)
				answer = "y="+a+"x^2"+b+"x+"+c;
			else
			{
				answer = "y="+a+"x^2+"+b+"x+"+c;
			}
		}
		else if(difLevel == 4) 
		{
			// Insert dif level 4 solution here
		}

		System.out.println(answer);
	}

	// Rounds digits to nearest hundreth place
	public static double roundToHundreth(double value) 
	{
    	DecimalFormat df = new DecimalFormat("#.00");
    	String formattedValue = df.format(value);
    	
    	value = Double.parseDouble(formattedValue);
    	return value;
	}

	// Check answer with string passed in
	public boolean checkAnswer(String answerIn)
	{
		String withAIn = "";
		String withHIn = "";
		String withKIn = "";
		String withBIn  = "";
		String withCIn = "";
		Double aIn = 0.0;
		int hIn = 0;
		int kIn  = 0;
		Double bIn = 0.0;
		Double cIn = 0.0;
		
		if(difLevel==1 || difLevel==2)
		{
			answerIn = answerIn.trim();
			withAIn = answerIn.substring(answerIn.indexOf("=")+1, answerIn.indexOf("("));
			withHIn = answerIn.substring(answerIn.lastIndexOf("-")+1, answerIn.indexOf(")"));
			withKIn = answerIn.substring(answerIn.indexOf("+")+1, answerIn.length());

			if(withAIn.indexOf(".")==-1)
				withAIn = withAIn + ".0";

			aIn = Double.parseDouble(withAIn);
			hIn = Integer.parseInt(withHIn);
			kIn = Integer.parseInt(withKIn);

			if(a==aIn && point2X==hIn && point2Y==kIn)
				solved = true;
		}
		else if(difLevel==3 || difLevel==4)
		{
			answerIn = answerIn.trim();
			withAIn = answerIn.substring(answerIn.indexOf("=")+1, answerIn.indexOf("x"));

			if(answerIn.indexOf("+")!=answerIn.lastIndexOf("+"))
				withBIn = answerIn.substring(answerIn.indexOf("+")+1, answerIn.lastIndexOf("x"));
			else
				withBIn = answerIn.substring(answerIn.indexOf("-"), answerIn.lastIndexOf("x"));

			withCIn = answerIn.substring(answerIn.lastIndexOf("+")+1, answerIn.length());

			if(withAIn.indexOf(".")==-1)
				withAIn = withAIn + ".0";

			if(withBIn.indexOf(".")==-1)
				withBIn = withBIn + ".0";

			if(withCIn.indexOf(".")==-1)
				withCIn = withCIn + ".0";

			aIn = Double.parseDouble(withAIn);
			bIn = Double.parseDouble(withBIn);
			cIn = Double.parseDouble(withCIn);

			System.out.println(aIn);
			System.out.println(bIn);
			System.out.println(cIn);

			if(a==aIn && b==bIn && c==cIn)
				solved = true;
		}

		return solved;
	}

	// Draws frog ascent in jump
	public void drawJumpUp(int frogXIn, int frogYIn)
	{
		frogXIn = (problemAreaX2-problemAreaX1)/2+problemAreaX1-40;
		frogYIn = 355-20*point2Y;

		gpRef.setFrogX(frogXIn);
		gpRef.setFrogY(frogYIn);

		gpRef.repaint();
	}

	// Draws frog descent in jump
	public void drawJumpDown(int origFrogYIn)
	{
		int frogX = problemAreaX2;

		gpRef.setFrogX(frogX);
		gpRef.setFrogY(origFrogYIn);

		gpRef.repaint();
	}

	// Draws problem or parabola on GamePanel
	public void draw(Graphics g) 
	{
		g.setColor(Color.RED);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		g.setFont(font);

		g.fillOval(problemAreaX1-10, 355, dotRadius, dotRadius);
		g.fillOval((problemAreaX2-problemAreaX1)/2+problemAreaX1, 355-20*point2Y, dotRadius, dotRadius);
		g.fillOval(problemAreaX2, 355, dotRadius, dotRadius);
		
		if(!solved)
		{
			g.drawString("("+point1X+","+point1Y+")", problemAreaX1-10, 345);
			g.drawString("("+point2X+","+point2Y+")", (problemAreaX2-problemAreaX1)/2+problemAreaX1, 355-20*point2Y-10);
			
			if(difLevel==1 || difLevel==4)
				g.drawString("("+point3X+","+point3Y+")", problemAreaX2, 345);
		}
		else
		{
			g.drawArc(problemAreaX1, 360-20*point2Y, problemAreaX2-problemAreaX1, 40*point2Y, 0, 180);
		}
	}

}
