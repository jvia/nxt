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
 * 
 * @author Jeremiah Via
 */
public abstract class CenterBehaviors implements Behavior {
    public final static int OFFSET = 15;
    //public final static int MAX_AREA = 3000;
    public final static int IMAGE_WIDTH = 176;
    public final static int IMAGE_HEIGHT = 144;
    public final static int X_CENTER_OF_IMAGE = IMAGE_WIDTH / 2;
    public final static int Y_CENTER_OF_IMAGE = IMAGE_HEIGHT / 2;


    protected Pilot pilot;
    protected NXTCam cam;
    protected Rectangle r;
    
    public CenterBehaviors(NXTCam c, Pilot pilot) {
        this.pilot = pilot;
        cam = c;
        r = new Rectangle(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);        
    }

    
}
