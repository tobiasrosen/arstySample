/**
 *
 */


import java.util.TreeMap;
import java.util.Comparator;

/**
 * @author Tobias Rosen
 * Autocomplete Node Map is a TreeMap that stores Nodes for Autocomplete. 
 * Each key in this map in a Node of Trie. Each value is string with each char being the
 * preseeding Node.
 * This map offers utility methods that obtian the maximum and minimum Nodes.
 * A maximum Node is the Node that has the highest weighted word within its descendents
 * of all Nodes in the map. A minimum node is the opposite.
 * This map has a maximum number of Nodes that is provided upon construction.
 * THis Map is the underlying structure of Autocomplete's priority queue.
 */
public class AutocompleteNodeMap extends TreeMap<Node, String> {
    // maxEntries is the max entries allowed in this Map
    int maxEntries;

    public AutocompleteNodeMap(Comparator c, int k) {
    	super(c);
    	maxEntries = k;
    }

    /**
     * putSafe() puts an item into this map only if is should be added.
     * An entry should be added only if this map has space (size less maxEntries.)
     * or the new node is larger than the smallest node in this map.
     * In the later case the smaller item will be removed.
     * @param n a key for a new map entry.
     * @param s a string representation of all the nodes that connect trieRoot to n.
     */
    public void putSafe(Node n, String s) {
    	if (maxEntries > size()) {
    		put(n, s);
    	} else {
    		Node smallest = lastKey();
    		double smallestC = smallest.trieMax();
    		double nC = n.trieMax();
    		if (nC > smallestC) {
    			remove(smallest);
    			putSafe(n, s);
    		}
    	}
    }
}