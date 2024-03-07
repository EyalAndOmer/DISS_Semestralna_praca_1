package sk.majba.montecarlo.be;

import java.util.TreeMap;

public class RangeMap<E> {
    private final TreeMap<Range, E> map;

    public RangeMap() {
        map = new TreeMap<>(new RangeComparator());
    }

    public void put(int lowerBound, int upperBound, E value) {
        map.put(new Range(lowerBound, upperBound), value);
    }

    public E get(int number) {
        for (Range range : map.keySet()) {
            if (range.contains(number)) {
                return map.get(range);
            }
        }
        return null;
    }
}
