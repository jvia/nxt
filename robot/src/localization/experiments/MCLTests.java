//package localization.experiments;
//
//import java.awt.Rectangle;
//import java.io.PrintStream;
//import lejos.geom.Line;
//import lejos.geom.Point;
//import lejos.nxt.Motor;
//import lejos.nxt.SensorPort;
//import lejos.nxt.UltrasonicSensor;
//import lejos.nxt.addon.OpticalDistanceSensor;
//import lejos.nxt.comm.RConsole;
//import lejos.robotics.proposal.ArcPoseController;
//import lejos.robotics.proposal.DifferentialPilot;
//import localization.LineMap;
//import localization.MCLParticleSet;
//import localization.MCLPoseProvider;
//import localization.Scanner;
//import util.RobotConstants;
//
///**
// *
// * @author Jeremiah Via
// */
//public class MCLTests {
//
//    public static void main(String[] args) {
//        DifferentialPilot pilot = new DifferentialPilot(
//                RobotConstants.WHEEL_DIAMETER / 10,
//                RobotConstants.TRACK_WIDTH / 10,
//                RobotConstants.leftMotor,
//                RobotConstants.rightMotor,
//                true); // robot uses reverse configuration
//
//
//        float width = 487f;
//        float height = 240f;
//
//        Line[] lines = new Line[]{
//            //world outline
//            new Line(0f, 0f, width, 0f),
//            new Line(0f, 0f, 0f, 240.4f),
//            new Line(width, 0f, width, 240.4f),
//            new Line(0f, 240.4f, width, 240.4f),
//            //captain america
//            new Line(73.4f, 104.7f, 135f, 104.7f),
//            new Line(73.4f, 104.7f, 73.4f, 41.6f),
//            new Line(73.4f, 41.6f, 135f, 41.6f),
//            new Line(135f, 104.7f, 135f, 41.6f),
//            //basil
//            new Line(64.4f, 193f, 188.6f, 193f),
//            new Line(64.4f, 193f, 64.4f, 153f),
//            new Line(64.4f, 153f, 188.6f, 153f),
//            new Line(188.6f, 193f, 188.6f, 153f),
//            //obi wan
//            new Line(234.4f, 218.3f, 254.5f, 218.3f),
//            new Line(254.5f, 218.3f, 254.5f, 240.4f),
//            new Line(234.4f, 218.3f, 234.4f, 240.4f),
//            //gromit
//            new Line(282f, 137f, 305.5f, 137f),
//            new Line(305.5f, 137f, 305.5f, 156.5f),
//            new Line(305.5f, 156.5f, 282f, 156.5f),
//            new Line(282f, 156.5f, 282f, 137f),
//            //preston
//            new Line(333.5f, 180f, 377f, 180f),
//            new Line(377f, 180f, 377f, 240.4f),
//            new Line(333.5f, 180f, 333.5f, 240.4f),
//            new Line(360f, 83.5f, 403.5f, 83.5f),
//            //jaffa
//            new Line(360.5f, 83.5f, 403.5f, 83.5f),
//            new Line(403.5f, 83.5f, 403.5f, 123.5f),
//            new Line(403.5f, 123.5f, 360.5f, 123.5f),
//            new Line(360.5f, 123.5f, 360.5f, 83.5f),
//            //wallace
//            new Line(420.5f, 157, 444f, 157f),
//            new Line(444f, 157f, 444f, 196.5f),
//            new Line(444f, 196.5f, 420.5f, 196.5f),
//            new Line(420.5f, 196.5f, 420.5f, 157f),
//            //tinky-winky
//            new Line(234.5f, 0, 234.5f, 21.5f),
//            new Line(234.5f, 21.5f, 244f, 21.5f),
//            new Line(244f, 21.5f, 244f, 64f),
//            new Line(244f, 64f, 305f, 64f),
//            new Line(305f, 64f, 305f, 42f),
//            new Line(305f, 42f, 346.5f, 42f),
//            new Line(346.5f, 42f, 346.5f, 0f),
//            //gandalf
//            new Line(width, 21.5f, width - 20.5f, 21.5f),
//            new Line(width - 20.5f, 21.5f, width - 20.5f, 0f)
//        };
//
//        LineMap map = new LineMap(lines, new Rectangle((int) width,
//                                                       (int) height));
//
//        Scanner scanner = new Scanner(Motor.C,
//                                      new UltrasonicSensor(SensorPort.S1),
//                                      3);
//        MCLPoseProvider poseProvider = new MCLPoseProvider(pilot,
//                                                           scanner,
//                                                           map,
//                                                           80,
//                                                           0);
//
//        ArcPoseController cont =
//                          new ArcPoseController(pilot, poseProvider);
//
//        UltrasonicSensor us = new UltrasonicSensor(SensorPort.S3);
//
//        RConsole.openBluetooth(60000);
//        System.setOut(new PrintStream(RConsole.openOutputStream()));
//
//
//        /* Tests */
//        Point p = new Point(0, 0);
//        MCLPoseProvider posepro = (MCLPoseProvider) cont.getPoseProvider();
//        while (posepro.isLost()) {
//            float x = cont.getPoseProvider().getPose().getX();
//            float y = cont.getPoseProvider().getPose().getY();
//            float O = cont.getPoseProvider().getPose().getHeading();
//
//            System.out.println("(" + x + ", " + y + ", " + O + ")\n");
//
//            MCLPoseProvider pp = (MCLPoseProvider) cont.getPoseProvider();
//            MCLParticleSet set = pp.getParticles();
//
//            for (int i = 0; i < set.numParticles(); i++) {
//                float x1 = set.getParticle(i).getPose().getX();
//                float y1 = set.getParticle(i).getPose().getY();
//                float O1 = set.getParticle(i).getPose().getHeading();
//                float w = set.getParticle(i).getWeight();
//
//                System.out.println("(" + x1 + ", " + y1 + ", " + O1 + ") @ " + w
//                                   + " weight");
//            }
//
//            cont.getPilot().travel(20, true);
//            while (cont.getPilot().isMoving()) {
//                if (us.getDistance() <= 20) {
//                    pilot.stop();
//                    pilot.rotate((Math.random() > 0.5) ? -60 : 60);
//                }
//            }
//        }
//
//        float x = cont.getPoseProvider().getPose().getX();
//        float y = cont.getPoseProvider().getPose().getY();
//        float O = cont.getPoseProvider().getPose().getHeading();
//
//        System.out.println("(" + x + ", " + y + ", " + O + ")\n");
//    }
//}
