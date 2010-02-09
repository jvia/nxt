/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectFollowing;

import javax.microedition.lcdui.Graphics;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.Pilot;

/**
 * 
 * @author Jeremiah Via
 */
public class CenterObject extends CenterBehaviors {
    Graphics g = new Graphics();
    long lastTimeSeen = 0;

    public CenterObject(NXTCam cam, Pilot pilot) {
        super(cam, pilot);
    }

    public boolean takeControl() {
        if (cam.getNumberOfObjects() > 0) {
            r = cam.getRectangle(0);
        }

        return (r.getCenterX() > X_CENTER_OF_IMAGE + OFFSET
                || r.getCenterX() < X_CENTER_OF_IMAGE - OFFSET
                && r.getCenterY() > Y_CENTER_OF_IMAGE + OFFSET
                || r.getCenterY() < Y_CENTER_OF_IMAGE - OFFSET);
    }

    public void action() {
      //  r = cam.getRectangle(0);

        // calculote error off center x,y
        float turnRate = (float) (r.getCenterX() - X_CENTER_OF_IMAGE);
        float angle = (float) r.getCenterY() - Y_CENTER_OF_IMAGE;

        // if the object is too close, we want to be able to back up so we need to rotate in reverse
        if (angle < 0) {
            turnRate = -turnRate;
        }


        pilot.steer(turnRate, angle, true);
    }

    public void suppress() {
        pilot.stop();
    }
}
