/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;

/**
 * 
 *
 * Exercise 04
 * 24 February 2010
 * J. Via, M. Staniaszek
 */
public class World {

    World previous;
    int missionaries, cannibals, boat;

    public World(World previous, int missionaries, int cannibals, int boat) {
        this.previous = previous;
        this.missionaries = missionaries;
        this.cannibals = cannibals;
        this.boat = boat;
    }

    public ArrayList<World> next(ArrayList<World> previous, int boatSize,
                                 int maxMisisonaries, int maxCannibals) {
        ArrayList<World> newWorlds = new ArrayList<World>();

        int newBoat = (boat == 1) ? 0 : 1;

        if (boat == 0) {
            for (int i = 0; i <= boatSize; i++) {
                for (int j = 0; j <= boatSize - i; j++) {
                    World w = new World(this, missionaries + i, cannibals + j,
                                        newBoat);
                    if (isLegal(w, maxMisisonaries, maxCannibals))
                        newWorlds.add(w);
                }
            }
        }
        else { // boat == 1
            for (int i = 0; i <= boatSize; i++) {
                for (int j = 0; j <= boatSize - i; j++) {
                    World w = new World(this, missionaries - i, cannibals - j,
                                        newBoat);
                    if (isLegal(w, maxMisisonaries, maxCannibals))
                        newWorlds.add(w);
                }
            }
        }

        for (World w : newWorlds)
            System.out.println(w);
        // remove previous worlds
        for (int i = 0; i < newWorlds.size(); i++) {
            if (previous.contains(newWorlds.get(i))) {
                newWorlds.remove(i);
                i = 0;
            }

        }

        for (World w : newWorlds)
            System.out.println(w);
        return newWorlds;
    }

    public boolean isLegal(World world, int maxM, int maxC) {

        if (world.missionaries != world.cannibals)
            return false;
        else if (world.missionaries < 0 || world.cannibals < 0)
            return false;
        if (world.missionaries == maxM || world.missionaries == 0)
            return true;
        return true;

    }

    public boolean equals(World w) {
        return this.missionaries == w.missionaries
               && this.cannibals == w.cannibals
               && this.boat == w.boat;
    }

    @Override
    public String toString() {
        return "" + missionaries + cannibals + boat;
    }
}
