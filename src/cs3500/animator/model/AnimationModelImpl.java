package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Represents implementation of our SimpleAnimation from the AnimationModel. In this animation we
 * are moving two shapes one of which we will decrease its size, and the other we will change its
 * color.
 */
public class AnimationModelImpl implements AnimationModel {

  private Canvas canvas;
  private ArrayList<Shape> shapes;
  private ArrayList<Move> moves = new ArrayList<Move>();
  private int tickSt;
  private int tickEd;
  private int shapeBefore = 0;
  private String textStart;
  private String textEnd;


  /**
   * Constructor for the the AnimationModelImpl.
   *
   * @param shapes is a list of shapes that the user wants to animate
   */
  public AnimationModelImpl(ArrayList<Shape> shapes) {
    this.shapes = shapes;
    int tick = 0;
    //this would be used in the animate class
    this.tickSt = 0;
    this.tickEd = 0;

    this.textStart = "";
    this.textEnd = "";
  }

  /**
   * no param constructor.
   */
  public AnimationModelImpl() {
    this.shapes = new ArrayList<Shape>();
    //this would be used in the animate class
    this.tickSt = 0;
    this.tickEd = 0;
    this.canvas = new Canvas();
    this.textStart = "";
    this.textEnd = "";
  }

  /**
   * Constructor using our builder Pattern.
   *
   * @param canvas The canvas of our screen in which we animate.
   * @param shapes A list of shapes in our model.
   * @param moves  A list of moves in our model.
   */
  public AnimationModelImpl(Canvas canvas, ArrayList<Shape> shapes, ArrayList<Move> moves) {
    this.canvas = canvas;
    this.shapes = shapes;
    this.moves = moves;
    this.convertMoves(moves);
  }

  private void convertMoves(ArrayList<Move> moves) {
    for (Move m : moves) {
      if (m.getChangeColor()) {
        moves.set(moves.indexOf(m), new Move(this, m.getCol(),m.getInd(),
            m.getTickStart(),m.getTickEnd(), false));
      }
      if (m.getChangeSize()) {
        moves.set(moves.indexOf(m), new Move(this, m.getDimension(),m.getInd(),
            m.getTickStart(),m.getTickEnd(), false));
      }
      if (m.getChangePosition()) {
        moves.set(moves.indexOf(m), new Move(this, m.getXPos(), m.getYPos(),m.getInd(),
            m.getTickStart(),m.getTickEnd(), false));
      }
    }
  }

  /**
   * Animate runs a series of commands on the list of shapes animating them.
   */
  public void animate() {
    for (int i = 0; i < moves.size(); i++) {
      moves.get(i).animate();
    }
  }

  @Override
  public void animateColor(Color col, int ind, int tickStart, int tickEnd) {
    if ((col.getRed() < 0 || col.getRed() > 255) || (col.getGreen() < 0 || col.getGreen() > 255)
        || (col.getBlue() < 0 || col.getBlue() > 255) || tickStart < 0 || tickEnd < 0) {
      throw new IllegalArgumentException("Invalid inputs for animateColor");
    } else if (tickSt == tickStart && tickEnd == tickEd
        && ind == shapeBefore) {
      int textSize = shapes.get(ind).getTextArr().size() - 1;
      shapes.get(ind).getTextArr().remove(textSize);
      shapes.get(ind).changeColor(col, tickStart, tickEnd);
      textEnd = shapes.get(ind).produceText(tickEnd);
      String text = textStart + "    " + textEnd;
      shapes.get(ind).getTextArr().add(text);
    } else {
      textStart = shapes.get(ind).produceText(tickStart);
      shapes.get(ind).changeColor(col, tickStart, tickEnd);
      textEnd = shapes.get(ind).produceText(tickEnd);
      String text = textStart + "    " + textEnd;
      shapes.get(ind).getTextArr().add(text);
      tickSt = tickStart;
      tickEd = tickEnd;
      shapeBefore = ind;
    }
  }

  @Override
  public void animatePosition(int xPos, int yPos, int ind, int tickStart, int tickEnd) {
    if (xPos < 0 || yPos < 0 || tickEnd < 0 || tickStart < 0) {
      throw new IllegalArgumentException("Invalid inputs for animatePosition");
    } else if (tickSt == tickStart && tickEnd == tickEd
        && ind == shapeBefore) {
      int textSize = shapes.get(ind).getTextArr().size() - 1;
      shapes.get(ind).getTextArr().remove(textSize);
      shapes.get(ind).moveShape(xPos, yPos, tickStart, tickEnd);
      textEnd = shapes.get(ind).produceText(tickEnd);
      String text = textStart + "    " + textEnd;
      shapes.get(ind).getTextArr().add(text);
    } else {
      textStart = shapes.get(ind).produceText(tickStart);
      shapes.get(ind).moveShape(xPos, yPos, tickStart, tickEnd);
      textEnd = shapes.get(ind).produceText(tickEnd);
      String text = textStart + "    " + textEnd;
      shapes.get(ind).getTextArr().add(text);
      tickSt = tickStart;
      tickEd = tickEnd;
      shapeBefore = ind;
    }
  }

  @Override
  public void animateSize(Dimension dimension, int ind, int tickStart, int tickEnd) {
    if (dimension.getHeight() < 0 || dimension.getWidth() < 0 || tickEnd < 0 || tickStart < 0) {
      throw new IllegalArgumentException("Invalid Inputs for animateSize");
    } else if (tickSt == tickStart && tickEnd == tickEd
        && ind == shapeBefore) {
      int textSize = shapes.get(ind).getTextArr().size() - 1;
      shapes.get(ind).getTextArr().remove(textSize);
      shapes.get(ind).resize(dimension, tickStart, tickEnd);
      textEnd = shapes.get(ind).produceText(tickEnd);
      String text = textStart + "    " + textEnd;
      shapes.get(ind).getTextArr().add(text);
    } else {
      textStart = shapes.get(ind).produceText(tickStart);
      shapes.get(ind).resize(dimension, tickStart, tickEnd);
      textEnd = shapes.get(ind).produceText(tickEnd);
      String text = textStart + "    " + textEnd;
      shapes.get(ind).getTextArr().add(text);
      tickSt = tickStart;
      tickEd = tickEnd;
      shapeBefore = ind;
    }
  }

  @Override
  public void doNothing(int ind, int tickStart, int tickEnd) {
    if (tickSt == tickStart && tickEnd == tickEd
        && ind == shapeBefore) {
      int textSize = shapes.get(ind).getTextArr().size() - 1;
      shapes.get(ind).getTextArr().remove(textSize);
      textEnd = shapes.get(ind).produceText(tickEnd);
      String text = textStart + "    " + textEnd;
      shapes.get(ind).getTextArr().add(text);
    } else {
      textStart = shapes.get(ind).produceText(tickStart);
      textEnd = shapes.get(ind).produceText(tickEnd);
      String text = textStart + "    " + textEnd;
      shapes.get(ind).getTextArr().add(text);
      tickSt = tickStart;
      tickEd = tickEnd;
      shapeBefore = ind;
    }
  }

  @Override
  public void addShape(Shape s) {
    this.shapes.add(s);
  }

  @Override
  public void removeShape(String name) {
    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).getName().equals(name)) {
        shapes.remove(i);
      }
    }
  }

  @Override
  public ArrayList<Shape> getShapes() {
    return this.shapes;
  }

  @Override
  public ArrayList<Move> getMoves() {
    return this.moves;
  }

  @Override
  public void addMove(Move move) {
    this.moves.add(move);
  }

  @Override
  public void addMoves(ArrayList<Move> moves) {
    this.moves.addAll(moves);
  }

  @Override
  public String getAnimationText() {
    String out = "";
    for (int i = 0; i < shapes.size(); i++) {
      out += "shape " + shapes.get(i).getName() + " " + shapes.get(i).getShape() + "\n";
      for (int j = 0; j < shapes.get(i).getTextArr().size(); j++) {
        out += "motion" + shapes.get(i).getName() + " " + shapes.get(i).getTextArr().get(j) + "\n";
      }
    }
    return out;
  }


  @Override
  public Canvas getBounds() {
    return this.canvas;
  }


  @Override
  public void addKeyFrame(String shapeID, int tick, Move m) {
    if (tick < 0) {
      throw new IllegalArgumentException("tick needs to be non negative");
    }
    int indShape;
    int tStart;
    int indMove;
    //step one get the shapes ind
    for (Shape s: shapes) {
      if (shapeID == s.getName()) {
        indShape = shapes.indexOf(s);
        //step two, retrieve the move in which the given tick is in
        // between the start tick and end tick
        for (Move move : moves) {
          //step 3 determine the shapes tick if its in between, or if it changes the tickStart
          //or tickEnd
          if (move.getInd() == indShape && move.getTickStart() < tick && move.getTickEnd() > tick) {
            tStart = move.getTickStart();
            // step 4 set the move to be whatever it is in its current tick to end tick, and add a
            // move from the beginning tick to current tick
            move.setTickStart(tick);
            if (m.getChangeColor()) {
              moves.add(new Move(this, m.getCol(), indShape, tStart, tick, true));

            }
            if (m.getChangePosition()) {
              moves.add(new Move(this,m.getXPos(), m.getYPos(), indShape,
                  tStart, tick, true));
            }
            if (m.getChangeSize()) {
              moves.add(new Move(this, m.getDimension(), indShape, tStart, tick, true));
            }
          }
          if (move.getInd() == indShape &&  move.getTickStart() < tick &&
              move.getTickEnd() == tick) {
            tStart = move.getTickStart();
            indMove = moves.indexOf(move);
            if (m.getChangeColor()) {
              moves.set(indMove, new Move(this,m.getCol(), indShape, tStart,
                  tick, true));
            }
            if (m.getChangePosition()) {
              moves.set(indMove, new Move(this,m.getXPos(), m.getYPos(), indShape, tStart,
                  tick, true));
            }
            if (m.getChangeSize()) {
              moves.set(indMove, new Move(this,m.getDimension(),
                  indShape, tStart, tick, true));
            }
          }
          break;
        }
        break;
      }
    }
  }

  @Override
  public void removeKeyFrame(String shapeID, int tick) {
    int indShape;
    for (Shape s: shapes) {
      if (shapeID.equals(s.getName())) {
        indShape = shapes.indexOf(s);
        for (Move m: moves) {
          if (m.getInd() == indShape && (tick == m.getTickStart() || tick == m.getTickEnd())
              && m.getIsKeyFrame()) {
            moves.remove(moves.indexOf(m));
          }
        }
      }
    }
  }


  @Override
  public void modifyKeyFrame(String shapeID, int tick, Move move) {
    int indShape;
    for (Shape s: shapes) {
      if (shapeID.equals(s.getName())) {
        indShape = shapes.indexOf(s);
        for (Move m: moves) {
          if (m.getInd() == indShape && (tick == m.getTickStart() || tick == m.getTickEnd())
              && m.getIsKeyFrame()) {
            if (move.getChangeColor()) {
              m.setCol(move.getCol());
            }
            else if (move.getChangePosition()) {
              m.setXPos(move.getXPos());
              m.setYPos(move.getYPos());
            }
            else if (move.getChangeSize()) {
              m.setDimension(move.getDimension());
            }
          }
        }
      }
    }
  }



  /**
   * The builder class implements AnimationBuilder and creates an AnimationModel based on the input
   * file.  It "describes constructing any animation, shape-by-shape and motion-by-motion," this is
   * from the course page.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {

    private Canvas canvas;
    private ArrayList<Move> moves = new ArrayList<Move>();

    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private ArrayList<String> shapeTemp = new ArrayList<String>();
    private ArrayList<Move> movesInBuilder = new ArrayList<Move>();


    @Override
    public AnimationModel build() {
      return new AnimationModelImpl(canvas, shapes, movesInBuilder);
    }

    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      this.canvas = new Canvas();
      canvas.setBounds(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
      shapeTemp.add(name + " " + type);
      return this;

    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) {
      for (int i = 0; i < shapeTemp.size(); i++) {
        if (shapeTemp.get(i).contains(name)) {
          if (shapeTemp.get(i).contains("rectangle")) {
            Shape s = new Rectangle(x1, y1, new Dimension(w1, h1), new Color(r1, g1, b1), name);
            shapes.add(s);
            //checks which move should be added
            if ((Math.abs(x1 - x2) > 0) || (Math.abs(y1 - y2) > 0)) {
              movesInBuilder.add(new Move(x2, y2, shapes.indexOf(s), t1, t2));

            } else if ((Math.abs(r1 - r2) > 0 || (Math.abs(g1 - g2) > 0)
                || (Math.abs(b1 - b2) > 0))) {
              movesInBuilder.add(new Move(new Color(r2, g2, b2), shapes.indexOf(s), t1, t2));
            } else {
              movesInBuilder.add(new Move(new Dimension(w2, h2), shapes.indexOf(s), t1, t2));
            }

          } else {
            Shape s = new Ellipse(x1, y1, new Dimension(w1, h1), new Color(r1, g1, b1), name);
            shapes.add(s);
            if ((Math.abs(x1 - x2) > 0) || (Math.abs(y1 - y2) > 0)) {
              movesInBuilder.add(new Move(x2, y2, shapes.indexOf(s), t1, t2));
            } else if ((Math.abs(r1 - r2) > 0 || (Math.abs(g1 - g2) > 0)
                || (Math.abs(b1 - b2) > 0))) {
              movesInBuilder.add(new Move(new Color(r2, g2, b2), shapes.indexOf(s), t1, t2));
            } else {
              movesInBuilder.add(new Move(new Dimension(w2, h2), shapes.indexOf(s), t1, t2));
            }
          }
        }
      }
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addKeyframe(String name, int t, int x, int y, int w,
        int h, int r, int g, int b) {
      return this;
    }
  }

}