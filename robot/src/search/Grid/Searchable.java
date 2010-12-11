/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.Grid;
import java.util.ArrayList;
/**
 *
 * @author Jeremiah
 */
public interface Searchable<T> extends Comparable<T> {

    int compareTo(T t);


    int expectedCost(T point);

    ArrayList<GridPoint> nextPoints(final ArrayList<GridPoint> visited,
                                    int height, int width);

}
