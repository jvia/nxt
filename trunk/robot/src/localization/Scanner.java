/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package localization;

import java.util.ArrayList;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.RangeReading;
import lejos.robotics.RangeReadings;
import lejos.robotics.RangeScanner;

/**
 * 
 * @author Jeremiah Via
 */
public class Scanner implements RangeScanner {

    private Motor motor;
    private OpticalDistanceSensor sensor;

    public Scanner(Motor motor, OpticalDistanceSensor sensor) {
        this.motor = motor;
        motor.smoothAcceleration(false);

        this.sensor = sensor;
    }

    public int scan(int angle) {
        motor.rotateTo(angle);

        motor.stop();
        int d = sensor.getDistance();

        motor.rotateTo(0);
        motor.stop();

        return d;
    }

    public int[] scan(int[] angle) throws InterruptedException {
        int[] d = new int[angle.length];

        for (int i = 0; i < angle.length; i++) {
            motor.rotateTo(angle[i]);
            motor.stop();
            Thread.sleep(4);
            d[i] = sensor.getDistance();
        }

        motor.rotateTo(0);
        return d;
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(Motor.C, new OpticalDistanceSensor(
                SensorPort.S1));

        Button.waitForPress();

        RangeReadings r = scanner.getRangeValues();

        for (RangeReading reading : r) {
            System.out.println(reading.getRange());
        }

        Button.waitForPress();

    }

    public RangeReadings getRangeValues() {
        ArrayList<RangeReading> r = new ArrayList<RangeReading>();
        for (int angle = -150; angle <= 180; angle += 30) {
            motor.rotateTo(angle);
            float distance = sensor.getDistance();
            if (distance < 80) { // legal value
                r.add(new RangeReading(angle, distance));
            }
            else { //illegal value
                r.add(new RangeReading(angle, Float.NaN));
            }
        }

        RangeReadings rr = new RangeReadings(r.size());
        rr.addAll(r);

//        // get readings
//        rangeReadings.add(new RangeReading(0, sensor.getDistance()));
//
//        motor.rotate(90);
//        rangeReadings.add(new RangeReading(90, sensor.getDistance()));
//
//        motor.rotateTo(180);
//
//        rangeReadings.add(new RangeReading(180, sensor.getDistance()));
//
//        motor.rotateTo(-90);
//
//        rangeReadings.add(new RangeReading(-90, sensor.getDistance()));
//
        motor.rotateTo(0);
        return rr;
    }
}
