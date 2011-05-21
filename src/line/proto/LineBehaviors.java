/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package line.proto;
import java.awt.Rectangle;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;
/**
 * 
 * @author Jeremiah Via
 */
public abstract class LineBehaviors implements Behavior
{
    private final static int TRACKED_COLOR     = 0;
    private final static int CENTER_THRESHOLD  = 5;
    private final static int IMAGE_WIDTH       = 176;
    private final static int IMAGE_HEIGHT      = 144;
    public final static int X_CENTER_OF_IMAGE = IMAGE_WIDTH / 2;

    protected NXTCam eye;
    protected TachoPilot pilot;
    protected double centerX;

    public LineBehaviors()
    {
        eye = new NXTCam(SensorPort.S1);
        eye.setTrackingMode(NXTCam.LINE_TRACKING);
        eye.sortBy(NXTCam.SIZE);
        eye.enableTracking(false);
        eye.enableTracking(true);
        
        pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER, RobotConstants.TRACK_WIDTH,
                               RobotConstants.leftMotor, RobotConstants.rightMotor, true);

        centerX = 0.0;
    }

    public abstract boolean takeControl();

    public abstract void action();
    public abstract void suppress();

    public Rectangle getBiggestRectangle()
    {
        for (int i = 0; i < eye.getNumberOfObjects(); i++)
        {   if (eye.getObjectColor(i) == TRACKED_COLOR)
            {
                centerX = eye.getRectangle(i).getCenterX();
                return eye.getRectangle(i);
            }
            
        }
        return eye.getRectangle(0);
    }

    public double errorOffCenter()
    {
        return  -(X_CENTER_OF_IMAGE - getBiggestRectangle().getCenterX());
    }

}
