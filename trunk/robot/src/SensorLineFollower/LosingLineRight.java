/*
 * This behaviour checks the right light sensor for a threshold 'black' value.
 * When this value is detected, the left motor's speed decreases, causing
 * the robot to turn left.
 */
package SensorLineFollower;

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

    public LosingLineRight(DifferentialPilot p, LightSensor rightSensor) {
        rightLight = rightSensor;
        pilot = p;
    }

    public boolean takeControl() {
        return (rightLight.getLightValue() < 40);
    }

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

    public void suppress() {
        Motor.A.stop();
        Motor.B.stop();

    }
}
