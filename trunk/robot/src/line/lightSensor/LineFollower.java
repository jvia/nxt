package line.lightSensor;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import search.proto.GeneratePoints;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class LineFollower {

    private Pilot pilot;
    private LightSensor leftSensor;
    private LightSensor rightSensor;
    private Arbitrator a;

    public LineFollower(Pilot pilot, LightSensor leftSensor,
                        LightSensor rightSensor) {
        this.pilot = pilot;
        this.leftSensor = leftSensor;
        this.rightSensor = rightSensor;
        Behavior findLine = new FindLine(leftSensor, rightSensor, pilot);
        Behavior generatePoints = new GeneratePoints(leftSensor, rightSensor,
                                                     pilot);
        Behavior forwards = new Forward(leftSensor, rightSensor, pilot);
        Behavior[] behaviors = {forwards, generatePoints, findLine};
        a = new Arbitrator(behaviors);
    }

    public void run() {
        a.start();
    }

    public static void main(String[] args) {
        Pilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER, RobotConstants.TRACK_WIDTH, RobotConstants.leftMotor, RobotConstants.rightMotor);
        LightSensor left = new LightSensor(SensorPort.S3);
        LightSensor right = new LightSensor(SensorPort.S4);
        LineFollower lf = new LineFollower(pilot, left, right);
        lf.run();
    }
}
