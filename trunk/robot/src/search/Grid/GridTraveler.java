/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.Grid;

import java.util.ArrayList;
import lejos.geom.Point;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.TachoPilot;
import util.Queue;

/**
 * 
 * @author Jeremiah Via
 */
public class GridTraveler {

    boolean DEBUG = true;

    public enum Facing {

        NORTH, SOUTH, EAST, WEST
    };

    private final int MAX_BLACK = 43;
    private final int SENSOR_WHEEL_DISTANCE = 140;
    final TachoPilot pilot;
    final LightSensor left;
    final LightSensor right;
    Point currPoint;
    int height, width;
    private Facing currentFacing;
    Queue<String> moves;

    public GridTraveler(TachoPilot pilot, LightSensor left, LightSensor right,
                        Point currPoint, int height, int width,
                        Facing currentFacing) {
        this.pilot = pilot;
        this.left = left;
        this.right = right;
        this.currPoint = currPoint;
        this.height = height;
        this.width = width;
        this.currentFacing = currentFacing;
        moves = new Queue<String>();
    }

    /**
    //NSEW
    GridPoint[][] grid;
    GridPoint currentPoint;// = grid[0][0];
    GridPoint goalPoint;// = grid[1][2];
    ArrayList<String> moves = new ArrayList<String>();
    private static final boolean north = true, south = true, east = true, west =
    true,
    noNorth = false, noSouth = false, noEast = false, noWest = false;
    ColorSensor s = new ColorSensor(SensorPort.S2);

    public GridTraveler(TachoPilot pilot, LightSensor left, LightSensor right,
    Facing currentFacing, GridPoint currentPoint,
    GridPoint goalPoint, GridPoint[][] grid) {
    this.pilot = pilot;
    this.left = left;
    this.right = right;
    this.currentFacing = currentFacing;
    this.currentPoint = currentPoint;
    this.goalPoint = goalPoint;
    this.grid = grid;
    }

    public void setCurrentPoint(final GridPoint currentPoint) {
    this.currentPoint = currentPoint;
    }

     */
    void travelUntilJunction() {
        while (!atJunction()) {
            if (alignedWithLine()) {
                pilot.forward();
            }
            else if (isOverLine(left)) {
                //pilot.rotate(60, true);
                pilot.steer(250, 360, true);
            }
            else if (isOverLine(right)) {
                //pilot.rotate(-60, true);
                pilot.steer(-250, 360, true);
            }
        }
        System.out.println("Junction!");
    }

    boolean moveOverJunction() {
        if (!atJunction())
            return false;

        pilot.travel(SENSOR_WHEEL_DISTANCE);
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
                    rotate = 220;
                else if (currentFacing == Facing.EAST)
                    rotate = 120;
                else if (currentFacing == Facing.WEST)
                    rotate = -120;

                currentFacing = Facing.NORTH;
                break;

            case SOUTH:
                if (currentFacing == Facing.NORTH)
                    rotate = 220;
                else if (currentFacing == Facing.EAST)
                    rotate = -120;
                else if (currentFacing == Facing.WEST)
                    rotate = 120;

                currentFacing = Facing.SOUTH;
                break;

            case EAST:
                if (currentFacing == Facing.NORTH)
                    rotate = - 120;
                else if (currentFacing == Facing.SOUTH)
                    rotate = 120;
                else if (currentFacing == Facing.WEST)
                    rotate = 220;

                currentFacing = Facing.EAST;
                break;

            case WEST:
                if (currentFacing == Facing.NORTH)
                    rotate = 120;
                else if (currentFacing == Facing.SOUTH)
                    rotate = -120;
                else if (currentFacing == Facing.EAST)
                    rotate = 220;

                currentFacing = Facing.WEST;
                break;
            default:
                return;
        }

        sensor = (rotate < 0) ? right : left;
        turnUntilLine(sensor, rotate);
    }

    void north() {
        moveOverJunction();
        faceDirection(Facing.NORTH);
        travelUntilJunction();
    }

    void south() {
        moveOverJunction();
        faceDirection(Facing.SOUTH);
        travelUntilJunction();
    }

    void east() {
        moveOverJunction();
        faceDirection(Facing.EAST);
        travelUntilJunction();
    }

    void west() {
        moveOverJunction();
        faceDirection(Facing.WEST);
        travelUntilJunction();
    }

    void printPoint(Point point) {
        System.out.print("(" + (int) point.x + ", " + (int) point.y + ")");
    }

    public void goTo(Point point) {
        while (!currPoint.equals(point)) {
            if (currPoint.x < point.x) {
                currPoint.x++;
                moves.add("E");
            }
            else if (currPoint.x > point.x) {
                currPoint.x--;
                moves.add("W");
            }
            else if (currPoint.y < point.y) {
                currPoint.y++;
                moves.add("S");
            }
            else if (currPoint.y > point.y) {
                currPoint.y--;
                moves.add("N");
            }
        }

        
        if ( moves.size() > 0 )
            executeMoves(moves);
        pilot.stop();
    }


    void executeMoves(ArrayList<String> list) {
        while (list.size() != 0) {
            String move = list.remove(0);
            if (!DEBUG) {
                if (move.equals("E"))
                    east();
                else if (move.equals("N"))
                    north();
                else if (move.equals("W"))
                    west();
                else if (move.equals("S"))
                    south();
            }
        }
    }
}

