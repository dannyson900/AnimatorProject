package cs3500.animator.model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Represents a Model for Animation. Each implementation will be able to perform operations based on
 * the model One object of this model will represent a single animation Model: manages our shapes,
 * enforces constraints.
 */
public interface AnimationModel {


  /**
   * Return a String that represents a log of the animations performed within our animation method
   * on each shape. Each Text on each shape will start out with the name of the shape the animation
   * was done upon Within that log, each step of animation performed will have its own line of text
   * each with a "Start" and "End" category. Start: logs the current tick and fields of the shape
   * when the animation started End: logs the tick when the animation ends, and the changes in which
   * the Shape was mutated (if a field of a shape was not mutated, we still log it, simply input the
   * same value),
   *
   * @return a String of text that tells us what changes were made when we animated our shapes
   */
  String getAnimationText();

  /**
   * Changes the color of a shape a change in color is only valid when given correct values of the
   * color (rgb, each between 0-255) and when the number of ticks is also a positive integer.
   *
   * @param col       The color we want the shape to be (rgb format, int between 0-255)
   * @param ind       Is the index of the shape we want to run animateColor on in the shapes array
   * @param tickStart Is the starting tick of this operation
   * @param tickEnd   Is the ending tick of this operation
   * @throws IllegalArgumentException if either the inputs of color is negative or above the max
   *                                  value of rgb, or if the number of ticks is negative.
   */
  void animateColor(Color col, int ind, int tickStart, int tickEnd);


  /**
   * Changes the position of a Shape a change in position is only valid when given correct values of
   * the Position, and when the number of ticks is a positive integer.
   *
   * @param xPos      The X Position we want our shape to be at (Positive ints for x and y
   *                  coordinate only)
   * @param yPos      The Y Position we want our shape to be at (Positive ints for x and y
   *                  coordinate only)
   * @param ind       Is the index of the shape we want to run animatePosition on in the shapes
   *                  array
   * @param tickStart Is the starting tick of this operation
   * @param tickEnd   Is the ending tick of this operation
   * @throws IllegalArgumentException if the given position is negative, or if the number of ticks
   *                                  is negative.
   */
  void animatePosition(int xPos, int yPos, int ind, int tickStart, int tickEnd);

  /**
   * Changes the dimensions of a Shape a change in dimension is only valid when given correct values
   * of the Dimension, and when the number of ticks is positive integer.
   *
   * @param dimension the Dimension we want our shape to be
   * @param ind       Is the index of the shape we want to run animateSize on in the shapes array
   * @param tickStart Is the starting tick of this operation
   * @param tickEnd   Is the ending tick of this operation
   * @throws IllegalArgumentException if dimension are negatives, or if the number of ticks is
   *                                  negative
   */
  void animateSize(Dimension dimension, int ind, int tickStart, int tickEnd);

  /**
   * Animate runs a series of moves on the list of shapes animating them.
   */
  void animate();

  /**
   * adds shapes to the animation.
   *
   * @param s is the shape that is to be added to the animation.
   */
  void addShape(Shape s);

  /**
   * removes shapes from the animation.
   *
   * @param name is the name of the shape that need to be removed.
   */
  void removeShape(String name);

  /**
   * gets the shapes from the animation.
   *
   * @return the list of shapes.
   */
  ArrayList<Shape> getShapes();

  /**
   * gets the moves from the animation.
   *
   * @return the list of moves.
   */
  ArrayList<Move> getMoves();

  /**
   * adds a move to the list of moves.
   *
   * @param move is the Move that is going to be added.
   */
  void addMove(Move move);

  /**
   * Adds a list of moves to the list.
   *
   * @param moves is the list of moves to be added.
   */
  void addMoves(ArrayList<Move> moves);


  /**
   * does nothing from tickStart to tickEnd and adds this info to the textArray.
   *
   * @param ind       is the index of the shape
   * @param tickStart is the starting tick of this.
   * @param tickEnd   is the ending tick of this.
   */
  void doNothing(int ind, int tickStart, int tickEnd);

  /**
   * This is a getter for the canvas from builder.
   *
   * @return this.Canvas.
   */
  Canvas getBounds();


  /**
   * Adds a move into our arraylist of moves, defined as a move created by a keyframe.
   *
   * @param shapeID a name of the shape
   * @param tick the tick we want to add the "keyframe" to
   * @param m the move we want to implement at the given tick
   */
  void addKeyFrame(String shapeID, int tick, Move m);


  /**
   * removes a move into our arrayList of moves, only removes if the move is defined as a keyframe.
   *
   * @param shapeID the name of the shape
   * @param tick the tick we want to remove "keyframe"
   */
  void removeKeyFrame(String shapeID, int tick);


  /**
   * modifies the keyframe with a given move.
   * @param shapeID The name of the shape we are modifying the move onto
   * @param move The type of move we want to change
   * @param tick the tick in which our keyframe was placed
   */
  void modifyKeyFrame(String shapeID, int tick, Move move);
}
