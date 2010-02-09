/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.proto;

import java.awt.Point;
import lejos.nxt.Button;


/**
 * 
 * @author Jeremiah Via
 */
public class Search {

    Point[] grid = {new Point(0, 0), new Point(1, 0), new Point(2, 0),
                    new Point(0, 1), new Point(1, 1), new Point(2, 1),
                    new Point(0, 2), new Point(1, 2), new Point(2, 2)};
    Queue agendaList;
    Point start = grid[0]; // (0,0)
    Point goal = grid[7]; // (0,2)

    /**
     * Determines if the (X,Y) values of both points are equal.
     * @param p first point
     * @param q second point
     * @return true iff p.x == q.x && p.y == q.y
     */
    public boolean areEqual(Point p, Point q) {
        return (p.getX() == q.getX()) && (p.getY() == q.getY());
    }

    public void generateChildren(Point p) {
        // add Point(x-1, y), if possible
        if (p.getX() - 1 >= 0) {
            agendaList.push(new Point(p.x - 1, p.y));
        }

        // add Point(x+1, y), if possible
        if (p.getX() + 1 <= grid[grid.length - 1].getX()) {
            agendaList.push(new Point(p.x + 1, p.y));
        }

        // add Point(x, y-1), if possible
        if (p.getY() - 1 >= 0) {
            agendaList.push(new Point(p.x, p.y - 1));
        }

        // add Point(x, y+1), if possible
        if (p.getY() + 1 <= grid[grid.length - 1].getY()) {
            agendaList.push(new Point(p.x, p.y + 1));
        }
    }

    public void bfs(Point currentState, Point goalState) {
        if (areEqual(currentState, goalState)) {
            return;
        }
        else {
            generateChildren(currentState);
            Point p = (Point) agendaList.pop();
            System.out.println(p);
            bfs(p, goalState);
        }

    }

    /**
     * Makes a grid of width x and depth y.
     * @param x Width of grid.
     * @param y Depth of grid.
     * @return An array of Points.
     */
    public static Point[] makeGrid(int x, int y) {
        int i = 0, yVal = 0;
        Point[] grid = new Point[x * y];

        while (i < grid.length && yVal <= y) {
            for (int j = 0; j < x; j++) {
                grid[i] = new Point(j, yVal);
                i++;
            }
            yVal++;
        }
        System.out.println("Generated grid with " + grid.length + " points.");
        return grid;
    }

    /**
     * Prints out an array of points in a grid pattern.
     * @param points An array of points.
     */
    public static void printGrid(Point[] points) {

        int lastY = 0;
        for (Point p : points) {
            if (p.y != lastY) {
                System.out.println("");
                lastY = p.y;
            }
            System.out.print("[" + p.x + "," + p.y + "]");
        }
        System.out.println("");
    }

    public static Point[] generateGrid(int width, int height) {
        Point[] points = new Point[width * height];

        for (int i = 0, x = 0, y = 0; i < points.length; i++) {
            if (x == width) {
                y++;
                x = 0;
            }
            points[i] = new Point(x++, y);
        }
        return points;
    }


    

    public static void main(String[] args) throws InterruptedException {
        printGrid(generateGrid(3, 7));
        Button.waitForPress();
    }
}
    

