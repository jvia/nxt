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
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;
import lejos.robotics.Pose;
import lejos.robotics.RangeReadings;
import lejos.robotics.mapping.RangeMap;
import lejos.robotics.proposal.ArcPoseController;
import lejos.robotics.proposal.DestinationUnreachableException;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.proposal.MapPathFinder;
import lejos.robotics.proposal.PathFinder;
import lejos.robotics.proposal.WayPoint;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class TravelingRobot {
    // static varibles

    private static float MAX_READING = 150;
    private static final int ERROR_THRESHOLD = 2;
    // the world
    private RangeMap map;
    private ArrayList<Point> colorsToVisit;
    // our sense of it
    private UltrasonicSensor sensor;
    // our way to move within it
    private DifferentialPilot pilot;
    private MCLParticleSet set;
    private MCLPoseProvider mcl;
    private ArcPoseController poseController;
    private RangeReadings readings;
    private Pose start;
    private float MAX_DISTANCE = 10f;

    public TravelingRobot(RangeMap map/*, UltrasonicSensor sensor,
            ArrayList<Point> colorsToVisit, Pose start*/) {
//        this.map = map;
//        this.sensor = sensor;
//        this.colorsToVisit = colorsToVisit;
//        this.start = start;

        sensor = new UltrasonicSensor(SensorPort.S1);
        pilot = new DifferentialPilot(
                RobotConstants.WHEEL_DIAMETER / 10, RobotConstants.TRACK_WIDTH / 10,
                RobotConstants.leftMotor, RobotConstants.rightMotor, true);
        Scanner scanner = new Scanner(Motor.C, sensor);
        start = new Pose(30, 20, 0);
        mcl = new MCLPoseProvider(start, pilot, scanner, map, 200, 0);
        set = mcl.getParticles();
    }

    public void visitPoints(ArrayList<Point> colors) throws
            DestinationUnreachableException {
        PathFinder pathFinder = new MapPathFinder(map, readings);

        Collection<WayPoint> route =
                             pathFinder.findRoute(start, colorsToVisit.remove(0));

        for (WayPoint nextStop : route) {
            System.out.println("Heading to (" + nextStop.x + ", " + nextStop.y
                               + ")");
            poseController.goTo(nextStop);
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
        System.out.println((int)pose.getX() + "," + (int) pose.getY()
                           + "\t\t\t" + width + "," + height + " \t\t\t" + set.
                getMaxWeight());

        return width < ERROR_THRESHOLD && height < ERROR_THRESHOLD;
    }

    public Pose localize() {
        while (true) {
            Pose currentPose = mcl.getPose();
            if (withinErrorThreshold(currentPose))
                return currentPose;
            else
                move();
        }
    }

    private void move() {
        float angle = (float) (-180+Math.random() * 360);
        float distance = (float) Math.random() * MAX_DISTANCE;

        pilot.travel(distance, true);
        while (pilot.isMoving())
                if (sensor.getRange() < 20)
                    pilot.stop();

        pilot.rotate(angle);
    }

    public ArrayList<Point> route(Point start, Point goal) {
        throw new UnsupportedOperationException("Do this");

    }

    public static void main(String[] args) {
        RConsole.openBluetooth(60000);
        System.setOut(new PrintStream(RConsole.openOutputStream()));

        float width = 119f;
        float height = 61f;

        Line[] lines = new Line[]{
            // world outline
            new Line(0f, 0f, 0f, height),
            new Line(0f, 0f, width, 0f),
            new Line(0f, height, width, height),
            new Line(width, 0f, width, height),
            // document box
            new Line(0f, 36f, 32.5f, 36f),
            new Line(32.5f, 36f, 32.5f, height),
            // brown box
            new Line(102.5f, 38f, width, 38f),
            new Line(102.5f, 38f, 102.5f, height)
        };

        Rectangle bound = new Rectangle(0, 0, (int) width, (int) height);
        RangeMap map = new LineMap(lines, bound);

        TravelingRobot robot = new TravelingRobot(map);

        System.out.println("--- At ---\t\t--- Error ---\t\t--- Weight ---");
        Pose pose = robot.localize();
        System.out.println("\nAt: (" + pose.getX() + ", " + pose.getY() + ")");
        RConsole.close();
    }
}
