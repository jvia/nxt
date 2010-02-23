/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.Grid.test;

import java.util.ArrayList;
import search.Grid.GridSearch;
import search.Grid.GridPoint;

/**
 * 
 * @author Jeremiah Via
 */
public class GridTest {

    public static void main(String[] args) {
        ArrayList<GridPoint> blocked = new ArrayList<GridPoint>();
        blocked.add(new GridPoint(1, 1, null));
        blocked.add(new GridPoint(2, 0, null));
        blocked.add(new GridPoint(3, 4, null));
        blocked.add(new GridPoint(5, 4, null));

        GridSearch g = new GridSearch(10, 10, blocked);

        GridPoint start = new GridPoint(0, 0, null);
        GridPoint goal = new GridPoint(5, 5, null);


        System.out.println("\nBreadth-first Search\n---------------");
        long begin = System.currentTimeMillis();
        ArrayList<GridPoint> blist = g.bfs(start, goal);
        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - begin) + " milliseconds\n" + blist.
                size() + " steps");
        for (GridPoint pw : blist)
            System.out.println(pw);



        System.out.println("\nDepth-first Search\n---------------");
        begin = System.currentTimeMillis();
        ArrayList<GridPoint> dlist = g.dfs(start, goal);
        end = System.currentTimeMillis();
        System.out.println("Time:" + (end - begin) + " milliseconds\n" + dlist.
                size() + " steps");
        for (GridPoint pw : dlist)
            System.out.println(pw);



        System.out.println("\nA* Search\n---------------");
        begin = System.currentTimeMillis();
        ArrayList<GridPoint> alist = g.aStar(start, goal);
        end = System.currentTimeMillis();
        System.out.println("Time:" + (end - begin) + " milliseconds\n" + alist.
                size() + " steps");
        for (GridPoint pw : alist)
            System.out.println(pw);
    }
}
