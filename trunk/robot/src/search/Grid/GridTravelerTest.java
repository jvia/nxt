///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package search.Grid;
//
//import java.util.ArrayList;
//import lejos.geom.Point;
//import lejos.nxt.Button;
//import lejos.nxt.LightSensor;
//import lejos.nxt.SensorPort;
//import lejos.robotics.navigation.TachoPilot;
//import util.RobotConstants;
//
///**
// *
// * @author Jeremiah Via
// */
//public class GridTravelerTest {
//
//    public static void main(String[] args) {
//        TachoPilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
//                                          RobotConstants.TRACK_WIDTH,
//                                          RobotConstants.leftMotor,
//                                          RobotConstants.rightMotor, true);
//        LightSensor left = new LightSensor(SensorPort.S3);
//        LightSensor right = new LightSensor(SensorPort.S4);
//
//        int height = 5, width = 5;
//        final Point start = new Point(0, 0);
//
//        GridTraveler gt = new GridTraveler(pilot, left, right, start, height,
//                                           width, GridTraveler.Facing.EAST);
//
//        ArrayList<String> moves = new ArrayList<String>();
//        moves.add("E");
//        moves.add("E");
//
//        Button.waitForPress();
//        gt.goTo(new Point(1,0));
//        gt.goTo(new Point(0,0));
//
//        Point q = new Point(0,0);
//
////        gt.goTo(goal);
////        gt.pilot.stop();
////        System.out.println("\n===");
////        gt.printPoint(start);
////        gt.printPoint(goal);
////        gt.goTo(new Point(1,3));
//        pilot.stop();
//        Button.waitForPress();
//
//    }
//}
