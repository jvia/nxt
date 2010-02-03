package patterns;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * An abstract class that defines common functionality that any Pattern maker
 * would need.
 * 
 * Exercise 2, Part 1
 * Due: 21 January 2010
 * @author Jeremiah Via, Michal Staniaszek
 */
public abstract class Pattern implements Behavior, ButtonListener
{
    private int buttonCount;
    private DifferentialPilot pilot = new DifferentialPilot(RobotConstants.WHEEL_DIAMETER,
                                  RobotConstants.TRACK_WIDTH, Motor.A, Motor.B);
    
    /**
     * Creates a Pattern behavior.
     * requires: nothing
     * ensures: Pattern object is created
     */
    public Pattern()
    {
        buttonCount = 0;
        Button.ENTER.addButtonListener(this);
    }    

    /**
     * Stops the DifferentialPilot
     * requires: nothing
     * ensures: robot is stoped
     */
    public void suppress()
    {   pilot.stop();   }

    /**
     * Method that determines if a specific pattern is supposed to occur.
     * requires: nothing
     * ensures: boolean value is returned
     * @return true if yes, false otherwise
     */
    public abstract boolean takeControl();

    /**
     * Performs an action defined by a subclass.
     * requires: nothing
     * ensures: action is performe
     */
    public abstract void action();

    /**
     * Method is called any a button is pressed on the robot. It increments
     * the button press count by one.
     * requires: nothing
     * ensures: button count is incremented by one
     * @param button button to listen for
     */
    public void buttonPressed(Button button)
    {   buttonCount++;  }

    /**
     * Does nothing
     * @param button does nothing
     */
    public void buttonReleased(Button button)
    {   /* do nothing */ }

    /**
     * Returns the pilot
     * @return the pilot
     */
    public DifferentialPilot getPilot()
    {   return pilot;   }

    /**
     * Returns the button count
     * @return buttoun count
     */
    public int getButtonCount()
    {   return buttonCount; }
}