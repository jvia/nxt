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
public class MoveForward extends Move
{

    /**
     * 
     * @param distance
     * @param pilot
     * @param ultrasonic
     */
    public MoveForward(int distance, Pilot pilot, UltrasonicSensor ultrasonic) {
        super(distance, pilot, ultrasonic);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean takeControl() {
        return (getUltrasonicSensor().getDistance() + ERROR_THRESHOLD > getDistance()? true : false);
    }

    /**
     *
     */
    @Override
    public void action() { pilot.forward(); }



}
