package util;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.robotics.proposal.DifferentialPilot;

/**
 * A line following class which works by avoiding a line. The robot should wiggle along the line attempting to keep both sensors off the line.
 *
 * @author nah
 */
public class LineFollower {

  private final DifferentialPilot m_pilot;

  private final LightSensor m_leftLightSensor;

  private final LightSensor m_rightLightSensor;

  private final int m_lineThreshold;

  private final int m_distFromSensorsToWheels;

  /**
   * Prints the value from the given light sensor to the LCD screen. Runs until the escape button is pressed.
   *
   * @param _sensor The light sensor to monitor.
   */
  public static void printSensorValue(LightSensor _sensor) {
    assert _sensor != null : "Input sensor is null";
    try {
      while (!Button.ESCAPE.isPressed()) {
        LCD.drawString("Light: " + _sensor.getLightValue(), 0, 4);
        Thread.sleep(100);
      }
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Construct a new line following object. The left and right side of the robot are defined from the point of view of someone standing behind the robot.
   *
   * @param _pilot A differential pilot to drive the robot.
   * @param _leftLightSensor The left light sensor.
   * @param _rightLightSensor The right light sense
   * @param _lineThreshold The threshold used to determine whether a line has been seen by a light sensor. Any value below this is considered to indicate the presence of a line.
   * @param _distFromSensorsToWheels The distance between the beam of the light sensors and the axle of the robot. In the same units as used to define _pilot.
   */
  public LineFollower(DifferentialPilot _pilot, LightSensor _leftLightSensor,
          LightSensor _rightLightSensor, int _lineThreshold,
          int _distFromSensorsToWheels) {
    m_pilot = _pilot;
    m_leftLightSensor = _leftLightSensor;
    m_rightLightSensor = _rightLightSensor;
    m_lineThreshold = _lineThreshold;
    m_distFromSensorsToWheels = _distFromSensorsToWheels;
  }

  /**
   * Used to check whether the given sensor is detecting a line or not.
   * @param _sensor
   * @return
   */
  protected boolean isOverLine(LightSensor _sensor) {
    return _sensor.getLightValue() < m_lineThreshold;
  }

  /**
   * Determines if the robot has arrived at a junction. It determines this if both sensors detect a line.
   * 
   * @return True if both sensors detect a line.
   */
  protected boolean atJunction() {
    return isOverLine(m_leftLightSensor) &&
            isOverLine(m_rightLightSensor);
  }

  /**
   * Determines if the robot is arrived at a junction. It determines this if both sensors do not detect a line.
   *
   */
  protected boolean alignedWithLine() {
    return !isOverLine(m_leftLightSensor) &&
            !isOverLine(m_rightLightSensor);
  }

  /**
//   * Follows a line until a junction is detected.
   */
  public void followLineUntilJunction() {

    try {
      while (!atJunction()) {
        if (alignedWithLine()) {
          m_pilot.forward();
        } else if (isOverLine(m_leftLightSensor)) {
          m_pilot.rotate(20, true);
        } else if (isOverLine(m_rightLightSensor)) {
          m_pilot.rotate(-20, true);
        }
        Thread.sleep(50);
      }
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
    }

    //make sure we're not moving at the function
    m_pilot.stop();
  }

  /**
   * Turns the robot right until the the left light sensor detects a line after an initial period (to get the robot off the current line).
   */
  public void turnRightUntilLine() {
    turnUntilLine(m_leftLightSensor, 120);
  }

  /**
   * Turns the robot left until the right light sensor detects a line after an initial period (to get the robot off the current line).
   */
  public void turnLeftUntilLine() {
    turnUntilLine(m_rightLightSensor, -120);
  }

  /**
   * When the robot's sensors are over a junction, this will move the robot so that its centre point is over the junction. This assumes that the DifferentialPilot uses mm!
   *
   * @return False if the robot is not currently at a junction. Returns true once movement is complete.
   *
   */
  public boolean moveOverJunction() {
    if (!atJunction()) {
      return false;
    }

    m_pilot.travel(m_distFromSensorsToWheels);
    return true;

  }

  /**
   * Turn the robot through an arc until a line is detected. Only starts checking for a line after _maxTurn/2 degress have been rotated.
   * @param _sensor The sensor to check for a line with.
   * @param _maxTurn The maximum turn to try.
   * @return  True if the line was detected before the turn was completed.
   */
  public boolean turnUntilLine(LightSensor _sensor, int _maxTurn) {
    //this is half the turn
    int halfTurn = _maxTurn / 2;

    //do an initial rotation to get off the current line
    m_pilot.rotate(halfTurn);

    // rotate until line
    m_pilot.rotate(halfTurn, true);

    //while we're still turning
    while (m_pilot.isMoving()) {
      if (isOverLine(_sensor)) {
        return true;
      }
    }

    //if we got this far then we didn't see a line
    return false;

  }
}
