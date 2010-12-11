/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.proto;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Jeremiah Via & Michal Staniaszek
 */
public class GridSearch {

    Point[] grid = {new Point(0, 0), new Point(1, 0), new Point(2, 0),
        new Point(0, 1), new Point(1, 1), new Point(2, 1),
        new Point(0, 2), new Point(1, 2), new Point(2, 2)};
    LinkedList<Point> agendaList;
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
            agendaList.addLast(new Point(p.x - 1, p.y));
        }

        // add Point(x+1, y), if possible
        if (p.getX() + 1 <= grid[grid.length - 1].getX()) {
            agendaList.addLast(new Point(p.x + 1, p.y));
        }

        // add Point(x, y-1), if possible
        if (p.getY() - 1 >= 0) {
            agendaList.addLast(new Point(p.x, p.y - 1));
        }

        // add Point(x, y+1), if possible
        if (p.getY() + 1 <= grid[grid.length - 1].getY()) {
            agendaList.addLast(new Point(p.x, p.y + 1));
        }
    }

    public void bfs(Point currentState, Point goalState) {
        if (areEqual(currentState, goalState)) {
            return;
        } else {
            generateChildren(currentState);
            Point p = agendaList.removeFirst();
            System.out.println(p);
            bfs(p, goalState);
        }

    }

    /**
     * Makes a grid of width x and depth y.
     * @param x Width of grid.
     * @param y Depth of grid.
     * @return
     */
    public static Point[] makeGrid(int x, int y) {
        int i = 0, yVal = 0;
        Point[] grid = new Point[x * y];

        while (i < grid.length && yVal <= y) {
            for (int j = 0; j < x; j++) {
                grid[i] = new Point(j, yVal);
            }
            yVal++;
            i++;
        }

        System.out.println("Generated grid with " + grid.length + "points.");
        return grid;
    }

    public static void printGrid(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            System.out.println("Point " + i + " = " + p[i]);
        }
    }

    public static void main(String[] args) {
        printGrid(makeGrid(4,4));
    }
}
