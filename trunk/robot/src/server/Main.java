package server;

import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import proxy.NavigationControls;

/**
 *
 * @author nah
 */
public class Main {

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        NavigationControls controls = new RobotController(10, 10, 0);


        BTConnection connection = Bluetooth.waitForConnection();


        Runnable server = new NavigationProtocolServer(controls,
                                                       connection.
                openDataInputStream(), connection.openDataOutputStream());


        try {
            server.run();
        }
        catch (RuntimeException e) {
            String message = e.getMessage();
            if (message != null) {
                System.out.println(e.getMessage());
            }
            Sound.buzz();
            Button.ESCAPE.waitForPress();
        }

        //
        //
        //    while (!Button.ESCAPE.isPressed()) {
        //      Pose currentPose = controls.getPose();
        //      Pose desiredPose = new Pose(currentPose.getX() + 5f, currentPose.getY() + 5f, currentPose.getHeading() + 45f);
        //      controls.goTo(desiredPose);
        //    }


    }
}
