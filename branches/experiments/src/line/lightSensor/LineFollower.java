package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class LineFollower {

    public static void lightValues(LightSensor left, LightSensor right){
        System.out.println(left.getLightValue()+"\t"+right.getLightValue());
    }

    public static void main(String[] args) {

        Pilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                     RobotConstants.TRACK_WIDTH,
                                     RobotConstants.leftMotor,
                                     RobotConstants.rightMotor,
                                     true);
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);

        Behavior findLine = new JunctionReached(left, right, pilot);
        Behavior forward = new Forward(left, right, pilot);
        Behavior[] bs = {forward, findLine};
        Arbitrator arr = new Arbitrator(bs);
        arr.start();
    }
}
