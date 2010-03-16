/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lineMap;

import java.util.Comparator;

/*
 * Author Michal Staniaszek
 */
public class PRMapNodeComparator implements Comparator<PRMapNode> {

    PRMapNode compNode;

    public PRMapNodeComparator(PRMapNode compNode) {
        super();
        this.compNode = compNode;
    }

    public int compare(PRMapNode n1, PRMapNode n2) {
        double node1Distance = n1.distanceFromPoint(compNode);
        double node2Distance = n2.distanceFromPoint(compNode);
        if (node1Distance > node2Distance) {
            return 1;
        } else if (node2Distance > node1Distance) {
            return -1;
        } else {
            return 0;
        }
    }
}
