///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package localization.experiments;
//
//import java.awt.Rectangle;
//import java.io.PrintStream;
//import lejos.geom.Line;
//import lejos.nxt.Motor;
//import lejos.nxt.SensorPort;
//import lejos.nxt.UltrasonicSensor;
//import lejos.nxt.comm.RConsole;
//import lejos.robotics.localization.MCLParticleSet;
//import lejos.robotics.localization.MCLPoseProvider;
//import lejos.robotics.mapping.LineMap;
//import lejos.robotics.proposal.ArcPoseController;
//import lejos.robotics.proposal.DifferentialPilot;
//import localization.Scanner;
//import util.RobotConstants;
//
///**
// *
// * @author Jeremiah Via
// */
//public class MCLTestsHome {
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
//        float width = 119f;
//        float height = 61f;
//
//        Line[] lines = new Line[]{
//            // world outline
//            new Line(0f, 0f, 0f, height),
//            new Line(0f, 0f, width, 0f),
//            new Line(0f, height, width, height),
//            new Line(width, 0f, width, height),
//            // document box
//            new Line(0f, 36f, 32.5f, 36f),
//            new Line(32.5f, 36f, 32.5f, height),
//            // brown box
//            new Line(102.5f, 38f, width, 38f),
//            new Line(102.5f, 38f, 102.5f, height)
//        };
//
//        LineMap map = new LineMap(lines, new Rectangle((int) width,
//                                                       (int) height));
//
//        Scanner scanner = new Scanner(Motor.C, new UltrasonicSensor(
//                SensorPort.S1));
//        MCLPoseProvider poseProvider = new MCLPoseProvider(pilot,
//                                                           scanner,
//                                                           map,
//                                                           100,
//                                                           0);
//
//        ArcPoseController cont = new ArcPoseController(pilot, poseProvider);
//
//        RConsole.openBluetooth(60000);
//        System.setOut(new PrintStream(RConsole.openOutputStream()));
//
//
//        /* localize method idea */
//        MCLPoseProvider pose = (MCLPoseProvider) cont.getPoseProvider();
//        MCLParticleSet particles = pose.getParticles();
//
//        while (true){
//            int errorWidth = particles.getErrorRect().x;
//            int errorHeight = particles.getErrorRect().y;
//
//            // see how we're doing
//            System.out.println("Estimate: (" + (int) poseProvider.getPose().getX() +
//                    ", " + (int) poseProvider.getPose().getY() + "); Error: ("
//                    + errorWidth + ", " + errorHeight + "); Weight: "
//                    + particles.getMaxWeight());
//
//
//            if (errorWidth < 20 && errorHeight < 20)
//                break; // we're localized
//            else // random move
//            {
//
//            }
//        }
//
////
////        /* Tests */
////        MCLPoseProvider posepro = (MCLPoseProvider) cont.getPoseProvider();
////        for (int j = 0; j < 5; j++) {
////            float x = cont.getPoseProvider().getPose().getX();
////            float y = cont.getPoseProvider().getPose().getY();
////            float O = cont.getPoseProvider().getPose().getHeading();
////
////            System.out.println("(" + x + ", " + y + ", " + O + ")\n");
////
////            MCLPoseProvider pp = (MCLPoseProvider) cont.getPoseProvider();
////            MCLParticleSet set = pp.getParticles();
////
////
////            for (int i = 0; i < set.numParticles(); i++) {
////                float x1 = set.getParticle(i).getPose().getX();
////                float y1 = set.getParticle(i).getPose().getY();
////                float O1 = set.getParticle(i).getPose().getHeading();
////                float w = set.getParticle(i).getWeight();
////
////                System.out.println("(" + x1 + ", " + y1 + ", " + O1 + ") @ " + w
////                                   + " weight");
////            }
////
////            MCLParticle m = set.getParticle(set.findClosest(30, 30));
////            System.out.println("Closest\n--------------\n" + m.getPose().getX()
////                               + ", " + m.getPose().getY() + " @ "
////                               + m.getWeight());
//
////            cont.getPilot().travel(20, true);
////            while (cont.getPilot().isMoving()) {
////                if (us.getDistance() <= 20) {
////                    pilot.stop();
////                    pilot.rotate((Math.random() > 0.5) ? -60 : 60);
////                }
////        }
//    }
////        float x = cont.getPoseProvider().getPose().getX();
////        float y = cont.getPoseProvider().getPose().getY();
////        float O = cont.getPoseProvider().getPose().getHeading();
////
////        System.out.println("(" + x + ", " + y + ", " + O + ")\n");
//}
//
