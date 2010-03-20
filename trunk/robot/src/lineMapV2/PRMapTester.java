/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lineMapV2;

import java.awt.Rectangle;
import java.util.ArrayList;
import lejos.nxt.Motor;
import lejos.robotics.proposal.ArcPoseController;
import lejos.robotics.proposal.DeadReckonerPoseProvider;
import lejos.robotics.proposal.DifferentialPilot;
import lejos.robotics.proposal.WayPoint;
import util.RobotConstants;

/*
 * Author Michal Staniaszek
 */
public class PRMapTester {

    public static void main(String[] args) {
        float width = 487f;
        float height = 240f;

        PRLine[] lines = new PRLine[]{
            //world outline
            new PRLine(0f, 0f, width, 0f),
            new PRLine(0f, 0f, 0f, 240.4f),
            new PRLine(width, 0f, width, 240.4f),
            new PRLine(0f, 240.4f, width, 240.4f),
            //captain america
            new PRLine(73.4f, 104.7f, 135f, 104.7f),
            new PRLine(73.4f, 104.7f, 73.4f, 41.6f),
            new PRLine(73.4f, 41.6f, 135f, 41.6f),
            new PRLine(135f, 104.7f, 135f, 41.6f),
            //basil
            new PRLine(64.4f, 193f, 188.6f, 193f),
            new PRLine(64.4f, 193f, 64.4f, 153f),
            new PRLine(64.4f, 153f, 188.6f, 153f),
            new PRLine(188.6f, 193f, 188.6f, 153f),
            //obi wan
            new PRLine(234.4f, 218.3f, 254.5f, 218.3f),
            new PRLine(254.5f, 218.3f, 254.5f, 240.4f),
            new PRLine(234.4f, 218.3f, 234.4f, 240.4f),
            //gromit
            new PRLine(282f, 137f, 305.5f, 137f),
            new PRLine(305.5f, 137f, 305.5f, 156.5f),
            new PRLine(305.5f, 156.5f, 282f, 156.5f),
            new PRLine(282f, 156.5f, 282f, 137f),
            //preston
            new PRLine(333.5f, 180f, 377f, 180f),
            new PRLine(377f, 180f, 377f, 240.4f),
            new PRLine(333.5f, 180f, 333.5f, 240.4f),
            new PRLine(360f, 83.5f, 403.5f, 83.5f),
            //jaffa
            new PRLine(360.5f, 83.5f, 403.5f, 83.5f),
            new PRLine(403.5f, 83.5f, 403.5f, 123.5f),
            new PRLine(403.5f, 123.5f, 360.5f, 123.5f),
            new PRLine(360.5f, 123.5f, 360.5f, 83.5f),
            //wallace
            new PRLine(420.5f, 157, 444f, 157f),
            new PRLine(444f, 157f, 444f, 196.5f),
            new PRLine(444f, 196.5f, 420.5f, 196.5f),
            new PRLine(420.5f, 196.5f, 420.5f, 157f),
            //tinky-winky
            new PRLine(234.5f, 0, 234.5f, 21.5f),
            new PRLine(234.5f, 21.5f, 244f, 21.5f),
            new PRLine(244f, 21.5f, 244f, 64f),
            new PRLine(244f, 64f, 305f, 64f),
            new PRLine(305f, 64f, 305f, 42f),
            new PRLine(305f, 42f, 346.5f, 42f),
            new PRLine(346.5f, 42f, 346.5f, 0f),
            //gandalf
            new PRLine(width, 21.5f, width - 20.5f, 21.5f),
            new PRLine(width - 20.5f, 21.5f, width - 20.5f, 0f)
        };

        PRLineMap map = new PRLineMap(lines, new Rectangle((int) width,
                (int) height));

        PRMap probRoadMap = new PRMap(map);
        probRoadMap.makeRoadMap();
//        for (PRMapNode node : probRoadMap.roadMapNodes) {
//            System.out.println(node.toString());
//            System.out.println(node.getLinkedPoints().toString());
//            System.out.println("-------------------------------");
//
//        }
//
//        System.out.println("There are " + probRoadMap.getLinks() + " links");
        ArrayList<WayPoint> route = probRoadMap.getRoute(new PRMapNode(10, 10), new PRMapNode(158, 50));

        DifferentialPilot pilot = new DifferentialPilot(RobotConstants.WHEEL_DIAMETER/10, RobotConstants.TRACK_WIDTH/10, Motor.A, Motor.B);
        DeadReckonerPoseProvider drp = new DeadReckonerPoseProvider(pilot);
        ArcPoseController arc = new ArcPoseController(pilot, drp);

        for (WayPoint wayPoint : route) {
            arc.goTo(wayPoint);
        }
    }
}


