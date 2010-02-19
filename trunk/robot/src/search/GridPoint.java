/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import lejos.geom.Point;

/**
 * 
 * @author Jeremiah Via
 */
public class GridPoint implements SearchNode {

    Point point;
    boolean north, south, east, west;


    public GridPoint(Point point, boolean north, boolean south, boolean east,
                     boolean west) {
        this.point = point;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public float getX(){
        return point.x;
    }

    public float getY(){
        return point.y;
    }

    public boolean isGoal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void generateChildren() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean equals(GridPoint point) {
        return this.point.x == point.getX() && this.point.y == point.getY();
    }


}
