package sk.majba.montecarlo.be;

import java.util.Comparator;

/**
 * Custom range comparator implementation that sorts the values in ascending order.
 */
class RangeComparator implements Comparator<Range> {
    @Override
    public int compare(Range r1, Range r2) {
        if (r1.lowerBound() != r2.lowerBound()) {
            return r1.lowerBound() - r2.lowerBound();
        } else {
            return r1.upperBound() - r2.upperBound();
        }
    }
}
