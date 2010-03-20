/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package localization;

import java.awt.Rectangle;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import lejos.geom.Line;
import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorSensor;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.nxt.comm.RConsole;
import lejos.robotics.Pose;
import lejos.robotics.RangeReadings;
import lejos.robotics.mapping.RangeMap;
import lejos.robotics.proposal.ArcPoseController;
import lejos.robotics.proposal.DestinationUnreachableException;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.proposal.WayPoint;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class TravelingRobot {
    // static varibles

    private static float MAX_READING = 150;
    private static final int ERROR_THRESHOLD = 10;
    // the world
    private RangeMap map;
    private ArrayList<Point> colorsToVisit;
    // our sense of it
    private OpticalDistanceSensor sensor = new OpticalDistanceSensor(SensorPort.S1);
    private static ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
    private UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
    /* our way to move within it */
    private DifferentialPilot pilot;
    private MCLParticleSet set;
    private MCLPoseProvider mcl;
    private ArcPoseController poseController;
    private RangeReadings readings;
    private Pose start;
    private float MAX_DISTANCE = 20f;
    Scanner scanner;

    public TravelingRobot(RangeMap map/*, UltrasonicSensor sensor*/,
                          ArrayList<Point> colorsToVisit, Pose start) {
        this.map = map;
        this.colorsToVisit = colorsToVisit;
        this.start = start;


        pilot = new DifferentialPilot(RobotConstants.WHEEL_DIAMETER / 10, RobotConstants.TRACK_WIDTH / 10,
                                      RobotConstants.leftMotor, RobotConstants.rightMotor);
        scanner = new Scanner(Motor.C, sensor);

        mcl = new MCLPoseProvider(start, pilot, scanner, map, 100, 0);
        set = mcl.getParticles();
        poseController = new ArcPoseController(pilot, mcl);
    }

    public void visitPoints() throws DestinationUnreachableException {
        MapPathFinder pathFinder = new MapPathFinder(map, readings);

        for (Point color : colorsToVisit) {
            Pose begin = localize();

            Collection<WayPoint> route = pathFinder.findRoute(begin, color);

            for (WayPoint nextStop : route) {
                System.out.println("Heading to (" + nextStop.x + ", " + nextStop.y + ")");
                poseController.goTo(nextStop);
            }

            if (mcl.getPose().getX() == color.x && mcl.getPose().getY() == color.y)
                Sound.twoBeeps();
        }
    }

    /**
     * Error threshold calculator. If all the particles are within this
     * threshold we can consider ourselves at a position.
     * @param pose
     * @return
     */
    private boolean withinErrorThreshold(Pose pose) {
        int width = set.getErrorRect().width;
        int height = set.getErrorRect().height;
        System.out.println((int) pose.getX() + "," + (int) pose.getY()
                           + "\t\t\t" + width + "," + height + " \t\t\t" + set.getMaxWeight());

        return width < ERROR_THRESHOLD && height < ERROR_THRESHOLD;
    }

    public Pose localize() {
        System.out.println("--- At ---\t\t--- Error ---\t\t--- Weight ---");
        while (true) {
            Pose currentPose = mcl.getPose();
            if (colorSensor.getColorNumber() == 9)
                        return currentPose;
            if (withinErrorThreshold(currentPose)) {
                System.out.println("\nAt: (" + currentPose.getX() + ", " + currentPose.getY() + ")");
                return currentPose;
            }
            else
                move();
        }
    }

    public void goTo(Point g) {

        Pose goal = new Pose(g.x, g.y, 0);
        while (colorSensor.getColorNumber() != 9) {
            try {
                Pose current = localize();
                navigation.MapPathFinder pathFinder = new navigation.MapPathFinder(map, scanner.getRangeValues());
                Collection<WayPoint> route = pathFinder.findRoute(current, goal);
                for (WayPoint p : route)
                    System.out.println("GO TO: (" + p.x + ", " + p.y + ")");
               // Button.waitForPress();
                for (WayPoint p : route) {
                    if (colorSensor.getColorNumber() == 9)
                        break;
                    poseController.goTo(p);
                }
            }
            catch (DestinationUnreachableException ex) {
                continue;
            }
        }
    }

    private void move() {
        float angle = (float) (Math.random() * 360);
        while (angle > 180)
            angle -= 360;
        pilot.rotate(angle);

        float distance = (float) Math.random() * MAX_DISTANCE;
        if (sensor.getDistance() > distance + 12 && us.getRange() > distance + 12)
            pilot.travel(distance, true);

        while (pilot.isMoving())
            if (sensor.getDistance() <= 30 || us.getRange() <= 30)
                pilot.stop();
    }

    public ArrayList<Point> route(Point start, Point goal) {
        throw new UnsupportedOperationException("Do this");

    }

    public static void main(String[] args) throws DestinationUnreachableException {
        RConsole.openBluetooth(60000);
        System.setOut(new PrintStream(RConsole.openOutputStream()));

        float width = 487f;
        float height = 240f;

        Line[] lines = new Line[]{
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

        LineMap map = new LineMap(lines, new Rectangle((int) width, (int) height));
        ArrayList<Point> colors = new ArrayList<Point>();
        colors.add(new Point(60, 30));
        TravelingRobot robot = new TravelingRobot(map, colors, new Pose(467, 176, 180));

//        robot.localize();
//        robot.move();
//        Pose p = robot.localize();
//        System.out.println("Final Pose: (" + p.getX() + ", " + p.getY() + ", " + p.getHeading() + ")");

        robot.goTo(new Point(236, 45));

        RConsole.close();
    }
}
