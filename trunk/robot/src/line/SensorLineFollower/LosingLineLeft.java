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

    public LosingLineLeft(DifferentialPilot p, LightSensor leftSensor) {
        leftLight = leftSensor;
        pilot = p;
    }

    public boolean takeControl() {
        return (leftLight.getLightValue() < 40);
    }

    public void action() {

        Motor.A.backward();
        Motor.B.backward();

        while (leftLight.getLightValue() < 40) {
            //Motor.A.setSpeed(Motor.A.getSpeed() - 50);
            //Motor.B.setSpeed(Motor.B.getSpeed() - 15);
            Motor.B.setSpeed(Motor.B.getSpeed() - 60);
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
