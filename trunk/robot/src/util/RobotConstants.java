package util;

import lejos.nxt.Motor;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Jeremiah Via
 */
public class RobotConstants
{
    /**
     * The diamater of the lego wheel in mm.
     */
    public static final float WHEEL_DIAMETER = 56f;

    /**
     * The distance between the center of each wheel in mm.
     */
    public static final float TRACK_WIDTH = 173f;

    public static final float SINGLE_ROTATION = (float) Math.PI * WHEEL_DIAMETER;

    public static final Motor leftMotor = Motor.B;
    public static final Motor rightMotor = Motor.A;

}
