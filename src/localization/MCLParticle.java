package localization;

import java.util.Random;
import lejos.robotics.*;
import lejos.robotics.mapping.RangeMap;
import lejos.robotics.Movement;
import lejos.geom.*;
import lejos.nxt.Button;

/*
 * WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 */
/**
 * Represents a particle for the particle filtering algorithm. The state of the
 * particle is the pose, which represents a possible pose of the robot.
 * 
 * The weight for a particle is set by taking a set of theoretical range readings using a
 * map of the environment, and comparing these ranges with those taken by the
 * robot. The weight represents the relative probability that the robot has this
 * pose. Weights are from 0 to 1.
 * 
 * @author Lawrie Griffiths
 * 
 */
public class MCLParticle {

    private static boolean debug = false;
    private static Random rand = new Random();
    // Instance variables (kept to minimum to allow maximum number of particles)
    private Pose pose;
    private float weight = 0;

    /**
     * Create a particle with a specific pose
     *
     * @param pose the pose
     */
    public MCLParticle(Pose pose) {
        this.pose = pose;
    }

    /**
     * Set the weight for this particle
     *
     * @param weight the weight of this particle
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Return the weight of this particle
     *
     * @return the weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Return the pose of this particle
     *
     * @return the pose
     */
    public Pose getPose() {
        return pose;
    }

    /**
     * Calculate the weight for this particle by comparing its readings with the
     * robot's readings
     *
     * @param rr Robot readings
     */
    public void calculateWeight(RangeReadings rr, RangeMap map, float divisor) {
        weight = 1;
        Pose tempPose = new Pose();
        tempPose.setLocation(pose.getLocation());

        for (int i = 0; i < rr.size(); i++) {
            float angle = rr.getAngle(i);
            tempPose.setHeading(pose.getHeading() + angle);
            float robotReading = rr.getRange(i);

            /*  10cm < valid sensor value < 80 */
            if (robotReading >= 80 || robotReading <= 10)
                continue;


            float range = map.range(tempPose);

            // TODO remove debug
            if (debug)
                System.out.println("\tAngle: " + angle + "\tExpect: " + range + "\tActual: " + robotReading);

            if (range < 0) {
                weight = 0;
                return;
            }


            float diff = robotReading - range;
            weight *= (float) Math.exp(-(diff * diff) / divisor);

            // TODO remove debug
            if (debug) {
                System.out.println("\tParticle:" + pose.getX() + ", " + pose.getY() + "\tWeight: " + weight);
                System.out.println("");
            }

        }
    }

    /**
     * Get a specific reading
     *
     * @param i the index of the reading
     * @return the reading
     */
    public float getReading(int i, RangeReadings rr, RangeMap map) {
        Pose tempPose = new Pose();
        tempPose.setLocation(pose.getLocation());
        tempPose.setHeading(pose.getHeading() + rr.getAngle(i));
        return map.range(tempPose);
    }

    /**
     * Apply the robot's move to the particle with a bit of random noise.
     * Only works for rotate or travel movements.
     *
     * @param move the robot's move
     */
    public void applyMove(Movement move, float distanceNoiseFactor,
                          float angleNoiseFactor) {
        float ym = (move.getDistanceTraveled() * ((float) Math.sin(Math.toRadians(pose.getHeading()))));
        float xm = (move.getDistanceTraveled() * ((float) Math.cos(Math.toRadians(pose.getHeading()))));

        pose.setLocation(new Point((float) (pose.getX() + xm + (distanceNoiseFactor * xm * rand.nextGaussian())),
                                   (float) (pose.getY() + ym + (distanceNoiseFactor * ym * rand.nextGaussian()))));
        pose.setHeading((float) (pose.getHeading() + move.getAngleTurned()
                                 + (angleNoiseFactor * move.getAngleTurned() * rand.nextGaussian())));
        pose.setHeading((float) ((int) (pose.getHeading() + 0.5f) % 360));
    }

    @Override
    public boolean equals(Object o) {
        MCLParticle other = (MCLParticle) o;
        return this.getPose().getX() == other.getPose().getX()
               && this.getPose().getY() == other.getPose().getY()
               && this.getPose().getHeading() == other.getPose().getHeading();
    }
}
