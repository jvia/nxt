/*
 * This behaviour moves the robot forwards if the colour sensor returns a
 * value which indicates black.  Note that this behaviour also resets the
 * speed values of the motors so that the robot does not stop if it loses the
 * line too many times.
 */

package line.SensorLineFollower;

import lejos.nxt.Motor;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.subsumption.Behavior;

/*
 * Author Michal Staniaszek
 */
public class FollowLine implements Behavior{

    ColorSensor colour;

    /**
     * Constructor for this behaviour.
     * @param colour The colour sensor (central sensor)
     */
    public FollowLine(ColorSensor colour) {
        this.colour = colour;
    }

    /**
     * Takes control when the raw red value of the colour sensor drops below 30,000.
     * @return
     */
    public boolean takeControl() {       
        return(colour.getRawRed() < 30000);
    }

    /**
     * Resets the speed of the motors to a base value (important), and then moves forwards.
     */
    public void action() {
        Motor.A.setSpeed(500);
        Motor.B.setSpeed(500);
        Motor.A.backward();
        Motor.B.backward();
    }

    /**
     * Suppresses this behaviour, stopping both motors.
     */
    public void suppress() {
        Motor.A.stop();
        Motor.B.stop();
    }

}
