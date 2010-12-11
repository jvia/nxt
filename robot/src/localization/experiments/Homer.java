package localization.experiments;

import java.awt.Rectangle;
import java.io.PrintStream;
import java.util.Collection;
import lejos.geom.Line;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;
import lejos.robotics.Pose;
import lejos.robotics.RangeReadings;
import lejos.robotics.RangeScanner;
import lejos.robotics.localization.MCLParticleSet;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.mapping.RangeMap;
import lejos.robotics.proposal.ArcPoseController;
import lejos.robotics.proposal.DestinationUnreachableException;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.localization.MCLPoseProvider;
import lejos.robotics.proposal.MapPathFinder;
import lejos.robotics.proposal.PathFinder;
import lejos.robotics.proposal.PoseController;
import lejos.robotics.proposal.WayPoint;
import util.RobotConstants;

/**
 * Test of Monte Carlo Localisation, Pose Controllers and Path finders.
 * 
 * The robot is put down in a random position in the mapped area. 
 * It makes random moves until if works out where it is. 
 * It then uses a path finder to find a route home. 
 * A pose controller is used to follow the route.
 * 
 * The robot is an robot that is supported by the DifferentialPilot class.
 * A range scanner located above the robot's center of rotation is required.
 * 
 * @author Lawrie Griffiths
 *
 */
public class Homer implements RangeScanner {
    // Tyre diameter and wheel base

    private static final float TYRE_DIAMETER = RobotConstants.WHEEL_DIAMETER;
    private static final float WHEEL_BASE = RobotConstants.TRACK_WIDTH;
    private static int BORDER = 10;
    private static int NUM_PARTICLES = 200;
    private static int MAX_RELIABLE_RANGE_READING = 150;
    private static float RANGE_READING_ANGLE = 45;
    private static int FORWARD_READING = 1;
    private static int MAX_DISTANCE = 40;
    // Distance from ultrasonic sensor to front of robot in cm
    private static final float PROJECTION = 12.0f;
    private static UltrasonicSensor range = new UltrasonicSensor(SensorPort.S1);
    private static RangeReadings readings = new RangeReadings(3);
    // Array of lines for the map
    static final float width = 487f;
    static final float height = 240f;
    static final Line[] lines = new Line[]{
        //world outline
        new Line(0f, 0f, width, 0f),
        new Line(0f, 0f, 0f, 240.4f),
        new Line(width, 0f, width, 240.4f),
        new Line(0f, 240.4f, width, 240.4f),
        //captain america
        new Line(73.4f, 104.7f, 135f, 104.7f),
        new Line(73.4f, 104.7f, 73.4f, 41.6f),
        new Line(73.4f, 41.6f, 135f, 41.6f),
        new Line(135f, 104.7f, 135f, 41.6f),
        //basil
        new Line(64.4f, 193f, 188.6f, 193f),
        new Line(64.4f, 193f, 64.4f, 153f),
        new Line(64.4f, 153f, 188.6f, 153f),
        new Line(188.6f, 193f, 188.6f, 153f),
        //obi wan
        new Line(234.4f, 218.3f, 254.5f, 218.3f),
        new Line(254.5f, 218.3f, 254.5f, 240.4f),
        new Line(234.4f, 218.3f, 234.4f, 240.4f),
        //gromit
        new Line(282f, 137f, 305.5f, 137f),
        new Line(305.5f, 137f, 305.5f, 156.5f),
        new Line(305.5f, 156.5f, 282f, 156.5f),
        new Line(282f, 156.5f, 282f, 137f),
        //preston
        new Line(333.5f, 180f, 377f, 180f),
        new Line(377f, 180f, 377f, 240.4f),
        new Line(333.5f, 180f, 333.5f, 240.4f),
        new Line(360f, 83.5f, 403.5f, 83.5f),
        //jaffa
        new Line(360.5f, 83.5f, 403.5f, 83.5f),
        new Line(403.5f, 83.5f, 403.5f, 123.5f),
        new Line(403.5f, 123.5f, 360.5f, 123.5f),
        new Line(360.5f, 123.5f, 360.5f, 83.5f),
        //wallace
        new Line(420.5f, 157, 444f, 157f),
        new Line(444f, 157f, 444f, 196.5f),
        new Line(444f, 196.5f, 420.5f, 196.5f),
        new Line(420.5f, 196.5f, 420.5f, 157f),
        //tinky-winky
        new Line(234.5f, 0, 234.5f, 21.5f),
        new Line(234.5f, 21.5f, 244f, 21.5f),
        new Line(244f, 21.5f, 244f, 64f),
        new Line(244f, 64f, 305f, 64f),
        new Line(305f, 64f, 305f, 42f),
        new Line(305f, 42f, 346.5f, 42f),
        new Line(346.5f, 42f, 346.5f, 0f),
        //gandalf
        new Line(width, 21.5f, width - 20.5f, 21.5f),
        new Line(width - 20.5f, 21.5f, width - 20.5f, 0f)
    };
    private static final Rectangle bound = new Rectangle(0, 0, 132, 340);
    private static RangeMap map = new LineMap(lines, bound);
    private static DifferentialPilot pilot;
    private static MCLParticleSet particles;
    private static MCLPoseProvider mcl;

    public static void main(String[] args) {
        Homer simpson = new Homer();
        simpson.run();
    }

    public void run() {
        RConsole.openBluetooth(0);
        System.setOut(new PrintStream(RConsole.openOutputStream()));

        // Create the robot and MCL pose provider and get its particle set
        pilot = new DifferentialPilot(
                TYRE_DIAMETER, WHEEL_BASE, RobotConstants.leftMotor, RobotConstants.rightMotor, true);
        mcl = new MCLPoseProvider(pilot, this, map, NUM_PARTICLES, BORDER);
        particles = mcl.getParticles();
        particles.setDebug(true);

        // Make random moves until we know where we are
        Pose start = localize();

        // Find a route home
        Pose home = new Pose(50, 300, -90);
        PathFinder pf = new MapPathFinder(map, readings);
        PoseController pc = new ArcPoseController(pilot, mcl);

        System.out.println("Located: (" + (int) start.getX() + "," + (int) start.getY() + ")");

        // Go home
        try {
            Collection<WayPoint> route = pf.findRoute(start, home);

            for (WayPoint wp : route) {
                System.out.println("Go to (" + (int) wp.x + "," + (int) wp.y + ")");
                Pose pose = pc.goTo(wp);
                goodEstimate(pose); // Just for diagnostics
                // Pose controller should have a goTo(Pose) method to do this
                pilot.rotate(wp.getHeading() - pose.getHeading());
            }
        }
        catch (DestinationUnreachableException e) {
            System.out.println("Unreachable");
        }
        Button.waitForPress();
    }

    @Override
    public RangeReadings getRangeValues() {
        takeReadings();
        return readings;
    }

    /**
     * Take a single range reading
     *
     */
    private void takeReading(float angle, int i) {
        int rangeByte = (int) range.getRange();
        float range;

        if (rangeByte > MAX_RELIABLE_RANGE_READING) range = -1f;
        else range = ((float) rangeByte);
        readings.setRange(i, angle, range);
    }

    /**
     * Take a set of 3 readings
     */
    public void takeReadings() {
        // Take forward reading
        takeReading(0f, FORWARD_READING);

        // Take left reading
        pilot.rotate(-RANGE_READING_ANGLE);
        takeReading(-RANGE_READING_ANGLE, 0);

        // Take right reading
        pilot.rotate(2 * RANGE_READING_ANGLE);
        takeReading(RANGE_READING_ANGLE, 2);
        pilot.rotate(-RANGE_READING_ANGLE);
    }

    /**
     * Make a random move
     */
    public void randomMove() {
        float angle = (float) Math.random() * 360;
        float distance = (float) Math.random() * MAX_DISTANCE;

        if (angle > 180f) angle -= 360f;

        // Get forward range
        float forwardRange = readings.getRange(1);

        // Don't move forward if we are near a wall
        if (forwardRange < 0
            || distance + BORDER + PROJECTION < forwardRange)
            pilot.travel(distance);

        pilot.rotate(angle);
    }

    /**
     * Check if estimated pose is accurate enough
     */
    boolean goodEstimate(Pose pose) {
        int width = particles.getErrorRect().width;
        int height = particles.getErrorRect().height;
        System.out.println("At " + (int) pose.getX() + "," + (int) pose.getY() + " Error: " + width + "," + height + " Weight: " + particles.getMaxWeight());
        return width < 50 && height < 50;
    }

    /**
     * Make random moves until the estimated pose is good enough
     */
    private Pose localize() {
        for (;;) {
            Pose pose = mcl.getPose();
            if (goodEstimate(pose)) return pose;
            else randomMove();
        }
    }
}

