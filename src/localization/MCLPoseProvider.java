package localization;

import lejos.robotics.Pose;
import lejos.robotics.mapping.RangeMap;
import lejos.robotics.MoveListener;
import lejos.robotics.MovementProvider;
import lejos.robotics.RangeReadings;
import lejos.robotics.RangeScanner;
import lejos.robotics.Movement;
import lejos.robotics.localization.PoseProvider;

public class MCLPoseProvider implements PoseProvider, MoveListener {

    private MCLParticleSet particles;
    private RangeScanner scanner;
    private RangeMap map;
    private boolean readingsRequired = true;
    private boolean lost = true;

    public MCLPoseProvider(MovementProvider mp, RangeScanner scanner,
                           RangeMap map, int numParticles, int border) {
        particles = new MCLParticleSet(map, numParticles, border);
        this.scanner = scanner;
        this.map = map;
        mp.addMoveListener(this);
    }

    public MCLPoseProvider(Pose pose, MovementProvider mp, RangeScanner scanner,
                           RangeMap map, int numParticles, int border) {
        particles = new MCLParticleSet(pose, map, numParticles, border);
        this.scanner = scanner;
        this.map = map;
        mp.addMoveListener(this);
    }

    public MCLParticleSet getParticles() {
        return particles;
    }

    public void movementStarted(Movement event, MovementProvider mp) {
    }

    public void movementStopped(Movement event, MovementProvider mp) {
        readingsRequired = true;
        particles.applyMove(event);
    }

    public Pose getPose() {
        if (readingsRequired) {
            RangeReadings rr = scanner.getRangeValues();
            readingsRequired = false;
          //  System.out.println("MCLPoseProvider.getPose() rr size: " + rr.size());
            
            if (!rr.incomplete()) {
                particles.calculateWeights(rr, map);
                lost = particles.resample(); // Cannot indicate robot is lost
            }
        }
        return particles.getEstimatedPose();
    }

    public boolean isLost(){
        return lost;
    }
}
