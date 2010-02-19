/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search;
import util.Queue;
/**
 * 
 * @author Jeremiah Via
 */
public class BreadthFirstSearch 
{
    Queue<GridPoint> agendaList;
    GridPoint currPoint, goalPoint;
    GridPoint [][] grid;

    public BreadthFirstSearch(GridPoint [][] grid,GridPoint currPoint, GridPoint goalPoint) {
        this.grid = grid;
        this.currPoint = currPoint;
        this.goalPoint = goalPoint;
    }

    public GridPoint getcurrPoint() {
        return currPoint;
    }

    public void setcurrPoint(GridPoint currPoint) {
        this.currPoint = currPoint;
    }

    public GridPoint getgoalPoint() {
        return goalPoint;
    }

    public void setgoalPoint(GridPoint goalPoint) {
        this.goalPoint = goalPoint;
    }

    public void add(GridPoint node) {
        agendaList.push(node);
    }

    public void remove() {
        currPoint = agendaList.pop();
    }

    public void generateChildren() {
        if (currPoint.getX() + 1 < grid.length && currPoint.east)
            agendaList.add(grid[(int)currPoint.getX()+1][(int)currPoint.getY()]);
        
        if (currPoint.getX() - 1 > 0 && currPoint.west)
            agendaList.add(grid[(int)currPoint.getX()-1][(int)currPoint.getY()]);
        
        if (currPoint.getY() + 1 < grid[0].length && currPoint.south)
            agendaList.add(grid[(int)currPoint.getX()][(int)currPoint.getY()+1]);
        
        if (currPoint.getY() -1 > 0 && currPoint.north)
            agendaList.add(grid[(int)currPoint.getX()][(int)currPoint.getY()-1]);
    }

    public boolean isGoal(GridPoint node) {
        return node.getX() == currPoint.getX() &&
                node.getY() == currPoint.getY();
    }
}