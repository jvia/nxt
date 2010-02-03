/*
 *Attempts to detect a right junction (paths forwards and right) and choose one.
 */

package SensorLineFollower;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 * Author Michal Staniaszek
 */
public class RightJunction implements Behavior{
ColorSensor colour;
    LightSensor right;
    DifferentialPilot p;

    public RightJunction(ColorSensor colour, LightSensor right, DifferentialPilot p) {
        this.colour = colour;
        this.right = right;
        this.p = p;
    }

    /**
     * Takes control if the right light sensor and colour sensor detect black values.
     * @return
     */
    public boolean takeControl() {
        return(right.getLightValue() < 40 && colour.getRawRed() < 30000);
    }

    public void action() {

        /*
         * The commented out code is an attempt at turning at a junction, but seems
         * not to work particularly well due to the other behaviours taking over
         * as soon as their take control method returns true.  If it worked, it
         * would turn the robot right 50% of the time, otherwise it would ignore the junction.
         */

//        double random = Math.random();
//
//        if (random < 0.5)  {
//            Motor.B.backward();
//            Motor.A.backward();
//        } else {
//            p.rotate(-90);
//        }

        /**
         * Turns the robot right.
         */
        Motor.B.backward();
        Motor.A.forward();

    }

    public void suppress() {
        Motor.B.stop();
        Motor.A.stop();
    }
}