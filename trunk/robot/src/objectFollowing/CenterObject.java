/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectFollowing;

import javax.microedition.lcdui.Graphics;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.Pilot;

/**
 * Behavior that attempts to keep the object centered in the robots field
 * of vision.
 *
 * Exercise 03, Part 03
 * 04 February 2010
 * @author Jeremiah Via
 */
public class CenterObject extends CenterBehaviors {

    /**
     * Constructs the behavior
     * @param cam
     * @param pilot
     */
    public CenterObject(NXTCam cam, Pilot pilot) {
        super(cam, pilot);
    }

    /**
     * Determines if the behavior should take control of the robot.
     * @return true if the object is not in the center +/- the error threshold
     */
    public boolean takeControl() {
        // robot sees a rectangle, so we can updte the last rectangle we saw
        if (cam.getNumberOfObjects() > 0) {
            rectangle = cam.getRectangle(0);
        }

        // checks to make sure the object is in the center +/- the error
        // threshold in either the X or Y axis
        return (rectangle.getCenterX() > X_CENTER_OF_IMAGE + ERROR_THRESHOLD
             || rectangle.getCenterX() < X_CENTER_OF_IMAGE - ERROR_THRESHOLD)
             &&(rectangle.getCenterY() > Y_CENTER_OF_IMAGE + ERROR_THRESHOLD
             || rectangle.getCenterY() < Y_CENTER_OF_IMAGE - ERROR_THRESHOLD);
    }
    
    /**
     * Actions attempts to calculate how the robot must steer based on the
     * position of the last object seen.
     */
    public void action() {
       // calculote error off center x,y
        float turnRate = (float) (rectangle.getCenterX() - X_CENTER_OF_IMAGE);
        float angle = (float) rectangle.getCenterY() - Y_CENTER_OF_IMAGE;

        // if the object is too close, we want to be able to back up so we need to rotate in reverse
        if (angle < 0) {
            turnRate = -turnRate;
        }

        pilot.steer(turnRate, angle, true);
    }

    /** Stops the robot */
    public void suppress() {
        pilot.stop();
    }
}
