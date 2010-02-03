/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objectFollowing;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * 
 *
 * Exercise 03, Part 03
 * 04 February 2010
 * @author Jeremiah Via
 */
public class ObjectFollower {
    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Pilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER, RobotConstants.TRACK_WIDTH,
                                     RobotConstants.leftMotor, RobotConstants.rightMotor);
        pilot.setMoveSpeed(720);
        
        NXTCam cam = new NXTCam(SensorPort.S1);
        cam.setTrackingMode(NXTCam.OBJECT_TRACKING);
        cam.sortBy(NXTCam.SIZE);
        cam.enableTracking(true);

        Button.ENTER.addButtonListener(new ButtonListener() {
            public void buttonPressed(Button button) { System.exit(0); }
            public void buttonReleased(Button button) {}
        });

        Behavior center = new CenterObject(cam, pilot);
        Behavior stayStill = new StayStill(cam, pilot);
        

        Behavior[] behaviors = { stayStill, center};

        Arbitrator arby = new Arbitrator(behaviors);
        arby.start();

    }
}
