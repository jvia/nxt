package distance;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pilot;

/**
 * Describes the behavior of moving forward.
 *
 * Exercise 03, Part 01
 * 4 February 2010
 * @author Jeremiah Via, Michal Staniazek
 */
public class MoveForward extends Move {

    /**
     * Constructs behavior
     * @param distance distance to maintain between object and robot
     * @param pilot pilot used to move the robot
     * @param us ultrasonic sensor to measure distance
     */
    public MoveForward(int distance, Pilot pilot, UltrasonicSensor ultrasonic) {
        super(distance, pilot, ultrasonic);
    }

    /**
     * Decides if behavior should take control
     * @return true if distance + ERROR_THRESHOLD is greater than the
     * distance measured
     */
    @Override
    public boolean takeControl() {
        return (ultrasonic.getDistance() + ERROR_THRESHOLD > distance)
               ? true
               : false;
    }

    /** Moves robot forward while take control condition is true. */
    @Override
    public void action() {
        pilot.forward();
    }
}