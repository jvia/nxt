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
        GridPoint goal1 = new GridPoint(2, 3);
        GridPoint goal2 = new GridPoint(3,3);
        GridPoint goal3 = new GridPoint(0,0);

        Button.waitForPress();


        st.bfs(start, goal1);
        System.out.println("\n");

        st.dfs(goal1, goal2);
        System.out.println("\n");

        st.aStars(goal2, start);
        System.out.println("\n");


    }
}
