/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package line.proto;
import lejos.robotics.subsumption.Arbitrator;
/**
 * 
 * @author Jeremiah Via
 */
public class LineFollower
{
    public static void main(String[] args) {

        LineBehaviors centerLine = new CenterLine();
        LineBehaviors forward = new Forward();
        LineBehaviors [] behaviors = {centerLine, forward};

        Arbitrator arb = new Arbitrator(behaviors);
        arb.start();
    }

}
