package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Move;
import cs3500.animator.model.Shape;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class is the textual view of an animation.  It creates a text based representation of the
 * given animation and either prints it to System.out or to a given file.
 */
public class TextualView extends JFrame implements View {

  private JPanel background;
  /**
   * this is the constructor for TextualView and prints out what happened in the animation.
   *
   * @param model  is the model of the animation.
   * @param output is what file this outputs to, if it is empty system.out.
   * @throws IOException if the file is not accessible.
   */

  public TextualView(AnimationModel model, String output) throws IOException {

    super();

    AnimationModel mode = model;
    mode.getMoves();
    this.setTitle("Animation Text View");

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //use a borderlayout with drawing panel in center and button panel in south
    this.setLayout(new BorderLayout());
    JPanel background = new JPanel();
    Canvas canvas = model.getBounds();
    background.setAlignmentX(canvas.getAlignmentX());
    background.setAlignmentY(canvas.getAlignmentY());
    background.setPreferredSize(canvas.getSize());
    JScrollPane scrollPane = new JScrollPane(background);
    this.add(scrollPane, BorderLayout.CENTER);
    this.pack();
    if (output.equals("")) {
      System.out.println(getCanvasInfo(canvas) + "\n" + getAnimationText(model));
    }
    else {
      FileWriter writer = new FileWriter(output);
      writer.write(getCanvasInfo(canvas) + "\n" + getAnimationText(model));
    }
  }

  /**
   * This method generates a String of all the moves in the model.
   *
   * @param model is the model of the animation.
   * @return a string based representation of the animation.
   */
  public String getAnimationText(AnimationModel model) {
    String out = "";
    for (int i = 0; i < model.getShapes().size(); i++) {
      Shape s = model.getShapes().get(i);
      out += "shape " + s.getName() + " " + s.getShape() + "\n";
      for (int j = 0; j < model.getMoves().size(); j++) {
        Move m = model.getMoves().get(j);
        int tStart = m.getTickStart();
        int tEnd = m.getTickEnd();
        if (m.getInd() == i) {
          if (m.getChangeColor()) {
            out += "motion" + model.getShapes().get(i).getName() + " " + tStart +
                " " + s.getX() + " " + s.getY() + " " + s.getDimension().getWidth() + " "
                + s.getDimension().getHeight() + " " + s.getColor().getRed() + " "
                + s.getColor().getGreen() + " " + s.getColor().getBlue() + " " + tEnd +
                " " + s.getX() + " " + s.getY() + " " + s.getDimension().getWidth() + " "
                + s.getDimension().getHeight() + " " + m.getCol().getRed() + " "
                + m.getCol().getGreen() + " " + m.getCol().getBlue() + "\n";
          } else if (m.getChangePosition()) {
            out += "motion" + model.getShapes().get(i).getName() + " " + tStart +
                " " + s.getX() + " " + s.getY() + " " + s.getDimension().getWidth() + " "
                + s.getDimension().getHeight() + " " + s.getColor().getRed() + " "
                + s.getColor().getGreen() + " " + s.getColor().getBlue() + " " + tEnd +
                " " + m.getXPos() + " " + m.getYPos() + " " + s.getDimension().getWidth() + " "
                + s.getDimension().getHeight() + " " + s.getColor().getRed() + " "
                + s.getColor().getGreen() + " " + s.getColor().getBlue() + "\n";
          } else {
            out += "motion" + model.getShapes().get(i).getName() + " " + tStart +
                " " + s.getX() + " " + s.getY() + " " + s.getDimension().getWidth() + " "
                + s.getDimension().getHeight() + " " + s.getColor().getRed() + " "
                + s.getColor().getGreen() + " " + s.getColor().getBlue() + " " + tEnd +
                " " + s.getX() + " " + s.getY() + " " + m.getDimension().getWidth() + " "
                + m.getDimension().getHeight() + " " + s.getColor().getRed() + " "
                + s.getColor().getGreen() + " " + s.getColor().getBlue() + "\n";
          }
        }
      }
    }
    return out;
  }


  /**
   * this method gets all the information relating to the canvas.
   *
   * @return all the necessary information about the canvas.
   */
  public String getCanvasInfo(Canvas canvas) {
    return "canvas " + canvas.getAlignmentX() + " " + canvas.getAlignmentY()
        + " " + canvas.getWidth() +
        " " + canvas.getHeight() + "\n";
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }
}
