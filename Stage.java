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
import java.awt.Font;
import java.awt.Graphics;

import java.text.DecimalFormat;   // Class for DecimalFormat

public class Stage 
{	
	// For the problem
	private int difLevel;
	private boolean isUp;
	private int point1X, point1Y;
	private int point2X, point2Y;
	private int point3X, point3Y;
	private double a;
	private double b;
	private double c;
	private int dotRadius;
	private String answer;
	
	// For checking user input
	private boolean solved;
	private GamePanel gpRef;

	// Initialize field vars
	public Stage(GamePanel gpRefIn, int difLevelIn, boolean isUpIn)
	{
		difLevel = difLevelIn;
		isUp = isUpIn;
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
	
	// Various getter methods
	public int getDifLevel()
	{
		return difLevel;
	}
	
	public boolean getSolved()
	{
		return solved;
	}

	public String getAnswer()
	{
		return answer;
	}

	public int getPoint2Y()
	{
		return point2Y;
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
			// insert difLevel 4 creation here
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
			
			System.out.println(answer);
		}
		else if(difLevel == 3)
		{
			a = ((double)point1Y-point2Y)/(((double)point1X-point2X)*((double)point1X-point2X));
			b = 2*point2X*a;
			c = point2Y+(point2X*point2X);

			a = roundToHundreth(a);
			b = roundToHundreth(b);
			c = roundToHundreth(c);

			answer = "y="+a+"x^2-"+b+"x+"+point2Y;
		}
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
			withBIn = answerIn.substring(answerIn.indexOf("+")+1, answerIn.lastIndexOf("x"));
			withCIn = answerIn.substring(answerIn.lastIndexOf("+")+1, answerIn.length());

			if(withAIn.indexOf(".")==-1)
				withAIn = withAIn + ".0";

			if(withBIn.indexOf(".")==-1)
				withAIn = withAIn + ".0";

			if(withCIn.indexOf(".")==-1)
				withAIn = withAIn + ".0";

			aIn = Double.parseDouble(withAIn);
			bIn = Double.parseDouble(withBIn);
			cIn = Double.parseDouble(withBIn);

			if(a==aIn && b==hIn && c==kIn)
				solved = true;
		}

		return solved;
	}

	// Gets frog in GamePanel to follow path of parabola
	public void drawJump(int frogXIn, int frogYIn)
	{
		int frogYLimit = 355-20*point2Y;

		long expectedtime = System.currentTimeMillis();
		long sleeptime = 500;

		for(int i=200; i<=420; i+=2)
		{
			// Wait for a secound
			while(System.currentTimeMillis() < expectedtime) { }
   			expectedtime += sleeptime;

			gpRef.repaint();
			
			System.out.println("i = " + i);
			
			frogXIn+=2;

			if(i<=310)
				frogYIn+=(frogYLimit/110 + 2);
			else
				frogYIn-=(frogYLimit/110 + 2);

			gpRef.setFrogX(frogXIn);
			gpRef.setFrogY(frogYIn);
		}
	}

	// Draws problem or parabola on GamePanel
	public void draw(Graphics g) 
	{
		g.setColor(Color.RED);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		g.setFont(font);

		g.fillOval(290, 355, dotRadius, dotRadius);
		g.fillOval(355, 355-20*point2Y, dotRadius, dotRadius);
		g.fillOval(420, 355, dotRadius, dotRadius);
		
		if(!solved)
		{
			g.drawString("("+point1X+","+point1Y+")", 300, 345);
			g.drawString("("+point2X+","+point2Y+")", 355, 355-20*point2Y-10);
			
			if(difLevel==1 || difLevel==4)
				g.drawString("("+point3X+","+point3Y+")", 420, 345);
		}
		else
		{
			g.drawArc(300, 360-20*point2Y, 120, 40*point2Y, 0, 180);
		}
	}

}
