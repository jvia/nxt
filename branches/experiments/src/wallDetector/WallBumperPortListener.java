package wallDetector;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.proposal.DifferentialPilot;

/**
 * Exercise 2, Part 2
 * @author Jeremiah Via, Michal Staniaszek
 */
public class WallBumperPortListener implements SensorPortListener
{
    static boolean forwards = true;
    WallBumperPortListener listen = new WallBumperPortListener();   

    /**
     * Run program
     * @param args
     */
    public static void main(String[] args)
    {
        DifferentialPilot pilot = new DifferentialPilot(55f, 112f, Motor.A, Motor.B);

        while (true)
        {
            if (forwards != true)
            {
                pilot.travel(-100);
                pilot.rotate(90);
                forwards = true;
            } 
            else
            {
                Motor.A.forward();
                Motor.B.forward();
                Thread.yield();
            }
        }
    }

    /**
     * Changes forward to false.
     * @param arg0 not used
     * @param arg1 not used
     * @param arg2 not used
     */
    public void stateChanged(SensorPort arg0, int arg1, int arg2)
    {
        forwards = false;
    }
}
