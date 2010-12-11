package maze;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.SimpleNavigator;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * Exercise 2, Part 3
 * Due: 21 January 2010
 * @author Jeremiah Via, Michal Staniaszek
 */
public class FindWall implements Behavior
{
    private Pilot pilot;
    private SimpleNavigator navigator;
    private TouchSensor touchSensor;

    /**
     * Creates the FindWall behavior.
     * requires: a builr robot with touch sensor and motors plugged
     *           into ports A and B
     * ensures: FindWall behavior is created
     */
    public FindWall()
    {
        pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                RobotConstants.TRACK_WIDTH, Motor.A, Motor.B);
        navigator = new SimpleNavigator(pilot);
        touchSensor = new TouchSensor(SensorPort.S1);
    }

    /**
     * Method to determine whether or not this behavior is to take control.
     * This behavior will be in effect until a wall is found, in others words,
     * until the touch sensor is pressed.
     * requires: a touch sensor
     * ensures: boolean value is returned
     * @return true if touch, false otherwise
     */
    public boolean takeControl()
    {   return !touchSensor.isPressed();    }

    /**
     * Actions to be taken by the robot when behavior is in effect. Makes the
     * robot make a sharp right turn at 60 degrees per secon, up to a 90 degree
     * arcs. If no wall is detected, this action will keep taking place giving
     * the appearance of a circle be driven when in fact it is actually four
     * 90 degree arcs in repeated succession.
     * requires: same requirements as the constructor
     * ensures: action will take place
     */
    public void action()
    {   navigator.steer(60, 90, true);  }

    /**
     * Stops the robot from moving when it's takeAction condition is no longer
     * true.
     * requires: same requirements as constructor
     * ensures: robot will stop
     */
    public void suppress()
    {   navigator.stop();   }
}
