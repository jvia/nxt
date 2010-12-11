/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search.Grid;
import java.util.ArrayList;
/**
 * 
 * @author Jeremiah Via
 */
public class MCState
{
    MCState previous;
    // string encodes missionaries, cannibals, and the boat in the form of
    // MCB, where the numbers are the respective type on the goal shore
    // ex: 211 = 2 missionaries, 1 cannibal, 1 boat on goal shore
    //     010 = 0 missionaries, 1 cannibal, 0 boat on goal shore
    int M,C,B;
    int boatSize;

    public MCState(int M, int C, int B, int boatSize, MCState previous) {
        this.M = M;
        this.C = C;
        this.B = B;
    }

    // start is 000
    // goal is 331

    public ArrayList<MCState> nextStates(final ArrayList<MCState> previousStates, int maxM, int maxC){
        ArrayList<MCState> newStates = new ArrayList<MCState>();

        // boat must always switch
        int newB = (B == 1)? 0 : 1;

        for (int i = boatSize; i > 0 ; i--){
            newStates.add(new MCState(M+i, C + (boatSize-i), newB, boatSize, this));
        }


        return newStates;
    }



}
