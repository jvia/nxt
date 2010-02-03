/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distance;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.subsumption.Behavior;

/**
 *
 * @author Jeremiah Via, Michal Staniaszek
 */
public abstract class Move implements Behavior {
    /**
     *
     */
    public final static int ERROR_THRESHOLD = 3;
    /**
     *
     */
    protected Pilot pilot;
    /**
     *
     */
    protected UltrasonicSensor ultrasonic;
    /**
     *
     */
    protected int distance;

    /**
     *
     * @param distance
     * @param pilot
     * @param ultrasonic
     */
    public Move(int distance, Pilot pilot, UltrasonicSensor ultrasonic) {
        this.pilot = pilot;
        this.ultrasonic = ultrasonic;
        this.distance = distance;

    }

    /**
     *
     * @return
     */
    public int getDistance() {
        return distance;
    }

    /**
     *
     * @return
     */
    public UltrasonicSensor getUltrasonicSensor() {
        return ultrasonic;
    }

    /**
     *
     * @return
     */
    public Pilot getTachoPilot() {
        return pilot;
    }

    /**
     *
     * @param distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     *
     * @return
     */
    public abstract boolean takeControl();

    /**
     *
     */
    public abstract void action();

    /**
     *
     */
    public void suppress() {
        pilot.stop();
    }
}
