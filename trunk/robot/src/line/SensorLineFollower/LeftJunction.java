/*
 *Attempts to detect a left junction (paths forwards and left) and choose one
 */
package line.SensorLineFollower;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 * Author Michal Staniaszek
 */
public class LeftJunction implements Behavior {

    ColorSensor colour;
    LightSensor left, right;
    DifferentialPilot p;

    /**
     * Constructor for this behaviour.
     * @param colour The colour sensor (centre sensor)
     * @param left The left light sensor
     * @param p A differential pilot
     */
    public LeftJunction(ColorSensor colour, LightSensor left, DifferentialPilot p) {
        this.colour = colour;
        this.left = left;
        this.p = p;
    }

    /**
     * Takes control if the left light sensor and colour sensor detect black values.
     * @return
     */
    public boolean takeControl() {
        return (left.getLightValue() < 40 && colour.getRawRed() < 30000);
    }

    /**
     * Turns the robot left at a left junction.
     */
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
//            p.rotate(90);
//        }


        /**
         * Turns the robot left.
         */
        Motor.B.forward();
        Motor.A.backward();
    }

    /**
     * Suppresses this behaviour, stopping both motors.
     */
    public void suppress() {
        Motor.B.stop();
        Motor.A.stop();
    }
}
