/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lineMap;

import java.util.ArrayList;
import java.util.Collections;
import lejos.geom.Line;
import lejos.robotics.mapping.PRLineMap;

/*
 * Author Michal Staniaszek
 */
public class PRMap {

    final int ROBOT_WIDTH = 6;
    final int MAX_DISTANCE = 150;
    final int MAX_NODES = 200;
    PRLineMap lineMap;
    ArrayList<PRMapNode> roadMapNodes;
    ArrayList<Line> edges;
//    ArrayList<ArrayList<PRMapNode>> adjacencyList = new ArrayList<ArrayList<PRMapNode>>();

    /**
     * Default constructor initialises the roadMapNodes array and the lineMap object.
     * @param lineMap
     */
    public PRMap(PRLineMap lineMap) {
        this.lineMap = lineMap;
        roadMapNodes = new ArrayList<PRMapNode>();
        edges = new ArrayList<Line>();
    }

    public void makeRoadMap() {
        while (roadMapNodes.size() < MAX_NODES) {
            PRMapNode newNode = generateNode();
            roadMapNodes.add(newNode);
            ArrayList<PRMapNode> neighbours = newNode.nodesNearTo(roadMapNodes, MAX_DISTANCE);
            for (PRMapNode pRMapNode : neighbours) {
                if (collisionFreePath(newNode, pRMapNode)) {
                    newNode.addLink(pRMapNode);
                }
            }
        }
//        createAdjacencyList();
    }

    public boolean collisionFreePath(PRMapNode p1, PRMapNode p2) {
        Line link = new Line(p1.getPoint().x, p1.getPoint().y, p2.getPoint().x, p2.getPoint().y);
        Line up = new Line(p1.getPoint().x + ROBOT_WIDTH, p1.getPoint().y + ROBOT_WIDTH, p2.getPoint().x + ROBOT_WIDTH, p2.getPoint().y + ROBOT_WIDTH);
        Line down = new Line(p1.getPoint().x - ROBOT_WIDTH, p1.getPoint().y - ROBOT_WIDTH, p2.getPoint().x - ROBOT_WIDTH, p2.getPoint().y - ROBOT_WIDTH);
        for (Line line : lineMap.getLines()) {
            if (link.intersectsLine(line) || down.intersectsLine(line) || up.intersectsLine(line));
            return false;
        }
        return true;
    }

    /**
     * Generates random points until one is found which is not inside an object.
     * @return A point in free space in the lineMap.
     */
    public PRMapNode generateNode() {
        boolean pointValid = false;
        PRMapNode generatedPoint = null;
        while (!pointValid) {
            int x = (int) (Math.random() * lineMap.getBoundingRect().width);
            int y = (int) (Math.random() * lineMap.getBoundingRect().height);
            generatedPoint = new PRMapNode(x, y);
            if (!generatedPoint.inObject(lineMap)) {
                pointValid = true;
            }
        }
        return generatedPoint;
    }

//    /**
//     * Creates an adjacency list of all the points.
//     */
//    public void createAdjacencyList() {
//        // Adds this node's connected components to the matrix.
//        adjacencyList.add(roadMapNodes.get(0).linkedTo);
//        // For each arraylist of PRMapNodes in the arraylist...
//        for (ArrayList<PRMapNode> arrayList : adjacencyList) {
//            /**
//             * Add each node's linkedTo list to the arrayList, if it is not already
//             * present in the arraylist.
//             */
//            for (PRMapNode pRMapNode : arrayList) {
//                if (!adjacencyList.contains(pRMapNode.linkedTo)) {
//                    adjacencyList.add(pRMapNode.linkedTo);
//                }
//            }
//        }
//    }
    public ArrayList<PRMapNode> aStar(PRMapNode start, PRMapNode goal) {
        assert (start.location.x < lineMap.getBoundingRect().width && start.location.x > 0);
        assert (start.location.y < lineMap.getBoundingRect().height && start.location.y > 0);
        assert (goal.location.x < lineMap.getBoundingRect().width && goal.location.x > 0);
        assert (goal.location.y < lineMap.getBoundingRect().height && goal.location.y > 0);


        /* Points to visit */
        ArrayList<PRMapNode> agenda = new ArrayList<PRMapNode>(),
                /* Points visited */
                visited = new ArrayList<PRMapNode>();

        /* Previous is set to null so that route gneration won't
        go further than it should */
        start.previous = null;
        start.cost = 0;
        start.expectedCost = start.expectedCost(goal);

        /* Add first item to agenda */
        agenda.add(start);

        /* Pop off first item in agenda, if it's the goal, we return the route
         * otherwise we add the next generation of points to visit
         */
        while (!(agenda.size() == 0)) {
            PRMapNode current = agenda.remove(0);
            if (current.equals(goal)) {
                return route(current);
            } else {
                visited.add(current);
                agenda.addAll(current.nextPointsHeuristic(goal, visited, start.cost++));
                Collections.sort(agenda, new PRMapNodeComparator(current));
            }
        }

        return route(start);
    }

    /**
     * This method is given the current point that matches the goal point
     * and then precedes to move backwards through the previous points in similar
     * fashion to a linked-list. Doing this, it constructs the route of points
     * that lead to the solution.
     *
     * @param point current point that is equal to the goal point
     * @return list of points from start to goal
     */
    public ArrayList<PRMapNode> route(PRMapNode point) {
        ArrayList<PRMapNode> route = new ArrayList<PRMapNode>();
        while (point != null) {
            route.add(0, point);
            point = point.previous;
        }
        return route;
    }

    public void connectStartAndGoal(PRMapNode start, PRMapNode goal) {
        for (PRMapNode pRMapNode : start.nodesNearTo(roadMapNodes, MAX_DISTANCE)) {
            if (collisionFreePath(start, pRMapNode)) {
                start.addLink(pRMapNode);
            }
        }
        for (PRMapNode pRMapNode : goal.nodesNearTo(roadMapNodes, MAX_DISTANCE)) {
            if (collisionFreePath(start, pRMapNode)) {
                start.addLink(pRMapNode);
            }
        }
        roadMapNodes.add(start);
        roadMapNodes.add(goal);
    }
}
