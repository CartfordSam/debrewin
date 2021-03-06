import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// De Bruijn sequence generator and other things

/**
 * Program Notes:
 * - be pretty cool if it took a generic, but that's not really what I'm going for in this program 
 * - ^ shuffles in particular
 * - I use "shift" and "rotate" interchangeably
 * 
 * TODO: 
 * - Write isDeBruijn() 
 * 
 * isDeb() implementation: generate array of all combinations for n,k. In for loop compare substring
 * to things in list, checking them off as you go. With binary search, should be O(nlogn).
 * ...It's not elegant, not clever, but quite comprehensible, and not too slow. 
 */

/**
 * yes
 * 
 * @author Reed Nelson, 2 time champion for most creative class names
 * @author ...
 */
public class Main {

  /**
   * Driver for the DeBruijn program
   * 
   * @param args
   */
  public static void main(String[] args) {

    final String A = "0123"; // Alphabet
    int k = A.length();
    int n = 4; // Window length
    
    DBGraph dbg = new DBGraph(A, n);
    
    //bigShuffleToSelf(A, n);

    System.out.println("For an alphabet \"" + A + "\" and window of " + n + ":");
    
    System.out.println("\nThere are " + numDBSequences(n, k) + " valid De Bruijn sequences");


    //System.out.println("\nAll sequences:");
    //dbg.printEulerian();
    
    //dbg.writeEulerian(-1);
    
    // System.out.println("\nOne such sequence:\n" + dbg.printEulerian(1)); // broken

    // allRotShuffleShiftToSelf("11101000");
    // allRotShuffleShiftToSelf("0111101011001000");

    // System.out.println("\nThe graph:");
    // dbg.printMatrix();   
    
  }

  /**
   * Determines whether the given sequence is a valid De Bruijn, for the given window Not yet
   * existent, obviously.
   * 
   * See Olivia's (Albertsofc) Python implementation. (Perhaps I'll get around to stealing it,
   * adapting it to Java)
   * 
   * @param sequence the sequence to check
   * @param n        the window size
   * @return true if the inputed sequence is a De Bruijn, false otherwise
   */
  public static boolean isDeBruijn(String sequence, int n) {

    // String superseq = sequence + sequence.substring(0, n - 1);
    // String subseq = "";
    //
    // for (int i = 0; i < (sequence.length() - n + 1); i++) {
    //
    // subseq = superseq.substring(i, i + n);
    //
    // if (sequence.contains(subseq)) {
    // return false;
    // } else ()
    //
    // }
    
    return false;

  }
  
  /**
   * Calculates the number of possible De Bruijn sequences considering the given window size and
   * alphabet length. Not very meaningful for even larger single digit values of n and k, due to the
   * limited maximum integer length in Java, and the growth rate for this function.
   * 
   * @param n the window length
   * @param k the alphabet length
   * @return the number of possible sequences or roughly the largest possible value.
   */
  public static String numDBSequences(int n, int k) {

    long l = (long) (Math.pow(factorial(k), Math.pow(k, n - 1)) / Math.pow(k, n));

    if (l == Long.MAX_VALUE) {
      return "over 9 pentillion";
    }

    return Long.toString(l);

  }

  /**
   * A humble helper for numPossibleDBSequences(), as j.l.Math doesn't have a function for this
   * 
   * Also the hello world of recursive functions, in case you wanna learn about that, Jamin
   * 
   * @param k the integer to be operated on
   * @return k!, eventually
   */
  private static int factorial(int k) {

    if (k == 0)
      return 1;

    return k * factorial(k - 1);

  }

  public static void bigShuffleToSelf(String A, int n) {
    
    String filePath = A.length() + "-" + n + ".txt"; 
    
    try {
      
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
      String line = bufferedReader.readLine();
      
      for (int i = 0; line != null; i++) {
        
        allRotShuffleToSelf(line, i);
        
        line = bufferedReader.readLine();
        
      }
      
      bufferedReader.close();
      
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  /**
   * Performs a perfect out shuffle, following the formula (length k, index i): if (i < k/2): i ->
   * 2i; if (i >= k/2): i -> 2i - k + 1
   * 
   * @param str the set to be shuffled
   * @return the shuffled set, as a String
   * @throws Exception in case an invalid String is passed
   */
  private static String outShuffle(String str) {

    // Only accept even strings, as this is implied in the definition of "out shuffle"
    if (str.length() % 2 != 0)
      return null; // Easier than exception handling

    int n = str.length() / 2;
    char[] newStr = new char[str.length()];
    String retStr = "";

    // Handle first half of domain
    for (int i = 0; i < n; i++)
      newStr[2 * i] = str.charAt(i);

    // Handle second half of domain
    for (int i = n; i < 2 * n; i++)
      newStr[(2 * i) - (2 * n) + 1] = str.charAt(i);

    // Convert from char Array to String
    for (int i = 0; i < newStr.length; i++)
      retStr += newStr[i];

    return retStr;

  }

  /**
   * Performs a perfect in shuffle, following the formula (length k, index i): if (i < k/2): i -> 2i
   * + 1; if (i >= k/2): i -> 2i - k
   * 
   * @param str the set to be shuffled
   * @return the shuffled set, as a String
   * @throws Exception in case an invalid String is passed
   */
  private static String inShuffle(String str) {

    // Only accept even strings, as this is implied in the definition of "in shuffle"
    if (str.length() % 2 != 0)
      return null; // Easier than exception handling

    int n = str.length() / 2;
    char[] newStr = new char[str.length()];
    String retStr = "";

    // Handle first half of domain
    for (int i = 0; i < n; i++)
      newStr[(2 * i) + 1] = str.charAt(i);

    // Handle second half of domain
    for (int i = n; i < 2 * n; i++)
      newStr[(2 * i) - (2 * n)] = str.charAt(i);

    // Convert from char Array to String
    for (int i = 0; i < newStr.length; i++)
      retStr += newStr[i];

    return retStr;

  }

  /**
   * Checks if the inputed string would retain its order after being out-shuffled
   * O(n) for true return, O(1) for false (a bit epic)
   * 
   * @param str the string to be checked
   * @return true if the string would out-shuffle to its original order, false otherwise
   */
  private static boolean outShufflesToSelf(String str) {
    
    int n = str.length() / 2;
    
    // Handle first half of domain
    for (int i = 0; i < n; i++)
      if (str.charAt(i) != (str.charAt(2 * i)))
        return false;

    // Handle second half of domain (shit ain't transitive)
    for (int i = n; i < 2 * n; i++)
      if (str.charAt(i) != (str.charAt((2 * i) - (2 * n) + 1)))
        return false;
    
    return true;
    
  }

  /**
   * Checks if the inputed string would retain its order after being in-shuffled
   * O(n) for true return, O(1) for false (a bit epic)
   * 
   * @param str the string to be checked
   * @return true if the string would in-shuffle to its original order, false otherwise
   */
  private static boolean inShufflesToSelf(String str) {

    int n = str.length() / 2;

    // Handle first half of domain
    for (int i = 0; i < n; i++)
      if (str.charAt(i) != (str.charAt((2 * i) + 1)))
        return false;

    // Handle second half of domain (shit ain't transitive)
    for (int i = n; i < 2 * n; i++)
      if (str.charAt(i) != (str.charAt((2 * i) - (2 * n))))
        return false;

    return true;

  }

  /**
   * Rotate a specified string right a specified amount
   * O(1)
   * 
   * @param str the string to be rotated
   * @param rot the amount to rotate by
   * @return the string, rotated the specified amount
   */
  private static String rotateRight(String str, int rot) {
    
    int cutPoint = str.length() - (rot % str.length()); // adjusts for rotations > str length
    String retStr = "";
    
    retStr = str.substring(cutPoint) + str.substring(0, cutPoint);
    
    return retStr;
    
  }
  
  /**
   * Rotate a specified string left a specified amount
   * O(1)
   * 
   * @param str the string to be rotated
   * @param rot the amount to rotate by
   * @return the string, rotated the specified amount
   */
  private static String rotateLeft(String str, int rot) {
    
    int cutPoint = rot % str.length(); // adjusts for rotations > str length
    String retStr = "";
    
    retStr = str.substring(cutPoint) + str.substring(0, cutPoint);
    
    return retStr;
    
  }
  
  /**
   * Checks to see if rotated versions of the given string shuffle (in or out) back to themselves,
   * prints results
   * 
   * *if you're wondering whether you want this one or allRotShuffleShiftToSelf(), you probably want
   * this one.
   * 
   * @param str
   */
  private static void allRotShuffleToSelf(String str, int j) {

    String rot;
    
    for (int i = 0; i < str.length(); i++) {

      rot = rotateRight(str, i);

      if (outShufflesToSelf(rot))
        System.out.println(j + ": " + rot + " - OUT");

      if (inShufflesToSelf(rot))
        System.out.println(j + ":" + rot + " - IN");

    }

  }

  /**
   * Checks to see if rotated versions of the given string shuffle, then rotate back to themselves,
   * prints results 
   * 
   * Performs the following operation, specifically:
   * rotate right i characters > shuffle > rotate left i characters (back to rotated) > compare to
   * original rotation
   * 
   * Interestingly, the left rotate only gets it back to the state of the right rotate, not back to
   * the original string.
   * 
   * @param str the string to be checked
   */
  private static void allRotShuffleShiftToSelf(String str) {

    System.out.println("\nFor the sequence " + str
        + ", \nthe following shifts \"shuffle-shift\" back to themselves:");

    for (int i = 0; i < str.length(); i++) {

      System.out.print(i + " - ");

      if (rotateLeft(outShuffle(rotateRight(str, i)), 2 * i).equals(str))
        System.out.print(" OUT");

      if (rotateLeft(inShuffle(rotateRight(str, i)), 2 * i).equals(str))
        System.out.print(" IN");

      System.out.println();

    }

  }

}





