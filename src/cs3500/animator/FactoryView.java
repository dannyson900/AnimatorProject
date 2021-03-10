package cs3500.animator;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.SVGAnimation;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.View;
import cs3500.animator.view.VisualView;
import java.io.IOException;

/**
 * This class takes contructs a view based on input.
 */
public class FactoryView {

  AnimationModel model;
  int ticksPerSecond;
  String output;

  /**
   * this is the constructor for the class.
   *
   * @param model          is the model for the view.
   * @param ticksPerSecond is the numeber of ticks per sec.
   * @param output         is the file where the view will output to.
   */
  public FactoryView(AnimationModel model, int ticksPerSecond, String output) {
    this.model = model;
    this.ticksPerSecond = ticksPerSecond;
    this.output = output;
  }

  /**
   * getView creates a view based on the string.
   *
   * @param name is the type of view to be created.
   * @return a new view based on what the input it
   * @throws IOException if the file is not accessible.
   */
  public View getView(String name) throws IOException {
    if (name.equals("text")) {
      return new TextualView(model, output);
    } else if (name.equals("svg")) {
      return new SVGAnimation(model, output);
    } else if (name.equals("visual")) {
      return new VisualView(model, ticksPerSecond);
    } else if (name.equals("edit")) {
      return new EditorView(model, ticksPerSecond);
    } else {
      throw new IllegalArgumentException("Need to input correct string for view.");
    }
  }
}
