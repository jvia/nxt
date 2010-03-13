/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Rectangle;
import java.util.ArrayList;
import lejos.geom.Line;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.Pose;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.proposal.ArcPoseController;
import lejos.robotics.proposal.DifferentialPilot;
import localization.MCLParticle;
import localization.MCLParticleSet;
import localization.MCLPoseProvider;
import localization.Scanner;
import proxy.NavigationControls;
import util.RobotConstants;

/**
 *
 * @author nah
 */
class RobotController implements NavigationControls {

    private final DifferentialPilot m_pilot;
//    private final DeadReckonerPoseProvider m_poser;
    private final ArcPoseController m_controller;
    private final MCLPoseProvider pose;
    private final LineMap map;

    /**
     * Create controls in cm coordinate frame.
     *
     * TODO: Add parameters to describe robot.
     */
    public RobotController(float x, float y, float heading) {
        m_pilot = new DifferentialPilot(
                RobotConstants.WHEEL_DIAMETER / 10,
                RobotConstants.TRACK_WIDTH / 10,
                RobotConstants.leftMotor,
                RobotConstants.rightMotor,
                true); // robot uses reverse configuration

//        m_poser = new DeadReckonerPoseProvider(m_pilot);
//        m_poser.setPose(x, y, heading);

        Line[] lines = new Line[]{
            // tinky-winky
            new Line(0f, 64f, 60f, 64f),
            new Line(60f, 64f, 60f, 42f),
            new Line(60f, 42f, 100f, 42f),
            new Line(100f, 0f, 100f, 42f),
            // gromit
            new Line(36f, 137f, 60f, 137f),
            new Line(36f, 137f, 36f, 156f),
            new Line(36f, 156f, 60f, 156f),
            new Line(60f, 137f, 60f, 156f),
            // preston
            new Line(87f, 180f, 87f, 240f),
            new Line(87f, 180f, 130f, 180f),
            new Line(130f, 180f, 130f, 240f),
            // jaffle
            new Line(113.5f, 123f, 157f, 123f),
            new Line(113.5f, 123f, 113.5f, 83f),
            new Line(113.5f, 83f, 157f, 83f),
            new Line(157f, 123f, 157f, 83f),
            // wallace
            new Line(174f, 197f, 174f, 157.5f),
            new Line(174f, 197f, 198f, 197f),
            new Line(198f, 197f, 198f, 157.5f),
            new Line(174f, 157.5f, 198f, 157.5f),
            // gandalf
            new Line(220f, 0f, 220f, 21.5f),
            new Line(240f, 21.5f, 220f, 21.5f)
        };

        map = new LineMap(lines, new Rectangle(0, 0, 240, 240));
        Scanner scanner = new Scanner(Motor.C, new OpticalDistanceSensor(
                SensorPort.S1));
        Pose startPose = new Pose(x, y, heading);
        pose = new MCLPoseProvider(startPose, m_pilot, scanner, map, 100, 0);

        m_controller = new ArcPoseController(m_pilot, pose);

    }

    /**
     * Move to pose.
     *
     * TODO: Respect target pose.
     * @param _target
     */
    public void goTo(Pose _target) {
        m_controller.goTo(_target.getLocation());
    }

    /**
     * Get's the robot's pose.
     * @return
     */
    public Pose getPose() {
        return pose.getPose();
    }

    public LineMap getMap() {
        return map;
    }

    /**
     * Testing
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        RobotController c = new RobotController(210, 30, 90);
        MCLParticleSet set = c.pose.getParticles();
        set.setDebug(true);

        ArrayList<MCLParticle> unique = new ArrayList<MCLParticle>();

//        // get unique particles
//        for (int i = 0; i < set.numParticles(); i++) {
//            System.out.println(unique.contains(set.getParticle(i)));
//            if (unique.contains(set.getParticle(i)))
//                continue;
//            else
//                unique.add(set.getParticle(i));
//            //Button.waitForPress();
//        }
//
//        // output unique partciels
//        for (MCLParticle p : unique) {
//            float x = p.getPose().getX();
//            float y = p.getPose().getY();
//            float heading = p.getPose().getHeading();
//            System.out.println("(" + x + "," + y + "," + heading + ")");
//            Button.waitForPress();
//        }

        System.out.println("("+c.pose.getPose().getX()+","+c.pose.getPose().getY()+")");
        Button.waitForPress();


        c.m_controller.goTo(210, 30);
        LCD.clearDisplay();

        set = c.pose.getParticles();
        unique.clear();
        // get unique particles
        for (int i = 0; i < 5; i++) {
            if (unique.contains(set.getParticle(i)))
                continue;
            else
                unique.add(set.getParticle(i));
        }
//
        // output unique partciels
        for (MCLParticle p : unique) {
            float x = p.getPose().getX();
            float y = p.getPose().getY();
            float heading = p.getPose().getHeading();
            System.out.println("(" + x + "," + y + "," + heading + ")");
            Button.waitForPress();
        }

        System.out.println("("+c.pose.getPose().getX()+","+c.pose.getPose().getY()+")");
        Button.waitForPress();
    }
}
