/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.proto;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
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
        return left.getLightValue() < 45 && right.getLightValue() < 45;
    }

    public void action() {
        pilot.steer(90, 90);
//        pilot.rotate(90);while(pilot.isMoving())Thread.yield();
        Sound.playTone(440, 200);
    }

    public void suppress() {}

}
