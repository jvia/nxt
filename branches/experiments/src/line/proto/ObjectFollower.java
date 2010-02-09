/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package line.proto;

import java.awt.Rectangle;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class ObjectFollower {
    public final static int OFFSET = 15;
    public final static int MAX_AREA = 3000;
    private final static int TRACKED_COLOR = 0;
    private final static int CENTER_THRESHOLD = 5;
    private final static int IMAGE_WIDTH = 176;
    private final static int IMAGE_HEIGHT = 144;
    private final static int MIN_X = 12;
    private final static int MAX_X = 164;
    private final static int X_CENTER_OF_IMAGE = IMAGE_WIDTH / 2;
    NXTCam cam;
    Motor left = RobotConstants.leftMotor;
    Motor right = RobotConstants.rightMotor;

    public double percentX(Rectangle r) {
        return r.getCenterX() / IMAGE_WIDTH;
    }

    public double percentY(Rectangle r) {
        return r.getCenterY() / IMAGE_HEIGHT;
    }

    public static void main(String[] args) {
        Rectangle r = new Rectangle(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        NXTCam cam = new NXTCam(SensorPort.S1);
        Motor left = RobotConstants.leftMotor;
        Motor right = RobotConstants.rightMotor;

        while ( true )
        {
            
        }
    }
}
