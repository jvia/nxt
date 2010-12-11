/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.proto.experiemnts;

/**
 * 
 * @author Jeremiah Via
 */
public class Path
{

    protected boolean blocked;

    public Path(boolean blocked) {
        this.blocked = blocked;
    }

    
    public Path() {
        blocked = false;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked(){return blocked;}

}
