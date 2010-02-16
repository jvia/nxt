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
    protected Path east, west, north, south;
    protected boolean visited;

    public GridPoint(int x, 
                     int y,
                     boolean visited,
                     Path east,
                     Path west,
                     Path north,
                     Path south) {
        point = new Point(x, y);
        this.east = east;
        this.west = west;
        this.north = north;
        this.south = south;
        this.visited = visited;
    }

    

    public boolean hasBeenVisisted(){ return visited; }


}
