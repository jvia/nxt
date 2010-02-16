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

    public GridPoint(int x, int y, boolean visited) {
        point = new Point(x,y);
        this.visited = visited;
    }

    public GridPoint(int x, int y){ point = new Point(x,y); visited = false;}

    public Path getEast() {
        return east;
    }

    public void setEast(Path east) {
        this.east = east;
    }

    public Path getNorth() {
        return north;
    }

    public void setNorth(Path north) {
        this.north = north;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Path getSouth() {
        return south;
    }

    public void setSouth(Path south) {
        this.south = south;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Path getWest() {
        return west;
    }

    public void setWest(Path west) {
        this.west = west;
    }

    public int getX(){ return (int) point.x;}
    public int getY(){return (int) point.y;}

    public boolean hasBeenVisisted(){ return visited; }

    @Override
    public String toString(){
        return "(" + point.getX() + ", " + point.getY() + ")";
    }
}