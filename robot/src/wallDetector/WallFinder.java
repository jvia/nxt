package wallDetector;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Exercise 2, Part 2
 * @author Jeremiah Via, Michal Staniaszek
 */
public class WallFinder
{
    /**
     * Run program
     * @param args
     */
    public static void main(String[] args)
    {
        Behavior reverse = new Reverse();
        Behavior hitWall = new HitWall();
        Behavior[] behaviors = {hitWall, reverse};
        Arbitrator arbitrator = new Arbitrator(behaviors);
        arbitrator.start();
    }
}