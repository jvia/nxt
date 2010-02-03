/*
 * This behaviour checks the right light sensor for a threshold 'black' value.
 * When this value is detected, the left motor's speed decreases, causing
 * the robot to turn left.
 */
package line.SensorLineFollower;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 * Author Michal Staniaszek
 */
public class LosingLineRight implements Behavior {

    LightSensor rightLight;
    DifferentialPilot pilot;

    /**
     * Constructor for this behaviour.
     * @param p A differential pilot
     * @param rightSensor The right light sensor
     */
    public LosingLineRight(DifferentialPilot p, LightSensor rightSensor) {
        rightLight = rightSensor;
        pilot = p;
    }

    /**
     * Takes control when the light value of the right light sensor drops below 40.
     * @return
     */
    public boolean takeControl() {
        return (rightLight.getLightValue() < 40);
    }

    /**
     * Increases the speed of the left wheel in order to re-align the robot with the line.  The commented out
     * code can also be used if sharper turns are required.
     */
    public void action() {

        Motor.A.backward();
        Motor.B.backward();

        while (rightLight.getLightValue() < 40) {
            //Motor.B.setSpeed(Motor.B.getSpeed() + 50);
            //Motor.A.setSpeed(Motor.A.getSpeed() - 15);
            Motor.A.setSpeed(Motor.A.getSpeed() - 60);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
        }

    }

    /**
     * Suppresses this behaviour, stopping both motors.
     */
    public void suppress() {
        Motor.A.stop();
        Motor.B.stop();

    }
}
