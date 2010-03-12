package search.Grid.test;

import java.io.PrintStream;
import lejos.nxt.Button;
import lejos.nxt.comm.RConsole;
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
        GridPoint start = new GridPoint(0, 0);
        GridPoint goal1 = new GridPoint(2, 2);

        RConsole.openBluetooth(60000);
        System.setOut(new PrintStream(RConsole.openOutputStream()));

        SearchTraveler st = new SearchTraveler();
        st.bfs(start, goal1);
        System.out.println("\n");

        Button.waitForPress();
        SearchTraveler st2 = new SearchTraveler();
        st2.dfs(start, goal1);

        Button.waitForPress();
        SearchTraveler st3 = new SearchTraveler();
        st3.aStars(start, goal1);

        RConsole.close();
    }
}
