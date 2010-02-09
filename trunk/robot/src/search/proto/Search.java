/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.proto;

import java.awt.Point;

/**
 * 
 * @author Jeremiah Via
 */
public class Search {

    public static void main(String[] args) {
        generateGrid(10,10);
       
    }

    public static Point[] generateGrid(int width, int height) {
        Point[] points = new Point[width * height];

        for (int i = 0, x = 0, y = 0; i < points.length; i++) {
            if (x == width) {
                y++;
                x = 0;
            }
            points[i] = new Point(x++, y);
            System.out.println(points[i]);
        }
        return points;
    }
}
