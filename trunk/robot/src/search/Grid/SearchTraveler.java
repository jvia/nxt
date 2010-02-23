/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.Grid;

import java.awt.Point;
import java.util.ArrayList;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.TachoPilot;
import search.Grid.GridTraveler.Facing;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class SearchTraveler {
    private final boolean DEBUG = false;

    private ArrayList<GridPoint> blocked;
    private int height, width;
    private GridTraveler robot;
    private GridSearch search;
    private TachoPilot pilot;
    private LightSensor left, right;

    public SearchTraveler(){
       pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                          RobotConstants.TRACK_WIDTH,
                                          RobotConstants.leftMotor,
                                          RobotConstants.rightMotor,
                                          true);
        left = new LightSensor(SensorPort.S3);
        right = new LightSensor(SensorPort.S4);

        robot = new GridTraveler(pilot, left, right, new GridPoint(0,0), height, width,
                                 Facing.EAST);

        height = 5;
        width = 5;

        blocked = new ArrayList<GridPoint>();

        search = new GridSearch(height, width, blocked);
    }

    public void dfs(GridPoint start, GridPoint goal){
        ArrayList<GridPoint> route = search.dfs(start, goal);
        ArrayList<String> moves = robot.generateMoves(route);
        System.out.println(moves.size());

        for (String move : moves)
            System.out.print(move);

        if (!DEBUG)
            robot.executeMoves(moves);
    }

    
    public void bfs(GridPoint start, GridPoint goal){
        ArrayList<GridPoint> route = search.bfs(start, goal);
        ArrayList<String> moves = robot.generateMoves(route);

        for (String move : moves)
            System.out.print(move);

        if (!DEBUG)
            robot.executeMoves(moves);
    }

    public void aStars(GridPoint start, GridPoint goal){
        ArrayList<GridPoint> route = search.aStar(start, goal);
        ArrayList<String> moves = robot.generateMoves(route);

        for (String move : moves)
            System.out.print(move);

        if (!DEBUG)
            robot.executeMoves(moves);
    }
    



    private class Initializer {

        int startX, startY;
        int goalX, goalY;
        Facing startFacing;
        boolean selected = false;
        Button enter = Button.ENTER;
        Button left = Button.LEFT;
        Button right = Button.RIGHT;

        public Initializer() {

            startX = 0;
            startY = 0;
            goalX = 0;
            goalY = 0;
            startFacing = Facing.NORTH;

            enter.addButtonListener(new ButtonListener() {

                public void buttonPressed(Button b) {
                    selected = true;
                }

                public void buttonReleased(Button b) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });
        }

        public void initilizeValues() {
            // get startintg x
            selected = false;
            do {
                LCD.drawString("Starting X:" + startX , 0, 0);
            }
            while (!selected);
        }
    }

}
