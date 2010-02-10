package search.proto.moveTo;

import lejos.geom.Point;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.Pilot;

/**
 * 
 * @author Jeremiah Via
 */
public class FindLine extends PointMaster {

    

    public FindLine(LightSensor leftSensor, LightSensor rightSensor,
                    Pilot pilot, Point currentPoint, Point goalPoint) {
        super(leftSensor, rightSensor, pilot, currentPoint, goalPoint);
    }

    public boolean takeControl() {

        if (currentPoint.x == goalPoint.x
                && currentPoint.y == goalPoint.y){
                atPoint = true;
                return false;
        }
        
        return !atPoint
                && leftSensor.getLightValue() <= MAX_BLACK_VALUE
               ^ rightSensor.getLightValue() <= MAX_BLACK_VALUE;
    }

    public void action() {
        if (currentPoint.equals(goalPoint))
            atPoint = true;
        /// testing
        // LineFollower.lightValues(leftSensor, rightSensor);
        // if leftSensor on MAX_BLACK_VALUE, stop left wheel

        Sound.playTone(440, 2);
        int angle = 360;
        int turnRate;

        if (leftSensor.getLightValue() <= MAX_BLACK_VALUE)
            turnRate = 90;
        else
            turnRate = -90;



        pilot.steer(turnRate, angle, true);
    }

    public void suppress() {
        pilot.stop();
    }
}
