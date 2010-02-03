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
public class FindLine implements Behavior
{
    private NXTCam cam;
    private TachoPilot pilot;
    private UltrasonicSensor ultrasonic;
    
    public FindLine(NXTCam cam, TachoPilot pilot, UltrasonicSensor ultrasonic)
    {
        this.cam = cam;
        this.pilot = pilot;
        this.ultrasonic = ultrasonic;
    }

    public boolean takeControl() {
        return (cam.getNumberOfObjects() == 0);
    }

    public void action() {

        RobotConstants.leftMotor.backward();
        System.out.println("No line");
        try {
            Thread.sleep((int) Math.random() * 100);
        }
        catch (InterruptedException ex) {

        }
    }

    public void suppress() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
