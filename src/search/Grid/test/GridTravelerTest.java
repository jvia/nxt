package search.Grid.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

/**
 *
 * @author Jeremiah Via
 */
public class GridTravelerTest {

    public static void main(String[] args) {
        LightSensor l = new LightSensor(SensorPort.S2);
        LightSensor r = new LightSensor(SensorPort.S4);

        while (true)
        {
            System.out.println(l.getLightValue() + ", " + r.getLightValue());
        }
    }}