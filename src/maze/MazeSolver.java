package maze;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Given a maze that is simply connected, this algorithm is guaranteed
 * to eventually find an exit because it utilizes a simple wall-following
 * algorithm.
 *
 * This is because topologically, a simply-connected maze can be deformed into
 * a circle. So essentially, the robot is following the right of a circle
 * to the other exit.
 * 
 * Exercise 2, Part 3
 * Due: 21 January 2010
 * @author Jeremiah Via, Michal Staniaszek
 */
public class MazeSolver
{
    /**
     * Run program
     * @param args not used
     */
    public static void main(String[] args)
    {
        // behaviors need to be created so they can be used
        Behavior findWall     = new FindWall();
        Behavior reposition   = new Reposition();
        Behavior stopProgram  = new StopProgram();

        // we need to store the behaviors in a priority-descending array
        Behavior [] behaviors = {findWall, reposition, stopProgram};

        // the arbitrator will cycle through behaviors and execute the ones
        // that are need to be executed
        Arbitrator arbitrator = new Arbitrator(behaviors);
        arbitrator.start();
    }
}