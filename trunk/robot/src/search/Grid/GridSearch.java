package search.Grid;

import java.util.ArrayList;
import util.Collections;

/**
 * Searches for a route in the grid that will take it to a specified
 * goal point
 *
 * Exercise 04
 * 24 February 2010
 * J. Via, M. Staniaszek
 */
public class GridSearch {

    ArrayList<GridPoint> blocked;
    int height, width;

    /**
     * Constructs the GridSearch objects.
     *
     * @param height height of the grid in which to search
     * @param width width of the grid in which to search
     * @param blocked a list of blocked points
     */
    public GridSearch(int height, int width, ArrayList<GridPoint> blocked) {
        this.height = height;
        this.width = width;
        this.blocked = blocked;
    }

    /**
     * This method is given the current point that matches the goal point
     * and then precedes to move backwards through the previous points in similar
     * fashion to a linked-list. Doing this, it constructs the route of points
     * that lead to the solution.
     *
     * @param point current point that is equal to the goal point
     * @return list of points from start to goal
     */
    private ArrayList<GridPoint> route(GridPoint point) {
        ArrayList<GridPoint> route = new ArrayList<GridPoint>();
        while (point != null) {
            route.add(0, point);
            point = point.previous;
        }

        return route;
    }

    /**
     * Performs depth-first search in order to find a route from the start
     * point to the goal point. In practice, this search is the quickest and
     * yeilds, by far, the worst route.
     * 
     * @param start starting point
     * @param goal goal point
     * @return the list of points from the start to the goal
     */
    public ArrayList<GridPoint> dfs(GridPoint start, GridPoint goal) {
        assert (start.x < width && start.x > 0);
        assert (start.y < height && start.y > 0);
        assert (goal.x < width && goal.x > 0);
        assert (goal.y < height && goal.y > 0);

        /* Points to visit */
        ArrayList<GridPoint> agenda = new ArrayList<GridPoint>(),
                /* Points visited */
                visited = new ArrayList<GridPoint>();

        /* previous is set to null so that route gneration won't
        go further than it should */
        start.previous = null;

        /* add first item to agenda */
        agenda.add(start);


        /* Pop off first item in agenda, if it's the goal, we return the route
         * otherwise we add the next generation of points to visit
         */
        while (!(agenda.size() == 0)) {
            // output agenda
            for (GridPoint p : agenda)
                System.out.print(p + " ");
            System.out.println("");


            GridPoint current = agenda.remove(0);
            if (current.equals(goal))
                return route(current);
            else {
                visited.add(current);
                /* adds points to the begining */
                agenda.addAll(0, current.nextPoints(visited, height, width));
                for (int i = 0; i < agenda.size(); i++) {
                    if (blocked.contains(agenda.get(i))) {
                        agenda.remove(i);
                        i = 0;
                    }
                }
            }
        }

        return route(start);
    }

    /**
     * Performs breadth-first search to find a route from a start point to a
     * goal point. This search will yield an optimal solution but will take
     * a while to do so.
     * 
     * @param start
     * @param goal
     * @return
     */
    public ArrayList<GridPoint> bfs(GridPoint start, GridPoint goal) {
        assert (start.x < width && start.x > 0);
        assert (start.y < height && start.y > 0);
        assert (goal.x < width && goal.x > 0);
        assert (goal.y < height && goal.y > 0);

        /* Points to visit */
        ArrayList<GridPoint> agenda = new ArrayList<GridPoint>();

        /* Points visited */
        ArrayList<GridPoint> visited = new ArrayList<GridPoint>();


        /* Previous is set to null so that route gneration won't
        go further than it should */
        start.previous = null;

        /* Add first item to agenda */
        agenda.add(start);

        /* Pop off first item in agenda, if it's the goal, we return the route
         * otherwise we add the next generation of points to visit
         */
        while (!(agenda.size() == 0)) {

            GridPoint current = agenda.remove(0);
            //System.out.println(agenda.size() + " items in the agenda. Expanding " + current);
           // try{Thread.sleep(100);}catch (InterruptedException ie){}
            if (current.equals(goal))
                return route(current);
            else {
                visited.add(current);
                /* Adds points to the end */
                agenda.addAll(current.nextPoints(visited, height, width));

                for (int i = 0; i < agenda.size(); i++) {
                    if (blocked.contains(agenda.get(i))) {
                        agenda.remove(i);
                        i = 0;
                    }
                }
            }
        }

        return route(start);
    }

    /**
     * Similar to breadth-first search but it uses a heuristic to sort the list
     * of points to visist. Trys to find the lowest cost route.
     * 
     * @param start starting point
     * @param goal goal point
     * @return a list of the points that lead from the start point to the
     *         goal point
     */
    public ArrayList<GridPoint> aStar(GridPoint start, GridPoint goal) {
        assert (start.x < width && start.x > 0);
        assert (start.y < height && start.y > 0);
        assert (goal.x < width && goal.x > 0);
        assert (goal.y < height && goal.y > 0);


        /* Points to visit */
        ArrayList<GridPoint> agenda = new ArrayList<GridPoint>(),
                /* Points visited */
                visited = new ArrayList<GridPoint>();

        /* Previous is set to null so that route gneration won't
        go further than it should */
        start.previous = null;
        start.cost = 0;
        start.expectedCost = start.expectedCost(goal);

        /* Add first item to agenda */
        agenda.add(start);

        /* Pop off first item in agenda, if it's the goal, we return the route
         * otherwise we add the next generation of points to visit
         */
        while (!(agenda.size() == 0)) {
            // output agenda
            for (GridPoint p : agenda)
                System.out.print(p + " ");
            System.out.println("");

            GridPoint current = agenda.remove(0);
            if (current.equals(goal))
                return route(current);
            else {
                visited.add(current);
                agenda.addAll(0,
                              current.nextPointsHeuristic(goal, visited, height,
                                                          width, start.cost++));
                if (blocked != null)
                    for (int i = 0; i < agenda.size(); i++) {
                        if (blocked.contains(agenda.get(i))) {
                            agenda.remove(i);
                            i = 0;
                        }
                    }
                Collections.sort(agenda);
            }
        }

        return route(start);
    }
}
