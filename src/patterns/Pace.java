package patterns;

/**
 * Exercise 2, Part 1
 * Due: 21 January 2010
 * @author Jeremiah Via, Michal Staniaszek
 */
public class Pace extends Pattern
{
    /**
     * Defines a pacing pattern.
     * requires: nothing
     * ensures: nothing
     */
    public void action()
    {
        for(int i = 0; i < 2; i++)
        {
            getPilot().travel(1000);
            getPilot().rotate(180);
        }
    }

    /**
     * Takes control if button has been pressed once
     * @return true if button count is 1, false otherwise
     */
    public boolean takeControl()
    {   return (getButtonCount() == 1); }
}
