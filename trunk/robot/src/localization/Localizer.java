/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package localization;

import java.awt.Rectangle;
import lejos.geom.Line;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.Pose;
import lejos.robotics.localization.MCLPoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.proposal.ArcPoseController;
import lejos.robotics.proposal.DifferentialPilot;
import util.RobotConstants;

/**
 * 
 * @author Jeremiah Via
 */
public class Localizer {

    Line[] lines = new Line[]{
        // tinky-winky
        new Line(0f, 64f, 60f, 64f),
        new Line(60f, 64f, 60f, 42f),
        new Line(60f, 42f, 100f, 42f),
        new Line(100f, 0f, 100f, 42f),
        // gromit
        new Line(36f, 137f, 60f, 137f),
        new Line(36f, 137f, 36f, 156f),
        new Line(36f, 156f, 60f, 156f),
        new Line(60f, 137f, 60f, 156f),
        // preston
        new Line(87f, 180f, 87f, 240f),
        new Line(87f, 180f, 130f, 180f),
        new Line(130f, 180f, 130f, 240f),
        // jaffle
        new Line(113.5f, 123f, 157f, 123f),
        new Line(113.5f, 123f, 113.5f, 83f),
        new Line(113.5f, 83f, 157f, 83f),
        new Line(157f, 123f, 157f, 83f),
        // wallace
        new Line(174f, 197f, 174f, 157.5f),
        new Line(174f, 197f, 198f, 197f),
        new Line(198f, 197f, 198f, 157.5f),
        new Line(174f, 157.5f, 198f, 157.5f),
        // gandalf
        new Line(220f, 0f, 220f, 21.5f),
        new Line(240f, 21.5f, 220f, 21.5f)
    };
    LineMap map = new LineMap(lines, new Rectangle(0, 0, 240, 240));
    Scanner scanner = new Scanner(Motor.C, new OpticalDistanceSensor(
            SensorPort.S1));
    DifferentialPilot pilot = new DifferentialPilot(
            RobotConstants.WHEEL_DIAMETER,
            RobotConstants.TRACK_WIDTH,
            Motor.B,
            Motor.A,
            true);
    MCLPoseProvider poseProvider = new MCLPoseProvider(pilot, scanner, map, 20,
                                                       20);
    ArcPoseController controller = new ArcPoseController(pilot, poseProvider);

    ///// testing
    @SuppressWarnings("static-access")
    static void main(String[] args) throws InterruptedException {
        Localizer l = new Localizer();

        l.pilot.forward();
        Thread.sleep(1000);
        l.pilot.stop();

        System.out.println(l.poseProvider.getPose().getX());

        Button.waitForPress();


    }
}
