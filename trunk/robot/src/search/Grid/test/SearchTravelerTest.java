package search.Grid.test;

import java.io.PrintStream;
import java.util.ArrayList;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.TachoPilot;
import search.Grid.GridPoint;
import search.Grid.GridTraveler.Facing;
import search.Grid.SearchTraveler;
import util.RobotConstants;

/**
 * Class for testing out path generation using GridSearch and then traveling
 * there using the GridTraveler.
 *
 * Exercise 04
 * 24 February 2010
 * J. Via, M. Staniaszek
 */
public class SearchTravelerTest {

    private static boolean BLUETOOTH = false;

    ;

    public static void main(String[] args) {
        GridPoint start = new GridPoint(0, 0);
        GridPoint goal1 = new GridPoint(3, 4);

        TachoPilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                          RobotConstants.TRACK_WIDTH,
                                          RobotConstants.leftMotor,
                                          RobotConstants.rightMotor,
                                          true);

        // left and right sensors
        LightSensor left = new LightSensor(SensorPort.S1);
        LightSensor right = new LightSensor(SensorPort.S4);

        // height & width of grid; be careful, increasing the size
        // increases the search exponentially
        int height = 5;
        int width = 5;

        // blocked points
        ArrayList<GridPoint> blocked = new ArrayList<GridPoint>();
        blocked.add(new GridPoint(2, 0));
        blocked.add(new GridPoint(3, 1));
        blocked.add(new GridPoint(3, 2));
        blocked.add(new GridPoint(3, 2));
        blocked.add(new GridPoint(0, 1));
        blocked.add(new GridPoint(0, 2));


        // create search traveler
        SearchTraveler st = new SearchTraveler(pilot, left, right, start,
                                               Facing.EAST, width, height,
                                               blocked);

        // we want to output to the console
        if (BLUETOOTH) {
            RConsole.openBluetooth(60000);
            System.setOut(new PrintStream(RConsole.openOutputStream()));
        }


        st.dfs(start, goal1);

//        SearchTraveler st2 = new SearchTraveler();
//        st2.dfs(start, goal1);

//        SearchTraveler st3 = new SearchTraveler();
//        st3.aStars(start, goal1);

        if (BLUETOOTH)
            RConsole.close();
    }
}
