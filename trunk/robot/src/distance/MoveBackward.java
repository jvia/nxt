/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distance;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pilot;

/**
 *
 * Exercise 03, Part 01
 * 4 February 2010
 * @author Jeremiah Via, Michal Staniazek
 */
public class MoveBackward extends Move {
    /**
     * 
     * @param distance
     * @param pilot
     * @param us
     */
    public MoveBackward(int distance, Pilot pilot, UltrasonicSensor us) {
        super(distance, pilot, us);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean takeControl() {
        return (ultrasonic.getDistance() - ERROR_THRESHOLD < distance ? true : false);

    }

    /**
     *
     */
    @Override
    public void action() { pilot.backward(); }
}
