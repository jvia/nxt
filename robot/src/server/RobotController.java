/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Rectangle;
import lejos.geom.Line;
import lejos.geom.Point;
import lejos.nxt.Motor;
import lejos.robotics.Pose;
import lejos.robotics.proposal.ArcPoseController;
import lejos.robotics.proposal.DeadReckonerPoseProvider;
import lejos.robotics.proposal.DifferentialPilot;
import localization.LineMap;
import proxy.NavigationControls;
import util.RobotConstants;

/**
 *
 * @author nah
 */
public class RobotController implements NavigationControls {

    private final DifferentialPilot m_pilot;
    private final DeadReckonerPoseProvider m_poser;
    private final ArcPoseController m_controller;
    float width = 119f;
    float height = 61f;
    Line[] lines;
    LineMap map;

    /**
     * Create controls in cm coordinate frame.
     *
     * TODO: Add parameters to describe robot.
     */
    public RobotController() {



        lines = new Line[]{
            // world outline
            new Line(0f, 0f, 0f, height),
            new Line(0f, 0f, width, 0f),
            new Line(0f, height, width, height),
            new Line(width, 0f, width, height),
            // document box
            new Line(0f, 36f, 32.5f, 36f),
            new Line(32.5f, 36f, 32.5f, height),
            // brown box
            new Line(102.5f, 38f, width, 38f),
            new Line(102.5f, 38f, 102.5f, height)
        };

        map = new LineMap(lines, new Rectangle((int) width,
                                                       (int) height));


        m_pilot = new DifferentialPilot(
                RobotConstants.WHEEL_DIAMETER / 10,
                RobotConstants.TRACK_WIDTH / 10,
                Motor.B, Motor.C);

        m_poser = new DeadReckonerPoseProvider(m_pilot);
        m_poser.setPosition(new Point(22,18));
        m_controller = new ArcPoseController(m_pilot, m_poser);

    }

    /**
     * Move to pose.
     *
     * TODO: Respect target pose.
     * @param _target
     */
    public void goTo(Pose _target) {
        m_controller.goTo(_target.getLocation());
    }

    /**
     * Get's the robot's pose.
     * @return
     */
    public Pose getPose() {
        return m_poser.getPose();
    }

    /**
     * Return the map
     * @return
     */
    public Line[] getLines(){
        return map.getLines();
    }
}
