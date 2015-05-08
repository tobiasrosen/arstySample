/**
 * @author Tobias Rosen
 */

import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;


public class AutocompleteTopMatches {
    // prefixT is the start node of all suffixes. It corresponds to the Node of last letter in prefix.
    private Node prefixT;
    private String prefix;
    private int k;
    private HashMap<String, Double> weights;
    // topKMap is a map of the highest priority Nodes to search.
    private AutocompleteNodeMap topKMap;
    //topKMatch are the top suffixes.
    private LinkedList<String> topKMatch;
    //minFind is the prefix with the smallest match in topKMatch;
    private double minFind;

    public AutocompleteTopMatches(Node n, String prefix, int k, HashMap<String, Double> w) {
        prefixT = n;
        this.prefix = prefix;
        this.k = k;
        weights = w;
        topKMap = new AutocompleteNodeMap(new AutocompleteNodeComparator(), k);
        topKMatch = new LinkedList<String>();
        if (prefixT.exists()) {
             topKMap.putSafe(prefixT, prefix);
        }
        findLeads(prefixT, prefix);
        minFind = Double.MAX_VALUE;
    }

    /**
     * createMatches() generates k suffix matches for k.
     * @return an iterable of K suffix matches.
     */
    public Iterable<String> createMatches() {
        while (checkMore()) {
            Node next = topKMap.firstKey();
            String nextString = topKMap.get(next);
            topKMap.remove(next);
            if (next.exists()) {
                wordMatchSafe(next, nextString);
            }
            findLeads(next, nextString);
        }
        return topKMatch;
    }

    /**
     * wordMatchSafe() adds a word to topKMatch if the word is safe.
     * A word is considered safe to add if topKMatch is not full, or 
     * the word is larger than the kth largest entry in the topKMAtch.
     * IN the later case the kth entry will be removed and new will be added.
     * @param new a node to be added to topKMatch.
     * @param newString the string correpinding to that new Node.
     */
    private void wordMatchSafe(Node newN, String newString) {
        if (topKMatch.contains(newString)) {
            return;
        }
        if (topKMatch.size() < k) {
            int index = findInsertIndex(newString);
            topKMatch.add(index, newString);
            minFind = weights.get(topKMatch.getLast());
        } else {
            String oldMatch = topKMatch.getLast();
            double oldMatchVal = weights.get(oldMatch);
            double neMatchVal = weights.get(newString);
            if (neMatchVal > oldMatchVal) {
                topKMatch.remove(oldMatch);
                wordMatchSafe(newN, newString);
            }
        }
    }

    /**
     * findInsertIndex() finds the index at which this item should be inserted into 
     * topKMatch. 
     * @param newMatch is a string represented the new word.
     */
    private int findInsertIndex(String newMatch) {
        double newVal = weights.get(newMatch);
        for (int i = 0; i < topKMatch.size(); i++) {
            double oldVal = weights.get(topKMatch.get(i));
            if (newVal > oldVal) {
                return i;
            }
        }
        return topKMatch.size();
    }

    /**
     * findLeads() searchs a Nodes children and adds the top k or less nodes to topKQueue.
     * @param node is the node beoing searched.
     * @param sofar corresponds to any letters between the prefix and node.
     */
    public void findLeads(Node node, String sofar) {
        for (char c : node.links.keySet()) {
            Node curr = node.links.get(c);
            topKMap.putSafe(curr, sofar + c);
        }
    }

    /** 
     * checkMore() returns true if the algorithm still needs to generate more suffixes.
     * checkMore() will return false iff the kth item in topKMatch is >=
     * the first item in topKQueue or topKQueue is empty. If there are not amounToFind items
     * in topKMatch return false or if first item in topKQueue is larger than kth item.
     */
    public boolean checkMore() {
        if (topKMap.isEmpty()) {
            return false;
        }
        if (topKMatch.size() == k) {
            Node largestQ = topKMap.firstKey();
            double largestQVal = largestQ.trieMax();
            if (largestQVal <= minFind) {
                return false;
            }
        }
        return true;
    }


}
