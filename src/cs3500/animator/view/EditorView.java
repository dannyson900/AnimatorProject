package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Move;
import cs3500.animator.model.Shape;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class is the editor view for the animation and it calculates the position, color and size of
 * the shapes and what they are supposed to go to.  It does the animation visually and adds the
 * ability for the user to input commands.
 */
public class EditorView extends JFrame implements View {

  /**
   * this is the constructor for editor view, it creates and animates the model.
   *
   * @param model       is the model to be animated.
   * @param ticksPerSec is the num of ticks per sec.
   */
  public EditorView(AnimationModel model, int ticksPerSec) {
    super();
    this.setTitle("Animation");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    JPanel background = new JPanel();
    Canvas canvas = model.getBounds();
    this.setLocation((int)canvas.getAlignmentX(), (int)canvas.getAlignmentY());
    this.setMinimumSize(new Dimension(canvas.getWidth() + 250, canvas.getHeight() + 250));
    JScrollPane scrollPane = new JScrollPane(background);
    scrollPane.setMinimumSize(canvas.getSize());
    this.add(scrollPane);
    ArrayList<Move> moves = model.getMoves();
    ArrayList<Shape> shapes = model.getShapes();
    EditorHelp help = new EditorHelp(model, shapes, moves, ticksPerSec);
    JScrollPane pane = new JScrollPane(help);
    pane.setPreferredSize(new Dimension(canvas.getWidth(),canvas.getHeight() + 100));
    pane.setMinimumSize(canvas.getSize());
    this.add(pane, BorderLayout.CENTER);
    this.pack();
    this.makeVisible();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }
}
