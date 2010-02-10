package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

/**
 * 
 * @author Jeremiah Via
 */
public class LightTest {

    public static void main(String[] args) throws InterruptedException {
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);

        while (true) {
            System.out.println("left: " + left.getLightValue());
            System.out.println("right: " + right.getLightValue());

            if (left.getLightValue() < 45 && right.getLightValue() < 45) {
                Motor.A.stop();

                Thread.sleep(1245);

            }
            else if (left.getLightValue() < 45) {
                Motor.B.stop();
            }
            else if (right.getLightValue() < 45) {
                Motor.A.stop();
            }
            else {
                Motor.A.backward();
                Motor.B.backward();
            }
        }
    }
}
