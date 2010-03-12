/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import proxy.NavigationControlProtocol;
import proxy.NavigationControls;

/**
 *
 * @author nah
 */
public class NavigationProtocolServer implements Runnable {

    private final NavigationControls m_target;
    private final NavigationControlProtocol m_protocol;

    public NavigationProtocolServer(NavigationControls _target,
                                    DataInputStream _dis, DataOutputStream _dos) {
        this.m_target = _target;
        m_protocol = new NavigationControlProtocol(_dis, _dos);
    }

    public void run() {

        //TODO: more elegant termination

        try {
            while (true) {
                NavigationControlProtocol.ControlCommands cmd = m_protocol.
                        readHeader();
                switch (cmd) {
//                    case GET_MAP:
//                        m_protocol.writeMap(m_target.getMap());
//                        break;
                    case GET_POSE:
                        m_protocol.writePose(m_target.getPose());
                        break;
                    case GO_TO:
                        m_target.goTo(m_protocol.readPose());
                        break;
                }

            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
