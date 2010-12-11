package search.proto.moveTo;

import lejos.geom.Point;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.Pilot;

/**
 * 
 * @author Jeremiah Via
 */
public class Forward extends PointMaster {


    public Forward(LightSensor leftSensor, LightSensor rightSensor,
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
               &&leftSensor.getLightValue() > MAX_BLACK_VALUE
               && rightSensor.getLightValue() > MAX_BLACK_VALUE;

    }

    public void action() {
        pilot.forward();
    }

    public void suppress() {
        pilot.stop();
    }
}
