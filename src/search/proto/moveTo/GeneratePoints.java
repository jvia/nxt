/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.proto.moveTo;

import lejos.geom.Point;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.Pilot;

/**
 *
 * @author Jeremiah Via
 */
public class GeneratePoints extends PointMaster {

    public GeneratePoints(LightSensor leftSensor, LightSensor rightSensor,
                          Pilot pilot, Point currentPoint, Point goalPoint) {
        super(leftSensor, rightSensor, pilot, currentPoint, goalPoint);
    }

    public boolean takeControl() {  
        
        
        System.out.println(currentPoint + "\n" + goalPoint);
        if (currentPoint.x == goalPoint.x
                && currentPoint.y == goalPoint.y){
                atPoint = true;
                return false;
        }
            
        
        ;
        return !atPoint
               && leftSensor.getLightValue() <= MAX_BLACK_VALUE
               && rightSensor.getLightValue() <= MAX_BLACK_VALUE;
    }

    public void action() {
        if (currentPoint.x < goalPoint.x) {
            switch (currentFacing){
                case NORTH:
                    pilot.steer(-90, 90);
                    break;
                case SOUTH:
                    pilot.steer(90, 90);
                            break;
                case EAST:
                        break;
                case WEST:
                    pilot.rotate(180);
            }

            currentFacing = Facing.EAST;
        }
        else if (currentPoint.x > goalPoint.x) { 
            switch (currentFacing){
                case NORTH:
                    pilot.steer(90, 90);
                    break;
                case SOUTH:
                    pilot.steer(-90, 90);
                    break;
                case EAST:
                    pilot.rotate(180);
                    break;
                case WEST:
                    break;

            }

            currentFacing = Facing.WEST;
        }
        else if (currentPoint.y < goalPoint.y) {
            switch (currentFacing){
                case NORTH:
                    pilot.rotate(180);
                    break;
                case SOUTH:
                    break;
                case EAST:
                    pilot.steer(-90, 90);
                    break;
                case WEST:
                    pilot.steer(90, 90);
                    break;
            }

            currentFacing = Facing.SOUTH;
        }
        else if (currentPoint.y > goalPoint.y) {
            switch (currentFacing){
                case NORTH:
                    break;
                case SOUTH:
                    pilot.rotate(180);
                    break;
                case EAST:
                    pilot.steer(90, 90);
                    break;
                case WEST:
                    pilot.steer(-90, 90);
            }

            currentFacing = Facing.NORTH;
        }

        pilot.forward();
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException ex) {
            System.out.println("FUCK!");
        }

        switch (currentFacing) {
            case EAST:
                currentPoint.x++;
                break;
            case WEST:
                currentPoint.x--;
                break;
            case NORTH:
                currentPoint.y--;
                break;
            case SOUTH:
                currentPoint.y++;
                break;
        }
        System.out.println(currentFacing + "\n("
                         + currentPoint.x + ", " + currentPoint.y + ")\n");



//        pilot.rotate(90);while(pilot.isMoving())Thread.yield();
        Sound.playTone(440, 200);
    }

    public void suppress() {pilot.stop();
    }
}
