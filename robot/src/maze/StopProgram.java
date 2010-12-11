package maze;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.robotics.subsumption.Behavior;

/**
 * Exercise 2, Part 3
 * Due: 21 January 2010
 * @author Jeremiah Via, Michal Staniaszek
 */
public class StopProgram implements Behavior, ButtonListener
{
    private boolean buttonPressed;

    /**
     * Creates StopProgram behavior. </br>
     * requires: nothing </br>
     * ensures: behavior is created </br>
     */
    public StopProgram()
    {
        Button.ENTER.addButtonListener(this);
        buttonPressed = false;
    }

    /**
     * Determines whether or not behavior should take place based on whether
     * or not the button has been pressed. </br>
     * requires: nothing </br>
     * ensures: boolean vlaue is returned </br>
     * @return ture if buton has been pressed, false otherwise
     */
    public boolean takeControl()
    {   return buttonPressed;   }

    /**
     * If takeControl is true, action will shut down the program. </br>
     * requires: nothing </br>
     * ensures: program is temrinated </br>
     */
    public void action()
    {   System.exit(0); }

    /**
     * Nothing to do
     */
    public void suppress()
    {    /* do nothing */   }

    /**
     * Sets buttonPressed to true if button has been pressed. </br>
     * requires: nothing </br>
     * ensures: button will be set to ture if pressed </br>
     * @param button true if pressed, false otherwise
     */
    public void buttonPressed(Button button)
    {   buttonPressed = true;   }

    /**
     * Nothing to do.
     * @param button
     */
    public void buttonReleased(Button button)
    {   /* do nothing */    }
}