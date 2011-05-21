package patterns;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Exercise 2, Part 1
 * Due: 19 January 2010
 * @author Jeremiah Via, Michal Staniaszek
 */
public class PatternMover
{
    /**
     * Runs the program
     * @param args
     */
    public static void main(String[] args)
    {
        // mark will perform these behaviors
        Behavior pace   = new Pace();
        Behavior square = new Square();

        // behaviors are added in priority-descending order
        Behavior [] behaviors = {pace, square};

        // arbitrator is created to cycle through behaviors and decide
        // which behaviors to perform at any given point in time
        Arbitrator arbitrator = new Arbitrator( behaviors );
        arbitrator.start();
    }
}