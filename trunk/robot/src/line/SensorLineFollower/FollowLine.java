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

    public FollowLine(ColorSensor colour) {
        this.colour = colour;
    }

    public boolean takeControl() {       
        return(colour.getRawRed() < 30000);
    }

    public void action() {
        Motor.A.setSpeed(500);
        Motor.B.setSpeed(500);
        Motor.A.backward();
        Motor.B.backward();
    }

    public void suppress() {
        Motor.A.stop();
        Motor.B.stop();
    }

}
