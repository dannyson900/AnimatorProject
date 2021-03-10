package model;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.Move;
import cs3500.animator.model.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import cs3500.animator.model.Ellipse;
import cs3500.animator.model.Rectangle;
import org.junit.Test;

/**
 * Represents our tester class Tests our AnimationModelImpl class and our Shape class.
 */
public class AnimationModelImplTest {

  Dimension dimension1 = new Dimension(100, 200);
  Dimension dimension2 = new Dimension(25, 25);
  Color red = new Color(255, 0, 0);
  Color blue = new Color(0, 0, 255);

  /**
   * Tests produceText method from our Shape class.
   */
  @Test
  public void testProduceText() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    Shape ellipse = new Ellipse(12, 11, dimension2, blue, "E");
    assertEquals("1 50 50 100 200 255 0 0", rectangle.produceText(1));
    assertEquals("1 12 11 25 25 0 0 255", ellipse.produceText(1));
  }

  /**
   * Tests changeColor method from our Shape class.
   */
  @Test
  public void testChangeColor() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    assertEquals("1 50 50 100 200 255 0 0", rectangle.produceText(1));
    rectangle.changeColor(blue, 1, 50);
    assertEquals("50 50 50 100 200 0 0 255", rectangle.produceText(50));
  }

  /**
   * Tests resize method from our Shape class.
   */
  @Test
  public void testChangeSize() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    assertEquals("1 50 50 100 200 255 0 0", rectangle.produceText(1));
    rectangle.resize(dimension2, 1, 50);
    assertEquals("50 50 50 25 25 255 0 0", rectangle.produceText(50));
  }

  /**
   * Tests moveShape method from our Shape class.
   */
  @Test
  public void testChangePosition() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    assertEquals("1 50 50 100 200 255 0 0", rectangle.produceText(1));
    rectangle.moveShape(10, 2, 1, 50);
    assertEquals("50 10 2 100 200 255 0 0", rectangle.produceText(50));
  }


  @Test
  public void testAnimateColor() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    Move move1 = new Move(model, new Color(0, 0, 0), 0, 1, 20, true);
    model.addMove(move1);
    model.animate();
    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0    20 50 50 100 200 0 0 0\n", model.getAnimationText());
  }

  @Test
  public void testAnimateSize() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    Move move1 = new Move(model, dimension2, 0, 1, 20, true);
    model.addMove(move1);
    model.animate();
    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0    20 50 50 25 25 255 0 0\n", model.getAnimationText());
  }

  @Test
  public void testAnimatePosition() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    Move move1 = new Move(model, 10, 145, 0, 1, 20, true);
    model.addMove(move1);
    model.animate();
    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0   "
        + " 20 10 145 100 200 255 0 0\n", model.getAnimationText());
  }

  /**
   * testing our models output of text when theres no animation added.
   */
  @Test
  public void testAnimatedText() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    assertEquals("shape R rectangle\n", model.getAnimationText());
  }

  @Test
  public void testAnimatedText2() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    Shape ellipse = new Ellipse(12, 11, dimension2, blue, "E");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle,
        ellipse)));
    assertEquals("shape R rectangle\n"
        + "shape E ellipse\n", model.getAnimationText());
  }

  /**
   * testing our models output of text when there is animation added.
   */
  @Test
  public void testAnimatedText3() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    Shape ellipse = new Ellipse(12, 11, dimension2, blue, "E");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle,
        ellipse)));
    Move move1 = new Move(model, dimension2, 0, 1, 20, true);
    model.addMove(move1);
    model.animate();
    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0    20 50 50 25 25 255 0 0\n"
        + "shape E ellipse\n", model.getAnimationText());
  }

  @Test
  public void testAnimatedText4() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    Shape ellipse = new Ellipse(12, 11, dimension2, blue, "E");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle,
        ellipse)));
    Move move1 = new Move(model, dimension2, 0, 1, 20, true);
    Move move2 = new Move(model, new Color(123, 20, 89), 1, 1, 20, true);
    model.addMoves(new ArrayList<Move>(Arrays.asList(move1, move2)));
    model.animate();
    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0    20 50 50 25 25 255 0 0\n"
        + "shape E ellipse\n"
        + "motionE 1 12 11 25 25 0 0 255    20 12 11 25 25 123 20 89\n", model.getAnimationText());
  }

  /**
   * Testing models that go through multiple animations but at different ticks.
   */
  @Test
  public void testMultipleAnimation1() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    Shape ellipse = new Ellipse(12, 11, dimension2, blue, "E");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle,
        ellipse)));
    Move move1 = new Move(model, dimension2, 0, 1, 20, true);
    Move move2 = new Move(model, new Color(0, 255, 173), 0, 30, 50, true);
    model.addMoves(new ArrayList<Move>(Arrays.asList(move1, move2)));
    model.animate();

    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0    20 50 50 25 25 255 0 0\n"
        + "motionR 30 50 50 25 25 255 0 0    50 50 50 25 25 0 255 173\n"
        + "shape E ellipse\n", model.getAnimationText());
  }

  @Test
  public void testMultipleAnimation2() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    Shape ellipse = new Ellipse(12, 11, dimension2, blue, "E");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle,
        ellipse)));
    Move move1 = new Move(model, dimension2, 0, 1, 20, true);
    Move move2 = new Move(model, new Color(0, 255, 173), 0, 30, 50, true);
    Move move3 = new Move(model, 100, 240, 0, 51, 90, true);
    model.addMoves(new ArrayList<Move>(Arrays.asList(move1, move2, move3)));
    model.animate();

    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0    20 50 50 25 25 255 0 0\n"
        + "motionR 30 50 50 25 25 255 0 0    50 50 50 25 25 0 255 173\n"
        + "motionR 51 50 50 25 25 0 255 173    90 100 240 25 25 0 255 173\n"
        + "shape E ellipse\n", model.getAnimationText());
  }

  /**
   * Testing models that go through multiple animations but at the same tick.
   */
  @Test
  public void testMultipleAnimation3() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    Shape ellipse = new Ellipse(12, 11, dimension2, blue, "E");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle,
        ellipse)));

    Move move1 = new Move(model, dimension2, 0, 1, 20, true);
    Move move2 = new Move(model, new Color(0, 255, 173), 0, 1, 20, true);
    Move move3 = new Move(model, 100, 240, 0, 1, 20, true);
    model.addMoves(new ArrayList<Move>(Arrays.asList(move1, move2, move3)));
    model.animate();

    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0    20 100 240 25 25 0 255 173\n"
        + "shape E ellipse\n", model.getAnimationText());
  }


  /**
   * tests animations that have both animations on the same tick and animation on different ticks.
   */
  @Test
  public void testMixedAnimations() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    Shape ellipse = new Ellipse(12, 11, dimension2, blue, "E");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle,
        ellipse)));

    Move move1 = new Move(model, 100, 100, 0, 1, 50, true);
    Move move2 = new Move(model, new Color(0, 255, 173), 0, 1, 50, true);
    Move move3 = new Move(model, new Dimension(50, 50), 0, 1, 50, true);
    Move move4 = new Move(model, new Color(255, 255, 255), 0, 51, 100, true);
    Move move5 = new Move(model, new Color(0, 0, 0), 1, 20, 40, true);
    Move move6 = new Move(model, new Dimension(25, 60), 1, 20, 40, true);
    Move move7 = new Move(model, new Dimension(450, 120), 1, 50, 100, true);
    model.addMoves(
        new ArrayList<Move>(Arrays.asList(move1, move2, move3, move4, move5, move6, move7)));
    model.animate();

    assertEquals("shape R rectangle\n"
        + "motionR 1 50 50 100 200 255 0 0    50 100 100 50 50 0 255 173\n"
        + "motionR 51 100 100 50 50 0 255 173    100 100 100 50 50 255 255 255\n"
        + "shape E ellipse\n"
        + "motionE 20 12 11 25 25 0 0 255    40 12 11 25 60 0 0 0\n"
        + "motionE 50 12 11 25 60 0 0 0    100 12 11 450 120 0 0 0\n", model.getAnimationText());
  }


  @Test
  public void testGetNameRect() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    assertEquals("R", rectangle.getName());
  }

  @Test
  public void testGetNameElli() {
    Shape ellipse = new Ellipse(50, 50, dimension1, red, "C");
    assertEquals("C", ellipse.getName());
  }

  @Test
  public void testGetNameNull() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, null);
    assertEquals(null, rectangle.getName());
  }

  @Test
  public void testGetShapeRect() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    assertEquals("rectangle", rectangle.getShape());
  }

  @Test
  public void testGetShapeEllipse() {
    Shape ellipse = new Ellipse(50, 50, dimension1, red, "C");
    assertEquals("ellipse", ellipse.getShape());
  }

  @Test
  public void testGetTextArr() {
    Shape ellipse = new Ellipse(50, 50, dimension1, red, "C");
    assertEquals(new ArrayList<String>(), ellipse.getTextArr());
  }

  /**
   * testing invalid inputs for our animation methods in our model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedAnimationSize() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    model.animateSize(new Dimension(-1, 10), 0, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailedAnimationSize2() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    model.animateSize(new Dimension(10, 10), 0, -4, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailedAnimationColor() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    model.animateColor(new Color(256, 0, 0), 0, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailedAnimationColor2() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    model.animateColor(new Color(255, 255, 255), 0, -4, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailedAnimationPosition() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    model.animatePosition(-1, 100, 1, 10, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailedAnimationPosition2() {
    Shape rectangle = new Rectangle(50, 50, dimension1, red, "R");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle)));
    model.animatePosition(1, 100, 0, -4, 100);
  }
}
