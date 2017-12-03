/**
 * Sequence.java
 * Lance Gundersen
 * 03DEC17
 * @version 1.0
 *
 * This program is the utility class for the Iterative vs. Recursive efficiency program.
 *
 *
 * The program implements error checking in and presents it to the user in a dialog.
 */

public class Sequence {

  private static int efficiencyCount = 0;

  private Sequence() {
  }

  public static int computeIterative(int n) {
    efficiencyCount = 0;
    int returnValue = 0;
    if (n == 0) {
      efficiencyCount++;
      return 0;
    } else if (n == 1) {
      efficiencyCount++;
      return 1;
    } else {
      int last = 1;
      int nextLast = 0;
      for (int i = 2; i <= n; i++) {
        efficiencyCount++;
        returnValue = 2 * last + nextLast;
        nextLast = last;
        last = returnValue;
      }
    }
    return returnValue;
  }

  public static int computeRecursive(int n) {
    efficiencyCount = 0;
    return computeRecursiveHelper(n);
  }

  private static int computeRecursiveHelper(int n) {
    if (n == 0) {
      efficiencyCount++;
      return 0;
    } else if (n == 1) {
      efficiencyCount++;
      return 1;
    } else {
      efficiencyCount++;
      return 2 * computeRecursiveHelper(n - 1) + computeRecursiveHelper(n - 2);
    }
  }

  public static int getEfficiencyCount() {
    return efficiencyCount;
  }

}
