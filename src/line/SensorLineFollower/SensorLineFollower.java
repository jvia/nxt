/*
 * This does the second bit of part 1, as well as being an attempt at part 2.
 *
 * This series of behaviours allows the robot to follow a line.  I have used a
 * method which utilises the possibility of controlling the speed of the individual
 * motors.  When the left light sensor is triggered, the speed of the left motor decreases,
 * and vice versa.
 */
package line.SensorLineFollower;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import maze.StopProgram;
import util.RobotConstants;

/*
 * Author Michal Staniaszek
 */
public class SensorLineFollower {

    /*
     * Defining the sensors and other things to be used by the behaviours.
     */
    private static final DifferentialPilot pilot = new DifferentialPilot(RobotConstants.TRACK_WIDTH, RobotConstants.TRACK_WIDTH, Motor.A, Motor.B, true);
    private static final ColorSensor colour = new ColorSensor(SensorPort.S2);
    private static final LightSensor leftLight = new LightSensor(SensorPort.S3);
    private static final LightSensor rightLight = new LightSensor(SensorPort.S4);

    public static void main(String[] args) {

        // behaviors need to be created so they can be used
        Behavior followLine = new FollowLine(colour);
        Behavior threeWayJunction = new ThreeWayJunction(leftLight, rightLight, colour, pilot);
        Behavior leftJunction = new LeftJunction(colour, leftLight, pilot);
        Behavior rightJunction = new RightJunction(colour, rightLight, pilot);
        Behavior findLine = new FindLine(colour, leftLight, rightLight, pilot);
        Behavior stopProgram = new StopProgram();
        Behavior losingLineRight = new LosingLineRight(pilot, rightLight);
        Behavior losingLineLeft = new LosingLineLeft(pilot, leftLight);

        // we need to store the behaviors in a priority-ascending array
        Behavior[] behaviors = {//findLine,
                                followLine,
                                losingLineRight,
                                losingLineLeft,
                                //leftJunction,
                                //rightJunction,
                                //threeWayJunction,
                                stopProgram};

        // the arbitrator will cycle through behaviors and execute the ones
        // that are need to be executed
        Arbitrator arbitrator = new Arbitrator(behaviors);
        arbitrator.start();
    }
}


