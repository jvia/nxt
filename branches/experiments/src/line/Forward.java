/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package line;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;
/**
 * 
 * @author Jeremiah Via
 */
public class Forward implements Behavior
{

    private final static int TRACKED_COLOR     = 0;
    private final static int CENTER_THRESHOLD  = 5;
    private final static int IMAGE_WIDTH       = 176;
    private final static int IMAGE_HEIGHT      = 144;
    private final static int MIN_X = 12;
    private final static int MAX_X = 164;
    private final static int X_CENTER_OF_IMAGE = IMAGE_WIDTH / 2;
    public static final int DISTANCE = 10;

    private NXTCam cam;
    private TachoPilot pilot;
    private UltrasonicSensor ultrasonic;

    public Forward(NXTCam cam, TachoPilot pilot, UltrasonicSensor ultrasonic)
    {
        this.cam = cam;
        this.pilot = pilot;
        this.ultrasonic = ultrasonic;
    }

    public boolean takeControl() {
        return cam.getNumberOfObjects() > 0
            && cam.getRectangle(cam.getNumberOfObjects()-1).getCenterX() <= X_CENTER_OF_IMAGE + 5
            && cam.getRectangle(cam.getNumberOfObjects()-1).getCenterX() >= X_CENTER_OF_IMAGE - 5;
    }

    public void action() {
        System.out.println("Forward");
        RobotConstants.leftMotor.backward();
        RobotConstants.rightMotor.backward();
    }

    public void suppress() {
        RobotConstants.rightMotor.stop();
        RobotConstants.leftMotor.stop();
    }

}
