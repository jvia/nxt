/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 * Author Michal Staniaszek
 */
public class TurnRightAcrossLine implements Behavior{

    LightSensor leftSensor;
    LightSensor rightSensor;
    DifferentialPilot pilot;

    public TurnRightAcrossLine(LightSensor leftSensor, LightSensor rightSensor, DifferentialPilot pilot) {
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
        this.pilot = pilot;
    }

    public boolean takeControl() {
        return true;
    }

    public void action() {

    }

    public void suppress() {
    }



}
