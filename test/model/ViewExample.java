package model;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.Ellipse;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * view example.
 */
public class ViewExample {

  /**
   * view example.
   * @param args string commands.
   */
  public static void main(String[] args) {
    Dimension dimension1 = new Dimension(100, 200);
    Dimension dimension2 = new Dimension(25, 25);
    Dimension dimension3 = new Dimension(50, 50);
    Color red = new Color(255, 0, 0);
    Color blue = new Color(0, 0, 255);
    Shape rectangle = new Rectangle(100, 100, dimension1, red, "R");
    Shape ellipse = new Ellipse(200, 200, dimension2, blue, "E");
    AnimationModel model = new AnimationModelImpl(new ArrayList<Shape>(Arrays.asList(rectangle,
        ellipse)));
    
  }
}

