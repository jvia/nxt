/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distance;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pilot;
/**
 * 
 * @author Jeremiah Via
 */
public class SitStill extends Move
{

    /**
     *
     * @return
     */
    @Override
    public boolean takeControl() {
        return ultrasonic.getDistance() < distance + ERROR_THRESHOLD &&
                ultrasonic.getDistance() > distance - ERROR_THRESHOLD;

    }

    /**
     *
     * @param distance
     * @param pilot
     * @param ultrasonic
     */
    public SitStill(int distance, Pilot pilot, UltrasonicSensor ultrasonic) {
        super(distance, pilot, ultrasonic);
    }

    /**
     * 
     */
    @Override
    public void action() {
        pilot.stop();
    }

}
