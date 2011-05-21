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
public class FindObject extends CenterBehaviors {
    int spinCount = 0;

    public FindObject(NXTCam cam, Pilot pilot) {
        super(cam, pilot);
    }

    public boolean takeControl() {
        return cam.getNumberOfObjects() == 0;
    }

    public void action() {
        // r was last seen object, go in that direction
        /*
        if (spinCount++ < 3)
        pilot.rotate(90, true);
        else{
        pilot.arc(500 , 90, true); // 0.5M radius
        spinCount = 0;
        }
         * 
         */
    }

    public void suppress() {
        pilot.stop();
    }
}
