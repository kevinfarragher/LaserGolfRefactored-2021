package main;

import java.util.ArrayList;

/**
 * The game manager stores the game's menus, levels, current level, mouse coordinates, 
 * and keeps track of the game's current state.
 */
public class GameManager {
	
		//program's driver
		public Driver driver;
		
		//Game's main menu, levels, and current level
		public MainMenu mainMenu=new MainMenu(this);
		public HowToPlayMenu howToPlayMenu=new HowToPlayMenu(this);
    	public SelectLevelMenu selectLevelMenu=new SelectLevelMenu(this);
    	public ArrayList<Level> levels = new ArrayList<Level>();
		public int level; //number of current level
		
		//game's state settings
	    public boolean placingMirror;
	    public boolean deletingMirror;
	    public boolean rotatingMirror;
	    public boolean restartingLevel;
	    public boolean mirrorPositionDetermined;
	    public boolean mirrorDeletingDetermined;
	    
	    //mouse coordinates
	    public int startMouseX=0;
	    public int startMouseY=0;
	    public int prevMouseX=0;
	    public int prevMouseY=0; //used when rotating mirrors
	    public int currMouseX=0;
	    public int currMouseY=0; //used when rotating mirrors

	    /**
	     * Constructs a gameManager with a given driver. The game's levels, current level, and state settings are initialized.
	     * @param driver - Driver of the program
	     */
	    public GameManager(Driver driver)
	    {
	    	this.driver = driver;
	    	
	    	addLevels();
	    	goToDefaultState();
	    }
	    
	    /**
	     * Loads the next level in the game
	     */
	    public void loadNextLevel()
	    {
	        level++;
	        if(level == levels.size()) {
	        	level = -1; 
	        }
	        
	        driver.frame.setContentPane((level == -1)? mainMenu : levels.get(level).contentPane);
	        driver.frame.pack();	        
	    }

	    /**
	     * Changes game's state to allow a mirror to be added to the current level.
	     */
	    public void addMirrorState()
	    {
	        placingMirror = true;
	        mirrorPositionDetermined = false;
	        deletingMirror = false;
	        rotatingMirror = false;
	        mirrorDeletingDetermined=false;
	        restartingLevel=false;
	    }

	    /**
	     * Changes game's state to allow a mirror to be deleted in the current level.
	     */
	    public void deleteMirrorState()
	    {
	        deletingMirror = true;
	        placingMirror = false;
	        mirrorPositionDetermined = false;
	        rotatingMirror = false;
	        mirrorDeletingDetermined=false;
	        restartingLevel=false;
	    }

	    /**
	     * Changes game's state to allow a mirror to be rotated in the current level.
	     */
	    public void rotateMirrorState()
	    {
	        rotatingMirror = true;
	        deletingMirror = false;
	        placingMirror = false;
	        mirrorPositionDetermined = false;
	        mirrorDeletingDetermined=false;
	        restartingLevel=false;
	    }
	    
	    /**
	     * Loads a given menu.
	     * @param menu - the menu to be loaded. 0=main menu; 1=how to play menu; 2=select level menu
	     */
	    public void loadMenu(int menu) {
	    	if(menu==0) {
	    		level=-1;
		    	driver.frame.setContentPane(mainMenu);
		    	driver.frame.pack();
	    	}
	    	else if(menu==1) {
	    		level=-2;
		    	driver.frame.setContentPane(howToPlayMenu);
		    	driver.frame.pack();
	    	}
	    	else {
	    		level=-3;
		    	driver.frame.setContentPane(selectLevelMenu);
		    	driver.frame.pack();
	    	}
	    }
	    
	    /**
	     * Loads a given level. 
	     * @param level - the given to be loaded (1-18)
	     */
	    public void loadLevel(int level) {
	    	this.level=level-1;
	    	driver.frame.setContentPane(levels.get(level-1).contentPane);
	    	driver.frame.pack();
	    }

	    /**
	     * Restores the game's default state for the current level.
	     */
	    public void goToDefaultState()
	    {
	    	rotatingMirror = false;
	        deletingMirror = false;
	        placingMirror = false;
	        mirrorPositionDetermined = false;
	        mirrorDeletingDetermined=false;
	        restartingLevel=true;
	    }
	    
	    /**
	     * Adds levels to the game manager
	     */
	    public void addLevels() {
	    	levels.add(new Level1(this));
	    	levels.add(new Level2(this));
	    	levels.add(new Level3(this));
	    	levels.add(new Level4(this));
	    	levels.add(new Level5(this));
	    	levels.add(new Level6(this));
	    	levels.add(new Level7(this));
	    	levels.add(new Level8(this));
	    	levels.add(new Level9(this));
	    	levels.add(new Level10(this));
	    	levels.add(new Level11(this));
	    	levels.add(new Level12(this));
	    	levels.add(new Level13(this));
	    	levels.add(new Level14(this));
	    	levels.add(new Level15(this));
	    	levels.add(new Level16(this));
	    	levels.add(new Level17(this));
	    	levels.add(new Level18(this));
	    	for(int i=0;i<levels.size();i++) { //determines each level's number of starting surfaces once initialized
	    		levels.get(i).determineNumberOfStartingSurfaces();
	    	}
	    	level = -1; //displays main menu
	    }
}
