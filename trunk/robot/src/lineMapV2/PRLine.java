package lineMapV2;

import lejos.geom.Line;
import lejos.geom.Point;


/**
 * Represents a line and supports calculating the point of intersection of two
 * line segments.
 *
 * @author Lawrie Griffiths
 *
 * <br/><br/>WARNING: THIS CLASS IS SHARED BETWEEN THE classes AND pccomms PROJECTS.
 * DO NOT EDIT THE VERSION IN pccomms AS IT WILL BE OVERWRITTEN WHEN THE PROJECT IS BUILT.
 *
 */
public class PRLine extends Line {

    public PRLine(float x1, float y1, float x2, float y2) {
        super(x1, y1, x2, y2);
    }

    public Point getPoint1(){
        return new Point(x1, y1);
    }
    
    public Point getPoint2(){
        return new Point(x2, y2);
    }

}

