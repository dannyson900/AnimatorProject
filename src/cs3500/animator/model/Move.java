package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;

/**
 * The Move class represents a single move or animation for a shape.
 */
public class Move {

  private boolean changeColor = false;
  private boolean changeSize = false;
  private boolean changePosition = false;
  private boolean doNothing = false;
  private boolean isKeyFrame = false;
  private AnimationModel model;
  private Color col;
  private Dimension dimension;
  private int ind;
  private int tickStart;
  private int tickEnd;
  private int xPos;
  private int yPos;


  /**
   * This constructor represents a change in color.
   *
   * @param model     is the Animation model where the shape is in.
   * @param col       is the Color that the shape will turn to.
   * @param ind       is the index of the shape that is going to be animated and is in shapes in the
   *                  model.
   * @param tickStart is the starting tick of the animation.
   * @param tickEnd   is the ending tick of the animation.
   * @param isKeyFrame determines if this move defined by a keyframe
   */
  public Move(AnimationModel model, Color col, int ind, int tickStart, int tickEnd,
      boolean isKeyFrame) {
    this.model = model;
    this.col = col;
    this.ind = ind;
    this.tickStart = tickStart;
    this.tickEnd = tickEnd;
    this.changeColor = true;
    this.isKeyFrame = isKeyFrame;
  }

  /**
   * This constructor represents a change in size.
   *
   * @param model     is the Animation model where the shape is in.
   * @param dimension is the dimension that the shape will turn into.
   * @param ind       is the index of the shape that is going to be animated and is in shapes in the
   *                  model.
   * @param tickStart is the starting tick of the animation.
   * @param tickEnd   is the ending tick of the animation.
   * @param isKeyFrame determines if this move defined by a keyframe
   */
  public Move(AnimationModel model, Dimension dimension, int ind, int tickStart, int tickEnd,
      boolean isKeyFrame) {
    this.model = model;
    this.dimension = dimension;
    this.ind = ind;
    this.tickStart = tickStart;
    this.tickEnd = tickEnd;
    this.changeSize = true;
    this.isKeyFrame = isKeyFrame;
  }

  /**
   * This constructor represents a change in position.
   *
   * @param model     is the Animation model where the shape is in.
   * @param xPos      is the Color that the x position the shape will go to.
   * @param yPos      is the Color that the y position the shape will go to.
   * @param ind       is the index of the shape that is going to be animated and is in shapes in the
   *                  model.
   * @param tickStart is the starting tick of the animation.
   * @param tickEnd   is the ending tick of the animation.
   * @param isKeyFrame determines if this move defined by a keyframe
   */
  public Move(AnimationModel model, int xPos, int yPos, int ind, int tickStart, int tickEnd,
      boolean isKeyFrame) {
    this.model = model;
    this.xPos = xPos;
    this.yPos = yPos;
    this.ind = ind;
    this.tickStart = tickStart;
    this.tickEnd = tickEnd;
    this.changePosition = true;
    this.isKeyFrame = isKeyFrame;
  }

  /**
   * This constructor represents an object doing nothing.
   *
   * @param model     is the Animation model where the shape is in.
   * @param ind       is the index of the shape that is going to be animated and is in shapes in the
   *                  model.
   * @param tickStart is the starting tick of the animation.
   * @param tickEnd   is the ending tick of the animation.
   */
  public Move(AnimationModel model, int ind, int tickStart, int tickEnd) {
    this.model = model;
    this.ind = ind;
    this.tickStart = tickStart;
    this.tickEnd = tickEnd;
    this.doNothing = true;
    this.isKeyFrame = false;
  }

  /**
   * This constructor represents a change in position in the builder class.
   *
   * @param x2      is the x position of where the shape will go.
   * @param y2      is the y position of where the shape will go.
   * @param indexOf is the index of the shape in shapes.
   * @param t1      is the starting time
   * @param t2      is the ending time
   */
  public Move(int x2, int y2, int indexOf, int t1, int t2) {
    this.xPos = x2;
    this.yPos = y2;
    this.ind = indexOf;
    this.tickStart = t1;
    this.tickEnd = t2;
    this.changePosition = true;
    this.isKeyFrame = false;
  }

  /**
   * This constructor represents a change in color in the builder class.
   *
   * @param color   is the color of what the shape will turn to.
   * @param indexOf is the index of the shape in shapes.
   * @param t1      is the starting time.
   * @param t2      is the ending time.
   */
  public Move(Color color, int indexOf, int t1, int t2) {
    this.col = color;
    this.ind = indexOf;
    this.tickStart = t1;
    this.tickEnd = t2;
    this.changeColor = true;
    this.isKeyFrame = false;
  }

  /**
   * This constructor represents a change in size in the builder class.
   *
   * @param dimension is the dimensions of what the shape will turn to.
   * @param indexOf   is the index of the shape in shapes.
   * @param t1        is the starting time.
   * @param t2        is the ending time.
   */
  public Move(Dimension dimension, int indexOf, int t1, int t2) {
    this.dimension = dimension;
    this.ind = indexOf;
    this.tickStart = t1;
    this.tickEnd = t2;
    this.changeSize = true;
    this.isKeyFrame = false;
  }


  /**
   * Animate runs the animate function in AnimationModel base upon which type of move this is.
   */
  public void animate() {
    if (changeColor) {
      model.animateColor(col, ind, tickStart, tickEnd);
    } else if (changeSize) {
      model.animateSize(dimension, ind, tickStart, tickEnd);
    } else if (changePosition) {
      model.animatePosition(xPos, yPos, ind, tickStart, tickEnd);
    }
  }

  /**
   * Getter for col.
   *
   * @return the color at the end of the animation.
   */
  public Color getCol() {
    return this.col;
  }

  /**
   * Getter for dimension.
   *
   * @return the dimension at the end of the animation.
   */
  public Dimension getDimension() {
    return this.dimension;
  }

  /**
   * Getter for ind.
   *
   * @return the index of the shape.
   */
  public int getInd() {
    return this.ind;
  }

  /**
   * Setter for ind.
   * @param ind is the num to set ind to.
   */
  public void setInd(int ind) {
    this.ind = ind;
  }


  /**
   * Getter for tickStart.
   *
   * @return the starting tick of the animation.
   */
  public int getTickStart() {
    return this.tickStart;
  }

  /**
   * Getter for tickEnd.
   *
   * @return the starting tick of the animation.
   */
  public int getTickEnd() {
    return this.tickEnd;
  }

  /**
   * Getter for xPos.
   *
   * @return the x position of the shape at the end of the animation.
   */
  public int getXPos() {
    return this.xPos;
  }

  /**
   * Getter for yPos.
   *
   * @return the y position of the shape at the end of the animation.
   */
  public int getYPos() {
    return this.yPos;
  }

  /**
   * Getter for changeColor.
   *
   * @return if this move is for color change or not.
   */
  public boolean getChangeColor() {
    return this.changeColor;
  }

  /**
   * Getter for changeSize.
   *
   * @return if this move is for size change or not.
   */
  public boolean getChangeSize() {
    return this.changeSize;
  }

  /**
   * Getter for changePosition.
   *
   * @return if this move is for position change or not.
   */
  public boolean getChangePosition() {
    return this.changePosition;
  }


  /**
   * setter for TickStart.
   *
   * @param t the tick you want to set tickStart to
   */
  public void setTickStart(int t) {
    this.tickStart = t;
  }


  /**
   * getter for isKeyFrame.
   *
   * @return a boolean that determines if this Move is a keyframe.
   */
  public boolean getIsKeyFrame() {
    return this.isKeyFrame;
  }

  /**
   * Getter for doNothing.
   *
   * @return if this move does nothing.
   */
  public boolean getDoNothing() {
    return this.doNothing;
  }

  /**
   * sets the xPosition of the shape's move.
   * @param x the x coordinate we are changing
   */
  public void setXPos(int x) {
    this.xPos = x;
  }

  /**
   * setter for the yPosition of the shape's move.
   * @param y the y coordinate we are changing
   */
  public void setYPos(int y) {
    this.yPos = y;
  }

  /**
   * setter for the color of the shape's move.
   * @param c the color we are changing.
   */
  public void setCol(Color c) {
    this.col = c;
  }

  /**
   * setter for the dimensions of the shape's move.
   * @param d the dimension we are changing.
   */
  public void setDimension(Dimension d) {
    this.dimension = d;
  }
}
