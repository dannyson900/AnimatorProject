package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Move;
import cs3500.animator.model.Shape;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class is the visual view for the animation and it calculates the position, color and size of
 * the shapes and what they are supposed to go to.
 */
public class VisualView extends JFrame implements View {

  /**
   * this is the constructor for visual view and animates the model.
   *
   * @param model       is the model to be animated.
   * @param ticksPerSec is the num of ticks per sec.
   */
  public VisualView(AnimationModel model, int ticksPerSec) {
    super();
    this.setTitle("Animation");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    JPanel background = new JPanel();
    Canvas canvas = model.getBounds();
    background.setAlignmentX(canvas.getAlignmentX());
    background.setAlignmentY(canvas.getAlignmentY());
    background.setPreferredSize(canvas.getSize());
    JScrollPane scrollPane = new JScrollPane(background);
    this.add(scrollPane);
    this.pack();
    ArrayList<Move> moves = model.getMoves();
    ArrayList<Shape> shapes = model.getShapes();
    VisualHelp help = new VisualHelp(shapes, moves, ticksPerSec);
    JScrollPane pane = new JScrollPane(help);
    this.add(pane);
    this.makeVisible();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }
}
