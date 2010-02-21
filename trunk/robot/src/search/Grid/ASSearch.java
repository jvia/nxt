/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.Grid;
import java.util.Stack;
import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import search.Grid.GridTraveler.Facing;
import util.Collections;
import util.Queue;
import util.RobotConstants;
/**
 * 
 * @author Jeremiah Via
 */
public class ASSearch
{

    GridTraveler traveler;
    ColorSensor colorSensor;
    Queue<PriorityPoint> agendaList;
    int BLUE = 3;
    int cost;
    Point goal;

    public ASSearch(TachoPilot pilot, LightSensor left, LightSensor right,
                    ColorSensor colorSensor, PriorityPoint currPoint, int height,
                    int width, Facing currentFacing) {
        traveler = new GridTraveler(pilot, left, right, currPoint, height, width,
                                    currentFacing);
        this.colorSensor = colorSensor;
        agendaList = new Queue<PriorityPoint>();
        agendaList.push(currPoint);
        cost = 0;
        goal = new Point(4,4);
    }

    boolean atGoal() {
        return colorSensor.getColorNumber() == BLUE;
    }

    void search() {
        Point point = agendaList.pop();
        traveler.goTo(point);
        cost++;
        generateSuccessors();
    }

    int fValue(Point p){
        return cost + (int) Math.sqrt(Math.pow(traveler.currPoint.x - p.x, 2) + Math.pow(traveler.currPoint.y - p.y, 2));
    }

    void generateSuccessors() {
        if (traveler.currPoint.x < traveler.width) {
            PriorityPoint p = new PriorityPoint(traveler.currPoint.x+1, traveler.currPoint.y, fValue(new Point(traveler.currPoint.x+1, traveler.currPoint.y)));
            agendaList.add(p);
            //agendaList.add(new Point(traveler.currPoint.x + 1,traveler.currPoint.y));
        }
        if (traveler.currPoint.y < traveler.height) {
            PriorityPoint p = new PriorityPoint(traveler.currPoint.x, traveler.currPoint.y+1, fValue(new Point(traveler.currPoint.x+1, traveler.currPoint.y)));
            agendaList.add(p);
          //  agendaList.add(new Point(traveler.currPoint.x, traveler.currPoint.y+ 1));
        }
        if (traveler.currPoint.x > 0) {
            PriorityPoint p = new PriorityPoint(traveler.currPoint.x-1, traveler.currPoint.y, fValue(new Point(traveler.currPoint.x+1, traveler.currPoint.y)));
            agendaList.add(p);
          //  agendaList.add(new Point(traveler.currPoint.x - 1,traveler.currPoint.y));
        }

        if (traveler.currPoint.y > 0) {
            PriorityPoint p = new PriorityPoint(traveler.currPoint.x, traveler.currPoint.y-1, fValue(new Point(traveler.currPoint.x+1, traveler.currPoint.y)));
            agendaList.add(p);
           // agendaList.add(new Point(traveler.currPoint.x, traveler.currPoint.y- 1));
        }

        Collections.sort(agendaList);

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

        PriorityPoint start = new PriorityPoint(0,0,0);

        int height = 5, width = 5;

        ASSearch as = new ASSearch(pilot, left, right, color, start, height,
                                   width, Facing.EAST);


        while (true) {
            as.search();
            if (as.atGoal())
                break;
        }

        System.out.println("SUCCESS!");
        as.traveler.pilot.rotate(720);
        Button.waitForPress();
    }
}

