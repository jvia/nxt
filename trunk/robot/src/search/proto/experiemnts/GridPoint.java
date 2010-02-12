/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.proto.experiemnts;
import lejos.geom.Point;
/**
 * 
 * @author Jeremiah Via
 */
public class GridPoint
{
    protected Point point;
    protected boolean visited;

    public GridPoint(int x, int y, boolean visited) {
        point = new Point(x,y);
        this.visited = visited;
    }

    public boolean hasBeenVisisted(){ return visited; }


}
