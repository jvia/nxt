package wallDetector;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.proposal.DifferentialPilot;

/**
 * Exercise 2, Part 2
 * @author Jeremiah Via, Michal Staniaszek
 */
public class WallBumperMain
{

    /**
     * Moves the robot forwards until the touch sensor is pressed.  When this happens,
     * the robot reverses and turns 90 degrees, and repeats this process.
     * @param args
     */
    public static void main(String[] args)
    {
        TouchSensor touch = new TouchSensor(SensorPort.S1);
        DifferentialPilot pilot = new DifferentialPilot(55f, 112f, Motor.A, Motor.B);

        while (true)
        {
            if (touch.isPressed())
            {
                pilot.travel(-100);
                pilot.rotate(90);
            } 
            else
            {
                Motor.A.forward();
                Motor.B.forward();
                Thread.yield();
            }
        }
    }
}