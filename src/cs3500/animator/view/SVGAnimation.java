package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Move;
import cs3500.animator.model.Shape;
import java.awt.Canvas;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * SVGAnimation a view that creates a SVG style formatted version of the given animation. It is a
 * purely text based representation of the animation in SVG format.
 */
public class SVGAnimation extends JFrame implements View {

  private JPanel background;
  String output;

  /**
   * this is the constructor for SVGAnimation and prints out what happened in the animation.
   *
   * @param model  is the model of the animation.
   * @param output is what file this outputs to, if it is empty system.out.
   * @throws IOException if the file is not accessible.
   */
  public SVGAnimation(AnimationModel model, String output) throws IOException {
    super();
    this.output = output;
    System.out.println(output);
    Canvas canvas = model.getBounds();
    if (output.equals("")) {
      System.out.println(generateSVG(model));
      System.out.flush();
      System.out.close();
    } else {
      FileWriter writer = new FileWriter(output);
      writer.write(generateSVG(model) + "\n");
    }
  }

  /**
   * This method generates a SVG style String of the animation.
   *
   * @param model is the model of the animation.
   * @return a SVG style string of the animation with canvas information.
   */
  private String generateSVG(AnimationModel model) {
    return "<svg width=\"" + model.getBounds().getWidth() + "\" height=\"" + model.getBounds()
        .getHeight() + "\" version=\"1.1\"\n" + "xmlns=\"http://www.w3.org/2000/svg\">\n" +
        getAnimationText(model) + "\n" +
        "</svg>";
  }

  /**
   * This method generates a SVG style String of all the moves in the model.
   *
   * @param model is the model of the animation.
   * @return a SVG style string of the animation.
   */
  private String getAnimationText(AnimationModel model) {
    String out = "";
    String name;
    for (int i = 0; i < model.getShapes().size(); i++) {
      Shape s = model.getShapes().get(i);
      if (s.getShape().equals("rectangle")) {
        name = "rect";
      } else {
        name = "ellipse";
      }
      out += "<" + name + " id=\"" + s.getName() + "\" x=\"" + s.getX() + "\" y=\"" + s.getY()
          + "\" width=\"" + s.getDimension().getWidth() + "\" height=\"" + s.getDimension()
          .getHeight() +
          "\" fill=\"rgb(" + s.getColor().getRed() + "," + s.getColor().getGreen()
          + "," + s.getColor().getBlue() + ")" + "\" visibility=\"visible\"" + ">\n";

      for (int j = 0; j < model.getMoves().size(); j++) {
        Move m = model.getMoves().get(j);
        if (m.getInd() == i) {
          String tStart = m.getTickStart() * 1000 + "ms";
          String tEnd = m.getTickEnd() * 1000 + "ms";
          if (m.getChangeColor()) {
            out += "<animate attributeType=\"xml\" begin=\"" + tStart + "\" dur=\"" + tEnd +
                "\" attributeName=\"fill" + "\" from=\"rgb(" + s.getColor().getRed() + ","
                + s.getColor().getGreen() + "," + s.getColor().getBlue() + ")" + "\" to=\"rgb(" +
                m.getCol().getRed() + "," + m.getCol().getGreen() + "," + m.getCol().getBlue() + ")"
                + "\" fill=\"remove\" />\n";
          } else if (m.getChangePosition()) {
            out += "<animate attributeType=\"xml\" begin=\"" + tStart + "\" dur=\"" + tEnd +
                "\" attributeName=\"x" + "\" from=\"" + s.getX() + "\" to=\"" + m.getXPos()
                + "\" fill=\"remove\" /> \n"
                + "<animate attributeType=\"xml\" begin=\"" + tStart + "\" dur=\"" + tEnd +
                "\" attributeName=\"Y" + "\" from=\"" + s.getY() + "\" to=\"" + m.getYPos()
                + "\" fill=\"remove\" />\n";
          } else {
            out += "<animate attributeType=\"xml\" begin=\"" + tStart + "\" dur=\"" + tEnd +
                "\" attributeName=\"width" + "\" from=\"" + s.getDimension().getWidth() +
                "\" to=\"" + m.getDimension().getWidth() + "\" fill=\"remove\" /> \n"
                + "<animate attributeType=\"xml\" begin=\"" + tStart + "\" dur=\"" + tEnd +
                "\" attributeName=\"height" + "\" from=\"" + s.getDimension().getHeight() + "\""
                + " to=\"" + m.getDimension().getHeight() + "\" fill=\"remove\" /> \n";
          }
        }
      }
      out += "</" + name + ">" + "\n";
    }
    return out;
  }



  @Override
  public void makeVisible() {
    this.setVisible(true);
  }
}
