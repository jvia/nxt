///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package lineMapV2;
//
//import java.awt.Rectangle;
//import java.util.ArrayList;
//import java.util.Collections;
//import lejos.geom.Line;
//import lejos.robotics.proposal.WayPoint;
//
///*
// * Author Michal Staniaszek
// */
//public class PRMap {
//
//    int links;
//    final int ROBOT_WIDTH = 6;
//    final int MAX_DISTANCE = 150;    final int MAX_NODES = 50;
//    PRLineMap lineMap;
//    ArrayList<PRMapNode> roadMapNodes;
//    ArrayList<Line> edges;
////    ArrayList<ArrayList<PRMapNode>> adjacencyList = new ArrayList<ArrayList<PRMapNode>>();
//
//    /**
//     * Default constructor initialises the roadMapNodes array and the lineMap object.
//     * @param lineMap
//     */
//    public PRMap(PRLineMap lineMap) {
//        this.lineMap = lineMap;
//        roadMapNodes = new ArrayList<PRMapNode>();
//        edges = new ArrayList<Line>();
//    }
//
//    public void makeRoadMap() {
//        while (roadMapNodes.size() < MAX_NODES) {
//            PRMapNode newNode = generateNode();
//            roadMapNodes.add(newNode);
//            ArrayList<PRMapNode> neighbours = newNode.nodesNearTo(roadMapNodes, MAX_DISTANCE);
//            for (PRMapNode pRMapNode : neighbours) {
//                if (collisionFreePath(newNode, pRMapNode)) {
//                    newNode.addLink(pRMapNode);
//                    links++;
//                }
//            }
//        }
////        createAdjacencyList();
//    }
//
//    public boolean collisionFreePath(PRMapNode p1, PRMapNode p2) {
//        Line link = new Line(p1.getPoint().x, p1.getPoint().y, p2.getPoint().x, p2.getPoint().y);
//        Line up = new Line(p1.getPoint().x + ROBOT_WIDTH, p1.getPoint().y + ROBOT_WIDTH, p2.getPoint().x + ROBOT_WIDTH, p2.getPoint().y + ROBOT_WIDTH);
//        Line down = new Line(p1.getPoint().x - ROBOT_WIDTH, p1.getPoint().y - ROBOT_WIDTH, p2.getPoint().x - ROBOT_WIDTH, p2.getPoint().y - ROBOT_WIDTH);
//        for (Line line : lineMap.getLines()) {
//            if (link.intersectsLine(line) || down.intersectsLine(line) || up.intersectsLine(line));
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Generates random points until one is found which is not inside an object.
//     * @return A point in free space in the lineMap.
//     */
//    public PRMapNode generateNode() {
//        boolean pointValid = false;
//        PRMapNode generatedPoint = null;
//        while (!pointValid) {
//            int x = (int) (Math.random() * lineMap.getBoundingRect().width);
//            int y = (int) (Math.random() * lineMap.getBoundingRect().height);
//            generatedPoint = new PRMapNode(x, y);
//            if (!generatedPoint.inObject(lineMap)) {
//                pointValid = true;
//            }
//        }
//        return generatedPoint;
//    }
//
////    /**
////     * Creates an adjacency list of all the points.
////     */
////    public void createAdjacencyList() {
////        // Adds this node's connected components to the matrix.
////        adjacencyList.add(roadMapNodes.get(0).linkedTo);
////        // For each arraylist of PRMapNodes in the arraylist...
////        for (ArrayList<PRMapNode> arrayList : adjacencyList) {
////            /**
////             * Add each node's linkedTo list to the arrayList, if it is not already
////             * present in the arraylist.
////             */
////            for (PRMapNode pRMapNode : arrayList) {
////                if (!adjacencyList.contains(pRMapNode.linkedTo)) {
////                    adjacencyList.add(pRMapNode.linkedTo);
////                }
////            }
////        }
////    }
//    public ArrayList<WayPoint> aStar(PRMapNode start, PRMapNode goal) {
//        assert (start.location.x < lineMap.getBoundingRect().width && start.location.x > 0);
//        assert (start.location.y < lineMap.getBoundingRect().height && start.location.y > 0);
//        assert (goal.location.x < lineMap.getBoundingRect().width && goal.location.x > 0);
//        assert (goal.location.y < lineMap.getBoundingRect().height && goal.location.y > 0);
//
//
//        /* Points to visit */
//        ArrayList<PRMapNode> agenda = new ArrayList<PRMapNode>(),
//                /* Points visited */
//                visited = new ArrayList<PRMapNode>();
//
//        /* Previous is set to null so that route gneration won't
//        go further than it should */
//        start.previous = null;
//        start.cost = 0;
//        start.expectedCost = start.expectedCost(goal);
//
//        /* Add first item to agenda */
//        agenda.add(start);
//
//        /* Pop off first item in agenda, if it's the goal, we return the route
//         * otherwise we add the next generation of points to visit
//         */
//        while (!(agenda.size() == 0)) {
//            PRMapNode current = agenda.remove(0);
//            if (current.equals(goal)) {
//                return route(current);
//            } else {
//                visited.add(current);
//                agenda.addAll(current.nextPointsHeuristic(goal, visited, start.cost++));
//                Collections.sort(agenda, new PRMapNodeComparator(current));
//            }
//        }
//
//        return route(start);
//    }
//
//    /**
//     * This method is given the current point that matches the goal point
//     * and then precedes to move backwards through the previous points in similar
//     * fashion to a linked-list. Doing this, it constructs the route of points
//     * that lead to the solution.
//     *
//     * @param point current point that is equal to the goal point
//     * @return list of points from start to goal
//     */
//    public ArrayList<WayPoint> route(PRMapNode point) {
//        PRMapNode pNode = point;
//        WayPoint wayPoint = new WayPoint(pNode.location);
//        ArrayList<WayPoint> route = new ArrayList<WayPoint>();
//        while (point != null) {
//            route.add(0, wayPoint);
//            pNode = pNode.previous;
//            wayPoint = new WayPoint(pNode.location);
//        }
//        return route;
//    }
//
//    public void connectStartAndGoal(PRMapNode start, PRMapNode goal) {
//        for (PRMapNode pRMapNode : start.nodesNearTo(roadMapNodes, MAX_DISTANCE)) {
//            if (collisionFreePath(start, pRMapNode)) {
//                start.addLink(pRMapNode);
//            }
//        }
//        for (PRMapNode pRMapNode : goal.nodesNearTo(roadMapNodes, MAX_DISTANCE)) {
//            if (collisionFreePath(start, pRMapNode)) {
//                start.addLink(pRMapNode);
//            }
//        }
//        roadMapNodes.add(start);
//        roadMapNodes.add(goal);
//    }
//
//    public int getLinks() {
//        return links;
//    }
//
//
//
//    public static void main(String[] args) {
//
//        float width = 487f;
//        float height = 240f;
//
//        Line[] lines = new Line[]{
//            //world outline
//            new Line(0f, 0f, width, 0f),
//            new Line(0f, 0f, 0f, 240.4f),
//            new Line(width, 0f, width, 240.4f),
//            new Line(0f, 240.4f, width, 240.4f),
//            //captain america
//            new Line(73.4f, 104.7f, 135f, 104.7f),
//            new Line(73.4f, 104.7f, 73.4f, 41.6f),
//            new Line(73.4f, 41.6f, 135f, 41.6f),
//            new Line(135f, 104.7f, 135f, 41.6f),
//            //basil
//            new Line(64.4f, 193f, 188.6f, 193f),
//            new Line(64.4f, 193f, 64.4f, 153f),
//            new Line(64.4f, 153f, 188.6f, 153f),
//            new Line(188.6f, 193f, 188.6f, 153f),
//            //obi wan
//            new Line(234.4f, 218.3f, 254.5f, 218.3f),
//            new Line(254.5f, 218.3f, 254.5f, 240.4f),
//            new Line(234.4f, 218.3f, 234.4f, 240.4f),
//            //gromit
//            new Line(282f, 137f, 305.5f, 137f),
//            new Line(305.5f, 137f, 305.5f, 156.5f),
//            new Line(305.5f, 156.5f, 282f, 156.5f),
//            new Line(282f, 156.5f, 282f, 137f),
//            //preston
//            new Line(333.5f, 180f, 377f, 180f),
//            new Line(377f, 180f, 377f, 240.4f),
//            new Line(333.5f, 180f, 333.5f, 240.4f),
//            new Line(360f, 83.5f, 403.5f, 83.5f),
//            //jaffa
//            new Line(360.5f, 83.5f, 403.5f, 83.5f),
//            new Line(403.5f, 83.5f, 403.5f, 123.5f),
//            new Line(403.5f, 123.5f, 360.5f, 123.5f),
//            new Line(360.5f, 123.5f, 360.5f, 83.5f),
//            //wallace
//            new Line(420.5f, 157, 444f, 157f),
//            new Line(444f, 157f, 444f, 196.5f),
//            new Line(444f, 196.5f, 420.5f, 196.5f),
//            new Line(420.5f, 196.5f, 420.5f, 157f),
//            //tinky-winky
//            new Line(234.5f, 0, 234.5f, 21.5f),
//            new Line(234.5f, 21.5f, 244f, 21.5f),
//            new Line(244f, 21.5f, 244f, 64f),
//            new Line(244f, 64f, 305f, 64f),
//            new Line(305f, 64f, 305f, 42f),
//            new Line(305f, 42f, 346.5f, 42f),
//            new Line(346.5f, 42f, 346.5f, 0f),
//            //gandalf
//            new Line(width, 21.5f, width - 20.5f, 21.5f),
//            new Line(width - 20.5f, 21.5f, width - 20.5f, 0f)
//        };
//
//        PRLineMap map = new PRLineMap(lines, new Rectangle((int) width,
//                (int) height));
//
//        PRMap probRoadMap = new PRMap(map);
//        probRoadMap.makeRoadMap();
//
//        for (PRMapNode node : probRoadMap.roadMapNodes) {
//            System.out.println(node.toString());
//            System.out.println(node.getLinkedPoints().toString());
//            System.out.println("-------------------------------");
//
//        }
//
//        System.out.println("There are " + probRoadMap.getLinks() + " links");
//
//    }
//
//}
