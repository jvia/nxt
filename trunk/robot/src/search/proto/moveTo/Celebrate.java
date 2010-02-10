/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.proto.moveTo;
import lejos.geom.Point;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.Pilot;
/**
 * 
 * @author Jeremiah Via
 */
public class Celebrate extends PointMaster
{

    public Celebrate(LightSensor leftSensor, LightSensor rightSensor,
                   Pilot pilot, Point currentPoint, Point goalPoint) {
        super(leftSensor, rightSensor, pilot, currentPoint, goalPoint);
    }


    public boolean takeControl() {
            if (currentPoint.x == goalPoint.x
                && currentPoint.y == goalPoint.y){
                atPoint = true;
                return true;
        }
        return atPoint;
    }

    public void action() {
        pilot.rotate(1080);
    }

    public void suppress() {
        pilot.stop();
    }

}
