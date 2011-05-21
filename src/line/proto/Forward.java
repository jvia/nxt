/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package line.proto;

/**
 * 
 * @author Jeremiah Via
 */
public class Forward extends LineBehaviors
{

    @Override
    public boolean takeControl() {
        return eye.getNumberOfObjects() > 0;
    }

    @Override
    public void action() {
         pilot.forward();
    }

    @Override
    public void suppress() {
        pilot.stop();
    }

}
