/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package localization;

import lejos.geom.Line;
import lejos.robotics.proposal.ArcPoseController;
import lejos.robotics.proposal.DifferentialPilot;

/**
 *
 * @author Jeremiah Via
 */
public class Localizer {

    // the world
    Line[] lines;
    int width, height;

    // our means of interacting with it
    DifferentialPilot pilot;
    Scanner scanner;
    MCLParticleSet set;
    MCLPoseProvider poseProvider;
    ArcPoseController controller;

    public  Localizer(Line [] lines, int width, int height){
        
    }
    


}
