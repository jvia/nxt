/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package line.proto;
/**
 * 
 * @author Jeremiah Via
 */
public class CenterLine extends LineBehaviors
{
    public boolean takeControl()
    {
         return (eye.getNumberOfObjects() == 0);
  
    }

    public void action() {
        System.out.println("Turn: " + errorOffCenter());
        if (centerX < X_CENTER_OF_IMAGE)
            pilot.rotate(-1);
        else
            pilot.rotate(1);
    }

    public void suppress() {
        pilot.stop();
    }

}
