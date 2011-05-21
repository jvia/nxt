package patterns;

/**
 * Exercise 2, Part 1
 * Due: 21 January 2010
 * @author Jeremiah Via, Michal Staniaszek
 */
public class Square extends Pattern
{

    public boolean takeControl()
    {   return (getButtonCount() == 2); }

    public void action()
    {
        
            getPilot().travel(500);
            getPilot().rotate(90 );
            getPilot().travel(500);
            getPilot().rotate(90);
          
    }
}
