package cs3500.animator;

import static java.lang.Integer.parseInt;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl.Builder;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.View;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is the main class and is the entry point for the program. It builds a model and view based
 * on user instruction.
 */
public final class Excellence {

  /**
   * This is the main method and creates a model and view based on a set of given instructons.
   *
   * @param args is a array of string that are instuctors to create a view.
   * @throws IOException if files are not found
   */
  public static void main(String[] args) throws IOException {
    String inputFile = "";
    String outputFile = "";
    String viewType = "";
    int ticksPerSecond = 1;
    View view;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-in")) {
        inputFile = args[i + 1];
        i++;
      } else if (args[i].equals("-out")) {
        outputFile = args[i + 1];
        i++;
      } else if (args[i].equals("-view")) {
        viewType = args[i + 1];
        i++;
      } else if (args[i].equals("-speed")) {
        ticksPerSecond = parseInt(args[i + 1]);
      }
    }
    if (inputFile.equals("")) {
      throw new IllegalArgumentException("No input file");
    } else if (viewType.equals("")) {
      throw new IllegalArgumentException("No specified view");
    }
    AnimationReader read = new AnimationReader();
    AnimationBuilder builder = new Builder();
    FileReader file = new FileReader(inputFile);
    //if inputFile cannot be found the files are not in the right spot, drag all of them into the
    //HW6 folder, the outer most one.
    read.parseFile(file, builder);
    AnimationModel model = (AnimationModel) builder.build();
    FactoryView factory = new FactoryView(model, ticksPerSecond, outputFile);
    view = factory.getView(viewType);
  }
}