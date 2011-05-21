package localization;

import java.io.PrintStream;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
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
    private OpticalDistanceSensor sensor;
    private RangeReadings readings;

    /**
     * Constructs the Scanner object.
     *
     * @param turret the motor the sensor is connected to
     * @param sensor the sensor 
     */
    public Scanner(Motor turret, OpticalDistanceSensor sensor) {
        this.turret = turret;
        this.turret.smoothAcceleration(true);
        readings = new RangeReadings(3);
        this.sensor = sensor;
    }

    /**
     * Scans every 30 degrees around it using the OpticalDistanceSensor and
     * returns those results as {@link RangeReadings}.
     * @return range readings
     */
    public RangeReadings getRangeValues() {
        sensor.powerOn();
        float distance;

        turret.rotateTo(-45);
        distance = sensor.getDistance()/10;
        distance = (distance >= 80) ? -1 : distance;
        readings.setRange(0, 45, distance);

        turret.rotateTo(0);
        distance = sensor.getDistance()/10;
        distance = (distance >= 80) ? -1 : distance;
        readings.setRange(1, 0, distance);

        turret.rotateTo(45);
        distance = sensor.getDistance()/10;
        distance = (distance >= 80) ? -1 : distance;
        readings.setRange(2, -45, distance);

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


        Scanner scanner = new Scanner(Motor.C, new OpticalDistanceSensor(SensorPort.S1));


        RangeReadings r = scanner.getRangeValues();

        System.out.println("\nSize:" + r.getNumReadings());
        System.out.println("Complete:" + !r.incomplete());
        System.out.println(
                "\n\n------------------------------------\nAngle\t\tRange\n------------------------------------");
        for (RangeReading reading : r) {
            int range = (int) reading.getRange();
            System.out.println(reading.getAngle() + "\t\t" + range);
        }
        System.out.println("");
        RConsole.close();
    }
}
