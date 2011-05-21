/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.proto;

import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class LineFollowerMain {

    public static final int MAX_BLACK_VALUE = 43;


    public static void main(String[] args) {
        Point currentPoint = new Point(0, 0);
        Point goalPoint = new Point(3, 4);
        Pilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                     RobotConstants.TRACK_WIDTH,
                                     RobotConstants.leftMotor,
                                     RobotConstants.rightMotor, true);
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);


        Button.waitForPress();

        Button.ENTER.addButtonListener(new ButtonListener() {
            public void buttonPressed(Button b) { System.exit(0); }
            public void buttonReleased(Button b) { }
        });





        while ( true ){
            if (left.getLightValue() <= MAX_BLACK_VALUE
                    && right.getLightValue() > MAX_BLACK_VALUE){
                pilot.steer(45, 90, true);
            }
            else if (right.getLightValue() <= MAX_BLACK_VALUE &&
                    left.getLightValue() > MAX_BLACK_VALUE){
                pilot.steer(-45, 90, true);
            }
            else if (left.getLightValue() > MAX_BLACK_VALUE
                    && right.getLightValue() > MAX_BLACK_VALUE){
                pilot.forward();
                
            }
            else if (left.getLightValue() <= MAX_BLACK_VALUE
                    && right.getLightValue() <= MAX_BLACK_VALUE){
                pilot.stop();
            }
        }

//        while (true) {
//            int angle = 0;
//            int turnRate = 0;
//
//
//            // iff only one sensor is on black
//            if (left.getLightValue() <= MAX_BLACK_VALUE
//                ^ right.getLightValue() <= MAX_BLACK_VALUE) {
//                if (left.getLightValue() <= MAX_BLACK_VALUE) {
//                    angle = 90;
//                    turnRate = 30;
//                }
//                else {
//                    angle = 90;
//                    turnRate = 30;
//                }
//            }
//            // if both sensors detect line
//            else if (left.getLightValue() <= MAX_BLACK_VALUE && right.
//                    getLightValue() <= MAX_BLACK_VALUE) {
//                angle = 90;
//                turnRate = 90;
//            }
//            // both on table
//            else if (left.getLightValue() > MAX_BLACK_VALUE && right.
//                    getLightValue() > MAX_BLACK_VALUE) {
//                angle = 0;
//                turnRate = 0;
//            }
//
//            pilot.steer(turnRate, angle, true);
//
//
//        }
    }
}
