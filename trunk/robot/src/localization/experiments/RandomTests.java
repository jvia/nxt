/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package localization.experiments;

import java.io.PrintStream;
import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.addon.ColorSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.nxt.comm.RConsole;
import lejos.robotics.proposal.DifferentialPilot;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class RandomTests {

    public static void main(String[] args) throws InterruptedException {
        OpticalDistanceSensor us = new OpticalDistanceSensor(SensorPort.S1);
        final DifferentialPilot pilot = new DifferentialPilot(
                RobotConstants.WHEEL_DIAMETER / 10,
                RobotConstants.TRACK_WIDTH / 10,
                RobotConstants.leftMotor,
                RobotConstants.rightMotor,
                true);
        final ColorSensor s = new ColorSensor(SensorPort.S3);


        RConsole.openBluetooth(60000);
        System.setOut(new PrintStream(RConsole.openOutputStream()));
     //   pilot.forward();
        while (!Button.ENTER.isPressed()) {
            System.out.println(s.getColorNumber());
            Thread.sleep(300);
        }
        RConsole.close();
    }
}
