

/**
 * @author Tobias Rosen
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 */
public class Trie {
    Node rootNode;
    
    /**
     * Trie() contructs a new Trie. Makes an initial rootNode. 
     */
    public Trie() {
        rootNode = new Node();
    }


    /**
     * find() finds a string in Trie.
     * @param s is the string being found
     * @param isFullWord tells the funtion whether or not s must exist in this.
     * @return boolean if true: s is in this.
     * if isFullWord then the last letter must exist in Trie. 
     * if not isFullWord just the sequence of letter must be in Trie.
     */
    public boolean find(String s, boolean isFullWord) {
        return find(rootNode, s, 0, isFullWord);
    }

    /**
     * find() executes a call to find for a string in Trie.
     * @param n is the current node being searched,
     * @param s is the string being searched.
     * @param local is the charater in s being considered at this Node.
     * @param isFullWord tells whether or not S must exist.
     * @return boolean if s somewhere in this. Used by find fn above.
     */
    private boolean find(Node n, String s, int local, boolean isFullWord) {
        if (n == null) {
            return false;
        }
        if (local == s.length()) {
            if (isFullWord) {
                return n.exists();
            } else {
                return true;
            }
        } else {
            return find(n.get(s.charAt(local)), s, local + 1, isFullWord);
        }
    }

    /**
     * insert() inserts a new string into this Trie.
     * @param s is the new string to be inserted.
     */
    public void insert(String s) {
        if (s.isEmpty() || s == null) {
            throw new IllegalArgumentException();
        } else {
            add(rootNode, s, 0);
        }
    }
    /**
     * add() is a helper funtion for insert.
     * completes the jobs of insert by making recursive calls on Trie and adding new Nodes.
     * @param n is the node currently being considered.
     * @param s is the new string to add to Trie.
     * @param local is the charater in s being considered at this Node.
     * @return Node is the created node that goes into the previous nodes map.
     */
    private Node add(Node n, String s, int local) {
        if (n == null) {
            n = new Node();
        }
        if (local == s.length()) {
            n.exists(true);
            return n;
        }
        char c = s.charAt(local);
        n.addLink(c, add(n.get(c), s, local + 1));
        return n;
    }
}
