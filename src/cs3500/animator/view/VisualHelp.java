package cs3500.animator.view;

import cs3500.animator.model.Ellipse;
import cs3500.animator.model.Move;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class is a helper class for Visual View and controls all the animation, timer, drawing of
 * the shapes and is an ActionListener.
 */
public class VisualHelp extends JPanel implements ActionListener {

  ArrayList<Shape> shapes;
  ArrayList<Move> moves;
  protected Timer timer;
  protected int tick;
  private Color col;
  private int xPos;
  private int yPos;
  private Dimension dimension;

  /**
   * Starts the timer and is the constructor for this class.
   *
   * @param shapes      is the arraylist of shapes to be animated.
   * @param moves       is the arraylist of animation moves.
   * @param ticksPerSec is the number of ticks per second.
   */
  VisualHelp(ArrayList<Shape> shapes, ArrayList<Move> moves, int ticksPerSec) {
    this.shapes = shapes;
    this.moves = moves;
    this.timer = new Timer(1000 / ticksPerSec, (ActionListener) this);
    this.setPreferredSize(new Dimension(600, 600));
    timer.start();
    tick = 0;
  }

  /**
   * Is The constructor for this class but doesn't start the timer.
   *
   * @param shapes      is the arraylist of shapes to be animated.
   * @param moves       is the arraylist of animation moves.
   */
  VisualHelp(ArrayList<Shape> shapes, ArrayList<Move> moves) {
    this.shapes = shapes;
    this.moves = moves;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Boolean isMultipleMoves;
    ArrayList<Integer> indexes = new ArrayList<Integer>();
    for (Move move : moves) {
      indexes.add(move.getInd());
    }
    for (int i = 0; i < moves.size(); i++) {
      int count = 0;
      Move move = moves.get(i);
      int shapeIndex = move.getInd();
      for (int z = 0; z < indexes.size(); z++) {
        if (move.getInd() == indexes.get(z)) {
          count++;
        }
      }
      isMultipleMoves = count > 1;
      col = shapes.get(shapeIndex).getColor();
      xPos = shapes.get(shapeIndex).getX();
      yPos = shapes.get(shapeIndex).getY();
      dimension = shapes.get(shapeIndex).getDimension();
      if (this.tick >= move.getTickStart() && this.tick <= move.getTickEnd()) {
        if (isMultipleMoves) {
          int c = 0;
          ArrayList<Move> temp = new ArrayList<Move>();
          for (int j = i; j < moves.size(); j++) {
            if (moves.get(j).getInd() == move.getInd()
                && ((moves.get(j).getTickStart() >= move.getTickStart())) &&
                (moves.get(j).getTickEnd() <= move.getTickEnd()) &&
                ((this.tick >= moves.get(j).getTickStart()) &&
                    (this.tick <= moves.get(j).getTickEnd()))) {
              temp.add(moves.get(j));
              c++;
            }
          }
          i += c;
          for (Move m : temp) {
            tween(m, shapeIndex);
          }
        } else {
          tween(move, shapeIndex);
        }
        g.setColor(col);
        if (shapes.get(shapeIndex).getShape().equals("ellipse")) {
          g.fillOval(xPos, yPos, (int) dimension.getWidth(), (int) dimension.getHeight());
          shapes.set(shapeIndex,
              new Ellipse(xPos, yPos, dimension, col, shapes.get(shapeIndex).getName()));
        } else if (shapes.get(shapeIndex).getShape().equals("rectangle")) {
          g.fillRect(xPos, yPos, (int) dimension.getWidth(), (int) dimension.getHeight());
          shapes.set(shapeIndex,
              new Rectangle(xPos, yPos, dimension, col, shapes.get(shapeIndex).getName()));
        }
      }
    }
  }

  /**
   * this method calculates and sets the color dimension and position of the shape.
   *
   * @param move       is the move that is going to happen.
   * @param shapeIndex is the index in shapes of the shape.
   */
  private void tween(Move move, int shapeIndex) {
    int rd;
    int gr;
    int bl;
    int width;
    int height;
    double a = (((double) move.getTickEnd() - (double) this.tick) / (move.getTickEnd()
        - (double) move.getTickStart()));
    double b = (((double) this.tick - (double) move.getTickStart()) / (move.getTickEnd()
        - (double) move.getTickStart()));
    if (move.getChangeColor()) {
      rd = (int) ((shapes.get(shapeIndex).getColor().getRed() * a) + (move.getCol().getRed()
          * b));
      gr = (int) ((shapes.get(shapeIndex).getColor().getGreen() * a) + (move.getCol().getGreen()
          * b));
      bl = (int) ((shapes.get(shapeIndex).getColor().getBlue() * a) + (move.getCol().getBlue()
          * b));
      col = new Color(rd, gr, bl);
    } else if (move.getChangePosition()) {
      xPos = (int) ((shapes.get(shapeIndex).getX() * a) + (move.getXPos() * b));
      yPos = (int) ((shapes.get(shapeIndex).getY() * a) + (move.getYPos() * b));
    } else if (move.getChangeSize()) {
      width = (int) ((shapes.get(shapeIndex).getDimension().getWidth() * a)
          + (move.getDimension().getWidth() * b));
      height = (int) ((shapes.get(shapeIndex).getDimension().getHeight() * a)
          + (move.getDimension().getHeight() * b));
      dimension = new Dimension(width, height);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (tick < this.getEndTick()) {
      tick++;
      repaint();
    } else {
      timer.stop();
    }
  }

  /**
   * calulates the largest endtick.
   *
   * @return the largest endtick.
   */
  protected int getEndTick() {
    int endTick = 0;
    for (Move move : moves) {
      if (endTick < move.getTickEnd()) {
        endTick = move.getTickEnd();
      }
    }
    return endTick;
  }
}
