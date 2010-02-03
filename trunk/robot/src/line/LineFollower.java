/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package line;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class LineFollower {

    NXTCam cam;
    TachoPilot pilot;
    UltrasonicSensor ultrasonic;

    public LineFollower() {
        cam = new NXTCam(SensorPort.S1);
        cam.setTrackingMode(NXTCam.LINE_TRACKING);
        cam.sortBy(NXTCam.NO_SORTING);
        cam.enableTracking(true);


        pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                               RobotConstants.TRACK_WIDTH, Motor.B, Motor.A,
                               true);
        ultrasonic = new UltrasonicSensor(SensorPort.S2);

        Button.ENTER.addButtonListener(new ButtonListener() {

            public void buttonPressed(Button button) {
                System.exit(0);
            }

            public void buttonReleased(Button button) {
            }

        });


    }

    public void run() {
        CenterLine center = new CenterLine(cam, pilot, ultrasonic);
        Forward forward = new Forward(cam, pilot, ultrasonic);
        FindLine find = new FindLine(cam, pilot, ultrasonic);
        Behavior[] b = {find, forward, center};
        Arbitrator arb = new Arbitrator(b);
        arb.start();
    }

    public static void main(String[] args) {
        LineFollower lineFollower = new LineFollower();
        lineFollower.run();
    }

}
