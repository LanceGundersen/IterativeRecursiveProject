import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * IterativeRecursiveProject.java
 * Lance Gundersen
 * 03DEC17
 * @version 1.0
 *
 * This program implements an Iterative vs. Recursive efficiency program. It only allows for
 * 0-10 numbers and writes to a outputLog.csv file in the program root directory.
 *
 * The program implements error checking in and presents it to the user in a dialog.
 */

public class IterativeRecursiveProject extends JFrame {

  static final int WINDOWWIDTH = 450, WINDOWHEIGHT = 300;
  private static FileWriter fileWriter;
  private JLabel enterNLabel = new JLabel("Enter n:");
  private JLabel resultLabel = new JLabel("Result:");
  private JLabel efficiencyLabel = new JLabel("Efficiency:");
  private JTextField efficiencyOutput = new JTextField("");
  private JTextField resultOutput = new JTextField("");
  private JRadioButton iterativeRadio = new JRadioButton("Iterative");
  private JRadioButton recursiveRadio = new JRadioButton("Recursive");
  private JButton computeButton = new JButton("Compute");
  private JTextField entry = new JTextField("");
  private ButtonGroup radios = new ButtonGroup();
  private JOptionPane frame = new JOptionPane();
  private JLabel blankLabel = new JLabel("");
  private File outputLog = new File("outputLog.csv");
  private ArrayList<String> logList = new ArrayList<>();
  private ArrayList<String> listIter = new ArrayList<>();
  private ArrayList<String> listRec = new ArrayList<>();
  private int entryValue;

  public IterativeRecursiveProject() {
    setFrame(WINDOWWIDTH, WINDOWHEIGHT);
    setResizable(false);
    JPanel primaryPanel = new JPanel();
    add(primaryPanel);
    primaryPanel.setLayout(new GridLayout(5, 2, 0, 10));
    radios.add(iterativeRadio);
    radios.add(recursiveRadio);
    primaryPanel.add(iterativeRadio);
    primaryPanel.add(recursiveRadio);
    iterativeRadio.setSelected(true);
    primaryPanel.add(enterNLabel);
    primaryPanel.add(entry);
    primaryPanel.add(blankLabel);
    primaryPanel.add(computeButton);
    primaryPanel.add(resultLabel);
    primaryPanel.add(resultOutput);
    primaryPanel.add(efficiencyLabel);
    primaryPanel.add(efficiencyOutput);

    computeButton.addActionListener(new ComputeButtonListener());
    CloseApplicationWriteFileAdapter close = new CloseApplicationWriteFileAdapter();
    addWindowListener(close);
  }

  public static void main(String[] args) {
    IterativeRecursiveProject irp = new IterativeRecursiveProject();
    irp.display();
  }

  private void setFrame(int frameWidth, int frameHeight) {
    setSize(frameWidth, frameHeight);
    setLocationRelativeTo(null);
  }

  public void display() {
    setVisible(true);
  }

  public void addToCSV(String choice) {
    logList.add(choice + ", " + Sequence.getEfficiencyCount() + ", " + entryValue);
  }

  public void addToListIter() {
    listIter.add(Sequence.getEfficiencyCount() + ", " + entryValue);
  }

  public void addToListRec() {
    listRec.add(Sequence.getEfficiencyCount() + ", " + entryValue);
  }

  public void writeFiles() {

    try {
      fileWriter = new FileWriter(outputLog);
      for (String l : logList) {
        fileWriter.write(l + System.getProperty("line.separator"));
      }
      fileWriter.close();
      for (String l : listIter) {
        fileWriter.write(l + System.getProperty("line.separator"));
      }
      fileWriter.close();
      for (String l : listRec) {
        fileWriter.write(l + System.getProperty("line.separator"));
      }
      fileWriter.close();
    } catch (IOException e) {
      e.getMessage();
    }
  }

  public int getEntryValue() {
    try {
      return Integer.parseInt(entry.getText());
    } catch (NumberFormatException e) {
      clearEntryValue();
      return 11;
    }
  }

  public void setEntryValue() {
    this.entryValue = getEntryValue();
  }

  public void clearEntryValue() {
    entry.setText("");
  }

  public void errorPopUp() {
    JOptionPane.showMessageDialog(frame, "Invalid input.");
  }

  class ComputeButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      setEntryValue();
      if (entryValue > 10 || entryValue < 0) {
        errorPopUp();
      } else if (iterativeRadio.isSelected()) {
        resultOutput.setText(String.valueOf(Sequence.computeIterative(entryValue)));
        efficiencyOutput.setText(String.valueOf(Sequence.getEfficiencyCount()));
        addToCSV("Iterative");
        addToListIter();
      } else if (recursiveRadio.isSelected()) {
        resultOutput.setText(String.valueOf(Sequence.computeRecursive(entryValue)));
        efficiencyOutput.setText(String.valueOf(Sequence.getEfficiencyCount()));
        addToCSV("Recursive");
        addToListRec();
      }
      clearEntryValue();
    }
  }

  class CloseApplicationWriteFileAdapter extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
      try {
        if (!logList.isEmpty()) {
          writeFiles();
          fileWriter.close();
        }
        System.out.println("Try statement close");
      } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("Caught IOException");
        System.exit(0);
      } catch (NullPointerException e1) {
        e1.printStackTrace();
        System.out.println("Caught null pointer");
        System.exit(0);
      }
      System.exit(0);
    }
  }
}
