/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import lejos.nxt.Button;
import util.Queue;

/**
 * 
 * @author Jeremiah Via
 */
public class BreadthFirstSearch {

    private static final boolean north = true, south = true, east = true, west =
                                                                          true,
            noNorth = false, noSouth = false, noEast = false, noWest = false;
    Queue<GridPoint> agendaList;
    GridPoint currPoint, goalPoint;
    GridPoint[][] grid;

    public BreadthFirstSearch(GridPoint[][] grid, GridPoint currPoint) {
        this.grid = grid;
        System.out.println("1");try {Thread.sleep(500);}catch (Exception e) {}
        this.currPoint = currPoint;
        agendaList = new Queue<GridPoint>();
        System.out.println("2");try {Thread.sleep(500);}catch (Exception e) {}
        agendaList.push(currPoint);
        System.out.println("3");try {Thread.sleep(500);}catch (Exception e) {}
    }

    public GridPoint getcurrPoint() {
        return currPoint;
    }

    public void setcurrPoint(GridPoint currPoint) {
        this.currPoint = currPoint;
    }

    public GridPoint getgoalPoint() {
        return goalPoint;
    }

    public void setgoalPoint(GridPoint goalPoint) {
        this.goalPoint = goalPoint;
    }

    public void add(GridPoint node) {
        agendaList.push(node);
    }

    public void remove() {
        currPoint = agendaList.pop();
    }

    public void generateChildren() {
        GridPoint lastPoint = currPoint;
        currPoint = agendaList.pop();

        

        if (currPoint.getX() + 1 < grid.length && currPoint.east)
            agendaList.add(grid[currPoint.getX() + 1][currPoint.getY()]);

        if (currPoint.getX() - 1 > 0 && currPoint.west)
            agendaList.add(grid[currPoint.getX() - 1][currPoint.getY()]);

        if (currPoint.getY() + 1 < grid[0].length && currPoint.south)
            agendaList.add(grid[currPoint.getX()][currPoint.getY() + 1]);

        if (currPoint.getY() - 1 > 0 && currPoint.north)
            agendaList.add(grid[currPoint.getX()][currPoint.getY() - 1]);
    }

    public boolean isGoal(GridPoint node) {
        return node.getX() == currPoint.getX() && node.getY()
                                                  == currPoint.getY();
    }

    @Override
    public String toString() {
        String ret = "";
        for (GridPoint point : agendaList) {
            ret += point + " ";
        }

        return ret;
    }

    public static void main(String[] args) {

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

        GridPoint curr = grid[0][0];
        BreadthFirstSearch s = new BreadthFirstSearch(grid, curr);
        Button.waitForPress();


        System.out.println(s.toString());
        s.generateChildren();
        System.out.println(s);
        s.generateChildren();
        System.out.println(s);
        s.generateChildren();
        System.out.println(s);


        Button.waitForPress();
    }
}
