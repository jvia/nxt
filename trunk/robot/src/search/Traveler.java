/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;
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
public class Traveler {

    private final TachoPilot pilot;
    private final LightSensor left;
    private final LightSensor right;
    private final int MAX_BLACK = 43;
    private final int SENSOR_WHEEL_DISTANCE = 100;
    private Facing currentFacing;
    //NSEW
    GridPoint[][] grid;
    GridPoint currentPoint;// = grid[0][0];
    GridPoint goalPoint;// = grid[1][2];
    ArrayList<String> moves = new ArrayList<String>();
    private static final boolean north = true, south = true, east = true, west = true,
                                noNorth = false, noSouth = false, noEast = false, noWest = false;
    ColorSensor s = new ColorSensor(SensorPort.S2);

    public enum Facing {

        NORTH, SOUTH, EAST, WEST
    };

    public Traveler(TachoPilot pilot, LightSensor left, LightSensor right, Facing currentFacing, GridPoint currentPoint, GridPoint goalPoint, GridPoint[][] grid) {
        this.pilot = pilot;
        this.left = left;
        this.right = right;
        this.currentFacing = currentFacing;
        this.currentPoint = currentPoint;
        this.goalPoint = goalPoint;
        this.grid = grid;
    }
    public void setCurrentPoint(final GridPoint currentPoint){
        this.currentPoint = currentPoint;
        }

    void travelUntilJunction() {
        while (!atJunction()) {
//            if (s.getColorNumber() == 3){
//                pilot.stop();return;
//            }
            if (alignedWithLine()) {
                pilot.forward();
            }
            else if (isOverLine(left)) {
                pilot.steer(180, 360, true);
            }
            else if (isOverLine(right)) {
                pilot.steer(-180, 360, true);
            }
        }
    }

    boolean moveOverJunction() {
        if (!atJunction())
            return false;

        pilot.travel(0.75f * RobotConstants.SINGLE_ROTATION);
        return true;
    }

    boolean isOverLine(LightSensor sensor) {
        return sensor.getLightValue() < MAX_BLACK;
    }

    boolean atJunction() {
        return isOverLine(left) && isOverLine(right);
    }

    boolean alignedWithLine() {
        return !isOverLine(left) && !isOverLine(right);
    }

    void turnRightUntilLine() {
        turnUntilLine(left, 120);
    }

    void turnLeftUntilLine() {
        turnUntilLine(right, 120);
    }

    boolean turnUntilLine(LightSensor sensor, int maxTurn) {
        int halfTurn = maxTurn / 2;

        pilot.rotate(halfTurn);

        pilot.rotate(halfTurn, true);
        while (pilot.isMoving())
            if (isOverLine(sensor))
                return true;
        return false;
    }

    void faceDirection(Facing goal) {
        if (currentFacing == goal)
            return;

        int rotate = 0;
        LightSensor sensor = null;

        switch (goal) {
            case NORTH:
                if (currentFacing == Facing.SOUTH)
                    rotate = 180;
                else if (currentFacing == Facing.EAST)
                    rotate = 90;
                else if (currentFacing == Facing.WEST)
                    rotate = -90;

                currentFacing = Facing.NORTH;
                break;
                
            case SOUTH:
                if (currentFacing == Facing.NORTH)
                    rotate = 180;
                else if (currentFacing == Facing.EAST)
                    rotate = -90;
                else if (currentFacing == Facing.WEST)
                    rotate = 90;

                currentFacing = Facing.SOUTH;
                break;
                
            case EAST:
                if (currentFacing == Facing.NORTH)
                    rotate = - 90;
                else if (currentFacing == Facing.SOUTH)
                    rotate = 90;
                else if (currentFacing == Facing.WEST)
                    rotate = 180;

                currentFacing = Facing.EAST;
                break;
                
            case WEST:
                if (currentFacing == Facing.NORTH)
                    rotate = 90;
                else if (currentFacing == Facing.SOUTH)
                    rotate = -90;
                else if (currentFacing == Facing.EAST)
                    rotate = 180;

                currentFacing = Facing.WEST;
                break;
        }

        sensor = (rotate < 0) ? right : left;
        turnUntilLine(sensor, rotate);
    }

    void north(){
        moveOverJunction();
        faceDirection(Facing.NORTH);
        travelUntilJunction();
    }
    
    void south(){
        moveOverJunction();
        faceDirection(Facing.SOUTH);
        travelUntilJunction();
    }

    void east(){
        moveOverJunction();
        faceDirection(Facing.EAST);
        travelUntilJunction();
    }

    void west(){
        moveOverJunction();
        faceDirection(Facing.WEST);
        travelUntilJunction();
    }
    
    // need to be able to go to next point in search
    void goTo(GridPoint nextPoint){
        while (!currentPoint.equals(nextPoint)){
            if (currentPoint.getX() < nextPoint.getX() && currentPoint.east){
                currentPoint = grid[currentPoint.getY()][currentPoint.getX()+1];
                moves.add("E");
            }
            if (currentPoint.getX() > nextPoint.getX() && currentPoint.west){
                currentPoint = grid[currentPoint.getY()][currentPoint.getX()-1];
                moves.add("W");
            }
            if (currentPoint.getY() < nextPoint.getY() && currentPoint.south){
                currentPoint = grid[currentPoint.getY()+1][currentPoint.getX()];
                moves.add("S");
            }
            if (currentPoint.getY() > nextPoint.getY() && currentPoint.south){
                currentPoint = grid[currentPoint.getY()-1][currentPoint.getX()];
                moves.add("N");
            }
        }
        executeMoves(moves);
    }

    void executeMoves(ArrayList<String> list){
        while (list.size() != 0){
            String move = list.remove(0);
            if (move.equals("E")) east();
            else if (move.equals("N")) north();
            else if (move.equals("W")) west();
            else if (move.equals("S")) south();
        }
    }

    /////////////////////////////////////////////////////////
    public static void main(String[] args) {
        TachoPilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                          RobotConstants.TRACK_WIDTH,
                                          RobotConstants.leftMotor,
                                          RobotConstants.rightMotor, true);
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);

        GridPoint[][] grid =
    {
        {new GridPoint(0, 0, noNorth, south, east, noWest), new GridPoint(1, 0,false, true, true, true), new GridPoint(2, 0, false, true, false, true)},
        {new GridPoint(0, 1, true, true, true, false), new GridPoint(1, 1, true, true, true, true), new GridPoint( 2, 1, true, true, false, true)},
        {new GridPoint(0, 2, true, false, true, false), new GridPoint(1, 2, true, false, true, true), new GridPoint(2, 2, true, false, false, true)}
    };



        GridPoint[][] grid2 =
    {
        {new GridPoint(0, 0, noNorth, south, east, noWest), new GridPoint(1, 0,false, false, true, true), new GridPoint(2, 0, false, true, false, true)},
        {new GridPoint(0, 1, true, true, true, false), new GridPoint(1, 1, false, true, true, true), new GridPoint( 2, 1, true, true, false, true)},
        {new GridPoint(0, 2, true, false, true, false), new GridPoint(1, 2, true, false, true, true), new GridPoint(2, 2, true, false, false, true)}
    };

        GridPoint start = grid[0][0],
                  goal = grid[0][1];

        System.out.println(start + " " + goal);
        Traveler t = new Traveler(pilot, left, right, Facing.EAST, start, goal, grid2);

        Button.waitForPress();

        t.goTo(t.goalPoint);
    }
}
