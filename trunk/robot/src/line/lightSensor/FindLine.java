package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.subsumption.Behavior;

/**
 * 
 * @author Jeremiah Via
 */
public class FindLine implements Behavior {

    public static int BLACK = 45;
    private LightSensor leftSensor;
    private LightSensor rightSensor;
    private Pilot pilot;
    
    public FindLine(LightSensor leftSensor, LightSensor rightSensor,
                    Pilot pilot) {
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
        this.pilot = pilot;
    }

    public boolean takeControl() {
        return leftSensor.getLightValue() <= BLACK
               ^ rightSensor.getLightValue() <= BLACK;
    }

    public void action() {
        /// testing
       // LineFollower.lightValues(leftSensor, rightSensor);
        // if leftSensor on black, stop left wheel
        
        Sound.playTone(440, 2);
        int angle = 360;
        int turnRate;

        if (leftSensor.getLightValue() <= BLACK)
            turnRate = 90;
        else
            turnRate = -90;

       

        pilot.steer(turnRate, angle, true);
    }

    public void suppress() {
        pilot.stop();
    }

}
