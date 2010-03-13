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
 * Allows the robot to scan its surroundings.
 *
 * Exercise 05
 * 23 March 2010
 *
 * @author J. Via (jxv911@cs.bham.ac.uk)
 *         M. Staniaszek (mxs968@cs.bham.ac.uk)
 */
public class Scanner implements RangeScanner {

    private Motor turret;
    private OpticalDistanceSensor sensor;

    /**
     * Constructs the Scanner object.
     *
     * @param turret the motor the sensor is connected to
     * @param sensor the sensor 
     */
    public Scanner(Motor turret, OpticalDistanceSensor sensor) {
        this.turret = turret;
        this.turret.smoothAcceleration(true);

        this.sensor = sensor;
    }

    /**
     * Scans every 30 degrees around it using the OpticalDistanceSensor and
     * returns those results as {@link RangeReadings}.
     * @return range readings
     */
    public RangeReadings getRangeValues() {
        // power on sensor
        sensor.powerOn();

        ArrayList<RangeReading> r = new ArrayList<RangeReading>();
        for (int angle = -150; angle <= 180; angle += 30) {
            turret.rotateTo(angle);
            float distance = sensor.getDistance();
            if (distance < 800) { // legal value
                r.add(new RangeReading(angle, distance));
            }
            else { //illegal value
                r.add(new RangeReading(angle, Float.NaN));
            }
        }

        RangeReadings rr = new RangeReadings(r.size());
        rr.addAll(r);

        // reset to starting point and power off to save battery
        turret.rotateTo(0);
        sensor.powerOff();
        return rr;
    }

    /**
     * Unit testing.
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(Motor.C, new OpticalDistanceSensor(
                SensorPort.S1));

        Button.waitForPress();

        RangeReadings r = scanner.getRangeValues();

        for (RangeReading reading : r) {
            System.out.println(reading.getAngle() +"°: "+reading.getRange());
        }

        Button.waitForPress();

    }
}
