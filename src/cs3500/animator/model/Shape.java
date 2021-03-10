package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Shape, its x and y position, dimension sizes, color, shape type and name.
 */
public abstract class Shape {

  private int x;
  private int y;
  private Dimension dimension;
  private Color color;
  private String shape;
  private String name;
  private ArrayList<String> textArr = new ArrayList<String>();

  /**
   * Creates our Shape.
   *
   * @param x         the x position of our Shape
   * @param y         the y position of our Shape
   * @param dimension the initial dimensions of our Shape
   * @param color     the initial Color of our Shape
   * @throws IllegalArgumentException if either x or y is non-positive
   */
  public Shape(int x, int y, Dimension dimension, Color color, String shape, String name) {
    this.x = x;
    this.y = y;
    this.dimension = dimension;
    this.color = color;
    this.shape = shape;
    this.name = name;
  }

  /**
   * changeColor changes the color of this shape.
   *
   * @param col       is the color we change this.color to
   * @param tickStart is the where the color change starts
   * @param tickEnd   is the where the color change ends
   */

  public void changeColor(Color col, int tickStart, int tickEnd) {

    int tick = tickEnd - tickStart;

    double changeRed = (double) Math.abs(this.color.getRed() - col.getRed()) / tick;
    double changeBlue = (double) Math.abs(this.color.getBlue() - col.getBlue()) / tick;
    double changeGreen = (double) Math.abs(this.color.getGreen() - col.getGreen()) / tick;

    double finalR = this.color.getRed();
    double finalG = this.color.getGreen();
    double finalB = this.color.getBlue();
    int t = 0;
    while (t < tick) {
      finalR = mutateField(this.color.getRed(), col.getRed(), changeRed, finalR);
      finalG = mutateField(this.color.getGreen(), col.getGreen(), changeGreen, finalG);
      finalB = mutateField(this.color.getBlue(), col.getBlue(), changeBlue, finalB);
      t++;
    }
    this.color = new Color((int) Math.round(finalR), (int) Math.round(finalG),
        (int) Math.round(finalB));
  }


  /**
   * moveShape changes the x and y coordinates of this shape moving it.
   *
   * @param xPos      is the x position we change this.x to
   * @param yPos      is the y position we change this.y to
   * @param tickStart is the where the color change starts
   * @param tickEnd   is the where the color change ends
   */
  public void moveShape(int xPos, int yPos, int tickStart, int tickEnd) {
    int tick = tickEnd - tickStart;

    double changeX = Math.abs((double) this.x - (double) xPos) / tick;
    double changeY = Math.abs((double) this.y - (double) yPos) / tick;
    double finalX = this.x;
    double finalY = this.y;
    int t = 0;
    while (t < tick) {
      finalX = mutateField(this.x, xPos, changeX, finalX);
      finalY = mutateField(this.y, yPos, changeY, finalY);
      t++;
    }
    this.x = (int) Math.round(finalX);
    this.y = (int) Math.round(finalY);
  }


  /**
   * resize changes dimension of this.dimension changing the size of this shape.
   *
   * @param dimension is the dimension we change this.dimension to
   * @param tickStart is the where the color change starts
   * @param tickEnd   is the where the color change ends
   */
  public void resize(Dimension dimension, int tickStart, int tickEnd) {
    int tick = tickEnd - tickStart;
    double changeWidth = Math.abs(this.dimension.getWidth() - dimension.width) / tick;
    double changeHeight = Math.abs(this.dimension.getHeight() - dimension.height) / tick;
    double finalWidth = this.dimension.width;
    double finalHeight = this.dimension.height;
    int t = 0;
    while (t < tick) {
      finalWidth = mutateField(this.dimension.width, dimension.width, changeWidth, finalWidth);
      finalHeight = mutateField(this.dimension.height, dimension.height, changeHeight, finalHeight);
      t++;
    }
    this.dimension = new Dimension((int) Math.round(finalWidth), (int) Math.round(finalHeight));
  }

  /**
   * MutateField adds, subtracts or leaves the field by the given number.
   *
   * @param initNum  is the initial number of the field we want to change
   * @param finalNum is the number we want to change the field to
   * @param num      is the number we change initNum by
   * @param curNum   is the current number
   * @return curNum minus num if we want to decrease, curNum + num if we want to increase or curNum
   *                if the number is correct.
   */
  private double mutateField(double initNum, double finalNum, double num, double curNum) {
    if (initNum > finalNum) {
      return (curNum - num);
    } else if (initNum < finalNum) {
      return (curNum + num);
    } else {
      return curNum;
    }
  }

  /**
   * ProduceText gets all of the information about the shape.
   *
   * @param tick is the current tick of the animation
   * @return a string with all of the information about this shape
   */

  public String produceText(int tick) {
    return tick + " " + this.x + " " + this.y + " " + this.dimension.width + " "
        + this.dimension.height + " " + this.color.getRed() + " " + this.color.getGreen() +
        " " + this.color.getBlue();
  }

  /**
   * getTextArr is a getter for textArr.
   *
   * @return this shape's text array
   */
  public ArrayList<String> getTextArr() {
    return this.textArr;
  }

  /**
   * getName is a getter for name.
   *
   * @return this shape's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * getShape is a getter for shape.
   *
   * @return the shape type
   */
  public String getShape() {
    return this.shape;
  }


  /**
   * getter for x.
   *
   * @return the x position of this shape.
   */
  public int getX() {
    return this.x;
  }

  /**
   * getter for y.
   *
   * @return the y position of this shape.
   */
  public int getY() {
    return this.y;
  }

  /**
   * getter for color.
   *
   * @return the color of this shape.
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * getter for dimension.
   *
   * @return the dimension of this shape.
   */
  public Dimension getDimension() {
    return this.dimension;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, dimension, color, shape, name, textArr);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Shape)) {
      return false;
    }
    Shape other = (Shape) obj;
    return other.x == this.x && other.y == this.y && other.color == this.color
        && other.dimension == this.dimension && other.shape == this.shape && other.name == this.name
        && other.textArr == this.textArr;
  }
}
