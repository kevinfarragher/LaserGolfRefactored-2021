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

public class HowToPlayMenu extends JPanel {
	
	//Main menu's labels
	public JLabel howToPlayTitle; 
	public JLabel overviewLabel;
	public JLabel buttonsLabel;
	public JLabel startButtonLabel;
	public JLabel restartButtonLabel;
	public JLabel quitButtonLabel;
	public JLabel addMirrorButtonLabel;
	public JLabel deleteMirrorButtonLabel;
	public JLabel rotateMirrorButtonLabel;
	public JLabel objectsLabel;
	public JLabel emitterLabel;
	public JLabel sensorLabel;
	public JLabel mirrorLabel;
	public JLabel blackBodyLabel;
	public JLabel beamSplitterLabel;
	public JLabel laserLabel;
	public JLabel colorFilterLabel;
	public JLabel explosiveLabel;
	public JButton exitToMainMenuButton;
	
	//How to play menu's gameManager
	public GameManager gameManager;
	
	/**
	 * Constructs the how to play menu with a GameManager instance. Initializes its components. 
	 * @param gameManager - the game's manager
	 */
	public HowToPlayMenu(GameManager gameManager) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		setBackground(Color.GRAY);
		this.gameManager=gameManager;
		
		this.setPreferredSize(new Dimension(1200,560));
		add(Box.createVerticalStrut(5));
		
		howToPlayTitle=new JLabel("How To Play", SwingConstants.CENTER); //Game title label
		howToPlayTitle.setFont(new Font("Dialog", Font.PLAIN, 32));
		howToPlayTitle.setPreferredSize(new Dimension(1200,40));
		howToPlayTitle.setMinimumSize(new Dimension(1200,40));
		howToPlayTitle.setMaximumSize(new Dimension(1200,40));
		add(howToPlayTitle);
		add(Box.createVerticalStrut(10));
		
		overviewLabel=new JLabel(); 
		overviewLabel.setText(
				"<html>Overview: Laser Golf is a puzzle/arcade game where the"
				+ " player’s primary objective is to guide coloured lasers through a variety of complex levels from emitters "
				+ "to correspondingly coloured receivers such that all receivers are being struck by the correct colour "
				+ "simultaneously. The player can draw as many mirrors as they like to achieve this, but "
				+ "the catch is that in order to attain a higher score, the player must use fewer mirrors. Each level has a set "
				+ "“par” of mirrors which represents the primary solution, but in many cases the level can be completed with "
				+ "fewer than the par of mirrors, resulting in large score-rewards. </html>"
				);
		overviewLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		add(overviewLabel);
		add(Box.createVerticalStrut(20));
		
		buttonsLabel=new JLabel(); 
		buttonsLabel.setText("<html><b><u>Buttons</u></b></html>");
		buttonsLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		add(buttonsLabel);
		
		startButtonLabel=new JLabel();
		startButtonLabel.setText("<html>Start Button - Runs the level. If clicked during a level's run, the level is stopped.</html>");
		startButtonLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(startButtonLabel);
		
		restartButtonLabel=new JLabel(); 
		restartButtonLabel.setText("<html>Restart Button - Restarts the level. The mirrors placed on the current level are removed. If clicked during a level's run, the level is stopped.</html>");
		restartButtonLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(restartButtonLabel);
		
		quitButtonLabel=new JLabel(); 
		quitButtonLabel.setText("<html>Quit Button - Returns to the main menu from the current level.</html>");
		quitButtonLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(quitButtonLabel);
		
		addMirrorButtonLabel=new JLabel(); 
		addMirrorButtonLabel.setText("<html>Add Mirror Button - Enables the ability to add a mirror to the current level. A mirror can be placed by clicking one"
				+ "once, where the first click indicates the first end point of the mirror, and clicking again, where the second click indicates the second"
				+ "end point of the mirror.</html>");
		addMirrorButtonLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(addMirrorButtonLabel);
		
		deleteMirrorButtonLabel=new JLabel(); 
		deleteMirrorButtonLabel.setText("<html>Delete Mirror Button - Enables the ability to delete a mirror of the current level. When a mirror's center (marked by "
				+ "a black point) is clicked, the mirror is deleted.</html>");
		deleteMirrorButtonLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(deleteMirrorButtonLabel);
		
		rotateMirrorButtonLabel=new JLabel(); 
		rotateMirrorButtonLabel.setText("<html>Rotate Mirror Button - Enables the ability to rotate a mirror of the current level. When a mirror's center (marked by "
				+ "a black point) is clicked and dragged upwards and downwards, the mirror is rotated 1 degree (counterclockwise and clockwise, respectively).</html>");
		rotateMirrorButtonLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(rotateMirrorButtonLabel);
		add(Box.createVerticalStrut(20));
		
		objectsLabel=new JLabel(); 
		objectsLabel.setText("<html><b><u>Objects</u></b></html>");
		objectsLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
		add(objectsLabel);
		
		emitterLabel=new JLabel();
		emitterLabel.setText("<html>Emitter - Displayed as a non-black, colored circle with an outlined smaller circle (laser) in its center. Emits a laser at its current angle."
				+ "which is indicated by the line coming out of it.</html>");
		emitterLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(emitterLabel);
		
		sensorLabel=new JLabel();
		sensorLabel.setText("<html>Sensor - Displayed as a non-black, colored rectangle with a black outline. Once a laser of the same color reaches it, the next level is loaded.");
		sensorLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(sensorLabel);
		
		mirrorLabel=new JLabel();
		mirrorLabel.setText("<html>Mirror - Displayed as a cyan rectangle. Lasers reflect off of them.");
		mirrorLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(mirrorLabel);
		
		blackBodyLabel=new JLabel();
		blackBodyLabel.setText("<html>Blackbody - Displayed as a black line. When a laser collides with them, the current level run fails.");
		blackBodyLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(blackBodyLabel);
		
		beamSplitterLabel=new JLabel();
		beamSplitterLabel.setText("<html>Beam Splitter - Displayed as a black rectangle with three different colored lasers pointing from it"
				+ "When a laser collides with them, its lasers are fired.</html>");
		beamSplitterLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(beamSplitterLabel);
		
		laserLabel=new JLabel();
		laserLabel.setText("<html>Laser - Displayed as an oulinted, non-black circle. Fired from emitters and reflects off mirrors"
				+ "When a laser collides with them, its lasers are fired.</html>");
		laserLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(laserLabel);
		
		colorFilterLabel=new JLabel();
		colorFilterLabel.setText("<html>Color Filter - Displayed as a non-black rectangle. When a laser moves through them, the laser's color is changed to the filter's color."
				+ "When a laser collides with them, its lasers are fired.</html>");
		colorFilterLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(colorFilterLabel);
		
		explosiveLabel=new JLabel();
		explosiveLabel.setText("<html>Explosive - Displayed as a black rectangle with a yellow triangle as a warning symbol. When a laser collides with them, the current level run fails.");
		explosiveLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		add(explosiveLabel);
		add(Box.createVerticalStrut(20));
		
		exitToMainMenuButton=new JButton("Return to Main Menu"); 
		exitToMainMenuButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		exitToMainMenuButton.setPreferredSize(new Dimension(1200,40));
		exitToMainMenuButton.setMinimumSize(new Dimension(1200,40));
		exitToMainMenuButton.setMaximumSize(new Dimension(1200,40));
		add(exitToMainMenuButton);
		exitToMainMenuButton.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				gameManager.loadMenu(0);
			} });
	}
}
