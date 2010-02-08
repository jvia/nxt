/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectFollowing;

import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.Pilot;

/**
 * 
 * @author Jeremiah Via
 */
public class StayStill extends CenterBehaviors {

    public StayStill(NXTCam cam, Pilot pilot) { super(cam, pilot); }

    public boolean takeControl() {
        if (cam.getNumberOfObjects() > 0) {
            r = cam.getRectangle(0);
        }
          

        return ( r.getCenterX() < X_CENTER_OF_IMAGE + OFFSET
                || r.getCenterX() > X_CENTER_OF_IMAGE - OFFSET)
                && (r.getCenterY() < Y_CENTER_OF_IMAGE + OFFSET
                || r.getCenterY() > Y_CENTER_OF_IMAGE - OFFSET);
    }

    public void action() { pilot.stop(); }
    public void suppress() { /* do nothing*/ }
}
