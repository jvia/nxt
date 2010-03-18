package localization;

import java.io.PrintStream;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;
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
        float distance = distance = sensor.getRange();

        turret.rotateTo(-45);
        distance = sensor.getRange();
        distance = (distance >= 150)? -1 : distance;
        readings.setRange(0, -45, distance);

        turret.rotateTo(0);
        distance = sensor.getRange();
        distance = (distance >= 150)? -1 : distance;
        readings.setRange(1, 0, distance);

        turret.rotateTo(45);
        distance = sensor.getRange();
        distance = (distance >= 150)? -1 : distance;
        readings.setRange(2, 45, distance);


//        for (int angle = -180 + (360 / readings.size()), i = 0; i < 6; angle += (360 / readings.size()), i++) {
//            turret.rotateTo(angle);
//            distance = sensor.getRange();
//            distance = (distance >= 150) ? -1 : distance;
//            readings.setRange(i, angle, distance);
//        }

        turret.rotateTo(0);

        return readings;
    }

    /**
     * Unit testing.
     * @param args unused
     */
    public static void main(String[] args) {
        RConsole.openBluetooth(60000);
        System.setOut(new PrintStream(RConsole.openOutputStream()));


        Scanner scanner = new Scanner(Motor.C, new UltrasonicSensor(
                SensorPort.S1));

        Button.waitForPress();

        RangeReadings r = scanner.getRangeValues();

        System.out.println("Size:" + r.getNumReadings());
        System.out.println("Complete:" + !r.incomplete());
        Button.waitForPress();
        System.out.println("\n------------------------------------\nAngle\t\tRange\n------------------------------------");
        for (RangeReading reading : r) {
            int range = (int) reading.getRange();
            System.out.println(reading.getAngle() + "\t\t" + range);
        }

        RConsole.close();
    }
}
