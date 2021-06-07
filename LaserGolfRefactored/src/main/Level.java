package main;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 * Contains all the variables/methods for a level.
 */
public abstract class Level extends JPanel implements MouseInputListener {

	//program's gameManager
	protected GameManager gameManager;
	
	//used when placing a mirror
	int clickCount = 0;
	
	//store the level's number of par score and the player's score on the level
	protected int parScore;
	protected int playerScore=0;
	
	protected boolean isStarted=false; //level's running status (whether the level's laser's have been emitted or not)

	//a level's objects, which include surfaces (mirrors, black bodies), sensors, and emitters, explosives, beam splitters, and color filters
	protected ArrayList<LevelObject>levelObjects=new ArrayList<LevelObject>();
	
	//a level's lasers, which are emitted from its emitters
	protected ArrayList<Laser> lasers=new ArrayList<Laser>();
	
	//a level's current selected mirror
	protected Mirror selectedMirror;
	
	//a level's buttons
	protected JButton startButton;
	protected JButton restartButton;
	protected JButton quitButton;
	protected JButton addMirrorButton;
	protected JButton removeMirrorButton;
	protected JButton adjustMirrorAngleButton;
	
	//Labels for the par of a level and a player's score on a level
	protected JLabel parLabel;
	protected JLabel playerScoreLabel;
	
	//Displays content of the level
	protected JPanel contentPane;

	/**
	 * Constructs a level by initializing its gameManager, par score, outer walls, and buttons. The level's selected mirror is 
	 * initialized to null. Each level's objects are added/placed in each level's subclass.
	 * @param driver - the driver of the program
	 * @param gameManager - the gameManager of the program
	 */
	public Level(GameManager gameManager, int parScore) {
		super(null);
		setPreferredSize(new Dimension(1000, 450));
		setBackground(Color.YELLOW);
		
		this.gameManager=gameManager;
		addMouseListener(this);
		addMouseMotionListener(this);
		
		this.parScore = parScore;
		
		createOuterWalls();
		
		selectedMirror=null;
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(this, BorderLayout.CENTER);
		
		initializeButtons();
	}
	
	@Override
	protected void paintComponent(Graphics graphicHelper) {
		super.paintComponent(graphicHelper);
		Graphics2D g = (Graphics2D)graphicHelper;
		
		updateLevelStatus();
		draw(g);
		if(gameManager.placingMirror==true && gameManager.mirrorPositionDetermined==true) { //if the user is actively placing a mirror on the current level display, a temporary mirror is shown as it is being dragged and resized.
			Mirror tempMirror=new Mirror(gameManager.startMouseX,gameManager.startMouseY,gameManager.currMouseX,gameManager.currMouseY,false);
			tempMirror.draw(g);
		}
		if(gameManager.rotatingMirror==true || gameManager.deletingMirror==true) { //if the user is attempting to delete or rotate a mirror, a point is drawn on each mirror to represent each mirror's center point. The center point of each mirror is clicked by the user to delete a mirror or start rotating a mirror.
			for(int i=0;i<levelObjects.size();i++) { 
				if(levelObjects.get(i) instanceof Mirror) {
					Mirror mirror=(Mirror)levelObjects.get(i);
					if(mirror.isPartOfLevelDesign==false) {
						g.setStroke(new BasicStroke(5));
						g.setColor(Color.BLACK);
						g.drawLine((int)(mirror.getP1().getX()+mirror.getP2().getX())/2, (int)(mirror.getP1().getY()+mirror.getP2().getY())/2, (int)(mirror.getP1().getX()+mirror.getP2().getX())/2, (int)(mirror.getP1().getY()+mirror.getP2().getY())/2);
					}
				}
			}
		}
		
		//automatic repainting functionality
		long start = System.currentTimeMillis();
		long delta = 0;
		long frameTime = 1000/60;
		while(delta<frameTime) {
			delta = System.currentTimeMillis() - start;
		}
		
		repaint();
	}
	
	/**
	 * Used to draw a level. Draws a level's objects, which include surfaces (mirrors, black bodies), emitters, sensors, color filters,
	 * explosives, and beam splitters. Also draws a level's lasers. 
	 * @param g - Graphics object to draw the level's objects
	 */
	public void draw(Graphics2D g) {
		for(int i=0;i<levelObjects.size();i++) {
			levelObjects.get(i).draw(g);
		}
		
		for(int i = 0; i < lasers.size(); i++) {
			lasers.get(i).draw(g);
		}
	}
	
	/**
	 * Updates a level's status by continuously firing lasers from the level's emitters, updating each lasers' travel, and 
	 * checking the level's objects (sensors, color filters, beam splitters, black bodies, mirrors, and explosives) for laser collisions.
	 */
	public void updateLevelStatus() {
		if(isStarted==true) { //if a level is currently running
			for(int i=0;i<levelObjects.size();i++) { //each emitter's laser are continuously fired until they reach a sensor that accepts them
				if(levelObjects.get(i) instanceof Emitter) {
					Emitter emitter=(Emitter) levelObjects.get(i);
					emitter.fire(lasers);
				}
			}
			
			for(int i=0;i<lasers.size();i++) { //updates the lasers' travel
				lasers.get(i).tick();
			}
			
			for(int i=0;i<levelObjects.size();i++) {
				levelObjects.get(i).checkForCollisionWithLaser(lasers, this);
			}
			
			boolean allhit = true; //variable to keep track of whether every sensor has been hit by a laser of its color
			for(int i = 0; i < levelObjects.size(); i++) {
				if(levelObjects.get(i) instanceof Sensor) {
				Sensor sensor=(Sensor)levelObjects.get(i);
				sensor.checkForCollisionWithLaser(lasers, this);
					if(sensor.isHit()==false) { //if a sensor hasn't been hit by a laser of its color, every sensor hasn't been hit by a laser of its color and the level continues
						allhit = false;
					}
				}
			}
			if(allhit) { //If every sensor has been hit by a laser of its color, the user's score on the level is displayed, the level ends/is reset, and the next level is loaded
				isStarted = false; 
				//JOptionPane.showMessageDialog(gameManager.driver.frame, String.format("Your score: %d%nYour score relative to par: %d", playerScore, playerScore - parScore));
				resetLevel();
				removePlacedMirrors();
				gameManager.loadNextLevel();
				return;
			}
		}
	}
	
	/**
	 * Used to reset level by resetting the emitters' lasers and the level's sensors.
	 */
	public void resetLevel() {
		lasers.clear();
		for(int i=0;i<levelObjects.size();i++) { //sensors reset
			if(levelObjects.get(i) instanceof Sensor) {
				Sensor sensor=(Sensor)levelObjects.get(i);
				sensor.hit=false;
			}
			if(levelObjects.get(i) instanceof BeamSplitter) {
				BeamSplitter beamSplitter=(BeamSplitter)levelObjects.get(i);
				beamSplitter.sensor.hit=false;
			}
		}
		isStarted=false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Handles mouse pressed events
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		for(int i=0;i<levelObjects.size();i++) { //if a level is currently being displayed, the level isn't being ran, and the user clicks on one of the level's emitters, the emitter and its laser are rotated and the level is reset to its default state.
			if(levelObjects.get(i) instanceof Emitter) {
				Emitter emitter=(Emitter)levelObjects.get(i);
				if(emitter.contains(gameManager.currMouseX, gameManager.currMouseY) && isStarted==false) {
					emitter.rotate();
					gameManager.goToDefaultState();
					return;
				}
			}
		}
		if(gameManager.placingMirror==true) { //if a level is currently being displayed and the user is attempting to add a mirror, the status of whether the mirror's starting position has been determined is set to true.
			gameManager.mirrorPositionDetermined=true;
			clickCount++;
		
			if(clickCount%2!=0) {
				gameManager.startMouseX = e.getX();
				gameManager.startMouseY = e.getY();
				
			}else {
				gameManager.currMouseX = e.getX();
				gameManager.currMouseY = e.getY();
				
				if(gameManager.placingMirror==true) { //if a level is currently being displayed, the user is attempting to add a mirror, and the mirror, if placed using the starting and current points, doesn't intersect with any of the level's objects, which include surfaces (mirrors, black bodies), sensors, emitters, color filters, explosives, and beam splitters, the mirror is added to the level and displayed.
					if((gameManager.startMouseX!=gameManager.currMouseX) && (gameManager.startMouseY!=gameManager.currMouseY)) { //if the start and current points aren't the same
						for(int i=0;i<levelObjects.size();i++) {
							if(levelObjects.get(i).checkIfMirrorCanBePlaced(gameManager)==false) {
								System.out.println("Mirror can't be placed here");
								gameManager.mirrorPositionDetermined=false;
								return;
							}
						}
						
						//The mirror is placed and added to the level if the mirror, when placed using the starting and current points, 
						//doesn't contain/intersect any of the level's objects, which include the surfaces (black bodies, mirrors) sensors, 
						//emitters, beam splitters, color filters, and explosives of the level.
						addMirror(); 
					}
				}
			}
		}
		if(gameManager.deletingMirror==true) { //if a level is currently being displayed, the user is attempting to delete a mirror, and the mouse point contains one of the center points of the level's mirrors, the mirror is removed from the level's list of surfaces and deleted.
			for(int i=0;i<levelObjects.size();i++) {
				if(levelObjects.get(i) instanceof Mirror) { //if the surface is a mirror
					Mirror mirror=(Mirror) levelObjects.get(i);
					if(mirror.isPartOfLevelDesign==false) {
						Point2D.Double mirrorCenter=new Point2D.Double((mirror.getP1().getX()+mirror.getP2().getX())/2,(mirror.getP1().getY()+mirror.getP2().getY())/2); //current iterated mirror's center point
						if(mirrorCenter.distance(new Point2D.Double(gameManager.currMouseX, gameManager.currMouseY))<=2) {
							levelObjects.remove(i);
							playerScore--;
							playerScoreLabel.setText(String.format("Your score: %d", playerScore));
							return;
						}
					}
				}
			}
		}
		if (gameManager.rotatingMirror) //if a level is currently being displayed, the user is attempting to rotate a mirror, and the mouse point contains one of the center points of the level's mirrors, the mirror is marked as the selected mirror for rotation.
        {
			for(int i=0;i<levelObjects.size();i++) {
				if(levelObjects.get(i) instanceof Mirror) { //if the surface is a mirror
					Mirror mirror=(Mirror) levelObjects.get(i);
					if(mirror.isPartOfLevelDesign==false) {
						Point2D.Double mirrorCenter=new Point2D.Double((mirror.getP1().getX()+mirror.getP2().getX())/2,(mirror.getP1().getY()+mirror.getP2().getY())/2); //current iterated mirror's center point
						if(mirrorCenter.distance(new Point2D.Double(gameManager.currMouseX, gameManager.currMouseY))<=4) {
							selectedMirror=(Mirror)mirror;
							return;
						}
					}
				}
			}
        }
		repaint();
	}
	
	/**
	 * Handles mouse dragged events.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		gameManager.currMouseX=e.getX();
		gameManager.currMouseY=e.getY();
		if(this!=null) { //if a level is currently being displayed
			if (gameManager.rotatingMirror) //if a mirror is being rotated and the mouse is being dragged, the mirror will be rotated based on the difference between the mouse's current y-coordinate and the mouse's previous y-coordinate.
	        {
	            gameManager.currMouseY = e.getY();   
	            if(this.selectedMirror!=null) {
	            	selectedMirror.rotate(gameManager);
	            }
	            gameManager.prevMouseY = e.getY();
	        }
		}
		repaint();
	}

	/**
	 * Handles mouse released events
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		gameManager.currMouseX=e.getX();
		gameManager.currMouseY=e.getY();
			if(this!=null) { //if a level is being displayed

			if (gameManager.rotatingMirror) //if a mirror is rotated and the mouse is released, the rotated version of the mirror is checked for if it contains/intersects one of the level's objects, which includes surfaces (black bodies, mirrors), emitters, sensors, beam splitters, color filters, and explosives. If so, the mirror is reset to its previous (initial) rotation before this current rotation. If not, the mirror rotation is complete. At the end, the selected mirror for rotation is set to null, with there no longer being a selected mirror for rotation in the current level.
	        {
				if(this.selectedMirror!=null) { //if the current rotated mirror is not null
					for(int i=0;i<levelObjects.size();i++) {
						if(levelObjects.get(i).checkIfMirrorCanBeRotated(selectedMirror)==false) {
							System.out.println("Mirror can't be rotated here");
							this.selectedMirror.currRotatedX1=this.selectedMirror.prevRotatedX1;
							this.selectedMirror.currRotatedY1=this.selectedMirror.prevRotatedY1;
							this.selectedMirror.currRotatedX2=this.selectedMirror.prevRotatedX2;
							this.selectedMirror.currRotatedY2=this.selectedMirror.prevRotatedY2;
							this.selectedMirror.currTheta=this.selectedMirror.prevTheta;
							this.selectedMirror=null;
							return;
						}
					}
		
					//if the rotated version of the mirror doesn't contain/intersect any of the level's objects, which include surfaces 
					//(black bodies, mirrors), emitters, sensors, color filters, explosives, and beam splitters, the mirror rotation is 
					//complete. The mirror's previous points are set to the current points after this current rotation. The The selected 
					//mirror for rotation is set to null, with there no longer being a selected mirror for rotation in the current level.
					this.selectedMirror.prevRotatedX1=this.selectedMirror.currRotatedX1;
					this.selectedMirror.prevRotatedY1=this.selectedMirror.currRotatedY1;
					this.selectedMirror.prevRotatedX2=this.selectedMirror.currRotatedX2;
					this.selectedMirror.prevRotatedY2=this.selectedMirror.currRotatedY2;
					this.selectedMirror.prevTheta=this.selectedMirror.currTheta;
					this.selectedMirror=null;
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * Handles mouse moved events. When the mouse is moved, the mouse's x and y-coordinates are stored.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		gameManager.currMouseX=e.getX();
		gameManager.currMouseY=e.getY();
	}
	
	/**
	 * Adds a mirror to the objects of the level based on the mouse's start and current points.
	 */
	public void addMirror() {
		levelObjects.add(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,false));
		gameManager.mirrorPositionDetermined = false;
		playerScore++;
		playerScoreLabel.setText(String.format("Your score: %d", playerScore));
	}
	
	/**
	 * Determine a level's number of starting surfaces by counting the level's mirrors and black bodies.
	 * @return - a level's number of starting surfaces
	 */
	public int determineNumberOfStartingSurfaces() {
		int numStartingSurfaces=0;
		for(int i=0;i<levelObjects.size();i++) {
			if(levelObjects.get(i) instanceof Mirror || levelObjects.get(i) instanceof Blackbody) {
				numStartingSurfaces++;
			}
		}
		return numStartingSurfaces;
	}
	
	/**
	 * Removes a level's placed mirrors
	 */
	public void removePlacedMirrors() {
		for(int i=0;i<levelObjects.size();i++) { //mirrors placed by the user are removed
			if(levelObjects.get(i) instanceof Mirror) {
				Mirror mirror=(Mirror)levelObjects.get(i);
				if(mirror.isPartOfLevelDesign==false) {
					playerScore--;
					playerScoreLabel.setText(String.format("Your score: %d", playerScore));
					levelObjects.remove(i);
					i--;
				}
			}
		}
	}
	
	/**
	 * Creates the outer walls of the level
	 */
	public void createOuterWalls() {
		levelObjects.add(new Blackbody(0,0,1000,0));
		levelObjects.add(new Blackbody(0,0,0,450));
		levelObjects.add(new Blackbody(0,450,1000,450));
		levelObjects.add(new Blackbody(1000,0,1000,450));
	}
	
	/**
	 * Intitializes a level's buttons (start, restart, quit, add mirror, delete mirror, adjust mirror).
	 */
	public void initializeButtons() {
		JPanel buttonTray = new JPanel();
		
		startButton=new JButton("Start"); //Button for starting and halting a level
		startButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		buttonTray.add(startButton);
		startButton.addActionListener(e -> { //When clicked, isStarted is set to !isStarted and the level's default state is restored. If isStarted was true before clicked, the level's current run is halted and the level's emitters reset. If isStarted was false before clicked, the level is started and the laser will start moving. 
					isStarted=!isStarted;
					gameManager.goToDefaultState();
					if(isStarted==false) {
						resetLevel();
					}
			});
		
		restartButton=new JButton("Restart"); //Button for restarting a level
		restartButton.setFont(new Font("Dialog", Font.PLAIN, 9)); 
		buttonTray.add(restartButton);
		restartButton.addActionListener(e -> {  //When clicked, the level is reset and the level is restored to its default state.
				resetLevel();
				playerScore=0;
				playerScoreLabel.setText(String.format("Your score: %d", playerScore));
				gameManager.goToDefaultState();
				removePlacedMirrors();
			});
		
		quitButton=new JButton("Quit"); //Button for quitting a level
		quitButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		buttonTray.add(quitButton);
		quitButton.addActionListener(e -> { //When clicked, the level is reset, restored to its default settings, and the user is returned to the main menu (level 0) 
				resetLevel();
				playerScore=0;
				playerScoreLabel.setText(String.format("Your score: %d", playerScore));
				removePlacedMirrors();
				gameManager.loadMenu(0);
				gameManager.goToDefaultState();
			});
		
		addMirrorButton=new JButton("Add Mirror"); //Button for adding mirror
		addMirrorButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		buttonTray.add(addMirrorButton);
		addMirrorButton.addActionListener(e -> {  
				if(isStarted) {
					resetLevel(); 
				}
				if(gameManager.placingMirror==true) { //When clicked, if adding mirror mode is enabled, adding mirror mode is disabled and the level is restored to its default state.  
			    	gameManager.goToDefaultState();
			    	return;
			    }
			    gameManager.addMirrorState(); //When clicked, if adding mirror mode is disabled, adding mirror mode is enabled and the current run of the level is halted.
			    isStarted=false;
			});
		
		removeMirrorButton=new JButton("Delete Mirror"); //Button for deleting mirror
		removeMirrorButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		buttonTray.add(removeMirrorButton);
		removeMirrorButton.addActionListener(e -> { 
				if(isStarted) {
					resetLevel(); 
				}
				if(gameManager.deletingMirror==true) { //When clicked, if deleting mirror mode is enabled, adding mirror mode is disabled and the level is restored to its default state
			    	gameManager.goToDefaultState();
			    	return;
			    }
			    gameManager.deleteMirrorState(); //When clicked, if deleting mirror mode is disabled, deleting mirror mode is enabled and the current run of the level is halted.
			    isStarted=false;
			});
		
		adjustMirrorAngleButton=new JButton("Adjust Mirror Angle"); //button for adjusting mirror angle 
		adjustMirrorAngleButton.setFont(new Font("Dialog", Font.PLAIN, 9));
		buttonTray.add(adjustMirrorAngleButton);
		adjustMirrorAngleButton.addActionListener(e -> { //When clicked, if rotating mirror mode is disabled, rotating mirror mode is enabled and the current run of the level is halted. If rotating mirror mode is enabled, rotating mirror mode is disabled and the level is restored to its default state.  
				if(isStarted) {
					resetLevel(); 
				}
				if(gameManager.rotatingMirror==true) {
			    	gameManager.goToDefaultState();
			    	return;
			    }
				gameManager.rotateMirrorState();
				isStarted=false;
			});
		
		parLabel = new JLabel(String.format("Par %d", parScore));
		buttonTray.add(parLabel);
		playerScoreLabel = new JLabel(String.format("Your score: %d", playerScore));
		buttonTray.add(playerScoreLabel);
		
		contentPane.add(buttonTray, BorderLayout.PAGE_END);
		
		startButton.setVisible(true);
		restartButton.setVisible(true);
		quitButton.setVisible(true);
		addMirrorButton.setVisible(true);
		removeMirrorButton.setVisible(true);
		adjustMirrorAngleButton.setVisible(true);
	}
}
