/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search;

/**
 * 
 * @author Jeremiah Via
 */
public interface Search
{
    public void add(SearchNode node);
    public void remove();
    public void generateChildren();
    
    public boolean isGoal(SearchNode node);
    public void setGoal();
    public SearchNode getGoal();

}
