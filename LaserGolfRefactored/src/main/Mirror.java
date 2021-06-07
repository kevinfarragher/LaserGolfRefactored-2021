package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * A mirror that can be rotated, added, deleted, and reflects lasers upon collision.
 * 
 */
public class Mirror extends Surface{

	static final Color MIRROR_COLOR = new Color(0xb0fffe); //mirror's color
	
	/**
	 * Previous and current thetas (angles) of the mirror. Used, for instance, when the mirror is being rotated.
	 */
	public double prevTheta;
	public double currTheta;
	
	/**
	 * The coordinates of the mirror before a rotation.
	 */
	public double prevRotatedX1;
	public double prevRotatedY1;
	public double prevRotatedX2;
	public double prevRotatedY2;
	
	/**
	 * The coordinates of the mirror after a rotation. The coordinates of the mirror's rotated version.
	 */
	public double currRotatedX1;
	public double currRotatedY1;
	public double currRotatedX2;
	public double currRotatedY2;
	
    /**
     * Constructs a mirror with given starting and ending points and whether mirror is part of level's design. The mirror's previous theta (angle), current theta (angle), and additional rotation are initialized to 0, and the previous and current points of the rotated version of the mirror are initialized to the mirror's given starting and ending points when first created.
     * @param x1 - x-coordinate of mirror's starting point
     * @param y1 - y-coordinate of mirror's starting point
     * @param x2 - x-coordinate of mirror's ending point
     * @param y2 - y-coordinate of mirror's ending point
     * @param isPartOfLevelDesign - determines whether mirror is part of level's design. If true, mirror can be removed; otherwise, it can't be removed
     */
	public Mirror(double x1, double y1, double x2, double y2, boolean isPartOfLevelDesign) {
		super(x1, y1, x2, y2, MIRROR_COLOR);
		additionalRotation=0;
		prevTheta=0;
		currTheta=0;
		prevRotatedX1=x1;
		prevRotatedY1=y1;
		prevRotatedX2=x2;
		prevRotatedY2=y2;
		currRotatedX1=x1;
		currRotatedY1=y1;
		currRotatedX2=x2;
		currRotatedY2=y2;
		this.isPartOfLevelDesign=isPartOfLevelDesign;
	}
	
	/**
	 * Checks if a mirror has collided with a laser. If so, the laser is properly reflected off of the mirror, and the laser's 
	 * most recent mirror it has reflected off of is set to the mirror.
	 * @param lasers - the lasers of a level
	 */
	public void checkForCollisionWithLaser(ArrayList<Laser> lasers, Level level) {
		for (Laser laser : lasers) {
			Mirror mRotated=new Mirror(currRotatedX1,currRotatedY1,currRotatedX2,currRotatedY2,false); //rotated version of the mirror. The rotated verison of the mirror is displayed on the screen.
			if((mRotated.ptSegDist(new Point2D.Double(laser.getCenterX(), laser.getCenterY())) < Surface.COLLIDE_DIST) && this!=laser.mostRecentMirrorReflectedOff) { //if the laser has collided with the mirror (rotated version), the laser is reflected off of the mirror. The laser's most recent mirror it has reflected off of is set to the mirror.
				Vector2D incident = new Vector2D(laser.theta);
				Vector2D normal = mRotated.getNormal();
				Vector2D reflect = incident.subtract(normal.scalarMultiply(2 * incident.dotProduct(normal)));
		        laser.theta = reflect.angleOf(); //Yield.
		        laser.mostRecentMirrorReflectedOff=this;
			}
		}
	}
	
	/**
	 * Checks if a mirror can be placed on a level by checking if the mirror intersects the mirror. If the mirror intersects
	 * the mirror, the mirror is not placed.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be placed, false if mirror cannot be placed.
	 */
	@Override
	public boolean checkIfMirrorCanBePlaced(GameManager gameManager) {
		//if the mirror, placed using the starting and current points, intersects the mirror (it's rotated version), the mirror isn't placed.
		Mirror mRotated=new Mirror(currRotatedX1,currRotatedY1,currRotatedX2,currRotatedY2,true); //the rotated version of the mirror. The rotated version of each mirror is displayed on the screen.
		if(new Mirror(gameManager.startMouseX,gameManager.startMouseY, gameManager.currMouseX,gameManager.currMouseY,true).intersectsLine(mRotated)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a mirror can be rotated on a level by checking if the mirror intersects the mirror. If the mirror intersects
	 * the mirror, the mirror is not rotated.
	 * @param gameManager - the game's manager
	 * @return - true if mirror can be rotated, false if mirror cannot be rotated.
	 */
	@Override
	public boolean checkIfMirrorCanBeRotated(Mirror selectedMirror) {
		//if the rotated version of the mirror intersects the mirror, the mirror isn't rotated
		Mirror mRotated=new Mirror(selectedMirror.currRotatedX1,selectedMirror.currRotatedY1,selectedMirror.currRotatedX2,selectedMirror.currRotatedY2,true); //the rotated version of the selected mirror trying to be rotated
		Mirror cimRotated=new Mirror(currRotatedX1,currRotatedY1,currRotatedX2,currRotatedY2,true); //the rotated version of the mirror
		if(mRotated.intersectsLine(cimRotated) && this!=selectedMirror) {
			return false;
		}
		return true;
	}
	
	/**
	 * Rotates a mirror based on mouse coordinates when the mouse is being dragged
	 * @param gameManager - the game's game manager
	 */
	public void rotate(GameManager gameManager) {
		 if (gameManager.currMouseY - gameManager.prevMouseY > 0) //if the difference between the mouse's current y-coordinate and previous y-coordinate is greater than 0, the mirror will be rotated clockwise by 1 degree.
         {
         	currTheta++;
         }
         else if (gameManager.currMouseY - gameManager.prevMouseY < 0) //if the difference between the mouse's current y-coordinate and previous y-coordinate is less than 0, the mirror will be rotated counter-clockwise by 1 degree.
         {
         	currTheta--;
         }
         else
         {
         	additionalRotation += 0; //if the difference between the mouse's current y-coordinate and previous y-coordinate is 0, the mirror will not be rotated.
         	currTheta+=0;
         }
       updateRotatedCoordinates(); //after the mirror has been rotated, the mirror's rotated coordinates are updated
	}
	
	/**
	 * Draws the mirror.
	 * @param g - Graphics instance used to draw the mirror.
	 */
	public void draw(Graphics2D g) {
		Graphics2D copy = (Graphics2D) g.create(); //Copy of graphics instance created to avoid rotating all graphics objects when the mirror is drawn
		copy.setColor(col);
		copy.setStroke(new BasicStroke(5));
		copy.rotate(Math.toRadians(currTheta), (x1+x2)/2, (y1+y2)/2);
		copy.draw(this);
		copy.dispose();
	}
	
	/**
	 * Updates the coordinates of the rotated version of the mirror. The rotated version of the mirror is displayed on the screen.
	 */
	public void updateRotatedCoordinates() {
		double[]startingPointCorrdinates= {x1,y1}; //array of mirror's inital starting point. The x and y-coordinate of the mirror's initial starting point are stored in the array.
		double[]endingPointCoordinates= {x2,y2}; //array of mirror's inital ending point. The x and y-coordinate of the mirror's initial ending point are stored in the array.
		double angle_radians = currTheta*Math.PI/180; //angle of the mirror in radians
		Point2D.Double[]rotatedPoints=rotate(startingPointCorrdinates,endingPointCoordinates,angle_radians); //array that stores the points of the reflected version of the mirror based on its initial starting point coordinates, initial ending point coordinates, and current theta (angle).
		//The coordinates of the start and end points of the current reflected version of the mirror are updated
		currRotatedX1=rotatedPoints[0].x;
		currRotatedY1=rotatedPoints[0].y;
		currRotatedX2=rotatedPoints[1].x;
		currRotatedY2=rotatedPoints[1].y;
	}
	
	/**
	 * Used to find the points of the reflected version of the mirror based on its initial starting point coordinates, initial ending point coordinates, and current theta (angle).
	 * @param startingPointCoordinates - the x and y-coordinates of the initial starting point of the mirror
	 * @param endingPointCoordinates - the x and y-coordinates of the initial ending point of the mirror
	 * @param theta - the current theta (angle) of the mirror
	 * @return
	 */
	public Point2D.Double[] rotate(double[]startingPointCoordinates, double[]endingPointCoordinates, double theta) {
	    // startingPointCoordinates and endingPointCoordinates are arrays of length 2 with the x, y coordinates of
	    // the inital starting and endings points of the mirror with the form [x, y]

	    double[]midpoint = { //array to store the x and y-coordinates of the mirror's midpoint
	        (startingPointCoordinates[0] + endingPointCoordinates[0])/2,
	        (startingPointCoordinates[1] + endingPointCoordinates[1])/2
	    };

	    //The midpoint is made the origin
	    double[] startingPointCoordinates_mid = {
	        startingPointCoordinates[0] - midpoint[0],
	        startingPointCoordinates[1] - midpoint[1]
	    };
	    double[]endingPointCoordinates_mid = {
	        endingPointCoordinates[0] - midpoint[0],
	        endingPointCoordinates[1] - midpoint[1]
	    };
	    
	    double[]startingPointCoordinates_rotated = { //array to store the x and y-coordinates of the mirror's rotated initial starting point based on its current theta (angle).
	        Math.cos(theta)*startingPointCoordinates_mid[0] - Math.sin(theta)*startingPointCoordinates_mid[1],
	        Math.sin(theta)*startingPointCoordinates_mid[0] + Math.cos(theta)*startingPointCoordinates_mid[1]
	    };
	    
	    double[]endingPointCoordinates_rotated = { //array to store the x and y-coordinates of the mirror's rotated initial ending point based on its current theta (angle).
	        Math.cos(theta)*endingPointCoordinates_mid[0] - Math.sin(theta)*endingPointCoordinates_mid[1],
	        Math.sin(theta)*endingPointCoordinates_mid[0] + Math.cos(theta)*endingPointCoordinates_mid[1]
		};

	    // The midpoint coordinates are returned to the previous origin
	    startingPointCoordinates_rotated[0] = startingPointCoordinates_rotated[0] + midpoint[0];
	    startingPointCoordinates_rotated[1] = startingPointCoordinates_rotated[1] + midpoint[1];
	    endingPointCoordinates_rotated[0] = endingPointCoordinates_rotated[0] + midpoint[0];
	    endingPointCoordinates_rotated[1] = endingPointCoordinates_rotated[1] + midpoint[1];
	    
	    //array to stores the rotated starting and ending points of the mirror
	    Point2D.Double[]rotatedPoints= {new Point2D.Double(startingPointCoordinates_rotated[0],startingPointCoordinates_rotated[1]),new Point2D.Double(endingPointCoordinates_rotated[0],endingPointCoordinates_rotated[1])};

	    return rotatedPoints;
	}
}
