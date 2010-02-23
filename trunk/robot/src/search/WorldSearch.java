/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;
import lejos.nxt.Button;

/**
 * 
 * @author Jeremiah Via
 */
public class WorldSearch {

    int maxMissionaries, maxCannibals, boatSize;

    public WorldSearch(int maxMissionaries, int maxCannibals, int boatSize) {
        this.maxMissionaries = maxMissionaries;
        this.maxCannibals = maxCannibals;
        this.boatSize = boatSize;
    }

    ArrayList<World> steps(World w) {
        ArrayList<World> steps = new ArrayList<World>();
        while (w != null) {
            steps.add(w);
            w = w.previous;
        }

        return steps;

    }

    public ArrayList<World> searchWorlds(World start, World goal) {

        ArrayList<World> stateList = new ArrayList<World>();
        ArrayList<World> previousStates = new ArrayList<World>();

        start.previous = null;

        stateList.add(start);
        while (stateList.size() != 0) {
            World currentWorld = stateList.remove(0);
            if (currentWorld.equals(goal)) {
                return steps(currentWorld);
            }
            else {
                previousStates.add(currentWorld);
                stateList.addAll(
                        currentWorld.next(previousStates, boatSize,
                                          maxMissionaries, maxCannibals));
            }
            for (int i = 0; i < stateList.size(); i++) {
                if (previousStates.contains(stateList.get(i))) {
                    stateList.remove(i);
                    i = 0;
                }

            }
        }

        return steps(start);

    }

    public static void main(String[] args) {

        WorldSearch search = new WorldSearch(3, 3, 2);

        World start = new World(null,0,0,0);
        World goal = new World(null, 3,3,1);

        ArrayList<World> worlds = search.searchWorlds(start, goal);
        for (World world : worlds) {
            System.out.print(world + " ");
        }
        Button.waitForPress();
    }

}
