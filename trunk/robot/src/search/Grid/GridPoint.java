package search.Grid;

import java.awt.Point;
import java.util.ArrayList;

/**
 * A class to define points in a grid. They can generate new states
 * and are meant to be used in searching.
 *
 * Exercise 04
 * 24 February 2010
 * J. Via, M. Staniaszek
 */
public class GridPoint extends Point implements Comparable<GridPoint> {

    protected GridPoint previous;
    protected int cost, expectedCost;

    /**
     * Constructs a GridPoint object.
     * @param x x-coordinate
     * @param y y-coorindate
     * @param previous the GridPoint that leads to this point
     * @param cost cost to get to this node (used in heuristic search)
     * @param expectedCost expected cost to solution (used in heuristic search)
     */
    public GridPoint(int x, int y, GridPoint previous, int cost, int expectedCost) {
        super(x, y);
        this.previous = previous;
        this.cost = cost;
        this.expectedCost = expectedCost;
    }

    /**
     * Simplified constructor used when heuristics aren't needed.
     * @param x x-coordinate
     * @param y y-coorindate
     * @param previous the GridPoint that leads to this point
     */
    public GridPoint(int x, int y, GridPoint previous) {
        this(x, y, previous, 0, 0);
    }

    /**
     * Simplified constructor when only a Point is needed.
     * @param x x-coordinate
     * @param y y-coorindate
     */
    public GridPoint(int x, int y) {
        this(x, y, null, 0, 0);
    }

    /**
     * Generates all the posible legal successor states of the current point
     * and adds them to a list.
     * 
     * @param visited a list of visited points
     * @param height height of the grid
     * @param width width of the grid
     * @return a list of successor points
     */
    public ArrayList<GridPoint> nextPoints(final ArrayList<GridPoint> visited, int height, int width) {

        // temporary holder
        ArrayList<GridPoint> children = new ArrayList<GridPoint>();

        // four possible successors, adds them if they are valid
        if (x < width)
            children.add(new GridPoint(x + 1, y, this));
        if (x > 0)
            children.add(new GridPoint(x - 1, y, this));
        if (y < height)
            children.add(new GridPoint(x, y + 1, this));
        if (y > 0)
            children.add(new GridPoint(x, y - 1, this));

        // Remove nodes that we've visited before.
        // Can't use removeAll(Collection c) because lejos hates us
        for (int i = 0; i < children.size(); i++) {
            if (visited.contains(children.get(i))) {
                children.remove(i);
                i = 0;
            }
        }
        
        return children;
    }

    /**
     * Similar to nextPoints in that it generates the successors to the curent
     * point but it takes into account a cost + expected cost.
     * 
     * @param goal goal point used to calculate how far the point is
     *             from the goal
     * @param visited a list of previously visited points
     * @param height height of the grid
     * @param width width of the crid
     * @param cost current cost to reach present location
     * @return a list of points with heuristic values taken into account
     */
    public ArrayList<GridPoint> nextPointsHeuristic(final GridPoint goal, final ArrayList<GridPoint> visited,
                                                    int height, int width,int cost) {
        ArrayList<GridPoint> children = new ArrayList<GridPoint>();

        if (x < width) {
            GridPoint pt = new GridPoint(x + 1, y, this);
            pt.cost = cost;
            pt.expectedCost = expectedCost(goal);
            children.add(pt);
        }
        if (x > 0) {
            GridPoint pt = new GridPoint(x - 1, y, this);
            pt.cost = cost;
            pt.expectedCost = expectedCost(goal);
            children.add(pt);
        }
        if (y < height) {
            GridPoint pt = new GridPoint(x, y + 1, this);
            pt.cost = cost;
            pt.expectedCost = expectedCost(goal);
            children.add(pt);
        }
        if (y > 0) {
            GridPoint pt = new GridPoint(x, y - 1, this);
            pt.cost = cost;
            pt.expectedCost = expectedCost(goal);
            children.add(pt);
        }


        // remove visited nodes
        for (int i = 0; i < children.size(); i++) {
            if (visited.contains(children.get(i))) {
                children.remove(i);
                i = 0;
            }
        }

        return children;
    }

    /**
     * Calculates the expected cost to reach a given point from the current point.
     * It does in it in the form of:
     *          f(x) = g(x) + h(x)
     * where
     *          g(x) = accumulated cost
     *          h(x) = estimated cost to goal
     *
     * @param point point to calculate cost to
     * @return expected cost to travel to the point
     */
    public int expectedCost(GridPoint point) {
        expectedCost = cost + (int) Math.sqrt(Math.pow(this.x - point.x, 2)
                                              + Math.pow(this.x - point.y, 2));
        return expectedCost;
    }

    /**
     * Determines if two GridPoints are equal to one another. Equality is
     * simply determine by having the same (X,Y) values because search can
     * reach the same point in different ways resulting in different parents,
     * costs, etc. 
     *
     * @param point point whose equality is in question
     * @return true if they are equal, false otherwise
     */
    public boolean equals(GridPoint point) {
        return this.x == point.x && this.y == point.y;
    }

    /**
     * Returns a String representation of the object. Useful for debugging.
     * @return String of object
     */
    @Override
    public String toString() { return "(" + x + ", " + y + ")"; }

    /**
     * Compares two GridPoints and returns an integer relating where they are
     * in relation to each other.
     *
     * N.B. it doesn't specify how far away they should be from ecah other
     * 
     * @param o GridPoint to compareTo
     * @return              { this < o : negative value }
     *            (this, o) { this = o : 0              }
     *                      { this > o : positive value }
     */
    public int compareTo(GridPoint o) {
       // return (o.cost+o.expectedCost) - (this.cost+this.expectedCost);// test this
        Integer hOfO1 = cost + expectedCost;
        Integer hOfO2 = o.cost + o.expectedCost;

        return hOfO1.compareTo(hOfO2);
    }
}


