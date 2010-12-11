package maze;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;

/**
 * Exercise 2, Part 3
 * Due: 21 January 2010
 * @author Jeremiah Via, Michal Staniaszek
 */
public class Reposition implements Behavior
{
    private TouchSensor touchSensor;

    /**
     * Creates reposition behavior.
     * requires: built robot with touch sensor and motors attached to
     *           to ports A and B
     * ensures: behavior will be created
     */
    public Reposition()
    {   touchSensor = new TouchSensor(SensorPort.S1);   }

    /**
     * Determines whether or no this behavior should take control. Will take
     * over when robot is agianst a wall.
     * requires: same requirements as constructor
     * ensnures: a boolean will be returned
     * @return true if agianst wall, fals otherwise
     */
    public boolean takeControl()
    {
        return touchSensor.isPressed();
    }

    /**
     * Action rotates the robot in place by spinning the left wheel for
     * 0.2 seconds.
     * requires: same requirements and constructor
     * ensures: action will take place if takeControl is true
     */
    public void action()
    {
        Motor.B.backward();
        try{ Thread.sleep(200); }
        catch(Exception e){}
        Motor.B.stop();
    }

    /**
     * Nothing to suppresse, so not used.
     */
    public void suppress()
    {   /* nothing to suppress */ }
}
