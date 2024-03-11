package sk.majba.montecarlo.be;

import java.util.Map;
import java.util.TreeMap;

/**
 * Custom TreeMap implementation that contains a range as its keys and accept any object as its values
 * @param <E> the object value of the map
 */
public class RangeMap<E> {
    private final TreeMap<Range, E> map;

    public RangeMap() {
        map = new TreeMap<>(new RangeComparator());
    }

    public void put(int lowerBound, int upperBound, E value) {
        map.put(new Range(lowerBound, upperBound), value);
    }

    public E get(int number) {
        // If we access the key and value of each element in a loop it is better to use the entrySet method
        for (Map.Entry<Range, E> entry : map.entrySet()) {
            if (entry.getKey().contains(number)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
