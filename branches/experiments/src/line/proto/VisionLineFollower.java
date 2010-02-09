/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package line.proto;

import java.awt.Rectangle;
import javax.microedition.lcdui.Graphics;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class VisionLineFollower {
    private final static int TRACKED_COLOR = 0;
    private final static int CENTER_THRESHOLD = 5;
    private final static int IMAGE_WIDTH = 176;
    private final static int IMAGE_HEIGHT = 144;
    private final static int MIN_X = 12;
    private final static int MAX_X = 164;
    private final static int X_CENTER_OF_IMAGE = IMAGE_WIDTH / 2;

    public static int getError(Rectangle rect) {
        return (int) -(rect.getX() / 2 - X_CENTER_OF_IMAGE);
    }

    public static void main(String[] args) throws InterruptedException {
        NXTCam cam = new NXTCam(SensorPort.S1);
        cam.setTrackingMode(NXTCam.LINE_TRACKING);
        cam.sortBy(NXTCam.NO_SORTING);
        cam.enableTracking(true);
        
        Motor left = RobotConstants.leftMotor;
        Motor right = RobotConstants.rightMotor;

        Graphics g = new Graphics();

        int DEFAULT_SPEED = 360, SLOW_SPEED = 180;


        while (true) {

            // instead of keeping image in center of line, keep it on line.
            // more stable

            // can see the line
            if (cam.getNumberOfObjects() > 0)
            {
                Rectangle lineEnd = cam.getRectangle(cam.getNumberOfObjects()-1);

                if (X_CENTER_OF_IMAGE > lineEnd.getMaxY()) // line on right, turn right
                {
                    left.backward();
                    right.flt();
                }
                else if (X_CENTER_OF_IMAGE < lineEnd.getMinX()) // line on left, turn left
                {
                    right.backward();
                    left.flt();

                }
                else
                {
                    left.backward();
                    right.backward();
                }
            }
            // cannot see a line
            else
            {
                continue;
            }






/*
            for (int i = 0; i < cam.getNumberOfObjects(); i++)
            {
                Rectangle r = cam.getRectangle(i);
                g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
            }

            LCD.clearDisplay();

            
            Rectangle lastRect = cam.getRectangle(cam.getNumberOfObjects()-1);
            if (cam.getNumberOfObjects() > 0)
            {
                // do line stuff


                // keep center x of last box in center of camera
                if (lastRect.getCenterX() < X_CENTER_OF_IMAGE - 5)
              //  if (lastRect.getCenterX() - X_CENTER_OF_IMAGE > 0) // line is on right
                {
                    left.setSpeed(SLOW_SPEED);
                    right.stop();
                    left.backward();
                }
                else if (lastRect.getCenterX() > X_CENTER_OF_IMAGE + 5)
                //else if (lastRect.getCenterX() - X_CENTER_OF_IMAGE < 0) // line is on left
                {
                    right.setSpeed(SLOW_SPEED);
                    right.backward();
                    left.stop();

                }
                else // line is in center
                {
                    left.setSpeed(DEFAULT_SPEED);
                    right.setSpeed(DEFAULT_SPEED);
                    left.backward();
                    right.backward();
                }
            }
            else
            {
                // find line
                // figure out where we last saw the line
                if (lastRect.getCenterX() < X_CENTER_OF_IMAGE - 2) // line is on right
                {
                    right.stop();
                    left.backward();
                }
               else if (lastRect.getCenterX() > X_CENTER_OF_IMAGE + 2) // line is on left
                {
                    right.backward();
                    left.stop();

                }
                else // line was in center, sop turn around
                {
                    left.forward();
                    Thread.sleep(500);
                    right.forward();
                }

            }

            */

            /* attempt # 1
            for(int i = 0; i < cam.getNumberOfObjects(); i++)
            {
            System.out.print("" + i + ": ");
            System.out.println("(" + (int)cam.getRectangle(i).getCenterX() + ", " + (int)cam.getRectangle(i).getCenterY() + ")");
            Thread.sleep(200);
            }

            boolean turningLeft = false, turningRight = false;

            // line found
            if (cam.getNumberOfObjects() > 0)
            {
            if (cam.getRectangle(cam.getNumberOfObjects()-1).getCenterX() > X_CENTER_OF_IMAGE)
            {
            //  right.forward();
            turningRight = true;
            turningLeft = false;

            right.stop();
            left.backward();
            }
            else if (cam.getRectangle(cam.getNumberOfObjects()-1).getCenterX() > X_CENTER_OF_IMAGE)
            {
            turningRight = false;
            turningLeft = true;

            //left.forward();
            left.stop();
            right.backward();
            }
            else
            {

            left.backward();
            right.backward();
            }
            }
            // no line found
            else
            {
            while (cam.getNumberOfObjects() == 0)
            {
            if (turningLeft)
            {
            left.stop();
            right.backward();
            }
            else if (turningRight)
            {
            right.stop();
            left.backward();
            }
            else
            {
            left.stop();
            right.stop();
            }


            turningRight = false;
            turningLeft = false;

            }
            }
            // Thread.sleep(3000);
            // LCD.clearDisplay();
            }
             * 
             */
             
        }
    }
}
            