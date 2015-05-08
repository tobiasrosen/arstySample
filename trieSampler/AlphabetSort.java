
import java.util.Scanner;
import java.math.BigInteger;

/**
 *********** Note to Artsy **************
 * This class alphabet sorts a file. 
 * After using javac *.java to compile run using command line arguements: 
 * java AlphabetSort < [File Name]
 * An albabet sort sort a file according to the input alphabet, which should be 
 * the first line of any input file.
 * I have enclosed a file of a random alphabet and random words name: testAlphaLarge1
 */




/**
 * @author Tobias Rosen
 * AlphabetSort takes in an alphabet and list of words to sort 
 * Sources:
 * http://stackoverflow.com/questions/19484406/
 * detecting-if-a-string-has-unique-characters-comparing-my-solution-to-cracking
 */
public class AlphabetSort {
    /** 
     * main() executes an alphabet sort based on a particlar input alphabet and words.
     * @param args is a file that has a list of words to sort within it.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        if (!in.hasNextLine()) {
            throw new IllegalArgumentException();
        }
        String alphabet = in.nextLine();
        if (!AlphabetSort.isUniqueChars(alphabet)) {
            throw new IllegalArgumentException();
        }
        if (!in.hasNextLine()) {
            throw new IllegalArgumentException();
        }
        Trie t = new Trie();
        String tempWord;
        while (in.hasNextLine()) {
            tempWord = in.nextLine();
            t.insert(tempWord);
        }
        printTrie("", alphabet, t.rootNode);
    }

    /**
     * printTrie() prints a Trie in alphabatical order according to alphabet.
     * @param curr is the current string being checked if present in Node n.
     * @param alphabet is the alphabet to sort by,
     * @param n is the current node being considered.
     */
    public static void printTrie(String curr, String alphabet, Node n) {
        if (n.exists()) {
            System.out.println(curr);
        }
        for (int i = 0; i < alphabet.length(); i++) {
            char c = alphabet.charAt(i);
            if (n.containsKey(c)) {
                printTrie(curr + c, alphabet, n.get(c));
            }
        }
    }
       
    /**
     * isUniqueChars checks if str has all unique characters.
     * @param str a String to check if its characters are unique.
     * @return boolean whether or not every character in str is unique.
     */
    public static boolean isUniqueChars(String str) {
        int numDigitsMax = Character.MAX_VALUE;
        if (str.length() > numDigitsMax) {
            return false;
        }
        BigInteger checker = new BigInteger("0");
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if (checker.testBit(val)) {
                return false; 
            }
            checker = checker.setBit(val);
        }
        // none of the characters has been seen more than once.
        return true;
    }
}
