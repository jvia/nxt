package wallDetector;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * A class to represent the behavior of hitting a wall.
 * 
 * Exercise 2, Part 2
 * @author Jeremiah Via, Michal Staniaszek
 */
public class HitWall implements Behavior
{
    private TouchSensor touch;
    private DifferentialPilot pilot;

    /**
     * Creates a HitWall behavior.
     * requires: a sensor to detect one
     * ensures: a HitWall behavior is created
     */
    public HitWall()
    {
        touch = new TouchSensor(SensorPort.S1);
        pilot = new DifferentialPilot(RobotConstants.WHEEL_DIAMETER,
                                      RobotConstants.TRACK_WIDTH, Motor.B, Motor.A);
    }

    /**
     *
     * @return
     */
    public boolean takeControl()
    {   return !touch.isPressed();  }

    /**
     *
     */
    public void action()
    {
        pilot.forward();
        while(pilot.isMoving())
            Thread.yield();
    }

    /**
     * 
     */
    public void suppress()
    {   pilot.stop();   }
}