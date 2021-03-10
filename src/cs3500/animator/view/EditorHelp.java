package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Ellipse;
import cs3500.animator.model.Move;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shape;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

/**
 * This class extends the visualHelp class and is a helper for the EditorView.  It constructs an
 * animation with the ability to input commands through buttons and text boxes.
 */
public class EditorHelp extends VisualHelp {

  private AnimationModel model;
  private JLabel commandDisplay = new JLabel();
  private Timer timer;
  private Boolean start = false;
  private Boolean pause = false;
  private Boolean loop = false;
  private int ticksPerSec;
  private static ArrayList<Shape> tempS = new ArrayList<Shape>();
  private static ArrayList<Move> tempM = new ArrayList<Move>();
  private JLabel inputDisplay;


  /**
   * This is constructor for this class and sets up all of the buttons and input dialog panels.
   *
   * @param model       is the model that is to be animated.
   * @param shapes      is the arraylist of shapes to be animated.
   * @param moves       is the arraylist of animation moves.
   * @param ticksPerSec is the number of ticks per second.
   */
  EditorHelp(AnimationModel model, ArrayList<Shape> shapes, ArrayList<Move> moves,
      int ticksPerSec) {
    super(shapes, moves);
    JPanel commands;
    this.model = model;
    tempS.addAll(super.shapes);
    this.ticksPerSec = ticksPerSec;
    commands = new JPanel();
    commands.setBorder(BorderFactory.createTitledBorder("Command buttons"));
    commands.setLayout(new BoxLayout(commands, BoxLayout.PAGE_AXIS));

    ButtonGroup groupStart = new ButtonGroup();
    ButtonGroup groupPR = new ButtonGroup();
    ButtonGroup groupED = new ButtonGroup();
    ButtonGroup groupIS = new ButtonGroup();
    JRadioButton[] buttons = new JRadioButton[8];

    buttons[0] = new JRadioButton("Start");
    buttons[1] = new JRadioButton("Pause");
    buttons[2] = new JRadioButton("Resume");
    buttons[3] = new JRadioButton("Restart");
    buttons[4] = new JRadioButton("Enable Loop");
    buttons[5] = new JRadioButton("Disable Loop");
    buttons[6] = new JRadioButton("Increase Speed");
    buttons[7] = new JRadioButton("Decrease Speed");

    buttons[0].setActionCommand("Start");
    buttons[1].setActionCommand("Pause");
    buttons[2].setActionCommand("Resume");
    buttons[3].setActionCommand("Restart");
    buttons[4].setActionCommand("Enable Loop");
    buttons[5].setActionCommand("Disable Loop");
    buttons[6].setActionCommand("Increase Speed");
    buttons[7].setActionCommand("Decrease Speed");
    for (int i = 0; i < buttons.length; i++) {
      buttons[i].addActionListener(this);
      commands.add(buttons[i]);
    }
    groupStart.add(buttons[0]);
    groupPR.add(buttons[1]);
    groupPR.add(buttons[2]);
    groupED.add(buttons[4]);
    groupED.add(buttons[5]);
    groupIS.add(buttons[6]);
    groupIS.add(buttons[7]);
    commands.add(commandDisplay);
    this.setLayout(new BorderLayout());
    this.add(commands, BorderLayout.EAST);

    JPanel inputDialogPanel = new JPanel();
    inputDialogPanel.setLayout(new FlowLayout());

    JButton inputButton = new JButton("Click to enter a command");
    inputButton.setActionCommand("Input");
    inputButton.addActionListener(this);
    inputDialogPanel.add(inputButton);

    inputDisplay = new JLabel("Default");
    inputDialogPanel.add(inputDisplay);
    this.add(inputDialogPanel, BorderLayout.SOUTH);
  }

  @Override
  protected void paintComponent(Graphics g) {
    if (start && !pause) {
      super.paintComponent(g);
    }
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() != null) {
      if (e.getActionCommand().equals("Start")) {
        start();
        commandDisplay.setText("Start animation");
      } else if (e.getActionCommand().equals("Pause")) {
        commandDisplay.setText("Paused animation");
        pause();
      } else if (e.getActionCommand().equals("Resume")) {
        commandDisplay.setText("Resumed animation");
        resume();
      } else if (e.getActionCommand().equals("Restart")) {
        commandDisplay.setText("Restart animation");
        restart();
      } else if (e.getActionCommand().equals("Enable Loop")) {
        commandDisplay.setText("Enabled Loop");
        enableLoop();
      } else if (e.getActionCommand().equals("Disable Loop")) {
        commandDisplay.setText("Disabled Loop");
        disableLoop();
      } else if (e.getActionCommand().equals("Increase Speed")) {
        commandDisplay.setText("Increased Speed");
        increaseSpeed();
      } else if (e.getActionCommand().equals("Decrease Speed")) {
        commandDisplay.setText("Decreased Speed");
        decreaseSpeed();
      } else if (e.getActionCommand().equals("Input")) {
        String input = JOptionPane.showInputDialog("Please enter your command" +
            "\nList of commands: addShape, deleteShape,addKeyframe, deleteKeyframe, modifyKeyFrame"
            + "\nExample command: -command addShape -type ellipse -x 100 -y 200 "
            + "-dimension 15 30 -color 125 10 200"
            + "\nExample command: -command deleteShape -name shapeName"
            + "\nHave the commands/information be separated by a space");
        inputDisplay.setText(input);
        if (input != null) {
          String[] inputArray = input.split(" ");
          input(inputArray);
        }
      }
    }
    if (!loop) {
      super.actionPerformed(e);
    } else {
      if (tick < this.getEndTick()) {
        tick++;
        repaint();
      } else {
        restart();
      }
    }
  }

  /**
   * This method starts the program and only runs if the program has not started before.
   */
  private void start() {
    if (!start) {
      start = true;
      this.timer = new Timer(1000 / this.ticksPerSec, this);
      timer.start();
      super.tick = 0;
    }
  }

  /**
   * this method pauses the animation.
   */
  private void pause() {
    if (!pause) {
      pause = true;
      timer.stop();
    }
  }

  /**
   * this method resumes the animation if it was paused.
   */
  private void resume() {
    if (pause) {
      pause = false;
      timer.start();
    }
  }

  /**
   * this method restarts the animation.
   */
  private void restart() {
    timer.restart();
    for (int i = 0; i < shapes.size(); i++) {
      shapes.set(i, tempS.get(i));
    }
    for (int i = 0; i < moves.size(); i++) {
      moves.set(i, tempM.get(i));
    }
    tick = 0;
  }

  /**
   * this method enables the animation loop.
   */
  private void enableLoop() {
    loop = true;
  }

  /**
   * this method disables the animation loop.
   */
  private void disableLoop() {
    loop = false;
  }

  /**
   * this method increases the speed of the animation by 5 ticks per second.
   */
  private void increaseSpeed() {
    this.ticksPerSec += 5;
    timer.setDelay(1000 / ticksPerSec);
  }

  /**
   * this method decreases the speed of the animation by 5 ticks per second.
   */
  private void decreaseSpeed() {
    this.ticksPerSec -= 5;
    timer.setDelay(1000 / ticksPerSec);
  }

  /**
   * This method takes a list of commands and information and runs the given command based off of
   * the rest of the information.
   *
   * @param input is the list of commands and information.
   */
  private void input(String[] input) {
    String commandType = "";
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    Dimension dimension;
    String name = "";
    int r = 0;
    int g = 0;
    int b = 0;
    Color col;
    String shapeType = "";
    int t = 0;
    // example input: -command addShape -type ellipse -x 100 -y 200
    // -dimension 15 30 -color 125 10 200
    try {
      for (int i = 0; i < input.length; i++) {
        if (input[i].equals("-command")) {
          commandType = input[i + 1];
          break;
        }
      }
      if (commandType.equals("addShape")) {
        for (int j = 0; j < input.length; j++) {
          if (input[j].equals("-type")) {
            shapeType = input[j + 1];
            j++;
          } else if (input[j].equals("-x")) {
            x = Integer.parseInt(input[j + 1]);
            j++;
          } else if (input[j].equals("-y")) {
            y = Integer.parseInt(input[j + 1]);
            j++;
          } else if (input[j].equals("-dimension")) {
            width = Integer.parseInt(input[j + 1]);
            height = Integer.parseInt(input[j + 2]);
            j += 2;
          } else if (input[j].equals("-color")) {
            r = Integer.parseInt(input[j + 1]);
            g = Integer.parseInt(input[j + 2]);
            b = Integer.parseInt(input[j + 3]);
            j += 3;
          } else if (input[j].equals("-name")) {
            name = input[j + 1];
            j++;
          }
        }
        col = new Color(r, g, b);
        dimension = new Dimension(width, height);
        System.out.println("type: " + shapeType);
        if (shapeType.equals("rectangle")) {
          shapes.add(new Rectangle(x, y, dimension, col, name));
        } else if (shapeType.equals("ellipse")) {
          shapes.add(new Ellipse(x, y, dimension, col, name));
        } else {
          throw new IllegalArgumentException("invalid shape type");
        }
      } else if (commandType.equals("deleteShape")) {
        int count = 0;
        int shapeCount = 0;
        boolean changed = false;
        for (int c = 0; c < input.length; c++) {
          if (input[c].equals("-name")) {
            for (int z = 0; z < shapes.size(); z++) {
              if (shapes.get(z).getName().equals(input[c + 1])) {
                shapes.remove(z);
                tempS.remove(z);
                for (int k = 0; k < moves.size(); k++) {
                  if (moves.get(k).getInd() == shapeCount) {
                    moves.remove(k);
                    count++;
                  }
                }
                z--;
                shapeCount++;
                changed = true;
              }
              if (!changed) {
                shapeCount++;
              } else {
                changed = false;
              }
            }
          }
        }
        adjustMoveInd();
        tempM = moves;
      } else if (commandType.equals("addKeyframe")) {
        for (int j = 0; j < input.length; j++) {
          String move = "";
          if (input[j].equals("-name")) {
            name = input[j + 1];
            j++;
          } else if (input[j].equals("-tick")) {
            t = Integer.parseInt(input[j + 1]);
            j++;
          } else if (input[j].equals("-move")) {
            for (int k = j; k < input.length; k++) {
              if (input[k].equals("-color")) {
                move = "color";
                r = Integer.parseInt(input[j + 1]);
                g = Integer.parseInt(input[j + 2]);
                b = Integer.parseInt(input[j + 3]);
                j += 3;

              } else if (input[j].equals("-x")) {
                move = "position";
                x = Integer.parseInt(input[j + 1]);
                j++;
              } else if (input[j].equals("-y")) {
                move = "position";
                y = Integer.parseInt(input[j + 1]);
                j++;
              } else if (input[j].equals("-dimension")) {
                move = "size";
                width = Integer.parseInt(input[j + 1]);
                height = Integer.parseInt(input[j + 2]);
                j += 2;
              }
            }
            col = new Color(r, g, b);
            dimension = new Dimension(width, height);
            if (move.equals("position")) {
              model.addKeyFrame(name, t, new Move(model, x, y, getIndShape(name),
                  0, 0, true));
            } else if (move.equals("color")) {
              model.addKeyFrame(name, t, new Move(model, col, getIndShape(name),
                  0, 0, true));
            } else if (move.equals("size")) {
              model.addKeyFrame(name, t, new Move(model, dimension, getIndShape(name),
                  0, 0, true));
            } else {
              throw new IllegalArgumentException("invalid move");
            }
          }
        }
      } else if (commandType.equals("deleteKeyframe")) {
        for (int j = 0; j < input.length; j++) {
          if (input[j].equals("-tick")) {
            t = Integer.parseInt(input[j + 1]);
            j++;
          } else if (input[j].equals("-name")) {
            name = input[j + 1];
            j++;
          }
        }
        model.removeKeyFrame(name, t);
      } else if (commandType.equals("modifyKeyframe")) {
        String move = "";
        for (int j = 0; j < input.length; j++) {
          if (input[j].equals("-name")) {
            name = input[j + 1];
            j++;
          } else if (input[j].equals("-tick")) {
            t = Integer.parseInt(input[j + 1]);
            j++;
          } else if (input[j].equals("-move")) {
            for (int k = j; k < input.length; k++) {
              if (input[k].equals("-color")) {
                move = "color";
                r = Integer.parseInt(input[j + 1]);
                g = Integer.parseInt(input[j + 2]);
                b = Integer.parseInt(input[j + 3]);
                j += 3;

              } else if (input[j].equals("-x")) {
                move = "position";
                x = Integer.parseInt(input[j + 1]);
                j++;
              } else if (input[j].equals("-y")) {
                move = "position";
                y = Integer.parseInt(input[j + 1]);
                j++;
              } else if (input[j].equals("-dimension")) {
                move = "size";
                width = Integer.parseInt(input[j + 1]);
                height = Integer.parseInt(input[j + 2]);
                j += 2;
              }
            }
            col = new Color(r, g, b);
            dimension = new Dimension(width, height);
            if (move.equals("position")) {
              model.modifyKeyFrame(name, t, new Move(model, x, y, getIndShape(name),
                  0, 0, true));
            } else if (move.equals("color")) {
              model.modifyKeyFrame(name, t, new Move(model, col, getIndShape(name),
                  0, 0, true));
            } else if (move.equals("size")) {
              model.modifyKeyFrame(name, t, new Move(model, dimension, getIndShape(name),
                  0, 0, true));
            } else {
              throw new IllegalArgumentException("invalid move");
            }
          }
        }
      }
      if (commandType.equals("")) {
        throw new IllegalArgumentException("need \"-command\"");
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Invalid list of commands");
    }
  }

  /**
   * this method adjusts the indexes of the shapes.
   */
  private void adjustMoveInd() {
    for (int i = 0; i < moves.size(); i++) {
      moves.get(i).setInd(i);
    }
  }

  /**
   * gives us the number of what index the name of our shape is, in an arrayList of shapes.
   *
   * @param name the name of the shape we are looking for
   * @return an int telling us what index this shape was in.
   */
  private int getIndShape(String name) {
    int ind = -1;
    for (Shape s : shapes) {
      if (shapes.get(shapes.indexOf(s)).getName().equals(name)) {
        ind = shapes.indexOf(s);
        break;
      }
    }
    return ind;
  }
}

