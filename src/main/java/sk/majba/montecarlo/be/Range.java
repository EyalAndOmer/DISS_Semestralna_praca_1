package sk.majba.montecarlo.be;

/**
 * Class that interprets a range given by 2 integers.
 * @param lowerBound The lower bound of the given interval (inclusive)
 * @param upperBound The upper bound of the given interval (inclusive)
 */
public record Range(int lowerBound, int upperBound) {
    public boolean contains(int number) {
        return number >= lowerBound && number <= upperBound;
    }

}
