/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distance;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pilot;

/**
 * 
 * 
 * Exercise 03, Part 01
 * 4 February 2010
 * @author Jeremiah Via, Michal Staniazek
 */
public class SitStill extends Move {

    /**
     * Constructs behavior
     * @param distance distance to maintain between object and robot
     * @param pilot pilot used to move the robot
     * @param us ultrasonic sensor to measure distance
     */
    public SitStill(int distance, Pilot pilot, UltrasonicSensor ultrasonic) {
        super(distance, pilot, ultrasonic);
    }

    /**
     * Decides if behavior should take contorl
     * @return true if distance - ERROR_THRESHOLD is less than measured
     * distance and less than distance + ERROR_THRESHOLD, false otherwise
     */
    @Override
    public boolean takeControl() {
        return ultrasonic.getDistance() < distance + ERROR_THRESHOLD && ultrasonic.
                getDistance() > distance - ERROR_THRESHOLD;

    }

    /** Stops the robot. */
    @Override
    public void action() {
        pilot.stop();
    }

}
