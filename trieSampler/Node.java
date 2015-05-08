/**
 *@author Tobias Rosen
 */

import java.util.HashMap;    

/**
 * Node is node in a Trie. 
 * Node stores existence of this word in boolean exists.
 * Node stores a HashMap of links to other Nodes.
 * A node exists if it the end of chain of Nodes that make up a word in Trie.
 * If Node is stored in Trie that is weighted values TrieMax and existsWeight are used.
 * trieMAx is the max weight stored in the Trie where this is root.
 * if this node exists, existsWeight corresponds to the word made up by this Node.
 */
public class Node {
    private boolean exists;
    HashMap<Character, Node> links;
    private double existsWeight;
    private double trieMax;
    char name;

    /** 
     * Node Constructor. New node exists is set to false and has empty links.
     */
    public Node() {
        exists = false;
        links = new HashMap<Character, Node>(2);
        existsWeight = -1;
        trieMax = 0;
    }

    /**
     * An alternat constructor that give a Node a name.
     * @param c is the future name of node
     */
    public Node(char c) {
        this();
        name = c;
    }

    /**
     * addLink() adds a new link from this node to another by adding to links
     * @param c the new key (type char) for key-value pair in links.
     * @param node the new value (type Node) for key-value pair in links.
     */
    public void addLink(char c, Node node) {
        links.put(c, node);
    }

    /**
     * exists() checks if this node Exists in Trie.
     * @return boolean if true this Node is the end of a word in Trie.
     */
    public boolean exists() {
        return exists;
    }

    /**
     * exists() sets exists to b.
     * @param b is a boolean that replaces exists.
     */
    public void exists(boolean b) {
        exists = b;
    }

    /**
     * existsWeight() sets the existsWieght of this Node.
     * @param val becomes the existWeight of this.
     */
    public void existWeight(double val) {
        existsWeight = val;
    }

    /**
     * trieWeight() sets the trieMax of this Node.
     * @param val becomes the trieMax of this.
     */
    public void trieWeight(double val) {
        trieMax = val;
    }

    /**
     * trieMax() returns the max word in Trie rooted at this.
     */
    public double trieMax() {
        return trieMax;
    }

    /**
     * existVal() returns the value of word that end in this node.
     * this function returns -1 if node does not exist.
     */
    public double existVal() {
        return existsWeight;
    }

    /**
     * containsKey() checks if this Node links to another node.
     * @param c the key of the value node being checked to exist.
     * @return boolean if or if not this node links to a node representing c.
     */
    public boolean containsKey(Character c) {
        return links.containsKey(c);
    }

    /**
     * get() returns a node in links.
     * @param c is a character key of the Node desired.
     * @return Node the node that represents c in this Map.
     */
    public Node get(Character c) {
        return links.get(c);
    }
}