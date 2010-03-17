/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package localization.experiments;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
/**
 * 
 * @author Jeremiah Via
 */
public class RandomTests
{
    public static void main(String[] args) {
        UltrasonicSensor us = new UltrasonicSensor(SensorPort.S3);
        while (true)
        {
            System.out.println(us.getDistance());
        }
    }

}
