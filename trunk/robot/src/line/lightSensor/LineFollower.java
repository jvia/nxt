package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.robotics.navigation.Pilot;

/**
 * 
 * @author Jeremiah Via
 */
public class LineFollower {

    private Pilot pilot;
    private LightSensor leftSensor;
    private LightSensor rightSensor;

    public LineFollower(Pilot pilot, LightSensor leftSensor,
                        LightSensor rightSensor) {
        this.pilot = pilot;
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
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

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public LightSensor getRightSensor() {
        return rightSensor;
    }

    public void setRightSensor(LightSensor rightSensor) {
        this.rightSensor = rightSensor;
    }

    
}
