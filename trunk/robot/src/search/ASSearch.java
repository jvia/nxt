package search;

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
//import lejos.nxt.addon.ColorSensor;
//import lejos.robotics.navigation.TachoPilot;
//import search.Grid.GridTraveler.Facing;
//import util.Collections;
//import util.Queue;
//import util.RobotConstants;
//
///**
// *
// * @author Jeremiah Via
// */
//public class ASSearch {
//
//    GridTraveler traveler;
//    ColorSensor colorSensor;
//    Queue<PriorityPoint> agendaList;
//    ArrayList<PriorityPoint> blockedPoints;
//    ArrayList<PriorityPoint> visited;
//    int cost;
//    PriorityPoint start;
//    PriorityPoint goal;
//
//    public ASSearch(GridTraveler traveler, ColorSensor colorSensor,
//                    PriorityPoint start,
//                    PriorityPoint goal) {
//        this.traveler = traveler;
//        this.colorSensor = colorSensor;
//        this.start = start;
//        this.goal = goal;
//        agendaList = new Queue<PriorityPoint>();
//        agendaList.push(this.start);
//        cost = 0;
//
//        visited = new ArrayList<PriorityPoint>();
//        blockedPoints = new ArrayList<PriorityPoint>();
//        blockedPoints.add(new PriorityPoint(1, 1));
//        blockedPoints.add(new PriorityPoint(2, 0));
//    }
//
//    boolean atGoal() {
//        return start.equals(goal);
//    }
//
//    void search() {
//        Point point = agendaList.pop();
//        traveler.goTo(point);
//        //blockedPoints.add(new PriorityPoint(point.x, point.y));
//        cost++;
//        generateSuccessors();
//    }
//
//    int fValue(Point p) {
//        return cost + (int) Math.sqrt(Math.pow(traveler.currPoint.x - p.x, 2)
//                                      + Math.pow(traveler.currPoint.y - p.y, 2));
//    }
//
//    void generateSuccessors() {
//        ArrayList<PriorityPoint> candidates = new ArrayList<PriorityPoint>();
//        if (traveler.currPoint.x < traveler.width) {
//            int fVal = fValue(new Point(traveler.currPoint.x + 1,
//                                        traveler.currPoint.y));
//            PriorityPoint p = new PriorityPoint(traveler.currPoint.x + 1,
//                                                traveler.currPoint.y, fVal);
//            if (!blockedPoints.contains(p) )
//                agendaList.add(p);
//        }
//        if (traveler.currPoint.y < traveler.height) {
//            int fVal = fValue(new Point(traveler.currPoint.x, traveler.currPoint.y
//                                                              + 1));
//            PriorityPoint p = new PriorityPoint(traveler.currPoint.x, traveler.currPoint.y
//                                                                      + 1, fVal);
//            if (!blockedPoints.contains(p))
//                agendaList.add(p);
//        }
//        if (traveler.currPoint.x > 0) {
//            int fVal = fValue(new Point(traveler.currPoint.x - 1,
//                                        traveler.currPoint.y));
//            PriorityPoint p = new PriorityPoint(traveler.currPoint.x - 1,
//                                                traveler.currPoint.y, fVal);
//            if (!blockedPoints.contains(p))
//                agendaList.add(p);
//        }
//        if (traveler.currPoint.y > 0) {
//            int fVal = fValue(new Point(traveler.currPoint.x, traveler.currPoint.y
//                                                              - 1));
//            PriorityPoint p = new PriorityPoint(traveler.currPoint.x, traveler.currPoint.y
//                                                                      - 1, fVal);
//            if (!blockedPoints.contains(p))
//                agendaList.add(p);
//        }
//
//        // filter out previously visited points
////        for (PriorityPoint candidate : candidates)
////            if (!traveler.visited(candidate))
////                agendaList.add(candidate);
//
//        // sort collection based on priority, lower is better
//        Collections.sort(agendaList);
//
//        for (int i = 0; i < agendaList.size() && i < 6; i++) {
//            traveler.printPoint(agendaList.get(i));
//            //System.out.print(agendaList.get(i).priorityValue + ", ");
//
//        }
//        System.out.println("\n------");
//       // Button.waitForPress();
//
//    }
//
//    public static void main(String[] args) {
//        TachoPilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
//                                          RobotConstants.TRACK_WIDTH,
//                                          RobotConstants.leftMotor,
//                                          RobotConstants.rightMotor, true);
//        LightSensor left = new LightSensor(SensorPort.S3);
//        LightSensor right = new LightSensor(SensorPort.S4);
//        ColorSensor color = new ColorSensor(SensorPort.S2);
//
//        PriorityPoint start = new PriorityPoint(0, 0);
//        PriorityPoint goal = new PriorityPoint(2, 1);
//        int height = 5, width = 5;
//
//        GridTraveler traveler = new GridTraveler(pilot, left, right, start,
//                                                 height, width, Facing.EAST);
//
//
//        ASSearch as = new ASSearch(traveler, color, start, goal);
//
//        while (!as.atGoal()) {
//            as.search();
//        }
//
//        System.out.println("SUCCESS!");
//        as.traveler.pilot.rotate(720);
//        Button.waitForPress();
//    }
//}
//
