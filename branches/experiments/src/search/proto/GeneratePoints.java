/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.proto;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.subsumption.Behavior;
/**
 * 
 * @author Jeremiah Via
 */
public class GeneratePoints implements Behavior
{
    private LightSensor left, right;
    private Pilot pilot;

    public GeneratePoints(LightSensor left, LightSensor right, Pilot pilot) {
        this.left = left;
        this.right = right;
        this.pilot = pilot;
    }

    

    public boolean takeControl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void action() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void suppress() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
