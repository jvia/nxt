package distance;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.subsumption.Behavior;

/**
 * Abstract class that abstract away the common functionality of movements.
 *
 * Exercise 03, Part 01
 * 4 February 2010
 * @author Jeremiah Via, Michal Staniazek
 */
public abstract class Move implements Behavior {

    /** Compensates for noise in ultrasonic sensor readings. */
    public final static int ERROR_THRESHOLD = 3;

    /** Pilot used to move robot */
    protected Pilot pilot;

    /** Sensor used to measure distance */
    protected UltrasonicSensor ultrasonic;

    /** The distanc we want to maintain +/- the error threshold */
    protected int distance;
    
    /**
     * Constructor initializes Move behavior.
     * @param distance distance to maintain
     * @param pilot pilot used to manuever robot
     * @param ultrasonic ultrasonic sensor to measure distance
     */
    public Move(int distance, Pilot pilot, UltrasonicSensor ultrasonic) {
        this.pilot = pilot;
        this.ultrasonic = ultrasonic;
        this.distance = distance;

    }

    /** Basic behavior that simply stops the robot */
    public void suppress() { pilot.stop(); }

}
