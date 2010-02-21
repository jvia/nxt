/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.Grid;

import java.util.ArrayList;
import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import search.Grid.GridTraveler.Facing;
import util.RobotConstants;
import util.Stack;

/**
 * 
 * @author Jeremiah Via
 */
public class DFSearch {

    GridTraveler traveler;
    ColorSensor colorSensor;
    Stack<Point> agendaList;
    int BLUE = 3;

    public DFSearch(TachoPilot pilot, LightSensor left, LightSensor right,
                    ColorSensor colorSensor, Point currPoint, int height,
                    int width, Facing currentFacing) {
        traveler = new GridTraveler(pilot, left, right, currPoint, height, width,
                                    currentFacing);
        this.colorSensor = colorSensor;
        agendaList = new Stack<Point>();
        agendaList.push(currPoint);
    }

    boolean atGoal() {
        return colorSensor.getColorNumber() == BLUE;
    }

    void search() {
        Point point = agendaList.pop();
        traveler.goTo(point);
        generateSuccessors();
    }

    void generateSuccessors() {
        if (traveler.currPoint.x < traveler.width) {
            agendaList.add(new Point(traveler.currPoint.x + 1,
                                        traveler.currPoint.y));
        }
        if (traveler.currPoint.y < traveler.height) {
            agendaList.add(new Point(traveler.currPoint.x, traveler.currPoint.y
                                                              + 1));
        }
        if (traveler.currPoint.x > 0) {
            agendaList.add(new Point(traveler.currPoint.x - 1,
                                        traveler.currPoint.y));
        }

        if (traveler.currPoint.y > 0) {
            agendaList.add(new Point(traveler.currPoint.x, traveler.currPoint.y
                                                              - 1));
        }

        for (int i = 0; i < agendaList.size() && i < 3; i++){
            traveler.printPoint(agendaList.get(i));
        }
        System.out.println("\n------");
        
    }

    public static void main(String[] args) {
        TachoPilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                          RobotConstants.TRACK_WIDTH,
                                          RobotConstants.leftMotor,
                                          RobotConstants.rightMotor, true);
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);
        ColorSensor color = new ColorSensor(SensorPort.S2);

        int height = 5, width = 5;
        final Point start = new Point(0, 0);

        DFSearch df = new DFSearch(pilot, left, right, color, start, height,
                                   width, Facing.EAST);


        while (true) {
            df.search();
            if (df.atGoal())
                break;
        }

        System.out.println("SUCCESS!");
        df.traveler.pilot.rotate(720);
        Button.waitForPress();
    }
}


