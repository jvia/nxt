package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * 
 * @author Jeremiah Via
 */
public class FindLine implements Behavior {

    public static int BLACK = 40;
    private LightSensor leftSensor;
    private LightSensor rightSensor;
    private TachoPilot pilot;
    
    public FindLine(LightSensor leftSensor, LightSensor rightSensor,
                    TachoPilot pilot) {
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
        this.pilot = pilot;
    }

    public LightSensor getLeftSensor() {
        return leftSensor;
    }

    public TachoPilot getPilot() {
        return pilot;
    }

    public LightSensor getRightSensor() {
        return rightSensor;
    }

    public void setLeftSensor(LightSensor leftSensor) {
        this.leftSensor = leftSensor;
    }

    public void setPilot(TachoPilot pilot) {
        this.pilot = pilot;
    }

    public void setRightSensor(LightSensor rightSensor) {
        this.rightSensor = rightSensor;
    }

    public boolean takeControl() {
        return leftSensor.getLightValue() <= BLACK
               || rightSensor.getLightValue() <= BLACK;
    }

    public void action() {
        // if leftSensor on black, stop left wheel
        int angle = 30;
        int turnRate;

        if (leftSensor.getLightValue() <= BLACK)
            turnRate = 30;
        else
            turnRate = -30;

       

        pilot.steer(turnRate, angle, true);
    }

    public void suppress() {
        pilot.stop();
    }

}
