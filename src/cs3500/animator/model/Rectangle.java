package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Represents a Rectangle defined as a Shape.
 */
public class Rectangle extends Shape {

  /**
   * Constructs our Shape.
   *
   * @param x         the x coordinate of our shape
   * @param y         the y coordinate of our shape
   * @param dimension the initial dimensions of our Shape
   * @param color     the initial Color of our Shape
   */
  public Rectangle(int x, int y, Dimension dimension, Color color, String name) {
    super(x, y, dimension, color, "rectangle", name);
  }
}

