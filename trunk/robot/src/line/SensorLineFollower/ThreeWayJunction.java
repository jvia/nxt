/*
 * Attempts to detect a three way junction (i.e. path forwards, left and right),
 * and turn a random direction.
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
public class ThreeWayJunction implements Behavior {

    LightSensor rightLight;
    LightSensor leftLight;
    ColorSensor color;
    DifferentialPilot pilot;

    public ThreeWayJunction(LightSensor left, LightSensor right, ColorSensor color, DifferentialPilot p) {

        rightLight = right;
        leftLight = left;
        this.color = color;
        pilot = p;

    }

    /**
     * Takes control if all three sensors detect a 'black' value as a return from
     * the method which returns a light or colour value.
     * @return
     */
    public boolean takeControl() {

        return (rightLight.getLightValue() < 40 
                && leftLight.getLightValue() < 40 
                && color.getRawRed() < 30000);

    }

    public void action() {

        /*
         * The commented out code is an attempt at turning at a junction, but seems
         * not to work particularly well due to the other behaviours taking over
         * as soon as their take control method returns true.
         */

//        double random = Math.random();
//
//        if (random < 0.33)  {
//            Motor.B.backward();
//            Motor.A.backward();
//        } else if (random < 0.66){
//           p.rotate(90);
//        } else {
//          p.rotate(-90)
//        }


        /**
         * Ignores the junction and keeps on going.
         */
        Motor.A.backward();
        Motor.B.backward();

    }

    public void suppress() {

        Motor.A.stop();
        Motor.B.stop();
    }
}
