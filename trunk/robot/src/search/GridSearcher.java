/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class GridSearcher {

    TachoPilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                      RobotConstants.TRACK_WIDTH,
                                      RobotConstants.leftMotor,
                                      RobotConstants.rightMotor, true);
    LightSensor left = new LightSensor(SensorPort.S3);
    LightSensor right = new LightSensor(SensorPort.S4);
    static ColorSensor color = new ColorSensor(SensorPort.S2);
    GridPoint[][] grid = {
        {
            new GridPoint(0, 0, false, true, true, false),
            new GridPoint(1, 0, false, true, true, true),
            new GridPoint(2, 0, false, true, false, true)
        },
        {
            new GridPoint(0, 1, true, true, true, false),
            new GridPoint(1, 1, true, true, true, true),
            new GridPoint(2, 1, true, true, false, true)},
        {
            new GridPoint(0, 2, true, false, true, false),
            new GridPoint(1, 2, true, false, true, true),
            new GridPoint(2, 2, true, false, false, true)
        }
    };
    GridPoint start = grid[0][0];
    Traveler traveler = new Traveler(pilot, left, right, Traveler.Facing.EAST,
                                     start, null, grid);

    boolean atGoal() {
        return color.getColorNumber() == 3;
    }

    public static void main(String[] args) {
        GridSearcher g = new GridSearcher();
        GridPoint currentPoint = g.start;
        BreadthFirstSearch bfs = new BreadthFirstSearch(g.grid, currentPoint);
        
        Button.waitForPress();

        while (!g.atGoal()) {
           g.traveler.goTo(currentPoint);
            bfs.generateChildren();
            System.out.println(bfs);
            currentPoint = bfs.agendaList.pop();
            System.out.println(currentPoint);
        }


    }
}
