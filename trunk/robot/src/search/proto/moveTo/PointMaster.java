/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.proto.moveTo;

import lejos.geom.Point;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.subsumption.Behavior;

/**
 * 
 * @author Jeremiah Via
 */
public abstract class PointMaster implements Behavior {

    public static int MAX_BLACK_VALUE = 45;
    protected LightSensor leftSensor;
    protected LightSensor rightSensor;
    protected Pilot pilot;
    protected Point goalPoint;
    protected Point currentPoint;
    protected Facing currentFacing;

    enum Facing {

        NORTH, SOUTH, EAST, WEST
    };

    protected boolean atPoint;

    public PointMaster(LightSensor leftSensor, LightSensor rightSensor,
                       Pilot pilot, Point currentPoint, Point goalPoint) {
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
        this.pilot = pilot;
        this.currentPoint = currentPoint;
        this.goalPoint = goalPoint;
        atPoint = false;
        currentFacing = Facing.EAST;

    }
}
