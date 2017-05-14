/*
 * Varun Ayyappan
 * 04/21/2017
 * Period 1
 * 
 * JumpIn.java
 * 
 * The class that sets up graphics. Has JFrame and main JPanel that shows
 * appropriate panel by selecting the necessary card.
 * 
 * No testing here.
 */

import java.awt.CardLayout; // Class for CardLayout

import javax.swing.JFrame;  // Classes for JFrame, JPanel
import javax.swing.JPanel;

public class JumpIn 
{
	
	private JFrame jiFrame;      // The frame
	private JPanel jiPanel;      // Main Panel that holds other panel in cards
	private MainMenu mmPanel;      // In jiPanel
	private JPanel mgPanel;        // In jiPanel
	private GamePanel gPanel;      // In mgPanel
	private InfoPanel iPanel;      // In mgPanel
	private DirectionsPanel dPanel;  // In jiPanel
	private PauseMenu pmPanel;      // In jiPanel
	private SaveProgress spPanel;    // In spPanel 
	private CardLayout cards;
	
	private final String MAIN_MENU, GAME_PANEL, DIRECTION_PANEL, PAUSE_MENU, SAVE_PANEL;  // For CardLayout
	private int sizeX, sizeY;   // Size of frame
	
	// Initialize field variables
	public JumpIn() 
	{
		sizeX = 1200;
		sizeY = 700;
		
		jiFrame = new JFrame();
		jiPanel = new JPanel();
		mmPanel = new MainMenu(this);
		mgPanel = new JPanel();
		gPanel = new GamePanel(this, sizeX, sizeY);
		iPanel = new InfoPanel(gPanel, sizeX, sizeY);
		dPanel = new DirectionsPanel(this, sizeX, sizeY);
		pmPanel = new PauseMenu(this);
		spPanel = new SaveProgress(this, iPanel, sizeX, sizeY);
		cards = new CardLayout();
		
		MAIN_MENU = "Card with Main Menu";
		GAME_PANEL = "Card with GamePanel";
		DIRECTION_PANEL = "Card with Directions";
		PAUSE_MENU = "Card with Pasue Menu";
		SAVE_PANEL = "Card with Save Screen";
	}
	
	// Instantiates instance of class and calls run
	public static void main(String[] args) 
	{
		JumpIn ji = new JumpIn();
		ji.run();
	}
	
	// Sets up basic graphics backbone
	public void run() 
	{
		jiFrame.setSize(sizeX, sizeY);
		jiFrame.setLocation(0, 0);
		jiFrame.setDefaultCloseOperation(jiFrame.EXIT_ON_CLOSE);
		jiFrame.setResizable(false);
		jiFrame.getContentPane().add(jiPanel);
		
		jiPanel.setLayout(cards);
		jiPanel.add(mmPanel, MAIN_MENU);
		jiPanel.add(mgPanel, GAME_PANEL);
		jiPanel.add(dPanel, DIRECTION_PANEL);
		jiPanel.add(pmPanel, PAUSE_MENU);
		jiPanel.add(spPanel, SAVE_PANEL);
		
		mgPanel.setLayout(null);
		mgPanel.add(gPanel);
		mgPanel.add(iPanel);

		jiFrame.setVisible(true);
	}
	
	// Call this when it is necessary to shift to another card
	public void shift(int selection, int source) 
	{
		if(selection == 1)
		{
			gPanel.resetFrog();
			cards.show(jiPanel, MAIN_MENU);
		}
		else if(selection == 2)
		{
			cards.show(jiPanel, GAME_PANEL);
			gPanel.requestFocus();
		}
		else if(selection == 3)
		{
			cards.show(jiPanel, DIRECTION_PANEL);
			dPanel.setSource(source);
		}
		else if(selection == 4)
			cards.show(jiPanel, PAUSE_MENU);
		else if(selection == 5)
		{
			cards.show(jiPanel, SAVE_PANEL);
			spPanel.repaint();
		}
	}
	
}