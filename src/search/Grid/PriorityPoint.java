/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.Grid;
import lejos.geom.Point;
/**
 * 
 * @author Jeremiah Via
 */
public class PriorityPoint extends Point implements Comparable<PriorityPoint>
{
    int priorityValue;

    public PriorityPoint(float x, float y, int priorityValue){
        super(x,y);
        this.priorityValue = priorityValue;
    }

    public PriorityPoint(float x, float y){
        this(x, y, 0);
        
    }

    public int compareTo(PriorityPoint o) {
        Integer pv = priorityValue;
        return pv.compareTo(o.priorityValue);
    }

}
