
import java.util.Comparator;


/**
 * @author Tobias Rosen
 * AutocompleteNodeComparator compares nodes so for Autocomplete so that they are in order 
 * for a AutocompleteNodeMap.
 */
public class AutocompleteNodeComparator implements Comparator {
    
    /**
     * compare() compares two objects returning -1, 0, 1 if the first object
     * is less than, equal to, or grater than the second object respectively.
     * @param o1 the first object to compare.
     * @param o2 the second object to compare.
     * @return int represeting which object is larger.
     */
    @Override
    public int compare(Object o1, Object o2) {
        Node n1 = (Node) o1;
        Node n2 = (Node) o2;
        return compare(n1, n2);
    }
    /**
     * A helper compare that compares two Node specifically. See above for details.
     * @param o1 the first object to compare.
     * @param o2 the second object to compare.
     * @return int represeting which object is larger.
     */
    public int compare(Node n1, Node n2) {
        double n1val = n1.trieMax();
        double n2val = n2.trieMax();

        if (n1val < n2val) {
            return 1;
        }
        if (n1val == n2val) {
            return n2.hashCode() - n1.hashCode();
        } else {
            return -1;
        }

    }
}