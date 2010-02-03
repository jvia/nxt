package distance;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.Pilot;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import util.RobotConstants;

/**
 * Main class that initializes the behaviors and the robotic componenets needed
 * in those behaviors. In essence, it runs the program.
 *
 * Exercise 03, Part 01
 * 4 February 2010
 * @author Jeremiah Via, Michal Staniazek
 */
public class SonarDistanceMaintainer {

    /**
     * Runs program
     * @param args not used
     */
    public static void main(String [] args) {
        Pilot pilot = new TachoPilot(RobotConstants.WHEEL_DIAMETER,
                                     RobotConstants.TRACK_WIDTH, Motor.B,
                                     Motor.A, true);
        UltrasonicSensor ultrasonic = new UltrasonicSensor(SensorPort.S1);
        int distance = 20;
        pilot.setMoveSpeed(720);

        // simply a way to exit the program
        Button.ENTER.addButtonListener(new ButtonListener() {
            public void buttonPressed(Button button) { System.exit(0); }
            public void buttonReleased(Button button) {}
        });

        // Need to created the behaviors and add to an array
        Move forward = new MoveForward(distance, pilot, ultrasonic);
        Move backward = new MoveBackward(distance, pilot, ultrasonic);
        Move still = new SitStill(distance, pilot, ultrasonic);
        Behavior[] moves = {forward, backward, still};

        // Initialize arbitrator with behavior array and tell it to start
        // cycling through behaviors
        Arbitrator arb = new Arbitrator(moves);
        arb.start();
    }
}