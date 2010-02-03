/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectFollowing;

import java.awt.Rectangle;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.subsumption.Behavior;

/**
 * Abstract class that encapsulates common fnuctionality of object
 * following behaviors.
 *
 * Exercise 03, Part 03
 * 04 February 2010
 * @author Jeremiah Via
 */
public abstract class CenterBehaviors implements Behavior {

    /** Error off dead center allowed  */
    public final static int ERROR_THRESHOLD = 15;

    /** Width of the camera image, in pixels */
    public final static int IMAGE_WIDTH = 176;

    /** Height of the camera image, in pixels */
    public final static int IMAGE_HEIGHT = 144;

    /** Center of the image on the X-axis, in pixels */
    public final static int X_CENTER_OF_IMAGE = IMAGE_WIDTH / 2;

    /** Center of the image on the Y-axis, in pixels */
    public final static int Y_CENTER_OF_IMAGE = IMAGE_HEIGHT / 2;

    /** Pilot used to move robot */
    protected Pilot pilot;
    /**
     * Camera used to detect objects */
    protected NXTCam cam;

    /** Reference to last rectangle observed by the camera  */
    protected Rectangle rectangle;
    
    /**
     * Constructs behavior
     * @param cam NXTCam used by the robot
     * @param pilot pilot that controls robot
     */
    public CenterBehaviors(NXTCam cam, Pilot pilot) {
        this.pilot = pilot;
        this.cam = cam;
        rectangle = new Rectangle(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
}