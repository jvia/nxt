/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

/*
 * Author Michal Staniaszek
 */
public class AdjustToCentreLine implements Behavior {

    LightSensor leftSensor;
    LightSensor rightSensor;

    public AdjustToCentreLine(LightSensor leftSensor, LightSensor rightSensor) {
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
    }



    public boolean takeControl() {
        return (leftSensor.getLightValue() < JunctionReached.BLACK ^ rightSensor.getLightValue() < JunctionReached.BLACK);
    }

    public void action() {

        Motor.A.backward();
        Motor.B.backward();

        if (leftSensor.getLightValue() < JunctionReached.BLACK) {
            while (leftSensor.getLightValue() < 40) {
                Motor.B.setSpeed(Motor.B.getSpeed() - 60);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred");
                }
            }
        } else {
            while (rightSensor.getLightValue() < 40) {
                Motor.A.setSpeed(Motor.A.getSpeed() - 60);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    System.out.println("Exception occurred");
                }
            }
        }
    }

    public void suppress() {
        Motor.A.stop();
        Motor.B.stop();
    }
}
