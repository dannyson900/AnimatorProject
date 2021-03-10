package model;

import static org.junit.Assert.assertEquals;

import cs3500.animator.FactoryView;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl.Builder;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.View;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;


/**
 * test for text view.
 */
public class TextExample {
  @Test
  public void testTextView() throws IOException {

    Appendable out = new StringBuilder();
    String inputFile = "hanoi.txt";
    String outputFile = "out.txt";
    String viewType = "text";
    int ticksPerSecond = 1;
    View view;
    if (inputFile.equals("")) {
      throw new IllegalArgumentException("No input file");
    } else if (viewType.equals("")) {
      throw new IllegalArgumentException("No specified view");
    }
    AnimationReader read = new AnimationReader();
    AnimationBuilder builder = new Builder();
    FileReader file = new FileReader(inputFile);
    read.parseFile(file, builder);
    AnimationModel model = (AnimationModel) builder.build();
    FactoryView factory = new FactoryView(model, ticksPerSecond, outputFile);
    view = factory.getView(viewType);
    FileReader expected = new FileReader("expected.txt");


    assertEquals(expected.toString(), file.toString());
  }

  @Test
  public void testSVGView() throws IOException {

    Appendable out = new StringBuilder();
    String inputFile = "buildings.txt";
    String outputFile = "out.txt";
    String viewType = "svg";
    int ticksPerSecond = 1;
    View view;
    if (inputFile.equals("")) {
      throw new IllegalArgumentException("No input file");
    } else if (viewType.equals("")) {
      throw new IllegalArgumentException("No specified view");
    }
    AnimationReader read = new AnimationReader();
    AnimationBuilder builder = new Builder();
    FileReader file = new FileReader(inputFile);
    read.parseFile(file, builder);
    AnimationModel model = (AnimationModel) builder.build();
    FactoryView factory = new FactoryView(model, ticksPerSecond, outputFile);
    view = factory.getView(viewType);
    FileReader expected = new FileReader("expectedSVG.txt");
    assertEquals(expected.toString(), file.toString());
  }
}

