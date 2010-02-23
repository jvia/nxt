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

    boolean DEBUG = false;

    public enum Facing {

        NORTH, SOUTH, EAST, WEST
    };

    private final int MAX_BLACK = 43;
    private final int SENSOR_WHEEL_DISTANCE = 140;
    final TachoPilot pilot;
    final LightSensor left;
    final LightSensor right;
    GridPoint currPoint;
    int height, width;
    private Facing currentFacing;
    Queue<String> moves;

    public GridTraveler(TachoPilot pilot, LightSensor left, LightSensor right,
                        GridPoint currPoint, int height, int width,
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
        currPoint.y--;
        moveOverJunction();
        faceDirection(Facing.NORTH);
        travelUntilJunction();
    }

    void south() {
        currPoint.y++;
        moveOverJunction();
        faceDirection(Facing.SOUTH);
        travelUntilJunction();
    }

    void east() {
        currPoint.x++;
        moveOverJunction();
        faceDirection(Facing.EAST);
        travelUntilJunction();
    }

    void west() {
        currPoint.x--;
        moveOverJunction();
        faceDirection(Facing.WEST);
        travelUntilJunction();
    }

    void printPoint(Point point) {
        System.out.print("(" + (int) point.x + ", " + (int) point.y + ")");
    }

    public ArrayList<String> generateMoves(ArrayList<GridPoint> list){
        ArrayList<String>  route = new ArrayList<String>();
        GridPoint point1 = list.remove(0);
        while ( list.size() != 0){
            GridPoint point2 = list.remove(0);
            if (point1.x < point2.x)
                route.add("E");
            if (point1.x > point2.x)
                route.add("W");
            if (point1.y < point2.y)
                route.add("S");
            if (point1.y > point2.y)
                route.add("N");

            point1 = point2;
        }

        return route;
    }

    public void goTo(Point point) {
        while (!currPoint.equals(point)) {
            if (currPoint.x < point.x) {
                moves.add("E");
            }
            else if (currPoint.x > point.x) {
                moves.add("W");
            }
            else if (currPoint.y < point.y) {
                moves.add("S");
            }
            else if (currPoint.y > point.y) {
                moves.add("N");
            }
        }

        
        if ( moves.size() > 0 )
            executeMoves(moves);
        pilot.stop();
    }


    public void executeMoves(ArrayList<String> list) {
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

