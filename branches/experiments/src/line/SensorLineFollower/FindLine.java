/**
 * Attempts to find a line if none of the sensors detect black.
 */

package line.SensorLineFollower;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 * Author Michal Staniaszek
 */
public class FindLine implements Behavior{

    ColorSensor colour;
    LightSensor leftLight ;
    LightSensor rightLight;
    DifferentialPilot pilot;

    /**
     * Constructor for this behaviour.
     * @param colour The colour sensor (centre sensor)
     * @param left The left light sensor
     * @param right The right light sensor
     * @param pilot A differential Pilot
     */
    public FindLine(ColorSensor colour, LightSensor left, LightSensor right, DifferentialPilot pilot) {
        this.colour = colour;
        leftLight = left;
        rightLight = right;
        this.pilot = pilot;
    }

    /**
     * Takes control if none of the sensors return a black value.
     * @return
     */
    public boolean takeControl() {
        return ((colour.getRawRed() > 30000 
                && leftLight.getLightValue() > 40
                && rightLight.getLightValue() > 40));
    }


    /**
     * Moves the robot around in a hexagonal shape in an attempt to find a line to follow
     */
    public void action() {
        for (int i = 0; i < 8; i++) {
            pilot.travel(125f);
            pilot.rotate(45, true);
        }
       
    }

    /**
     * Suppresses the behaviour, stopping both motors.
     */
    public void suppress() {
        Motor.A.stop();
        Motor.B.stop();
    }

}
