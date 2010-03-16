/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lineMap;

/*
 * Author Michal Staniaszek
 */
public class Edge {

    double length;
    PRMapNode point1;
    PRMapNode point2;

    public Edge(PRMapNode point1, PRMapNode point2) {
        this.point1 = point1;
        this.point2 = point2;
        length = point1.distanceFromPoint(point2);
    }

    public double getLength() {
        return length;
    }

    public PRMapNode getPoint1() {
        return point1;
    }

    public PRMapNode getPoint2() {
        return point2;
    }

}
