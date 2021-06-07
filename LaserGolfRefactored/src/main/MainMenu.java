package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Constructs and display the game's main menu. 
 */
public class MainMenu extends JPanel {

	/**
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 5531880633878607963L;

	public JLabel gameTitle; //Main menus's title label
	
	//Main menu's buttons
	public JButton startButton;
	public JButton howToPlayButton;
	public JButton selectLevelButton;
	
	public GameManager gameManager; //Main menu's game manager
	
	/**
	 * Constructs a main menu given the program's gameManager. The main menu's labels and buttons are initialized.
	 * @param gameManager - GameManager of the program
	 */
	public MainMenu(GameManager gameManager) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		setBackground(Color.GRAY);
		
		this.gameManager=gameManager;
		
		gameTitle=new JLabel("Laser Golf", SwingConstants.CENTER); //Game title label
		gameTitle.setFont(new Font("Dialog", Font.PLAIN, 32));
		gameTitle.setPreferredSize(new Dimension(800,130));
		gameTitle.setMinimumSize(new Dimension(800,130));
		gameTitle.setMaximumSize(new Dimension(800,130));
		add(gameTitle);
		add(Box.createVerticalStrut(20));
		
		startButton=new JButton("Start"); //Button for starting the game. The game's first level is displayed after clicking.
		startButton.setPreferredSize(new Dimension(800,130));
		startButton.setMinimumSize(new Dimension(800,130));
		startButton.setMaximumSize(new Dimension(800,130));
		startButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		add(startButton);
		add(Box.createVerticalStrut(20));
		startButton.addActionListener(e -> { //When clicked, the game's level is set to 1.
					gameManager.loadNextLevel();
			});
		
		howToPlayButton=new JButton("How To Play");  //Button for user to be sent to a screen to discover how to play the game.
		howToPlayButton.setPreferredSize(new Dimension(800,130));
		howToPlayButton.setMinimumSize(new Dimension(800,130));
		howToPlayButton.setMaximumSize(new Dimension(800,130));
		howToPlayButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		add(howToPlayButton);
		add(Box.createVerticalStrut(20));
		howToPlayButton.addActionListener(e -> {  //When clicked, the user is sent to the how to play screen
					gameManager.loadMenu(1);
			});
		
		selectLevelButton=new JButton("Select Level"); //Button for user to be sent to a screen to select a level they have already beaten
		selectLevelButton.setPreferredSize(new Dimension(800,130));
		selectLevelButton.setMinimumSize(new Dimension(800,130));
		selectLevelButton.setMaximumSize(new Dimension(800,130));
		selectLevelButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		add(selectLevelButton);
		selectLevelButton.addActionListener(e -> {  //when clicked, the user is sent to the select level screen
					gameManager.loadMenu(2);
			});
	}
}
