package distance;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pilot;

/**
 * Describes the behavior of moving backward.
 * 
 * Exercise 03, Part 01
 * 4 February 2010
 * @author Jeremiah Via, Michal Staniazek
 */
public class MoveBackward extends Move {

    /**
     * Constructs behavior
     * @param distance distance to maintain between object and robot
     * @param pilot pilot used to move the robot
     * @param us ultrasonic sensor to measure distance
     */
    public MoveBackward(int distance, Pilot pilot, UltrasonicSensor us) {
        super(distance, pilot, us);
    }

    /**
     * Decides when the behavior should take contorl.
     * @return It till return true if the distance - ERROR_THRESHOLD is greater
     * then the distance specified.
     */
    @Override
    public boolean takeControl() {
        return (ultrasonic.getDistance() - ERROR_THRESHOLD < distance)
               ? true
               : false;
    }

    /** Move the robot backwards while the takeControl condition remains true */
    @Override
    public void action() {
        pilot.backward();
    }

}
