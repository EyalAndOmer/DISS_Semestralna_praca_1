package sk.majba.montecarlo.be;

import java.util.Comparator;

class RangeComparator implements Comparator<Range> {
    @Override
    public int compare(Range r1, Range r2) {
        if (r1.getLowerBound() != r2.getLowerBound()) {
            return r1.getLowerBound() - r2.getLowerBound();
        } else {
            return r1.getUpperBound() - r2.getUpperBound();
        }
    }
}
