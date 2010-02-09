package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * 
 * @author Jeremiah Via
 */
public class Forward implements Behavior {

    public static int BLACK = 40;
    private LightSensor leftSensor;
    private LightSensor rightSensor;
    private Pilot pilot;

    public Forward(LightSensor leftSensor, LightSensor rightSensor,
                   Pilot pilot) {
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
        this.pilot = pilot;
    }

    public LightSensor getLeftSensor() {
        return leftSensor;
    }

    public void setLeftSensor(LightSensor leftSensor) {
        this.leftSensor = leftSensor;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(TachoPilot pilot) {
        this.pilot = pilot;
    }

    public LightSensor getRightSensor() {
        return rightSensor;
    }

    public void setRightSensor(LightSensor rightSensor) {
        this.rightSensor = rightSensor;
    }

    public boolean takeControl() {
        return (leftSensor.getLightValue() > BLACK && rightSensor.getLightValue()
                                                      > BLACK);
    }

    public void action() {
        pilot.steer(0, 0, true);
    }

    public void suppress() {
        pilot.stop();
    }
}