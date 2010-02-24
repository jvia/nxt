package search.Grid.test;

import lejos.nxt.Button;
import search.Grid.GridPoint;
import search.Grid.SearchTraveler;

/**
 * Class for testing out path generation using GridSearch and then traveling
 * there using the GridTraveler.
 *
 * Exercise 04
 * 24 February 2010
 * J. Via, M. Staniaszek
 */
public class SearchTravelerTest {

    public static void main(String[] args) {
        SearchTraveler st = new SearchTraveler();

        GridPoint start = new GridPoint(0, 0);
        GridPoint goal1 = new GridPoint(2, 3);
        GridPoint goal2 = new GridPoint(3,3);

        Button.waitForPress();

        st.bfs(start, goal1);
        System.out.println("\n");

        st.dfs(goal1, goal2);
        System.out.println("\n");

        st.aStars(goal2, start);
        System.out.println("\n");
    }
}
