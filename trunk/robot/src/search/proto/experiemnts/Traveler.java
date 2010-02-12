/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.proto.experiemnts;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class Traveler {

    public static final int MAX_BLACK_VALUE = 43;

    enum Facing {

        NORTH, SOUTH, EAST, WEST
    };

    Pilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                 RobotConstants.TRACK_WIDTH,
                                 RobotConstants.leftMotor,
                                 RobotConstants.rightMotor, true);
    LightSensor left = new LightSensor(SensorPort.S3);
    LightSensor right = new LightSensor(SensorPort.S4);
    Facing currentFacing = Facing.EAST;
    GridPoint currentPoint = new GridPoint(0, 0, false);// change this!

    public void travelUntilJunction() {
        while (!(left.getLightValue() <= MAX_BLACK_VALUE
                 && right.getLightValue() <= MAX_BLACK_VALUE)) {
            if (left.getLightValue() <= MAX_BLACK_VALUE
                && right.getLightValue() > MAX_BLACK_VALUE) {
                pilot.steer(45, 90, true);
            }
            else if (right.getLightValue() <= MAX_BLACK_VALUE && left.
                    getLightValue() > MAX_BLACK_VALUE) {
                pilot.steer(-45, 90, true);
            }
            else if (left.getLightValue() > MAX_BLACK_VALUE
                     && right.getLightValue() > MAX_BLACK_VALUE) {
                pilot.forward();
            }
        }

        pilot.travel(0.75f * RobotConstants.SINGLE_ROTATION);
    }

    public void travelEast() {
        switch (currentFacing) {
            case NORTH:
                pilot.rotate(-90);
                break;

            case SOUTH:
                pilot.rotate(90);
                break;

            case WEST:
                pilot.rotate(180);
                break;

        }

        currentFacing = Facing.EAST;

        pilot.forward();
        try { Thread.sleep(500);}
        catch (Exception e) {}
        
        travelUntilJunction();

    }

    public void travelWest() {
        switch (currentFacing) {
            case NORTH:
                pilot.rotate(90);
                break;
            case EAST:
                pilot.rotate(180);
                break;
            case SOUTH:
                pilot.rotate(-90);
                break;
        }
        currentFacing = Facing.WEST;

        pilot.forward();
        try { Thread.sleep(500);}
        catch (Exception e) {}
        
        travelUntilJunction();

    }

    public void travelNorth() {
        switch (currentFacing) {
            case EAST:
                pilot.rotate(90);
                break;
            case SOUTH:
                pilot.rotate(180);
                break;
            case WEST:
                pilot.rotate(-90);
                break;
        }

        currentFacing = Facing.NORTH;

        pilot.forward();
        try { Thread.sleep(500);}
        catch (Exception e) {}

        travelUntilJunction();

    }

    public void travelSouth() {
        switch (currentFacing) {
            case NORTH:
                pilot.rotate(180);
                break;
            case EAST:
                pilot.rotate(-90);
                break;
            case WEST:
                pilot.rotate(90);
                break;
        }

        currentFacing = Facing.SOUTH;

        pilot.forward();
        try { Thread.sleep(500);}
        catch (Exception e) {}

        travelUntilJunction();
        

    }

    public static void main(String[] args) throws InterruptedException {
        Traveler t = new Traveler();
        Button.waitForPress();


        t.travelEast();
        t.travelEast();
        t.travelSouth();
        t.travelSouth();
        t.travelWest();
        t.travelWest();
        t.travelNorth();
        t.travelNorth();
    }
}
