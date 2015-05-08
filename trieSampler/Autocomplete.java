
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *********** Note to Artsy **************
 * This class runs an autocomplete on an input file and spits out the top k prefix for
 * for any input prefix. Top matches are determined by weights in input file. 
 * I have enclosed a cities.txt file that contains world cities and their respective populations.
 * To run:
 *
 * After using javac *.java to compile run using command line arguements: 
 * java AutoComplete [File Name] [k]
 * Where k is the number of autocomplete desired suggestion. 
 * Now in terminal type any prefix. [prefix]
 * 
 * Ex:
airbears2-10-142-191-149:artsySample henryrosen$ java Autocomplete cities.txt 10
H
     7012738.0  Hong Kong, China
     3597816.0  Hyderābād, India
     3467331.0  Hồ Chí Minh City, Vietnam
     3229883.0  Harbin, China
     2163824.0  Havana, Cuba
     2099451.0  Houston, Texas, United States
     1878129.0  Hangzhou, China
     1739117.0  Hamburg, Germany
     1542813.0  Harare, Zimbabwe
     1431270.0  Hanoi, Vietnam
 */


/**
 * @author Tobias Rosen
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 */
public class Autocomplete {
    private HashMap<String, Double> weights;
    private Trie weightedTrie;

    /**
     * Initializes required data structures from parallel arrays.
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        if (terms.length != weights.length) {
            throw new IllegalArgumentException();
        }
        this.weights = new HashMap<String, Double>();
        weightedTrie = new Trie();
        for (int i = 0; i < terms.length; i++) {
            String tempWord = terms[i];
            Double tempVal = weights[i];
            if (tempVal < 0) {
                throw new IllegalArgumentException();
            }
            if (this.weights.containsKey(tempWord)) {
                throw new IllegalArgumentException();
            }
            addWeightedWord(weightedTrie.rootNode, tempWord, tempVal, 0, '*');
            this.weights.put(tempWord, tempVal);
        }
    }

    /**
     * addWeightedWord() adds a word to a weightedTrie
     * @param n is the node currently being considered.
     * @param word is the word being added.
     * @param weight is the weight of the word being added.
     * @param local is location of the current letter of word.
     * @param name is the name of the node currently beig created or passed through.
     * This function is very similar to the normal Trie add method.
     * However it differs by putting weights into all Trie Node wieghts rather
     * leaving the weights as zero.
     * @return returns a weighted Node to be used by a previos Node's dictionary.
     */
    private Node addWeightedWord(Node n, String word, double weight, int local, char name) {
        if (n == null) {
            n = new Node(name);
            n.trieWeight(weight);
        }
        if (local == word.length()) {
            if (n.exists()) {
                throw new IllegalArgumentException();
            }
            n.exists(true);
            n.existWeight(weight);
            return n;
        }
        char c = word.charAt(local);
        n.trieWeight(Math.max(weight, n.trieMax()));
        n.links.put(c, addWeightedWord(n.get(c), word, weight, local + 1, c));
        return n;
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term is a term in Trie.
     * @return the weight of term in Trie.
     */
    public double weightOf(String term) {
        return weights.get(term);
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        Iterable<String> match = topMatches(prefix, 1);
        Iterator<String> onlyMatch = match.iterator();
        return onlyMatch.next();
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param prefix is the string that autocompletions will be found for.
     * @param k is the number of desired Autocomplete suggestions to be stored in an iterable.
     * @return Iterable of words that are Autocompletes for prefix.
     */
    public Iterable<String> topMatches(String prefix, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
        if ((prefix.length() == 2)) {
            if ((prefix.charAt(1) == '"') && (prefix.charAt(1) == '"')) {
                AutocompleteTopMatches matches = new AutocompleteTopMatches(weightedTrie.rootNode,
                                                                    new String(), k, weights);
                return matches.createMatches();
            }
        }
        Node prefixRoot = getPrefix(weightedTrie.rootNode, prefix);
        if (prefixRoot == null) {
            return new LinkedList<String>();
        }
        AutocompleteTopMatches topMatches = new AutocompleteTopMatches(prefixRoot, prefix,
                                                                       k, weights);
        return topMatches.createMatches();
    }

    /**
     * getPrefix() obtains the end Node of prefix in weightedTrie.
     * the end Node corresponds to the node of the last letter in prefix.
     * @param n starting node and changes to end node
     * @param prefix is the string that guides fn through Trie Nodes.
     * the last letter in the prefix is the node that is returned.
     * @return a Node that corresponds to the last leter in the prefix.
     */
    private Node getPrefix(Node n, String prefix) {
        for (int x = 0; x < prefix.length(); x++) {
            if (!(n.links.get(prefix.charAt(x)) == null)) {
                n = n.links.get(prefix.charAt(x));
            } else {
                return null;
            }   
        }
        return n;
    }

    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * @param word The word to spell-check
     * @param dist Maximum edit distance to search
     * @param k    Number of results to return 
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        LinkedList<String> results = new LinkedList<String>();  
        /* YOUR CODE HERE; LEAVE BLANK IF NOT PURSUING BONUS */
        return results;
    }
    /**
     * Test client. Reads the data from the file, then repeatedly reads autocomplete 
     * queries from standard input and prints out the top k matching terms.
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            double weight = in.readDouble();   // read the next weight
            weights[i] = weight;
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
            }
        }
    }
}
