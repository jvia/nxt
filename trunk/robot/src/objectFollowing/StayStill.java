package objectFollowing;

import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.Pilot;

/**
 * Describes when the robot should not move.
 *
 * Exercise 03, Part 03
 * 04 February 2010
 * @author Jeremiah Via
 */
public class StayStill extends CenterBehaviors {

    /**
     * Constructs behavior
     * @param  NXTCam to use
     * @param pilot moves the robot
     */
    public StayStill(NXTCam cam, Pilot pilot) {
        super(cam, pilot);
    }

    /**
     * Determines if the robot should take control based on where the object is
     * in relation to the camera.
     * @return true if object is in the center with regard to the error threshold
     */
    public boolean takeControl() {
        // only update object if robot can see one
        if (cam.getNumberOfObjects() > 0) {
            rectangle = cam.getRectangle(0);
        }

        // determine if object is in the center
        return (rectangle.getCenterX() < X_CENTER_OF_IMAGE + ERROR_THRESHOLD
             || rectangle.getCenterX() > X_CENTER_OF_IMAGE - ERROR_THRESHOLD)
             &&(rectangle.getCenterY() < Y_CENTER_OF_IMAGE + ERROR_THRESHOLD
             || rectangle.getCenterY() > Y_CENTER_OF_IMAGE - ERROR_THRESHOLD);
    }

    /**
     * Stops the robot
     */
    public void action() {
        pilot.stop();
    }

    /**
     * No behavior to suppress, action method is a finite action.
     */
    public void suppress() { /* do nothing*/ }
}