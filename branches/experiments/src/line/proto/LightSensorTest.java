/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package line.proto;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.TachoPilot;
import util.RobotConstants;
/**
 * 
 * @author Jeremiah Via
 */
public class LightSensorTest 
{
    public static void main(String[] args) {
        ColorSensor cs = new ColorSensor(SensorPort.S1);
        TachoPilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER, RobotConstants.TRACK_WIDTH, Motor.B, Motor.A);
        NXTCam cam = new NXTCam(SensorPort.S1);

        Button.ENTER.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button button)
            {   System.exit(0); }
            public void buttonReleased(Button button) {}
        });

        cam.setTrackingMode(cam.LINE_TRACKING);
        


        while (true)
        {       
            
             
          


        }


        
    }
}

