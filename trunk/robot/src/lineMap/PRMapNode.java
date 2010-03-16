/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lineMap;

import java.util.ArrayList;
import java.util.Collections;
import lejos.geom.Point;
import lejos.robotics.mapping.PRLineMap;

/*
 * Author Michal Staniaszek
 */
public class PRMapNode {

    protected ArrayList<PRMapNode> linkedTo;
    protected Point location;
    protected PRMapNode previous;
    protected int cost, expectedCost;

    /**
     * Constructor.  Calls the super constructor
     * (Point) with x and y values.
     * @param x X value of the point to add.
     * @param y Y value of the point to add.
     */
    public PRMapNode(int x, int y) {
        location = new Point(x, y);
        linkedTo = new ArrayList<PRMapNode>();
    }

    /**
     * Constructor.  Calls the super constructor with a Point as input.
     * @param p Location of this point.
     */
    public PRMapNode(Point p) {
        location = new Point(p.x, p.y);
        linkedTo = new ArrayList<PRMapNode>();
    }

    /**
     * Adds a link from this point to the point received in
     * parameters, and adds a link from that point to this point.
     * @param p A point to which to link this point.
     */
    public void addLink(PRMapNode p) {
        System.out.println("received point " + p + "as input");
        if (!isLinkedTo(p)) {
            linkedTo.add(p);
            System.out.println("Created link from " + this + " to " + p);
        }
        if (!p.isLinkedTo(this)) {
            p.addLink(this);
        }
    }

    public Point getPoint() {
        return location;
    }

    /**
     * Checks if the passed point is linked to this one.
     * @param p A point to check.
     * @return Boolean value.  True if the linkedTo array contains
     * the point p, false otherwise.
     */
    public boolean isLinkedTo(PRMapNode p) {
        return linkedTo.contains(p);
    }

    /**
     * Gets all the points that this point is linked to.
     * @return An arraylist of PRMapPoints.
     */
    public ArrayList<PRMapNode> getLinkedPoints() {
        return linkedTo;
    }

    /**
     * Returns points that are within a specified distance of this node.
     * @param roadMapNodes The array of nodes to search.
     * @param maxDistance The maximum distance of other nodes from this node.
     * @return A PRMapNode array containing up to 30 PRMapNodes which are within
     * a circle of radius MAX_DISTANCE (where this point is the centre)
     * , and are sorted in order of closest to furthest away from this node.
     */
    public ArrayList<PRMapNode> nodesNearTo(ArrayList<PRMapNode> roadMapNodes, int maxDistance) {
        ArrayList<PRMapNode> neighbours = new ArrayList<PRMapNode>();
        Collections.sort(roadMapNodes, new PRMapNodeComparator(this));
        while (neighbours.size() < 30) {
            for (PRMapNode pRMapNode : roadMapNodes) {
                if (pRMapNode.distanceFromPoint(this) < maxDistance) {
                    neighbours.add(pRMapNode);
                }
            }
        }
        return neighbours;
    }

    /**
     * Checks whether this node is in an object within a linemap.
     * @param lineMap A linemap containing a map of the world to be navigated.
     * @return Boolen value.  True if this point is inside an object (i.e. not in
     * a legal position), false otherwise.
     */
    public boolean inObject(PRLineMap lineMap) {
        return lineMap.inside(getPoint());
    }

    /**
     * Gets the distance of this node from another node.
     * @param p Node to check the distance to.
     * @return An int representing euclidian distance between the two points.
     */
    public double distanceFromPoint(PRMapNode p) {
        return location.distance(p.location);
    }

    /**
     * Gets the expected cost from this point to another point.
     * @param p A PRMapNode.
     * @return The straight line distance between this point and the passed point.
     */
    int expectedCost(PRMapNode p) {
        return (int) p.distanceFromPoint(this);
    }

    /**
     * Returns an array of the nodes linked to this one, but also updates the
     * cost and expected cost values of the nodes.
     * @param goal The goal point.
     * @param visited The nodes that have already been visited by search.
     * @param cost The current cost.
     * @return Array of nodes with their costs changed.
     */
    public ArrayList<PRMapNode> nextPointsHeuristic(final PRMapNode goal, final ArrayList<PRMapNode> visited, int cost) {
        ArrayList<PRMapNode> children = new ArrayList<PRMapNode>();
        for (PRMapNode pRMapNode : linkedTo) {
            if (!visited.contains(pRMapNode)) {
                pRMapNode.cost = cost;
                pRMapNode.expectedCost = expectedCost(goal);
                children.add(pRMapNode);
            }
        }
        return children;
    }

    public static void main(String[] args) {
        PRMapNode t1 = new PRMapNode(3, 4);
        PRMapNode t2 = new PRMapNode(5, 6);
        t1.addLink(t2);
    }
}
