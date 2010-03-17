package localization;

import java.util.ArrayList;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.RangeReading;
import lejos.robotics.RangeReadings;
import lejos.robotics.RangeScanner;
import lejos.robotics.proposal.DifferentialPilot;

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

    private DifferentialPilot pilot;
    private Motor turret;
    private UltrasonicSensor sensor;
    private RangeReadings readings;

    /**
     * Constructs the Scanner object.
     *
     * @param turret the motor the sensor is connected to
     * @param sensor the sensor 
     */
    public Scanner(Motor turret, UltrasonicSensor sensor) {
        this.turret = turret;
        this.turret.smoothAcceleration(true);
        readings = new RangeReadings(3);
        this.sensor = sensor;
    }

    public Scanner(DifferentialPilot pilot, UltrasonicSensor sensor,
                   int numReadings) {
        this.pilot = pilot;
        this.sensor = sensor;
        readings = new RangeReadings(numReadings);
    }

    /**
     * Scans every 30 degrees around it using the OpticalDistanceSensor and
     * returns those results as {@link RangeReadings}.
     * @return range readings
     */
    public RangeReadings getRangeValues() {
        float distance;

        distance = sensor.getRange();
        distance = (distance >= 150) ? -1 : distance;
        readings.set(0, new RangeReading(0, distance));

        turret.rotateTo(-45);
        distance = sensor.getRange();
        distance = (distance >= 150) ? -1 : distance;
        readings.set(1, new RangeReading(-45, distance));

        turret.rotateTo(45);
        distance = sensor.getRange();
        distance = (distance >= 150) ? -1 : distance;
        readings.set(2, new RangeReading(45, distance));


        turret.rotateTo(0);

        return readings;
//
//        ArrayList<RangeReading> r = new ArrayList<RangeReading>();
//        for (int angle = -90; angle <= 180; angle += 90) {
//            turret.rotateTo(angle);
//            float distance = sensor.getDistance() / 10;
//            r.add(new RangeReading(angle, distance));
//        }
//
//        RangeReadings rr = new RangeReadings(0);
//        rr.addAll(r);
//
//        // reset to starting point and power off to save battery
//        turret.rotateTo(0);
//        return rr;
    }

    /**
     * Unit testing.
     * @param args unused
     */
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(Motor.C, new OpticalDistanceSensor(
//                SensorPort.S1));
//
//        Button.waitForPress();
//
//        RangeReadings r = scanner.getRangeValues();
//
//        System.out.println("Size:" + r.getNumReadings());
//        System.out.println("Complete:" + !r.incomplete());
//
//        int screen = 0;
//        for (RangeReading reading : r) {
//            int range = (int) reading.getRange();
//            System.out.println(reading.getAngle() + "°: " + range);
//            if (screen++ == 4) {
//                Button.waitForPress();
//                LCD.clearDisplay();
//                screen = 0;
//            }
//        }
//
//        Button.waitForPress();
//
//    }
}
