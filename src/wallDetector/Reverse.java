package wallDetector;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * A class to represent reversing when a touch sensor is encountered.
 * 
 * Exercise 2, Part 2
 * @author Jeremiah Via, Michal Staniaszek
 */
public class Reverse implements Behavior
{
    TouchSensor touch;
    DifferentialPilot pilot;

    /**
     * Creates a Reverse behavior.
     * ensures: behavior is created
     * requires: nothing
     */
    public Reverse()
    {
        touch = new TouchSensor(SensorPort.S1);
        pilot = new DifferentialPilot(RobotConstants.WHEEL_DIAMETER,
                                      RobotConstants.TRACK_WIDTH, Motor.B, Motor.A);
    }

    /**
     * Determines whether or not to take control if the touch sensor is
     * pressed.
     * ensures: boolean value representing sensor state is returned
     * requires: a sensor on the robot
     * @return true if sensor is down, false otherwise
     */
    public boolean takeControl()
    {   return touch.isPressed();   }

    /**
     * Backs up and turns if touch sensor is pressed.
     * ensures: action will be performed if control is taken
     * requires: a sensor on the robot
     */
    public void action()
    {
        pilot.travel(-150);
        pilot.rotate(90);
    }

    /**
     * Stops the robot when the takeControl condition is no longer valid.
     * ensures: behavior is supressed
     * requires: touch sensor on robot
     */
    public void suppress()
    {   pilot.stop();   }
}
