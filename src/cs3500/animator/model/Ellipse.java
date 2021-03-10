package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Represents a Ellipse, defined as a Shape.
 */
public class Ellipse extends Shape {

  /**
   * Constructs our Shape.
   *
   * @param x         the x coordinate of our shape
   * @param y         the y coordinate of our shape
   * @param dimension the initial dimensions of our Shape
   * @param color     the initial Color of our Shape
   */
  public Ellipse(int x, int y, Dimension dimension, Color color, String name) {
    super(x, y, dimension, color, "ellipse", name);
  }
}
