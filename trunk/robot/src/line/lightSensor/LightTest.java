/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package line.lightSensor;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
/**
 * 
 * @author Jeremiah Via
 */
public class LightTest
{
    public static void main(String[] args) throws InterruptedException {
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);

        while (true)
        {
            System.out.println("left: " + left.getLightValue() );
            System.out.println("right: " + right.getLightValue());
            Thread.sleep(200);
            LCD.clear();
        }
    }

}
