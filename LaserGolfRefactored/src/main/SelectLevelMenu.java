package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SelectLevelMenu extends JPanel{
	
	//Select level menu's labels and buttons
	public JLabel selectLevelLabel; 
	public JButton[]levelButtons=new JButton[18];
	private JButton exitToMainMenuButton;
	
	//Select level menu's gameManager
	private GameManager gameManager;
	
	/**
	 * Constructs a select level menu given the program's gameManager. The select level menu's labels and buttons are initialized.
	 * @param gameManager - GameManager of the program
	 */
	public SelectLevelMenu(GameManager gameManager) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		setBackground(Color.GRAY);
		this.gameManager=gameManager;
		
		this.setPreferredSize(new Dimension(1200,640));
		add(Box.createVerticalStrut(5));
		
		selectLevelLabel=new JLabel("Select Level", SwingConstants.CENTER); //Game title label
		selectLevelLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
		selectLevelLabel.setPreferredSize(new Dimension(1200,25));
		selectLevelLabel.setMinimumSize(new Dimension(1200,25));
		selectLevelLabel.setMaximumSize(new Dimension(1200,25));
		add(selectLevelLabel);
		add(Box.createVerticalStrut(10));
		
		for(int i=0;i<levelButtons.length;i++) { //initializes select level menu's level buttons
			int levelNumber=i+1;
			levelButtons[i]=new JButton("Level " + levelNumber); 
			levelButtons[i].setFont(new Font("Dialog", Font.PLAIN, 9));
			levelButtons[i].setPreferredSize(new Dimension(1200,25));
			levelButtons[i].setMinimumSize(new Dimension(1200,25));
			levelButtons[i].setMaximumSize(new Dimension(1200,25));
			add(levelButtons[i]);
			add(Box.createVerticalStrut(5));
			levelButtons[i].addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					gameManager.loadLevel(levelNumber);
				} });
		}
		
		exitToMainMenuButton=new JButton("Return to Main Menu"); 
		exitToMainMenuButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		exitToMainMenuButton.setPreferredSize(new Dimension(1200,50));
		exitToMainMenuButton.setMinimumSize(new Dimension(1200,50));
		exitToMainMenuButton.setMaximumSize(new Dimension(1200,50));
		add(exitToMainMenuButton);
		exitToMainMenuButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				gameManager.loadMenu(0);
			} });
	}
}
