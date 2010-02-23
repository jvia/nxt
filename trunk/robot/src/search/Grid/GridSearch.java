/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.Grid;

import java.util.ArrayList;
import lejos.nxt.Button;
import util.Collections;
import util.Comparator;

/**
 * 
 * @author Jeremiah Via
 */
public class GridSearch {

    ArrayList<GridPoint> blocked;
    int height, width;

    public GridSearch(int height, int width, ArrayList<GridPoint> blocked) {
        this.height = height;
        this.width = width;
        this.blocked = blocked;
    }

    ArrayList<GridPoint> route(GridPoint point) {
        ArrayList<GridPoint> route = new ArrayList<GridPoint>();
        while (point != null) {
            route.add(0, point);
            point = point.previous;
        }

        return route;
    }

    public ArrayList<GridPoint> dfs(GridPoint start, GridPoint goal) {
        assert (start.x < width && start.x > 0);
        assert (start.y < height && start.y > 0);
        assert (goal.x < width && goal.x > 0);
        assert (goal.y < height && goal.y > 0);

        ArrayList<GridPoint> agenda = new ArrayList<GridPoint>(),
                visited = new ArrayList<GridPoint>();

        start.previous = null;
        agenda.add(start);


        while (!(agenda.size() == 0)) {
            GridPoint current = agenda.remove(0);
            if (current.equals(goal))
                return route(current);
            else {
                visited.add(current);
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

    public ArrayList<GridPoint> bfs(GridPoint start, GridPoint goal) {
        assert (start.x < width && start.x > 0);
        assert (start.y < height && start.y > 0);
        assert (goal.x < width && goal.x > 0);
        assert (goal.y < height && goal.y > 0);

        ArrayList<GridPoint> agenda = new ArrayList<GridPoint>(),
                visited = new ArrayList<GridPoint>();

        start.previous = null;
        agenda.add(start);

        while (!(agenda.size() == 0)) {
            GridPoint current = agenda.remove(0);
            if (current.equals(goal))
                return route(current);
            else {
                visited.add(current);
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

    public ArrayList<GridPoint> aStar(GridPoint start, GridPoint goal) {
        assert (start.x < width && start.x > 0);
        assert (start.y < height && start.y > 0);
        assert (goal.x < width && goal.x > 0);
        assert (goal.y < height && goal.y > 0);


        ArrayList<GridPoint> agenda = new ArrayList<GridPoint>(),
                visited = new ArrayList<GridPoint>();

        start.previous = null;
        start.cost = 0;
        start.expectedCost = start.expectedCost(goal);


        agenda.add(start);

        while (!(agenda.size() == 0)) {
            GridPoint current = agenda.remove(0);
            if (current.equals(goal))
                return route(current);
            else {
                visited.add(current);
                agenda.addAll(current.nextPointsHeuristic(goal, visited, height,
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

    public static void main(String[] args) {
        ArrayList<GridPoint> blocked = new ArrayList<GridPoint>();
        GridSearch search = new GridSearch(5, 5, blocked);
        GridPoint start = new GridPoint(0, 0, null), goal = new GridPoint(3, 4,
                                                                          null);
        //ArrayList<GridPoint> bList = search.bfs(start, goal);
        // ArrayList<GridPoint> dList = search.dfs(start, goal);
        ArrayList<GridPoint> aList = search.aStar(start, goal);


        //System.out.println("BFS: " +bList.size());
        //System.out.println("DFS: " +dList.size());
        System.out.println("A*: " + aList.size());

        Button.waitForPress();
    }


}




