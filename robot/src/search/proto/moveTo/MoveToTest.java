/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.proto.moveTo;

import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class MoveToTest {

    public static void main(String[] args) {
        Point currentPoint = new Point(0, 0);
        Point goalPoint = new Point(3, 4);
        Pilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                     RobotConstants.TRACK_WIDTH,
                                     RobotConstants.leftMotor,
                                     RobotConstants.rightMotor, true);
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);

        //Button.waitForPress();


        Behavior findLine = new FindLine(left, right, pilot, currentPoint,
                                         goalPoint);
        Behavior forward = new Forward(left, right, pilot, currentPoint,
                                       goalPoint);
        Behavior fuck = new GeneratePoints(left, right, pilot, currentPoint,
                                           goalPoint);
        Behavior pussy = new Celebrate(left, right, pilot, currentPoint,
                                       goalPoint);

        Behavior[] shit = {findLine, forward, fuck, pussy};

        Arbitrator arrr = new Arbitrator(shit);
        arrr.start();

        Sound.playTone(600, 600);
        System.out.println("["+currentPoint.x + ", " + currentPoint.y+"]");

      //  Button.waitForPress();
    }
}


