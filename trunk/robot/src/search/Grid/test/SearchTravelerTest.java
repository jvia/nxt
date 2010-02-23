/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.Grid.test;

import lejos.nxt.Button;
import search.Grid.GridPoint;
import search.Grid.SearchTraveler;

/**
 * 
 * @author Jeremiah Via
 */
public class SearchTravelerTest {

    public static void main(String[] args) {
        SearchTraveler st = new SearchTraveler();

        GridPoint start = new GridPoint(0, 0);
        GridPoint goal = new GridPoint(2, 3);

        Button.waitForPress();


//        st.bfs(start, goal);
//        Button.waitForPress();
//        System.out.println("\n");

//        st.dfs(start, goal);
//        Button.waitForPress();
//        System.out.println("\n");

        st.aStars(start, goal);
        Button.waitForPress();
        System.out.println("\n");


    }
}
