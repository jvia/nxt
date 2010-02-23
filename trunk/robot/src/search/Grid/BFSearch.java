///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package search.Grid;
//
//import lejos.geom.Point;
//import lejos.nxt.Button;
//import lejos.nxt.LightSensor;
//import lejos.nxt.SensorPort;
//import lejos.nxt.addon.ColorSensor;
//import lejos.robotics.navigation.TachoPilot;
//import search.Grid.GridTraveler.Facing;
//import util.Queue;
//import util.RobotConstants;
//
///**
// *
// * @author Jeremiah Via
// */
//public class BFSearch {
//
//    GridTraveler traveler;
//    ColorSensor colorSensor;
//    Queue<Point> agendaList;
//    int BLUE = 3;
//
//    public BFSearch(TachoPilot pilot, LightSensor left, LightSensor right,
//                    ColorSensor colorSensor, Point currPoint, int height,
//                    int width, Facing currentFacing) {
//        traveler = new GridTraveler(pilot, left, right, currPoint, height, width,
//                                    currentFacing);
//        this.colorSensor = colorSensor;
//        agendaList = new Queue<Point>();
//        agendaList.push(currPoint);
//    }
//
//    boolean atGoal() {
//        return colorSensor.getColorNumber() == BLUE;
//    }
//
//    void search() {
//        Point point = agendaList.pop();
//        traveler.goTo(point);
//        generateSuccessors();
//    }
//
//    void generateSuccessors() {
//        if (traveler.currPoint.x < traveler.width)
//            agendaList.push(new Point(traveler.currPoint.x + 1, traveler.currPoint.y));
//        if (traveler.currPoint.x > 0)
//            agendaList.push(new Point(traveler.currPoint.x - 1, traveler.currPoint.y));
//        if (traveler.currPoint.y < traveler.height)
//            agendaList.push((new Point(traveler.currPoint.x, traveler.currPoint.y + 1)));
//        if (traveler.currPoint.y > 0)
//            agendaList.push(new Point(traveler.currPoint.x, traveler.currPoint.y - 1));
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
//        int height = 5, width = 5;
//        final Point start = new Point(0, 0);
//
//        BFSearch bf = new BFSearch(pilot, left, right, color, start, height,
//                                   width, Facing.EAST);
//
//        Button.waitForPress();
//
//        while (true){
//            bf.search();
//            if ( bf.atGoal() ) break;
//        }
//
//        System.out.println("SUCCESS!");
//        bf.traveler.pilot.rotate(720);
//
//    }
//}
