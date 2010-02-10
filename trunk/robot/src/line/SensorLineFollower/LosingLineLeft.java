/*
 * This behaviour checks the left light sensor for a threshold 'black' value.
 * When this value is detected, the right motor's speed decreases, causing
 * the robot to turn right.
 */
package line.SensorLineFollower;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 * Author Michal Staniaszek
 */
public class LosingLineLeft implements Behavior {

    LightSensor leftLight;
    DifferentialPilot pilot;

    /**
     * Constructor for this behaviour.
     * @param p A differential pilot
     * @param leftSensor The left light sensor
     */
    public LosingLineLeft(DifferentialPilot p, LightSensor leftSensor) {
        leftLight = leftSensor;
        pilot = p;
    }

    /**
     * Takes control when the light value of the left light sensor drops below 40.
     * @return
     */
    public boolean takeControl() {
        return (leftLight.getLightValue() < 40);
    }

    /**
     * Increases the speed of the right wheel in order to re-align the robot with the line.  The commented out
     * code can also be used if sharper turns are required.
     */
    public void action() {

        Motor.A.backward();
        Motor.B.backward();

        while (leftLight.getLightValue() < 40) {
            Motor.B.setSpeed(Motor.B.getSpeed() - 60);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println("Exception occurred");
            }
        }

    }

    /**
     *
     */
    public void suppress() {

        Motor.A.stop();
        Motor.B.stop();

    }
}
