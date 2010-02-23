/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.Grid;

import java.awt.Point;
import java.util.ArrayList;
import lejos.nxt.Button;

/**
 * 
 * @author Jeremiah Via
 */
public class GridPoint extends Point implements Comparable<GridPoint> {

    GridPoint previous;
    int cost, expectedCost;

    public GridPoint(int x, int y, GridPoint previous, int cost,
                     int expectedCost) {
        super(x, y);
        this.previous = previous;
        this.cost = cost;
        this.expectedCost = expectedCost;
    }

    public GridPoint(int x, int y, GridPoint previous) {
        this(x, y, previous, 0, 0);
    }

    public GridPoint(int x, int y) {
        this(x, y, null, 0, 0);
    }

    public ArrayList<GridPoint> nextPoints(final ArrayList<GridPoint> visited,
                                           int height, int width) {
        ArrayList<GridPoint> children = new ArrayList<GridPoint>();
        if (x < width)
            children.add(new GridPoint(x + 1, y, this));
        if (x > 0)
            children.add(new GridPoint(x - 1, y, this));
        if (y < height)
            children.add(new GridPoint(x, y + 1, this));
        if (y > 0)
            children.add(new GridPoint(x, y - 1, this));

        for (int i = 0; i < children.size(); i++) {
            if (visited.contains(children.get(i))) {
                children.remove(i);
                i = 0;
            }
        }
        return children;
    }

    public ArrayList<GridPoint> nextPointsHeuristic(final GridPoint goal,
                                                    final ArrayList<GridPoint> visited,
                                                    int height, int width,
                                                    int cost) {
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

    public int expectedCost(GridPoint point) {
        expectedCost = cost + (int) Math.sqrt(Math.pow(this.x - point.x, 2)
                                              + Math.pow(this.x - point.y, 2));
        return expectedCost;
    }

    public boolean equals(GridPoint point) {
        return this.x == point.x && this.y == point.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        GridPoint p = new GridPoint(0, 0, null);
        GridPoint q = new GridPoint(1, 0, p);
        GridPoint r = new GridPoint(0, 1, p);
        GridPoint w = new GridPoint(2, 0, r);

        ArrayList<GridPoint> list = new ArrayList<GridPoint>();
        list.add(p);
        list.add(q);
        list.add(r);
        list.add(w);

        for (GridPoint l : list)
            System.out.print(l);
        System.out.println();

        System.out.print(list.contains(p));
        System.out.print(list.contains(r));
        System.out.print(list.contains(w));

        list.remove(p);
        list.remove(q);

        for (GridPoint l : list)
            System.out.print(l);
        System.out.println();

        System.out.print(list.contains(p));
        System.out.print(list.contains(r));
        System.out.print(list.contains(w));

        Button.waitForPress();
    }

    public int compareTo(GridPoint o) {
        Integer hOfO1 = cost + expectedCost;
        Integer hOfO2 = o.cost + o.expectedCost;

        return hOfO1.compareTo(hOfO2);
    }
}


