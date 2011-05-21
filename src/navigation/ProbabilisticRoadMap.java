/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation;

import java.util.ArrayList;
import java.util.Collection;
import lejos.geom.Point;
import lejos.robotics.Pose;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.proposal.DestinationUnreachableException;
import lejos.robotics.proposal.WayPoint;

/**
 * 
 * @author Jeremiah Via
 */
public class ProbabilisticRoadMap implements PathFinder {

    private static int MAX_NODES = 200;
    private static int MAX_DIST = 10;
    LineMap map;
    ArrayList<Point> nodes;
    ArrayList<WayPoint> route;
    ArrayList<Edge> edges;

    public ProbabilisticRoadMap(LineMap map) {
        this.map = map;
        nodes = new ArrayList<Point>();
    }

    public void constructMap() {
        while (nodes.size() < MAX_NODES) {
            Point pt = randomPoint();
            ArrayList<Point> neighbours = getNeighbours(pt, nodes, MAX_DIST);

            sort(neighbours);

            for (Point neighbour : neighbours) {
                if (!inConnectedComponent(pt, neighbour) && collissionFreePath(pt, neighbour)) {
                    edges.add(new Edge(pt, neighbour));
                    addToConnectedComponent(pt, neighbour);
                }
            }
        }
    }

    private ArrayList<Point> getNeighbours(Point pt, ArrayList<Point> nodes, int MAX_DIST) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void sort(ArrayList<Point> neighbours) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Collection<WayPoint> findRoute(Pose start, Point destination) throws DestinationUnreachableException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<WayPoint> findRoute(Pose start, Pose destination) throws DestinationUnreachableException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean collissionFreePath(Point pt, Point neighbour) {
        return false;
    }

    private boolean inConnectedComponent(Point pt, Point neighbour) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void addToConnectedComponent(Point pt, Point neighbour) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Point randomPoint() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
