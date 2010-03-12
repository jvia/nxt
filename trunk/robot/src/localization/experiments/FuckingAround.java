/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package localization.experiments;

import java.awt.Rectangle;
import lejos.geom.Line;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.mapping.LineMap;

/**
 * 
 * @author Jeremiah Via
 */
public class FuckingAround {

    public static void main(String[] args) {
        OpticalDistanceSensor ds = new OpticalDistanceSensor(SensorPort.S1);

        //all measurements in cm, 0,0 in bottom left
        Line[] segments = new Line[]{
            // triangle on west wall
            new Line(0f, 43.5f, 23f, 43.5f),
            new Line(23f, 43.5f, 0f, 103f),
            new Line(0f, 103f, 0f, 43.5f),

            // free-floating square
            new Line(95f, 70.5f, 110f, 89f),
            new Line(110f, 89f, 95f, 104.5f),
            new Line(95f, 104.5f, 80.5f, 84f),
            new Line(80.5f, 84f, 95f, 70.5f),

            // triangle on south wall
            new Line(116f, 0f, 174f, 0f),
            new Line(174f, 0f, 145f, 29f),
            new Line(145f, 29f, 116f, 0f),

            // square in south-east corner
            new Line(219f, 0f, 239.5f, 0f),
            new Line(239.5f, 0f, 239.5f, 21f),
            new Line(239.5f, 21f, 219f, 21f),
            new Line(219f, 21f, 219f, 0f),

            // free-floating parallelogram
            new Line(149f, 91.5f, 190f, 88f),
            new Line(190f, 88f, 209f, 127.5f),
            new Line(209f, 127.5f, 167.5f, 127f),
            new Line(167.5f, 127f, 149f, 91.5f),

            // free-floating rectangle
            new Line(49.5f, 134.5f, 70f, 146.5f),
            new Line(70f, 146.5f, 50.5f, 181f),
            new Line(50.5f, 181f, 30f, 169f),
            new Line(30f, 169f, 49.5f, 134.5f),

            // square on north wall
            new Line(86.5f, 180f, 130f, 180f),
            new Line(130f, 180f, 130.5f, 240f),
            new Line(130.5f, 240f, 88f, 240f),
            new Line(88f, 240f, 86.5f, 180f),};

        LineMap map = new LineMap(segments, new Rectangle(0, 0, 240, 240));

        while (true) {
            System.out.println(ds.getDistance());

        }
    }
}
