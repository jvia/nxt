/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.proto;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import line.lightSensor.FindLine;
import line.lightSensor.Forward;
import util.RobotConstants;
/**
 * 
 * @author Jeremiah Via
 */
public class LineTest
{
    public static void main(String[] args) {
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);
        Pilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER, RobotConstants.TRACK_WIDTH, RobotConstants.leftMotor, RobotConstants.rightMotor, true);
        
        Behavior findLine = new FindLine(left, right, pilot);
        Behavior forward = new Forward(left, right, pilot);

        Behavior [] bees = {findLine, forward};
        Arbitrator a = new Arbitrator(bees);
        a.start();

    }

}
